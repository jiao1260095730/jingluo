package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.entity.Teacher;
import com.jingluo.jingluo.mapper.TeacherMapper;
import com.jingluo.jingluo.service.TeacherService;
import com.jingluo.jingluo.utils.NumberUtil;
import com.jingluo.jingluo.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 13:34
 */
@Component
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper dao;

    @Override
    public ResultInfo selectAll() {
        return ResultInfo.success(dao.selectAll());
    }

    @Override
    public ResultInfo insertTeacher(Teacher dto) {
        dto.setPassword(NumberUtil.getMd5Str(dto.getPassword()));
        int insert = dao.insert(dto);
        if (insert > 0) {
            return ResultInfo.success("添加成功");
        }
        return ResultInfo.fail("添加失败");
    }
}
