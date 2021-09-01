package org.processframework.open.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author apple
 * @desc 环境变量
 * @since 1.0.0.RELEASE
 */
@AllArgsConstructor
@Getter
public enum EnvConfigEnum implements BaseEnum{
    PROD("正式环境"),DEV("测试环境")
    ;

    private String name;
    @Override
    public String toChinese() {
        return this.getName();
    }
}
