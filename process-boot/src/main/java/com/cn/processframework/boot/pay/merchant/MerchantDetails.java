package com.cn.processframework.boot.pay.merchant;

import com.cn.processframework.pay.PayConfigStorage;

import java.io.Serializable;

/**
 * @author apple
 * @desc 商户信息列表
 * @since 1.0 23:45
 */
public interface MerchantDetails extends PayConfigStorage, Serializable {


    /**
     * 获取支付商户详细信息id
     *
     * @return 支付商户详细信息id
     */
    String getDetailsId();
}
