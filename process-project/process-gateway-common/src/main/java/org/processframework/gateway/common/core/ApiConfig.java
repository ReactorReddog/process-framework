package org.processframework.gateway.common.core;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.processframework.gateway.common.TokenValidator;

/**
 * @author apple
 * @desc 核心引用
 * @since 1.0.0.RELEASE
 */
@Data
public class ApiConfig {
    /**
     * 校验token
     */
    private TokenValidator tokenValidator = apiParam -> apiParam != null && StringUtils.isNotBlank(apiParam.fetchAccessToken());
}
