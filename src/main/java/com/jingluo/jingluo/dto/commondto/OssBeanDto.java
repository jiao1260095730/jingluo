package com.jingluo.jingluo.dto.commondto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/11 15:51
 */
@Data
public class OssBeanDto {
    //存储在OSS中文件名
    private String objName;
    //存储在OSS中文件路径
    private String url;
}
