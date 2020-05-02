package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.dto.KnowBaseCreateDto;
import com.jingluo.jingluo.entity.KnowBase;
import com.jingluo.jingluo.service.KnowBaseService;
import com.jingluo.jingluo.utils.TokenUtil;
import com.jingluo.jingluo.vo.ResultInfo;
import org.springframework.stereotype.Component;

/**
 * @Description 知识库业务类
 * @Author 焦斌
 * @Date 2020/5/2 17:24
 */
@Component
public class KnowBaseServiceImpl implements KnowBaseService {

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
        String isPublic = dto.getIsPublic();
        int groupId = dto.getGroupId();
        int courseId = dto.getCourseId();
        String baseInfo = dto.getBaseInfo();
        //知识库种类 1文档知识库；2画板知识库；3模板知识库
        String baseKind = dto.getBaseKind();

        KnowBase knowBase = new KnowBase();

        //校验token是否失效
        if (TokenUtil.tokenValidate(userToken, userCode)) {
            return ResultInfo.fail("登录已失效，请重新登陆");
        }

        if ("1".equals(createFrom)) {
            //个人创建知识库
            knowBase.setReserved1(SystemConfig.CREATE_FROM_PERSON);
            knowBase.setBaseId(111);
            knowBase.setBaseName(baseName);
            //关联学生，使用预留字段2 关联学生userCode
            knowBase.setReserved2(userCode);
        }
        return null;
    }
}
