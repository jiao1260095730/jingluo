package com.jingluo.jingluo.mapper;

import com.jingluo.jingluo.entity.StudentGroup;
import org.apache.ibatis.annotations.Param;

public interface StudentGroupMapper {
    StudentGroup selectStundentGroup(StudentGroup sg);

    void deleteGroupMember(@Param("studentId") Integer studentId,@Param("groupId") Integer groupId);
}
