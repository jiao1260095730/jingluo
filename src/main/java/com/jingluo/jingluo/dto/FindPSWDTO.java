package com.jingluo.jingluo.dto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/16 23:47
 */
@Data
public class FindPSWDTO {
    //用户code
    private String userCode;
    //手机号
    private String phone;
    //验证码
    private String validateCode;
    //新密码
    private String newPassword;
}
