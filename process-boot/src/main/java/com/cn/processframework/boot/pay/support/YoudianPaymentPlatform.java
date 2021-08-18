package com.cn.processframework.boot.pay.support;

import com.cn.processframework.boot.pay.merchant.PaymentPlatform;
import com.cn.processframework.pay.HttpConfigStorage;
import com.cn.processframework.pay.PayConfigStorage;
import com.cn.processframework.pay.PayService;
import com.cn.processframework.pay.TransactionType;
import com.cn.processframework.pay.support.wxyoudian.WxYouDianPayConfigStorage;
import com.cn.processframework.pay.support.wxyoudian.WxYouDianPayService;
import com.cn.processframework.pay.support.wxyoudian.YoudianTransactionType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author apple
 * @desc 友店支付平台
 * @since 1.0 23:44
 */
@Configuration(YoudianPaymentPlatform.platformName)
@ConditionalOnMissingBean(YoudianPaymentPlatform.class)
//@ConditionalOnClass(name = {"com.cn.reddog.pay.wx.youdian.api.WxYouDianPayConfigStorage"})
@ConditionalOnClass(value = WxYouDianPayConfigStorage.class )
public class YoudianPaymentPlatform implements PaymentPlatform {

    public static final String platformName = "youdianPay";


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
        if (payConfigStorage instanceof WxYouDianPayConfigStorage) {
            return new WxYouDianPayService((WxYouDianPayConfigStorage) payConfigStorage);
        }
        WxYouDianPayConfigStorage configStorage = new WxYouDianPayConfigStorage();
        configStorage.setKeyPrivate(payConfigStorage.getKeyPrivate());
        configStorage.setKeyPublic(payConfigStorage.getKeyPublic());
        configStorage.setSignType(payConfigStorage.getSignType());
        configStorage.setPayType(payConfigStorage.getPayType().toString());
//        configStorage.setMsgType(payConfigStorage.getMsgType());
        configStorage.setSeller(payConfigStorage.getSeller());
        configStorage.setInputCharset(payConfigStorage.getInputCharset());
        configStorage.setTest(payConfigStorage.isTest());
        return new WxYouDianPayService(configStorage);
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
        return YoudianTransactionType.valueOf(name);
    }


}

