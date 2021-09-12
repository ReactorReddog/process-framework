package org.processframework.gateway.common.properties;

import lombok.Data;
import org.processframework.gateway.common.constant.GatewayConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

@ConfigurationProperties(prefix = GatewayConstant.GATEWAY_PROPERTIES_PREFIX)
@Data
public class ProcessGatewayProperties {
    /**
     * 请求地址
     */
    private String prefix;
    /**
     * 开启restful请求，默认开启
     */
    private boolean enableRestFul;
    /**
     * 网关地址
     */
    private String gatewayIndexPath;
    /**
     * secret
     */
    private String secret;
    /**
     * 错误信息容量 默认200
     */
    private Long storeErrorCapacity;
    /**
     * 监控线程
     */
    private Integer monitorRouteInterceptor;

    /**
     * 监控数据刷新到数据库时间间隔
     */
    private Integer flushPeriodSeconds;
    /**
     * 监控定时任务每n秒，执行一次
     */
    private Integer schedulePeriodSeconds;
    /**
     * 路径白名单
     */
    private List<String> pathWhiteList;
    /**
     * 监控错误容量
     */
    private Integer monitorErrorCapacity;
    {
        enableRestFul = true;
        prefix = "/rest";
        storeErrorCapacity = 200L;
        monitorRouteInterceptor = 5;
        gatewayIndexPath="/";
        flushPeriodSeconds = 30;
        schedulePeriodSeconds = 30;
        monitorErrorCapacity = 200;
        pathWhiteList = List.of("/actuator");
    }
}
