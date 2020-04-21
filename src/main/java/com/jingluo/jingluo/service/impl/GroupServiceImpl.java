package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.entity.Group;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.entity.StudentGroup;
import com.jingluo.jingluo.mapper.GroupMapper;
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.service.GroupService;
import com.jingluo.jingluo.utils.IdCode;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private IdCode idCode;
   //IdCode idCode = new IdCode();

    /**
     * 创建团队
     * @param group
     * @param stuCode
     * @return
     */
    @Override
    public ResultInfo groupCreate(Group group, String stuCode) {

        //获取学生个人信息
        Student stu = studentMapper.selectByCode(stuCode);
        if (stu == null){
            return ResultInfo.fail("学生信息不能为空");
        }
        StudentGroup sg = new StudentGroup();

        //判断学生是否加入过团队
        if (stu.getGroupId() == null){
            //团队id
            group.setGroupId(idCode.id());
            //所在班级
            group.setClassId(stu.getClassId());
            //团队成员加 1
            group.setStuNum(group.getStuNum()+1);
            //创建时间
           // group.setCreateTime(new Date());

            //学生团队关联
            sg.setStudentId(stu.getStudentId());
            sg.setGroupId(group.getGroupId());
            sg.setStuGroId(idCode.id());
            int count = groupMapper.insertGroupStudent(sg);
            int count2 = groupMapper.insertSelective(group);
            if (count ==1 && count2 ==1){
                return ResultInfo.success("团队创建成功");
            }else {
                return ResultInfo.fail("团队创建失败l");
            }
        }else {
            return ResultInfo.fail("团队创建失败");
        }
    }

    /**
     * 删除团队
     * @param groupId
     * @return
     */
    @Override
    public ResultInfo deleteGroup(Integer groupId) {
        int count = groupMapper.deleteGroup(groupId);
        int count2 = groupMapper.deleteStudentGroup(groupId);

        if (count == 1 && count2 ==1){
            return ResultInfo.success("删除成功");
        }else{
            return ResultInfo.fail("删除失败");
        }

    }

    /**
     * 删除团队中的成员
     * @param groupId
     * @param studentId
     * @return
     */
    @Override
    public ResultInfo deleteGroupMember(Integer groupId, Integer studentId) {
        try {
            Group group = groupMapper.findGroupId(groupId);
            //成员减一
            group.setStuNum(group.getStuNum() - 1);
            groupMapper.updateByPrimaryKey(group);
            groupMapper.deleteGroupMember(studentId);
            return ResultInfo.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("删除失败");
        }
    }

    /**
     * 添加成员
     * @param groupId
     * @param studentId
     * @return
     */
    @Override
    public ResultInfo addGroupMember(Integer groupId, Integer studentId) {

        try {

            Group group = groupMapper.findGroupId(groupId);
            StudentGroup sg = new StudentGroup();
            if (group != null){
                //成员加一
                group.setStuNum(group.getStuNum() + 1);
                groupMapper.updateByPrimaryKey(group);
            }else {
                return ResultInfo.fail("没有这个团队");
            }

            //学生团队关联
            sg.setStudentId(studentId);
            sg.setGroupId(groupId);
            sg.setStuGroId(idCode.id());
            groupMapper.insertGroupStudent(sg);

            return  ResultInfo.success("添加成员成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("添加成员失败");
        }


    }



}
