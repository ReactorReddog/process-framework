package org.processframework.gateway.common.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.ServerWebExchangeUtil;
import org.processframework.gateway.common.SpringContext;
import org.processframework.gateway.common.WebUtil;
import org.processframework.gateway.common.constant.NacosConfigs;
import org.processframework.gateway.common.core.GatewayPushDTO;
import org.processframework.gateway.common.manage.*;
import org.processframework.gateway.common.manage.initalizer.ChannelMsgProcessor;
import org.processframework.gateway.common.properties.ApiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author apple
 */
@Slf4j
@RestController
public class ConfigChannelController {

    private static final Map<String, Class<? extends ChannelMsgProcessor>> processorMap = new HashMap<>(16);

    static {
        processorMap.put(NacosConfigs.GROUP_CHANNEL + NacosConfigs.DATA_ID_GRAY, EnvGrayManager.class);
        processorMap.put(NacosConfigs.GROUP_CHANNEL + NacosConfigs.DATA_ID_IP_BLACKLIST, IPBlacklistManager.class);
        processorMap.put(NacosConfigs.GROUP_CHANNEL + NacosConfigs.DATA_ID_ISV, IsvManager.class);
        processorMap.put(NacosConfigs.GROUP_CHANNEL + NacosConfigs.DATA_ID_ROUTE_PERMISSION, IsvRoutePermissionManager.class);
        processorMap.put(NacosConfigs.GROUP_CHANNEL + NacosConfigs.DATA_ID_LIMIT_CONFIG, LimitConfigManager.class);
        processorMap.put(NacosConfigs.GROUP_CHANNEL + NacosConfigs.DATA_ID_ROUTE_CONFIG, RouteConfigManager.class);
    }
    @Resource
    private ApiConfigProperties apiConfigProperties;

    @Autowired
    private ServerCodecConfigurer codecConfigurer;

    @PostMapping("/reddog/configChannelMsg")
    public Mono<String> configChannel(ServerWebExchange exchange) {
        ServerRequest serverRequest = ServerWebExchangeUtil.createReadBodyRequest(exchange, codecConfigurer);
        // 读取请求体中的内容
        return serverRequest.bodyToMono(String.class)
                .flatMap(requestJson -> {
                    String sign = exchange.getRequest().getHeaders().getFirst("sign");
                    try {
                        // 签名验证
                        WebUtil.checkResponseBody(requestJson, sign, apiConfigProperties.getSecret());
                    } catch (Exception e) {
                        log.error("configChannelMsg错误", e);
                        return Mono.just(e.getMessage());
                    }
                    GatewayPushDTO gatewayPushDTO = JSON.parseObject(requestJson, GatewayPushDTO.class);
                    ChannelMsgProcessor channelMsgProcessor = getChannelMsgProcessor(gatewayPushDTO);
                    channelMsgProcessor.process(gatewayPushDTO.getChannelMsg());
                    return Mono.just("ok");
                });
    }

    private ChannelMsgProcessor getChannelMsgProcessor(GatewayPushDTO gatewayPushDTO) {
        String key = gatewayPushDTO.getGroupId() + gatewayPushDTO.getDataId();
        Class<? extends ChannelMsgProcessor> aClass = processorMap.get(key);
        return SpringContext.getBean(aClass);
    }

}
