package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.entity.Group;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.service.GroupService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "团队创建", tags = "团队创建")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "创建个人团队", notes = "创建个人团队")
    @PostMapping("api/group/groupCreateOne.do")
    public ResultInfo groupCreate(@RequestBody Group group, String stuCode){
        System.out.println("-------------------");
        System.out.println("-------------------");
        System.out.println("-------------------");
        System.out.println("-------------------");
        System.out.println(group.getGroupName());
        System.out.println(group.getCreateTime());
        return groupService.groupCreate(group,stuCode);
    }


        @ApiOperation(value = "添加团队成员",notes = "添加团队成员")
        @PostMapping("api/group/addGroupMember.do")
        public ResultInfo addGroupMember(Integer groupId, Integer StudentId){

            return groupService.addGroupMember(groupId,StudentId);
        }

        @ApiOperation(value = "删除团队",notes = "删除团队")
        @DeleteMapping("api/group/deleteGroup.do")
        public ResultInfo deleteGroup(Integer groupId){

            return groupService.deleteGroup(groupId);
        }

        @ApiOperation(value = "删除团队中成员",notes = "删除团队中成员")
        @DeleteMapping("api/group/deleteGroupMember.do")
        public ResultInfo deleteGroupMember(Integer groupId, Integer StudentId){

            return groupService.deleteGroupMember(groupId,StudentId);
        }


    }
