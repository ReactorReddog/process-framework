package com.cn.processframework.part.plateform.request;

import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.part.plateform.AuthStateCache;
import com.cn.processframework.part.plateform.configuration.AuthConfig;
import com.cn.processframework.part.plateform.AuthUserGender;
import com.cn.processframework.part.plateform.scope.AuthPinterestScope;
import com.cn.processframework.part.plateform.exception.AuthException;
import com.cn.processframework.part.plateform.AuthUser;
import com.cn.processframework.part.plateform.AuthCallback;
import com.cn.processframework.part.plateform.AuthToken;
import com.cn.processframework.part.plateform.AuthScopeUtils;
import com.cn.processframework.part.plateform.HttpUtils;
import com.cn.processframework.part.plateform.UrlBuilder;

import java.util.Objects;

import static com.cn.processframework.part.plateform.configuration.AuthDefaultSource.PINTEREST;


/**
 * @author apple
 * @desc Pinterest登录
 * @since 1.0 22:31
 */
public class AuthPinterestRequest extends AuthDefaultRequest {

    private static final String FAILURE = "failure";

    public AuthPinterestRequest(AuthConfig config) {
        super(config, PINTEREST);
    }

    public AuthPinterestRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, PINTEREST, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        this.checkResponse(accessTokenObject);
        return AuthToken.builder()
            .accessToken(accessTokenObject.getString("access_token"))
            .tokenType(accessTokenObject.getString("token_type"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String userinfoUrl = userInfoUrl(authToken);
        // TODO: 是否需要 .setFollowRedirects(true)
        String response = new HttpUtils(config.getHttpConfig()).get(userinfoUrl);
        JSONObject object = JSONObject.parseObject(response);
        this.checkResponse(object);
        JSONObject userObj = object.getJSONObject("data");
        return AuthUser.builder()
            .rawUserInfo(userObj)
            .uuid(userObj.getString("id"))
            .avatar(getAvatarUrl(userObj))
            .username(userObj.getString("username"))
            .nickname(userObj.getString("first_name") + " " + userObj.getString("last_name"))
            .gender(AuthUserGender.UNKNOWN)
            .remark(userObj.getString("bio"))
            .token(authToken)
            .source(source.toString())
            .build();
    }

    private String getAvatarUrl(JSONObject userObj) {
        // image is a map data structure
        JSONObject jsonObject = userObj.getJSONObject("image");
        if (Objects.isNull(jsonObject)) {
            return null;
        }
        return jsonObject.getJSONObject("60x60").getString("url");
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
            .queryParam("scope", this.getScopes(",", false, AuthScopeUtils.getDefaultScopes(AuthPinterestScope.values())))
            .build();
    }

    /**
     * 返回获取userInfo的url
     *
     * @param authToken token
     * @return 返回获取userInfo的url
     */
    @Override
    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.userInfo())
            .queryParam("access_token", authToken.getAccessToken())
            .queryParam("fields", "id,username,first_name,last_name,bio,image")
            .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if (!object.containsKey("status") && FAILURE.equals(object.getString("status"))) {
            throw new AuthException(object.getString("message"));
        }
    }

}
