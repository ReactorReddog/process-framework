package com.cn.processframework.part.plateform.request.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.part.plateform.cache.AuthStateCache;
import com.cn.processframework.part.plateform.config.AuthConfig;
import com.cn.processframework.part.plateform.config.AuthDefaultSource;
import com.cn.processframework.part.plateform.constants.AuthResponseStatus;
import com.cn.processframework.part.plateform.constants.AuthUserGender;
import com.cn.processframework.part.plateform.constants.scope.impl.AuthMicrosoftScope;
import com.cn.processframework.part.plateform.exception.AuthException;
import com.cn.processframework.part.plateform.model.AuthUser;
import com.cn.processframework.part.plateform.model.callback.AuthCallback;
import com.cn.processframework.part.plateform.model.result.AuthResponse;
import com.cn.processframework.part.plateform.model.token.AuthToken;
import com.cn.processframework.part.plateform.utils.AuthScopeUtils;
import com.cn.processframework.part.plateform.utils.HttpUtils;
import com.cn.processframework.part.plateform.utils.UrlBuilder;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.util.MapUtil;

import java.util.Map;

/**
 * @author apple
 * @desc 微软登录
 * @since 1.5.0
 */
public class AuthMicrosoftRequest extends AuthDefaultRequest {
    public AuthMicrosoftRequest(AuthConfig config) {
        super(config, AuthDefaultSource.MICROSOFT);
    }

    public AuthMicrosoftRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.MICROSOFT, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        return getToken(accessTokenUrl(authCallback.getCode()));
    }

    /**
     * 获取token，适用于获取access_token和刷新token
     *
     * @param accessTokenUrl 实际请求token的地址
     * @return token对象
     */
    private AuthToken getToken(String accessTokenUrl) {
        HttpHeader httpHeader = new HttpHeader();

        Map<String, String> form = MapUtil.parseStringToMap(accessTokenUrl, false);

        String response = new HttpUtils(config.getHttpConfig()).post(accessTokenUrl, form, httpHeader, false);
        JSONObject accessTokenObject = JSONObject.parseObject(response);

        this.checkResponse(accessTokenObject);

        return AuthToken.builder()
            .accessToken(accessTokenObject.getString("access_token"))
            .expireIn(accessTokenObject.getIntValue("expires_in"))
            .scope(accessTokenObject.getString("scope"))
            .tokenType(accessTokenObject.getString("token_type"))
            .refreshToken(accessTokenObject.getString("refresh_token"))
            .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if (object.containsKey("error")) {
            throw new AuthException(object.getString("error_description"));
        }
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String token = authToken.getAccessToken();
        String tokenType = authToken.getTokenType();
        String jwt = tokenType + " " + token;

        HttpHeader httpHeader = new HttpHeader();
        httpHeader.add("Authorization", jwt);

        String userInfo = new HttpUtils(config.getHttpConfig()).get(userInfoUrl(authToken), null, httpHeader, false);
        JSONObject object = JSONObject.parseObject(userInfo);
        this.checkResponse(object);
        return AuthUser.builder()
            .rawUserInfo(object)
            .uuid(object.getString("id"))
            .username(object.getString("userPrincipalName"))
            .nickname(object.getString("displayName"))
            .location(object.getString("officeLocation"))
            .email(object.getString("mail"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    @Override
    public AuthResponse refresh(AuthToken authToken) {
        return AuthResponse.builder()
            .code(AuthResponseStatus.SUCCESS.getCode())
            .data(getToken(refreshTokenUrl(authToken.getRefreshToken())))
            .build();
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     * @since 1.9.3
     */
    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(super.authorize(state))
            .queryParam("response_mode", "query")
            .queryParam("scope", this.getScopes(" ", true, AuthScopeUtils.getDefaultScopes(AuthMicrosoftScope.values())))
            .build();
    }

    /**
     * 返回获取accessToken的url
     *
     * @param code 授权code
     * @return 返回获取accessToken的url
     */
    @Override
    protected String accessTokenUrl(String code) {
        return UrlBuilder.fromBaseUrl(source.accessToken())
            .queryParam("code", code)
            .queryParam("client_id", config.getClientId())
            .queryParam("client_secret", config.getClientSecret())
            .queryParam("grant_type", "authorization_code")
            .queryParam("scope", this.getScopes(" ", true, AuthScopeUtils.getDefaultScopes(AuthMicrosoftScope.values())))
            .queryParam("redirect_uri", config.getRedirectUri())
            .build();
    }

    /**
     * 返回获取userInfo的url
     *
     * @param authToken 用户授权后的token
     * @return 返回获取userInfo的url
     */
    @Override
    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.userInfo()).build();
    }

    /**
     * 返回获取accessToken的url
     *
     * @param refreshToken 用户授权后的token
     * @return 返回获取accessToken的url
     */
    @Override
    protected String refreshTokenUrl(String refreshToken) {
        return UrlBuilder.fromBaseUrl(source.refresh())
            .queryParam("client_id", config.getClientId())
            .queryParam("client_secret", config.getClientSecret())
            .queryParam("refresh_token", refreshToken)
            .queryParam("grant_type", "refresh_token")
            .queryParam("scope", this.getScopes(" ", true, AuthScopeUtils.getDefaultScopes(AuthMicrosoftScope.values())))
            .queryParam("redirect_uri", config.getRedirectUri())
            .build();
    }
}
