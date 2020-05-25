package com.jingluo.jingluo.dto.knowbasemodel;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/25 22:42
 */
@Data
public class Docs {

    private String id;

    private String parentId;

    private String title;

    private String author;

    private String creatTime;

    private String updateTime;
}
