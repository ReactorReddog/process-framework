package com.cn.processframework.doc.api;

import com.cn.processframework.doc.support.DocItemMenu;
import com.cn.processframework.doc.support.doc.DocInfoDate;

/**
 * @author apple
 * @package com.cn.processframework.doc
 * @desc <p>定义标题或目录、文档详情信息获取接口</p>
 * @since 1.0.0.RELEASE
 */
public interface CatelogConfigApi {
    /**
     * 获取开放平台目录
     * @param scopeId 授权组
     * @param treeId 树形id
     * @param docId 文档id
     * @param docType 文档类型
     * @param tag 标签呢
     * @return DocItemMenu
     */
    DocItemMenu getApiCateLogItemMenu(Long scopeId,Long treeId,Long docId,Long docType,String tag);

    /**
     * 获取文档信息介绍
     * @param treeId 树id 非必传
     * @param docType 文档类型
     * @param isEn 是否是英文
     * @param docId 文档id
     * @return DocInfoDate
     */
    DocInfoDate getDocumentDocInfoDate(Long treeId,Long docType,boolean isEn,Long docId);
}
