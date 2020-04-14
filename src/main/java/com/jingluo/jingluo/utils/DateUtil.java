package com.jingluo.jingluo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: WashCar
 * @description:
 * @author: Feri
 * @create: 2019-11-07 17:05
 */
public class DateUtil {
    //获取指定天数
    public static String getTime(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    //获取今天剩余的秒数
    public static long getDaySeconds() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()) + " 23:59:59");
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            return (calendar1.getTimeInMillis() - calendar.getTimeInMillis()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //获取指定月份之后的日期
    public static Date getDateByMonths(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static Date getDate() throws ParseException {

        return new SimpleDateFormat().parse(getTime());

    }

    public static String getTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
