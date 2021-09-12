package org.processframework.gateway.common.excutor;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author apple
 * @desc json返回结果
 * @since 1.0.0.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JsonResult extends ApiResult {
    private Object data;
}
