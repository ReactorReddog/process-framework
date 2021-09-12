package org.processframework.gateway.common.route;

import org.springframework.context.ApplicationEvent;

/**
 * @author apple
 * @desc 发现新服务，更新路由信息
 * @since 1.0.0.RELEASE
 */
public interface RegistryListener {

    void onEvent(ApplicationEvent applicationEvent);

}
