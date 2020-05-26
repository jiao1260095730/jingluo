package com.jingluo.jingluo.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String dateFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
