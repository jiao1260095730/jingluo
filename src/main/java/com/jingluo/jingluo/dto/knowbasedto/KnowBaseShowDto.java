package com.jingluo.jingluo.dto.knowbasedto;

import lombok.Data;

/**
 * @Description 展示个人知识库列表实体
 * @Author 焦斌
 * @Date 2020/5/24 19:34
 */
@Data
public class KnowBaseShowDto {
    //用户token
    private String userToken;
    //分页查询 页码
    private Integer page;
}
