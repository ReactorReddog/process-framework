package org.processframework.gateway.common.context;

import lombok.Data;
import org.processframework.gateway.common.ApiParam;
import org.springframework.cloud.client.ServiceInstance;

/**
 * @author apple
 * @desc 默认拦截器参数
 * @since 1.0.0.RELEASE
 */
@Data
public class DefaultRouteInterceptorContext implements RouteInterceptorContext {
    /** 请求参数 */
    private ApiParam apiParam;
    /** 错误信息 */
    private String serviceErrorMsg;
    /** 微服务返回状态 */
    private int responseStatus;
    /** 开始时间 */
    private long beginTimeMillis;
    /** 结束时间 */
    private long finishTimeMillis;
    /** 请求上下文 */
    private Object requestContext;
    /** 微服务返回结果 */
    private String serviceResult;
    /** 请求包大小 */
    private long requestDataSize;
    /** 返回内容大小 */
    private long responseDataSize;
    /** 负载均衡选中的微服务 */
    private ServiceInstance serviceInstance;
}
