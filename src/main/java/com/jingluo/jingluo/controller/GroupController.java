package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.dto.groupdto.GroupDto;
import com.jingluo.jingluo.dto.groupdto.GroupStuId;
import com.jingluo.jingluo.dto.groupdto.TransferManagerDto;
import com.jingluo.jingluo.dto.SelectGroupDto;

import com.jingluo.jingluo.service.GroupService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "学生的团队操作", tags = "学生的团队操作")
@CrossOrigin
public class GroupController {

    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "创建个人团队", notes = "创建个人团队")
    @PostMapping("api/group/groupCreateOne.do")
    public ResultInfo groupCreate(@RequestBody GroupDto groupDto) {

        return groupService.groupCreate(groupDto);
    }

    @ApiOperation(value = "添加团队成员", notes = "添加团队成员")
    @PostMapping("api/group/addGroupMember.do")
    public ResultInfo addGroupMember(@RequestBody GroupStuId groupStuId) {

        return groupService.addGroupMember(groupStuId);
    }

    @ApiOperation(value = "删除团队", notes = "删除团队")
    @DeleteMapping("api/group/deleteGroup.do")
    public ResultInfo deleteGroup(@RequestBody GroupStuId groupStuId) {

        return groupService.deleteGroup(groupStuId);
    }

    @ApiOperation(value = "删除团队中成员", notes = "删除团队中成员")
    @DeleteMapping("api/group/deleteGroupMember.do")
    public ResultInfo deleteGroupMember(@RequestBody GroupStuId groupStuId) {

        return groupService.deleteGroupMember(groupStuId);
    }

    @ApiOperation(value = "查询团队 type 0:班级名称 1:团队名称 ", notes = "查询团队 type 0:班级名称 1:团队名称")
    @PostMapping("api/group/selectGroup.do")
    public ResultInfo selectGroup( @RequestBody SelectGroupDto selectGroupDto) {

        return groupService.selectGroup(selectGroupDto);
    }

  /*  @ApiOperation(value = "分页查询所有团队",notes = "分页查询所有团队")
    @PostMapping("api/group/selectAllGroup.do")
    public ResultInfo selectAllGroup(){
        return groupService.selectAllGroup();
    }
*/
    @ApiOperation(value = "修改团队信息", notes = "修改团队信息")
    @PostMapping("api/group/updataGroup.do")
    public ResultInfo updataGroup(@RequestBody GroupDto groupDto) {

        return groupService.updataGroup(groupDto);
    }

    @ApiOperation(value = "转让管理员", notes = "转让管理员")
    @PostMapping("api/group/transferManager.do")
    public ResultInfo transferManager(@RequestBody TransferManagerDto transferManagerDto) {
        return groupService.transferManager(transferManagerDto);
    }


}
