package org.processframework.gateway.common.manage.loadbalancer.builder;

import org.processframework.gateway.common.ApiParam;

/**
 * @author apple
 */
public class AppIdGrayUserBuilder implements GrayUserBuilder {

    @Override
    public String buildGrayUserKey(ApiParam apiParam) {
        return apiParam.fetchAppKey();
    }

    @Override
    public int order() {
        return 0;
    }
}
