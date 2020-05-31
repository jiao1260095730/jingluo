package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.UserType;
import com.jingluo.jingluo.dto.userdto.*;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.service.StudentService;
import com.jingluo.jingluo.service.UserService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 学生的相关操作controller
 * @Author 焦斌
 * @Date 2020/3/28 19:54
 */
@RestController
@Api(value = "学生的用户操作",tags = "学生的用户操作")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    private int stuType = UserType.student.getCode();

    @ApiOperation(value = "学号登录", notes = "userCode : 学生学号      password ： 密码")
    @PostMapping("api/student/login.do")
    public ResultInfo login(@RequestBody UserLoginDto userDto) {
        return userService.login(userDto, stuType);
    }

    @ApiOperation(value = "绑定手机号", notes = "userToken ：学生token    phone ： 手机号     validCode ： 验证码")
    @PostMapping("api/student/bindPhone.do")
    public ResultInfo bindPhone(@RequestBody UserValidDto userDto) {
        return userService.bindPhone(userDto, stuType);
    }

    @ApiOperation(value = "修改密码", notes = "userToken : 学生token     oldPassword : 旧密码   newPassword ： 新密码")
    @PostMapping("api/student/updatePSW.do")
    public ResultInfo updatePSW(@RequestBody UpdatePSWDTO userDto) {
        return userService.updatePassword(userDto, stuType);
    }

    @ApiOperation(value = "找回密码", notes = "userCode : 学号   phone ： 手机号   validCode ： 验证码   newPassword : 新密码")
    @PostMapping("api/student/findPSW.do")
    public ResultInfo findPSW(@RequestBody FindPSWDTO findPSWDTO) {
        return userService.findPassword(findPSWDTO, stuType);
    }

    @ApiOperation(value = "查询所有学生",notes = "查询所有学生")
    @GetMapping("api/student/selectAll.do")
    public ResultInfo selectAll(){
        return studentService.selectAll();
    }

    @ApiOperation(value = "退出登陆", notes = "userToken : 学生token")
    @PostMapping("api/student/logOut.do")
    public ResultInfo logOut(@RequestParam String userToken) {
        return userService.logOut(userToken, stuType);
    }

    @ApiOperation(value = "手机号登录", notes = "phone : 手机号   validCode : 验证码")
    @PostMapping("api/student/phoneLogin.do")
    public ResultInfo phoneLogin(@RequestBody UserPhoneLoginDto userDto) {
        return userService.phoneLogin(userDto, stuType);
    }

    @ApiOperation(value = "获取学生信息", notes = "userToken : 学生token")
    @PostMapping("api/student/selectOne.do")
    public ResultInfo selectOne(@RequestParam String userToken) {
        return userService.selectOne(userToken, stuType);
    }

    @ApiOperation(value = "修改学生信息", notes = "userToken : 学生token    name : 姓名   nickName ： 昵称   headImg ：头像url")
    @PostMapping("api/student/updateMsg.do")
    public ResultInfo updateMsg(@RequestBody UserUpdateMsgDto dto) {
        return userService.updateMsg(dto, stuType);
    }

    @ApiOperation(value = "添加学生", notes = "添加学生")
    @PostMapping("api/student/insertStu.do")
    public ResultInfo insertStu(@RequestBody Student dto) {
        return studentService.insertStu(dto);
    }

    @ApiOperation(value = "获取唯一业务id", notes = "获取唯一业务id")
    @GetMapping("api/student/getUniqueId.do")
    public ResultInfo getUniqueId() {
        return studentService.getUniqueId();
    }
}

