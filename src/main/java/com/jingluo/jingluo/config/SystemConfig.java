package com.jingluo.jingluo.config;

/**
 * @Description 系统配置文件
 * @Author 焦斌
 * @Date 2020/4/8 21:53
 */
public class SystemConfig {

    //Redis服务器的地址
    public static final String REDIS_HOST = "redis://47.98.174.241:6379";
    //Redis服务器的密码
    public static final String REDIS_PASS = "123456";
    //OSS阿里云存储的存储空间名称bucketName
    public static final String OSS_BUCKET = "jingluo-1";
    //OSS存储中文件对象名称objectName
    public static final String OSS_FILE_OBJECT_NAME = "file-1";
    //OSS存储中文件对象名称objectName
    public static final String OSS_IMG_OBJECT_NAME = "img-1";
    //OSS访问连接的有效期  默认十年
    public static final int OSS_URL_MONTHS = 120;
    //短信验证码的有效期5分钟
    public static final int SMS_CODE_TIME = 5;
    //登录成功后token的时效  60分钟
    public static final int TOKEN_REDIS_TIME = 60 * 60;
    //知识库创建来源 1个人 2团队 3教师通过课程创建
    public static final String CREATE_FROM_PERSON = "1";
    public static final String CREATE_FROM_GROUP = "2";
    public static final String CREATE_FROM_COURSE = "3";
    //知识库是否对外开放 0私有 1共享
    public static final String KNOWBASE_PUBLIC = "0";
    public static final String KNOWBASE_PRIVATE = "1";
    //知识库是否已删除 0否 1是
    public static final String KNOWBASE_NOT_DELETE = "0";
    public static final String KNOWBASE_IS_DELETE = "1";
}
