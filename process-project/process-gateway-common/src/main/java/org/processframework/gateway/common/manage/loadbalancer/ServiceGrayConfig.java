package org.processframework.gateway.common.manage.loadbalancer;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * @author apple
 */
@Data
public class ServiceGrayConfig {

    private String serviceId;

    /**
     * 用户id
     */
    private Set<String> userKeys;

    /** 存放接口隐射关系，key:nameversion，value:newVersion */
    private Map<String, String> grayNameVersion;

    public boolean containsKey(Object userKey) {
        return userKeys.contains(String.valueOf(userKey));
    }

    public String getVersion(String name) {
        return grayNameVersion.get(name);
    }

}
