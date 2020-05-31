package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/30 23:42
 */
@Api(value = "教师的班级操作",tags = "教师的班级操作")
@RestController
public class TeacherClassController {


    @ApiOperation(value = "获取班级信息", notes = "获取班级信息")
    @PostMapping("api/teacher/class/getClassMsg.do")
    public ResultInfo getClassMsg(@RequestParam String json) {

        return ResultInfo.success();
    }
}
