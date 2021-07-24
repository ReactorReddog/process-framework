package com.cn.processframework.part.plateform.request.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.part.plateform.cache.AuthStateCache;
import com.cn.processframework.part.plateform.config.AuthConfig;
import com.cn.processframework.part.plateform.config.AuthDefaultSource;
import com.cn.processframework.part.plateform.constants.AuthResponseStatus;
import com.cn.processframework.part.plateform.constants.AuthUserGender;
import com.cn.processframework.part.plateform.exception.AuthException;
import com.cn.processframework.part.plateform.model.AuthUser;
import com.cn.processframework.part.plateform.model.callback.AuthCallback;
import com.cn.processframework.part.plateform.model.token.AuthToken;
import com.cn.processframework.part.plateform.utils.HttpUtils;
import com.cn.processframework.part.plateform.utils.UrlBuilder;

/**
 * @author apple
 * @desc 微信登录
 * @since 1.0 22:11
 */
public class AuthWeChatEnterpriseRequest extends AuthDefaultRequest {
    public AuthWeChatEnterpriseRequest(AuthConfig config) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE);
    }

    public AuthWeChatEnterpriseRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE, authStateCache);
    }

    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = this.doGetAuthorizationCode(this.accessTokenUrl(authCallback.getCode()));
        JSONObject object = this.checkResponse(response);
        return AuthToken.builder().accessToken(object.getString("access_token")).expireIn(object.getIntValue("expires_in")).code(authCallback.getCode()).build();
    }

    protected AuthUser getUserInfo(AuthToken authToken) {
        String response = this.doGetUserInfo(authToken);
        JSONObject object = this.checkResponse(response);
        if (!object.containsKey("UserId")) {
            throw new AuthException(AuthResponseStatus.UNIDENTIFIED_PLATFORM, this.source);
        } else {
            String userId = object.getString("UserId");
            String userDetailResponse = this.getUserDetail(authToken.getAccessToken(), userId);
            JSONObject userDetail = this.checkResponse(userDetailResponse);
            return AuthUser.builder().rawUserInfo(userDetail).username(userDetail.getString("name")).nickname(userDetail.getString("alias")).avatar(userDetail.getString("avatar")).location(userDetail.getString("address")).email(userDetail.getString("email")).uuid(userId).gender(AuthUserGender.getWechatRealGender(userDetail.getString("gender"))).token(authToken).source(this.source.toString()).build();
        }
    }

    private JSONObject checkResponse(String response) {
        JSONObject object = JSONObject.parseObject(response);
        if (object.containsKey("errcode") && object.getIntValue("errcode") != 0) {
            throw new AuthException(object.getString("errmsg"), this.source);
        } else {
            return object;
        }
    }

    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(this.source.authorize()).queryParam("appid", this.config.getClientId()).queryParam("agentid", this.config.getAgentId()).queryParam("redirect_uri", this.config.getRedirectUri()).queryParam("state", this.getRealState(state)).build();
    }

    protected String accessTokenUrl(String code) {
        return UrlBuilder.fromBaseUrl(this.source.accessToken()).queryParam("corpid", this.config.getClientId()).queryParam("corpsecret", this.config.getClientSecret()).build();
    }

    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(this.source.userInfo()).queryParam("access_token", authToken.getAccessToken()).queryParam("code", authToken.getCode()).build();
    }

    private String getUserDetail(String accessToken, String userId) {
        String userDetailUrl = UrlBuilder.fromBaseUrl("https://qyapi.weixin.qq.com/cgi-bin/user/get").queryParam("access_token", accessToken).queryParam("userid", userId).build();
        return (new HttpUtils(this.config.getHttpConfig())).get(userDetailUrl);
    }
}
