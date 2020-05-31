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
 * @Date 2020/5/30 23:38
 */
@Api(value = "学生的课程操作",tags = "学生的课程操作")
@RestController
public class StudentCourseController {


    @ApiOperation(value = "获取课程信息", notes = "获取课程信息")
    @PostMapping("api/student/course/getCourseMsg.do")
    public ResultInfo getCourseMsg(@RequestParam String json) {

        return ResultInfo.success();
    }
}
