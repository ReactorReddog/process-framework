package org.processframework.open.service;

/**
 * @author apple
 * @desc 服务错误通知
 * @since 1.0.0.RELEASE
 */
public class ServiceException extends RuntimeException{
    private final transient ServiceError error;

    public ServiceException(String subCode, String subMsg) {
        super(subMsg);
        this.error = new ServiceErrorImpl(subCode, subMsg);
    }

    public ServiceException(String subMsg) {
        super(subMsg);
        String subCode = ServiceErrorEnum.ISV_COMMON_ERROR.getErrorMeta().getError().getSub_code();
        this.error = new ServiceErrorImpl(subCode, subMsg);
    }

    public ServiceException(ServiceError error) {
        super(error.toString());
        this.error = error;
    }

    public ServiceError getError() {
        return error;
    }
}
