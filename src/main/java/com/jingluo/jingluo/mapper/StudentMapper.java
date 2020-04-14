package com.jingluo.jingluo.mapper;

import com.jingluo.jingluo.entity.Student;

import java.util.List;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/8 19:55
 */
public interface StudentMapper {

    Student selectByCode(String userCode);

    List<Student> selectAll();

    int insert(Student student);

    int update(Student student);
}
