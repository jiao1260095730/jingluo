package com.jingluo.jingluo.utils;

import org.junit.Test;

public class MD5UtilTest {

    @Test
    public void testMD5() {
        String md5Str = NumberUtil.getMd5Str("123456");
        System.out.println(md5Str);
    }

}
