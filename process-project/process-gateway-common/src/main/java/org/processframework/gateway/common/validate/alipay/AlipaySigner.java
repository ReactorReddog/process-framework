package org.processframework.gateway.common.validate.alipay;

import org.processframework.gateway.common.ApiException;
import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.ErrorEnum;
import org.processframework.gateway.common.validate.Signer;

/**
 * @author apple 
 * @desc 支付宝签名验证实现。
 * @see <a href="https://docs.open.alipay.com/291/106118">支付宝签名</a>
 */
public class AlipaySigner implements Signer {

    @Override
    public boolean checkSign(ApiParam apiParam, String secret) {
        // 服务端存的是公钥
        String charset = apiParam.fetchCharset();
        String signType = apiParam.fetchSignMethod();
        if (signType == null) {
            throw new ApiException(ErrorEnum.ISV_DECRYPTION_ERROR_MISSING_ENCRYPT_TYPE);
        }
        if (charset == null) {
            throw new ApiException(ErrorEnum.ISV_INVALID_CHARSET);
        }
        return AlipaySignature.rsaCheckV2(apiParam, secret, charset, signType);
    }

}
