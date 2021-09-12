package org.processframework.gateway.common.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author apple
 * @desc 服务名称信息
 * @since 1.0.0.RELEASE
 */
@Data
@AllArgsConstructor
public class ServiceDefinition {

    /**
     * 服务名称，对应spring.application.name
     */
    private String serviceId;

    public String fetchServiceIdLowerCase() {
        return serviceId.toLowerCase();
    }
}