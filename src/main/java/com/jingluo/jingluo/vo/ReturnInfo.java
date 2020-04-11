package com.jingluo.jingluo.vo;

import com.jingluo.jingluo.common.ResultCode;
import lombok.Data;

/**
* 返回信息实体
* @author 焦斌
* @date 2020/3/28
*/
@Data
public class ReturnInfo {
    private int code;
    private String msg;
    private Object data;

    public static ReturnInfo success(String msg,Object obj){
        ReturnInfo r = new ReturnInfo();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMsg(msg);
        r.setData(obj);
        return r;
    }
    public static ReturnInfo success(Object obj){
        return success("OK",obj);
    }
    public static ReturnInfo success(){
        return success(null);
    }
    public static ReturnInfo fail(String msg){
        ReturnInfo r = new ReturnInfo();
        r.setCode(ResultCode.FAIL.getCode());
        r.setMsg(msg);
        r.setData(null);
        return r;
    }
    public static ReturnInfo fail(){
        return fail("FAIL");
    }

    public static ReturnInfo setR(boolean issuccess,Object obj){
        if(issuccess){
            return success(obj);
        }else {
            return fail();
        }
    }
}
