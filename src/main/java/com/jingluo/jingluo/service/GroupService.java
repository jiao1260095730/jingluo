package com.jingluo.jingluo.service;

import com.jingluo.jingluo.dto.GroupDto;
import com.jingluo.jingluo.dto.GroupStuId;
import com.jingluo.jingluo.dto.TransferManagerDto;
import com.jingluo.jingluo.entity.Group;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.vo.ResultInfo;

public interface GroupService {

    //创建团队
    ResultInfo groupCreate(GroupDto groupDto, String stuCode);

    //删除团队
    ResultInfo deleteGroup(GroupStuId groupStuId);

    //删除团队中的成员
    ResultInfo deleteGroupMember(GroupStuId groupStuId);

    //添加成员
    ResultInfo addGroupMember(GroupStuId groupStuId);

    //根据 班级名称 或者 团队名称 查询团队信息
    ResultInfo selectGroup(String selectName,String  type);

    //修改团队信息
    ResultInfo updataGroup(GroupDto groupDto);

    ResultInfo transferManager(TransferManagerDto transferManagerDto);
}
