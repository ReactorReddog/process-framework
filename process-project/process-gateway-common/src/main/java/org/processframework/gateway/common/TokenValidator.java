package org.processframework.gateway.common;

/**
 * @author apple
 * @desc token验证工具
 */
@FunctionalInterface
public interface TokenValidator {
    /**
     * token验证
     * @param apiParam 参数
     * @return
     */
    boolean validateToken(ApiParam apiParam);
}