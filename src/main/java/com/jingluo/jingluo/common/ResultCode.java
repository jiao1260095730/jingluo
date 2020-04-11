package com.jingluo.jingluo.common;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/11 15:56
 */
public enum ResultCode {
    SUCCESS(200),FAIL(400);
    private int code;
    private ResultCode(int v){
        code=v;
    }
    public int getCode(){
        return code;
    }
}
