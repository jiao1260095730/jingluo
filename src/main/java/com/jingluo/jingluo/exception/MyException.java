package com.jingluo.jingluo.exception;

import com.jingluo.jingluo.common.ErrorStatusEnum;

/**
 * @Description 自定义异常类
 * @Author 焦斌
 * @Date 2020/4/16 21:55
 */
public class MyException extends Exception{

    //错误码，在ErrorStatusEnum类中定义
    private int code;

    public MyException() {}

    public MyException(String msg) {
        super(msg);
    }

    public MyException(ErrorStatusEnum errorStatusEnum) {
        super(errorStatusEnum.getDescription());
        code = errorStatusEnum.getCode();
    }

    public MyException(String msg, Exception e) {
        super(msg, e);
    }

    public int getCode() {
        return code;
    }
}
