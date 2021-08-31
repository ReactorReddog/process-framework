package org.processframework.doc.configuration;

import org.processframework.doc.properties.DocProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author apple
 * @desc doc配置
 * @since 1.0.0.RELEASE
 */
@EnableConfigurationProperties({DocProperties.class})
public class ProcessDocConfiguration {
}
