package org.processframework.gateway.common.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.processframework.gateway.common.manage.initalizer.ChannelMsg;

/**
 * @author apple
 * @desc 网关推送信息
 * @since 1.0.0.RELEASE
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayPushDTO {
    private String dataId;
    private String groupId;
    private ChannelMsg channelMsg;
}
