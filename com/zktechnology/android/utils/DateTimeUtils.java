package com.zktechnology.android.utils;

import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.guide.guidecore.utils.ShutterHandler;
import com.youth.banner.BannerConfig;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    private static long getOldEncodeTime(int i, int i2, int i3, int i4, int i5, int i6) {
        return (long) ((((((((i - 2000) * 12) * 31) + ((i2 - 1) * 31)) + i3) - 1) * 86400) + (((i4 * 60) + i5) * 60) + i6);
    }

    public static Date getOldDecodeTime(long j) {
        Calendar instance = Calendar.getInstance();
        if (j <= 0) {
            instance.set(1, BannerConfig.TIME);
            instance.set(2, 1);
            instance.set(5, 1);
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
        } else {
            instance.set(13, (int) (j % 60));
            long j2 = j / 60;
            instance.set(12, (int) (j2 % 60));
            long j3 = j2 / 60;
            instance.set(11, (int) (j3 % 24));
            long j4 = j3 / 24;
            instance.set(5, (int) ((j4 % 31) + 1));
            long j5 = j4 / 31;
            instance.set(2, (int) (j5 % 12));
            instance.set(1, (int) ((j5 / 12) + ShutterHandler.NUC_TIME_MS));
        }
        return instance.getTime();
    }

    public static long getOldEncodeTime(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return getOldEncodeTime(instance.get(1), instance.get(2) + 1, instance.get(5), instance.get(11), instance.get(12), 0);
    }

    public static boolean isInExpires(String str, String str2, int i) {
        if (ZKLauncher.sUserValidTimeFun.equals("0")) {
            return true;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        if (str.equals("0") || str2.equals("0")) {
            return true;
        }
        if (i == 0) {
            try {
                if (str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                    str = str.replace(ExifInterface.GPS_DIRECTION_TRUE, " ").substring(0, str.length() - 9);
                }
                if (str2.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                    str2 = str2.replace(ExifInterface.GPS_DIRECTION_TRUE, " ").substring(0, str2.length() - 9);
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date parse = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                Date parse2 = simpleDateFormat.parse(str);
                Date parse3 = simpleDateFormat.parse(str2);
                if (parse.getTime() < parse2.getTime() || parse.getTime() > parse3.getTime()) {
                    return false;
                }
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if (i == 1) {
                try {
                    if (str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                        str = str.replace(ExifInterface.GPS_DIRECTION_TRUE, " ").substring(0, str.length() - 3);
                    }
                    if (str2.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                        str2 = str2.replace(ExifInterface.GPS_DIRECTION_TRUE, " ").substring(0, str2.length() - 3);
                    }
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(ZKConstantConfig.DATE_FORMAT_3, Locale.US);
                    Date parse4 = simpleDateFormat2.parse(simpleDateFormat2.format(new Date()));
                    Date parse5 = simpleDateFormat2.parse(str);
                    Date parse6 = simpleDateFormat2.parse(str2);
                    if (parse4.getTime() < parse5.getTime() || parse4.getTime() > parse6.getTime()) {
                        return false;
                    }
                    return true;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return false;
        }
    }
}
