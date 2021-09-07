package org.processframework.gateway.common;

/**
 * @author apple
 * @desc 定义错误返回信息
 * code 返回码
 * msg 返回码描述
 * sub_code 明细返回码
 * sub_msg 明细返回码描述
 * solutions 解决方案
 * @since 1.0.0.RELEASE
 */
public interface Error {
    /**
     * 返回码
     * @return String
     */
    String getCode();

    /**
     * 返回码描述
     * @return String
     */
    String getMsg();

    /**
     * 明细返回码
     * @return String
     */
    String getSub_code();

    /**
     * 明细返回码描述
     * @return String
     */
    String getSub_msg();

    /**
     * 解决方案
     * @return String
     */
    String getSolution();
}
