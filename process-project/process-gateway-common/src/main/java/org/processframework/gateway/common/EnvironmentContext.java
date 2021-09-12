package org.processframework.gateway.common;

import org.springframework.core.env.Environment;

/**
 * @author apple
 * @desc environment上下文
 */
public class EnvironmentContext {
    private static Environment environment;

    /**
     * 获取environment
     * @return
     */
    public static Environment getEnvironment() {
        return environment;
    }

    /**
     * 设置environment
     * @param environment
     */
    public static void setEnvironment(Environment environment) {
        EnvironmentContext.environment = environment;
    }

    /**
     * 获取属性
     * @param key key
     * @param defaultValue 默认key
     * @return
     */
    public static String getValue(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

}
