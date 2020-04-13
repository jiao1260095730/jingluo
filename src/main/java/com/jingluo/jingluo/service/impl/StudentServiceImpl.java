package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.service.StudentService;
import com.jingluo.jingluo.vo.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public ReturnInfo queryAll() {
        return ReturnInfo.success(dao.selectAll());
    }
}
