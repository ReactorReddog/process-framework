package com.cn.processframework.doc.support;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @desc 数据展示
 * @since 1.0.0.RELEASE
 */
@Data
public class DocInfoDate implements Serializable {
    /**
     * 中文名展示
     */
    private String apiChineseName;
    /**
     * 错误信息+解决方案+对象
     */
    private Error apiErrDemoJson;
    /**
     * 错误信息+解决方案+xml方案
     */
    private String apiErrDemoXml;
    /**
     * 描述
     */
    private String description;
    /**
     * 用户权限组
     */
    private List<ScopeApiInfo> applyScopes;
    /**
     * 环境
     */
    private List<EnvConfig> envConfigs;
    /**
     * 错误码解释
     */
    private List<ErrorCode> errorCodes;
    /**
     * 当前文档标签 及其地址连接
     */
    private List<DocInfoLabel>labels;
    /**
     *
     */
    private String msgDemo;
    /**
     * 大标题展示
     */
    private String name;
    /**
     * 公共请求参数
     */
    private List<DocParams> publicParams;
    /**
     * 公共返回参数
     */
    private List<DocParams> publicResponseParams;
    /**
     * 请求参数
     */
    private List<DocParams> requestParams;
    /**
     * 响应参数
     */
    private List<DocParams> responseParams;
    /**
     * json响应实例
     */
    private String rspSampleJson;
    /**
     * xml响应实例
     */
    private String rspSampleXml;
    /**
     * 普通响应实例json
     */
    private String rspSampleSimplifyJson;
    /**
     * sdk请求实例
     */
    private List<ScopeApiInfo>sdkDemos;
    /**
     * API工具
     */
    private List<ScopeApiInfo> tools;

}
