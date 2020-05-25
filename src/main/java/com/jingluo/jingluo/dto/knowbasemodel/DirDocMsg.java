package com.jingluo.jingluo.dto.knowbasemodel;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/25 22:37
 */
@Data
public class DirDocMsg {

    @JSONField(name = "firstMenu")
    private List<FirstMenu> firstMenu;

    @JSONField(name = "secondMenu")
    private List<SecondMenu> secondMenu;

    @JSONField(name = "docs")
    private List<Docs> docs;
}
