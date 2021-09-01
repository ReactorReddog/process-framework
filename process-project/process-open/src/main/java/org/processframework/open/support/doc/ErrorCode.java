package org.processframework.open.support.doc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author apple
 * @desc 错误码解释
 * @since 1.0.0.RELEASE
 */
@Data
public class ErrorCode implements Serializable {
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 解决方案
     */
    private String solution;
}
