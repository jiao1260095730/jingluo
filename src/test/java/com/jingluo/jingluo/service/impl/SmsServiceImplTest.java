package com.jingluo.jingluo.service.impl;


import com.jingluo.jingluo.Application;
import com.jingluo.jingluo.config.RedisConfig;
import com.jingluo.jingluo.service.SmsService;
import com.jingluo.jingluo.utils.RedissonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmsServiceImplTest {


    @Autowired
    private SmsService smsService;

    private String phone = "13213538971";
    private String bindStr = "sms:validCode:bind:";
    private String findStr = "sms:validCode:find:";

    @Test
    public void testSendSms() {
        System.out.println(smsService.sendSms(phone, 1, bindStr).getCode());

    }

    @Test
    public void testGetKeys() {
        System.out.println(RedissonUtil.getKeys(RedisConfig.SMS_HOUR + phone + ":*"));
    }
}
