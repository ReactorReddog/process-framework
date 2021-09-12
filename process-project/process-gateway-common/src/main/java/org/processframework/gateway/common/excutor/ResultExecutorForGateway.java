package org.processframework.gateway.common.excutor;

import org.springframework.web.server.ServerWebExchange;

/**
 * @author apple
 * @desc 网关消息合并
 * @since 1.0.0.RELEASE
 */
public interface ResultExecutorForGateway extends ResultExecutor<ServerWebExchange,String> {
}
