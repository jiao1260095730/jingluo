package com.jingluo.jingluo.common;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 16:10
 */
public enum UserType {
    //学生老师通用功能识别
    student(1),
    teacher(2);

    private int code;

    private UserType(int code){
        this.code=code;
    }

    public int getCode(){
        return code;
    }
}
