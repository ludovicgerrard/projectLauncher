package com.zktechnology.android.push.util;

import android.util.Log;
import java.util.Calendar;

public class Utils {
    private static final String TAG = "Utils";

    public static int formatAccPushTime(String str) {
        String str2;
        int i;
        int i2;
        int intValue = Integer.valueOf(str.substring(0, 4)).intValue();
        if (str.contains("-")) {
            String substring = str.substring(5);
            i2 = Integer.valueOf(substring.substring(0, 2)).intValue();
            str2 = substring.substring(3);
            i = Integer.valueOf(str2.substring(0, 2)).intValue();
        } else {
            String substring2 = str.substring(4);
            i2 = Integer.valueOf(substring2.substring(0, 2)).intValue();
            str2 = substring2.substring(2);
            i = Integer.valueOf(str2.substring(0, 2)).intValue();
        }
        String substring3 = str2.substring(3);
        int intValue2 = Integer.valueOf(substring3.substring(0, 2)).intValue();
        String substring4 = substring3.substring(3);
        return computeTimeRTEvent(intValue, i2, i, intValue2, Integer.valueOf(substring4.substring(0, 2)).intValue(), Integer.valueOf(substring4.substring(3)).intValue());
    }

    public static int computeTimeRTEvent(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = (((((((i - 2000) * 12) * 31) + ((i2 - 1) * 31)) + i3) - 1) * 86400) + (((i4 * 60) + i5) * 60) + i6;
        Log.d("BBBBB", "computeTimeRTEvent: " + i7);
        return i7;
    }

    public static String getCurrentDate() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(1);
        int i2 = instance.get(2) + 1;
        int i3 = instance.get(5);
        String valueOf = String.valueOf(i);
        String valueOf2 = String.valueOf(i2);
        if (i2 < 10) {
            valueOf2 = "0" + valueOf2;
        }
        String valueOf3 = String.valueOf(i3);
        if (i3 < 10) {
            valueOf3 = "0" + valueOf3;
        }
        return String.format("%s%s%s", new Object[]{valueOf, valueOf2, valueOf3});
    }

    public static String getCurrentMonthAndDate() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(2) + 1;
        int i2 = instance.get(5);
        String valueOf = String.valueOf(i);
        if (i < 10) {
            valueOf = "0" + valueOf;
        }
        String valueOf2 = String.valueOf(i2);
        if (i2 < 10) {
            valueOf2 = "0" + valueOf2;
        }
        return String.format("%s%s", new Object[]{valueOf, valueOf2});
    }

    public static int getVerifyDate() {
        Calendar instance = Calendar.getInstance();
        return (instance.get(1) * 10000) + ((instance.get(2) + 1) * 100) + instance.get(5);
    }

    public static int getVerifyTime() {
        Calendar instance = Calendar.getInstance();
        return (instance.get(11) * 100) + instance.get(12);
    }
}
