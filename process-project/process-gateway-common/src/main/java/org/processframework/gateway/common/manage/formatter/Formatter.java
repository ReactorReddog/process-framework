package org.processframework.gateway.common.manage.formatter;

import java.util.Map;

/**
 * @author apple
 * @desc 参数格式化
 * @since 1.0.0.RELEASE
 */
public interface Formatter<T extends Map<String, Object>> {

    /**
     * 参数格式化，即动态修改请求参数
     *
     * @param requestParams 原始请求参数，在此基础上追加或修改参数
     */
    void format(T requestParams);
}
