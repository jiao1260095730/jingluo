package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.dto.knowbasedto.*;
import com.jingluo.jingluo.service.KnowBaseService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "创建知识库", notes = "学生创建个人/团队知识库 createFrom字段： 1个人 2团队" )
    @PostMapping("api/student/knowBase/createKnowBase.do")
    public ResultInfo createPersonKnowBase(@RequestBody KnowBaseCreateDto dto) {
        return knowBaseService.createKnowBase(dto);
    }

    @ApiOperation(value = "展示知识库列表", notes = "学生展示相关的所有知识库(个人和团队关联的知识库)" )
    @PostMapping("api/student/knowBase/showAllKnowBase.do")
    public ResultInfo showAllKnowBase(@RequestBody KnowBaseShowDto dto) {
        return knowBaseService.showAllKnowBase(dto);
    }

    @ApiOperation(value = "根据关键字查询知识库", notes = "根据关键字查询知识库" )
    @PostMapping("api/student/knowBase/selectKnowBaseBykeys.do")
    public ResultInfo selectKnowBaseBykeys(String userToken, String keyWord) {
        return knowBaseService.selectKnowBaseBykeys(userToken, keyWord);
    }

    @ApiOperation(value = "创建目录和文档", notes = "创建目录和文档" )
    @PostMapping("api/student/knowBase/createDirAndDoc.do")
    public ResultInfo createDirAndDoc(DirDocCreateDto dto) {
        return knowBaseService.createDirAndDoc(dto);
    }

    @ApiOperation(value = "查询目录文档", notes = "查询目录文档" )
    @PostMapping("api/student/knowBase/showAllDirAndDoc.do")
    public ResultInfo showAllDirAndDoc(@RequestBody DirectoryShowDto dto) {
        return knowBaseService.showAllDirAndDoc(dto);
    }

    @ApiOperation(value = "删除一个文档", notes = "删除一个文档" )
    @DeleteMapping("api/student/knowBase/delOneDoc.do")
    public ResultInfo delOneDoc(@RequestBody DocDeleteDto dto) {
        return knowBaseService.delOneDoc(dto);
    }

    @ApiOperation(value = "删除一个知识库", notes = "删除一个知识库" )
    @DeleteMapping("api/student/knowBase/delOneKnowBase.do")
    public ResultInfo delOneKnowBase(@RequestBody KnowBaseDelDto dto) {
        return knowBaseService.delOneKnowBase(dto);
    }

    @ApiOperation(value = "展示回收站中的知识库", notes = "展示回收站中的知识库" )
    @PostMapping("api/student/knowBase/showAllKBInDel.do")
    public ResultInfo showAllKBInDel(@RequestBody KnowBaseShowDto dto) {
        return knowBaseService.showAllKBInDel(dto);
    }

    @ApiOperation(value = "彻底删除一个知识库", notes = "彻底删除一个知识库" )
    @DeleteMapping("api/student/knowBase/delOneKnowBaseReally.do")
    public ResultInfo delOneKnowBaseReally(@RequestBody KnowBaseDelDto dto) {
        return knowBaseService.delOneKnowBaseReally(dto);
    }
}
