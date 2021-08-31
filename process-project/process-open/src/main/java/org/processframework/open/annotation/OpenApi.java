package org.processframework.open.annotation;

import java.lang.annotation.*;

/**
 * @author apple
 * @desc 开放api 接口层级
 * @since 1.0.0.RELEASE
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenApi {
    /**
     * 接口名,例如:taobao.user.buyer.get
     * @return String
     */
    String methodValue();

    /**
     * 版本号控制 如:v2.0.0
     * @return String
     */
    String version() default "v2.0";

    /**
     * 忽略验证 业务参数除外 默认是验证
     * @return boolean
     */
    boolean ignoreValidate() default false;

    /**
     * 告诉网关是否需要对结果进行合并 默认为合并 客户端将直接收到网关封装的结果
     * @return boolean
     */
    boolean mergeResult() default true;

    /**
     * 指定接口是否需要授权才能访问 可在管理端进行修改
     * @return boolean
     */
    boolean permission() default false;

    /**
     * 是否需要appAuthToken 设置为true 网关会先校验其是否存在
     * @return boolean
     */
    boolean needToken() default false;

    /**
     * 定义业务错误码 用于文档显示
     * @return boolean
     */
    BizCode [] bizCode() default{};
}
