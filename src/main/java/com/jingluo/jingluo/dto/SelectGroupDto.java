package com.jingluo.jingluo.dto;

import lombok.Data;

@Data
public class SelectGroupDto {
    //0.班级名称 1.团队名称
    private String selevtName;
    //类型 0.班级名称 1.团队名称
    private String type;
    //用户
    private String userToken;
    //页码
    private Integer page;
}
