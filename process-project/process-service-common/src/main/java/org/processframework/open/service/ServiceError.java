package org.processframework.open.service;

/**
 * @author apple
 * @desc error
 * @since 1.0.0.RELEASE
 */
public interface ServiceError {
    /**
     * sub_code（明细返回码）
     * @return sub_code（明细返回码）
     */
    String getSub_code();

    /**
     * sub_msg（明细返回码描述）
     * @return sub_msg（明细返回码描述）
     */
    String getSub_msg();
}
