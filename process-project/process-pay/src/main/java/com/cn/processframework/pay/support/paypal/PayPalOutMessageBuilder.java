package com.cn.processframework.pay.support.paypal;

import com.cn.processframework.pay.TextBuilder;

import java.util.Map;

/**
 * @author apple
 * @desc
 * @since 1.0 21:31
 */
public class PayPalOutMessageBuilder extends TextBuilder {


    public PayPalOutMessageBuilder(Map<String, Object> message) {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, Object> entry : message.entrySet()) {
            out.append(entry.getKey()).append('=').append(entry.getValue()).append("<br>");
        }
        super.content(out.toString());
    }


}