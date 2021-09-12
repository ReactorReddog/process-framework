package org.processframework.gateway.common.manage;

import java.io.Serializable;

/**
 * @author apple
 * @desc isv信息
 * @since 1.0.0.RELEASE
 */
public interface Isv extends Serializable {

    /**
     * appKey
     * @return 返回appKey
     */
    String getAppKey();

    /**
     * 秘钥
     * @return 返回秘钥
     */
    String getSecretInfo();

    /**
     * 获取平台的私钥
     * @return 返回私钥
     */
    String getPrivateKeyPlatform();

    /**
     * 0启用，1禁用
     * @return 返回状态
     */
    Byte getStatus();
}
