package com.xzq.module_base.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期工具
 *
 * @author Alex
 */
public class DateUtils {

    public static final String PATTERN_YYYY_MM_DD_HHMMSS_0 = "yyyyMMddHHmmss";
    public static final String PATTERN_YYYY_MM_DD_HHMMSS_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YYYY_MM_DD_HHMMSS_2 = "yyyy.MM.dd HH:mm:ss";

    public static final String PATTERN_YYYY_MM_DD_HHMM_0 = "yyyyMMdd HH:mm";
    public static final String PATTERN_YYYY_MM_DD_HHMM_1 = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_YYYY_MM_DD_HHMM_2 = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_MM_DD_HHMM_2 = "MM月dd日 HH:mm";
    public static final String PATTERN_MM_DD_HHMM_3 = "MM月dd日 HH:mm:ss";
    public static final String PATTERN_MM_DD = "MM.dd";
    public static final String PATTERN_MM_DD_1 = "MM月dd日";

    public static final String PATTERN_YYYY_MM_DD_0 = "yyyyMMdd";
    public static final String PATTERN_YYYY_MM_DD_1 = "yyyy-MM-dd";
    public static final String PATTERN_YYYY_MM_DD_2 = "yyyy/MM/dd";
    public static final String PATTERN_YYYY_MM_DD_3 = "yyyy.MM.dd";
    public static final String PATTERN_YYYY_MM_DD_4 = "yyyy年MM月dd日";

    public static final String PATTERN_HH_MM_SS_0 = "HH:mm:ss";
    public static final String PATTERN_HH_MM_SS_1 = "HH-mm-ss";
    public static final String PATTERN_HH_MM_SS_2 = "HH/mm/ss";

    public static final String PATTERN_HH_MM_0 = "HH:mm";

    public static final String PATTERN_YYYY = "yyyy";
    public static final String PATTERN_HH = "HH";
    public static final String PATTERN_MM = "mm";
    public static final String DYNAMIC_TIME = "dd MM月";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(PATTERN_YYYY_MM_DD_HHMMSS_1,
            Locale.getDefault());

