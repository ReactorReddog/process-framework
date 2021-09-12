package org.processframework.gateway.common.manage.loadbalancer.builder;

import org.processframework.gateway.common.ApiParam;

/**
 * @author apple
 * @desc 灰度用户
 * @since 1.0.0.RELEASE
 */
public interface GrayUserBuilder {

    /**
     * 获取灰度用户key
     *
     * @param apiParam apiParam
     * @return 返回用户key
     */
    String buildGrayUserKey(ApiParam apiParam);

    /**
     * 优先级，数字小优先
     *
     * @return 返回数字
     */
    int order();
}
