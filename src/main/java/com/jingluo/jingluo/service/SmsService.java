package com.jingluo.jingluo.service;

import com.jingluo.jingluo.vo.ResultInfo;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/9 21:30
 */
public interface SmsService {

    ResultInfo sendSms(String phone, int intType, String strType);
}
