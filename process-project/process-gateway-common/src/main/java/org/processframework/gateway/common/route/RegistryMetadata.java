package org.processframework.gateway.common.route;

import org.processframework.gateway.common.constant.GatewayConstant;

import java.util.Map;

/**
 * @author apple
 * @desc 服务注册扩展信息
 * @since 1.0.0.RELEASE
 */
public interface RegistryMetadata {
    /**
     * 获取上下文地址
     * @param metadata 扩展信息
     * @return
     */
    default String getContextPath(Map<String, String> metadata) {
        return metadata.getOrDefault(GatewayConstant.METADATA_SERVER_CONTEXT_PATH
                , metadata.getOrDefault(GatewayConstant.METADATA_SERVER_CONTEXT_PATH_COMPATIBILITY, ""));
    }

}
