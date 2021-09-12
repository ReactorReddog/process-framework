package org.processframework.gateway.common.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author apple
 * @desc 服务holder类
 * @since 1.0.0.RELEASE
 */
@Getter
@Setter
@AllArgsConstructor
public class ServiceHolder {
    private String serviceId;
    private long lastUpdatedTimestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceHolder that = (ServiceHolder) o;
        return lastUpdatedTimestamp == that.lastUpdatedTimestamp &&
                serviceId.equals(that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, lastUpdatedTimestamp);
    }
}