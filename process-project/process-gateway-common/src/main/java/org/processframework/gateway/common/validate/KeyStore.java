package org.processframework.gateway.common.validate;

import lombok.Data;

/**
 * @author apple
 * @desc keystore
 */
@Data
public class KeyStore {
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;
}