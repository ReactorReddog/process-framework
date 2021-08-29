package org.processframework.doc;

import org.processframework.doc.api.CatelogConfigApi;
import org.processframework.doc.support.DocItemMenu;
import org.processframework.doc.support.doc.DocInfoDate;

/**
 * @author apple
 * @package com.cn.processframework.doc
 * @desc <p>定义标题或目录、文档详情信息获取接口 默认实现</p>
 * @since 1.0.0.RELEASE
 */
public class DefaultCatelogConfigApi implements CatelogConfigApi {
    /**
     * 获取开放平台目录
     * @param scopeId 授权组
     * @param treeId 树形id
     * @param docId 文档id
     * @param docType 文档类型
     * @param tag 标签呢
     * @return
     */
    @Override
    public DocItemMenu getApiCateLogItemMenu(Long scopeId, Long treeId, Long docId, Long docType, String tag) {
        return null;
    }

    /**
     * 获取文档信息介绍
     * @param treeId 树id 非必传
     * @param docType 文档类型
     * @param isEn 是否是英文
     * @param docId 文档id
     * @return
     */
    @Override
    public DocInfoDate getDocumentDocInfoDate(Long treeId, Long docType, boolean isEn, Long docId) {
        return null;
    }
}
