package com.cn.processframework.part.plateform.constants.scope.impl;

import com.cn.processframework.part.plateform.constants.scope.AuthScope;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author apple
 * @desc 微信公众平台 OAuth 授权范围
 * @since 1.0 22:09
 */
@Getter
@AllArgsConstructor
public enum AuthWechatMpScope implements AuthScope {
    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    SNSAPI_USERINFO("snsapi_userinfo", "弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息", true),
    SNSAPI_BASE("snsapi_base", "不弹出授权页面，直接跳转，只能获取用户openid", false);

    private String scope;
    private String description;
    private boolean isDefault;

}

