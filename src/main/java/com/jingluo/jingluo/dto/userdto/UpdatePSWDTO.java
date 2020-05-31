package com.jingluo.jingluo.dto.userdto;

import lombok.Data;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 13:37
 */
@Data
public class UpdatePSWDTO {
    //userToken
    private String userToken;
    //旧密码
    private String oldPassword;
    //新密码
    private String newPassword;
}
