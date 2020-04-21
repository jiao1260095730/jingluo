package com.jingluo.jingluo.service;

import com.jingluo.jingluo.entity.Group;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.vo.ResultInfo;

public interface GroupService {

    //创建团队
    ResultInfo groupCreate(Group group, String stuCode);

    //删除团队
    ResultInfo deleteGroup(Integer groupId);

    //删除团队中的成员
    ResultInfo deleteGroupMember(Integer groupId, Integer studentId);

    //添加成员
    ResultInfo addGroupMember(Integer groupId, Integer studentId);
}
