package com.cn.processframework.part.plateform.request;

import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.part.plateform.AuthStateCache;
import com.cn.processframework.part.plateform.configuration.AuthConfig;
import com.cn.processframework.part.plateform.configuration.AuthDefaultSource;
import com.cn.processframework.part.plateform.AuthResponseStatus;
import com.cn.processframework.part.plateform.scope.AuthKujialeScope;
import com.cn.processframework.part.plateform.exception.AuthException;
import com.cn.processframework.part.plateform.AuthUser;
import com.cn.processframework.part.plateform.AuthCallback;
import com.cn.processframework.part.plateform.AuthResponse;
import com.cn.processframework.part.plateform.AuthToken;
import com.cn.processframework.part.plateform.AuthScopeUtils;
import com.cn.processframework.part.plateform.HttpUtils;
import com.cn.processframework.part.plateform.UrlBuilder;

/**
 * @author apple
 * @desc 酷家乐授权登录
 * @since 1.11.0
 */
public class AuthKujialeRequest extends AuthDefaultRequest {

    public AuthKujialeRequest(AuthConfig config) {
        super(config, AuthDefaultSource.KUJIALE);
    }

    public AuthKujialeRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.KUJIALE, authStateCache);
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     * 默认只向用户请求用户信息授权
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     * @since 1.11.0
     */
    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(super.authorize(state))
            .queryParam("scope", this.getScopes(",", false, AuthScopeUtils.getDefaultScopes(AuthKujialeScope.values())))
            .build();
    }

    @Override
    public AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        return getAuthToken(response);
    }

    private AuthToken getAuthToken(String response) {
        JSONObject accessTokenObject = checkResponse(response);
        JSONObject resultObject = accessTokenObject.getJSONObject("d");
        return AuthToken.builder()
            .accessToken(resultObject.getString("accessToken"))
            .refreshToken(resultObject.getString("refreshToken"))
            .expireIn(resultObject.getIntValue("expiresIn"))
            .build();
    }

    private JSONObject checkResponse(String response) {
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        if (!"0".equals(accessTokenObject.getString("c"))) {
            throw new AuthException(accessTokenObject.getString("m"));
        }
        return accessTokenObject;
    }

    @Override
    public AuthUser getUserInfo(AuthToken authToken) {
        String openId = this.getOpenId(authToken);
        String response = new HttpUtils(config.getHttpConfig()).get(UrlBuilder.fromBaseUrl(source.userInfo())
            .queryParam("access_token", authToken.getAccessToken())
            .queryParam("open_id", openId)
            .build());
        JSONObject object = JSONObject.parseObject(response);
        if (!"0".equals(object.getString("c"))) {
            throw new AuthException(object.getString("m"));
        }
        JSONObject resultObject = object.getJSONObject("d");

        return AuthUser.builder()
            .rawUserInfo(resultObject)
            .username(resultObject.getString("userName"))
            .nickname(resultObject.getString("userName"))
            .avatar(resultObject.getString("avatar"))
            .uuid(resultObject.getString("openId"))
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 获取酷家乐的openId，此id在当前client范围内可以唯一识别授权用户
     *
     * @param authToken 通过{@link AuthKujialeRequest#getAccessToken(AuthCallback)}获取到的{@code authToken}
     * @return openId
     */
    private String getOpenId(AuthToken authToken) {
        String response = new HttpUtils(config.getHttpConfig()).get(UrlBuilder.fromBaseUrl("https://oauth.kujiale.com/oauth2/auth/user")
            .queryParam("access_token", authToken.getAccessToken())
            .build());
        JSONObject accessTokenObject = checkResponse(response);
        return accessTokenObject.getString("d");
    }

    @Override
    public AuthResponse refresh(AuthToken authToken) {
        String response = new HttpUtils(config.getHttpConfig()).post(refreshTokenUrl(authToken.getRefreshToken()));
        return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).data(getAuthToken(response)).build();
    }
}
