package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.UserType;
import com.jingluo.jingluo.dto.*;
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

    @ApiOperation(value = "学号登录", notes = "学生使用学号、密码登录")
    @PostMapping("api/student/login.do")
    public ResultInfo login(@RequestBody UserLoginDto userDto) {
        return userService.login(userDto, stuType);
    }

    @ApiOperation(value = "绑定手机号", notes = "学生绑定手机号，输入发送给手机的验证码")
    @PostMapping("api/student/bindPhone.do")
    public ResultInfo bindPhone(@RequestBody UserValidDto userDto) {
        return userService.bindPhone(userDto, stuType);
    }

    @ApiOperation(value = "修改密码", notes = "学生修改密码，输入旧密码修改")
    @PostMapping("api/student/updatePSW.do")
    public ResultInfo updatePSW(@RequestBody UpdatePSWDTO userDto) {
        return userService.updatePassword(userDto, stuType);
    }

    @ApiOperation(value = "找回密码", notes = "学生修改密码，使用手机验证码修改，用于忘记密码时找回密码")
    @PostMapping("api/student/findPSW.do")
    public ResultInfo findPSW(@RequestBody FindPSWDTO findPSWDTO) {
        return userService.findPassword(findPSWDTO, stuType);
    }

    @ApiOperation(value = "查询所有学生",notes = "查询所有学生")
    @GetMapping("api/student/selectAll.do")
    public ResultInfo selectAll(){
        return studentService.selectAll();
    }

    @ApiOperation(value = "退出登陆", notes = "学生退出登陆")
    @PostMapping("api/student/logOut.do")
    public ResultInfo logOut(@RequestBody TokenDto tokenDto) {
        return userService.logOut(tokenDto, stuType);
    }

    @ApiOperation(value = "手机号登录", notes = "学生登录，使用手机号、验证码")
    @PostMapping("api/student/phoneLogin.do")
    public ResultInfo phoneLogin(@RequestBody UserPhoneLoginDto userDto) {
        return userService.phoneLogin(userDto, stuType);
    }

    @ApiOperation(value = "获取学生信息", notes = "根据学生token查询学生信息")
    @PostMapping("api/student/selectOne.do")
    public ResultInfo selectOne(@RequestParam String token) {
        return userService.selectOne(token, stuType);
    }
}

