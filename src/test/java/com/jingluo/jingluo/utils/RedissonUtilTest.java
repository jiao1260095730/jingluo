package com.jingluo.jingluo.utils;

import org.junit.Test;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/9 22:52
 */
public class RedissonUtilTest {

    @Test
    public void testStr() {
        String key = "phone";
        String value = "13213538971";
        RedissonUtil.setStr(key, value);

        if (RedissonUtil.checkKey(key)) {
            System.out.println(RedissonUtil.getStr(key));
        }
    }

    @Test
    public void testDel() {
        String key = "phone";
        RedissonUtil.delKey(key);
    }
}
