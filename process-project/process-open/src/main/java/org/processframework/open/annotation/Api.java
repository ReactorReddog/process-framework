package org.processframework.open.annotation;

import java.lang.annotation.*;

/**
 * @author apple
 * @desc 请求类接口 接口层级
 * @since 1.0.0.RELEASE
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {
    /**
     * 中文名展示
     * @return
     */
    String apiChineseName();

    /**
     * 英文名展示
     * @return
     */
    String apiEnName();
}
