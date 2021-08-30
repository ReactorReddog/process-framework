package org.processframework.open.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author apple
 * @desc 请求参数多个
 * @since 1.0.0.RELEASE
 */
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMpParams {
    /**
     * 子级参数
     * @return
     */
    ApiMpParam[] params() default {};
}
