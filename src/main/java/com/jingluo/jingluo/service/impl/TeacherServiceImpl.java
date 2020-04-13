package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.mapper.TeacherMapper;
import com.jingluo.jingluo.service.TeacherService;
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
}
