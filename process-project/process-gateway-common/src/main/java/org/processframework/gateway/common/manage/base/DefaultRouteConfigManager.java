package org.processframework.gateway.common.manage.base;

import com.cn.processframework.tools.BeanUtil;
import org.processframework.gateway.common.constant.RouteStatus;
import org.processframework.gateway.common.core.RouteConfig;
import org.processframework.gateway.common.manage.RouteConfigManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author apple
 * @desc 路由配置默认实现
 */
public class DefaultRouteConfigManager implements RouteConfigManager {
    /**
     * key: routeId
     */
    protected static Map<String, RouteConfig> routeConfigMap = new ConcurrentHashMap<>(64);

    private static final RouteConfig DEFAULT_ROUTE_CONFIG;

    static {
        DEFAULT_ROUTE_CONFIG = new RouteConfig();
        DEFAULT_ROUTE_CONFIG.setStatus(RouteStatus.ENABLE.getStatus());
    }

    @Override
    public void load(String serviceId) {

    }

    @Override
    public void update(RouteConfig routeConfig) {
        this.doUpdate(routeConfig.getRouteId(), routeConfig);
    }

    protected void save(RouteConfig routeConfig) {
        Byte status = routeConfig.getStatus();
        if (status == null) {
            routeConfig.setStatus(RouteStatus.ENABLE.getStatus());
        }
        routeConfigMap.put(routeConfig.getRouteId(), routeConfig);
    }

    protected void doUpdate(String routeId, Object res) {
        RouteConfig routeConfig = routeConfigMap.get(routeId);
        if (routeConfig == null) {
            routeConfig = newRouteConfig();
            routeConfig.setRouteId(routeId);
            routeConfigMap.put(routeId, routeConfig);
        }
//        MyBeanUtil.copyPropertiesIgnoreNull(res, routeConfig);
        BeanUtil.copyNonNull(res,routeConfig);
    }

    protected RouteConfig newRouteConfig() {
        return new RouteConfig();
    }

    @Override
    public RouteConfig get(String routeId) {
        return routeConfigMap.getOrDefault(routeId, DEFAULT_ROUTE_CONFIG);
    }
}
