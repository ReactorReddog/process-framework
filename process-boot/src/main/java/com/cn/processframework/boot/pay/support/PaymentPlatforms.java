package com.cn.processframework.boot.pay.support;

import com.cn.processframework.boot.pay.merchant.PaymentPlatform;
import com.cn.processframework.pay.TransactionType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author apple
 * @desc 支付平台集
 * @since 1.0 23:41
 */
public final class PaymentPlatforms {

    private static final Map<String, PaymentPlatform> PAYMENT_PLATFORMS = new HashMap<String, PaymentPlatform>();


    /**
     * 加载支付平台
     *
     * @param platform 支付平台
     */
    public static void loadPaymentPlatform(PaymentPlatform platform) {
        PAYMENT_PLATFORMS.put(platform.getPlatform(), platform);
    }

    /**
     * 获取所有的支付平台
     *
     * @return 所有的支付平台
     */
    public static Map<String, PaymentPlatform> getPaymentPlatforms() {
        return PAYMENT_PLATFORMS;
    }

    /**
     * 通过支付平台名称与交易类型(支付类型)名称或者交易类型
     *
     * @param platformName        支付平台名称
     * @param transactionTypeName 交易类型名称
     * @return 交易类型
     */
    public static TransactionType getTransactionType(String platformName, String transactionTypeName) {
        PaymentPlatform platform = getPaymentPlatform(platformName);
        return platform.getTransactionType(transactionTypeName);
    }

    /**
     * 通过支付平台名称与交易类型(支付类型)名称或者交易类型
     *
     * @param platformName 支付平台名称
     * @return 交易类型
     */
    public static PaymentPlatform getPaymentPlatform(String platformName) {
        PaymentPlatform platform = PAYMENT_PLATFORMS.get(platformName);
        return platform;
    }


}
