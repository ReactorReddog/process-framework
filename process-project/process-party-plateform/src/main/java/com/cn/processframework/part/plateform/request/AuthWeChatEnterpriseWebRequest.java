package com.cn.processframework.part.plateform.request;

import com.cn.processframework.part.plateform.AuthStateCache;
import com.cn.processframework.part.plateform.configuration.AuthConfig;
import com.cn.processframework.part.plateform.configuration.AuthDefaultSource;
import com.cn.processframework.part.plateform.scope.AuthWeChatEnterpriseWebScope;
import com.cn.processframework.part.plateform.AuthScopeUtils;
import com.cn.processframework.part.plateform.UrlBuilder;

/**
 * <p>
 * 企业微信网页登录
 * </p>
 *
 * @author apple
 * @since 1.0.1
 */
public class AuthWeChatEnterpriseWebRequest extends AbstractAuthWeChatEnterpriseRequest {
    public AuthWeChatEnterpriseWebRequest(AuthConfig config) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE_WEB);
    }

    public AuthWeChatEnterpriseWebRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE_WEB, authStateCache);
    }

    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("appid", config.getClientId())
            .queryParam("redirect_uri", config.getRedirectUri())
            .queryParam("response_type", "code")
            .queryParam("scope", this.getScopes(",", false, AuthScopeUtils.getDefaultScopes(AuthWeChatEnterpriseWebScope.values())))
            .queryParam("state", getRealState(state).concat("#wechat_redirect"))
            .build();
    }
}
