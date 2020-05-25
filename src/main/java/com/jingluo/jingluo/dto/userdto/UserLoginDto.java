package com.jingluo.jingluo.dto.userdto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/13 22:20
 */
@Data
public class UserLoginDto {
    //用户名 学号、工号
    private String userCode;
    //密码
    private String password;
}
