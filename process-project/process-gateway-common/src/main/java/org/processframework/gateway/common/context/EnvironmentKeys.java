package org.processframework.gateway.common.context;

import org.processframework.gateway.common.EnvironmentContext;

/**
 * @author apple
 * @desc 默认参数配置
 * @since 1.0.0.RELEASE
 */
public enum EnvironmentKeys {
    SPRING_PROFILES_ACTIVE("spring.profiles.active", "default"),
    /**
     * spring.application.name
     */
    SPRING_APPLICATION_NAME("spring.application.name"),

    /**
     * sign.urlencode=true，签名验证拼接字符串的value部分进行urlencode
     */
    SIGN_URLENCODE("sign.urlencode"),

    /**
     * process.restful.path=/xx ，指定请求前缀，默认/rest
     */
    PROCESS_RESTFUL_PATH("process.restful.path", "/rest"),

    /**
     * 排除其它微服务，多个用英文逗号隔开
     */
    PROCESS_SERVICE_EXCLUDE("process.service.exclude"),
    /**
     * 排除其它微服务，正则形式，多个用英文逗号隔开
     */
    PROCESS_SERVICE_EXCLUDE_REGEX("process.service.exclude-regex"),
    /**
     * 预发布域名
     */
    PRE_DOMAIN("pre.domain"),

    /**
     * post请求body缓存大小
     */
    MAX_IN_MEMORY_SIZE("spring.codec.max-in-memory-size", "262144"),

    ;

    private final String key;
    private String defaultValue;

    public String getKey() {
        return key;
    }

    EnvironmentKeys(String key) {
        this.key = key;
    }

    EnvironmentKeys(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return EnvironmentContext.getValue(key, defaultValue);
    }

    public String getValue(String defaultValue) {
        return EnvironmentContext.getValue(key, defaultValue);
    }
}