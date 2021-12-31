package com.cn.processframework.pay.support.wx;

import java.util.Date;
import java.util.Map;

/**
 * @author apple 
 * @desc weixin账单接口
 * @since 1.0 22:03
 */
public interface WxBillService {

    @Deprecated
    Map<String, Object> downloadbill(Date billDate, String billType, String path);

}