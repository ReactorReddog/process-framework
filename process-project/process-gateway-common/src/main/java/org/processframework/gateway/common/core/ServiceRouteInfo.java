package org.processframework.gateway.common.core;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author apple
 */
@Data
public class ServiceRouteInfo {

    /**
     * 服务名称，对应spring.application.name
     */
    private String serviceId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * 更新时间
     */
    private LocalDateTime updateTime = LocalDateTime.now();
    /**
     * 描述
     */
    private String description;
    /**
     * 路由实例列表
     */
    private List<RouteDefinition> routeDefinitionList;

    public String fetchServiceIdLowerCase() {
        return serviceId.toLowerCase();
    }
}