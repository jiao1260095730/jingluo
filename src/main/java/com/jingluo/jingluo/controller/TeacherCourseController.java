package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/30 23:39
 */
@Api(value = "教师的课程操作",tags = "教师的课程操作")
@RestController
@CrossOrigin
public class TeacherCourseController {

    @ApiOperation(value = "获取课程信息", notes = "获取课程信息")
    @PostMapping("api/teacher/course/getCourseMsg.do")
    public ResultInfo getCourseMsg(@RequestParam String json) {

        return ResultInfo.success();
    }
}
