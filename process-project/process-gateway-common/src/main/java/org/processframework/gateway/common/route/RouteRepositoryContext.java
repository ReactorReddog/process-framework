package org.processframework.gateway.common.route;

import org.processframework.gateway.common.core.TargetRoute;
import org.processframework.gateway.common.manage.RouteRepository;

/**
 * @author apple
 * @desc 路由上下文
 * @since 1.0.0.RELEASE
 */
public class RouteRepositoryContext {

    private RouteRepositoryContext() {
    }

    private static RouteRepository<? extends TargetRoute> routeRepository;

    public static RouteRepository<? extends TargetRoute> getRouteRepository() {
        return routeRepository;
    }

    public static <T extends TargetRoute> void setRouteRepository(RouteRepository<T> routeRepository) {
        RouteRepositoryContext.routeRepository = routeRepository;
    }

    /**
     * 根据路由id查询路由
     *
     * @param routeId 路由id
     * @return 返回路由信息，没有返回null
     */
    public static TargetRoute getTargetRoute(String routeId) {
        return routeRepository.get(routeId);
    }

}
