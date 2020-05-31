package com.jingluo.jingluo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.dto.knowbasedto.*;
import com.jingluo.jingluo.dto.commondto.Page;
import com.jingluo.jingluo.dto.knowbasemodel.DirDocMsg;
import com.jingluo.jingluo.dto.knowbasemodel.Docs;
import com.jingluo.jingluo.dto.knowbasemodel.FirstMenu;
import com.jingluo.jingluo.dto.knowbasemodel.SecondMenu;
import com.jingluo.jingluo.entity.*;
import com.jingluo.jingluo.mapper.*;
import com.jingluo.jingluo.service.KnowBaseService;
import com.jingluo.jingluo.utils.IdCode;
import com.jingluo.jingluo.utils.StringUtil;
import com.jingluo.jingluo.utils.TokenUtil;
import com.jingluo.jingluo.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 知识库业务类
 * @Author 焦斌
 * @Date 2020/5/2 17:24
 */
@Component
@Transactional
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

    @Autowired
    private KnowDocMapper knowDocMapper;

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

        int baseId = IdCode.id();

        knowBase.setCreateTime(new Date());
        knowBase.setBaseId(baseId);
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

            if (knowBaseMapper.insertSelective(knowBase) == 1) {
                LoggerCommon.info("创建个人知识库成功");
                return ResultInfo.success("创建个人知识库成功, base_id : " + baseId, baseId);
            }
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
            return ResultInfo.success("创建团队知识库成功, base_id : " + baseId, baseId);
        }
        if (SystemConfig.CREATE_FROM_COURSE.equals(createFrom)) {
            //教师从课程创建知识库
            knowBase.setCourseId(courseId);
            knowBase.setReserved1(SystemConfig.CREATE_FROM_COURSE);
            //查询课程，并设置知识库的关联parentName为课程name
            Course course = courseMapper.selectCourseByCourseId(courseId);
            knowBase.setParentName(course.getCourseName());
            knowBaseMapper.insertSelective(knowBase);
            return ResultInfo.success("创建课程知识库成功, base_id : " + baseId, baseId);
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

            List<KnowBase> result = new ArrayList<>();
            List<KnowBase> resultKB = getKnowBases(userCode);

            for (KnowBase knowBase : resultKB) {
                if ("0".equals(knowBase.getIsDelete())) {
                    result.add(knowBase);
                }
            }

            //实现分页功能
            Page page = new Page();
            page.setCurrentPage(dto.getPage());
            //每页的开始数
            page.setStart((page.getCurrentPage() - 1) * page.getPageSize());
            //list的大小
            int count = result.size();
            //设置总页数
            page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
            //对list进行截取
            page.setDataList(result.subList(page.getStart()
                    , count - page.getStart() > page.getPageSize() ? page.getStart() + page.getPageSize() : count));

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

            List<KnowBase> result = new ArrayList<>();
            for (KnowBase knowBase : resultKB) {
                if ("0".equals(knowBase.getIsDelete())) {
                    result.add(knowBase);
                }
            }

            LoggerCommon.info("查询成功，集合：" + result);
            return ResultInfo.success(result);
        } catch (Exception e) {
            LoggerCommon.error("出现异常");
            return ResultInfo.fail("出现异常, 查询失败");
        }
    }

    //创建目录和文档
    @Override
    public ResultInfo createDirAndDoc(DirDocCreateDto dto) {
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
        /*String jsonStr = dto.getDirDocMsgJson();
        JSONObject json =  JSON.parseObject(jsonStr);
        DirDocMsg dirDocMsg = JSONObject.toJavaObject(json, DirDocMsg.class);*/
        String json = dto.getDirDocMsgJson();
        DirDocMsg dirDocMsg = JSONObject.parseObject(json, DirDocMsg.class);
        LoggerCommon.info("转换后的实体：" + dirDocMsg);
        //获取每个子对象集合
        List<FirstMenu> firstMenu = dirDocMsg.getFirstMenu();
        List<SecondMenu> secondMenu = dirDocMsg.getSecondMenu();
        List<Docs> docs = dirDocMsg.getDocs();
        //遍历一级目录对象
        for (FirstMenu menu : firstMenu) {
            Directory directory = new Directory();
            //设置业务主键为传入的id
            directory.setDirId(Integer.valueOf(menu.getId()));
            //设置目录中的知识库id
            directory.setBaseId(knowBaseId);
            directory.setParentId(0);
            directory.setDirTitle(menu.getTitle());
            if (directoryMapper.insert(directory) != 0) {
                LoggerCommon.info("以及目录  目录表dir_id为：" + menu.getId() + "的目录插入目录表成功");
            } else {
                LoggerCommon.error("一级目录落表失败");
            }
        }
        //遍历二级目录对象
        for (SecondMenu menu : secondMenu) {
            Directory directory = new Directory();
            //设置业务主键为传入的id
            directory.setDirId(Integer.valueOf(menu.getId()));

            //directory.setBaseId(knowBaseId);  二级目录不能关联知识库
            directory.setParentId(0);
            directory.setDirTitle(menu.getTitle());
            directory.setParentId(Integer.valueOf(menu.getParentId()));
            if (directoryMapper.insert(directory) != 0) {
                LoggerCommon.info("二级目录  目录表dir_id为：" + menu.getId() + "的目录插入目录表成功");
            } else {
                LoggerCommon.error("二级目录落表失败");
            }
        }
        //遍历文档对象
        for (Docs doc : docs) {
            KnowDoc knowDoc = new KnowDoc();
            //业务主键
            knowDoc.setDocId(Integer.valueOf(doc.getId()));
            //knowDoc.setBaseId();    暂时不设置文档直属于知识库的情况
            knowDoc.setDirId(Integer.valueOf(doc.getId()));
            knowDoc.setAuthorId(Integer.valueOf(doc.getAuthorId()));
            knowDoc.setAuthorName(doc.getAuthor());
            //文章标题
            knowDoc.setDocTitle(doc.getTitle());
            //文章内容
            knowDoc.setDocArticle(doc.getArticle());
            //更新时间
            knowDoc.setUpdateTime(new Date());
            knowDoc.setCreateTime(new Date());
            knowDoc.setReadNum(1);
            knowDoc.setYestNum(0);
            knowDoc.setCollectNum(0);
            knowDoc.setAttNum(0);
            knowDoc.setIsDelete("0");
            if (knowDocMapper.insert(knowDoc) != 0) {
                LoggerCommon.info("文档doc_id为：" + doc.getId() + "的文档插入文档表成功");
            } else {
                LoggerCommon.error("文档表落表失败");
            }
        }
        return ResultInfo.success("目录和文档入表成功");
    }

    //展示选定知识库下所有文档
    @Override
    public ResultInfo showAllDirAndDoc(DirectoryShowDto dto) {
        try {
            String userToken = dto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            Integer knowBaseId = dto.getKnowBaseId();
            //查询所有以及目录对象
            List<Directory> firstDirs = directoryMapper.selectAllFirstDirs(knowBaseId);
            //通过一级目录id查询二级目录对象
            List<Directory> secondDirs = new ArrayList<>();
            for (Directory directory : firstDirs) {
                Integer dirId = directory.getDirId();
                List<Directory> directories = directoryMapper.selectAllSecondDirs(dirId);
                secondDirs.addAll(directories);
            }
            List<KnowDoc> knowDocs = new ArrayList<>();
            for (Directory secondDir : secondDirs) {
                Integer dirId = secondDir.getDirId();
                List<KnowDoc> knowDocs1 = knowDocMapper.selectAllDocs(dirId);
                knowDocs.addAll(knowDocs1);
            }

            //创建返回对象的model
            DirDocMsg dirDocMsg = new DirDocMsg();

            List<FirstMenu> firstMenus = new ArrayList<>();
            for (Directory firstDir : firstDirs) {
                FirstMenu firstMenu = new FirstMenu();
                firstMenu.setId(firstDir.getDirId() + "");
                firstMenu.setTitle(firstDir.getDirTitle());
                firstMenus.add(firstMenu);
            }
            dirDocMsg.setFirstMenu(firstMenus);

            List<SecondMenu> secondMenus = new ArrayList<>();
            for (Directory secondDir : secondDirs) {
                SecondMenu secondMenu = new SecondMenu();
                secondMenu.setId(secondDir.getDirId() + "");
                secondMenu.setTitle(secondDir.getDirTitle());
                secondMenu.setParentId(secondDir.getParentId() + "");
                secondMenus.add(secondMenu);
            }
            dirDocMsg.setSecondMenu(secondMenus);

            List<Docs> docses = new ArrayList<>();
            for (KnowDoc knowDoc : knowDocs) {
                Docs docs = new Docs();
                docs.setId(knowDoc.getDocId() + "");
                docs.setTitle(knowDoc.getDocTitle());
                docs.setAuthor(knowDoc.getAuthorName());
                docs.setParentId(knowDoc.getDirId() + "");
                docs.setCreatTime(StringUtil.dateFormat(knowDoc.getCreateTime()));
                docs.setUpdateTime(StringUtil.dateFormat(knowDoc.getUpdateTime()));
                docs.setAuthorId(knowDoc.getAuthorId() + "");
                docs.setArticle(knowDoc.getDocArticle());
                docses.add(docs);
            }
            dirDocMsg.setDocs(docses);
            //String json = (String)JSONObject.toJSON(dirDocMsg);
            String json = JSONObject.toJSONStringWithDateFormat(dirDocMsg, "yyyy-MM-dd HH:mm:ss");

            return ResultInfo.success("查询成功", json);
        } catch (Exception e) {
            LoggerCommon.error("查询目录文档异常");
            return ResultInfo.fail("查询目录文档异常");
        }
    }

    //删除一个文档
    @Override
    public ResultInfo delOneDoc(DocDeleteDto dto) {
        try {
            Integer docId = dto.getDocId();
            String userToken = dto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }

            if (knowDocMapper.deleteOneDoc(docId) != 1) {
                LoggerCommon.error("更新文档删除标志失败");
                return ResultInfo.fail("更新文档删除标志失败");
            }
            return ResultInfo.success("删除成功");
        } catch (Exception e) {
            LoggerCommon.error("出现异常");
            return ResultInfo.fail("出现异常");
        }
    }

    //删除一个知识库
    @Override
    public ResultInfo delOneKnowBase(KnowBaseDelDto dto) {
        try {
            String userToken = dto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            Integer baseId = dto.getBaseId();
            if (knowBaseMapper.deleteOneKnowBase(baseId) != 1) {
                LoggerCommon.error("更新知识库删除标志为已删除  失败");
                return ResultInfo.fail("更新知识库删除标志为已删除  失败");
            }
            return ResultInfo.success("删除成功");
        } catch (Exception e) {
            LoggerCommon.error("出现异常");
            return ResultInfo.fail("出现异常");
        }
    }

    //展示回收站中的知识库
    @Override
    public ResultInfo showAllKBInDel(KnowBaseShowDto dto) {
        try {
            String userToken = dto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            List<KnowBase> resultKnowBase = getKnowBases(userCode);
            List<KnowBase> result = new ArrayList<>();
            for (KnowBase knowBase : resultKnowBase) {
                //如果isDelete为1， 则为已删除
                if ("1".equals(knowBase.getIsDelete())) {
                    result.add(knowBase);
                }
            }

            //实现分页功能
            Page page = new Page();
            page.setCurrentPage(dto.getPage());
            //list的大小
            int count = result.size();
            //每页的开始数
            page.setStart((page.getCurrentPage() - 1) * page.getPageSize());
            //设置总页数
            page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
            //对list进行截取
            page.setDataList(result.subList(page.getStart()
                    , count - page.getStart() > page.getPageSize() ? page.getStart() + page.getPageSize() : count));

            LoggerCommon.info("查询成功，集合：" + page.getDataList());
            return ResultInfo.success(page);
        } catch (Exception e) {
            LoggerCommon.error("出现异常");
            return ResultInfo.fail("出现异常");
        }
    }

    //彻底删除知识库
    @Override
    public ResultInfo delOneKnowBaseReally(KnowBaseDelDto dto) {
        try {
            String userToken = dto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            Integer baseId = dto.getBaseId();
            if (knowBaseMapper.deleteOneKnowBaseReally(baseId) != 1) {
                LoggerCommon.error("彻底删除知识库操作失败");
                return ResultInfo.fail("彻底删除知识库操作失败");
            }
            return ResultInfo.success("知识库base_id为：" + baseId + "的知识库已彻底删除");
        } catch (Exception e) {
            LoggerCommon.error("出现异常");
            return ResultInfo.fail("出现异常");
        }
    }

    @Override
    public ResultInfo selectOneBaseMsg(String userToken, Integer baseId) {
        try {
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            //校验token是否失效
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                LoggerCommon.error("登录已失效，请重新登陆");
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            KnowBase knowBase = knowBaseMapper.selectknowBaseByBaseId(baseId);
            return ResultInfo.success("查询成功", knowBase);
        } catch (Exception e) {
            LoggerCommon.error("查询失败 : " + e);
            return ResultInfo.fail("查询失败 ：" + e);
        }
    }
}
