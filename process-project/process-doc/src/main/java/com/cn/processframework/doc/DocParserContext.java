package com.cn.processframework.doc;

import java.util.HashSet;
import java.util.Set;

/**
 * @author apple
 * @package com.cn.processframework.doc
 * @desc <p></p>
 * @since
 */
public class DocParserContext {
    /**
     * 忽略显示
     */
    public static volatile Set<String> ignoreHttpMethods =new HashSet<>();
    static {
        ignoreHttpMethods.add("post");
        ignoreHttpMethods.add("options");
        ignoreHttpMethods.add("head");
        ignoreHttpMethods.add("put");
        ignoreHttpMethods.add("delete");
        ignoreHttpMethods.add("patch");
    }

}
