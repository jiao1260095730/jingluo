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
 * @Description 学生对知识库的相关操作
 * @Author 焦斌
 * @Date 2020/5/2 18:55
 */
@Api(value = "学生的知识库操作",tags = "学生的知识库操作")
@RestController
public class StudentKnowBaseController {

    @Autowired
    private KnowBaseService knowBaseService;

//    @ApiOperation(value = "创建个人知识库", notes = "学生创建个人知识库" )
//    @PostMapping("api/student/knowBase/createPersonKnowBase.do")
//    public ResultInfo createPersonKnowBase(@RequestBody KnowBaseCreateDto dto) {
//        return knowBaseService.createKnowBase(dto);
//    }
//
//    @ApiOperation(value = "创建团队知识库", notes = "学生创建团队知识库" )
//    @PostMapping("api/student/knowBase/createGroupKnowBase.do")
//    public ResultInfo createGroupKnowBase(@RequestBody KnowBaseCreateDto dto) {
//        return knowBaseService.createKnowBase(dto);
//    }
}
