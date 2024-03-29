package com.jingluo.jingluo.utils;

import com.jingluo.jingluo.config.SystemConfig;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description Redisson工具，封装redis
 * @Author 焦斌
 * @Date 2020/4/9 22:39
 */
public class RedissonUtil {
    private static RedissonClient client;

    static {
        //设置用户名密码
        Config config = new Config();
        config.useSingleServer()
                .setAddress(SystemConfig.REDIS_HOST)
                .setPassword(SystemConfig.REDIS_PASS) //设置对于master节点的连接池中连接数最大为1000
                .setConnectionPoolSize(1000) //设置对于master节点的连接池中连接数最大为500
                .setConnectTimeout(30000) //同任何节点建立连接时的等待超时。时间单位是毫秒。
                .setTimeout(3000)//等待节点回复命令的时间。该时间从命令发送成功时开始计时。
                .setPingTimeout(30000)
                .setReconnectionTimeout(3000)//当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
                .setIdleConnectionTimeout(10000) //如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒
                .setPingConnectionInterval(60000);
        client = Redisson.create(config);
    }

    //新增 String：key-value
    public static void setStr(String key, String value) {
        //1、字符串类型
        client.getBucket(key).set(value);
    }

    //新增 String：key-value   设置时间 秒
    public static void setStr(String key, String value, long seconds) {
        client.getBucket(key).set(value, seconds, TimeUnit.SECONDS);
    }

    //新增 Hash：key-fields-values
    public static void putHash(String key, String field, String val) {
        client.getMap(key).put(field, val);
    }

    public static void putAllHash(String key, long seconds, Map<Object, String> map) {
        RMap rm = client.getMap(key);
        if (seconds > 0) {
            rm.expire(seconds, TimeUnit.SECONDS);
        }
        rm.putAll(map);
    }

    public static <T> void putAll(String key, long seconds, Map<Object, T> map) {
        RMap rm = client.getMap(key);
        if (seconds > 0) {
            rm.expire(seconds, TimeUnit.SECONDS);
        }
        rm.putAll(map);
    }

    //删除 String
    public static void delKey(String key) {
        client.getKeys().delete(key);
//        client.getList(key).remove();
    }

    //查询
    public static String getStr(String key) {
        String str = (String) client.getBucket(key).get();
        return str;
    }

    public static Collection<Object> getHash(String key) {
        return client.getMap(key).values();
    }

    public static Map<Object, Object> getHashAll(String key) {
        return client.getMap(key).readAllMap();
    }

    public static Object getHashVal(String key, String field) {
        return client.getMap(key).get(field);
    }

    //系统
    //设置有效期
    public static void setExpire(String key, long seconds) {
        client.getKeys().expire(key, seconds, TimeUnit.SECONDS);
    }

    //开启分布式锁  setnx
    public static void lock(String key) {
//        Lock lock=new ReentrantLock();
//        lock.lock();
//        lock.unlock();
        client.getLock(key).lock();
    }

    //释放分布式锁
    public static void unlock(String key) {
        client.getLock(key).unlock();
    }

    //验证key是否存在
    public static boolean checkKey(String key) {
        long exists = client.getKeys().countExists(key);
        return exists > 0;
    }

    public static int getKeys(String k) {
        return (int) client.getKeys().getKeysByPattern(k).spliterator().estimateSize();
    }
}
