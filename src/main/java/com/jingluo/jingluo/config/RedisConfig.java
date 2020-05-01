package com.jingluo.jingluo.config;

/**
 * @Description 存储redis中key的常量类
 * @Author 焦斌
 * @Date 2020/4/11 18:38
 */
public class RedisConfig {
    //绑定手机验证码存储
    public static final String SMS_CODE_BIND = "sms:code:bind:";
    //找回密码验证码存储
    public static final String SMS_CODE_FIND = "sms:code:find:";
    //每分钟发送次数
    public static final String SMS_MINUTE = "sms:min:";
    //每小时发送次数
    public static final String SMS_HOUR = "sms:hour:";
    //每天发送次数
    public static final String SMS_DAY = "sms:day:";
    //学生登录token
    public static final String TOKEN_STUDENT_PRE = "token:student:";
    //教师登录token
    public static final String TOKEN_TEACHER_PRE = "token:teacher:";
}
