package com.cn.processframework.boot.pay.support;

import com.cn.processframework.boot.pay.merchant.PaymentPlatform;
import com.cn.processframework.pay.HttpConfigStorage;
import com.cn.processframework.pay.PayConfigStorage;
import com.cn.processframework.pay.PayService;
import com.cn.processframework.pay.TransactionType;
import com.cn.processframework.pay.support.alipay.AliPayConfigStorage;
import com.cn.processframework.pay.support.alipay.AliPayService;
import com.cn.processframework.pay.support.alipay.AliTransactionType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author apple
 * @desc 支付宝支付平台
 * @since 1.0 23:40
 */
@Configuration(AliPaymentPlatform.platformName)
@ConditionalOnMissingBean(AliPaymentPlatform.class)
@ConditionalOnClass(name = {"com.cn.reddog.pay.alipay.api.AliPayConfigStorage"})
public class AliPaymentPlatform implements PaymentPlatform {

    public static final String platformName = "aliPay";



    /**
     * 获取商户平台
     *
     * @return 商户平台
     */
    @Override
    public String getPlatform() {
        return platformName;
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param payConfigStorage 支付配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(PayConfigStorage payConfigStorage) {
        if (payConfigStorage instanceof AliPayConfigStorage) {
            return new AliPayService((AliPayConfigStorage) payConfigStorage);
        }
        AliPayConfigStorage configStorage = new AliPayConfigStorage();
        configStorage.setInputCharset(payConfigStorage.getInputCharset());
        configStorage.setAppid(payConfigStorage.getAppid());
        configStorage.setPid(payConfigStorage.getPid());
        configStorage.setAttach(payConfigStorage.getAttach());
        configStorage.setSeller(payConfigStorage.getSeller());
        configStorage.setKeyPrivate(payConfigStorage.getKeyPrivate());
        configStorage.setKeyPublic(payConfigStorage.getKeyPublic());
        configStorage.setNotifyUrl(payConfigStorage.getNotifyUrl());
        configStorage.setReturnUrl(payConfigStorage.getReturnUrl());
//        configStorage.setMsgType(payConfigStorage.getMsgType());
        configStorage.setPayType(payConfigStorage.getPayType());
        configStorage.setTest(payConfigStorage.isTest());
        configStorage.setSignType(payConfigStorage.getSignType());
        return new AliPayService(configStorage);
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param payConfigStorage  支付配置
     * @param httpConfigStorage 网络配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(PayConfigStorage payConfigStorage, HttpConfigStorage httpConfigStorage) {
        PayService payService = getPayService(payConfigStorage);
        payService.setRequestTemplateConfigStorage(httpConfigStorage);
        return payService;
    }

    @Override
    public TransactionType getTransactionType(String name) {
        return AliTransactionType.valueOf(name);
    }


}
