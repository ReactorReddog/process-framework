package org.processframework.open.service;

import lombok.Getter;

/**
 * @author apple
 * @desc 错误对象
 */
@Getter
public class ServiceErrorMeta {
    private String modulePrefix;
    private String subCode;
    public ServiceErrorMeta(String modulePrefix, String subCode) {
        this.modulePrefix = modulePrefix;
        this.subCode = subCode;
    }

    public ServiceError getError() {
        return ServiceErrorFactory.getError(this, ServiceContext.getCurrentContext().getLocale());
    }


}
