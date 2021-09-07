package org.processframework.gateway.common;

import java.util.Locale;
import java.util.Objects;

/**
 * @author apple
 * @desc 异常类
 * @since 1.0.0.RELEASE
 */
public class ApiException extends RuntimeException{
    private transient Error error;

    private transient ErrorMeta errorMeta;

    private Object[] params;

    public ApiException(ErrorMeta errorMeta,Object... params){
        this.errorMeta = errorMeta;
        this.params = params;
    }

    public ApiException(Throwable cause,ErrorMeta errorMeta,Object...params){
        super(cause);
        this.errorMeta = errorMeta;
        this.params = params;
    }

    public Error getError(Locale locale){
        return Objects.isNull(error)?ErrorFactory.getError(errorMeta,locale,params):error;
    }
    @Override
    public String getMessage(){
        String message = super.getMessage();
        return Objects.isNull(message)?errorMeta.toString():message;
    }
}
