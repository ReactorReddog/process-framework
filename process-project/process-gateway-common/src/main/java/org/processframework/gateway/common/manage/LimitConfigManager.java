package org.processframework.gateway.common.manage;

import org.processframework.gateway.common.core.ConfigLimitDto;
import org.processframework.gateway.common.core.ServiceBeanInitializer;

/**
 * @author apple
 * @desc 限流服务
 * @since 1.0.0.RELEASE
 */
public interface LimitConfigManager extends ServiceBeanInitializer {
    /**
     * 更新限流配置
     * @param configLimitDto 路由配置
     */
    void update(ConfigLimitDto configLimitDto);

    /**
     * 获取限流配置
     * @param limitKey 路由id
     * @return 返回ConfigLimitDto
     */
    ConfigLimitDto get(String limitKey);
}
