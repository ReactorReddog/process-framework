package org.processframework.open.support;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @package com.cn.processframework.doc.support
 * @desc <p>单目录接口数据等</p>
 * @since
 */
@Data
public class TreeCategorie implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 展示优先级顺序
     */
    private Long displayOrder;
    /**
     * 文档url
     */
    private String docUrl;
    /**
     * 小标题名称-对应大标题目录
     */
    private String name;
    /**
     * 选中状态
     */
    private boolean selected;
    /**
     * 接口目录
     */
    private List<CateLogTree> cateLogTrees;
}
