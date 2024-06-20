package com.zkteco.android.employeemgmt.util;

import com.guide.guidecore.utils.ShutterHandler;
import com.youth.banner.BannerConfig;
import java.util.Calendar;
import java.util.Date;

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
}
