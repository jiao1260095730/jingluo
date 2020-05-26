package com.jingluo.jingluo.dto.knowbasemodel;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/25 22:32
 */
@Data
public class FirstMenu {
    //一级目录标题
    @JSONField(name = "title")
    private String title;
    //一级目录业务id
    @JSONField(name = "id")
    private String id;
}
