package org.processframework.gateway.common.excutor;

/**
 * @author apple
 * @desc 点转下划线 将方法名中的"."转成"_"并在后面追加"_response"
 * @since 1.0.0.RELEASE
 */
public interface DataNameBuilder {
    /**
     * 构建数据节点名称
     * @param method method
     * @return String
     */
    String build(String method);
}
