package org.processframework.gateway.common.validate;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author apple
 * @desc 负责各类加解密
 * @since 1.0.0.RELEASE
 */
public class ApiEncrypter implements Encrypter {
    /**
     * AES文本加密
     * @param content  明文
     * @param password 密码
     * @return String
     * @throws Exception
     */
    @Override
    public String aesEncryptToHex(String content, String password) throws Exception {
        return AESUtil.encryptToHex(content, password);
    }

    /**
     * AES BASE16机制文本解密
     * @param hex      待解密文本,16进制内容
     * @param password 密码
     * @return String
     * @throws Exception
     */
    @Override
    public String aesDecryptFromHex(String hex, String password) throws Exception {
        return AESUtil.decryptFromHex(hex, password);
    }

    /**
     * AES BASE64位加密
     * @param content  明文
     * @param password 密码
     * @return String
     * @throws Exception
     */
    @Override
    public String aesEncryptToBase64String(String content, String password) throws Exception {
        return AESUtil.encryptToBase64String(content, password);
    }

    /**
     * AES BASE64位解密
     * @param base64String 待解密文本,16进制内容
     * @param password     密码
     * @return String
     * @throws Exception
     */
    @Override
    public String aesDecryptFromBase64String(String base64String, String password) throws Exception {
        return AESUtil.decryptFromBase64String(base64String, password);
    }

    /**
     * 私钥解密
     * @param data 解密内容
     * @param privateKey 私钥
     * @return String
     * @throws Exception
     */
    @Override
    public String rsaDecryptByPrivateKey(String data, String privateKey) throws Exception {
        return RSAUtil.decryptByPrivateKey(data, privateKey);
    }

    /**
     * 私钥加密
     * @param data 明文
     * @param privateKey 私钥
     * @return String
     * @throws Exception
     */
    @Override
    public String rsaEncryptByPrivateKey(String data, String privateKey) throws Exception {
        return RSAUtil.encryptByPrivateKey(data, privateKey);
    }

    /**
     * 新版私钥解密
     * @param data 解密内容
     * @param privateKey 私钥
     * @return String
     * @throws Exception
     */
    @Override
    public String rsaDecryptByPrivateKeyNew(String data, String privateKey) throws Exception {
        return RSANewUtil.decryptByPrivateKey(data, privateKey);
    }

    /**
     * 新版私钥加密
     * @param data 待加密内容
     * @param privateKey 私钥
     * @return String
     * @throws Exception
     */
    @Override
    public String rsaEncryptByPrivateKeyNew(String data, String privateKey) throws Exception {
        return RSANewUtil.encryptByPrivateKey(data, privateKey);
    }

    /**
     * md5加密
     * @param value 待加密内容
     * @return String
     */
    @Override
    public String md5(String value) {
        return DigestUtils.md5Hex(value);
    }

}
