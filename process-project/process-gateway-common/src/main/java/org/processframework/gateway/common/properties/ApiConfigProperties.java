package org.processframework.gateway.common.properties;

import lombok.Data;
import org.processframework.gateway.common.constant.GatewayConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apple
 * @desc 网关配置
 * @since 1.0.0.RELEASE
 */
@ConfigurationProperties(prefix = GatewayConstant.GATEWAY_PROPERTIES_PREFIX)
@Data
public class ApiConfigProperties {
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
    /**
     * i18n模板
     */
    private List<String> i18nModules = new ArrayList<>();
    /**
     * 忽略验证，设置true，则所有接口不会进行签名校验
     */
    private boolean ignoreValidate;
    /**
     * 是否对结果进行合并。<br>
     * 默认情况下是否合并结果由微服务端决定，一旦指定该值，则由该值决定，不管微服务端如何设置。
     */
    private Boolean mergeResult;

    /**
     * 请求超时时间，默认5分钟，即允许在5分钟内重复请求
     */
    private int timeoutSeconds;
    /**
     * 是否开启限流功能
     */
    private boolean openLimit;

    /**
     * 显示返回sign
     */
    private boolean showReturnSign;

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
        timeoutSeconds = 300;
        openLimit = true;
        showReturnSign = true;
    }
}
