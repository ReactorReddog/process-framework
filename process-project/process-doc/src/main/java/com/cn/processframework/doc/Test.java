package com.cn.processframework.doc;

import com.cn.processframework.doc.support.doc.DocInfoDate;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.StringWriter;

public class Test {
    public static void main(String[] args) throws IOException {
        DocInfoDate docInfoDate = new DocInfoDate();
        StringWriter s=new StringWriter();
        String b = new String();
        XmlUtils.getInstance().xmlMapper().writeValue(s,docInfoDate);
        b = s.toString();
        System.out.println(b);
    }
}
