package com.cn.processframework.part.plateform.request.impl;

import com.cn.processframework.part.plateform.cache.AuthStateCache;
import com.cn.processframework.part.plateform.config.AuthConfig;
import com.cn.processframework.part.plateform.config.AuthDefaultSource;
import com.cn.processframework.part.plateform.utils.UrlBuilder;
/**
 * <p>
 * 企业微信二维码登录
 * </p>
 *
 * @author apple
 * @author liguanhua (347826496(a)qq.com) 重构该类，将通用方法提取
 * @author lapple 修改类名
 * @since 1.10.0
 */
public class AuthWeChatEnterpriseQrcodeRequest extends AbstractAuthWeChatEnterpriseRequest {
    public AuthWeChatEnterpriseQrcodeRequest(AuthConfig config) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE);
    }

    public AuthWeChatEnterpriseQrcodeRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE, authStateCache);
    }

    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("appid", config.getClientId())
            .queryParam("agentid", config.getAgentId())
            .queryParam("redirect_uri", config.getRedirectUri())
            .queryParam("state", getRealState(state))
            .build();
    }
}
