package org.processframework.gateway.common.manage.initalizer;

/**
 * @author apple
 */
public interface BeanInitializer extends ChannelMsgProcessor {
    /**
     * 执行加载操作
     */
    void load();
}
