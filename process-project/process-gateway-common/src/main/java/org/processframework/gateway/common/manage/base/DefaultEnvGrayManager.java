package org.processframework.gateway.common.manage.base;

import com.google.common.collect.Maps;
import org.processframework.gateway.common.manage.EnvGrayManager;
import org.processframework.gateway.common.manage.loadbalancer.ServiceGrayConfig;

import java.util.Map;

/**
 * @author apple
 * @desc 灰度发布默认实现
 * @since 1.0.0.RELEASE
 */
public class DefaultEnvGrayManager implements EnvGrayManager {

    /**
     * key：serviceId，服务对应的灰度配置
     */
    private final Map<String, ServiceGrayConfig> serviceGrayConfigMap = Maps.newConcurrentMap();

    /**
     * key:instanceId value:serviceId
     */
    private final Map<String, String> instanceIdServiceIdMap = Maps.newConcurrentMap();

    @Override
    public void saveServiceGrayConfig(ServiceGrayConfig serviceGrayConfig) {
        serviceGrayConfigMap.put(serviceGrayConfig.getServiceId(), serviceGrayConfig);
    }

    @Override
    public boolean containsKey(String serviceId, Object userKey) {
        if (serviceId == null || userKey == null) {
            return false;
        }
        ServiceGrayConfig grayConfig = this.getGrayConfig(serviceId);
        return grayConfig != null && grayConfig.containsKey(userKey);
    }

    @Override
    public String getVersion(String serviceId, String nameVersion) {
        if (serviceId == null || nameVersion == null) {
            return null;
        }
        boolean opened = instanceIdServiceIdMap.containsValue(serviceId);
        // 没有开启灰度
        if (!opened) {
            return null;
        }
        ServiceGrayConfig grayConfig = this.getGrayConfig(serviceId);
        return grayConfig != null ? grayConfig.getVersion(nameVersion) : null;
    }

    private ServiceGrayConfig getGrayConfig(String serviceId) {
        if (serviceId == null || !instanceIdServiceIdMap.containsValue(serviceId)) {
            return null;
        }
        return serviceGrayConfigMap.get(serviceId);
    }

    @Override
    public void openGray(String instanceId, String serviceId) {
        instanceIdServiceIdMap.putIfAbsent(instanceId, serviceId);
    }

    @Override
    public void closeGray(String instanceId) {
        instanceIdServiceIdMap.remove(instanceId);
    }

    @Override
    public void load() {

    }
}
