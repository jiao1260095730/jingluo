package com.jingluo.jingluo.dao;

import com.jingluo.jingluo.entity.Student;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/8 19:55
 */
public interface StudentDao {

    @Select("select * from student order by id desc")
    @ResultType(Student.class)
    List<Student> selectAll();
}
