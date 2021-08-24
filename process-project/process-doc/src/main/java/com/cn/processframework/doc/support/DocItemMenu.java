package com.cn.processframework.doc.support;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @package com.cn.processframework.doc.support
 * @desc <p>文档标题及其菜单</p>
 * @since 1.0.0.RELEASE
 */
@Data
public class DocItemMenu implements Serializable {
    /**
     * 下标
     */
    private String docIndex;
    /**
     * id
     */
    private Long id;
    /**
     * 目录名称
     */
    private String name;
    /**
     * 提示标签
     */
    private String tag;
    /**
     * 次级数据展示
     */
    private List<TreeCategorie> treeCategories;
}
