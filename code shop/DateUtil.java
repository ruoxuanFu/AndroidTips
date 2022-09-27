package com.hc.myapplication.utils;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /**
     * dateFormat
     */
    public static String YYYY_MM_DD1 = "yyyy-MM-dd";
    public static String YYYY_MM_DD2 = "yyyy/MM/dd";
    public static String YYYY_MM_DD3 = "yyyy.MM.dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static String YYYY_MM_DD_HH_MM_SS_CHINESE = "yyyy年MM月dd日 HH:mm:ss";
    public static String YYYY_MM_DD_HH_MM_CHINESE = "yyyy年MM月dd日 HH:mm";
    public static String HH_MM = "HH:mm";

    /**
     * 获取当前年份
     *
     * @return int
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return int
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期
     *
     * @return int
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    public static SimpleDateFormat getDefaultSimpleDateFormat(String dateFormat) {
        return new SimpleDateFormat(dateFormat, Locale.getDefault());
    }

    /**
     * 获取当前时间
     *
     * @param dateFormat 时间格式
     * @return String
     */
    public static String getTodayDateTime(String dateFormat) {
        SimpleDateFormat format = getDefaultSimpleDateFormat(dateFormat);
        return format.format(new Date());
    }

    /**
     * 转换时间
     *
     * @param time       要转换的时间
     * @param dateFormat 目标时间的格式
     * @return String
     */
    @Nullable
    public static String transformTimeFormat(long time, String dateFormat) {
        SimpleDateFormat sdr = getDefaultSimpleDateFormat(dateFormat);
        String times = null;
        try {
            times = sdr.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 转换时间
     *
     * @param time       要转换的时间
     * @param dateFormat 目标时间的格式
     * @return String
     */
    @Nullable
    public static String transformTimeFormat(String time, String dateFormat) {
        String times = null;
        try {
            if (TextUtils.isEmpty(time)) {
                return null;
            }

            long longTime;

            if (time.length() == 13) {
                longTime = Long.parseLong(time);
            } else if (time.length() < 13) {
                longTime = Long.parseLong(time) * 1000L;
            } else {
                return null;
            }
            times = transformTimeFormat(longTime, dateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 给定时间格式转换为Date数据
     *
     * @param dateStr    String
     * @param dateFormat String
     * @return Date
     */
    @Nullable
    public static Date parseDate(String dateStr, String dateFormat) {
        SimpleDateFormat simpleDateFormat = getDefaultSimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断是否为今天
     *
     * @param day        传入的时间
     * @param timeFormat 传入时间格式，需要和day匹配
     * @return true今天 false不是
     */
    public static boolean isToday(String day, String timeFormat) {
        //传入时间
        SimpleDateFormat format = getDefaultSimpleDateFormat(timeFormat);
        Calendar cal = Calendar.getInstance();
        Date calDate = null;
        try {
            calDate = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (calDate != null) {
            cal.setTime(calDate);
        } else {
            return false;
        }

        //当前时间
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            return diffDay == 0;
        }
        return false;
    }

    /**
     * 判断传入时间戳是星期几
     *
     * @param time       传入时间
     * @param timeFormat 传入时间格式
     * @return String
     */
    @Nullable
    public static String getWeakStr(String time, String timeFormat) {
        String week = null;
        SimpleDateFormat sdf = getDefaultSimpleDateFormat(timeFormat);
        Calendar c = Calendar.getInstance();
        try {
            Date parse = sdf.parse(time);
            if (parse != null) {
                c.setTime(parse);
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                week = "星期日";
                break;
            case Calendar.MONDAY:
                week = "星期一";
                break;
            case Calendar.TUESDAY:
                week = "星期二";
                break;
            case Calendar.WEDNESDAY:
                week = "星期三";
                break;
            case Calendar.THURSDAY:
                week = "星期四";
                break;
            case Calendar.FRIDAY:
                week = "星期五";
                break;
            case Calendar.SATURDAY:
                week = "星期六";
                break;
            default:
                break;
        }
        return week;
    }

    /**
     * 获取今天属于周几
     *
     * @return int
     */
    public static int getWeakIntNow() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int day = dayOfWeek - 1 == 0 ? 7 : dayOfWeek - 1;
        return day;
    }

    /**
     * 把秒数转换为可读的计时xx:xx:xx
     *
     * @param time int
     * @return String
     */
    public static String secToTime(int time) {
        String timeStr;
        int hour;
        int minute;
        int second;
        if (time <= 0) {
            return "00:00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 一位数前面加上0
     * 0->00,1->01
     *
     * @param num int
     * @return String
     */
    public static String unitFormat(int num) {
        String retStr;
        if (num >= 0 && num < 10) {
            retStr = "0" + num;
        } else {
            retStr = "" + num;
        }
        return retStr;
    }

    /**
     * 计算给定的时间距离现在多久，返回最大的时间单位
     *
     * @param time 给定的时间戳
     * @return 返回记录现在的时间 如：10:10返回10分钟之前，10:10:10返回10小时之前
     */
    public static String calculateTimeFromNow(long time) {
        long disTime = (System.currentTimeMillis() - time) / 1000;
        if (disTime < 60 && disTime >= 0) {
            // 秒
            return disTime + "秒钟前";
        } else if (disTime < 60 * 60 && disTime >= 60) {
            // 秒,分
            return disTime / 60 + "分钟前";
        } else if (disTime >= 60 * 60 && disTime < 60 * 60 * 24) {
            // 秒,分,时
            return disTime / 3600 + "小时前";
        } else if (disTime >= 60 * 60 * 24) {
            // 秒,分,时,天
            return disTime / (60 * 60 * 24) + "天前";
        }
        return "1分钟前";
    }

    /**
     * 计算给定的时间距离现在多久，返回详细信息
     *
     * @param time 给定的时间
     * @return 返回记录现在的时间 如：10:10返回10分钟10秒前，10:10:10返回10小时10分钟10秒前
     */
    public static String calculateTimeFromNowSpecific(long time) {
        long disTime = (System.currentTimeMillis() - time) / 1000;
        StringBuilder sb = new StringBuilder();
        if (disTime < 60 && disTime >= 0) {
            // 秒
            sb.append(disTime);
        } else if (disTime < 60 * 60 && disTime >= 60) {
            // 秒,分
            sb.append(disTime % 60).append(":").append(disTime / 60);
        } else if (disTime >= 60 * 60 && disTime < 60 * 60 * 24) {
            // 秒,分,时
            sb.append(disTime % 3600 % 60).append(":").append(disTime % 3600 / 60).append(":").append(disTime / 3600);
        } else if (disTime >= 60 * 60 * 24) {
            // 秒,分,时,天
            sb.append(disTime % (60 * 60 * 24) % 3600 % 60)
                    .append(":")
                    .append(disTime % (60 * 60 * 24) % 3600 / 60)
                    .append(":")
                    .append(disTime % (60 * 60 * 24) / 3600)
                    .append(":")
                    .append(disTime / (60 * 60 * 24));
        }

        String calculateTime = sb.toString();
        StringBuilder calculateTimeSb = new StringBuilder();

        if (calculateTime.contains(":")) {
            String[] times = calculateTime.split(":");
            int length = times.length;
            for (int i = length - 1; i >= 0; i--) {
                if (3 == i) {
                    calculateTimeSb.append(times[i]).append("天");
                }
                if (2 == i) {
                    calculateTimeSb.append(times[i]).append("时");
                }
                if (1 == i) {
                    calculateTimeSb.append(times[i]).append("分");
                }
                if (0 == i) {
                    calculateTimeSb.append(times[i]).append("秒之前");
                }
            }
        } else {
            calculateTimeSb.append(calculateTime).append("秒钟之前");
        }

        return calculateTimeSb.toString();
    }

    /**
     * 获取 currentDate 的 num 天前的时间
     *
     * @param currentDate 当前时间
     * @param num         天数
     * @return Date
     */
    @Nullable
    public static Date getBeforeSevenDate(Date currentDate, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - num);
        String timeFormat = transformTimeFormat(calendar.getTimeInMillis(), YYYY_MM_DD1);
        if (TextUtils.isEmpty(timeFormat)) {
            return null;
        } else {
            return parseDate(timeFormat, YYYY_MM_DD1);
        }
    }

    /**
     * 获取今天的剩余时间
     * 现在时间到23:59:59还有多久
     *
     * @return Date
     */
    @Nullable
    public static Date getTodayTimeLeft() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        String timeFormat = transformTimeFormat(calendar.getTimeInMillis(), YYYY_MM_DD1);
        if (TextUtils.isEmpty(timeFormat)) {
            return null;
        } else {
            long timestamp = parseDate(timeFormat, YYYY_MM_DD1).getTime() - 1000;
            return new Date(timestamp);
        }
    }
}
