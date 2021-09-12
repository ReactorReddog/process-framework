package org.processframework.gateway.common.constant;

/**
 * @author apple
 * @desc 网关常量
 * @since 1.0.0.RELEASE
 */
public class GatewayConstant {
    /**
     * 空json
     */
    public static final String EMPTY_JSON = "{}";
    /**
     * error header头编码
     */
    public static final String X_SERVICE_ERROR_CODE = "x-service-error-code";
    /**
     * 错误头信息
     */
    public static final String X_SERVICE_ERROR_MESSAGE = "x-service-error-message";
    /**
     * cache prame
     */
    public static final String CACHE_ROUTE_INTERCEPTOR_CONTEXT = "cacheRouteInterceptorContext";
    /**
     * Attribute key
     */
    public static final String THROWABLE_KEY = "process.throwable.attribute";
    /**
     * 路由键
     */
    public static final String CACHE_ROUTE_INFO = "cacheRouteInfo";
    /**
     * 参数cache
     */
    public static final String CACHE_API_PARAM = "cacheApiParam";
    /**
     * 上传请求
     */
    public static final String CACHE_UPLOAD_REQUEST = "cacheUploadRequest";
    /**
     * 重定向非法访问地址
     */
    public static final String UNKNOWN_PATH = "/process/unknown";
    /**
     * 签名验证失败请求地址
     */
    public static final String VALIDATE_ERROR_PATH = "/process/validateError";
    /**
     * 网关配置前缀
     */
    public static final String GATEWAY_PROPERTIES_PREFIX = "spring.cloud.gateway.process";
    /**
     *
     */
    public static final String METADATA_SERVER_CONTEXT_PATH = "server.servlet.context-path";
    /**
     *
     */
    public static final String METADATA_SERVER_CONTEXT_PATH_COMPATIBILITY = "context-path";
    /**
     * 预发布服务器key
     */
    public static final String METADATA_ENV_KEY = "env";
    /**
     * 预发布服务器value
     */
    public static final String METADATA_ENV_PRE_VALUE = "pre";
    /**
     * 是否是灰度发布服务器value
     */
    public static final String METADATA_ENV_GRAY_VALUE = "gray";
    public static final String TARGET_SERVICE = "target-service";
    public static final String RESTFUL_REQUEST = "restful-request";

    public static final String PROCESS_ROUTES_PATH = "/process/route";

    public static final String METADATA_PROCESS_ROUTES_PATH = "process.routes.path";

}
