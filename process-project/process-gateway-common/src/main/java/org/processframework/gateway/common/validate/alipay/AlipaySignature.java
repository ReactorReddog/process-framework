package org.processframework.gateway.common.validate.alipay;

import com.cn.processframework.tools.StreamUtil;
import com.cn.processframework.tools.string.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.processframework.gateway.common.ApiException;
import org.processframework.gateway.common.ErrorEnum;
import org.processframework.gateway.common.validate.SignConfig;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author apple
 * @desc 仿支付宝加密、解密、验签
 * @since 1.0.0.RELEASE
 */
public class AlipaySignature {
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 内容转换 将map或者json类型的参数或者数据转化为xx=x1&xx1=x2数据 类似地址拼接
     *
     * @param sortedParams map或json领域对象
     * @return String
     */
    public static String getSignContent(Map<String, Object> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (String key : keys) {
            String value = String.valueOf(sortedParams.get(key));
            if (StringUtils.areNotEmpty(key, value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     * rsa内容签名
     *
     * @param content   内容
     * @param publicKey 公钥
     * @param charset   字符类型
     * @param signType  加密类型
     * @return String
     */
    public static String rsaSign(String content, String publicKey, String charset, String signType) {
        if (AlipayConstants.SIGN_TYPE_RSA.equals(signType)) {
            return rsaSign(content, publicKey, charset);
        } else if (AlipayConstants.SIGN_TYPE_RSA2.equals(signType)) {
            return rsa256Sign(content, publicKey, charset);
        } else {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE_TYPE);
        }

    }

    /**
     * sha1WithRsa 加签
     *
     * @param params    内容
     * @param publicKey 公钥
     * @param charset   字符类型
     * @param signType  加密类型
     * @return String
     */
    public static String rsaSign(Map<String, Object> params, String publicKey,
                                 String charset, String signType) {
        String signContent = getSignContent(params);

        return rsaSign(signContent, publicKey, charset, signType);

    }

    /**
     * sha1WithRsa 加签
     *
     * @param content   内容
     * @param publicKey 公钥
     * @param charset   字符类型 utf-8、等
     * @return String
     */
    public static String rsaSign(String content, String publicKey,
                                 String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(AlipayConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(AlipayConstants.SIGN_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE_TYPE, ie);
        } catch (Exception e) {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE, e);
        }
    }

    /**
     * sha256WithRsa 加签
     *
     * @param content    内容
     * @param privateKey 私钥
     * @param charset    字符类型
     * @return String
     */
    public static String rsa256Sign(String content, String privateKey, String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(AlipayConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(privateKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(AlipayConstants.SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE, e);
        }

    }

    /**
     * 获取 PKCS8公钥
     *
     * @param signType 加密类型
     * @param ins      byte
     * @return PrivateKey
     */
    public static PrivateKey getPrivateKeyFromPKCS8(String signType, InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(signType)) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(signType);
        byte[] encodedKey = StreamUtil.readText(ins, "UTF-8").getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    /**
     * 获取签名内容 v1版本
     *
     * @param params 签名构建参数
     * @return String
     */
    public static String getSignCheckContentV1(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");
        params.remove("sign_type");

        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = SignConfig.wrapVal(params.get(key));
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }

        return content.toString();
    }

    /**
     * 获取签名内容 v2
     *
     * @param params 签名构建参数
     * @return
     */
    public static String getSignCheckContentV2(Map<String, ?> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");

        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = SignConfig.wrapVal(params.get(key));
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }

        return content.toString();
    }

    /**
     * RSA签名验证
     *
     * @param content   内容
     * @param sign      加密数据
     * @param publicKey 公钥
     * @param charset   字符类型
     * @param signType  签名类型
     * @return
     */
    public static boolean rsaCheck(String content, String sign, String publicKey, String charset, String signType) {
        if (AlipayConstants.SIGN_TYPE_RSA.equals(signType)) {
            return rsaCheckContent(content, sign, publicKey, charset);
        } else if (AlipayConstants.SIGN_TYPE_RSA2.equals(signType)) {
            return rsa256CheckContent(content, sign, publicKey, charset);
        } else {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE_TYPE);
        }

    }

    /**
     * RSA加密验证V1版本
     *
     * @param params    参数
     * @param publicKey 公钥
     * @param charset   字符类型 UTF-8
     * @return boolean
     */
    public static boolean rsaCheckV1(Map<String, String> params, String publicKey, String charset) {
        String sign = params.get("sign");
        String content = getSignCheckContentV1(params);

        return rsaCheckContent(content, sign, publicKey, charset);
    }

