package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.dto.groupdto.GroupDto;
import com.jingluo.jingluo.dto.groupdto.GroupStuId;
import com.jingluo.jingluo.dto.groupdto.TransferManagerDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional
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
            if (stu.getGroupId() == null || stu.getGroupId() == 0) {
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
                //添加管理员
                group.setStudentCode(stu.getStudentCode());
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
                if (count == 1 && count2 == 1 && insert == 1) {
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
     * @param groupStuId
     * @return
     */
    @Override
    public ResultInfo deleteGroup(GroupStuId groupStuId) {
        int count = groupMapper.deleteGroup(groupStuId.getGroupId());
        int count2 = groupMapper.deleteStudentGroup(groupStuId.getGroupId());
        //删除学生信息里的团队id
        Student student = studentMapper.selectByCode(groupStuId.getStudentCode());
        student.setGroupId(0);
        int count3 = studentMapper.update(student);
        if (count == 1 && count2 == 1 && count3 == 1) {
            return ResultInfo.success("删除成功");
        } else {
            return ResultInfo.fail("删除失败");
        }

    }

    /**
     * 删除团队中的成员
     *
     * @param groupStuId
     * @return
     */
    @Override
    public ResultInfo deleteGroupMember(GroupStuId groupStuId) {
        try {
            Group group = groupMapper.findGroupId(groupStuId.getGroupId());
            //成员减一
            group.setStuNum(group.getStuNum() - 1);
            groupMapper.updateByPrimaryKeySelective(group);
            groupMapper.deleteGroupMember(groupStuId.getStudentId());
            //删除学生信息里的团队id
            Student student = studentMapper.selectByCode(groupStuId.getStudentCode());
            student.setGroupId(0);
            studentMapper.update(student);
            return ResultInfo.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("删除失败");
        }
    }

    /**
     * 添加成员
     *
     * @param groupStuId
     * @return
     */
    @Override
    public ResultInfo addGroupMember(GroupStuId groupStuId) {
        try {
            Student student = studentMapper.selectByCode(groupStuId.getStudentCode());
            student.setGroupId(groupStuId.getGroupId());
            studentMapper.update(student);
            Group group = groupMapper.findGroupId(groupStuId.getGroupId());
            StudentGroup sg = new StudentGroup();
            if (group != null) {
                //成员加一
                group.setStuNum(group.getStuNum() + 1);
                groupMapper.updateByPrimaryKeySelective(group);
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
     *
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
     *
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

    /**
     * 转让管理员
     * @param transferManagerDto
     * @return
     */
    @Override
    public ResultInfo transferManager(TransferManagerDto transferManagerDto) {

        //根据老管理员获取学生信息
        Student oldStudent = studentMapper.selectByCode(transferManagerDto.getOldAdministratorCode());
        //根据老管理员获取这个团队信息
        Group group = groupMapper.findGroupId(oldStudent.getGroupId());
        //根基新管理员code 获取这个学生信息
        Student xinStudent = studentMapper.selectByCode(transferManagerDto.getXinAdministratorCode());

        //判断转让后是否退出
        if (transferManagerDto.getType().equals("1")) {
            //判断是否是管理员 如果是 && 新管理员是这个团队中的成员
            if (group.getStudentCode().equals(transferManagerDto.getOldAdministratorCode()) && xinStudent.getGroupId().equals(group.getGroupId())) {
                group.setStudentCode(transferManagerDto.getXinAdministratorCode());
                groupMapper.updateByPrimaryKeySelective(group);
                GroupStuId groupStuId = new GroupStuId();
                groupStuId.setGroupId(group.getGroupId());
                groupStuId.setStudentId(oldStudent.getStudentId());
                groupStuId.setStudentCode(oldStudent.getStudentCode());
                deleteGroupMember(groupStuId);
            } else {
                return ResultInfo.fail("不是管理员或新管理员不是团队成员,请正确操作");
            }
        } else {
            //判断是否是管理员 如果是 && 新管理员是这个团队中的成员
            if (group.getStudentCode().equals(transferManagerDto.getOldAdministratorCode()) && xinStudent.getGroupId().equals(group.getGroupId())) {
                group.setStudentCode(transferManagerDto.getXinAdministratorCode());
                groupMapper.updateByPrimaryKeySelective(group);
            } else {
                return ResultInfo.fail("请正确操作");
            }
        }

        return ResultInfo.success("管理员转让成功");
    }

}
