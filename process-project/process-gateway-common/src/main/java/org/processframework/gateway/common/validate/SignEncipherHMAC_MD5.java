package org.processframework.gateway.common.validate;

import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.ApiException;
import org.processframework.gateway.common.ErrorEnum;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * HMAC_MD5加密
 * @author apple
 */
@Slf4j
public class SignEncipherHMAC_MD5 implements SignEncipher {

    public static final String HMAC_MD5 = "HmacMD5";

    @Override
    public byte[] encrypt(String input, String secret) {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_MD5);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            log.error("HMAC_MD5加密加密失败NoSuchAlgorithmException", e);
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE,e);
        } catch (InvalidKeyException e) {
            log.error("HMAC_MD5加密加密失败InvalidKeyException", e);
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE,e);
        }
    }
}
