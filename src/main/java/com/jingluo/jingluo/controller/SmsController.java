package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.common.SmsType;
import com.jingluo.jingluo.config.RedisConfig;
import com.jingluo.jingluo.service.SmsService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/9 21:29
 */
@RestController
@Api(value = "短信服务", tags = "实现各种短信功能")
@CrossOrigin
public class SmsController {

    @Autowired
    private SmsService smsService;


    @ApiOperation(value = "绑定手机时发送验证码", notes = "绑定手机号时发送验证码给指定手机号")
    @PostMapping("api/sms/sendBindCode")
    public ResultInfo sendBindSms(@RequestParam String phone) {
        return smsService.sendSms(phone, SmsType.bindcode.getCode(), RedisConfig.SMS_CODE_BIND);
    }


    @ApiOperation(value = "找回密码时发送验证码", notes = "找回密码时发送验证码给指定手机号")
    @PostMapping("api/sms/sendFindCode")
    public ResultInfo sendFindSms(@RequestParam String phone) {
        return smsService.sendSms(phone, SmsType.findcode.getCode(), RedisConfig.SMS_CODE_FIND);
    }

}
