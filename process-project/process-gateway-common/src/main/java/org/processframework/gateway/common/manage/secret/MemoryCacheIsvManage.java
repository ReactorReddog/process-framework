package org.processframework.gateway.common.manage.secret;

import org.processframework.gateway.common.core.IsvDefinition;
import org.processframework.gateway.common.manage.Isv;
import org.processframework.gateway.common.manage.IsvManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author apple
 * @desc 本地内存存储ISV
 * @since 1.0.0.RELEASE
 */
public class MemoryCacheIsvManage implements IsvManager<IsvDefinition> {
    /**
     * key: appKey
     */
    private final Map<String, IsvDefinition> isvCache = new ConcurrentHashMap<>(64);
    @Override
    public void load() {

    }


    @Override
    public void update(IsvDefinition isvInfo) {
        isvCache.put(isvInfo.getAppKey(), isvInfo);
    }

    @Override
    public void remove(String appKey) {
        isvCache.remove(appKey);
    }

    @Override
    public IsvDefinition getIsv(String appKey) {
        return isvCache.get(appKey);
    }

    public Map<String, IsvDefinition> getIsvCache() {
        return isvCache;
    }
}
