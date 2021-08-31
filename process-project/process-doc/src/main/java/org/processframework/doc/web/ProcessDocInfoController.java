package org.processframework.doc.web;

import org.processframework.doc.api.CatelogConfigApi;
import org.processframework.doc.support.DocItemMenu;
import org.processframework.doc.support.doc.DocInfoDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.processframework.doc.web.ProcessDocInfoController.DEFAULT_URL;

/**
 * @author apple
 * @desc
 */
@RestController
@RequestMapping(DEFAULT_URL)
public class ProcessDocInfoController {
    public static final String DEFAULT_URL = "handler.document";
    @Autowired
    private CatelogConfigApi catelogConfigApi;
    @GetMapping("/catelogItemMenu")
    public DocItemMenu getApiCateLogItemMenu(Long scopeId,Long treeId,Long docId,Long docType,String tag){
        return catelogConfigApi.getApiCateLogItemMenu(scopeId,treeId,docId,docType,tag);
    }

    @GetMapping("/document")
    public DocInfoDate getDocumentInfo(Long treeId, Long docType, Long docId, HttpServletRequest request){
        //获取请求语言
        String language = request.getHeader("content-language");
        boolean isEn = !language.equals("zh-CN");
        return catelogConfigApi.getDocumentDocInfoDate(treeId, docType, isEn, docId);
    }
}
