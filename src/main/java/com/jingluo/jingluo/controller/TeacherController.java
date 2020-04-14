package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.UserType;
import com.jingluo.jingluo.dto.UserDto;
import com.jingluo.jingluo.service.TeacherService;
import com.jingluo.jingluo.service.UserService;
import com.jingluo.jingluo.vo.ReturnInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 教师的相关操作controller
 * @Author 焦斌
 * @Date 2020/4/12 13:32
 */
@RestController
@Api(value = "教师的相关操作", tags = "教师的相关操作")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "教师登录", notes = "教师登录")
    @PostMapping("api/teacher/login.do")
    public ReturnInfo login(UserDto userDto) {
        return userService.login(userDto, UserType.teacher.getCode());
    }

    @ApiOperation(value = "教师绑定手机号", notes = "教师绑定手机号")
    @PostMapping("api/teacher/bindPhone.do")
    public ReturnInfo bindPhone(@RequestBody UserDto userDto) {
        return userService.bindPhone(userDto, UserType.teacher.getCode());
    }

    @ApiOperation(value = "教师修改密码", notes = "教师修改密码，可支持使用旧密码修改和找回密码中使用验证码修改")
    @PostMapping("api/teacher/updatePSW.do")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode",value = "教师工号", required = true,dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oldPassword",value = "旧密码", required = false,dataType = "String"),
            @ApiImplicitParam(name = "validateCode",value = "验证码", required = false,dataType = "String")})
    public ReturnInfo updatePSW(@RequestBody UserDto userDto) {
        return userService.updatePassword(userDto, UserType.teacher.getCode());
    }


}
