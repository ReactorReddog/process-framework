//package org.processframework.gateway.common.route;
//
//import lombok.extern.slf4j.Slf4j;
//import org.processframework.gateway.common.context.RouteInterceptorContext;
//import org.processframework.gateway.common.sync.ProcessAsyncConfigurer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * 用于收集监控数据
// *
// * @author open
// */
//@Component
//@Slf4j
//public class MonitorRouteInterceptor implements RouteInterceptor {
//
//    @Autowired
//    ProcessAsyncConfigurer processAsyncConfigurer;
//
//    @Autowired
//    MonitorRouteInterceptorService monitorRouteInterceptorService;
//
//    @Override
//    public void preRoute(RouteInterceptorContext context) {
//    }
//
//    @Override
//    public void afterRoute(RouteInterceptorContext context) {
//        processAsyncConfigurer.getAsyncExecutor().execute(()-> {
//            monitorRouteInterceptorService.storeRequestInfo(context);
//        });
//    }
//
//    @Override
//    public int getOrder() {
//        return -1000;
//    }
//
//
//}
