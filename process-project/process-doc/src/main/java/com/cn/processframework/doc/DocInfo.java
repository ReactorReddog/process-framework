package com.cn.processframework.doc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @package com.cn.processframework.doc
 * @desc <p>文档信息</p>
 * @since
 */
@Data
public class DocInfo implements Serializable {
    private String title;
    @JSONField(serialize = false)
    private String serviceId;
    private List<DocModule> docModuleList;
}
