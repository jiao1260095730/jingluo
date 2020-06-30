package com.jingluo.jingluo.common.base;

public enum ErrorTypeEnum {
    SYSTEM_ERROR("system_error","系统异常");

    private String code;
    private String msg;


    ErrorTypeEnum(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
