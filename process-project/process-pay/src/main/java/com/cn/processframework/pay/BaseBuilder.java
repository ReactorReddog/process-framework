package com.cn.processframework.pay;
/**
 * @author apple
 * @desc 支付回调基础builder类
 * @since 1.0 14:04
 */
public abstract class BaseBuilder<BuilderType, ValueType> {
    public abstract ValueType build();

    public void setCommon(PayOutMessage m) {

    }
}
