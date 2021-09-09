package org.processframework.gateway.common.validate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author apple
 */
public class SignConfig {
    private static volatile Wrapper wrapper = new Wrapper() {};

    public static void enableUrlencodeMode() {
        wrapper = new Wrapper() {
            @Override
            public String wrapVal(Object val) {
                String valStr = String.valueOf(val);
                return URLEncoder.encode(valStr, StandardCharsets.UTF_8);
            }
        };
    }

    public static String wrapVal(Object val) {
        return wrapper.wrapVal(val);
    }

    interface Wrapper {
        default String wrapVal(Object val) {
            return String.valueOf(val);
        }
    }

}
