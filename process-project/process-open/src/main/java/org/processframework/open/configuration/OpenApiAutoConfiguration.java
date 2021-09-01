package org.processframework.open.configuration;

import lombok.extern.slf4j.Slf4j;
import org.processframework.open.OpenApiManageProcessor;
import org.processframework.open.properties.OpenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author apple
 * @desc 配置
 * @since 1.0.0.RELEASE
 */
@Configuration
@EnableConfigurationProperties({OpenProperties.class})
@ConditionalOnClass({OpenApiManageProcessor.class})
@Slf4j
public class OpenApiAutoConfiguration {

    @Autowired
    private OpenProperties openProperties;

    @Bean
    @ConditionalOnMissingBean({OpenApiManageProcessor.class})
    public OpenApiManageProcessor openApiManageProcessor(){

    }
}
