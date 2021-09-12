package org.processframework.gateway.common.manage.initalizer;

/**
 * @author apple
 */
public interface ChannelMsgProcessor {
    default void process(ChannelMsg channelMsg) {
    }
}