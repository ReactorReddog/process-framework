package org.processframework.gateway.common.api;

import org.processframework.gateway.common.ApiException;
import org.processframework.gateway.common.ErrorEnum;
import org.processframework.gateway.common.ServerWebExchangeUtil;
import org.processframework.gateway.common.constant.GatewayConstant;
import org.processframework.gateway.common.core.ApiContext;
import org.processframework.gateway.common.excutor.ResultExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author apple
 */
@RestController
public class GatewayController {

    /**
     * 处理签名错误返回
     *
     * @param exchange exchange
     * @return 返回最终结果
     */
    @RequestMapping(GatewayConstant.VALIDATE_ERROR_PATH)
    public Mono<String> validateError(ServerWebExchange exchange) {
        Throwable throwable = ServerWebExchangeUtil.getThrowable(exchange);
        // 合并微服务传递过来的结果，变成最终结果
        ResultExecutor<ServerWebExchange, String> resultExecutor = ApiContext.getApiConfig().getGatewayResultExecutor();
        String gatewayResult = resultExecutor.buildErrorResult(exchange, throwable);
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return Mono.just(gatewayResult);
    }

    @RequestMapping(GatewayConstant.UNKNOWN_PATH)
    public Mono<String> unknown(ServerWebExchange exchange) {
        ApiException exception = ErrorEnum.ISV_INVALID_METHOD.getErrorMeta().getException();
        ResultExecutor<ServerWebExchange, String> resultExecutor = ApiContext.getApiConfig().getGatewayResultExecutor();
        String gatewayResult = resultExecutor.buildErrorResult(exchange, exception);
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return Mono.just(gatewayResult);
    }

}
