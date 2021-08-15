package com.cn.processframework.doc;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @package com.cn.processframework.doc
 * @desc <p></p>
 * @since
 */
@Data
public class DocModule implements Serializable {
    private String module;
    private List<DocItem> docItems;
    private int order;
}
