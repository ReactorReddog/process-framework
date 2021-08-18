package com.cn.processframework.boot.pay.support;

import com.cn.processframework.boot.pay.merchant.PaymentPlatform;
import com.cn.processframework.pay.HttpConfigStorage;
import com.cn.processframework.pay.PayConfigStorage;
import com.cn.processframework.pay.PayService;
import com.cn.processframework.pay.TransactionType;
import com.cn.processframework.pay.support.payoneer.PayoneerConfigStorage;
import com.cn.processframework.pay.support.payoneer.PayoneerPayService;
import com.cn.processframework.pay.support.payoneer.PayoneerTransactionType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author apple
 * @desc P卡(派安盈)支付平台
 * @since 1.0 23:42
 */
@Configuration(PayoneerPaymentPlatform.platformName)
@ConditionalOnMissingBean(PayoneerPaymentPlatform.class)
@ConditionalOnClass(name = {"com.cn.reddog.pay.payoneer.api.PayoneerConfigStorage"})
public class PayoneerPaymentPlatform implements PaymentPlatform {

    public static final String platformName = "payoneerPay";

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
        if (payConfigStorage instanceof PayoneerConfigStorage) {
            return new PayoneerPayService((PayoneerConfigStorage) payConfigStorage);
        }
        PayoneerConfigStorage configStorage = new PayoneerConfigStorage();
        configStorage.setProgramId(payConfigStorage.getPid());
        configStorage.setUserName(payConfigStorage.getSeller());
        configStorage.setApiPassword(payConfigStorage.getKeyPrivate());
        configStorage.setNotifyUrl(payConfigStorage.getNotifyUrl());
        configStorage.setReturnUrl(payConfigStorage.getReturnUrl());
        configStorage.setSignType(payConfigStorage.getSignType());
        configStorage.setPayType(payConfigStorage.getPayType());
//        configStorage.setMsgType(payConfigStorage.getMsgType());
        configStorage.setInputCharset(payConfigStorage.getInputCharset());
        configStorage.setTest(payConfigStorage.isTest());
        return new PayoneerPayService(configStorage);
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
        return PayoneerTransactionType.valueOf(name);
    }


}
