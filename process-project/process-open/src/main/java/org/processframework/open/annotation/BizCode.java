package org.processframework.open.annotation;

import java.lang.annotation.*;

/**
 * @author apple
 * @desc 错误码实体
 * @since 1.0.0.RELEASE
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizCode {
    /**
     * 错误码
     * @return String
     */
    String code();

    /**
     * 错误消息
     * @return String
     */
    String msg();

    /**
     * 错误描述
     * @return String
     */
    String solution();
}
