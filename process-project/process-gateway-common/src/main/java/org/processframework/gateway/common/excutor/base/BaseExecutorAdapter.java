package org.processframework.gateway.common.excutor.base;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.ErrorEnum;
import org.processframework.gateway.common.ParamNames;
import org.processframework.gateway.common.context.RouteInterceptorContext;
import org.processframework.gateway.common.core.ApiConfig;
import org.processframework.gateway.common.excutor.DataNameBuilder;
import org.processframework.gateway.common.excutor.ResultAppender;
import org.processframework.gateway.common.excutor.ResultExecutor;
import org.processframework.gateway.common.manage.Isv;
import org.processframework.gateway.common.manage.IsvManager;
import org.processframework.gateway.common.validate.alipay.AlipaySignature;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.processframework.gateway.common.ErrorEnum.*;
import static org.processframework.gateway.common.constant.GatewayConstant.EMPTY_JSON;

/**
 * @author apple
 * @desc 处理微服务中结果返回
 * @param <T>
 * @param <R>
 */
@Slf4j
public abstract class BaseExecutorAdapter <T,R> implements ResultExecutor<T,R> {
    /**
     * 初始化http状态码 ${@link ErrorEnum}
     */
    private static final Map<Integer, ErrorEnum> HTTP_STATUS_ERROR_ENUM_MAP = new HashMap<>(8);
    /**
     * 返回码参数
     */
    private static final String GATEWAY_CODE_NAME = "code";
    /**
     * 返回码描述参数
     */
    private static final String GATEWAY_MSG_NAME = "msg";
    /**
     * 开始区域
     */
    private static final String ARRAY_START = "[";
    /**
     * 结束区域
     */
    private static final String ARRAY_END = "]";
    /**
     * json替换
     */
    private static final String ROOT_JSON = "{'items':%s}".replace("'", "\"");
    static {
        HTTP_STATUS_ERROR_ENUM_MAP.put(HttpStatus.OK.value(), SUCCESS);
        HTTP_STATUS_ERROR_ENUM_MAP.put(HttpStatus.BAD_REQUEST.value(),ISP_UNKNOWN_ERROR);
        HTTP_STATUS_ERROR_ENUM_MAP.put(HttpStatus.NO_CONTENT.value(), ISV_MISSING_METHOD);
    }

    /**
     * 获取业务方约定返回码
     * @param t request
     * @return 返回码
     */
    public abstract Integer getResponseStatus(T t);

    /**
     * 获取微服务端错误信息
     * @param t request
     * @return 错误信息
     */
    public abstract String getResponseErrorMessage(T t);

    /**
     * 返回apiparam参数
     * @param t request
     * @return 返回apiParam参数
     */
    public abstract ApiParam getApiParam(T t);

    /**
     * 获取locale
     * @param t request
     * @return 返回locale
     */
    protected abstract Locale getLocale(T t);

    /**
     * 返回拦截器上下文
     * @param t request
     * @return 拦截器上下文
     */
    protected abstract RouteInterceptorContext getRouteInterceptorContext(T t);

    @Override
    public String mergeResult(T request, String serviceResult) {
        return null;
    }

    /**
     * 格式化返回结果
     * @param serviceResult serviceResult
     * @return 结果
     */
    protected String formatResult(String serviceResult) {
        if (StringUtils.isBlank(serviceResult)) {
            return EMPTY_JSON;
        }
        // 如果直接返回数组，需要进行包装，变成：{"items": [...]}
        if (serviceResult.startsWith(ARRAY_START) && serviceResult.endsWith(ARRAY_END)) {
            return String.format(ROOT_JSON, serviceResult);
        }
        return serviceResult;
    }

    /**
     * 该路由是否合并结果
     *
     * @param request request
     * @return true：需要合并
     */
    protected boolean isMergeResult(T request) {
        ApiParam params = this.getApiParam(request);
        return params != null && params.fetchMergeResult();
    }

    public String merge(T exchange, Map<String, Object> responseData) {
        JSONObject finalData = new JSONObject(true);
        ApiParam params = this.getApiParam(exchange);
        if (params == null) {
            params = new ApiParam();
            params.setName("error");
        }
        ApiConfig apiConfig = ApiConfig.getInstance();
        // 点换成下划线
        DataNameBuilder dataNameBuilder = apiConfig.getDataNameBuilder();
        // alipay_goods_get_response
        String responseDataNodeName = dataNameBuilder.build(params.fetchName());
        finalData.put(responseDataNodeName, responseData);
        ResultAppender resultAppender = apiConfig.getResultAppender();
        // 追加额外的结果
        if (resultAppender != null) {
            resultAppender.append(finalData, params, exchange);
        }
        // 添加服务端sign
        this.addResponseSign(apiConfig, params, finalData, responseDataNodeName);
        return finalData.toJSONString();
    }

    /**
     * 添加服务端sign
     * @param apiConfig apiconfig
     * @param params apiParam
     * @param finalData json数据
     * @param responseDataNodeName 最终构建转换的方法名
     */
    private void addResponseSign(ApiConfig apiConfig, ApiParam params, JSONObject finalData, String responseDataNodeName) {
        if (apiConfig.isShowReturnSign() && !CollectionUtils.isEmpty(params)) {
            // 添加try...catch，生成sign出错不影响结果正常返回
            try {
                String responseSignContent = this.buildResponseSignContent(responseDataNodeName, finalData);
                String sign = this.createResponseSign(apiConfig.getIsvManager(), params, responseSignContent);
                if (StringUtils.isNotBlank(sign)) {
                    finalData.put(ParamNames.RESPONSE_SIGN_NAME, sign);
                }
            } catch (Exception e) {
                log.error("生成平台签名失败, params: {}", params.toJSONString(), e);
            }
        }
    }
    /**
     * 获取待签名内容
     *
     * @param rootNodeName 业务数据节点
     * @param finalData    最终结果
     * @return 返回待签名内容
     */
    protected String buildResponseSignContent(String rootNodeName, JSONObject finalData) {
        String body = finalData.toJSONString();
        int indexOfRootNode = body.indexOf(rootNodeName);
        if (indexOfRootNode > 0) {
            int signDataStartIndex = indexOfRootNode + rootNodeName.length() + 2;
            int length = body.length() - 1;
            return body.substring(signDataStartIndex, length);
        }
        return null;
    }
    /**
     * 这里需要使用平台的私钥生成一个sign，需要配置两套公私钥。
     *
     * @param isvManager          isvManager
     * @param params              请求参数
     * @param responseSignContent 待签名内容
     * @return 返回平台生成的签名
     */
    protected String createResponseSign(IsvManager isvManager, ApiParam params, String responseSignContent) {
        if (StringUtils.isEmpty(responseSignContent)) {
            return null;
        }
        // 根据appId获取秘钥
        String appKey = params.fetchAppKey();
        if (StringUtils.isEmpty(appKey)) {
            return null;
        }
        Isv isvInfo = isvManager.getIsv(appKey);
        String privateKeyPlatform = isvInfo == null ? null : isvInfo.getPrivateKeyPlatform();
        if (StringUtils.isEmpty(privateKeyPlatform)) {
            return null;
        }
        return AlipaySignature.rsaSign(
                responseSignContent
                , privateKeyPlatform
                , params.fetchCharset()
                , params.fetchSignMethod()
        );
    }
}
