package org.processframework.gateway.common.manage.initalizer;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author apple
 * @desc 信息
 * @since 1.0.0.RELEASE
 */
@Data
public class ChannelMsg {
    private String operation;
    private JSONObject data;

    public <T> T toObject(Class<T> clazz) {
        return data.toJavaObject(clazz);
    }
}
