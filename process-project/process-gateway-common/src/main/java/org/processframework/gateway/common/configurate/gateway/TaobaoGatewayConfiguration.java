package org.processframework.gateway.common.configurate.gateway;

import org.processframework.gateway.common.ParamNames;
import org.processframework.gateway.common.core.ApiContext;
import org.processframework.gateway.common.validate.taobao.TaobaoSigner;

/**
 * @author apple
 * @desc 具备淘宝开放平台能力配置
 * 淘宝开放平台：http://open.taobao.com/doc.htm
 * @since 1.0.0.RELEASE
 */
public class TaobaoGatewayConfiguration extends BaseGatewayConfiguration {

    static {
        ParamNames.APP_KEY_NAME = "app_key";
        ParamNames.SIGN_TYPE_NAME = "sign_method";
        ParamNames.VERSION_NAME = "v";
        ParamNames.APP_AUTH_TOKEN_NAME = "session";

        ApiContext.getApiConfig().setSigner(new TaobaoSigner());
    }

}
