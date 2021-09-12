package org.processframework.gateway.common;

import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.context.DefaultRouteInterceptorContext;
import org.processframework.gateway.common.context.RouteInterceptorContext;
import org.processframework.gateway.common.core.ApiConfig;
import org.processframework.gateway.common.route.RouteInterceptor;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author apple
 */
@Slf4j
public class RouteInterceptorUtil {

    public static void runPreRoute(Object requestContext, ApiParam param, Consumer<RouteInterceptorContext> saveContext) {
        DefaultRouteInterceptorContext defaultRouteInterceptorContext = new DefaultRouteInterceptorContext();
        saveContext.accept(defaultRouteInterceptorContext);
        defaultRouteInterceptorContext.setBeginTimeMillis(System.currentTimeMillis());
        defaultRouteInterceptorContext.setRequestContext(requestContext);
        defaultRouteInterceptorContext.setApiParam(param);
        getRouteInterceptors().forEach(routeInterceptor -> {
            if (routeInterceptor.match(defaultRouteInterceptorContext)) {
                routeInterceptor.preRoute(defaultRouteInterceptorContext);
            }
        });
    }

    public static void runAfterRoute(RouteInterceptorContext routeInterceptorContext) {
        if (routeInterceptorContext == null) {
            return;
        }
        try {
            getRouteInterceptors().forEach(routeInterceptor -> {
                if (routeInterceptor.match(routeInterceptorContext)) {
                    routeInterceptor.afterRoute(routeInterceptorContext);
                }
            });
        } catch (Exception e) {
            log.error("执行路由拦截器异常, apiParam:{}", routeInterceptorContext.getApiParam().toJSONString());
        }
    }

    public static List<RouteInterceptor> getRouteInterceptors() {
        return ApiConfig.getInstance().getRouteInterceptors();
    }

    public static void addInterceptors(Collection<RouteInterceptor> interceptors) {
        List<RouteInterceptor> routeInterceptors = getRouteInterceptors();
        routeInterceptors.addAll(interceptors);
        routeInterceptors.sort(Comparator.comparing(RouteInterceptor::getOrder));
    }
}
