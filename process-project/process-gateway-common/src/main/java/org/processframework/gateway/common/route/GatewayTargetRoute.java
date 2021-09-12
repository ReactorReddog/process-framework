package org.processframework.gateway.common.route;

import org.processframework.gateway.common.core.RouteDefinition;
import org.processframework.gateway.common.core.ServiceDefinition;
import org.processframework.gateway.common.core.TargetRoute;
import org.springframework.util.StringUtils;

/**
 * @author apple
 * @desc 网关路由
 * @since 1.0.0.RELEASE
 */
public class GatewayTargetRoute implements TargetRoute {
    /**
     * 服务信息
     */
    private ServiceDefinition serviceDefinition;
    /**
     * 路由信息
     */
    private RouteDefinition routeDefinition;


    public GatewayTargetRoute(ServiceDefinition serviceDefinition, RouteDefinition routeDefinition) {
        this.serviceDefinition = serviceDefinition;
        this.routeDefinition = routeDefinition;
    }

    @Override
    public String getFullPath() {
        String serviceId = serviceDefinition.getServiceId();
        String path = StringUtils.trimLeadingCharacter(routeDefinition.getPath(), '/');
        return "/" + serviceId + "/" + path;
    }

    @Override
    public ServiceDefinition getServiceDefinition() {
        return serviceDefinition;
    }

    @Override
    public RouteDefinition getRouteDefinition() {
        return routeDefinition;
    }
}
