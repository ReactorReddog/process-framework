package org.processframework.gateway.common.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * @author apple
 */
@Getter
@Setter
@ToString
public class ErrorEntity {
    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 版本号
     */
    private String version;
    /**
     * 服务id
     */
    private String serviceId;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     *
     *
     */
    private long count;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorEntity that = (ErrorEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}