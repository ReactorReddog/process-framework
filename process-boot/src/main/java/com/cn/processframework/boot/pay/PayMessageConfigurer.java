package com.cn.processframework.boot.pay;

import com.cn.processframework.boot.pay.merchant.PaymentPlatform;
import com.cn.processframework.pay.PayMessage;
import com.cn.processframework.pay.PayMessageHandler;
import com.cn.processframework.pay.PayService;
import com.cn.processframework.pay.inteceptor.PayMessageInterceptor;

import java.util.List;

/**
 * @author apple
 * @desc 支付回调通知配置
 * @since 1.0 23:30
 */
public interface PayMessageConfigurer {

    /**
     * 添加处理器
     *
     * @param platform 商户平台，渠道
     * @param handler  处理器
     */
    void addHandler(PaymentPlatform platform, PayMessageHandler handler);

    /**
     * 获取处理器
     *
     * @param platform 商户平台，渠道
     * @return 处理器
     */
    PayMessageHandler<PayMessage, PayService> getHandler(PaymentPlatform platform);

    /**
     * 添加拦截器
     *
     * @param platform    商户平台，渠道
     * @param interceptor 拦截器
     */
    void addInterceptor(PaymentPlatform platform, PayMessageInterceptor interceptor);

    /**
     * 获取拦截器
     *
     * @param platform 商户平台，渠道
     * @return 拦截器
     */
    List<PayMessageInterceptor<PayMessage, PayService>> getInterceptor(PaymentPlatform platform);

}

