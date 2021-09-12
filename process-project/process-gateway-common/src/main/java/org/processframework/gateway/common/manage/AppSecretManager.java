package org.processframework.gateway.common.manage;

import java.util.Map;

/**
 * @author apple
 * @desc 负责秘钥管理
 */
public interface AppSecretManager {
    
    /**
     * 初始化秘钥数据
     * @param appSecretStore
     */
    void addAppSecret(Map<String, String> appSecretStore);

    /**
     * 获取应用程序的密钥
     *
     * @param appKey
     * @return 返回秘钥
     */
    String getSecret(String appKey);

    /**
     * 是否是合法的appKey
     *
     * @param appKey
     * @return 返回appKey
     */
    boolean isValidAppKey(String appKey);
}