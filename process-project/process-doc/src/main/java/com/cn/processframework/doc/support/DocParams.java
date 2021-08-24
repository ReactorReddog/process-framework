package com.cn.processframework.doc.support;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @desc 参数
 * @since 1.0.0.RELEASE
 */
@Data
public class DocParams implements Serializable {
    /**
     * 当前参数默认值
     */
    private String defaultValue;
    /**
     * 当前参数实例值
     */
    private String demoValue;
    /**
     * 当前参数的描述
     */
    private String description;
    /**
     * 优先级 越低越高
     */
    private Long fieldsReadLevel;
    /**
     *
     */
    private String fileExt;
    /**
     * 最大长度
     */
    private Long maxLength;
    /**
     * 最大列表长度
     */
    private Long maxListSize;
    /**
     * 最大值
     */
    private String maxValue;
    /**
     * 最小值
     */
    private String minValue;
    /**
     * 名字
     */
    private String name;

    private Boolean needShortAuthority;

    private String paramOrder;
    /**
     * 父级id
     */
    private String parentId;
    /**
     * 是否必填
     */
    private boolean required;
    /**
     * 子参数
     */
    private List<DocParams> subParams;
    /**
     * 类型
     */
    private String type;
    /**
     * 写入顺序
     */
    private Long writeLevel;
    /**
     * 版本
     */
    private String version;
}
