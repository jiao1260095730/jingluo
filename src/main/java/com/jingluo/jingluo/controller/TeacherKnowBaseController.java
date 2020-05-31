package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.dto.knowbasedto.KnowBaseCreateDto;
import com.jingluo.jingluo.service.KnowBaseService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class TeacherKnowBaseController {

    @Autowired
    private KnowBaseService knowBaseService;

    @ApiOperation(value = "创建课程知识库", notes = "userToken : 教师token   createFrom ： 创建来源 1个人 2团队 3教师通过课程创建" +
            "   baseName ：知识库名称    baseInfo ： 知识库简介    baseKind ：知识库种类 1文档知识库；2画板知识库；3模板知识库" +
            "   isPublic ：可见范围 是否发布 0私有 1发布    courseId : 如果为课程创建则需记录课程id 业务主键 如果为其他创建可删除该字段" +
            "   groupId ：如果为团队创建则需记录团队id 业务主键  如果为其他创建可删除该字段" )
    @PostMapping("api/teacher/knowBase/createCourseKnowBase.do")
    public ResultInfo createCourseKnowBase(@RequestBody KnowBaseCreateDto dto) {
        return knowBaseService.createKnowBase(dto);
    }
}
