package com.jingluo.jingluo.dto.userdto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/2 10:38
 */
@Data
public class UserPhoneLoginDto {
    //手机号
    private String phone;
    //验证码
    private String validCode;
}
