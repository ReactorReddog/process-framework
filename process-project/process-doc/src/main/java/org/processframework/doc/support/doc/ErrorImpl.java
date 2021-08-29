package org.processframework.doc.support.doc;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author apple
 * @desc 实现
 */
@Data
@AllArgsConstructor
public class ErrorImpl implements Error{
    /**
     * 错误码
     */
    private String code;
    /**
     * 错误描述
     */
    private String msg;
    /**
     * 错误详情码
     */
    private String sub_code;
    /**
     * 错误详情描述
     */
    private String sub_msg;
    /**
     * 解决方案
     */
    private String solution;

}
