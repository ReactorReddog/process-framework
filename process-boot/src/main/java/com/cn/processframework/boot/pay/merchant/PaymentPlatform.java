package com.cn.processframework.boot.pay.merchant;

import com.cn.processframework.pay.BasePayType;
import com.cn.processframework.pay.HttpConfigStorage;
import com.cn.processframework.pay.PayConfigStorage;
import com.cn.processframework.pay.PayService;

/**
 * @author apple
 * @desc 支付平台
 * @since 1.0 23:46
 */
public interface PaymentPlatform<S extends PayService> extends BasePayType {

    /**
     * 获取商户平台
     * @return 商户平台
     */
    String getPlatform();

    /**
     * 获取支付平台对应的支付服务
     * @param payConfigStorage 支付配置
     * @return 支付服务
     */
    S getPayService(PayConfigStorage payConfigStorage);
    /**
     * 获取支付平台对应的支付服务
     * @param payConfigStorage 支付配置
     * @param httpConfigStorage 网络配置
     * @return 支付服务
     */
    S getPayService(PayConfigStorage payConfigStorage, HttpConfigStorage httpConfigStorage);

}
