package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.UserType;
import com.jingluo.jingluo.dto.userdto.*;
import com.jingluo.jingluo.entity.Teacher;
import com.jingluo.jingluo.service.TeacherService;
import com.jingluo.jingluo.service.UserService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 教师的相关操作controller
 * @Author 焦斌
 * @Date 2020/4/12 13:32
 */
@RestController
@Api(value = "教师的用户操作", tags = "教师的用户操作")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    private int teaType = UserType.teacher.getCode();

    @ApiOperation(value = "工号登录", notes = "教师登录")
    @PostMapping("api/teacher/login.do")
    public ResultInfo login(@RequestBody UserLoginDto userDto) {
        return userService.login(userDto, teaType);
    }

    @ApiOperation(value = "绑定手机号", notes = "教师绑定手机号")
    @PostMapping("api/teacher/bindPhone.do")
    public ResultInfo bindPhone(@RequestBody UserValidDto userDto) {
        return userService.bindPhone(userDto, teaType);
    }

    @ApiOperation(value = "修改密码", notes = "教师修改密码，输入旧密码修改")
    @PostMapping("api/teacher/updatePSW.do")
    public ResultInfo updatePSW(@RequestBody UpdatePSWDTO userDto) {
        return userService.updatePassword(userDto, teaType);
    }

    @ApiOperation(value = "找回密码", notes = "教师修改密码，使用手机验证码修改，用于忘记密码时找回密码")
    @PostMapping("api/teacher/findPSW.do")
    public ResultInfo findPSW(@RequestBody FindPSWDTO findPSWDTO) {
        return userService.findPassword(findPSWDTO, teaType);
    }

    @ApiOperation(value = "查询所有教师", notes = "查询教师列表")
    @GetMapping("api/teacher/selectAll.do")
    public ResultInfo selectAll() {
        return teacherService.selectAll();
    }

    @ApiOperation(value = "退出登陆", notes = "教师退出登陆")
    @PostMapping("api/teacher/logOut.do")
    public ResultInfo logOut(@RequestBody TokenDto tokenDto) {
        return userService.logOut(tokenDto, teaType);
    }

    @ApiOperation(value = "手机号登录", notes = "教师登录，使用手机号、验证码")
    @PostMapping("api/teacher/phoneLogin.do")
    public ResultInfo phoneLogin(@RequestBody UserPhoneLoginDto userDto) {
        return userService.phoneLogin(userDto, teaType);
    }

    @ApiOperation(value = "获取教师信息", notes = "根据教师token查询学生信息")
    @PostMapping("api/teacher/selectOne.do")
    public ResultInfo selectOne(@RequestParam String token) {
        return userService.selectOne(token, teaType);
    }

    @ApiOperation(value = "修改教师信息", notes = "修改教师信息")
    @PostMapping("api/teacher/updateMsg.do")
    public ResultInfo updateMsg(@RequestBody UserUpdateMsgDto dto) {
        return userService.updateMsg(dto, teaType);
    }

    @ApiOperation(value = "添加教师", notes = "添加教师")
    @PostMapping("api/teacher/insertTea.do")
    public ResultInfo insertTea(@RequestBody Teacher dto) {
        return teacherService.insertTeacher(dto);
    }
}
