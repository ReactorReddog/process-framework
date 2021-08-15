package com.cn.processframework.doc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.gradle.internal.impldep.org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author apple
 * @package com.cn.processframework.doc
 * @desc <p>参数 类型 是否必填 最大长度 实例值</p>
 * @since
 */
@Data
public class DocParameter implements Serializable {
    private static final AtomicInteger gen = new AtomicInteger();
    /**
     * 唯一标识
     */
    private Integer id = gen.incrementAndGet();
    /**
     *
     */
    private String module;
    /**
     * 参数名
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 最大长度
     */
    private String maxLength = "-";
    /**
     * 是否必填
     */
    private boolean required;
    /**
     * 描述
     */
    private String description;
    /**
     * 实例值
     */
    private String example = "";

    @JSONField(name = "x-example")
    private String x_example = "";
    /**
     * 子数据
     */
    private List<DocParameter> refs;

    public String getParamExample() {
        return StringUtils.isBlank(example) ? x_example : example;
    }
}
