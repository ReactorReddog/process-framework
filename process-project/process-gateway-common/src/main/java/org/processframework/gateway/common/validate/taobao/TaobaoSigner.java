package org.processframework.gateway.common.validate.taobao;

import org.processframework.gateway.common.ApiException;
import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.ErrorEnum;
import org.processframework.gateway.common.validate.SignConfig;
import org.processframework.gateway.common.validate.SignEncipher;
import org.processframework.gateway.common.validate.SignEncipherHMAC_MD5;
import org.processframework.gateway.common.validate.SignEncipherMD5;

import java.util.*;

/**
 * @author apple
 * @desc 淘宝开放平台签名验证实现
 * @see <a href="http://open.taobao.com/doc.htm?docId=101617&docType=1">淘宝签名</a>
 */
public class TaobaoSigner extends AbstractSigner {

    private final Map<String, SignEncipher> signEncipherMap = new HashMap<>();

    public TaobaoSigner() {
        signEncipherMap.put("md5", new SignEncipherMD5());
        signEncipherMap.put("hmac", new SignEncipherHMAC_MD5());
    }


    @Override
    public String buildServerSign(ApiParam param, String secret) {
        String signMethod = param.fetchSignMethod();
        if (signMethod == null) {
            signMethod = "md5";
        }
        SignEncipher signEncipher = signEncipherMap.get(signMethod);
        if (signEncipher == null) {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE_TYPE,signMethod);
        }

        // 第一步：参数排序
        Set<String> keySet = param.keySet();
        List<String> paramNames = new ArrayList<>(keySet);
        Collections.sort(paramNames);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder paramNameValue = new StringBuilder();
        for (String paramName : paramNames) {
            String val = SignConfig.wrapVal(param.get(paramName));
            paramNameValue.append(paramName).append(val);
        }

        // 第三步：使用MD5/HMAC加密
        String source = paramNameValue.toString();
        byte[] bytes = signEncipher.encrypt(source, secret);

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes).toUpperCase();
    }
}
