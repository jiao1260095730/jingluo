package com.jingluo.jingluo.entity;

public class StudentGroup {
    private Integer StuGroId;
    private Integer StudentId;
    private Integer GroupId;

    private String isCollect;

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public Integer getStuGroId() {
        return StuGroId;
    }

    public void setStuGroId(Integer stuGroId) {
        StuGroId = stuGroId;
    }

    public Integer getStudentId() {
        return StudentId;
    }

    public void setStudentId(Integer studentId) {
        StudentId = studentId;
    }

    public Integer getGroupId() {
        return GroupId;
    }

    public void setGroupId(Integer groupId) {
        GroupId = groupId;
    }
}
