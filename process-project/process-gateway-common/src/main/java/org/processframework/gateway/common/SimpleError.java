package org.processframework.gateway.common;

import lombok.Data;

/**
 * @author apple
 * @desc error简单实现模式
 * @since 1.0.0.RELEASE
 */
@Data
public class SimpleError implements Error{

    private String code;
    private String msg;
    private String sub_code;
    private String sub_msg;
    private String solution;

    public SimpleError(String code,String msg,String sub_code,String sub_msg,String solution){
        this.code = code;
        this.msg = msg;
        this.sub_code = sub_code;
        this.sub_msg = sub_msg;
        this.solution = solution;
    }
}
