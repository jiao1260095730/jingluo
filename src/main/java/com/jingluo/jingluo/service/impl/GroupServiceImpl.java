package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.dto.GroupDto;
import com.jingluo.jingluo.dto.GroupStuId;
import com.jingluo.jingluo.entity.Class;
import com.jingluo.jingluo.entity.Group;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.entity.StudentGroup;
import com.jingluo.jingluo.mapper.ClassMapper;
import com.jingluo.jingluo.mapper.GroupMapper;
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.service.GroupService;
import com.jingluo.jingluo.utils.IdCode;
import com.jingluo.jingluo.vo.ResultInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component

public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClassMapper classMapper;



    /**
     * 创建团队
     *
     * @param groupDto
     * @param stuCode
     * @return
     */
    @Override
    public ResultInfo groupCreate(GroupDto groupDto, String stuCode) {

        try {
            //获取学生个人信息
            Student stu = studentMapper.selectByCode(stuCode);
            if (stu == null) {
                return ResultInfo.fail("学生信息不能为空");
            }
            StudentGroup sg = new StudentGroup();
            Group group = new Group();
            //判断学生是否加入过团队
            if (stu.getGroupId() == null) {
                //团队id
                group.setGroupId(IdCode.id());
                //团队名称
                group.setGroupName(groupDto.getGroupName());
                //团队简介
                group.setGroupInfo(groupDto.getGroupInfe());
                //创建来源
                group.setCreateFrom(groupDto.getCreateFrom());
                //所在班级
                group.setClassId(stu.getClassId());
                //团队成员加 1
                group.setStuNum(1);
                //创建时间
                group.setCreateTime(new Date());

                int count2 = groupMapper.insertSelective(group);
                //学生团队关联
                sg.setStudentId(stu.getStudentId());
                sg.setGroupId(group.getGroupId());
                sg.setStuGroId(IdCode.id());
                int count = groupMapper.insertGroupStudent(sg);

                //学生信息添加团队id
                stu.setGroupId(group.getGroupId());
                int insert = studentMapper.update(stu);
                if (count == 1 && count2 == 1 && insert ==1) {
                    return ResultInfo.success("团队创建成功");
                } else {
                    return ResultInfo.fail("团队创建失败");
                }
            } else {
                return ResultInfo.fail("团队创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("团队创建失败");
        }
    }

    /**
     * 删除团队
     *
     * @param groupId
     * @return
     */
    @Override
    public ResultInfo deleteGroup(Integer groupId) {
        int count = groupMapper.deleteGroup(groupId);
        int count2 = groupMapper.deleteStudentGroup(groupId);

        if (count == 1 && count2 == 1) {
            return ResultInfo.success("删除成功");
        } else {
            return ResultInfo.fail("删除失败");
        }

    }

    /**
     * 删除团队中的成员
     * @param groupStuId
     * @return
     */
    @Override
    public ResultInfo deleteGroupMember(GroupStuId groupStuId) {
        try {
            Group group = groupMapper.findGroupId(groupStuId.getGroupId());
            //成员减一
            group.setStuNum(group.getStuNum() - 1);
            groupMapper.updateByPrimaryKey(group);
            groupMapper.deleteGroupMember(groupStuId.getStudentId());
            return ResultInfo.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("删除失败");
        }
    }

    /**
     * 添加成员
     * @param groupStuId
     * @return
     */
    @Override
    public ResultInfo addGroupMember(GroupStuId groupStuId) {

        try {

            Group group = groupMapper.findGroupId(groupStuId.getGroupId());
            StudentGroup sg = new StudentGroup();
            if (group != null) {
                //成员加一
                group.setStuNum(group.getStuNum() + 1);
                groupMapper.updateByPrimaryKey(group);
            } else {
                return ResultInfo.fail("没有这个团队");
            }

            //学生团队关联
            sg.setStudentId(groupStuId.getStudentId());
            sg.setGroupId(groupStuId.getGroupId());
            sg.setStuGroId(IdCode.id());
            groupMapper.insertGroupStudent(sg);

            return ResultInfo.success("添加成员成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("添加成员失败");
        }

    }

    /**
     * 通过 班级名称 团队名称查询 团队
     * @param selectName
     * @param type
     * @return
     */
    @Override
    public ResultInfo selectGroup(String selectName, String type) {
        // 0 表示班级名称
        if (type.equals("0")) {
            Class aClass = classMapper.selectById(selectName);
            List<Group> group = groupMapper.selectGroupByClassId(aClass.getClassId());
            if (group != null) {
                return ResultInfo.fail("查询成功", group);
            } else {
                return ResultInfo.fail("其输入正确的班级");
            }
        }
        //1 表示团队名称
        if (type.equals("1")) {
            Group group = groupMapper.selectGroupByGroupName(selectName);
            if (group != null) {
                return ResultInfo.fail("查询成功", group);
            } else {
                return ResultInfo.fail("其输入正确的团队名称");
            }
        }
        return ResultInfo.fail("查询失败,请正确输入要搜索的名称");
    }

    /**
     * 修改团队信息
     * @param groupDto
     * @return
     */
    @Override
    public ResultInfo updataGroup(GroupDto groupDto) {

        Group group = new Group();
        group.setGroupName(groupDto.getGroupName());
        group.setGroupInfo(groupDto.getGroupInfe());

        int count = groupMapper.updateByPrimaryKeySelective(group);
        if (count == 1) {
            return ResultInfo.success("修改成功");
        } else {
            return ResultInfo.fail("修改失败");
        }

    }

}
