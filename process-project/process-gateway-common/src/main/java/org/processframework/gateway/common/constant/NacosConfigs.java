package org.processframework.gateway.common.constant;

/**
 * @author apple
 * @desc nacos配置信息
 */
public class NacosConfigs {

    public static final String GROUP_CHANNEL = "process:channel";

    public static final String GROUP_ROUTE = "process:route";

    public static final String DATA_ID_GRAY = "com.cn.reddog.gateway.channel.gray";

    public static final String DATA_ID_IP_BLACKLIST = "com.cn.reddog.gateway.channel.ipblacklist";

    public static final String DATA_ID_ISV = "com.cn.reddog.gateway.channel.isv";

    public static final String DATA_ID_ROUTE_PERMISSION = "ccom.cn.reddog.gateway.channel.routepermission";

    public static final String DATA_ID_LIMIT_CONFIG = "com.cn.reddog.gateway.channel.limitconfig";

    public static final String DATA_ID_ROUTE_CONFIG = "com.cn.reddog.gateway.channel.routeconfig";

    private static final String DATA_ID_TPL = "com.cn.reddog.gateway.route.%s";

    public static String getRouteDataId(String serviceId) {
        return String.format(DATA_ID_TPL, serviceId);
    }
}
