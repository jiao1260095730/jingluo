package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.vo.ReturnInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/3/28 19:54
 */
@RestController
@Api(value = "实现天气的各种操作",tags = "实现天气的各种操作")
public class AController {

    @ApiOperation(value = "实现天气的新增",notes = "实现天气的新增")
    @GetMapping("api/weather/queryall.do")
    public ReturnInfo save(){
        System.out.println("111");
        ReturnInfo r = new ReturnInfo();
        r.setMsg("111");
        r.setCode(1);
        return r;
    }
}

