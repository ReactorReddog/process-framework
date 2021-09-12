package org.processframework.gateway.common.configurate;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.core.ApiConfig;
import org.processframework.gateway.common.properties.ApiConfigProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @author apple
 */
@Slf4j
@EnableConfigurationProperties(ApiConfigProperties.class)
public class BaseGatewayAutoConfiguration {

    @Autowired
    private ApiConfigProperties apiConfigProperties;

    @PostConstruct
    public void after() {
        log.info("网关基本配置：{}", JSON.toJSONString(apiConfigProperties));
        ApiConfig apiConfig = ApiConfig.getInstance();
        BeanUtils.copyProperties(apiConfigProperties, apiConfig);
    }

}