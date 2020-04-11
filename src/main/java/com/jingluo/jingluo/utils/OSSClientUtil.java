package com.jingluo.jingluo.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.SystemConfig;

import java.io.*;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/9 23:36
 */
public class OSSClientUtil {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static String accessKeyId = "LTAI4Fj6vtSyVJhMj6yT7FF1";
    private static String accessKeySecret = "cMF6HO0YOrQ5eIWfTlPmNaNFhUBvBg";
    // 创建OSSClient实例。
    static OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    /**
     * 此方法将文件上传到指定文件夹下
     */
    public static String upload(String fileName, byte[] data, int months, String packageName) {
        fileName = packageName + "/";
        return upload(fileName, data, months);
    }

    /**
    * 默认上传带文件夹的文件有效日期  十年
    */
    public static String upload(String fileName, byte[] data, String packageName) {
        return upload(fileName, data, SystemConfig.OSS_URL_MONTHS, packageName);
    }

    /**
     * 上传字节，包含有效时间，默认时间传入0则默认十年，返回访问路径
     * 此方法直接传文件到bucket下
     */
    public static String upload(String fileName, byte[] data, int months) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));

        //上传内容
        ossClient.putObject(SystemConfig.OSS_BUCKET, fileName, new ByteArrayInputStream(data), objectMetadata);

        //3、获取访问路径
        String url;
        if (months > 0) {
            //设置资源的有效期
            url = ossClient.generatePresignedUrl(SystemConfig.OSS_BUCKET, fileName, DateUtil.getDateByMonths(months)).toString();
        } else {
            //不设置资源有效期
            url = ossClient.generatePresignedUrl(SystemConfig.OSS_BUCKET, fileName, DateUtil.getDateByMonths(SystemConfig.OSS_URL_MONTHS)).toString();
        }
        //4、关闭 销毁
        ossClient.shutdown();
        return url;
    }

    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }
}
