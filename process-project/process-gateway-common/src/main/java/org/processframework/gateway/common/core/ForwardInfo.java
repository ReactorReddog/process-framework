package org.processframework.gateway.common.core;

import lombok.Data;

import static org.processframework.gateway.common.constant.GatewayConstant.VALIDATE_ERROR_PATH;

/**
 * @author apple
 */
@Data
public class ForwardInfo {
    /**
     * 路由
     */
    private TargetRoute targetRoute;

    public static ForwardInfo getErrorForwardInfo() {
        return ErrorForwardInfo.errorForwardInfo;
    }

    public ForwardInfo(TargetRoute targetRoute) {
        this.targetRoute = targetRoute;
    }

    public String getFullPath() {
        return targetRoute.getFullPath();
    }

    public String getPath() {
        return targetRoute.getRouteDefinition().getPath();
    }

    public String getVersion() {
        return targetRoute.getRouteDefinition().getVersion();
    }

    static class ErrorForwardInfo extends ForwardInfo {

        public static ErrorForwardInfo errorForwardInfo = new ErrorForwardInfo();

        public ErrorForwardInfo() {
            this(null);
        }

        public ErrorForwardInfo(TargetRoute targetRoute) {
            super(targetRoute);
        }

        @Override
        public String getFullPath() {
            return getPath();
        }

        @Override
        public String getPath() {
            return VALIDATE_ERROR_PATH;
        }

        @Override
        public String getVersion() {
            return "";
        }
    }

}
