package com.jingluo.jingluo.common.base;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 杨爱民
 * @Date 2020-06-29 21:21
 * @Desc result返回值类
 */

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 4998466710143623852L;

    private boolean success;
    private String code;
    private String message;
    private T data;

    public Result(boolean success) {
        this.success = success;
    }

    public static <T> Result<T> create() {
        return new Result<T>(true);
    }

    public static <T> Result<T> create(boolean success) {
        return new Result<T>(success);
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置code和message
     */
    public Result<T> error(String code, String message) {
        this.setSuccess(false);
        this.setCode(code);
        this.setMessage(message);
        return this;
    }

    /**
     * 设置code和message
     */
    public Result<T> error(ErrorTypeEnum typeEnum) {
        this.setSuccess(false);
        this.setCode(typeEnum.getCode());
        this.setMessage(typeEnum.getMsg());
        return this;
    }

    /**
     * 设置code和message
     */
    public Result<T> error(String message) {
        this.setSuccess(false);
        this.setCode(ErrorTypeEnum.SYSTEM_ERROR.getCode());
        this.setMessage(message);
        return this;
    }

    /**
     * 设置code和message
     */
    public Result<T> success(T data) {
        this.setSuccess(true);
        this.setData(data);
        return this;
    }

    /**
     * 设置code和message
     */
    public Result<T> success(T data, String message) {
        this.setSuccess(true);
        this.setData(data);
        this.setMessage(message);
        return this;
    }
}
