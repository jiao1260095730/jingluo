package com.jingluo.jingluo.utils;

import org.junit.Test;

public class NumberUtilTest {

    @Test
    public void testMD5() {

        String password = "520029bly-=0";
        System.out.println(NumberUtil.getMd5Str(password));
    }

    @Test
    public void testIntNum() {
        System.out.println(NumberUtil.createIntNum(6));
    }

    @Test
    public void testStrNum() {
        String strNum = NumberUtil.createStrNum(6);
        System.out.println(strNum);
    }
}
