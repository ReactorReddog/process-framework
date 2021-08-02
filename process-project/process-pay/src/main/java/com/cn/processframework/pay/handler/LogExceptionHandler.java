package com.cn.processframework.pay.handler;

import com.cn.processframework.pay.exception.PayErrorException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author apple
 * @desc LogExceptionHandler 日志处理器
 * @since 1.0 17:24
 */
@Slf4j
public class LogExceptionHandler implements PayErrorExceptionHandler {


    @Override
    public void handle(PayErrorException e) {

        log.error("Error happens", e);

    }
}
