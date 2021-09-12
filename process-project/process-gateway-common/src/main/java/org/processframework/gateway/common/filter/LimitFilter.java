package org.processframework.gateway.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.*;
import org.processframework.gateway.common.core.ApiConfig;
import org.processframework.gateway.common.core.ConfigLimitDto;
import org.processframework.gateway.common.core.LimitType;
import org.processframework.gateway.common.manage.LimitConfigManager;
import org.processframework.gateway.common.manage.LimitManager;
import org.processframework.gateway.common.support.Orders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.processframework.gateway.common.constant.GatewayConstant.RESTFUL_REQUEST;

/**
 * spring cloud gateway限流过滤器
 * @author apple
 */
@Slf4j
public class LimitFilter implements GlobalFilter, Ordered {

    private static final ErrorMeta LIMIT_ERROR_META = ErrorEnum.ISV_METHOD_CALL_LIMITED.getErrorMeta();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getAttribute(RESTFUL_REQUEST) != null) {
            return chain.filter(exchange);
        }
        ApiConfig apiConfig = ApiConfig.getInstance();
        // 限流功能未开启，直接返回
        if (!apiConfig.isOpenLimit()) {
            return chain.filter(exchange);
        }
        ApiParam apiParam = ServerWebExchangeUtil.getApiParam(exchange);
        if (apiParam == null) {
            return chain.filter(exchange);
        }
        ConfigLimitDto configLimitDto = this.findConfigLimitDto(apiConfig, apiParam, exchange);
        if (configLimitDto == null) {
            return chain.filter(exchange);
        }
        // 单个限流功能未开启
        if (configLimitDto.getLimitStatus() == ConfigLimitDto.LIMIT_STATUS_CLOSE) {
            return chain.filter(exchange);
        }
        Byte limitType = configLimitDto.getLimitType();
        LimitManager limitManager = ApiConfig.getInstance().getLimitManager();
        // 如果是窗口策略
        if (limitType == LimitType.LEAKY_BUCKET.getType()) {
            boolean acquire = limitManager.acquire(configLimitDto);
            // 被限流，返回错误信息
            if (!acquire) {
                throw new ApiException(LIMIT_ERROR_META);
            }
        } else if (limitType == LimitType.TOKEN_BUCKET.getType()) {
            limitManager.acquireToken(configLimitDto);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Orders.LIMIT_FILTER_ORDER;
    }

    protected ConfigLimitDto findConfigLimitDto(ApiConfig apiConfig, ApiParam apiParam, ServerWebExchange exchange) {
        LimitConfigManager limitConfigManager = apiConfig.getLimitConfigManager();

        String routeId = apiParam.fetchNameVersion();
        String appKey = apiParam.fetchAppKey();
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();

        String[] limitKeys = new String[]{
                routeId,
                appKey,
                routeId + appKey,

                ip + routeId,
                ip + appKey,
                ip + routeId + appKey,
        };

        List<ConfigLimitDto> limitConfigList = new ArrayList<>();
        for (String limitKey : limitKeys) {
            ConfigLimitDto configLimitDto = limitConfigManager.get(limitKey);
            if (configLimitDto == null) {
                continue;
            }
            limitConfigList.add(configLimitDto);
        }
        if (limitConfigList.isEmpty()) {
            return null;
        }
        limitConfigList.sort(Comparator.comparing(ConfigLimitDto::getOrderIndex));
        return limitConfigList.get(0);
    }
}
