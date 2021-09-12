package org.processframework.gateway.common.excutor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.processframework.gateway.common.*;
import org.processframework.gateway.common.Error;
import org.processframework.gateway.common.constant.GatewayConstant;
import org.processframework.gateway.common.context.RouteInterceptorContext;
import org.processframework.gateway.common.excutor.base.BaseExecutorAdapter;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * @author apple
 * @desc 网关消息处理
 * @since 1.0.0.RELEASE
 */
public class GatewayResultExecutor extends BaseExecutorAdapter<ServerWebExchange, String> implements ResultExecutorForGateway {
    /**
     * 合并错误结果
     * @param request 请求
     * @param ex 返回
     * @return
     */
    @Override
    public String buildErrorResult(ServerWebExchange request, Throwable ex) {
        Locale locale = getLocale(request);
        Error error;
        if (ex.getCause() instanceof TimeoutException) {
            error = ErrorEnum.ISP_GATEWAY_RESPONSE_TIMEOUT.getErrorMeta().getError(locale);
        } else if (ex instanceof ApiException) {
            ApiException apiException = (ApiException) ex;
            error = apiException.getError(locale);
        } else {
            error = ErrorEnum.ISP_UNKNOWN_ERROR.getErrorMeta().getError(locale);
        }
        JSONObject jsonObject = (JSONObject) JSON.toJSON(error);
        return this.merge(request, jsonObject);
    }

    /**
     * 获取业务方约定返回码
     * @param serverWebExchange request
     * @return 返回码
     */
    @Override
    public Integer getResponseStatus(ServerWebExchange serverWebExchange) {
        HttpStatus statusCode = serverWebExchange.getResponse().getStatusCode();
        int responseStatus = statusCode.value();
        List<String> errorCodeList = serverWebExchange.getResponse().getHeaders().get(GatewayConstant.X_SERVICE_ERROR_CODE);
        if (!CollectionUtils.isEmpty(errorCodeList)) {
            String errorCode = errorCodeList.get(0);
            responseStatus = Integer.parseInt(errorCode);
        }
        return responseStatus;
    }
    /**
     * 获取微服务端错误信息
     * @param serverWebExchange request
     * @return 错误信息
     */
    @Override
    public String getResponseErrorMessage(ServerWebExchange serverWebExchange) {
        String errorMsg = null;
        List<String> errorMessageList = serverWebExchange.getResponse().getHeaders().get(GatewayConstant.X_SERVICE_ERROR_MESSAGE);
        if (!CollectionUtils.isEmpty(errorMessageList)) {
            errorMsg = errorMessageList.get(0);
        }
        if (StringUtils.hasText(errorMsg)) {
            errorMsg = UriUtils.decode(errorMsg, StandardCharsets.UTF_8);
        }
        serverWebExchange.getResponse().getHeaders().remove(GatewayConstant.X_SERVICE_ERROR_MESSAGE);
        return errorMsg;
    }
    /**
     * 返回apiparam参数
     * @param serverWebExchange request
     * @return 返回apiParam参数
     */
    @Override
    public ApiParam getApiParam(ServerWebExchange serverWebExchange) {
        return ServerWebExchangeUtil.getApiParam(serverWebExchange);
    }
    /**
     * 获取locale
     * @param serverWebExchange request
     * @return 返回locale
     */
    @Override
    protected Locale getLocale(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getLocaleContext().getLocale();
    }
    /**
     * 返回拦截器上下文
     * @param serverWebExchange request
     * @return 拦截器上下文
     */
    @Override
    protected RouteInterceptorContext getRouteInterceptorContext(ServerWebExchange serverWebExchange) {
        return (RouteInterceptorContext) serverWebExchange.getAttributes().get(GatewayConstant.CACHE_ROUTE_INTERCEPTOR_CONTEXT);
    }
}
