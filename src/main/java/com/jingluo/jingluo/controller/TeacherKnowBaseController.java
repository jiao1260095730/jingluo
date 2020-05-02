package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.dto.KnowBaseCreateDto;
import com.jingluo.jingluo.service.KnowBaseService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 教师对知识库的相关操作
 * @Author 焦斌
 * @Date 2020/5/2 19:01
 */
@RestController
@Api(value = "教师的知识库操作", tags = "教师的知识库操作")
public class TeacherKnowBaseController {

    @Autowired
    private KnowBaseService knowBaseService;

    @ApiOperation(value = "创建课程知识库", notes = "教师创建课程知识库" )
    @PostMapping("api/teacher/knowBase/createCourseKnowBase.do")
    public ResultInfo createCourseKnowBase(@RequestBody KnowBaseCreateDto dto) {
        return knowBaseService.createKnowBase(dto);
    }
}
