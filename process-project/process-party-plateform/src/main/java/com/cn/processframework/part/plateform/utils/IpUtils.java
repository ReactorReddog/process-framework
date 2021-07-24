package com.cn.processframework.part.plateform.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author apple
 * @desc ip获取工具
 * @since 1.0 22:52
 */
public class IpUtils {
    /**
     * 获取IP
     *
     * @return ip
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
