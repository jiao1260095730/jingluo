package com.jingluo.jingluo.utils;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 13:56
 */
public class StringUtil {
    public static boolean isEmpty(String... args) {
        boolean r = false;
        for (String s : args) {
            if (s == null || s.length() == 0) {
                r = true;
                break;
            }
        }
        return r;
    }
}
