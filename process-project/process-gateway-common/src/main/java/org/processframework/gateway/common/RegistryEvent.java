package org.processframework.gateway.common;

import org.processframework.gateway.common.core.InstanceDefinition;

/**
 * @author apple 
 * @desc 新的实例注册事件
 */
public interface RegistryEvent {

    /**
     * 新实例注册进来时触发
     * @param instanceDefinition 实例信息
     */
    void onRegistry(InstanceDefinition instanceDefinition);

    /**
     * 服务下线时触发
     * @param serviceId 服务id
     */
    void onRemove(String serviceId);
}
