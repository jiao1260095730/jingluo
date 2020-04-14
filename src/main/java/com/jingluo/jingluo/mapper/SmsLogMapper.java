package com.jingluo.jingluo.mapper;

import com.jingluo.jingluo.entity.SmsLog;
import org.apache.ibatis.annotations.Insert;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 16:12
 */
public interface SmsLogMapper {
    @Insert("insert into sms_log(recphone, code, info, type, flag, ctime, ) values" +
            " (#{recPhone}, #{code}, #{info}, #{type}, #{flag}, #{ctime})")
    int insert(SmsLog smsLog);
}
