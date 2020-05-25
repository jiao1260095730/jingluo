package com.jingluo.jingluo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.dto.knowbasedto.DirDocCreateDto;
import com.jingluo.jingluo.dto.knowbasedto.DirectoryShowDto;
import com.jingluo.jingluo.dto.knowbasedto.KnowBaseCreateDto;
import com.jingluo.jingluo.dto.knowbasedto.KnowBaseShowDto;
import com.jingluo.jingluo.dto.commondto.Page;
import com.jingluo.jingluo.dto.knowbasemodel.DirDocMsg;
import com.jingluo.jingluo.dto.knowbasemodel.Docs;
import com.jingluo.jingluo.dto.knowbasemodel.FirstMenu;
import com.jingluo.jingluo.dto.knowbasemodel.SecondMenu;
import com.jingluo.jingluo.entity.*;
import com.jingluo.jingluo.mapper.*;
import com.jingluo.jingluo.service.KnowBaseService;
import com.jingluo.jingluo.utils.IdCode;
import com.jingluo.jingluo.utils.TokenUtil;
import com.jingluo.jingluo.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 知识库业务类
 * @Author 焦斌
 * @Date 2020/5/2 17:24
 */
@Component
public class KnowBaseServiceImpl implements KnowBaseService {

    @Autowired
    private KnowBaseMapper knowBaseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DirectoryMapper directoryMapper;

    /**
     * 创建知识库
     */
    @Override
    public ResultInfo createKnowBase(KnowBaseCreateDto dto) {
        //获取创建来源 1个人 2团队 3教师通过课程创建
        String createFrom = dto.getCreateFrom();
        //获取相关参数
        String baseName = dto.getBaseName();
        String userToken = dto.getUserToken();
        String userCode = TokenUtil.getUserCodeFormToken(userToken);

        int groupId = dto.getGroupId();
        int courseId = dto.getCourseId();
        String baseInfo = dto.getBaseInfo();
        //知识库种类 1文档知识库；2画板知识库；3模板知识库
        String baseKind = dto.getBaseKind();

        //校验token是否失效
        if (TokenUtil.tokenValidate(userToken, userCode)) {
            LoggerCommon.error("登录已失效，请重新登陆");
            return ResultInfo.fail("登录已失效，请重新登陆");
        }
        //三种创建方式共用
        KnowBase knowBase = new KnowBase();

        knowBase.setCreateTime(new Date());
        knowBase.setBaseId(IdCode.id());
        knowBase.setBaseName(baseName);
        knowBase.setBaseInfo(baseInfo);
        knowBase.setIsPublic(dto.getIsPublic() == null ? SystemConfig.KNOWBASE_PUBLIC : dto.getIsPublic());
        knowBase.setBaseKind(baseKind);
        knowBase.setDocNum(0);
        knowBase.setAttNum(1);
        knowBase.setReadNum(0);
        knowBase.setIsDelete(SystemConfig.KNOWBASE_NOT_DELETE);

        if (SystemConfig.CREATE_FROM_PERSON.equals(createFrom)) {
            //个人创建知识库
            knowBase.setCreateFrom(SystemConfig.CREATE_FROM_PERSON);
            //关联学生userCode
            knowBase.setStudentCode(userCode);
            //查询用户，设置知识库的关联parentName为学生昵称
            Student student = studentMapper.selectByCode(userCode);
            knowBase.setParentName(student.getNickName());
            knowBaseMapper.insertSelective(knowBase);
            LoggerCommon.info("创建个人知识库成功");
            return ResultInfo.success("创建个人知识库成功");
        }
        if (SystemConfig.CREATE_FROM_GROUP.equals(createFrom)) {
            //团队创建知识库
            knowBase.setGroupId(groupId);
            knowBase.setCreateFrom(SystemConfig.CREATE_FROM_GROUP);
            //是否在团队首页显示 0否 1是
            knowBase.setIsShow("1");
            //查询团队，并设置知识库的关联parentName为团队name
            Group group = groupMapper.selectGroupByGroupId(groupId);
            knowBase.setParentName(group.getGroupName());
            knowBaseMapper.insertSelective(knowBase);
            LoggerCommon.info("创建团队知识库成功");
            return ResultInfo.success("创建团队知识库成功");
        }
        if (SystemConfig.CREATE_FROM_COURSE.equals(createFrom)) {
            //教师从课程创建知识库
            knowBase.setCourseId(courseId);
            knowBase.setReserved1(SystemConfig.CREATE_FROM_COURSE);
            //查询课程，并设置知识库的关联parentName为课程name
            Course course = courseMapper.selectCourseByCourseId(courseId);
            knowBase.setParentName(course.getCourseName());
            knowBaseMapper.insertSelective(knowBase);
            return ResultInfo.success("创建课程知识库成功");
        }
        return ResultInfo.fail("创建知识库失败");
    }

