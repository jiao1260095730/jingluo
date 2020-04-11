package com.jingluo.jingluo.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import org.apache.log4j.Logger;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/10 23:20
 */
public class LoggerCommon {
    public final static int MAX_WORD=5000;
    public static boolean DEBUG = false;

    /**
     * common Info 记录日志
     * @param infoMsg
     */
    public static void commoninfo(String infoMsg){
        commonLogger().info(infoMsg);
    }

    /**
     * common Debug 记录日志
     * @param debugMsg
     */
    public static void commondebug(String debugMsg){
        commonLogger().debug(debugMsg);
    }

    /**
     * common Warn记录日志
     * @param warnMsg
     */
    public static void commonwarn(String warnMsg){
        commonLogger().warn(warnMsg);
    }

    /**
     * common Error 记录日志
     * @param errorMsg
     */
    public static void commonerror(String errorMsg, Throwable e){
        commonErrorLogger().error(errorMsg + ": " + getExceptionInfo(e));
    }

    /**
     * common Error 记录日志
     * @param errorMsg
     */
    public static void commonerror(String errorMsg, String error){
        commonErrorLogger().error(errorMsg + ": " + error);
    }

    /**
     * packet Info 记录日志
     * @param msg
     */
    public static void packetinfo(String msg){
        packetLogger().info(msg);
    }
    /**
     * common Error 记录单个日志
     * @param errorMsg
     */
    public static void commonerror(String errorMsg){
        commonErrorLogger().error(errorMsg);
    }

    /***********************************************************************
     * 取得log4j日志记录对象common，此对象将log运行日志保存到common.log
     * @return
     * 2018年5月9日
     ***********************************************************************/
    private static Logger commonLogger() {
        return Logger.getLogger("common");
    }


    /***********************************************************************
     * 取得log4j日志记录对象commonErrorLogger，此对象将log错误日志保存到common-error.log
     * @return
     * 2018年5月9日
     ***********************************************************************/
    private static Logger commonErrorLogger() {
        return Logger.getLogger("common-error");
    }


    /**********************************************************************
     *  取得log4j日志记录对象packetLogger，此对象将log错误日志保存到packet-info.log
     * @return
     * 2018年5月9日
     **********************************************************************/
    private static Logger packetLogger() {
        return Logger.getLogger("packet");
    }


    /**
     * 将异常信息中内容转换为字符串形式返回
     * @param e 异常对象
     * @return String    返回类型
     * @throws
     */
    public static String getExceptionInfo(Throwable e) {
        String s = null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        s = sw.toString();
        // 控制最大字符
        if (s.length()>MAX_WORD) {
            s = s.substring(0, MAX_WORD);
        }
        if (null != sw) {
            try {
                sw.close();
                sw = null;
            } catch (IOException e2) {}
            finally {
                sw = null;
            }
        }
        if (null != pw) {
            pw.close();
            pw = null;
        }
        return s;
    }
}
