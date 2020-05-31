package com.jingluo.jingluo.dto.knowbasedto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/27 23:37
 */
@Data
public class DocDeleteDto {
    //用户token
    private String userToken;
    //文档id
    private Integer docId;
}
