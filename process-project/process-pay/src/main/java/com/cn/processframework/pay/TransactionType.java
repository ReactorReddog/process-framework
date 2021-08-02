package com.cn.processframework.pay;

/**
 * @author apple
 * @desc 基本交易类型
 * @since 1.0 14:21
 */
public interface TransactionType {
    /**
     * 获取交易类型
     * @return 交易类型
     */
    String getType();

    /**
     * 获取接口
     * @return 接口
     */
    String getMethod();
}
