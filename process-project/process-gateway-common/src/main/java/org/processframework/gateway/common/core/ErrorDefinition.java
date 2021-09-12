package org.processframework.gateway.common.core;

import lombok.Data;

/**
 * @author apple
 * @desc 错误实例
 * @since 1.0.0.RELEASE
 */
@Data
public class ErrorDefinition {
    /**
     * 接口名
     */
    private String name;
    /**
     * 版本号
     */
    private String version;
    /**
     * 服务名
     */
    private String serviceId;
    /**
     * 错误内容
     */
    private String errorMsg;

}
