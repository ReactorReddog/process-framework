package org.processframework.doc.test;

import org.processframework.doc.XmlUtils;
import org.processframework.doc.support.doc.DocInfoDate;
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
