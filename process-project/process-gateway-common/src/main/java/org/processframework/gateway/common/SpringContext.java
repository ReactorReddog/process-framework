package org.processframework.gateway.common;

import org.springframework.context.ApplicationContext;

/**
 * @author apple
 * @desc spring上下文工具
 * @since 1.0.0.RELEASE
 */
public class SpringContext {
    /**
     * 上下文
     */
    private static ApplicationContext ctx;

    /**
     * 根据类类型获取bean
     * @param clazz 获取的类
     * @param <T> 类对象
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    /**
     * 根据bean名获取bean
     * @param beanName 名
     * @return
     */
    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    /**
     * 设置applications
     * @param ctx 上下文
     */
    public static void setApplicationContext(ApplicationContext ctx) {
        SpringContext.ctx = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
