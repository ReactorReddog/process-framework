package org.processframework.open.annotation;

/**
 * @author apple
 * @desc 请求类接口最顶层
 * @since 1.0.0.RELEASE
 */
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
