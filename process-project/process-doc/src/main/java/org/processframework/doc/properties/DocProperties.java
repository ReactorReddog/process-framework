package org.processframework.doc.properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.processframework.doc.properties.DocProperties.PREFIX;

/**
 * @author apple 
 * @desc doc配置
 * @since 1.0.0.RELEASE
 */
@ConfigurationProperties(prefix = PREFIX)
public class DocProperties {
    public static final String PREFIX = "process.open.doc";
    /**
     * 数据库配置
     */
    private DataSourceProperties dataSource;
    /**
     * 沙箱地址
     */
    private String urlTest;
    /**
     * 正式访问地址
     */
    private String urlProd;
}
