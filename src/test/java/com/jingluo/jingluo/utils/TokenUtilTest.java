package com.jingluo.jingluo.utils;

import org.junit.Test;

public class TokenUtilTest {

    @Test
    public void test() {
        String token = "706469769515106304111";
        String substring = token.substring(18, token.length());
        System.out.println(substring);
    }
}