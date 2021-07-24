package com.cn.processframework.part.plateform.request.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.part.plateform.cache.AuthStateCache;
import com.cn.processframework.part.plateform.config.AuthConfig;
import com.cn.processframework.part.plateform.config.AuthDefaultSource;
import com.cn.processframework.part.plateform.constants.AuthResponseStatus;
import com.cn.processframework.part.plateform.constants.AuthUserGender;
import com.cn.processframework.part.plateform.constants.scope.impl.AuthBaiduScope;
import com.cn.processframework.part.plateform.exception.AuthException;
import com.cn.processframework.part.plateform.model.AuthUser;
import com.cn.processframework.part.plateform.model.callback.AuthCallback;
import com.cn.processframework.part.plateform.model.result.AuthResponse;
import com.cn.processframework.part.plateform.model.token.AuthToken;
import com.cn.processframework.part.plateform.utils.AuthScopeUtils;
import com.cn.processframework.part.plateform.utils.HttpUtils;
import com.cn.processframework.part.plateform.utils.UrlBuilder;
import com.xkcoding.http.util.StringUtil;

/**
 * @author apple
 * @desc 百度账号登录
 * @since 1.0 22:14
 */
public class AuthBaiduRequest extends AuthDefaultRequest {

    public AuthBaiduRequest(AuthConfig config) {
        super(config, AuthDefaultSource.BAIDU);
    }

    public AuthBaiduRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.BAIDU, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        return getAuthToken(response);
    }

    /**
     * https://openapi.baidu.com/rest/2.0/passport/users/getInfo?access_token=121.c86e87cc0828cc1dabb8faee540531d4.YsUIAWvYbgqVni1VhkgKgyLh8nEyELbDOEZs_OA.OgDgmA
     * https://openapi.baidu.com/rest/2.0/passport/users/getInfo?access_token=121.2907d9facf9fb97adf7287fa75496eda.Y3NSjR3-3HKt1RgT0HEl7GgxRXT5gOOVdngXezY.OcC_7g
     * 新旧应用返回的用户信息不一致
     *
     * @param authToken token信息
     * @return AuthUser
     */
    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String userInfo = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(userInfo);
        this.checkResponse(object);
        return AuthUser.builder()
                .rawUserInfo(object)
                .uuid(object.containsKey("userid") ? object.getString("userid") : object.getString("openid"))
                .username(object.getString("username"))
                .nickname(object.getString("username"))
                .avatar(getAvatar(object))
                .remark(object.getString("userdetail"))
                .gender(AuthUserGender.getRealGender(object.getString("sex")))
                .token(authToken)
                .source(source.toString())
                .build();
    }

    private String getAvatar(JSONObject object) {
        String protrait = object.getString("portrait");
        return StringUtil.isEmpty(protrait) ? null : String.format("http://himg.bdimg.com/sys/portrait/item/%s.jpg", protrait);
    }

    @Override
    public AuthResponse revoke(AuthToken authToken) {
        String response = doGetRevoke(authToken);
        JSONObject object = JSONObject.parseObject(response);
        this.checkResponse(object);
        // 返回1表示取消授权成功，否则失败
        AuthResponseStatus status = object.getIntValue("result") == 1 ? AuthResponseStatus.SUCCESS : AuthResponseStatus.FAILURE;
        return AuthResponse.builder().code(status.getCode()).msg(status.getMsg()).build();
    }

    @Override
    public AuthResponse refresh(AuthToken authToken) {
        String refreshUrl = UrlBuilder.fromBaseUrl(this.source.refresh())
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", authToken.getRefreshToken())
                .queryParam("client_id", this.config.getClientId())
                .queryParam("client_secret", this.config.getClientSecret())
                .build();
        String response = new HttpUtils(config.getHttpConfig()).get(refreshUrl);
        return AuthResponse.builder()
                .code(AuthResponseStatus.SUCCESS.getCode())
                .data(this.getAuthToken(response))
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
                .queryParam("display", "popup")
                .queryParam("scope", this.getScopes(" ", true, AuthScopeUtils.getDefaultScopes(AuthBaiduScope.values())))
                .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if (object.containsKey("error") || object.containsKey("error_code")) {
            String msg = object.containsKey("error_description") ? object.getString("error_description") : object.getString("error_msg");
            throw new AuthException(msg);
        }
    }

    private AuthToken getAuthToken(String response) {
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        this.checkResponse(accessTokenObject);
        return AuthToken.builder()
                .accessToken(accessTokenObject.getString("access_token"))
                .refreshToken(accessTokenObject.getString("refresh_token"))
                .scope(accessTokenObject.getString("scope"))
                .expireIn(accessTokenObject.getIntValue("expires_in"))
                .build();
    }
}