package org.processframework.gateway.common.core;

import lombok.Data;

/**
 * @author apple
 * @desc 动态服务实例
 * @since 1.0.0.RELEASE
 */
@Data
public class ServiceGrayDefinition {
    /**
     * 服务id
     */
    private String serviceId;
    /**
     * 实例id
     */
    private String instanceId;
    /**
     * 其他信息
     */
    private String data;
}
