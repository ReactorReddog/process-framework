package com.cn.processframework.pay.handler;

import com.cn.processframework.pay.exception.PayErrorException;

/**
 * @author apple 
 * @package com.cn.processframework.pay
 * @desc <p>异常处理器</p>
 * @since
 */
@FunctionalInterface
public interface PayErrorExceptionHandler {
    /**
     * 异常统一处理器
     * @param e 支付异常
     */
    void handle(PayErrorException e);
}
