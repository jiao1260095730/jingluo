package com.jingluo.jingluo.mapper;

import com.jingluo.jingluo.entity.Teacher;
import com.jingluo.jingluo.vo.ResultInfo;

import java.util.List;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 13:36
 */
public interface TeacherMapper {

    Teacher selectByCode(String userCode);

    int insert(Teacher teacher);

    int update(Teacher teacher);

    List<Teacher> selectAll();

    int selectBindPhone(String phone);
}
