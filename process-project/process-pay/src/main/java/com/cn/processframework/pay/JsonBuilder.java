package com.cn.processframework.pay;

import com.alibaba.fastjson.JSONObject;

/**
 * @author apple
 * @desc 支付回调json渠道组装类
 * @since 1.0 14:07
 */
public class JsonBuilder extends BaseBuilder<JsonBuilder, PayOutMessage> {
    JSONObject json = null;

    public JsonBuilder(JSONObject json) {
        this.json = json;
    }

    public JsonBuilder content(String key, Object content) {
        this.json.put(key, content);
        return this;
    }

    public JSONObject getJson() {
        return json;
    }

    @Override
    public PayOutMessage build() {
        PayJsonOutMessage message = new PayJsonOutMessage();
        setCommon(message);
        message.setContent(json.toJSONString());
        return message;
    }
}
