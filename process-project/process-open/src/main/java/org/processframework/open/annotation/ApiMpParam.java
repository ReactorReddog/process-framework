package org.processframework.open.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * @author apple
 * @desc api接口请求参数 和ApiMpParams一起用
 * @since 1.0.0.RELEASE
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMpParam {
    /**
     * 参数名称
     * @return
     */
    String name() default "";

    /**
     * 参数字段
     * @return
     */
    String value() default "";

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

}
