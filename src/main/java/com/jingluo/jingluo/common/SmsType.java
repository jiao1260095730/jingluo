package com.jingluo.jingluo.common;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 16:10
 */
public enum  SmsType {
    //绑定手机号发送的验真码标识
    BIND_CODE(1),
    //找回密码时发送的验真码标识
    FIND_CODE(2),
    //手机号登录时发送的验证码标识
    LOGIN_CODE(3);

    private int code;

    private SmsType(int code){
        this.code=code;
    }

    public int getCode(){
        return code;
    }
}
