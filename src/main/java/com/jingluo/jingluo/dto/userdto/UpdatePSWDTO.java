package com.jingluo.jingluo.dto.userdto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 13:37
 */
@Data
public class UpdatePSWDTO {
    //用户code  学号、工号
    private String userCode;
    //新密码
    private String newPassword;
    //旧密码
    private String oldPassword;
    //token
    private String token;
}