    /**
     * 分页展示学生的知识库列表
     */
    @Override
    public ResultInfo showAllKnowBase(KnowBaseShowDto dto) {
        try {
            String userToken = dto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }

            List<KnowBase> resultKB = getKnowBases(userCode);

            //实现分页功能
            Page page = new Page();
            page.setCurrentPage(dto.getPage());
            //每页的开始数
            page.setStar((page.getCurrentPage() - 1) * page.getPageSize() + 1);
            //list的大小
            int count = resultKB.size();
            //设置总页数
            page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
            //对list进行截取
            page.setDataList(resultKB.subList(page.getStar()
                    , count - page.getStar() > page.getPageSize() ? page.getStar() + page.getPageSize() : count));

            LoggerCommon.info("查询成功，集合：" + page.getDataList());
            return ResultInfo.success(page);
        } catch (Exception e) {
            LoggerCommon.error("出现异常");
            return ResultInfo.fail("出现异常, 查询失败");
        }
    }

    //提取方法，获取学生个人和团队相关的知识库集合
    private List<KnowBase> getKnowBases(String userCode) {
        //创建总的知识库列表
        List<KnowBase> resultKB = new ArrayList<>();
        //查询个人下所有知识库
        List<KnowBase> knowBases1 = knowBaseMapper.selectKnowBasesByUserCode(userCode);
        List<KnowBase> knowBases2 = new ArrayList<>();
        //查询个人相关的团队的知识库
        List<Group> groups = groupMapper.selectAllGroupByUserCode(userCode);
        for (Group group : groups) {
            //遍历所有团队下相关的知识库
            knowBases2.addAll(knowBaseMapper.selectKnowBasesByGroupId(group.getGroupId()));
        }
        //汇总查询结果
        resultKB.addAll(knowBases1);
        resultKB.addAll(knowBases2);
        return resultKB;
    }

    //模糊查询知识库
    @Override
    public ResultInfo selectKnowBaseBykeys(String userToken, String keyWord) {
        try {
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            List<KnowBase> resultKB = new ArrayList<>();
            List<KnowBase> knowBases1 = knowBaseMapper.selectKBByUserCodeAndKeyWord(userCode, keyWord);
            List<KnowBase> knowBases2 = new ArrayList<>();
            List<Group> groups = groupMapper.selectAllGroupByUserCode(userCode);
            for (Group group : groups) {
                //遍历所有团队下相关的知识库
                knowBases2.addAll(knowBaseMapper.selectKBByGroupIdAndKeyWord(group.getGroupId(), keyWord));
            }
            resultKB.addAll(knowBases1);
            resultKB.addAll(knowBases2);

            LoggerCommon.info("查询成功，集合：" + resultKB);
            return ResultInfo.success(resultKB);
        } catch (Exception e) {
            LoggerCommon.error("出现异常");
            return ResultInfo.fail("出现异常, 查询失败");
        }
    }

    @Override
    public ResultInfo createDirectory(DirDocCreateDto dto) {
        String userToken = dto.getUserToken();
        String userCode = TokenUtil.getUserCodeFormToken(userToken);
        //校验token是否失效
        if (TokenUtil.tokenValidate(userToken, userCode)) {
            LoggerCommon.error("登录已失效，请重新登陆");
            return ResultInfo.fail("登录已失效，请重新登陆");
        }
        //获取知识库id
        Integer knowBaseId = dto.getKnowBaseId();
        //获取json串
        String json = dto.getDirDocMsgJson();
        DirDocMsg dirDocMsg = JSONObject.parseObject(json, DirDocMsg.class);
        LoggerCommon.info("转换后的实体：" + dirDocMsg);
        //获取每个子对象集合
        List<FirstMenu> firstMenu = dirDocMsg.getFirstMenu();
        List<SecondMenu> secondMenu = dirDocMsg.getSecondMenu();
        List<Docs> docs = dirDocMsg.getDocs();
        for (FirstMenu menu : firstMenu) {
            Directory directory = new Directory();
            //设置业务主键为传入的id
            directory.setDirId(Integer.valueOf(menu.getId()));
            //设置目录中的知识库id
            directory.setBaseId(knowBaseId);
            directory.setParentId(0);
            directory.setDirTitle(menu.getTitle());
            if (directoryMapper.insert(directory) == 0) {
                LoggerCommon.info("目录表dir_id为：" + menu.getId() + "的目录插入目录表成功");
            }
            LoggerCommon.error("一级目录落表失败");
        }

        return null;
    }

    //展示选定知识库下所有文档
    @Override
    public ResultInfo showAllDirectory(DirectoryShowDto dto) {

        return null;
    }
}
