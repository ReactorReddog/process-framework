package org.processframework.gateway.common.core;

import lombok.Data;

import java.util.Map;

/**
 * @author apple
 * @desc 服务实例信息
 * @since 1.0.0.RELEASE
 */
@Data
public class InstanceDefinition {
    /**
     * 服务实例id
     */
    private String instanceId;
    /**
     * 服务id
     */
    private String serviceId;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * 扩展信息
     */
    private Map<String, String> metadata;
}
