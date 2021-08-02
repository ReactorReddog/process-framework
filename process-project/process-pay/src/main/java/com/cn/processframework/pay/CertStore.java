package com.cn.processframework.pay;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author apple
 * @package com.cn.processframework.pay
 * @desc <p>证书存储类型</p>
 * @since
 */
public interface CertStore {
    /**
     * 证书信息转化为对应的输入流
     *
     * @param cert 证书信息
     * @return 输入流
     * @throws IOException 找不到文件异常
     */
    InputStream getInputStream(Object cert) throws IOException;
}
