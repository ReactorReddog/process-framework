package org.processframework.gateway.common.excutor;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author apple
 * @desc 返回固定的数据
 * {
 *     "result": {
 *         "code": "20000",
 *         "msg": "Service Currently Unavailable",
 *         "sub_code": "isp.unknown-error",
 *         "sub_msg": "系统繁忙"
 *     },
 *     "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
 * }
 * @since 1.0.0.RELEASE
 */
@Data
@NoArgsConstructor
public class CustomDataNameBuilder implements DataNameBuilder{
    private String dataName = "result";
    @Override
    public String build(String method) {
        return dataName;
    }
}
