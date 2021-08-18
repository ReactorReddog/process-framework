package com.cn.processframework.boot.autoconfigure.pay;

import com.cn.processframework.boot.pay.MerchantPayServiceManager;
import com.cn.processframework.boot.pay.PayServiceConfigurer;
import com.cn.processframework.boot.pay.PayServiceManager;
import com.cn.processframework.boot.pay.DefalutPayMessageConfigurer;
import com.cn.processframework.boot.pay.MerchantDetailsServiceConfigurer;
import com.cn.processframework.boot.pay.PayMessageConfigurer;
import com.cn.processframework.boot.pay.merchant.MerchantDetailsService;
import com.cn.processframework.boot.pay.merchant.PaymentPlatform;
import com.cn.processframework.boot.pay.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @author apple
 * @desc 支付转载配置
 * @since 1.0 22:38
 */
@Configuration
@ImportAutoConfiguration({AliPaymentPlatform.class, FuiouPaymentPlatform.class, PayoneerPaymentPlatform.class, PaypalPaymentPlatform.class, UnionPaymentPlatform.class, WxPaymentPlatform.class, YoudianPaymentPlatform.class})
public class PayAutoConfiguration {
    @Autowired
    @Order
    public void loadPaymentPlatforms(List<PaymentPlatform> platforms){
        for (PaymentPlatform platform : platforms){
            PaymentPlatforms.loadPaymentPlatform(platform);
        }
    }


    @Bean
    @ConditionalOnMissingBean(MerchantDetailsServiceConfigurer.class)
    public MerchantDetailsServiceConfigurer detailsServiceConfigurer(){
        return new MerchantDetailsServiceConfigurer();
    }


    @Bean
    @ConditionalOnMissingBean(PayServiceManager.class)
    public MerchantPayServiceManager payServiceManager(){
        return new MerchantPayServiceManager();
    }


    @Bean
    @ConditionalOnMissingBean(PayMessageConfigurer.class)
    public PayMessageConfigurer messageHandlerConfigurer(){
        return new DefalutPayMessageConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean(MerchantDetailsService.class)
    @ConditionalOnBean(PayServiceConfigurer.class)
    protected MerchantDetailsService configure(PayServiceConfigurer configurer, MerchantDetailsServiceConfigurer merchantDetails, PayMessageConfigurer payMessageConfigurer) {
        configurer.configure(merchantDetails);
        configurer.configure(payMessageConfigurer);
        MerchantDetailsService detailsService = merchantDetails.getBuilder().build();
        return detailsService;
    }

}