    /**
     * RSA加密验证V1版本
     *
     * @param params    参数
     * @param publicKey 公钥
     * @param charset   字符类型
     * @param signType  签名类型
     * @return boolean
     */
    public static boolean rsaCheckV1(Map<String, String> params, String publicKey,
                                     String charset, String signType) {
        String sign = params.get("sign");
        String content = getSignCheckContentV1(params);

        return rsaCheck(content, sign, publicKey, charset, signType);
    }

    /**
     * RSA 签名认证 v2版本
     *
     * @param params    参数
     * @param publicKey 公钥
     * @param charset   字符类型
     * @return boolean
     */
    public static boolean rsaCheckV2(Map<String, String> params, String publicKey,
                                     String charset) {
        String sign = params.get("sign");
        String content = getSignCheckContentV2(params);

        return rsaCheckContent(content, sign, publicKey, charset);
    }

    /**
     * RSA 签名认证 v2版本
     *
     * @param params    参数
     * @param publicKey 公钥
     * @param charset   字符类型
     * @param signType  加密类型
     * @return boolean
     */
    public static boolean rsaCheckV2(Map<String, ?> params, String publicKey,
                                     String charset, String signType) {
        String sign = String.valueOf(params.get("sign"));
        String content = getSignCheckContentV2(params);

        return rsaCheck(content, sign, publicKey, charset, signType);
    }

