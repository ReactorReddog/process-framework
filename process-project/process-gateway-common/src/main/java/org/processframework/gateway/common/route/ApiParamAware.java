package org.processframework.gateway.common.route;

import org.processframework.gateway.common.ApiParam;

/**
 * @author apple
 * @desc apiParam
 * @since 1.0.0.RELEASE
 * @param <T>
 */
public interface ApiParamAware<T> {
    /**
     * 获取apiApram
     * @param t 领域对象
     * @return apiParam
     */
    ApiParam getApiParam(T t);
}
