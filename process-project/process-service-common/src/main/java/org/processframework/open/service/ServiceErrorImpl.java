package org.processframework.open.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author apple
 * @desc 错误实现
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceErrorImpl implements ServiceError{
    private String sub_code;
    private String sub_msg;
}
