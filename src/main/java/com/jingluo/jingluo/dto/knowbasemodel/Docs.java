package com.jingluo.jingluo.dto.knowbasemodel;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/25 22:42
 */
@Data
public class Docs {
    //文档业务id
    private String id;
    //文档父级id
    private String parentId;
    //文档标题
    private String title;
    //文档作者
    private String author;
    //文档作者id
    private String authorId;
    //文档创建时间
    private String creatTime;
    //文档最后一次更改时间
    private String updateTime;
    //文档内容
    private String article;
}
