package com.jingluo.jingluo.utils;

import com.alibaba.druid.util.StringUtils;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.RedisConfig;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/2 18:14
 */
public class TokenUtil {

    private static String stuTokenPre = RedisConfig.TOKEN_STUDENT_PRE;
    private static String teaTokenPre = RedisConfig.TOKEN_TEACHER_PRE;

    public static boolean tokenValidate(String userToken, String userCode) {
        String stuTokenKey = stuTokenPre + userCode;
        String teaTokenKey = teaTokenPre + userCode;
        //拿出Redis中token进行比较，如果不一致或者没有，返回登录
        if (!StringUtils.equals(userToken, RedissonUtil.getStr(stuTokenKey))
                && !StringUtils.equals(userToken, RedissonUtil.getStr(teaTokenKey))) {
            LoggerCommon.error("登录已失效，请重新登陆");
            return true;
        }
        return false;
    }
}
