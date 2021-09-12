package org.processframework.gateway.common.core;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.processframework.gateway.common.ApiValidator;
import org.processframework.gateway.common.TokenValidator;
import org.processframework.gateway.common.Validator;
import org.processframework.gateway.common.excutor.*;
import org.processframework.gateway.common.manage.*;
import org.processframework.gateway.common.manage.base.*;
import org.processframework.gateway.common.manage.formatter.ParameterFormatter;
import org.processframework.gateway.common.manage.loadbalancer.builder.GrayUserBuilder;
import org.processframework.gateway.common.manage.isv.MemoryCacheIsvManage;
import org.processframework.gateway.common.properties.ApiConfigProperties;
import org.processframework.gateway.common.route.RouteInterceptor;
import org.processframework.gateway.common.validate.Signer;
import org.processframework.gateway.common.validate.alipay.AlipaySigner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author apple
 * @desc 核心引用
 * @since 1.0.0.RELEASE
 */
@Data
public class ApiConfig {
    private static ApiConfig instance = new ApiConfig();

    /**
     * 获取apiConfig实例
     * @return
     */
    public static ApiConfig getInstance(){
        return instance;
    }


    /**
     * 设置apiConfig实例
     * @param apiConfig
     */
    public static void setInstance(ApiConfig apiConfig) {
        instance = apiConfig;
    }
    /**
     * 构建数据节点名称
     */
    private DataNameBuilder dataNameBuilder = new DefaultDataNameBuilder();
    /**
     * 校验token
     */
    private TokenValidator tokenValidator = apiParam -> apiParam != null && StringUtils.isNotBlank(apiParam.fetchAccessToken());

    /**
     * 验证
     */
    private Validator validator = new ApiValidator();
    /**
     * 追加结果
     */
    private ResultAppender resultAppender;

    /**
     * 显示返回sign
     */
    private boolean showReturnSign = true;
    /**
     * isv管理器
     */
    private IsvManager<IsvDefinition>isvManager =new MemoryCacheIsvManage();

    /**
     * 是否对结果进行合并。<br>
     * 默认情况下是否合并结果由微服务端决定，一旦指定该值，则由该值决定，不管微服务端如何设置。
     */
    private Boolean mergeResult;

    /**
     * gateway合并结果处理
     */
    private ResultExecutorForGateway gatewayResultExecutor = new GatewayResultExecutor();

    /**
     * 忽略验证，设置true，则所有接口不会进行签名校验
     */
    private boolean ignoreValidate;
    /**
     * 灰度实例
     */
    private List<GrayUserBuilder> grayUserBuilders;

    public void addGrayUserBuilder(GrayUserBuilder grayUserBuilder) {
        grayUserBuilders.add(grayUserBuilder);
        grayUserBuilders.sort(Comparator.comparing(GrayUserBuilder::order));
    }


    /**
     * 错误模块
     */
    private List<String> i18nModules = new ArrayList<>();


    /**
     * 处理错误信息
     */
    private ServiceErrorManager serviceErrorManager = new DefaultServiceErrorManager();
    /**
     * 网关配置信息
     */
    private ApiConfigProperties gatewayProperties;
    /**
     * ISV路由权限信息
     */
    private IsvRoutePermissionManager isvRoutePermissionManager = new DefaultIsvRoutePermissionManager();
    /**
     * 路由配置
     */
    private RouteConfigManager routeConfigManager = new DefaultRouteConfigManager();
    /**
     * 限流配置
     */
    private LimitConfigManager limitConfigManager = new DefaultLimitConfigManager();
    /**
     * 限流管理
     */
    private LimitManager limitManager = new DefaultLimitManager();
    /**
     * ip黑名单
     */
    private IPBlacklistManager ipBlacklistManager = new DefaultIPBlacklistManager();
    /**
     * 用户key管理
     */
    private EnvGrayManager userKeyManager = new DefaultEnvGrayManager();
    /**
     * 参数格式化
     */
    private ParameterFormatter parameterFormatter;
    /**
     * 路由拦截器
     */
    private List<RouteInterceptor> routeInterceptors = new ArrayList<>(4);
    /**
     * 是否开启限流功能
     */
    private boolean openLimit = true;
    private boolean useGateway;

    /**
     * 签名工具
     */
    private Signer signer = new AlipaySigner();
    /**
     * 获取监控数据
     */
    private MonitorManager monitorManager = new MonitorManager();
    /**
     * 超时时间
     */
    private int timeoutSeconds = 60 * 5;
}
