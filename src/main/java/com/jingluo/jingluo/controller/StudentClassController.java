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
 * @Date 2020/5/30 23:41
 */
@RestController
@Api(value = "学生的班级操作", tags = "学生的班级操作")
@CrossOrigin
public class StudentClassController {

    @ApiOperation(value = "获取班级信息", notes = "获取班级信息")
    @PostMapping("api/student/class/getClassMsg.do")
    public ResultInfo getClassMsg(@RequestParam String json) {

        return ResultInfo.success();
    }
}
