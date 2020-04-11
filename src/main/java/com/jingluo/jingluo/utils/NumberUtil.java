package com.jingluo.jingluo.utils;

import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/11 13:24
 */
public class NumberUtil {

    /**
     * MD5加密算法：是一种非常普遍，不可逆的算法，安全性非常高
     * 1、如果项目中使用了Spring框架，可以直接使用spring自带的工具类
     * 2、也可以去下载MD5加密的jar包
     * 3、如果觉得jar包太大了，可以直接复制一个md5源码
     */
    public static String getMd5Str(String mingWen) {

        /*
        自定义一种加密算法
         */
        String firstMi = DigestUtils.md5DigestAsHex(mingWen.getBytes());
        String jieStr = firstMi.substring(5, 16);
        String salt = "mingwenjiami";
        String newStr = firstMi + jieStr + salt;

        return DigestUtils.md5DigestAsHex(newStr.getBytes());
    }

    /**
    * 生成指定位数验证码
    */
    public static int createNum(int len) {
        return (int) (new Random().nextInt((int) (Math.pow(10, len) - Math.pow(10, len - 1))) +
                Math.pow(10, len - 1));
    }
}
