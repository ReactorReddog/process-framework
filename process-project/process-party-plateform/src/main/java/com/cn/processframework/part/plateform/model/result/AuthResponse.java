package com.cn.processframework.part.plateform.model.result;
import com.cn.processframework.part.plateform.constants.AuthResponseStatus;
import lombok.*;

import java.io.Serializable;

/**
 * @author apple
 * @desc 统一授权响应类
 * @since 1.0 21:13
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse<T> implements Serializable {
    /**
     * 授权响应状态码
     */
    private int code;

    /**
     * 授权响应信息
     */
    private String msg;

    /**
     * 授权响应数据，当且仅当 code = 2000 时返回
     */
    private T data;

    /**
     * 是否请求成功
     *
     * @return true or false
     */
    public boolean ok() {
        return this.code == AuthResponseStatus.SUCCESS.getCode();
    }
}
