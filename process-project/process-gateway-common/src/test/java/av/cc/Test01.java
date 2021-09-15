package av.cc;

import org.junit.jupiter.api.Test;
import org.processframework.gateway.common.ApiException;
import org.processframework.gateway.common.ErrorEnum;

import java.util.Locale;


public class Test01 {
    @Test
    public void ss(){
//        throw new ApiException(ErrorEnum.AOP_UNKNOWN_ERROR);
        new ApiException(ErrorEnum.ISV_METHOD_CALL_LIMITED.getErrorMeta());
    }
}
