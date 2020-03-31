package com.jingluo.jingluo.vo;

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

    public static ReturnInfo setOK(Object data){
        ReturnInfo r=new ReturnInfo();
        r.setCode(200);
        r.setMsg("OK");
        r.setData(data);
        return r;
    }
    public static ReturnInfo setERROR(){
        ReturnInfo r=new ReturnInfo();
        r.setCode(400);
        r.setMsg("ERROR");
        r.setData(null);
        return r;
    }
}
