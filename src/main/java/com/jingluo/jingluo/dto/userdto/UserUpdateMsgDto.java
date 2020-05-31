package com.jingluo.jingluo.dto.userdto;

import lombok.Data;

/**
 * @Description 用户修改个人信息dto
 * @Author 焦斌
 * @Date 2020/5/5 16:38
 */
@Data
public class UserUpdateMsgDto {
    //userToken
    private String userToken;
    //姓名
    private String name;
    //昵称
    private String nickName;
    //头像
    private String headImg;
}
