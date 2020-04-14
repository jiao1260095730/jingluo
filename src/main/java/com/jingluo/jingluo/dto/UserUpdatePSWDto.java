package com.jingluo.jingluo.dto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 13:37
 */
@Data
public class UserUpdatePSWDto {
    private String userCode;
    //password 可用做密码、修改密码时的新密码
    private String password;
    private String phone;
    private String validateCode;
    private String oldPassword;
}
