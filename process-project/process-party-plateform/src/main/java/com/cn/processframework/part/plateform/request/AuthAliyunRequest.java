package com.cn.processframework.part.plateform.request;

import com.alibaba.fastjson.JSONObject;
import com.cn.processframework.part.plateform.AuthStateCache;
import com.cn.processframework.part.plateform.configuration.AuthConfig;
import com.cn.processframework.part.plateform.configuration.AuthDefaultSource;
import com.cn.processframework.part.plateform.AuthUserGender;
import com.cn.processframework.part.plateform.AuthUser;
import com.cn.processframework.part.plateform.AuthCallback;
import com.cn.processframework.part.plateform.AuthToken;

/**
 * @author apple
 * @desc 阿里云登录
 * @since 1.0 22:13
 */
public class AuthAliyunRequest extends AuthDefaultRequest {

    public AuthAliyunRequest(AuthConfig config) {
        super(config, AuthDefaultSource.ALIYUN);
    }

    public AuthAliyunRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.ALIYUN, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        return AuthToken.builder()
                .accessToken(accessTokenObject.getString("access_token"))
                .expireIn(accessTokenObject.getIntValue("expires_in"))
                .tokenType(accessTokenObject.getString("token_type"))
                .idToken(accessTokenObject.getString("id_token"))
                .refreshToken(accessTokenObject.getString("refresh_token"))
                .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String userInfo = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(userInfo);
        return AuthUser.builder()
                .rawUserInfo(object)
                .uuid(object.getString("sub"))
                .username(object.getString("login_name"))
                .nickname(object.getString("name"))
                .gender(AuthUserGender.UNKNOWN)
                .token(authToken)
                .source(source.toString())
                .build();
    }

}