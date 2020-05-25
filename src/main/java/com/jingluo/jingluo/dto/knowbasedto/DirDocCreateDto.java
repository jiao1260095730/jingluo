package com.jingluo.jingluo.dto.knowbasedto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/25 23:24
 */
@Data
public class DirDocCreateDto {
    //用户token
    private String userToken;
    //知识库id
    private Integer knowBaseId;
    //创建目录、文档的json串
    private String dirDocMsgJson;
}
