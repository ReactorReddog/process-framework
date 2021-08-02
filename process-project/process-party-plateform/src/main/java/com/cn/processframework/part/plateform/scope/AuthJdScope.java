package com.cn.processframework.part.plateform.scope;

import com.cn.processframework.part.plateform.AuthScope;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author apple
 * @desc 京东平台 OAuth 授权范围
 * @since 1.0 22:02
 */
@Getter
@AllArgsConstructor
public enum AuthJdScope implements AuthScope {
    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    SNSAPI_BASE("snsapi_base", "基础授权", true);

    private String scope;
    private String description;
    private boolean isDefault;
}
