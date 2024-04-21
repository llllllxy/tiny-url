package org.tinycloud.tinyurl.common.utils;


import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 时间工具类(普通)
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-26 10:37
 */

public class DateUtils {

    // 用来全局控制 上一周，本周，下一周的周数变化
    private static int weeks = 0;

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期 默认得时间格式 yyyy-MM-dd
     *
     * @return 日期字符串
     */
    public static String today() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(dt);
    }

    /**
     * 获取当前日期 比如 DateTool.getCurrentDate("yyyy-MM-dd"); 返回值为 2012-05-15
     *
     * @return yyyy-MM-dd
     */
    public static String today(String format) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dt);
    }

    /**
     * 获取当前时间 默认得时间格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 时间字符串
     */
    public static String now() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        return sdf.format(dt);
    }

    /**
     * 获取当前时间 比如 DateTool.getCurrentTime("yyyy-MM-dd HH:mm:ss"); 返回值为 2012-05-15 23:44:20
     *
     * @param format 格式
     * @return String 时间字符串
     */
    public static String now(String format) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(dt);
        return date;
    }

    /**
     * 获取当前时间 默认得时间格式 HH:mm:ss
     *
     * @return 时间字符串
     */
    public static String time() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
        return sdf.format(dt);
    }

    /**
     * 获取当前时间 比如 DateTool.getCurrentTime("HH:mm:ss"); 返回值为 23:44:20
     *
     * @param format 格式
     * @return String 时间字符串
     */
    public static String time(String format) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(dt);
        return date;
    }

    /**
     * 字符串转日期
     *
     * @param dateString 日期字符串
     * @param pattern    格式
     * @return Date
     */
    public static Date parse(String dateString, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException ignored) {
        }
        return date;
    }

    /**
     * 日期转字符串
     *
     * @param date    日期
     * @param pattern 格式
     * @return String
     */
    public static String format(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 将一个日期时间字符串，由一个格式转换成另一个格式 比如:DateTool.timeFormat("20120506123344",
     * "yyyyMMddHHmmss", "yyyy年M月d日 HH:mm") 返回值为2012年5月6日 12:33
     *
     * @param time      旧格式时间字符串
     * @param oldFormat 旧格式
     * @param newFormat 新格式
     * @return 新格式时间字符串
     */
    public static String timeFormat(String time, String oldFormat, String newFormat) {
        try {
            SimpleDateFormat oldSdf = new SimpleDateFormat(oldFormat);
            Date date = oldSdf.parse(time);
            SimpleDateFormat newSdf = new SimpleDateFormat(newFormat);
            return newSdf.format(date);
        } catch (Exception e) {
            return time;
        }
    }

    /**
     * 计量时间差 (time2 - time1)，返回秒数 比如 DateTool.timeDiff("2012-10-25 02:49:15",
     * "yyyyMMdd HH:mm:ss", "20121025025030", "yyyyMMddHHmmss") 返回值为 75
     *
     * @param time1   时间1
     * @param format1 格式1
     * @param time2   时间2
     * @param format2 格式2
     * @return 时间间隔-秒数
     */
    public static int timeDiff(String time1, String format1, String time2, String format2) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
            Date date1 = sdf1.parse(time1);
            SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
            Date date2 = sdf2.parse(time2);
            return Long.valueOf(date2.getTime() - date1.getTime()).intValue() / 1000;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 计量时间差 (time2 - time1)，返回秒数 比如 DateTool.timeDiff("2011-10-25 02:50:30",
     * "2012-10-25 02:50:30",) 返回值为 75
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 时间间隔-秒数
     */
    public static int timeDiff(String time1, String time2) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_TIME_PATTERN);
            Date date1 = sdf1.parse(time1);
            SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_TIME_PATTERN);
            Date date2 = sdf2.parse(time2);
            return Long.valueOf(date2.getTime() - date1.getTime()).intValue() / 1000;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1 yyyy-MM-dd
     * @param date2 yyyy-MM-dd
     * @return 时间间隔-天数
     */
    public static int dayDiff(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(DATE_PATTERN);
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
            return Long.valueOf(date.getTime() - mydate.getTime()).intValue() / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return true or false
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 获取昨天日期
     *
     * @return 字符串
     */
    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(DATE_PATTERN).format(cal.getTime());
    }

    /**
     * 获取明天日期
     *
     * @return 字符串
     */
    public static String getTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return new SimpleDateFormat(DATE_PATTERN).format(cal.getTime());
    }

    /**
     * 获取上月
     *
     * @return 字符串
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return new SimpleDateFormat(DATE_PATTERN).format(cal.getTime());
    }

    /**
     * 获取去年同月
     *
     * @return
     */
    public static String getYearSameMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        return new SimpleDateFormat(DATE_PATTERN).format(cal.getTime());
    }


    /**
     * 字符串时间转换为Unix时间戳
     *
     * @param dateTime1 yyyy-MM-dd HH:mm:ss
     * @return long
     */
    public static long dateToStamp(String dateTime1) {
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            Date dateTime11 = dateTimeFormatter.parse(dateTime1);
            long mil = dateTime11.getTime() / 1000;
            return mil;
        } catch (Exception e) {
            throw new RuntimeException("日期格式错误！");
        }
    }

    /**
     * Unix时间戳转换成字符串时间
     *
     * @param sed long
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String stampToDate(long sed) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
        long lt = sed * 1000;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取若干秒后的时间 time格式 yyyy-MM-dd HH:mm:ss
     *
     * @param later 数字
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getLaterTime(String time, int later) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            Date date = sdf.parse(time);
            Calendar Cal = Calendar.getInstance();
            Cal.setTime(date);
            Cal.add(Calendar.SECOND, later);
            return sdf.format(Cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间-若干秒后的时间
     *
     * @param later
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getLaterTime(int later) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.SECOND, later);
            return sdf.format(nowTime.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 校验时间字符串是否为正确的日期格式yyyy-MM-dd
     *
     * @param str       日期字符串
     * @param formatStr 日期格式
     * @param canBeNull 是否允许为null，true允许，false 不允许
     * @return 正确/不正确
     */
    public static boolean isValidDate(String str, String formatStr, boolean canBeNull) {
        // 如果是空字符串的话，也返回true
        if (StringUtils.isEmpty(str)) {
            if (canBeNull) {
                return true;
            } else {
                return false;
            }
        } else {
            if (!(str.length() == formatStr.length())) {
                return false;
            }
        }
        boolean result = true;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            result = false;
        }
        return result;
    }

    /**
     * 校验时间字符串是否为正确的日期格式，不允许为空
     *
     * @param str       日期字符串
     * @param formatStr 日期格式
     * @return 正确/不正确
     */
    public static boolean isValidDate(String str, String formatStr) {
        return isValidDate(str, formatStr, false);
    }
}
