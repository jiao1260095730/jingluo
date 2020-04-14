package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.entity.Group;
import com.jingluo.jingluo.service.GroupService;
import com.jingluo.jingluo.vo.ReturnInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "团队创建", tags = "团队创建")
public class GroupController {

    private GroupService groupService;

    @ApiOperation(value = "创建个人团队", notes = "创建个人团队")
    @PostMapping("api/group/groupCreateOne.do")
    public ReturnInfo groupCreateOne(@RequestBody Group group){
        return null;
    }

}
