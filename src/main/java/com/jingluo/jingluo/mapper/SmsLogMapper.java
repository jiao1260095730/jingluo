package com.jingluo.jingluo.mapper;

import com.jingluo.jingluo.entity.SmsLog;
<<<<<<< HEAD
=======
import org.apache.ibatis.annotations.Insert;
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 16:12
 */
public interface SmsLogMapper {
<<<<<<< HEAD

=======
    @Insert("insert into sms_log(recphone, code, info, type, flag, ctime, ) values" +
            " (#{recPhone}, #{code}, #{info}, #{type}, #{flag}, #{ctime})")
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
    int insert(SmsLog smsLog);
}
