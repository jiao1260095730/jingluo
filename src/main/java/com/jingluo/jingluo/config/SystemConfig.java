package com.jingluo.jingluo.config;

/**
 * @Description 系统配置文件
 * @Author 焦斌
 * @Date 2020/4/8 21:53
 */
public class SystemConfig {

    //Redis服务器的地址
    public static final String REDIS_HOST="redis://47.98.174.241:6379";
    //Redis服务器的密码
    public static final String REDIS_PASS="123456";
    //OSS阿里云存储的存储空间名称bucketName
    public static final String OSS_BUCKET="jingluo-1";
    //OSS存储中文件对象名称objectName
    public static final String OSS_FILE_OBJECT_NAME="file-1";
    //OSS存储中文件对象名称objectName
    public static final String OSS_IMG_OBJECT_NAME="img-1";
    //OSS访问连接的有效期  默认十年
    public static final int OSS_URL_MONTHS=120;
}
