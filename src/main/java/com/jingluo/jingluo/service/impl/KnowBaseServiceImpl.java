package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.dto.KnowBaseCreateDto;
import com.jingluo.jingluo.entity.KnowBase;
import com.jingluo.jingluo.mapper.KnowBaseMapper;
import com.jingluo.jingluo.service.KnowBaseService;
import com.jingluo.jingluo.utils.IdCode;
import com.jingluo.jingluo.utils.TokenUtil;
import com.jingluo.jingluo.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description 知识库业务类
 * @Author 焦斌
 * @Date 2020/5/2 17:24
 */
@Component
public class KnowBaseServiceImpl implements KnowBaseService {

    @Autowired
    private KnowBaseMapper mapper;

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
        String userCode = dto.getUserCode();
        int groupId = dto.getGroupId();
        int courseId = dto.getCourseId();
        String baseInfo = dto.getBaseInfo();
        //知识库种类 1文档知识库；2画板知识库；3模板知识库
        String baseKind = dto.getBaseKind();

        //校验token是否失效
        if (TokenUtil.tokenValidate(userToken, userCode)) {
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
            knowBase.setReserved1(SystemConfig.CREATE_FROM_PERSON);
            //关联学生，使用预留字段2 关联学生userCode
            knowBase.setReserved2(userCode);
            mapper.insertSelective(knowBase);
            return ResultInfo.success("创建个人知识库成功");
        }
        if (SystemConfig.CREATE_FROM_GROUP.equals(createFrom)) {
            //团队创建知识库
            knowBase.setGroupId(groupId);
            knowBase.setReserved1(SystemConfig.CREATE_FROM_GROUP);
            mapper.insertSelective(knowBase);
            return ResultInfo.success("创建团队知识库成功");
        }
        if (SystemConfig.CREATE_FROM_COURSE.equals(createFrom)) {
            //教师从课程创建知识库
            knowBase.setCourseId(courseId);
            knowBase.setReserved1(SystemConfig.CREATE_FROM_COURSE);
            mapper.insertSelective(knowBase);
            return ResultInfo.success("创建课程知识库成功");
        }
        return ResultInfo.fail("创建知识库失败");
    }
}
