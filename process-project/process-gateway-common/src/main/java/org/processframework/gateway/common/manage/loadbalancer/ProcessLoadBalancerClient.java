package org.processframework.gateway.common.manage.loadbalancer;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.LoadBalanceUtil;
import org.processframework.gateway.common.ServerWebExchangeUtil;
import org.processframework.gateway.common.route.ServerChooserContext;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.*;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * @author apple
 * @desc 重写负载均衡处理。
 * 默认使用的是RibbonLoadBalancerClient类，详见org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration#loadBalancerClient()
 */
public class ProcessLoadBalancerClient extends RibbonLoadBalancerClient implements ServerChooserContext<ServerWebExchange> {

    private final SpringClientFactory clientFactory;
    private GatewayLoadBalanceServerChooser loadBalanceServerChooser;

    public ProcessLoadBalancerClient(SpringClientFactory clientFactory) {
        super(clientFactory);
        this.clientFactory = clientFactory;
        this.loadBalanceServerChooser = new GatewayLoadBalanceServerChooser(clientFactory);
    }

    /**
     * New: Select a server using a 'key'.
     */
    @Override
    public ServiceInstance choose(String serviceId, Object hint) {
        return loadBalanceServerChooser.choose(
                serviceId
                , (ServerWebExchange) hint
                , this.getLoadBalancer(serviceId)
                , () -> super.choose(serviceId, hint)
                , (servers) -> getRibbonServer(serviceId, servers)
        );
    }

    @Override
    public ApiParam getApiParam(ServerWebExchange exchange) {
        return ServerWebExchangeUtil.getApiParam(exchange);
    }

    @Override
    public String getHost(ServerWebExchange exchange) {
        return exchange.getRequest().getURI().getHost();
    }

    private RibbonServer getRibbonServer(String serviceId, List<Server> servers) {
        Server server = LoadBalanceUtil.chooseByRoundRobin(serviceId, servers);
        if (server == null) {
            return null;
        }
        return new RibbonServer(
                serviceId
                , server
                , isSecure(server, serviceId)
                , serverIntrospector(serviceId).getMetadata(server)
        );
    }

    private ServerIntrospector serverIntrospector(String serviceId) {
        ServerIntrospector serverIntrospector = this.clientFactory.getInstance(serviceId,
                ServerIntrospector.class);
        if (serverIntrospector == null) {
            serverIntrospector = new DefaultServerIntrospector();
        }
        return serverIntrospector;
    }

    private boolean isSecure(Server server, String serviceId) {
        IClientConfig config = this.clientFactory.getClientConfig(serviceId);
        ServerIntrospector serverIntrospector = serverIntrospector(serviceId);
        return RibbonUtils.isSecure(config, serverIntrospector, server);
    }
}
