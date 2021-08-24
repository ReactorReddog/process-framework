package com.cn.processframework.doc.support;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author apple
 * @desc 实现
 */
@Data
@AllArgsConstructor
public class ErrorImpl implements Error{
    private String code;
    private String msg;
    private String sub_code;
    private String sub_msg;
    private String solution;

}
