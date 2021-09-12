package org.processframework.gateway.common;

import lombok.Data;

/**
 * @author apple
 * @desc 记录路由错误
 * @since 1.0.0.RELEASE
 */
@Data
public class RouteErrorCount {
    /**
     * 路由id
     */
    private String routeId;
    /**
     * 错误数量
     */
    private Integer count;
}
