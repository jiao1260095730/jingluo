package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.UserType;
<<<<<<< HEAD
import com.jingluo.jingluo.dto.UserLoginDto;
import com.jingluo.jingluo.dto.UserUpdatePSWDto;
import com.jingluo.jingluo.dto.UserValidDto;
=======
import com.jingluo.jingluo.dto.UserDto;
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
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
<<<<<<< HEAD
    public ReturnInfo login(UserLoginDto userDto) {
=======
    public ReturnInfo login(UserDto userDto) {
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
        return userService.login(userDto, UserType.teacher.getCode());
    }

    @ApiOperation(value = "教师绑定手机号", notes = "教师绑定手机号")
    @PostMapping("api/teacher/bindPhone.do")
<<<<<<< HEAD
    public ReturnInfo bindPhone(@RequestBody UserValidDto userDto) {
        return userService.bindPhone(userDto, UserType.teacher.getCode());
    }

    @ApiOperation(value = "教师修改密码", notes = "教师修改密码，可支持使用旧密码修改和找回密码中使用验证码修改" +
            ",若使用旧密码修改，则输入工号、新密码、旧密码；若为使用验证码修改则输入工号、新密码、验证码")
=======
    public ReturnInfo bindPhone(@RequestBody UserDto userDto) {
        return userService.bindPhone(userDto, UserType.teacher.getCode());
    }

    @ApiOperation(value = "教师修改密码", notes = "教师修改密码，可支持使用旧密码修改和找回密码中使用验证码修改")
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
    @PostMapping("api/teacher/updatePSW.do")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode",value = "教师工号", required = true,dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oldPassword",value = "旧密码", required = false,dataType = "String"),
            @ApiImplicitParam(name = "validateCode",value = "验证码", required = false,dataType = "String")})
<<<<<<< HEAD
    public ReturnInfo updatePSW(@RequestBody UserUpdatePSWDto userDto) {
=======
    public ReturnInfo updatePSW(@RequestBody UserDto userDto) {
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
        return userService.updatePassword(userDto, UserType.teacher.getCode());
    }


}
