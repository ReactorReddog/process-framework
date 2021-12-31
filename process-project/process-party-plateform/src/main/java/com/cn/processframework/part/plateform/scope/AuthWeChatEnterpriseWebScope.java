package com.cn.processframework.part.plateform.scope;

import com.cn.processframework.part.plateform.AuthScope;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author apple 
 * @desc 企业自建应用授权范围
 * @since 1.0 22:08
 */
@Getter
@AllArgsConstructor
public enum AuthWeChatEnterpriseWebScope implements AuthScope {
    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    SNSAPI_BASE("snsapi_base", "应用授权作用域。企业自建应用固定填写：snsapi_base", true);

    private String scope;
    private String description;
    private boolean isDefault;

}