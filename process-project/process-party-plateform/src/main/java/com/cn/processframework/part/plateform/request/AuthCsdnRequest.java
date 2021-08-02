package com.cn.processframework.part.plateform.request;

import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.part.plateform.AuthStateCache;
import com.cn.processframework.part.plateform.configuration.AuthConfig;
import com.cn.processframework.part.plateform.configuration.AuthDefaultSource;
import com.cn.processframework.part.plateform.AuthUserGender;
import com.cn.processframework.part.plateform.exception.AuthException;
import com.cn.processframework.part.plateform.AuthUser;
import com.cn.processframework.part.plateform.AuthCallback;
import com.cn.processframework.part.plateform.AuthToken;

/**
 * @author apple
 * @desc CSDN 登录
 * @since 1.0 22:19
 */
@Deprecated
public class AuthCsdnRequest extends AuthDefaultRequest {

    public AuthCsdnRequest(AuthConfig config) {
        super(config, AuthDefaultSource.CSDN);
    }

    public AuthCsdnRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.CSDN, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        this.checkResponse(accessTokenObject);
        return AuthToken.builder().accessToken(accessTokenObject.getString("access_token")).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String response = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(response);
        this.checkResponse(object);
        return AuthUser.builder()
                .rawUserInfo(object)
                .uuid(object.getString("username"))
                .username(object.getString("username"))
                .remark(object.getString("description"))
                .blog(object.getString("website"))
                .gender(AuthUserGender.UNKNOWN)
                .token(authToken)
                .source(source.toString())
                .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if (object.containsKey("error_code")) {
            throw new AuthException(object.getString("error"));
        }
    }
}