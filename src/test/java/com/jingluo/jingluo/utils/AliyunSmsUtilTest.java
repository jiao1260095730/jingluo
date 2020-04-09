package com.jingluo.jingluo.utils;

import org.junit.Test;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/9 20:45
 */
public class AliyunSmsUtilTest {

    @Test
    public void testSendSms() {
        try {
            String phone = "13213538971";
            int code = 123456;
            if (AliyunSmsUtil.sendSms(phone, code)){
                System.out.println(1);
            } else{
                System.out.println(0);
            }
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }

    }
}
