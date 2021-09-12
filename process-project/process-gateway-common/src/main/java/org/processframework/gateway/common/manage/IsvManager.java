package org.processframework.gateway.common.manage;

import org.processframework.gateway.common.manage.initalizer.BeanInitializer;

/**
 * Isv管理
 *
 * @author apple
 */
public interface IsvManager<T extends Isv> extends BeanInitializer {

    /**
     * 更新isv
     *
     * @param t isv
     */
    void update(T t);

    /**
     * 删除isv
     *
     * @param appKey isv对应的appKey
     */
    void remove(String appKey);

    /**
     * 获取isv
     *
     * @param appKey isv对应的key
     * @return 返回isv
     */
    T getIsv(String appKey);

}
