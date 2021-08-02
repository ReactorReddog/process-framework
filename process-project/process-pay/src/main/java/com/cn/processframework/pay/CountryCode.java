package com.cn.processframework.pay;

/**
 * @author apple
 * @desc 国家代码
 * @since 1.0 17:06
 */
public interface CountryCode {
    /**
     * 获取国家代码
     * @return 国家代码
     */
    String getCode();

    /**
     * 获取国家名称
     * @return 国家名称
     */
    String getName();
}
