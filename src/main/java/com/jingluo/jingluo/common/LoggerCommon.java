package com.jingluo.jingluo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 日志工具类
 * @Author 焦斌
 * @Date 2020/4/28 23:23
 */
public class LoggerCommon {

    private final static Logger logger = LoggerFactory.getLogger(LoggerCommon.class);


    public static void info(String msg) {
        logger.info(msg);
    }

    public static void info(String msg, Object obj) {
        logger.info(msg, obj);
    }

    public static void info(String msg, Object... obj) {
        logger.info(msg, obj);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void error(String msg, Object obj) {
        logger.error(msg, obj);
    }
    public static void error(String msg, Object... obj) {
        logger.error(msg, obj);
    }

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public static void debug(String msg, Object obj) {
        logger.debug(msg, obj);
    }

    public static void debug(String msg, Object... obj) {
        logger.debug(msg, obj);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void warn(String msg, Object obj) {
        logger.warn(msg, obj);
    }

    public static void warn(String msg, Object... obj) {
        logger.warn(msg, obj);
    }
}
