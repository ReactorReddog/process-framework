package org.processframework.gateway.common.route;

import org.processframework.gateway.common.core.ForwardInfo;

/**
 * 转发选择
 * @author apple
 */
public interface ForwardChooser<T> {

    /**
     * 返回转发信息
     * @param t 上下文
     * @return 返回转发信息
     */
    ForwardInfo getForwardInfo(T t);

}
