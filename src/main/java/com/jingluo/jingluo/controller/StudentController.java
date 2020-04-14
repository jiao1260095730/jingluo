package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.UserType;
import com.jingluo.jingluo.dto.UserLoginDto;
import com.jingluo.jingluo.dto.UserUpdatePSWDto;
import com.jingluo.jingluo.dto.UserValidDto;
import com.jingluo.jingluo.service.StudentService;
import com.jingluo.jingluo.service.UserService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 学生的相关操作controller
 * @Author 焦斌
 * @Date 2020/3/28 19:54
 */
@RestController
@Api(value = "学生相关操作",tags = "学生相关操作")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "学生登录", notes = "学生登录")
    @PostMapping("api/student/login.do")
    public ResultInfo login(@RequestBody UserLoginDto userDto) {
        return userService.login(userDto, UserType.student.getCode());
    }

    @ApiOperation(value = "学生绑定手机号", notes = "学生绑定手机号")
    @PostMapping("api/student/bindPhone.do")
    public ResultInfo bindPhone(@RequestBody UserValidDto userDto) {
        return userService.bindPhone(userDto, UserType.student.getCode());
    }

    @ApiOperation(value = "学生修改密码", notes = "学生修改密码，可支持使用旧密码修改和找回密码中使用验证码修改")
    @PostMapping("api/student/updatePSW.do")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode",value = "学生学号", required = true,dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oldPassword",value = "旧密码", required = false,dataType = "String"),
            @ApiImplicitParam(name = "validateCode",value = "验证码", required = false,dataType = "String")})
    public ResultInfo updatePSW(@RequestBody UserUpdatePSWDto userDto) {
        return userService.updatePassword(userDto, UserType.student.getCode());
    }

    @ApiOperation(value = "查询学生列表",notes = "查询学生列表")
    @GetMapping("api/student/queryAll.do")
    public ResultInfo queryAllStudent(){
        return studentService.queryAll();
    }
}

