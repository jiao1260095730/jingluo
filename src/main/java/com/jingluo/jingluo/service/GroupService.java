package com.jingluo.jingluo.service;


import com.jingluo.jingluo.dto.groupdto.GroupDto;
import com.jingluo.jingluo.dto.groupdto.GroupStuId;
import com.jingluo.jingluo.dto.groupdto.SelectGroup;
import com.jingluo.jingluo.dto.groupdto.TransferManagerDto;
import com.jingluo.jingluo.dto.SelectGroupDto;
import com.jingluo.jingluo.vo.ResultInfo;


public interface GroupService {

    //创建团队
    ResultInfo groupCreate(GroupDto groupDto);

    //删除团队
    ResultInfo deleteGroup(GroupStuId groupStuId);

    //删除团队中的成员
    ResultInfo deleteGroupMember(GroupStuId groupStuId);

    //添加成员
    ResultInfo addGroupMember(GroupStuId groupStuId);

    //根据 班级名称 或者 团队名称 查询团队信息
    ResultInfo selectGroup(SelectGroupDto selectGroupDto);

    //修改团队信息
    ResultInfo updataGroup(GroupDto groupDto);

    ResultInfo transferManager(TransferManagerDto transferManagerDto);

    ResultInfo selectAllGroupByGroupId(SelectGroup selectGroup);
}
