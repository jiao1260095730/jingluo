package com.jingluo.jingluo.dto;

import lombok.Data;

/**
 * @Description 创建课程所需参数的dto
 * @Author 焦斌
 * @Date 2020/5/2 17:23
 */
@Data
public class KnowBaseCreateDto {
    //用户token   可解析用户的userCode
    private String userToken;
    //创建来源 1个人 2团队 3教师通过课程创建
    private String createFrom;
    //知识库名称
    private String baseName;
    //知识库简介
    private String baseInfo;
    //知识库种类 1文档知识库；2画板知识库；3模板知识库
    private String baseKind;
    //可见范围 是否发布 0私有 1发布
    private String isPublic;
    //如果为课程创建则需记录课程id 业务主键
    private int courseId;
    //如果为团队创建则需记录团队id 业务主键
    private int groupId;
}