    /**
     * RSA256内容校验
     *
     * @param content   加密参数
     * @param sign      加密数据
     * @param publicKey 公钥
     * @param charset   字符类型
     * @return
     */
    public static boolean rsa256CheckContent(String content, String sign, String publicKey,
                                             String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA",
                    new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(AlipayConstants.SIGN_SHA256RSA_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE, e);
        }
    }

    /**
     * RSA内容校验
     *
     * @param content   加密参数
     * @param sign      加密数据
     * @param publicKey 公钥
     * @param charset   字符串类型
     * @return
     */
    public static boolean rsaCheckContent(String content, String sign, String publicKey,
                                          String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA",
                    new ByteArrayInputStream(publicKey.getBytes()));
            java.security.Signature signature = java.security.Signature
                    .getInstance(AlipayConstants.SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE, e);
        }
    }

    /**
     * 获取公钥
     *
     * @param signType 加密类型
     * @param ins      byte
     * @return publicKey
     */
    public static PublicKey getPublicKeyFromX509(String signType, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(signType);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    /**
     * 验签并解密
     * <p>
     * <b>目前适用于公众号</b><br>
     * params参数示例：
     * <br>{
     * <br>biz_content=M0qGiGz+8kIpxe8aF4geWJdBn0aBTuJRQItLHo9R7o5JGhpic/MIUjvXo2BLB++BbkSq2OsJCEQFDZ0zK5AJYwvBgeRX30gvEj6eXqXRt16/IkB9HzAccEqKmRHrZJ7PjQWE0KfvDAHsJqFIeMvEYk1Zei2QkwSQPlso7K0oheo/iT+HYE8aTATnkqD/ByD9iNDtGg38pCa2xnnns63abKsKoV8h0DfHWgPH62urGY7Pye3r9FCOXA2Ykm8X4/Bl1bWFN/PFCEJHWe/HXj8KJKjWMO6ttsoV0xRGfeyUO8agu6t587Dl5ux5zD/s8Lbg5QXygaOwo3Fz1G8EqmGhi4+soEIQb8DBYanQOS3X+m46tVqBGMw8Oe+hsyIMpsjwF4HaPKMr37zpW3fe7xOMuimbZ0wq53YP/jhQv6XWodjT3mL0H5ACqcsSn727B5ztquzCPiwrqyjUHjJQQefFTzOse8snaWNQTUsQS7aLsHq0FveGpSBYORyA90qPdiTjXIkVP7mAiYiAIWW9pCEC7F3XtViKTZ8FRMM9ySicfuAlf3jtap6v2KPMtQv70X+hlmzO/IXB6W0Ep8DovkF5rB4r/BJYJLw/6AS0LZM9w5JfnAZhfGM2rKzpfNsgpOgEZS1WleG4I2hoQC0nxg9IcP0Hs+nWIPkEUcYNaiXqeBc=,
     * <br>sign=rlqgA8O+RzHBVYLyHmrbODVSANWPXf3pSrr82OCO/bm3upZiXSYrX5fZr6UBmG6BZRAydEyTIguEW6VRuAKjnaO/sOiR9BsSrOdXbD5Rhos/Xt7/mGUWbTOt/F+3W0/XLuDNmuYg1yIC/6hzkg44kgtdSTsQbOC9gWM7ayB4J4c=,
     * sign_type=RSA,
     * <br>charset=UTF-8
     * <br>}
     * </p>
     *
     * @param params
     * @param alipayPublicKey 支付宝公钥
     * @param cusPrivateKey   商户私钥
     * @param isCheckSign     是否验签
     * @param isDecrypt       是否解密
     * @return 解密后明文，验签失败则异常抛出
     */
    public static String checkSignAndDecrypt(Map<String, String> params, String alipayPublicKey, String cusPrivateKey, boolean isCheckSign, boolean isDecrypt) {
        String charset = params.get("charset");
        String bizContent = params.get("biz_content");
        if (isCheckSign) {
            if (!rsaCheckV2(params, alipayPublicKey, charset)) {
                throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE);
            }
        }

        if (isDecrypt) {
            return rsaDecrypt(bizContent, cusPrivateKey, charset);
        }

        return bizContent;
    }

    /**
     * 验签并解密
     * <p>
     * <b>目前适用于公众号</b><br>
     * params参数示例：
     * <br>{
     * <br>biz_content=M0qGiGz+8kIpxe8aF4geWJdBn0aBTuJRQItLHo9R7o5JGhpic/MIUjvXo2BLB++BbkSq2OsJCEQFDZ0zK5AJYwvBgeRX30gvEj6eXqXRt16/IkB9HzAccEqKmRHrZJ7PjQWE0KfvDAHsJqFIeMvEYk1Zei2QkwSQPlso7K0oheo/iT+HYE8aTATnkqD/ByD9iNDtGg38pCa2xnnns63abKsKoV8h0DfHWgPH62urGY7Pye3r9FCOXA2Ykm8X4/Bl1bWFN/PFCEJHWe/HXj8KJKjWMO6ttsoV0xRGfeyUO8agu6t587Dl5ux5zD/s8Lbg5QXygaOwo3Fz1G8EqmGhi4+soEIQb8DBYanQOS3X+m46tVqBGMw8Oe+hsyIMpsjwF4HaPKMr37zpW3fe7xOMuimbZ0wq53YP/jhQv6XWodjT3mL0H5ACqcsSn727B5ztquzCPiwrqyjUHjJQQefFTzOse8snaWNQTUsQS7aLsHq0FveGpSBYORyA90qPdiTjXIkVP7mAiYiAIWW9pCEC7F3XtViKTZ8FRMM9ySicfuAlf3jtap6v2KPMtQv70X+hlmzO/IXB6W0Ep8DovkF5rB4r/BJYJLw/6AS0LZM9w5JfnAZhfGM2rKzpfNsgpOgEZS1WleG4I2hoQC0nxg9IcP0Hs+nWIPkEUcYNaiXqeBc=,
     * <br>sign=rlqgA8O+RzHBVYLyHmrbODVSANWPXf3pSrr82OCO/bm3upZiXSYrX5fZr6UBmG6BZRAydEyTIguEW6VRuAKjnaO/sOiR9BsSrOdXbD5Rhos/Xt7/mGUWbTOt/F+3W0/XLuDNmuYg1yIC/6hzkg44kgtdSTsQbOC9gWM7ayB4J4c=,
     * sign_type=RSA,
     * <br>charset=UTF-8
     * <br>}
     * </p>
     *
     * @param params          参数
     * @param alipayPublicKey 支付宝公钥
     * @param cusPrivateKey   商户私钥
     * @param isCheckSign     是否验签
     * @param isDecrypt       是否解密
     * @return 解密后明文，验签失败则异常抛出
     */
    public static String checkSignAndDecrypt(Map<String, String> params, String alipayPublicKey,
                                             String cusPrivateKey, boolean isCheckSign,
                                             boolean isDecrypt, String signType) {
        String charset = params.get("charset");
        String bizContent = params.get("biz_content");
        if (isCheckSign) {
            if (!rsaCheckV2(params, alipayPublicKey, charset, signType)) {
                throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE);
            }
        }

        if (isDecrypt) {
            return rsaDecrypt(bizContent, cusPrivateKey, charset);
        }

        return bizContent;
    }

    /**
     * 加密并签名<br>
     * <b>目前适用于公众号</b>
     *
     * @param bizContent      待加密、签名内容
     * @param alipayPublicKey 支付宝公钥
     * @param cusPrivateKey   商户私钥
     * @param charset         字符集，如UTF-8, GBK, GB2312
     * @param isEncrypt       是否加密，true-加密  false-不加密
     * @param isSign          是否签名，true-签名  false-不签名
     * @return 加密、签名后xml内容字符串
     * <p>
     * 返回示例：
     * <alipay>
     * <response>密文</response>
     * <encryption_type>RSA</encryption_type>
     * <sign>sign</sign>
     * <sign_type>RSA</sign_type>
     * </alipay>
     * </p>
     */
    public static String encryptAndSign(String bizContent, String alipayPublicKey,
                                        String cusPrivateKey, String charset, boolean isEncrypt,
                                        boolean isSign) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(charset)) {
            charset = AlipayConstants.CHARSET_GBK;
        }
        sb.append("<?xml version=\"1.0\" encoding=\"").append(charset).append("\"?>");
        if (isEncrypt) {// 加密
            sb.append("<alipay>");
            String encrypted = rsaEncrypt(bizContent, alipayPublicKey, charset);
            sb.append("<response>").append(encrypted).append("</response>");
            sb.append("<encryption_type>RSA</encryption_type>");
            if (isSign) {
                String sign = rsaSign(encrypted, cusPrivateKey, charset);
                sb.append("<sign>").append(sign).append("</sign>");
                sb.append("<sign_type>RSA</sign_type>");
            }
            sb.append("</alipay>");
        } else if (isSign) {// 不加密，但需要签名
            sb.append("<alipay>");
            sb.append("<response>").append(bizContent).append("</response>");
            String sign = rsaSign(bizContent, cusPrivateKey, charset);
            sb.append("<sign>").append(sign).append("</sign>");
            sb.append("<sign_type>RSA</sign_type>");
            sb.append("</alipay>");
        } else {// 不加密，不加签
            sb.append(bizContent);
        }
        return sb.toString();
    }

    /**
     * 加密并签名<br>
     * <b>目前适用于公众号</b>
     *
     * @param bizContent      待加密、签名内容
     * @param alipayPublicKey 支付宝公钥
     * @param cusPrivateKey   商户私钥
     * @param charset         字符集，如UTF-8, GBK, GB2312
     * @param isEncrypt       是否加密，true-加密  false-不加密
     * @param isSign          是否签名，true-签名  false-不签名
     * @return 加密、签名后xml内容字符串
     * <p>
     * 返回示例：
     * <alipay>
     * <response>密文</response>
     * <encryption_type>RSA</encryption_type>
     * <sign>sign</sign>
     * <sign_type>RSA</sign_type>
     * </alipay>
     * </p>
     */
    public static String encryptAndSign(String bizContent, String alipayPublicKey,
                                        String cusPrivateKey, String charset, boolean isEncrypt,
                                        boolean isSign, String signType) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(charset)) {
            charset = AlipayConstants.CHARSET_GBK;
        }
        sb.append("<?xml version=\"1.0\" encoding=\"").append(charset).append("\"?>");
        if (isEncrypt) {// 加密
            sb.append("<alipay>");
            String encrypted = rsaEncrypt(bizContent, alipayPublicKey, charset);
            sb.append("<response>").append(encrypted).append("</response>");
            sb.append("<encryption_type>RSA</encryption_type>");
            if (isSign) {
                String sign = rsaSign(encrypted, cusPrivateKey, charset, signType);
                sb.append("<sign>").append(sign).append("</sign>");
                sb.append("<sign_type>");
                sb.append(signType);
                sb.append("</sign_type>");
            }
            sb.append("</alipay>");
        } else if (isSign) {// 不加密，但需要签名
            sb.append("<alipay>");
            sb.append("<response>").append(bizContent).append("</response>");
            String sign = rsaSign(bizContent, cusPrivateKey, charset, signType);
            sb.append("<sign>").append(sign).append("</sign>");
            sb.append("<sign_type>");
            sb.append(signType);
            sb.append("</sign_type>");
            sb.append("</alipay>");
        } else {// 不加密，不加签
            sb.append(bizContent);
        }
        return sb.toString();
    }

    /**
     * 公钥加密
     *
     * @param content   待加密内容
     * @param publicKey 公钥
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @return 密文内容
     */
    public static String rsaEncrypt(String content, String publicKey, String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509(AlipayConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(publicKey.getBytes()));
            Cipher cipher = Cipher.getInstance(AlipayConstants.SIGN_TYPE_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] data = StringUtils.isEmpty(charset) ? content.getBytes()
                    : content.getBytes(charset);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
            out.close();

            return StringUtils.isEmpty(charset) ? new String(encryptedData)
                    : new String(encryptedData, charset);
        } catch (Exception e) {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE, e);
        }
    }

    /**
     * 私钥解密
     *
     * @param content   待解密内容
     * @param publicKey 私钥
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @return 明文内容
     */
    public static String rsaDecrypt(String content, String publicKey,
                                    String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(AlipayConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(publicKey.getBytes()));
            Cipher cipher = Cipher.getInstance(AlipayConstants.SIGN_TYPE_RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] encryptedData = StringUtils.isEmpty(charset)
                    ? Base64.decodeBase64(content.getBytes())
                    : Base64.decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return StringUtils.isEmpty(charset) ? new String(decryptedData)
                    : new String(decryptedData, charset);
        } catch (Exception e) {
            throw new ApiException(ErrorEnum.ISV_INVALID_SIGNATURE, e);
        }
    }


}
