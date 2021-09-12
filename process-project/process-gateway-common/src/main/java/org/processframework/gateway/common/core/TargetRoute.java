package org.processframework.gateway.common.core;

/**
 * @author apple
 * @desc 路由
 * @since 1.0.0.RELEASE
 */
public interface TargetRoute {

    /**
     * 返回服务信息
     *
     * @return 返回服务信息
     */
    ServiceDefinition getServiceDefinition();

    /**
     * 返回微服务路由对象
     *
     * @return 返回微服务路由对象
     */
    RouteDefinition getRouteDefinition();

    String getFullPath();

}
