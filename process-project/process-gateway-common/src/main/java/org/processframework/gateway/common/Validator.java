package org.processframework.gateway.common;

/**
 * 校验接口
 * 
 * @author apple
 *
 */
public interface Validator {
    /**
     * 接口验证
     * @param param 接口参数
     */
    void validate(ApiParam param);
    
}