package com.banyou.app.util;

import com.blankj.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class TimeUtil {
    public static final int DEFAULT_INTERVAL = 1000;//默认时间间隔
    public static final int DEFAULT_DURATION = 100;//吐司默认时间
    public static final int DEFAULT_BUTTON = 2;//button可以点击间隔时间，有效防止多按导致重复打开界面
    public static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat DEFAULT_FORMAT_NO_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat DEFAULT_FORMAT_CHINA = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
    public static final DateFormat YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat YEAR_MONTH = new SimpleDateFormat("yyyy-MM");
    public static final long DEFAULT_NET_CONNECTION_TIME = 15000;//服务端与客户端连接时间
    public static final long DEFAULT_NET_READ_TIME = 30000;//客户端读取服务器发来的数据时间
    public static final long DEFAULT_NET_WRITE_TIME = 30000;//客户端向服务器传送数据时间
    public static final Calendar calendar = Calendar.getInstance(Locale.CHINA);

    /**
     * 得到当前时间
     *
     * @return
     */
    public static String getNowTime(DateFormat format) {
        return date2String(new Date(), format);
    }

    public static String date2String(Date date) {

        return TimeUtils.date2String(date, YEAR_MONTH);
    }

    public static String date2String(Date date, DateFormat format) {
        if (format == null)
            return date2String(date);
        return TimeUtils.date2String(date, format);
    }

    /**
     * String 转为Date
     */
    public static Date String2Date(String time, DateFormat format) {
        if (format == null)
            return String2Date(time);
        return TimeUtils.string2Date(time, format);
    }

    /**
     * date转时间戳
     *
     * @param time
     * @return
     */
    public static long date2Millis(Date time) {
        return TimeUtils.date2Millis(time);
    }

    /**
     * string转时间戳
     *
     * @param time
     * @return
     */
    public static long strineg2Millis(String time, DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private static Date String2Date(String time) {
        return TimeUtils.string2Date(time, DEFAULT_FORMAT);
    }


    /**
     * 获取任意时间的下一个月
     * 描述:<描述函数实现的功能>.
     *
     * @param repeatDate
     * @return
     */
    public static String getPreMonth(String repeatDate, DateFormat dateFormat) {

        Date date = null;
        try {
            date = dateFormat.parse(repeatDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + 1);

        String dayNext = dateFormat.format(calendar.getTime());
        return dayNext;
    }

    /**
     * 获取任意时间的上一个月
     * 描述:<描述函数实现的功能>.
     *
     * @param repeatDate
     * @return
     */
    public static String getLastMonth(String repeatDate, DateFormat dateFormat) {

        Date date = null;
        try {
            date = dateFormat.parse(repeatDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - 1);

        String dayBefore = dateFormat.format(calendar.getTime());
        return dayBefore;
    }


    /**
     * 获取当前系统前一天日期
     *
     * @param date
     * @return
     */
    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getLastDay(String specifiedDay) {//可以用new Date().toLocalString()传递参数

        Date date = null;
        try {
            date = YEAR_MONTH_DAY.parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - 1);

        String dayBefore = YEAR_MONTH_DAY.format(calendar.getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getNextDay(String specifiedDay) {

        Date date = null;
        try {
            date = YEAR_MONTH_DAY.parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + 1);

        String dayAfter = YEAR_MONTH_DAY.format(calendar.getTime());
        return dayAfter;
    }

    /**
     * 获得某个日期上一年的时间
     *
     * @return
     */
    public static String getLastYearTime() {
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        return date2String(calendar.getTime());
    }

    public static String getLastYearTime(int number) {
        calendar.clear();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, number);
        return date2String(calendar.getTime());
    }

    /**
     * 传入的天数间隔获得指定天数
     *
     * @param number 天数间隔
     * @return
     */
    public static String getAppointDay(int number, DateFormat format) {

        return TimeUtils.millis2String(date2Millis(new Date()) - number * 24 * 60 * 60 * 1000, format);
    }

    public static String getTime(int number) {
        calendar.clear();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, number);
        return date2String(calendar.getTime());
    }

    public static String getTime(int number, DateFormat format) {
        calendar.clear();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, number);
        return date2String(calendar.getTime(), format);
    }

    public static boolean compareDate(String nowDate) {
        Date date = null;
        try {
            date = YEAR_MONTH.parse(nowDate);
            return date == null ? false :
                    (YEAR_MONTH.parse(YEAR_MONTH.format(TimeUtils.getNowDate())).compareTo(date) < 0 ||
                            YEAR_MONTH.parse(YEAR_MONTH.format(TimeUtils.getNowDate())).compareTo(date) == 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean compareDate(String date1, String date2) {
        try {
            Date d1 = YEAR_MONTH.parse(date1);
            Date d2 = YEAR_MONTH.parse(date2);
            if (d1.equals(d2)) {
                System.out.println(date1 + "=" + date2);
                return false;
            } else if (d1.before(d2)) {
                System.out.println(date1 + "在" + date2 + "之前");
                return false;
            } else if (d1.after(d2)) {
                System.out.println(date1 + "在" + date2 + "之后");
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 毫秒转换为具体日期
     */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟


        return strDay + "天" + strHour + ":" + strMinute;
    }

    /**
     * 对比年份之间
     *
     * @param d1
     * @param d2
     */
    public static int compareDateYear(Date d1, Date d2) {
        calendar.clear();
        calendar.setTime(d1);
        int y1 = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.setTime(d2);
        int y2 = calendar.get(Calendar.YEAR);

        return y1 - y2;
    }
}
