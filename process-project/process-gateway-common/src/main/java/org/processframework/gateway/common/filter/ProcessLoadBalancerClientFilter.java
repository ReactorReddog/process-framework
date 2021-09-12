package org.processframework.gateway.common.filter;

import org.processframework.gateway.common.manage.loadbalancer.ProcessLoadBalancerClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static org.processframework.gateway.common.constant.GatewayConstant.TARGET_SERVICE;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author apple
 * @desc 扩展负载均衡服务器
 * @since 1.0.0.RELEASE
 */
public class ProcessLoadBalancerClientFilter extends LoadBalancerClientFilter {
    public ProcessLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        ServiceInstance serviceInstance;
        if (loadBalancer instanceof ProcessLoadBalancerClient) {
            ProcessLoadBalancerClient sopLoadBalancerClient = (ProcessLoadBalancerClient)loadBalancer;
            serviceInstance =  sopLoadBalancerClient.choose(((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost(), exchange);
        } else {
            serviceInstance = super.choose(exchange);
        }
        exchange.getAttributes().put(TARGET_SERVICE, serviceInstance);
        return serviceInstance;
    }
}
