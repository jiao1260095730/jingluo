package com.jingluo.jingluo.dto.groupdto;

import lombok.Data;

@Data
public class GroupStuId {
    //团队id
    private Integer GroupId;

    //学生账号
    private String StudentCode;
    //用户
    private String userToken;
}
