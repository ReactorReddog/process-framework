package org.processframework.bridge.route;

import com.alibaba.nacos.api.naming.pojo.Instance;
import org.processframework.gateway.common.core.ServiceHolder;

/**
 * @author apple
 * @desc nacos 服务获取实例
 * @since 1.0.0.RELEASE
 */
public class NacosServiceHolder extends ServiceHolder {
    private final Instance instance;
    public NacosServiceHolder(String serviceId, long lastUpdatedTimestamp,Instance instance) {
        super(serviceId, lastUpdatedTimestamp);
        this.instance = instance;
    }

    /**
     * 获取存活实例
     * @return
     */
    public Instance getInstance(){
        return this.instance;
    }
}
