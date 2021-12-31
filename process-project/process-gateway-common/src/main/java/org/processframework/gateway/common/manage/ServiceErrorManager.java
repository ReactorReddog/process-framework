package org.processframework.gateway.common.manage;

import org.processframework.gateway.common.core.ErrorDefinition;
import org.processframework.gateway.common.core.ErrorEntity;

import java.util.Collection;

/**
 * @author apple 
 * @desc 服务错误信息管理
 * @since 1.0.0.RELEASE
 */
public interface ServiceErrorManager {

    /**
     * 保存业务错误，一般由开发人员自己throw的异常
     * @param errorDefinition 错误信息实例
     */
    void saveBizError(ErrorDefinition errorDefinition);

    /**
     * 保存未知的错误信息
     * @param errorDefinition 错误信息实例
     */
    void saveUnknownError(ErrorDefinition errorDefinition);

    /**
     * 清除日志
     */
    void clear();

    /**
     * 获取所有错误信息
     * @return
     */
    Collection<ErrorEntity> listAllErrors();
}
