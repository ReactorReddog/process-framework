package com.cn.processframework.pay.exception;

/**
 * @author apple
 * @package com.cn.processframework.pay.exception
 * @desc <p>支付异常</p>
 * @since
 */
public class PayErrorException extends RuntimeException {
    private PayError error;

    public PayErrorException(PayError error) {
        super(error.getString());
        this.error = error;
    }


    public PayError getPayError() {
        return error;
    }
}
