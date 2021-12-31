package org.processframework.gateway.common.validate;

import org.processframework.gateway.common.ApiParam;

/**
 * @author apple 
 * @desc 负责签名校验
 */
public interface Signer {

    /**
     * 签名校验
     * @param apiParam 参数
     * @param secret 秘钥
     * @return true签名正确
     */
    boolean checkSign(ApiParam apiParam, String secret);
    
}
