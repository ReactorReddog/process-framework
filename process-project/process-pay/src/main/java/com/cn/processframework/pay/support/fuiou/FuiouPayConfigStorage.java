package com.cn.processframework.pay.support.fuiou;

import com.cn.processframework.pay.BasePayConfigStorage;

/**
 * @author apple 
 * @desc 富友支付配置
 * @since 1.0 21:04
 */
public class FuiouPayConfigStorage extends BasePayConfigStorage {
    /**
     * 商户代码
     */
    private String mchntCd;

    /**
     * 应用id
     *
     * @return 空
     */
    @Override
    public String getAppid() {
        return null;
    }

    /**
     * 应用id
     * 纠正名称
     *
     * @return 应用id
     */
    @Override
    public String getAppId() {
        return null;
    }


    /**
     * 合作商唯一标识
     */
    @Override
    public String getPid() {
        return mchntCd;
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    @Override
    public String getSeller() {
        return null;
    }

}
