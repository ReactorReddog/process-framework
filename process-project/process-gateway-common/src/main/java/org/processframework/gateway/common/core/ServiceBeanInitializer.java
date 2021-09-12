package org.processframework.gateway.common.core;

import org.processframework.gateway.common.manage.initalizer.ChannelMsgProcessor;

/**
 * @author apple
 * @desc 服务bean实例化
 * @since 1.0.0.RELEASE
 */
public interface ServiceBeanInitializer extends ChannelMsgProcessor {
    /**
     * 加载
     * @param serviceId 服务id
     */
    void load(String serviceId);
}
