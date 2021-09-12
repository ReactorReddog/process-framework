package org.processframework.gateway.common.configurate.gateway;

import org.processframework.gateway.common.configurate.AbstractConfiguration;
import org.processframework.gateway.common.core.ApiConfig;
import org.processframework.gateway.common.filter.GatewayModifyResponseGatewayFilter;
import org.processframework.gateway.common.filter.IndexFilter;
import org.processframework.gateway.common.filter.LimitFilter;
import org.processframework.gateway.common.filter.ParameterFormatterFilter;
import org.processframework.gateway.common.handler.CommonGatewayExceptionHandler;
import org.processframework.gateway.common.manage.loadbalancer.ProcessLoadBalancerClient;
import org.processframework.gateway.common.filter.ProcessLoadBalancerClientFilter;
import org.processframework.gateway.common.route.GatewayForwardChooser;
import org.processframework.gateway.common.route.GatewayRouteCache;
import org.processframework.gateway.common.route.GatewayRouteRepository;
import org.processframework.gateway.common.route.RouteRepositoryContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * @author apple
 * @desc 基础配置
 * @since 1.0.0.RELEASE
 */
public class BaseGatewayConfiguration extends AbstractConfiguration {

    public BaseGatewayConfiguration() {
        ApiConfig.getInstance().setUseGateway(true);
    }

    @Bean
    public IndexFilter indexFilter() {
        return new IndexFilter();
    }

    /**
     * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
     *
     * @param viewResolversProvider viewResolversProvider
     * @param serverCodecConfigurer serverCodecConfigurer
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler sopErrorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                                ServerCodecConfigurer serverCodecConfigurer) {

        CommonGatewayExceptionHandler jsonExceptionHandler = new CommonGatewayExceptionHandler();
        jsonExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return jsonExceptionHandler;
    }

    /**
     * 处理返回结果
     */
    @Bean
    GatewayModifyResponseGatewayFilter gatewayModifyResponseGatewayFilter() {
        return new GatewayModifyResponseGatewayFilter();
    }

    @Bean
    ParameterFormatterFilter parameterFormatterFilter() {
        return new ParameterFormatterFilter();
    }

    @Bean
    LimitFilter limitFilter() {
        return new LimitFilter();
    }

    @Bean
    GatewayRouteCache gatewayRouteCache(GatewayRouteRepository gatewayRouteRepository) {
        return new GatewayRouteCache(gatewayRouteRepository);
    }

    @Bean
    GatewayRouteRepository gatewayRouteRepository() {
        GatewayRouteRepository gatewayRouteRepository = new GatewayRouteRepository();
        RouteRepositoryContext.setRouteRepository(gatewayRouteRepository);
        return gatewayRouteRepository;
    }

    @Bean
    GatewayForwardChooser gatewayForwardChooser() {
        return new GatewayForwardChooser();
    }

    /**
     * 扩展默认的负载均衡选择，默认使用的是RibbonLoadBalancerClient
     * @param clientFactory
     * @return
     */
    @Bean
    LoadBalancerClient loadBalancerClient(SpringClientFactory clientFactory) {
        return new ProcessLoadBalancerClient(clientFactory);
    }

    /**
     * 扩展默认的负载均衡过滤器，默认是LoadBalancerClientFilter
     * @param sopLoadBalancerClient SopLoadBalancerClient
     * @param loadBalancerProperties loadBalancerProperties
     * @return
     */
    @Bean
    LoadBalancerClientFilter loadBalancerClientFilter(LoadBalancerClient sopLoadBalancerClient, LoadBalancerProperties loadBalancerProperties) {
        return new ProcessLoadBalancerClientFilter(sopLoadBalancerClient, loadBalancerProperties);
    }
}
