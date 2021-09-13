package org.processframework.open.service;

/**
 * @author apple
 * @desc error状态
 * @since 1.0.0.RELEASE
 */
public enum ServiceErrorEnum {
    /** 系统繁忙 */
    ISP_UNKNOW_ERROR("isp.unknown-error"),
    /** 参数错误 */
    ISV_PARAM_ERROR("isv.invalid-parameter"),
    /** 通用错误 */
    ISV_COMMON_ERROR("isv.common-error"),
    ;
    private ServiceErrorMeta errorMeta;

    ServiceErrorEnum(String subCode) {
        this.errorMeta = new ServiceErrorMeta("isp.error_", subCode);
    }

    public ServiceErrorMeta getErrorMeta() {
        return errorMeta;
    }
}
