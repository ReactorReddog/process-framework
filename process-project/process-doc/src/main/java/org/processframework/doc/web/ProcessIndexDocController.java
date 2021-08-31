package org.processframework.doc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author apple
 * @desc 跳转页控制
 * @since 1.0.1.RELEASE
 */
@Controller
public class ProcessIndexDocController {
    @GetMapping("/")
    public String index(){
        return "redirect:index.html";
    }
}
