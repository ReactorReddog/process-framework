package org.processframework.gateway.common.route;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.processframework.gateway.common.core.InstanceDefinition;
import org.processframework.gateway.common.core.ServiceRouteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.processframework.gateway.common.constant.GatewayConstant.METADATA_PROCESS_ROUTES_PATH;
import static org.processframework.gateway.common.constant.GatewayConstant.PROCESS_ROUTES_PATH;

/**
 * @author apple
 */
@Slf4j
public class ServiceRouteListener extends BaseServiceListener {

    @Autowired
    private GatewayRouteCache gatewayRouteCache;

    @Autowired
    private RoutesProcessor routesProcessor;

    @Override
    public void onRemoveService(String serviceId) {
        log.info("服务下线，删除路由配置，serviceId: {}", serviceId);
        gatewayRouteCache.remove(serviceId);
        routesProcessor.removeAllRoutes(serviceId);
    }

    @Override
    public void onAddInstance(InstanceDefinition instance) {
        String serviceName = instance.getServiceId();
        String url = getRouteRequestUrl(instance);
        log.info("拉取路由配置，serviceId: {}, url: {}", serviceName, url);
        ResponseEntity<String> responseEntity = getRestTemplate().getForEntity(url, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            ServiceRouteInfo serviceRouteInfo;
            try {
                serviceRouteInfo =  this.parseServiceRouteInfo(body);
            } catch (Exception e) {
                log.error("解析路由配置错误，body:{}", body, e);
                return;
            }
            gatewayRouteCache.load(serviceRouteInfo, callback -> routesProcessor.saveRoutes(serviceRouteInfo, instance));
        } else {
            log.error("拉取路由配置异常，url: {}, status: {}, body: {}", url, responseEntity.getStatusCodeValue(), responseEntity.getBody());
        }
    }

    private ServiceRouteInfo parseServiceRouteInfo(String body) {
        ServiceRouteInfo serviceRouteInfo = JSON.parseObject(body, ServiceRouteInfo.class);
        serviceRouteInfo.setServiceId(serviceRouteInfo.getServiceId().toLowerCase());
        return serviceRouteInfo;
    }

    protected HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(headers);
    }

    /**
     * 拉取路由请求url
     *
     * @param instance 服务实例
     * @return 返回最终url
     */
    private String getRouteRequestUrl(InstanceDefinition instance) {
        Map<String, String> metadata = instance.getMetadata();
        String customPath = metadata.get(METADATA_PROCESS_ROUTES_PATH);
        String homeUrl;
        String servletPath;
        // 如果metadata中指定了获取路由的url
        if (StringUtils.isNotBlank(customPath)) {
            // 自定义完整的url
            if (customPath.startsWith("http")) {
                homeUrl = customPath;
                servletPath = "";
            } else {
                homeUrl = getHomeUrl(instance);
                servletPath = customPath;
            }
        } else {
            // 默认处理
            homeUrl = getHomeUrl(instance);
            String contextPath = this.getContextPath(metadata);
            servletPath = contextPath + PROCESS_ROUTES_PATH;
        }
        if (StringUtils.isNotBlank(servletPath) && !servletPath.startsWith("/")) {
            servletPath = '/' + servletPath;
        }
        String query = buildQuery();
        return homeUrl + servletPath + query;
    }

    private static String getHomeUrl(InstanceDefinition instance) {
        return "http://" + instance.getIp() + ":" + instance.getPort();
    }
}
