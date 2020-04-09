package com.jingluo.jingluo.utils;

import java.util.Random;

/**
 * @Description 验证码工具类
 * @Author 焦斌
 * @Date 2020/4/8 22:36
 */
public class RandomNumUtil {

    //生成六位数验证码
    public static int createNum(int len){
        return (int)(new Random().nextInt((int)(Math.pow(10,len)-Math.pow(10,len-1)))+
                Math.pow(10,len-1));
    }
}
