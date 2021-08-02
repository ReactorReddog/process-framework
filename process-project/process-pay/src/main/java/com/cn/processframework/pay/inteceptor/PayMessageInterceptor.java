package com.cn.processframework.pay.inteceptor;

import com.cn.processframework.pay.PayMessage;
import com.cn.processframework.pay.PayService;
import com.cn.processframework.pay.exception.PayErrorException;

import java.util.Map;

/**
 * @author apple
 * @desc 支付消息拦截器，可以用来做验证等等
 *  此工具可用可不用 开发者可以根据实际情况进行使用 反正爱怎么玩你就怎么玩
 * @since 1.0 18:43
 */
public interface PayMessageInterceptor<M extends PayMessage, S extends PayService> {
    /**
     * 拦截微信消息
     *
     * @param payMessage 支付消息
     * @param context    上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param payService 支付服务
     * @return true代表OK，false代表不OK
     */
    boolean intercept(M payMessage,
                      Map<String, Object> context,
                      S payService
    ) throws PayErrorException;
}
