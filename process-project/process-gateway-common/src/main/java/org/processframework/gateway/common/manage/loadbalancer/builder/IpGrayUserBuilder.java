package org.processframework.gateway.common.manage.loadbalancer.builder;

import org.processframework.gateway.common.ApiParam;

/**
 * @author apple
 */
public class IpGrayUserBuilder implements GrayUserBuilder {
    
    @Override
    public String buildGrayUserKey(ApiParam apiParam) {
        return apiParam.fetchIp();
    }

    @Override
    public int order() {
        return 1;
    }
}
