package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.UserType;
import com.jingluo.jingluo.dto.FindPSWDTO;
import com.jingluo.jingluo.dto.UserLoginDto;
import com.jingluo.jingluo.dto.UpdatePSWDTO;
import com.jingluo.jingluo.dto.UserValidDto;
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
@Api(value = "教师的相关操作", tags = "教师的相关操作")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "教师登录", notes = "教师登录")
    @PostMapping("api/teacher/login.do")
    public ResultInfo login(UserLoginDto userDto) {
        return userService.login(userDto, UserType.teacher.getCode());
    }

    @ApiOperation(value = "教师绑定手机号", notes = "教师绑定手机号")
    @PostMapping("api/teacher/bindPhone.do")
    public ResultInfo bindPhone(@RequestBody UserValidDto userDto) {
        return userService.bindPhone(userDto, UserType.teacher.getCode());
    }

    @ApiOperation(value = "教师修改密码", notes = "教师修改密码，输入旧密码修改")
    @PostMapping("api/teacher/updatePSW.do")
    public ResultInfo updatePSW(@RequestBody UpdatePSWDTO userDto) {
        return userService.updatePassword(userDto, UserType.teacher.getCode());
    }

    @ApiOperation(value = "找回密码", notes = "教师修改密码，使用手机验证码修改，用于忘记密码时找回密码")
    @PostMapping("api/teacher/findPSW.do")
    public ResultInfo findPSW(@RequestBody FindPSWDTO findPSWDTO) {
        return userService.findPassword(findPSWDTO, UserType.teacher.getCode());
    }

    @ApiOperation(value = "查询教师列表",notes = "查询教师列表")
    @GetMapping("api/teacher/selectAll.do")
    public ResultInfo selectAll(){
        return teacherService.selectAll();
    }
}
