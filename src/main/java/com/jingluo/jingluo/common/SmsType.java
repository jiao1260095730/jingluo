package com.jingluo.jingluo.common;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 16:10
 */
public enum  SmsType {
    bindcode(1),
    findcode(2);

    private int code;

    private SmsType(int code){
        this.code=code;
    }

    public int getCode(){
        return code;
    }
}
