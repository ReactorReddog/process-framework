package org.processframework.gateway.common.route;

import org.processframework.gateway.common.core.InstanceDefinition;

/**
 * @author apple
 * @desc 服务监听
 * @since 1.0.0.RELEASE
 */
public interface ServiceListener {
    /**
     * 移除服务实例
     * @param serviceId 服务id
     */
    void onRemoveService(String serviceId);

    /**
     * 添加服务实例
     * @param instance 服务实例
     */
    void onAddInstance(InstanceDefinition instance);
}
