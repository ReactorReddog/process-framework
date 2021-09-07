package org.processframework.gateway.common;

import lombok.Getter;

import java.util.Locale;

import static org.processframework.gateway.common.ErrorFactory.UNDERLINE;

/**
 * @author apple
 * @desc 错误对象
 * @since 1.0.0.RELEASE
 */
@Getter
public class ErrorMeta {
    private final String modulePrefix;
    private final String code;
    private final String subCode;

    public ErrorMeta(String modulePrefix,String code,String subCode){
        this.modulePrefix = modulePrefix;
        this.code = code;
        this.subCode = subCode;
    }

    /**
     * 获取语言错误解析处理
     * @param locale 语言 如:简体中文{@link Locale#CHINA} 英语{@link Locale#ENGLISH}
     * @return
     */
    public Error getError(Locale locale){
        return ErrorFactory.getError(this,locale);
    }

    /**
     * 返回网关exception
     * @param params 参数
     * @return 返回异常
     */
    public ApiException getException(Object...params){
        if (params!=null && params.length==1){
            Object param = params[0];
            if (param instanceof Throwable){
                return new ApiException((Throwable) param,this);
            }
        }
        return new ApiException(this,params);
    }

    @Override
    public String toString(){
        return String.join("",modulePrefix,code,UNDERLINE,subCode);
    }
}
