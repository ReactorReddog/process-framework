package com.cn.processframework.part.plateform.scope;

import com.cn.processframework.part.plateform.AuthScope;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author apple
 * @desc 小米平台 OAuth 授权范围
 * @since 1.0 22:05
 */
@Getter
@AllArgsConstructor
public enum AuthMiScope implements AuthScope {

    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    profile("user/profile", "获取用户的基本信息", true),
    OPENID("user/openIdV2", "获取用户的OpenID", true),
    PHONE_EMAIL("user/phoneAndEmail", "获取用户的手机号和邮箱", true);

    private String scope;
    private String description;
    private boolean isDefault;

}
