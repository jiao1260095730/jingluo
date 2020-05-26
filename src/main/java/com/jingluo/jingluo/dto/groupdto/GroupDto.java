package com.jingluo.jingluo.dto.groupdto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GroupDto {
    //团队名称
    private String GroupName;
    //团队简介
    private String GroupInfo;
    //创建来源 0个人  1 课程
    private String CreateFrom;
    //用户
    private String userToken;
    //是否共享（0私有 1发布）
    private String isPublic;
    //图片
    private String headImg;




}
