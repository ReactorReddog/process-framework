package org.processframework.gateway.common.core;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author apple
 * @desc isv授权过的路由
 */
@Data
public class IsvRoutePermission {
    /**
     * appKey
     */
    private String appKey;
    /**
     * 路由id
     */
    private List<String> routeIdList = Collections.emptyList();
    /**
     * 路由md5
     */
    private String routeIdListMd5;
    /**
     * 监听地址
     */
    private String listenPath;

}
