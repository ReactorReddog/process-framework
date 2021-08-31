package org.processframework.doc.configuration;

import org.processframework.doc.DefaultCatelogConfigApi;
import org.processframework.doc.api.CatelogConfigApi;
import org.processframework.doc.properties.DocProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author apple
 * @desc doc配置
 * @since 1.0.0.RELEASE
 */
@Configuration
@EnableConfigurationProperties({DocProperties.class})
public class ProcessDocConfiguration {

    @ConditionalOnClass(CatelogConfigApi.class)
    private CatelogConfigApi getCateLogConfigApi(){
        return new DefaultCatelogConfigApi();
    }
}
