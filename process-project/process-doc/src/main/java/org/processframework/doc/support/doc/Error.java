package org.processframework.doc.support.doc;

/**
 * @author apple
 * @desc error
 * 定义错误返回信息
 *  code 返回码
 *  msg 返回码描述
 *  sub_code 明细返回码
 *  sub_msg 明细返回码描述
 *  solution 解决方案
 *
 */
public interface Error {
    /**
     * 状态码
     * @return 返回状态码
     */
    String getCode();

    /**
     * 获取错误信息
     * @return 返回错误信息
     */
    String getMsg();

    /**
     * 明细返回码
     * @return 明细返回码
     */
    String getSub_code();

    /**
     * 明细返回码描述
     * @return 明细返回码描述
     */
    String getSub_msg();

    /**
     * 解决方案
     * @return 解决方案
     */
    String getSolution();
}
