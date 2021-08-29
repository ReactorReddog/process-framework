package org.processframework.doc;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author apple
 * @package com.cn.processframework.doc
 * @desc <p>xml工具类</p>
 * @since 1.0.0.RELEASE
 */
public class XmlUtils {
    /**
     * 实例化当前对象
     * @return xmlutils
     */
    public static XmlUtils getInstance(){
        return new XmlUtils();
    }

    /**
     * 使用xmlmapper工具
     * @return xmlmapper
     */
    public XmlMapper xmlMapper(){
        return new XmlMapper();
    }
}
