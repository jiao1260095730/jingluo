package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.service.StudentService;
import com.jingluo.jingluo.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/8 19:48
 */
@Component
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper dao;

    @Override
    public ResultInfo selectAll() {
        List<Student> students = dao.selectAll();
        LoggerCommon.info("查询出的所有学生：" + students);
        return ResultInfo.success(students);
    }
}
