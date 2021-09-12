package org.processframework.gateway.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.ServerWebExchangeUtil;
import org.processframework.gateway.common.constant.GatewayConstant;
import org.processframework.gateway.common.core.ApiContext;
import org.processframework.gateway.common.excutor.ResultExecutor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author apple
 * @desc 网关统一异常处理,参考{@link org.springframework.web.server.AbstractErrorWebExceptionHandler}修改
 * @see org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
 * @see <a href="https://www.jianshu.com/p/71dbb116b2a4">webflux解析消息体</a>
 * @since 1.0.0.RELEASE
 */
@Slf4j
public class CommonGatewayExceptionHandler implements ErrorWebExceptionHandler {
    /**
     * messageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
    /**
     * messageWrite
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
    /**
     * ciewResokvers
     */
    private List<ViewResolver>viewResolvers = Collections.emptyList();

    /**
     * 处理类
     * @param exchange 请求或回馈等统一参数
     * @param ex 异常处理
     * @return void
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ResultExecutor<ServerWebExchange, String> resultExecutor = ApiContext.getApiConfig().getGatewayResultExecutor();
        String errorResult = resultExecutor.buildErrorResult(exchange, ex);
        ApiParam apiParam = ServerWebExchangeUtil.getApiParam(exchange);
        // 错误记录
        Object routeDefinition = exchange.getAttribute(GatewayConstant.CACHE_ROUTE_INFO);
        log.error("网关报错，参数:{}, 错误信息:{}, 路由信息:{}", apiParam, ex.getMessage(), routeDefinition, ex);
        // 参考AbstractErrorWebExceptionHandler
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), (serverRequest) -> this.renderErrorResponse(errorResult)).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageReaders messageReaders
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param viewResolvers viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageWriters messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }


    /**
     * 参考DefaultErrorWebExceptionHandler
     *
     * @param result 返回结果
     * @return 返回mono
     */
    protected Mono<ServerResponse> renderErrorResponse(String result) {
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(result));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param exchange exchange
     * @param response response
     * @return 返回Mono
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return CommonGatewayExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return CommonGatewayExceptionHandler.this.viewResolvers;
        }

    }
}
