package org.processframework.open.support;

import lombok.Data;

import java.io.Serializable;

/**
 * @author apple
 * @package com.cn.processframework.doc.support
 * @desc <p>目录接口展示</p>
 * @since 1.0.0.RELEASE
 */
@Data
public class CateLog implements Serializable {
    private Long id;
    /**
     * 文档id
     */
    private Long docId;
    /**
     * 文档类型
     */
    private Long docType;
    /**
     * 文档链接地址
     */
    private String docUrl;
    /**
     * 文档uuid
     */
    private String docUuid;
    /**
     * 文档方法描述
     */
    private String name;
    /**
     * 父级id
     */
    private Long pid;
    /**
     * 选中状态
     */
    private Boolean selected;
    /**
     * 名称展示
     */
    private String subName;
    /**
     * 提示窗展示
     */
    private String tips;
}
