package ddd.bb;

import org.junit.jupiter.api.Test;
import org.processframework.open.service.ServiceError;
import org.processframework.open.service.ServiceErrorEnum;
import org.processframework.open.service.ServiceException;

public class Test01 {
    @Test
    public void ss(){
//        throw new RuntimeException("牛逼");
        throw ServiceErrorEnum.ISP_UNKNOW_ERROR.getErrorMeta().getException();
    }
}
