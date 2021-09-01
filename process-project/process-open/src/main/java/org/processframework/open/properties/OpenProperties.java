package org.processframework.open.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.processframework.open.properties.OpenProperties.OPEN_PREFIX;

/**
 * @author apple
 * @desc 环境配置
 * @since 1.0.0.RELEASE
 */
@ConfigurationProperties(prefix = OPEN_PREFIX)
public class OpenProperties {
    /**
     * 前缀
     */
    public static final String OPEN_PREFIX = "process.open";
    /**
     * 是否开启开放平台 默认为开启
     */
    private boolean enabled = true;
}
