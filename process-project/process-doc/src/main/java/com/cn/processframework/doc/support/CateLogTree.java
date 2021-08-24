package com.cn.processframework.doc.support;

import lombok.Data;

import java.util.List;

/**
 * @author apple
 * @package com.cn.processframework.doc.support
 * @desc <p>方法目录相关信息</p>
 * @since
 */
@Data
public class CateLogTree {
    /**
     * id
     */
    private Long id;
    /**
     * 描述
     */
    private String name;
    /**
     * 选中状态
     */
    private Boolean selected;
    /**
     * 标签
     */
    private String tag;
    /**
     * 树形名称
     */
    private String treeName;
    /**
     * 方法
     */
    private List<CateLog> cateLogList;
}
