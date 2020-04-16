package com.jingluo.jingluo.vo;

import com.jingluo.jingluo.common.ResultCode;
import lombok.Data;

/**
* 返回信息实体
* @author 焦斌
* @date 2020/3/28
*/
@Data
public class ResultInfo {
    private int code;
    private String msg;
    private Object data;

    public static ResultInfo success(String msg, Object obj){
        ResultInfo r = new ResultInfo();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMsg(msg);
        r.setData(obj);
        return r;
    }
    public static ResultInfo fail(String msg, Object obj) {
        ResultInfo r = new ResultInfo();
        r.setCode(ResultCode.FAIL.getCode());
        r.setMsg(msg);
        r.setData(obj);
        return r;
    }
    public static ResultInfo fail(String msg, int code) {
        ResultInfo r = new ResultInfo();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
    public static ResultInfo fail(String msg, Object obj, int code) {
        ResultInfo r = new ResultInfo();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(obj);
        return r;
    }
    public static ResultInfo success(Object obj){
        return success("OK",obj);
    }
    public static ResultInfo success(){
        return success(null);
    }
    public static ResultInfo fail(String msg){
        ResultInfo r = new ResultInfo();
        r.setCode(ResultCode.FAIL.getCode());
        r.setMsg(msg);
        r.setData(null);
        return r;
    }
    public static ResultInfo fail(){
        return fail("FAIL");
    }

    public static ResultInfo setR(boolean issuccess, Object obj){
        if(issuccess){
            return success(obj);
        }else {
            return fail();
        }
    }
}
