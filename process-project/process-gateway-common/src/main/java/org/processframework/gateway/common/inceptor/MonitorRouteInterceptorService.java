//package org.processframework.gateway.common.inceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.processframework.gateway.common.ApiParam;
//import org.processframework.gateway.common.context.RouteInterceptorContext;
//import org.processframework.gateway.common.core.MonitorDTO;
//import org.processframework.gateway.common.core.MonitorData;
//import org.processframework.gateway.common.manage.MonitorManager;
//import org.processframework.gateway.common.properties.ProcessGatewayProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author open
// */
//@Service
//@Slf4j
//public class MonitorRouteInterceptorService {
//
//    @Resource
//    private ProcessGatewayProperties gatewayProperties;
//
//    @Autowired
//    DbMonitorInfoManager dbMonitorInfoManager;
//
//    @Autowired
//    MonitorManager monitorManager;
//
//    /**
//     * 记录接口调用流量，最大时间，最小时间，总时长，平均时长，调用次数，成功次数，失败次数.
//     * 需要考虑并发情况。
//     */
//    public synchronized void storeRequestInfo(RouteInterceptorContext context) {
//        ApiParam apiParam = context.getApiParam();
//        ServiceInstance serviceInstance = context.getServiceInstance();
//        String routeId = apiParam.getRouteId();
//        int spendTime = (int)(context.getFinishTimeMillis() - context.getBeginTimeMillis());
//        // 这步操作是线程安全的，底层调用了ConcurrentHashMap.computeIfAbsent
//        String key = getMonitorKey(routeId, serviceInstance.getInstanceId());
//        MonitorData monitorData = monitorManager.getMonitorInfo(key, (k) -> this.createMonitorInfo(apiParam, serviceInstance));
//        monitorData.storeMaxTime(spendTime);
//        monitorData.storeMinTime(spendTime);
//        monitorData.getTotalRequestCount().incrementAndGet();
//        monitorData.getTotalTime().addAndGet(spendTime);
//        if (context.isSuccessRequest()) {
//            monitorData.getSuccessCount().incrementAndGet();
//        } else {
//            monitorData.getErrorCount().incrementAndGet();
//            String errorMsg = context.getServiceErrorMsg();
//            monitorData.addErrorMsg(errorMsg, context.getResponseStatus());
//        }
//    }
//
//    private String getMonitorKey(String routeId, String instanceId) {
//        return routeId + instanceId;
//    }
//
//    /**
//     * 刷新到数据库
//     */
//    private synchronized void flushDb() {
//        Map<String, MonitorData> monitorData = monitorManager.getMonitorData();
//        if (monitorData.isEmpty()) {
//            return;
//        }
//        LocalDateTime checkTime = LocalDateTime.now();
//        List<String> tobeRemoveKeys = new ArrayList<>();
//        List<MonitorDTO> tobeSaveBatch = new ArrayList<>(monitorData.size());
//        monitorData.forEach((key, value) -> {
//            LocalDateTime flushTime = value.getFlushTime();
//            if (flushTime.isEqual(checkTime) || flushTime.isBefore(checkTime)) {
//                log.debug("刷新监控数据到数据库, MonitorData:{}", value);
//                tobeRemoveKeys.add(key);
//                MonitorDTO monitorDTO = getMonitorDTO(value);
//                tobeSaveBatch.add(monitorDTO);
//            }
//        });
//        dbMonitorInfoManager.saveMonitorInfoBatch(tobeSaveBatch);
//
//        for (String key : tobeRemoveKeys) {
//            monitorData.remove(key);
//        }
//    }
//
//    private MonitorDTO getMonitorDTO(MonitorData monitorData) {
//        MonitorDTO monitorDTO = new MonitorDTO();
//        monitorDTO.setRouteId(monitorData.getRouteId());
//        monitorDTO.setName(monitorData.getName());
//        monitorDTO.setVersion(monitorData.getVersion());
//        monitorDTO.setServiceId(monitorData.getServiceId());
//        monitorDTO.setInstanceId(monitorData.getInstanceId());
//        monitorDTO.setMaxTime(monitorData.getMaxTime());
//        monitorDTO.setMinTime(monitorData.getMinTime());
//        monitorDTO.setTotalTime(monitorData.getTotalTime().longValue());
//        monitorDTO.setTotalRequestCount(monitorData.getTotalRequestCount().longValue());
//        monitorDTO.setSuccessCount(monitorData.getSuccessCount().longValue());
//        monitorDTO.setErrorCount(monitorData.getErrorCount().longValue());
//        monitorDTO.setErrorMsgList(monitorData.getMonitorErrorMsgMap().values());
//        return monitorDTO;
//    }
//
//    private MonitorData createMonitorInfo(ApiParam apiParam, ServiceInstance serviceInstance) {
//        MonitorData monitorData = new MonitorData();
//        monitorData.setRouteId(apiParam.getRouteId());
//        monitorData.setName(apiParam.fetchName());
//        monitorData.setVersion(apiParam.fetchVersion());
//        monitorData.setServiceId(apiParam.fetchServiceId());
//        monitorData.setInstanceId(serviceInstance.getInstanceId());
//        monitorData.setTotalTime(new AtomicInteger());
//        monitorData.setMaxTime(0);
//        monitorData.setMinTime(0);
//        monitorData.setSuccessCount(new AtomicInteger());
//        monitorData.setTotalRequestCount(new AtomicInteger());
//        monitorData.setErrorCount(new AtomicInteger());
//        monitorData.setFlushTime(getFlushTime());
//        monitorData.setMonitorErrorMsgMap(new LRUCache<>(monitorErrorCapacity));
//        return monitorData;
//    }
//
//    private LocalDateTime getFlushTime() {
//        return LocalDateTime.now()
//                .plusSeconds(flushPeriodSeconds);
//    }
//
//
//    @PostConstruct
//    public void after() {
//        // 每隔schedulePeriodSeconds秒执行一次
//        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new MyNamedThreadFactory("monitorSchedule"));
//        // 延迟执行，随机5~14秒
//        int delay = 5 +  new Random().nextInt(10);
//        scheduledThreadPoolExecutor.scheduleWithFixedDelay(this::flushDb, delay, schedulePeriodSeconds, TimeUnit.SECONDS);
//
//    }
//}
