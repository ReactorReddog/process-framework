package org.processframework.gateway.common.manage;

import org.processframework.gateway.common.core.RouteConfig;
import org.processframework.gateway.common.core.ServiceBeanInitializer;

/**
 * 路由配置管理
 * @author apple
 */
public interface RouteConfigManager extends ServiceBeanInitializer {
    /**
     * 更新路由配置
     * @param routeConfig 路由配置
     */
    void update(RouteConfig routeConfig);

    /**
     * 获取路由配置
     * @param routeId 路由id
     * @return 返回RouteConfig
     */
    RouteConfig get(String routeId);
}
