package com.cn.processframework.part.plateform.request;

import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.part.plateform.AuthStateCache;
import com.cn.processframework.part.plateform.configuration.AuthConfig;
import com.cn.processframework.part.plateform.AuthUserGender;
import com.cn.processframework.part.plateform.scope.AuthStackoverflowScope;
import com.cn.processframework.part.plateform.exception.AuthException;
import com.cn.processframework.part.plateform.AuthUser;
import com.cn.processframework.part.plateform.AuthCallback;
import com.cn.processframework.part.plateform.AuthToken;
import com.cn.processframework.part.plateform.AuthScopeUtils;
import com.cn.processframework.part.plateform.HttpUtils;
import com.cn.processframework.part.plateform.UrlBuilder;
import com.xkcoding.http.constants.Constants;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.util.MapUtil;

import java.util.Map;

import static com.cn.processframework.part.plateform.configuration.AuthDefaultSource.STACK_OVERFLOW;


/**
 * Stack Overflow登录
 *
 * @author apple
 * @since 1.0 22:31
 */
public class AuthStackOverflowRequest extends AuthDefaultRequest {

    public AuthStackOverflowRequest(AuthConfig config) {
        super(config, STACK_OVERFLOW);
    }

    public AuthStackOverflowRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, STACK_OVERFLOW, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String accessTokenUrl = accessTokenUrl(authCallback.getCode());
        Map<String, String> form = MapUtil.parseStringToMap(accessTokenUrl, false);
        HttpHeader httpHeader = new HttpHeader();
        httpHeader.add(Constants.CONTENT_TYPE, "application/x-www-form-urlencoded");
        String response = new HttpUtils(config.getHttpConfig()).post(accessTokenUrl, form, httpHeader, false);

        JSONObject accessTokenObject = JSONObject.parseObject(response);
        this.checkResponse(accessTokenObject);

        return AuthToken.builder()
            .accessToken(accessTokenObject.getString("access_token"))
            .expireIn(accessTokenObject.getIntValue("expires"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String userInfoUrl = UrlBuilder.fromBaseUrl(this.source.userInfo())
            .queryParam("access_token", authToken.getAccessToken())
            .queryParam("site", "stackoverflow")
            .queryParam("key", this.config.getStackOverflowKey())
            .build();
        String response = new HttpUtils(config.getHttpConfig()).get(userInfoUrl);
        JSONObject object = JSONObject.parseObject(response);
        this.checkResponse(object);
        JSONObject userObj = object.getJSONArray("items").getJSONObject(0);

        return AuthUser.builder()
            .rawUserInfo(userObj)
            .uuid(userObj.getString("user_id"))
            .avatar(userObj.getString("profile_image"))
            .location(userObj.getString("location"))
            .nickname(userObj.getString("display_name"))
            .blog(userObj.getString("website_url"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
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
            .queryParam("scope", this.getScopes(",", false, AuthScopeUtils.getDefaultScopes(AuthStackoverflowScope.values())))
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
}
