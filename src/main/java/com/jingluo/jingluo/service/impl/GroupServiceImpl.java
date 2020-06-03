package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.dto.SelectGroupDto;
import com.jingluo.jingluo.dto.commondto.Page;
import com.jingluo.jingluo.dto.groupdto.GroupDto;
import com.jingluo.jingluo.dto.groupdto.GroupStuId;
import com.jingluo.jingluo.dto.groupdto.SelectGroup;
import com.jingluo.jingluo.dto.groupdto.TransferManagerDto;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.entity.Class;
import com.jingluo.jingluo.entity.Group;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.entity.StudentGroup;
import com.jingluo.jingluo.mapper.ClassMapper;
import com.jingluo.jingluo.mapper.GroupMapper;
import com.jingluo.jingluo.mapper.StudentGroupMapper;
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.service.GroupService;
import com.jingluo.jingluo.utils.IdCode;
import com.jingluo.jingluo.utils.TokenUtil;
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
    @Autowired
    private StudentGroupMapper studentGroupMapper;
    /**
     * 创建团队
     * @param groupDto
     * @return
     */
    @Override
    public ResultInfo groupCreate(GroupDto groupDto) {

        try {
            String userToken = groupDto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }

            //获取学生个人信息
            Student stu = studentMapper.selectByCode(userCode);
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
                group.setGroupInfo(groupDto.getGroupInfo());
                //创建来源
                group.setCreateFrom(groupDto.getCreateFrom());
                //所在班级
                group.setClassId(stu.getClassId());
                //添加管理员
                group.setStudentCode(stu.getStudentCode());
                //图片
                group.setHeadImg(groupDto.getHeadImg());
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


                if (count == 1 && count2 == 1 ) {
                    return ResultInfo.success("团队创建成功", group.getGroupId());
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
        try {
            String userToken = groupStuId.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            int count = groupMapper.deleteGroup(groupStuId.getGroupId());
            int count2 = groupMapper.deleteStudentGroup(groupStuId.getGroupId());

            if (count == 1 && count2 == 1 ) {
                return ResultInfo.success("删除成功");
            } else {
                return ResultInfo.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("操作失败");
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
            String userToken = groupStuId.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            Student student = studentMapper.selectByCode(groupStuId.getStudentCode());

            Group group = groupMapper.findGroupId(groupStuId.getGroupId());
            //成员减一
            group.setStuNum(group.getStuNum() - 1);
            groupMapper.updateByPrimaryKeySelective(group);
            studentGroupMapper.deleteGroupMember(student.getStudentId(),group.getGroupId());

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
            String userToken = groupStuId.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }

            Group group = groupMapper.findGroupId(groupStuId.getGroupId());
            StudentGroup sg = new StudentGroup();
            if (group != null) {
                //成员加一
                group.setStuNum(group.getStuNum() + 1);
                groupMapper.updateByPrimaryKeySelective(group);
            } else {
                return ResultInfo.fail("没有这个团队");
            }
            Student student = studentMapper.selectByCode(groupStuId.getStudentCode());
            //学生团队关联
            sg.setStudentId(student.getStudentId());
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
     * @param selectGroupDto
     * @return
     */
    @Override
    public ResultInfo selectGroup(SelectGroupDto selectGroupDto) {
        try {
            String userToken = selectGroupDto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            //实现分页功能
            Page page = new Page();
            // 0 表示班级名称
            if (selectGroupDto.getType().equals("0")) {
                Class aClass = classMapper.selectById(selectGroupDto.getSelevtName());
                List<Group> group = groupMapper.selectGroupByClassId(aClass.getClassId());
                if (group != null) {

                    page.setCurrentPage(selectGroupDto.getPage());
                    //每页的开始数
                    page.setStart((page.getCurrentPage() - 1) * page.getPageSize());
                    //list的大小
                    int count = group.size();
                    page.setTotalNum(count);

                    page.setPageSize(100);
                    //设置总页数
                    page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
                    //对list进行截取
                    page.setDataList(group.subList(page.getStart()
                            , count - page.getStart() > page.getPageSize() ? page.getStart() + page.getPageSize() : count));
                    return ResultInfo.success("查询成功", page);
                } else {
                    return ResultInfo.fail("其输入正确的班级");
                }
            }
            //1 表示团队名称
            if (selectGroupDto.getType().equals("1")) {
                List<Group>  group = groupMapper.selectGroupByGroupName(selectGroupDto.getSelevtName());
                if (group != null) {

                    page.setCurrentPage(selectGroupDto.getPage());
                    //每页的开始数
                    page.setStart((page.getCurrentPage() - 1) * page.getPageSize());
                    //list的大小
                    int count = group.size();
                    page.setTotalNum(count);
                    page.setPageSize(100);
                    //设置总页数
                    page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
                    //对list进行截取
                    page.setDataList(group.subList(page.getStart()
                            , count - page.getStart() > page.getPageSize() ? page.getStart() + page.getPageSize() : count));
                    return ResultInfo.success("查询成功", page);
                } else {
                    return ResultInfo.fail("其输入正确的团队名称");
                }
            }
            //查询全部
            if (selectGroupDto.getType().equals("2")){
                List<Group> AllGroup = groupMapper.selectAllGroup();

                page.setCurrentPage(selectGroupDto.getPage());
                //每页的开始数
                page.setStart((page.getCurrentPage() - 1) * page.getPageSize() + 1);
                //list的大小
                int count = AllGroup.size();
                page.setTotalNum(count);
                page.setPageSize(100);
                //设置总页数
                page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
                //对list进行截取
                page.setDataList(AllGroup.subList(page.getStart()
                        , count - page.getStart() > page.getPageSize() ? page.getStart() + page.getPageSize() : count));
                return ResultInfo.success("查询成功", page);
            }
            return ResultInfo.fail("查询失败");
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultInfo.fail("查询失败哦");
        }
    }

    /**
     * 修改团队信息
     *
     * @param groupDto
     * @return
     */
    @Override
    public ResultInfo updataGroup(GroupDto groupDto) {
        try {
            String userToken = groupDto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            Group group = new Group();
            group.setGroupId(groupDto.getGroupId());
            group.setGroupName(groupDto.getGroupName());
            group.setGroupInfo(groupDto.getGroupInfo());
            group.setHeadImg(groupDto.getHeadImg());
            group.setCreateFrom(groupDto.getCreateFrom());
            int count = groupMapper.updateByPrimaryKeySelective(group);
            if (count == 1) {
                return ResultInfo.success("修改成功");
            } else {
                return ResultInfo.fail("修改失败了");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultInfo.fail("修改失败");
        }
    }

    /**
     * 转让管理员
     * @param transferManagerDto
     * @return
     */
    @Override
    public ResultInfo transferManager(TransferManagerDto transferManagerDto) {

        try {
            String userToken = transferManagerDto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            //根据老管理员获取学生信息
            Student oldStudent = studentMapper.selectByCode(userCode);
            //根据老管理员获取这个团队信息
            Group group = groupMapper.findGroupId(transferManagerDto.getGroupId());
            Student xinStudent = studentMapper.selectByCode(transferManagerDto.getXinAdministratorCode());

           StudentGroup sg = new StudentGroup();
           sg.setStudentId(xinStudent.getStudentId());
           sg.setGroupId(transferManagerDto.getGroupId());

            //判断是否是团队的成员
            StudentGroup studentGroup = studentGroupMapper.selectStundentGroup(sg);
            //判断转让后是否退出
            if (transferManagerDto.getType().equals("1")) {
                //判断是否是管理员 如果是 && 新管理员是这个团队中的成员
                if (group.getStudentCode().equals(userCode) && studentGroup != null) {
                    //修改管理员
                    group.setStudentCode(transferManagerDto.getXinAdministratorCode());
                    //成员减一
                    group.setStuNum(group.getStuNum() - 1);
                    groupMapper.updateByPrimaryKeySelective(group);
                    studentGroupMapper.deleteGroupMember(oldStudent.getStudentId(),group.getGroupId());
                    return ResultInfo.success("管理员转让成功");
                } else {
                    return ResultInfo.fail("不是管理员或新管理员不是团队成员,请正确操作");
                }
            } else {
                //判断是否是管理员 如果是 && 新管理员是这个团队中的成员
                if (group.getStudentCode().equals(userCode) && studentGroup != null ) {
                    group.setStudentCode(transferManagerDto.getXinAdministratorCode());
                    groupMapper.updateByPrimaryKeySelective(group);
                    return ResultInfo.success("管理员转让成功");
                } else {
                    return ResultInfo.fail("请正确操作");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("操作失败");
        }
    }

    @Override
    public ResultInfo selectAllGroupByGroupId(SelectGroup selectGroup) {
        try {
            String userToken = selectGroup.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            Group group = groupMapper.selectGroupByGroupId(selectGroup.getGroupId());
            List<Student> studentList = groupMapper.selectStudentByGroupId(selectGroup.getGroupId());
            group.setStudent(studentList);
            return ResultInfo.success(group);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("查询失败");
        }
    }

}
