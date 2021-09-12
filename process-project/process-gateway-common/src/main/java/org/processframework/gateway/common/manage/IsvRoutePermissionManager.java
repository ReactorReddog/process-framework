package org.processframework.gateway.common.manage;


import org.processframework.gateway.common.core.IsvRoutePermission;
import org.processframework.gateway.common.manage.initalizer.BeanInitializer;

/**
 * @author apple
 * @desc isv路由权限管理
 * @since 1.0.0.RELEASE
 */
public interface IsvRoutePermissionManager extends BeanInitializer {

    /**
     * 加载权限
     * @param isvRoutePermission isvRoutePermission
     */
    void update(IsvRoutePermission isvRoutePermission);

    /**
     * 判断是否有权限
     * @param appKey appKey
     * @param routeId 路由id
     * @return true：有
     */
    boolean hasPermission(String appKey, String routeId);

    /**
     * 删除权限
     * @param appKey appKey
     */
    void remove(String appKey);
}
