package org.processframework.gateway.common.route;

import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.context.RouteInterceptorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

/**
 * 演示拦截器
 *
 * @author open
 */
@Slf4j
@Component
public class ProcessRouteInterceptor implements RouteInterceptor {

    @Override
    public void preRoute(RouteInterceptorContext context) {
        ApiParam apiParam = context.getApiParam();
        ServerWebExchange exchange = (ServerWebExchange) context.getRequestContext();
        URI uri = exchange.getRequest().getURI();
        log.info("请求URL:{}, 请求接口:{}, request_id:{}, app_id:{}, ip:{}",
                uri,
                apiParam.fetchNameVersion(),
                apiParam.fetchRequestId(),
                apiParam.fetchAppKey(),
                apiParam.fetchIp()
        );
    }

    @Override
    public void afterRoute(RouteInterceptorContext context) {
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
