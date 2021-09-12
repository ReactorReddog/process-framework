package org.processframework.gateway.common.api;

import org.processframework.gateway.common.core.ApiConfig;
import org.processframework.gateway.common.excutor.ApiResult;
import org.processframework.gateway.common.manage.MonitorManager;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 提供监控数据
 *
 * @author apple
 */
public abstract class BaseMonitorController<T> extends BaseController<T> {

    @GetMapping("/process/getMonitorData")
    public ApiResult doExecute(T request) {
        MonitorManager monitorManager = ApiConfig.getInstance().getMonitorManager();
        return execute(request, monitorManager::getMonitorData);
    }

}
