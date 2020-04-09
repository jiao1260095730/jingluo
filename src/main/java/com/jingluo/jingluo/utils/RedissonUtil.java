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
        Config config=new Config();
        config.useSingleServer().setAddress(SystemConfig.REDIS_HOST).setPassword(SystemConfig.REDIS_PASS).setConnectionPoolSize(64);
        client= Redisson.create(config);
    }

    //新增 String：key-value
    public static void setStr(String key,String value){
        //1、字符串类型
        client.getBucket(key).set(value);
    }
    //新增 String：key-value   设置时间 秒
    public static void setStr(String key,String value,long seconds){
        client.getBucket(key).set(value,seconds,TimeUnit.SECONDS);
    }

    //新增 Hash：key-fields-values
    public static  void putHash(String key,String field,String val){
        client.getMap(key).put(field,val);
    }
    public static  void putAllHash(String key,long seconds, Map<Object,String> map){
        RMap rm= client.getMap(key);
        if(seconds>0) {
            rm.expire(seconds, TimeUnit.SECONDS);
        }
        rm.putAll(map);
    }
    public static <T> void putAll(String key,long seconds, Map<Object,T> map){
        RMap rm= client.getMap(key);
        if(seconds>0) {
            rm.expire(seconds, TimeUnit.SECONDS);
        }
        rm.putAll(map);
    }
    //删除 String
    public static void delKey(String key){
        client.getKeys().delete(key);
//        client.getList(key).remove();
    }
    //查询
    public static String getStr(String key){
        return (String) client.getBucket(key).get();
    }
    public static Collection<Object> getHash(String key){
        return client.getMap(key).values();
    }
    public static Map<Object,Object> getHashAll(String key){
        return client.getMap(key).readAllMap();
    }
    public static Object getHashVal(String key,String field){
        return client.getMap(key).get(field);
    }
    //系统
    //设置有效期
    public static void setExpire(String key,long seconds){
        client.getKeys().expire(key,seconds, TimeUnit.SECONDS);
    }

    //开启分布式锁  setnx
    public static void lock(String key){
//        Lock lock=new ReentrantLock();
//        lock.lock();
//        lock.unlock();
        client.getLock(key).lock();
    }
    //释放分布式锁
    public static void unlock(String key){
        client.getLock(key).unlock();
    }
    //验证key是否存在
    public static boolean checkKey(String key){
        return client.getKeys().countExists(key)>0;
    }
    public static int getKeys(String k){
        return (int)client.getKeys().getKeysByPattern(k).spliterator().estimateSize();
    }
}
