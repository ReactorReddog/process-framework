package org.processframework.gateway.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.*;
import org.processframework.gateway.common.context.DefaultRouteInterceptorContext;
import org.processframework.gateway.common.context.EnvironmentKeys;
import org.processframework.gateway.common.core.ForwardInfo;
import org.processframework.gateway.common.properties.ProcessGatewayProperties;
import org.processframework.gateway.common.route.GatewayForwardChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.processframework.gateway.common.constant.GatewayConstant.CACHE_ROUTE_INTERCEPTOR_CONTEXT;

/**
 * @author apple
 * @desc 入口
 * @since 1.0.0.RELEASE
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class IndexFilter implements WebFilter {
    @Autowired
    private ServerCodecConfigurer codecConfigurer;
    @Resource
    private ProcessGatewayProperties gatewayProperties;

    @Autowired
    private Validator validator;

    @Autowired
    private GatewayForwardChooser gatewayForwardChooser;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        // 路径是否在白名单中，直接放行
        if (this.isPathInWhiteList(path)) {
            return chain.filter(exchange);
        }
        // 如果是restful请求，直接转发
        // see：com.cn.reddog.open.api.gateway.gateway.controller.RestfulController
        if (gatewayProperties.isEnableRestFul()&& path.startsWith(EnvironmentKeys.PROCESS_RESTFUL_PATH.getValue())) {
            return chain.filter(exchange);
        }
        if (Objects.equals(path, gatewayProperties.getGatewayIndexPath()) || "".equals(path)) {
            if (request.getMethod() == HttpMethod.POST) {
                ServerRequest serverRequest = ServerWebExchangeUtil.createReadBodyRequest(exchange, codecConfigurer);
                // 读取请求体中的内容
                Mono<?> modifiedBody = serverRequest.bodyToMono(byte[].class)
                        .switchIfEmpty(Mono.just("".getBytes()))
                        .flatMap(data -> {
                            // 构建ApiParam
                            ApiParam apiParam = ServerWebExchangeUtil.getApiParam(exchange, data);
                            // 签名验证
                            doValidate(exchange, apiParam);
                            return Mono.just(data);
                        });
                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, (Class)byte[].class);
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());

                // the new content type will be computed by bodyInserter
                // and then set in the request decorator
                headers.remove(HttpHeaders.CONTENT_LENGTH);

                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(
                        exchange, headers);
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            ForwardInfo forwardInfo = gatewayForwardChooser.getForwardInfo(exchange);
                            ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
                            ServerWebExchange newExchange = exchange.mutate().request(decorator).build();
                            ServerWebExchange forwardExchange = ServerWebExchangeUtil.getForwardExchange(newExchange, forwardInfo);
                            return chain.filter(forwardExchange);
                        }));

            } else {
                URI uri = exchange.getRequest().getURI();
                // 原始参数
                String originalQuery = uri.getRawQuery();
                // 构建ApiParam
                ApiParam apiParam = ServerWebExchangeUtil.getApiParamByQuery(exchange, originalQuery);
                // 签名验证
                doValidate(exchange, apiParam);

                ForwardInfo forwardInfo = gatewayForwardChooser.getForwardInfo(exchange);
                ServerWebExchange forwardExchange = ServerWebExchangeUtil.getForwardExchange(exchange, forwardInfo);
                return chain.filter(forwardExchange);
            }
        } else {
            return ServerWebExchangeUtil.forwardUnknown(exchange, chain);
        }
    }

    private boolean isPathInWhiteList(String path) {
        for (String whitePath : gatewayProperties.getPathWhiteList()) {
            if (path.startsWith(whitePath)) {
                return true;
            }
        }
        return false;
    }

    private void doValidate(ServerWebExchange exchange, ApiParam apiParam) {
        try {
            validator.validate(apiParam);
            this.afterValidate(exchange, apiParam);
        } catch (ApiException e) {
            log.error("验证失败, errorMsg:{}，url:{}, ip:{}, params:{}",
                    e.getMessage(),
                    exchange.getRequest().getURI().toString(),
                    apiParam.fetchIp(), apiParam.toJSONString());
            ServerWebExchangeUtil.setThrowable(exchange, e);
        }
    }

    private void afterValidate(ServerWebExchange exchange, ApiParam param) {
        RouteInterceptorUtil.runPreRoute(exchange, param, context ->  {
            DefaultRouteInterceptorContext defaultRouteInterceptorContext = (DefaultRouteInterceptorContext) context;
            defaultRouteInterceptorContext.setRequestDataSize(exchange.getRequest().getHeaders().getContentLength());
            exchange.getAttributes().put(CACHE_ROUTE_INTERCEPTOR_CONTEXT, context);
        });
    }

    private ServerHttpRequestDecorator decorate(
            ServerWebExchange exchange
            , HttpHeaders headers
            , CachedBodyOutputMessage outputMessage
    ) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }
}
