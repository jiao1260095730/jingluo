package com.jingluo.jingluo.service;

import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.vo.ResultInfo;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/8 19:48
 */
public interface StudentService {

    ResultInfo selectAll();

    ResultInfo insertStu(Student dto);

    ResultInfo getUniqueId();
}
