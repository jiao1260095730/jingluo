package com.jingluo.jingluo.utils;

import org.junit.Test;

public class NumberUtilTest {

    @Test
    public void testMD5() {

        String password = "520029bly-=0";
        System.out.println(NumberUtil.getMd5Str(password));
    }

    @Test
    public void testVolidateNum() {
        System.out.println(NumberUtil.createNum(6));
    }
}