    /**
     * 时间戳转日期
     */
    public static String timeStamp2Date(String timeStamp) {
        if (timeStamp == null || timeStamp.isEmpty() || timeStamp.equals("null")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD_1);
        return sdf.format(new Date(Long.valueOf(timeStamp + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @return
     */
    public static String date2TimeStamp(String date_str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD_HHMMSS_1);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 获取日期格式化字符串
     *
     * @param pattern 格式模板
     * @return 输出字符串
     */
    public static String getCalendarStr(String pattern) {
        return getCalendarStr(pattern, new GregorianCalendar().getTime());
    }

    /**
     * 获取日期格式化字符串
     *
     * @param pattern 格式模板
     * @return 输出字符串
     */
    public static String getCalendarStr(String pattern, long date) {
        return getCalendarStr(pattern, new Date(date));
    }

    /**
     * 获取日期格式化字符串
     *
     * @param pattern 格式模板
     * @return 输出字符串
     */
    public static String getCalendarStr(String pattern, Date date) {
        FORMAT.applyLocalizedPattern(pattern);
        return FORMAT.format(date);
    }

    /**
     * 获取指定格式日期的毫秒数
     *
     * @return 毫秒
     */
    public static long getMillis(String TargetTime, String pattern) {
        Calendar calendar = Calendar.getInstance();
        FORMAT.applyLocalizedPattern(pattern);
        try {
            // 特定格式的时间
            calendar.setTime(FORMAT.parse(TargetTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 根据年份和年的第周(从年第一天开始)，获得指定周第最后天的MM.dd格式的日期。
     *
     * @param year       年
     * @param weekOfYear 周数
     * @return 输出字符串
     */
    public static String getLastMonthAndDate(int year, int weekOfYear) {

        long oneDate = 24 * 60 * 60 * 1000;
        long oneWeekMillis = 7 * 24 * 60 * 60 * 1000;
        long sixDate = 6 * 24 * 60 * 60 * 1000;

        StringBuilder monthAndDate = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        Calendar cl = Calendar.getInstance();
        try {
            // 特定格式的时间
            cl.setTime(sdf.parse(year + "-01-01"));
            cl.setTimeInMillis(cl.getTimeInMillis() - oneDate * (cl.get(Calendar.DAY_OF_WEEK) - 1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl2 = Calendar.getInstance();
        long weekLasttDateMillis = cl.getTimeInMillis() + (weekOfYear - 1)
                * oneWeekMillis + sixDate;
        cl2.setTimeInMillis(weekLasttDateMillis);
        int month = cl2.get(Calendar.MONTH) + 1;
        if (month < 10) {
            monthAndDate.append("0");
        }
        monthAndDate.append(month);
        monthAndDate.append(".");

        int day = cl2.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            monthAndDate.append("0");
        }
        monthAndDate.append(day);
        return monthAndDate.toString();
    }

    /**
     * 根据年份和年的第几周(从年第一天开始)，获得指定周最-天的MM.dd格式的日期。
     *
     * @param year       年
     * @param weekOfYear 周数
     * @return 输出字符串
     */
    public static String getFirstMonthAndDate(int year, int weekOfYear) {

        long oneDate = 24 * 60 * 60 * 1000;
        long oneWeekMillis = 7 * 24 * 60 * 60 * 1000;

        StringBuilder monthAndDate = new StringBuilder();
        FORMAT.applyLocalizedPattern(PATTERN_YYYY_MM_DD_1);
        Calendar cl = Calendar.getInstance();
        try {
            // 特定格式的时间
            cl.setTime(FORMAT.parse(year + "-01-01"));
            cl.setTimeInMillis(cl.getTimeInMillis() - oneDate * (cl.get(Calendar.DAY_OF_WEEK) - 1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl2 = Calendar.getInstance();
        long weekFirstDateMillis = cl.getTimeInMillis() + (weekOfYear - 1)
                * oneWeekMillis;
        cl2.setTimeInMillis(weekFirstDateMillis);
        int month = cl2.get(Calendar.MONTH) + 1;
        if (month < 10) {
            monthAndDate.append("0");
        }
        monthAndDate.append(month);
        monthAndDate.append(".");

        int day = cl2.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            monthAndDate.append("0");
        }
        monthAndDate.append(day);
        return monthAndDate.toString();
    }

    /**
     * 根据绝对毫秒数计算当前日期所处周数
     *
     * @param currentTimeMillis 当前的毫秒数
     * @return 周数
     */
    public static int getWeekByMillis(long currentTimeMillis) {
        FORMAT.applyLocalizedPattern(PATTERN_YYYY);
        String yearStr = FORMAT.format(currentTimeMillis) + "-01-01";
        long yearBeginMillis = getMillis(yearStr, PATTERN_YYYY_MM_DD_1);
        long oneWeekMillis = 7 * 24 * 60 * 60 * 1000;
        return (int) ((currentTimeMillis - yearBeginMillis) / oneWeekMillis + 1);
    }

    /**
     * 获取某月中第一天是星期几
     *
     * @return 当为周日时，返回0，当为周一至周六时，则返回对应的1-6。
     */
    public static int getWeekOfFirstDayAtMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getCurrentDayOfWeek(calendar.getTimeInMillis());
    }

    /**
     * 得到当前日期是星期几。
     *
     * @param milliseconds 毫秒数
     * @return 当为周日时，返回0，当为周一至周六时，则返回对应的1-6。
     */
    public static int getCurrentDayOfWeek(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 得到当前日期是星期几。
     *
     * @param date 日期
     * @return 当为周日时，返回0，当为周一至周六时，则返回对应的1-6。
     */
    public static int getCurrentDayOfWeek(String date) {
        Calendar calendar = Calendar.getInstance();
        FORMAT.applyLocalizedPattern(PATTERN_YYYY_MM_DD_0);
        try {
            calendar.setTimeInMillis(FORMAT.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static String getWeekByTime(long milliseconds) {
        int intWeek = getCurrentDayOfWeek(milliseconds);
        switch (intWeek) {
            default:
                return "";
            case 0:
                return "周日";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
        }
    }

    public static String getWeekByTimeSec(long sec) {
        return getWeekByTime(sec * 1000);
    }

    /**
     * 动态时间
     *
     * @param time
     * @return
     */
    public static String getDynamicTime2(long time) {
        String today = getCalendarStr(DYNAMIC_TIME, System.currentTimeMillis());
        String dateStr = getCalendarStr(DYNAMIC_TIME, time * 1000);
        if (TextUtils.equals(today, dateStr)) {
            return "今天";
        }
        return dateStr;
    }

    /**
     * 动态时间
     *
     * @param time
     * @return
     */
    public static String getDynamicTime(long time) {
        long l = System.currentTimeMillis();
        if (l / 1000 == time) {
            return "今天";
        }
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat(DYNAMIC_TIME);
        return format.format(date);
    }

    /**
     * 动态时间
     *
     * @param time
     * @return
     */
    public static String getDynamicYear(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_YYYY);
        return format.format(date);
    }

    public static int getNowYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getNowYearStr() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static int getNowMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getNowDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

}
