package org.processframework.gateway.common.route;

import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.ServerWebExchangeUtil;
import org.processframework.gateway.common.constant.GatewayConstant;
import org.processframework.gateway.common.core.ForwardInfo;
import org.processframework.gateway.common.core.TargetRoute;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author apple
 */
public class GatewayForwardChooser extends BaseForwardChooser<ServerWebExchange> {

    @Override
    public ApiParam getApiParam(ServerWebExchange exchange) {
        return ServerWebExchangeUtil.getApiParam(exchange);
    }

    @Override
    public ForwardInfo getForwardInfo(ServerWebExchange exchange) {
        // 如果有异常，直接跳转到异常处理
        if (ServerWebExchangeUtil.getThrowable(exchange) != null) {
            return ForwardInfo.getErrorForwardInfo();
        }
        ForwardInfo forwardInfo = super.getForwardInfo(exchange);
        TargetRoute targetRoute = forwardInfo.getTargetRoute();
        exchange.getAttributes().put(GatewayConstant.CACHE_ROUTE_INFO, targetRoute.getRouteDefinition());
        return forwardInfo;
    }
}
