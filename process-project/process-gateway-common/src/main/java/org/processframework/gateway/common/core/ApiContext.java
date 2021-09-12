package org.processframework.gateway.common.core;

/**
 * @author apple
 * @desc 应用上下文
 * @since 1.0.0.RELEASE
 */
public class ApiContext {
    public static ApiConfig getApiConfig() {
        return ApiConfig.getInstance();
    }

    public static void setApiConfig(ApiConfig apiConfig) {
        ApiConfig.setInstance(apiConfig);
    }

}
