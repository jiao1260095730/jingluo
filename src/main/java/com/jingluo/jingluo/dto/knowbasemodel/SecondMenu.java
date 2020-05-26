package com.jingluo.jingluo.dto.knowbasemodel;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/25 22:39
 */
@Data
public class SecondMenu {
    //二级目录标题
    private String title;
    //二级目录业务id
    private String id;
    //二级目录父级目录id
    private String parentId;
}
