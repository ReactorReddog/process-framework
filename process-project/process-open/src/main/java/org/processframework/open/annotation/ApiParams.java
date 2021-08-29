package org.processframework.open.annotation;

import java.util.List;

/**
 * @author apple
 * @desc api接口请求参数
 * @since 1.0.0.RELEASE
 */
public @interface ApiParams {
    /**
     * 参数名称
     * @return
     */
    String name() default "";

    /**
     * 当前参数类型
     * @return
     */
    String type() default "String";

    /**
     * 是否必填
     * @return
     */
    boolean required() default false;

    /**
     * 参数描述
     * @return
     */
    String description() default "";

    /**
     * 子类参数
     * @return
     */
    ApiOpration[] bizApiOprations() default {};
}
