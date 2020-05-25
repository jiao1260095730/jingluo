package com.jingluo.jingluo.dto.groupdto;

import lombok.Data;

@Data
public class TransferManagerDto {
    //老管理员
    private String oldAdministratorCode;

    //新管理员
    private String xinAdministratorCode;

    //团队 id
    private Integer GroupId;

    //type:1 转让管理员并退出团队
    private String type;


}
