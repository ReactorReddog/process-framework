package org.processframework.gateway.common.route;

import org.processframework.gateway.common.core.ServiceRouteInfo;

import java.util.function.Consumer;

/**
 * @author apple
 * @desc 路由加载状态
 * @since 1.0.0.RELEASE
 */
public interface RouteLoader {
    /**
     * 加载路由
     *
     * @param serviceRouteInfo 服务路由信息
     * @param callback         加载成功后回调
     */
    void load(ServiceRouteInfo serviceRouteInfo, Consumer<Object> callback);

    /**
     * 移除某个微服务下的所有路由信息
     *
     * @param serviceId 服务id
     */
    void remove(String serviceId);
}
