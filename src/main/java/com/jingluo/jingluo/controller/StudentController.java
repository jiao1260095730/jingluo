package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.service.StudentService;
import com.jingluo.jingluo.vo.ReturnInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/3/28 19:54
 */
@RestController
@Api(value = "查询学生列表信息",tags = "查询学生列表信息")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "查询学生列表",notes = "查询学生列表")
    @GetMapping("api/student/queryall.do")
    public ReturnInfo queryAllStudent(){
        return studentService.queryAll();
    }
}

