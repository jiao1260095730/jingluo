package com.jingluo.jingluo.dto.userdto;

import lombok.Data;

/**
 * @Description 验证码实体类，用于绑定手机号
 * @Author 焦斌
 * @Date 2020/4/13 22:21
 */
@Data
public class UserValidDto {
    //userToken
    private String userToken;
    //手机号
    private String phone;
    //验证码
    private String validCode;
}
