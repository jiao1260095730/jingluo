package com.jingluo.jingluo.utils;

import com.jingluo.jingluo.config.RedisConfig;
import com.jingluo.jingluo.config.SystemConfig;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/9 22:52
 */
public class RedissonUtilTest {

    private String phone = "13213538971";

    private static RedissonClient client;

    //sms:validCode:bind:
    private String strType = RedisConfig.SMS_CODE_BIND;

    static {
        //设置用户名密码
        Config config = new Config();
        config.useSingleServer().setAddress(SystemConfig.REDIS_HOST).setPassword(SystemConfig.REDIS_PASS).setConnectionPoolSize(64);
        client = Redisson.create(config);
    }

    @Test
    public void testStr() {
        for (int i = 0; i < 10; i++) {

            String key = "phone1";
            String value = "13213538971";
            RedissonUtil.setStr(key, value, 20);

            if (RedissonUtil.checkKey(key)) {
                System.out.println(RedissonUtil.getStr(key));
            }
        }
    }

    @Test
    public void testDel() {
        String key = "phone1";
        RedissonUtil.delKey(key);
    }

    @Test
    public void testCheck() {
        String key = RedisConfig.SMS_CODE_BIND + "13213538971";
        System.out.println(RedissonUtil.checkKey(key));
    }

    @Test
    public void testChecks() {
        String key1 = RedisConfig.SMS_HOUR + phone + ":*";
        String key2 = RedisConfig.SMS_DAY + phone;
        System.out.println(RedissonUtil.getKeys(key2));

        //long l = client.getKeys().getKeysByPattern(key2).spliterator().estimateSize();
        long l = client.getKeys().getKeysByPattern(key2).spliterator().estimateSize();
        System.out.println(l);
    }

    @Test
    public void testGet() {
        System.out.println(RedissonUtil.checkKey(RedisConfig.SMS_DAY + phone));
        System.out.println(RedissonUtil.getStr(RedisConfig.SMS_DAY + phone));
    }
}
