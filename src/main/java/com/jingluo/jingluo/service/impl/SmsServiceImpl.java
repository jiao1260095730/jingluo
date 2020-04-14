package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.RedisConfig;
import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.mapper.SmsLogMapper;
import com.jingluo.jingluo.entity.SmsLog;
import com.jingluo.jingluo.service.SmsService;
import com.jingluo.jingluo.utils.AliyunSmsUtil;
import com.jingluo.jingluo.utils.DateUtil;
import com.jingluo.jingluo.utils.NumberUtil;
import com.jingluo.jingluo.utils.RedissonUtil;
import com.jingluo.jingluo.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/9 21:30
 */
@Component
public class SmsServiceImpl implements SmsService {
    @Autowired
    private SmsLogMapper smsLogDao;

    /**
     * 发送短信验证码共用方法
     *
     * @param intType 类型，1绑定手机号   2找回密码   在SmsType中定义
     * @param strType 类型，存储在redis中key不同，在RedisConfig中定义
     * @author 焦斌
     * @date 2020/4/12
     */
    @Override
    public ResultInfo sendSms(String phone, int intType, String strType) {

        try {
            /*
             * 设置发送信息次数限制
             *   一分钟内1次
             *   一小时内5次
             *   一天内10次
             *
             */
            int tag = 0;
            int hours = 0;
            int days = 0;
            //如果redis中存储的每天的次数记录有值，进行操作
            if (RedissonUtil.checkKey(RedisConfig.SMS_DAY + phone)) {
                days = Integer.parseInt(RedissonUtil.getStr(RedisConfig.SMS_DAY + phone));
                if (days >= 10) {
                    LoggerCommon.commonerror("当日发送次数已达10次");
                    tag = 1;
                    return ResultInfo.fail("当日发送次数已达10次，请明天再试");
                }
            } else if (RedissonUtil.checkKey(RedisConfig.SMS_HOUR + phone)) {
                hours = Integer.parseInt(RedissonUtil.getStr(RedisConfig.SMS_HOUR + phone));
            } else if (RedissonUtil.getKeys(RedisConfig.SMS_HOUR + phone + ":*") > 0) {
                hours = RedissonUtil.getKeys(RedisConfig.SMS_HOUR + phone + ":*");
                if (hours >= 5) {
                    //本小时已达上限
                    LoggerCommon.commonerror("一小时内发送次数已达5次");
                    tag = 2;
                    return ResultInfo.fail("一小时内发送次数已达5次，请稍后再试");
                }
            } else if (RedissonUtil.checkKey(RedisConfig.SMS_MINUTE + phone)) {
                LoggerCommon.commonerror("一分钟内已发送消息");
                tag = 3;
                return ResultInfo.fail("一分钟内已发送消息，请稍后再试");
            }
            if (tag == 0) {
                //1小时 5条
                //1分钟
                //1、发送短信验证码
                int code;
                //1、验证验证码是否生效
                if (RedissonUtil.checkKey(strType + phone)) {
                    code = Integer.parseInt(RedissonUtil.getStr(RedisConfig.SMS_CODE_BIND + phone));
                } else {
                    code = NumberUtil.createIntNum(6);
                }
                SmsLog smsLog = new SmsLog();
                smsLog.setFlag(1);
                // 2.发送消息
                if (AliyunSmsUtil.sendSms(phone, code)) {
                    //3、验证码 存储到Redis  String
                    RedissonUtil.setStr(strType + phone, code + "", SystemConfig.SMS_CODE_TIME * 60);
                    //4、更新各种频率的Key
                    RedissonUtil.setStr(RedisConfig.SMS_MINUTE + phone, "", 60);
                    RedissonUtil.setStr(RedisConfig.SMS_HOUR + phone, hours + 1 + "", 60 * 60);
                    RedissonUtil.setStr(RedisConfig.SMS_HOUR + phone + ":" + (hours + 1), "", 60 * 60);
                    RedissonUtil.setStr(RedisConfig.SMS_DAY + phone, days + 1 + "", DateUtil.getDaySeconds());

                    LoggerCommon.commoninfo("发送给手机号：" + phone + "的验证码为：" + code);
                    return ResultInfo.success("发送验证码成功，请注意查看");
                }
                //记录本次操作到数据库
                smsLog.setRecPhone(phone);
                smsLog.setInfo("发送给手机号：" + phone + "的验证码为：" + code);
                //type 为 1 表示为绑定手机   2 为找回密码
                smsLog.setFlag(1);
                smsLog.setType(intType);
                smsLog.setCode(code + "");
                //smsLog.setSendTime(DateUtil.getTime().);
                //smsLog.setType(SmsType.bindcode.getCode());
                smsLog.setType(intType);
                smsLogDao.insert(smsLog);
            }
            return ResultInfo.fail("发送消息失败");
        } catch (Exception e) {
            LoggerCommon.commonerror("发送短信出现异常", e);
            return ResultInfo.fail("发送短信出现异常");
        }
    }
}
