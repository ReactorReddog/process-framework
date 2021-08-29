package org.processframework.doc.support.doc;

import org.processframework.doc.constants.EnvConfigEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author apple
 * @desc 环境配置
 */
@Data
public class EnvConfig implements Serializable {
    /**
     * 环境
     */
    private EnvConfigEnum env;
    /**
     * 环境描述
     */
    private String envName;
    /**
     * http请求地址
     */
    private String httpUrl;
    /**
     * https请求地址
     */
    private String httpsUrl;
}
