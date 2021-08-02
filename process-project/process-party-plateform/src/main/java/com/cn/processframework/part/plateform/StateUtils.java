package com.cn.processframework.part.plateform;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author apple
 * @package com.cn.processframework.part.plateform.utils
 * @since
 * @desc <p>高性能创建uuid</p>
 */
public class StateUtils {
    private final static byte[] DIGITS = {
            '0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    public static String uuid(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long lsb = random.nextLong();
        long msb = random.nextLong();
        byte[]buf = new byte[32];
        formatUnsingnedLong(lsb,buf,20,12);
        formatUnsingnedLong(lsb>>48,buf,16,4);
        formatUnsingnedLong(msb,buf,12,4);
        formatUnsingnedLong(msb>>16,buf,8,4);
        formatUnsingnedLong(msb>>>32,buf,0,8);
        return new String(buf, StandardCharsets.UTF_8);
    }

    private static void formatUnsingnedLong(long val,byte[]buf,int offset,int len){
        int charPos = offset+len;
        int radix = 1<<4;
        int mask = radix-1;
        do{
            buf[--charPos] = DIGITS[((int)val)&mask];
        }while (charPos > offset);
    }
}
