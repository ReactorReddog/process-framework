package com.cn.processframework.doc.test;

import com.cn.processframework.doc.XmlUtils;
import com.cn.processframework.doc.support.doc.DocInfoDate;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

public class Test01 {
    @Test
    public  void xx() throws IOException {
        DocInfoDate docInfoDate = new DocInfoDate();
        StringWriter s=new StringWriter();
        String b = new String();
        XmlUtils.getInstance().xmlMapper().writeValue(s,docInfoDate);
        b = s.toString();
        System.out.println(b);
    }
}
