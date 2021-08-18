package com.cn.processframework.boot.pay;

/**
 * @author apple
 * @desc 支付服务配置，用于支付服务相关的配置，暂时主要为商户相关的配置，后期在进行添加别的配置
 * @since 1.0 23:34
 */
public interface PayServiceConfigurer {
    /**
     * 商户配置
     * @param configurer 商户配置
     */
    void configure(MerchantDetailsServiceConfigurer configurer);
    /**
     * 商户配置
     * @param configurer 支付消息配置
     */
    void configure(PayMessageConfigurer configurer) ;
}

