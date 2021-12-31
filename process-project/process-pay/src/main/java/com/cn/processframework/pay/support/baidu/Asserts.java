package com.cn.processframework.pay.support.baidu;

/**
 * @author apple 
 */
public class Asserts {
    
    public static void isNoNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isTrue(boolean bool, String message) {
        if (!bool) {
            throw new IllegalArgumentException(message);
        }
    }
}