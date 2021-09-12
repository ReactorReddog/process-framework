package org.processframework.gateway.common.manage.base;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.processframework.gateway.common.core.ConfigLimitDto;
import org.processframework.gateway.common.core.LimitType;
import org.processframework.gateway.common.manage.LimitManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author apple
 */
@Slf4j
public class DefaultLimitManager implements LimitManager {

    @Override
    public double acquireToken(ConfigLimitDto routeConfig) {
        if (routeConfig.getLimitStatus() == ConfigLimitDto.LIMIT_STATUS_CLOSE) {
            return 0;
        }
        if (LimitType.LEAKY_BUCKET.getType() == routeConfig.getLimitType().byteValue()) {
            return 0;
        }
        return routeConfig.fetchRateLimiter().acquire();
    }


    @Override
    public boolean acquire(ConfigLimitDto routeConfig) {
        if (routeConfig.getLimitStatus() == ConfigLimitDto.LIMIT_STATUS_CLOSE) {
            return true;
        }
        if (LimitType.TOKEN_BUCKET.getType() == routeConfig.getLimitType()) {
            return true;
        }
        int execCountPerSecond = routeConfig.getExecCountPerSecond();
        try {
            LoadingCache<Long, AtomicLong> counter = routeConfig.getCounter();
            // 被限流了
            return counter.get(routeConfig.getId()).incrementAndGet() <= execCountPerSecond;
        } catch (ExecutionException e) {
            log.error("窗口限流出错，routeConfig:{}", routeConfig, e);
            return false;
        }
    }

}
