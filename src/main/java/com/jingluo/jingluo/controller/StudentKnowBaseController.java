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

    @ApiOperation(value = "创建知识库", notes = "userToken : 学生token   createFrom ： 创建来源 1个人 2团队 3教师通过课程创建" +
            "   baseName ：知识库名称    baseInfo ： 知识库简介    baseKind ：知识库种类 1文档知识库；2画板知识库；3模板知识库" +
            "   isPublic ：可见范围 是否发布 0私有 1发布    courseId : 如果为课程创建则需记录课程id 业务主键 如果为其他创建可删除该字段" +
            "   groupId ：如果为团队创建则需记录团队id 业务主键  如果为其他创建可删除该字段" )
    @PostMapping("api/student/knowBase/createKnowBase.do")
    public ResultInfo createPersonKnowBase(@RequestBody KnowBaseCreateDto dto) {
        return knowBaseService.createKnowBase(dto);
    }

    @ApiOperation(value = "展示知识库列表", notes = "学生展示相关的所有知识库(个人和团队关联的知识库)" +
            "   userToken ：学生token   page ：当前页码   目前写死每页10条 " )
    @PostMapping("api/student/knowBase/showAllKnowBase.do")
    public ResultInfo showAllKnowBase(@RequestBody KnowBaseShowDto dto) {
        return knowBaseService.showAllKnowBase(dto);
    }

    @ApiOperation(value = "根据关键字查询知识库", notes = "userToken : 学生token  keyWord : 搜索关键字，知识库名称" )
    @PostMapping("api/student/knowBase/selectKnowBaseBykeys.do")
    public ResultInfo selectKnowBaseBykeys(String userToken, String keyWord) {
        return knowBaseService.selectKnowBaseBykeys(userToken, keyWord);
    }

    @ApiOperation(value = "创建目录和文档", notes = "userToken : 学生token  knowBaseId ：传入知识库id  dirDocMsgJson ： 创建目录、文档的json串" )
    @PostMapping("api/student/knowBase/createDirAndDoc.do")
    public ResultInfo createDirAndDoc(@RequestBody DirDocCreateDto dto) {
        return knowBaseService.createDirAndDoc(dto);
    }

    @ApiOperation(value = "查询目录文档", notes = "userToken : 学生token   knowBaseId ：知识库id" )
    @PostMapping("api/student/knowBase/showAllDirAndDoc.do")
    public ResultInfo showAllDirAndDoc(@RequestBody DirectoryShowDto dto) {
        return knowBaseService.showAllDirAndDoc(dto);
    }

    @ApiOperation(value = "删除一个文档", notes = "userToken : 学生token   docId ：文档id" )
    @DeleteMapping("api/student/knowBase/delOneDoc.do")
    public ResultInfo delOneDoc(@RequestBody DocDeleteDto dto) {
        return knowBaseService.delOneDoc(dto);
    }

    @ApiOperation(value = "删除一个知识库", notes = "userToken : 学生token   knowBaseId ：知识库id" )
    @DeleteMapping("api/student/knowBase/delOneKnowBase.do")
    public ResultInfo delOneKnowBase(@RequestBody KnowBaseDelDto dto) {
        return knowBaseService.delOneKnowBase(dto);
    }

    @ApiOperation(value = "展示回收站中的知识库", notes = "userToken ：学生token   page ：当前页码   目前写死每页10条" )
    @PostMapping("api/student/knowBase/showAllKBInDel.do")
    public ResultInfo showAllKBInDel(@RequestBody KnowBaseShowDto dto) {
        return knowBaseService.showAllKBInDel(dto);
    }

    @ApiOperation(value = "彻底删除一个知识库", notes = "userToken : 学生token   knowBaseId ：知识库id" )
    @DeleteMapping("api/student/knowBase/delOneKnowBaseReally.do")
    public ResultInfo delOneKnowBaseReally(@RequestBody KnowBaseDelDto dto) {
        return knowBaseService.delOneKnowBaseReally(dto);
    }
}
