package org.processframework.gateway.common.route;

import org.processframework.gateway.common.core.InstanceDefinition;
import org.processframework.gateway.common.core.ServiceRouteInfo;

/**
 * @author apple
 * @desc 路由
 * @since 1.0.0.RELEASE
 */
public interface RoutesProcessor {
    /**
     * 删除serviceId下所有路由
     *
     * @param serviceId serviceId
     */
    void removeAllRoutes(String serviceId);

    /**
     * 保存路由
     *
     * @param serviceRouteInfo 路由信息
     * @param instance 服务实例
     */
    void saveRoutes(ServiceRouteInfo serviceRouteInfo, InstanceDefinition instance);
}
