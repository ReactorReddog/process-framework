package com.cn.processframework.doc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author apple
 * @package com.cn.processframework.doc
 * @desc <p>返回码处理</p>
 * @since
 */
@Data
public class BizCode implements Serializable {
    /**
     * 返回编码
     */
    private String code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 错误建议
     */
    private String solution;
}
