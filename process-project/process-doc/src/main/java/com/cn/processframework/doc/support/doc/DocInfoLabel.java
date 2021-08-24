package com.cn.processframework.doc.support.doc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author apple
 * @desc 当前文档标签 及其地址连接
 */
@Data
public class DocInfoLabel implements Serializable {
    /**
     * 跳转地址 可直接使用#
     */
    private String clickUrl;
    /**
     * 展示名称
     */
    private String displayName;
    /**
     *
     */
    private String key;
    /**
     * 弹窗提示
     */
    private String tips;
}
