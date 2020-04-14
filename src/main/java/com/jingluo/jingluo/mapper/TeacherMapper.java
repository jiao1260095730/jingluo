package com.jingluo.jingluo.mapper;

import com.jingluo.jingluo.entity.Teacher;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 13:36
 */
public interface TeacherMapper {

    Teacher selectByCode(String userCode);

    int insert(Teacher teacher);

    int update(Teacher teacher);
}
