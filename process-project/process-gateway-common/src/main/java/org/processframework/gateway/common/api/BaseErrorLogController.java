package org.processframework.gateway.common.api;

import org.processframework.gateway.common.ApiParam;
import org.processframework.gateway.common.core.ApiConfig;
import org.processframework.gateway.common.core.ErrorEntity;
import org.processframework.gateway.common.excutor.ApiResult;
import org.processframework.gateway.common.excutor.JsonResult;
import org.processframework.gateway.common.manage.ServiceErrorManager;
import org.processframework.gateway.common.validate.taobao.TaobaoSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

/**
 * @author apple
 */
public abstract class BaseErrorLogController<T> {

    TaobaoSigner signer = new TaobaoSigner();

    @Value("${reddog.secret}")
    private String secret;

    protected abstract ApiParam getApiParam(T t);

    @GetMapping("/process/listErrors")
    public ApiResult listErrors(T request) {
        try {
            this.check(request);
            ServiceErrorManager serviceErrorManager = ApiConfig.getInstance().getServiceErrorManager();
            Collection<ErrorEntity> allErrors = serviceErrorManager.listAllErrors();
            JsonResult apiResult = new JsonResult();
            apiResult.setData(allErrors);
            return apiResult;
        } catch (Exception e) {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode("9377689");
            apiResult.setMsg(e.getMessage());
            return apiResult;
        }
    }

    @GetMapping("/process/clearErrors")
    public ApiResult clearErrors(T request) {
        try {
            this.check(request);
            ServiceErrorManager serviceErrorManager = ApiConfig.getInstance().getServiceErrorManager();
            serviceErrorManager.clear();
            return new ApiResult();
        } catch (Exception e) {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode("9377689");
            apiResult.setMsg(e.getMessage());
            return apiResult;
        }
    }

    protected void check(T request) {
        ApiParam apiParam = getApiParam(request);
        boolean right = signer.checkSign(apiParam, secret);
        if (!right) {
            throw new RuntimeException("签名校验失败");
        }
    }
}
