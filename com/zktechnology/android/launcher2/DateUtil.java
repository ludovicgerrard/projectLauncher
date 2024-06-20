package com.zktechnology.android.launcher2;

import com.tencent.map.geolocation.util.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";
    public static final ThreadLocal<SimpleDateFormat> defaultDateFormat = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        }
    };
    public static final ThreadLocal<SimpleDateFormat> defaultDateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        }
    };
    public static final ThreadLocal<SimpleDateFormat> defaultTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_TIME, Locale.US);
        }
    };

    private DateUtil() {
        throw new RuntimeException("￣ 3￣");
    }

    public static String getDateTimeFromMillis(long j) {
        return getDateTimeFormat(new Date(j));
    }

    public static String getDateFromMillis(long j) {
        return getDateFormat(new Date(j));
    }

    public static String getDateTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultDateTimeFormat.get());
    }

    public static String getDateFormat(int i, int i2, int i3) {
        return getDateFormat(getDate(i, i2, i3));
    }

    public static String getDateFormat(Date date) {
        return dateSimpleFormat(date, defaultDateFormat.get());
    }

    public static String getTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultTimeFormat.get());
    }

    public static String dateFormat(String str, String str2) {
        return dateSimpleFormat(java.sql.Date.valueOf(str), new SimpleDateFormat(str2, Locale.US));
    }

    public static String dateFormat(Date date, String str) {
        return dateSimpleFormat(date, new SimpleDateFormat(str, Locale.US));
    }

    public static String dateSimpleFormat(Date date, SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat == null) {
            simpleDateFormat = defaultDateTimeFormat.get();
        }
        if (date == null) {
            return "";
        }
        return simpleDateFormat.format(date);
    }

    public static Date getDateByDateTimeFormat(String str) {
        return getDateByFormat(str, defaultDateTimeFormat.get());
    }

    public static Date getDateByDateFormat(String str) {
        return getDateByFormat(str, defaultDateFormat.get());
    }

    public static Date getDateByFormat(String str, String str2) {
        return getDateByFormat(str, new SimpleDateFormat(str2, Locale.US));
    }

    private static Date getDateByFormat(String str, SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat == null) {
            simpleDateFormat = defaultDateTimeFormat.get();
        }
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDate(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3);
        return instance.getTime();
    }

    public static long getIntervalDays(String str, String str2) {
        return (java.sql.Date.valueOf(str2).getTime() - java.sql.Date.valueOf(str).getTime()) / DateUtils.ONE_DAY;
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(1);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(2) + 1;
    }

    public static int getDayOfMonth() {
        return Calendar.getInstance().get(5);
    }

    public static String getToday() {
        return getDateFormat(Calendar.getInstance().getTime());
    }

    public static String getYesterday() {
        Calendar instance = Calendar.getInstance();
        instance.add(5, -1);
        return getDateFormat(instance.getTime());
    }

    public static String getBeforeYesterday() {
        Calendar instance = Calendar.getInstance();
        instance.add(5, -2);
        return getDateFormat(instance.getTime());
    }

    public static String getOtherDay(int i) {
        Calendar instance = Calendar.getInstance();
        instance.add(5, i);
        return getDateFormat(instance.getTime());
    }

    public static String getCalcDateFormat(String str, int i) {
        return getDateFormat(getCalcDate(getDateByDateFormat(str), i));
    }

    public static Date getCalcDate(Date date, int i) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(5, i);
        return instance.getTime();
    }

    public static Date getCalcTime(Date date, int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        if (date != null) {
            instance.setTime(date);
        }
        instance.add(11, i);
        instance.add(12, i2);
        instance.add(13, i3);
        return instance.getTime();
    }

    public static Date getDate(int i, int i2, int i3, int i4, int i5, int i6) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2, i3, i4, i5, i6);
        return instance.getTime();
    }

    public static int[] getYearMonthAndDayFrom(String str) {
        return getYearMonthAndDayFromDate(getDateByDateFormat(str));
    }

    public static int[] getYearMonthAndDayFromDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return new int[]{instance.get(1), instance.get(2), instance.get(5)};
    }
}
