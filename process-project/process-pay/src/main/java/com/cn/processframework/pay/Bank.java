package com.cn.processframework.pay;

/**
 * @author apple
 * @desc 银行代码及名称
 * @since 1.0 14:19
 */
public interface Bank {
    /**
     * 获取银行的代码
     * @return 银行的代码
     */
    String getCode();

    /**
     * 获取银行的名称
     * @return 银行的名称
     */
    String getName();
}
