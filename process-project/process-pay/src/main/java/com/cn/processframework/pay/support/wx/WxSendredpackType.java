package com.cn.processframework.pay.support.wx;

import com.cn.processframework.pay.TransferOrder;
import com.cn.processframework.pay.TransferType;

import java.util.Map;

/**
 * @author apple 
 * @desc 微信红包交易类型
 * @since 1.0 22:00
 */
public enum WxSendredpackType  implements TransferType {
    /**
     * 现金红包-发放红包接口
     */
    SENDREDPACK("mmpaymkttransfers/sendredpack"),
    /**
     * 现金红包-发放裂变红包
     */
    SENDGROUPREDPACK("mmpaymkttransfers/sendgroupredpack"),
    /**
     * 现金红包-查询红包记录
     */
    GETHBINFO ("mmpaymkttransfers/gethbinfo"),
    /**
     * 小程序
     */
    SENDMINIPROGRAMHB ("mmpaymkttransfers/sendminiprogramhb")

    ;

    WxSendredpackType(String method) {
        this.method = method;
    }
    private String method;

    @Override
    public String getType() {
        return this.name();
    }
    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public Map<String, Object> setAttr(Map<String, Object> attr, TransferOrder order) {
        return attr;
    }
}
