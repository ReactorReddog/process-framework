package org.processframework.gateway.common.manage;

import org.apache.commons.codec.digest.DigestUtils;
import org.processframework.gateway.common.core.ApiConfig;
import org.processframework.gateway.common.core.ErrorDefinition;
import org.processframework.gateway.common.core.ErrorEntity;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author apple
 */
public class DefaultServiceErrorManager implements ServiceErrorManager {

    private static Map<String, ErrorEntity> store = new ConcurrentHashMap<>(128);

    @Override
    public Collection<ErrorEntity> listAllErrors() {
        return store.values();
    }

    /**
     * 记录业务错误信息
     * @param errorDefinition 错误信息实体
     */
    @Override
    public void saveBizError(ErrorDefinition errorDefinition) {

    }

    /**
     * 记录未知错误信息
     * @param errorDefinition 错误信息实例
     */
    @Override
    public void saveUnknownError(ErrorDefinition errorDefinition) {
        //判断容量是否上限
        boolean hasCapacity = store.size() < ApiConfig.getInstance().getGatewayProperties().getStoreErrorCapacity();
        // 这里还可以做其它事情，比如错误量到达一定数目后，自动发送邮件/微信给开发人员，方便及时获取异常情况
        String id = this.buildId(errorDefinition);
        ErrorEntity errorEntity = store.get(id);
        if (errorEntity == null && hasCapacity) {
            errorEntity = new ErrorEntity();
            BeanUtils.copyProperties(errorDefinition, errorEntity);
            errorEntity.setId(id);
            store.put(id, errorEntity);
        }
        if (errorEntity != null) {
            errorEntity.setCount(errorEntity.getCount() + 1);
        }
    }

    @Override
    public void clear() {
        store.clear();
    }

    protected String buildId(ErrorDefinition errorDefinition) {
        return DigestUtils.md5Hex(errorDefinition.getServiceId() + errorDefinition.getErrorMsg());
    }

}
