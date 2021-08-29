package org.processframework.doc.support.doc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author apple
 * @desc 如何获取api接口 api权限组
 * @since 1.0.0.RELEASE
 */
@Data
public class ScopeApiInfo implements Serializable {
    /**
     * api接口名称
     */
    private String name;
    /**
     * api地址
     */
    private String url;
    /**
     * 描述信息
     */
    private String descprition;
}
