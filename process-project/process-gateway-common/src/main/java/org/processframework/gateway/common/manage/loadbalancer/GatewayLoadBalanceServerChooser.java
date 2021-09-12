package org.processframework.gateway.common.manage.loadbalancer;


import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.ServerWebExchangeUtil;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author apple
 * @desc 网关处理器负载均衡选择器
 * @since 1.0.0.RELEASE
 */
public class GatewayLoadBalanceServerChooser extends LoadBalanceServerChooser<ServerWebExchange, ServiceInstance> {

    public GatewayLoadBalanceServerChooser(SpringClientFactory clientFactory) {
        this.setClientFactory(clientFactory);
    }

    @Override
    public String getHost(ServerWebExchange exchange) {
        return exchange.getRequest().getURI().getHost();
    }

    @Override
    public ApiParam getApiParam(ServerWebExchange exchange) {
        return ServerWebExchangeUtil.getApiParam(exchange);
    }

}
