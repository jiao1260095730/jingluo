package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.UserType;
import com.jingluo.jingluo.dto.FindPSWDTO;
import com.jingluo.jingluo.dto.UserLoginDto;
import com.jingluo.jingluo.dto.UpdatePSWDTO;
import com.jingluo.jingluo.dto.UserValidDto;
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
@Api(value = "学生相关操作",tags = "学生相关操作")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "学生登录", notes = "学生登录，使用学号、密码")
    @PostMapping("api/student/login.do")
    public ResultInfo login(@RequestBody UserLoginDto userDto) {
        return userService.login(userDto, UserType.student.getCode());
    }

    @ApiOperation(value = "学生绑定手机号", notes = "学生绑定手机号，输入发送给手机的验证码")
    @PostMapping("api/student/bindPhone.do")
    public ResultInfo bindPhone(@RequestBody UserValidDto userDto) {
        return userService.bindPhone(userDto, UserType.student.getCode());
    }

    @ApiOperation(value = "修改密码", notes = "学生修改密码，输入旧密码修改")
    @PostMapping("api/student/updatePSW.do")
    public ResultInfo updatePSW(@RequestBody UpdatePSWDTO userDto) {
        return userService.updatePassword(userDto, UserType.student.getCode());
    }

    @ApiOperation(value = "找回密码", notes = "学生修改密码，使用手机验证码修改，用于忘记密码时找回密码")
    @PostMapping("api/student/findPSW.do")
    public ResultInfo findPSW(@RequestBody FindPSWDTO findPSWDTO) {
        return userService.findPassword(findPSWDTO, UserType.student.getCode());
    }

    @ApiOperation(value = "查询学生列表",notes = "查询学生列表")
    @GetMapping("api/student/selectAll.do")
    public ResultInfo selectAll(){
        return studentService.selectAll();
    }
}

