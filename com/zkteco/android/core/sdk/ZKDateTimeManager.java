package com.zkteco.android.core.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ZKDateTimeManager {
    private Context context;
    private boolean isInit;
    private int time_dtFmt;

    public void setTime_dtFmt(int i) {
        this.time_dtFmt = i;
    }

    public void init(Context context2) {
        if (!this.isInit) {
            this.context = context2;
            this.isInit = true;
        }
    }

    public ZKDateTime getZKDateTime() {
        if (!this.isInit) {
            return null;
        }
        ZKDateTime zKDateTime = new ZKDateTime(this.context);
        zKDateTime.setTime_dtFmt(this.time_dtFmt);
        return zKDateTime;
    }

    public ZKDateTime getZKDateTime(long j) {
        if (!this.isInit) {
            return null;
        }
        ZKDateTime zKDateTime = new ZKDateTime(j, this.context);
        zKDateTime.setTime_dtFmt(this.time_dtFmt);
        return zKDateTime;
    }

    public ZKDateTime getZKDateTime(String str, String str2) {
        if (!this.isInit) {
            return null;
        }
        try {
            ZKDateTime zKDateTime = new ZKDateTime(new SimpleDateFormat(str, Locale.US).parse(str2).getTime(), this.context);
            zKDateTime.setTime_dtFmt(this.time_dtFmt);
            return zKDateTime;
        } catch (ParseException unused) {
            return null;
        }
    }

    private ZKDateTimeManager() {
        this.isInit = false;
        this.time_dtFmt = 9;
    }

    public static ZKDateTimeManager getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        /* access modifiers changed from: private */
        public static final ZKDateTimeManager instance = new ZKDateTimeManager();

        private Holder() {
        }
    }

    public static final class ZKDateTime {
        private Calendar calendar;
        private Context context;
        private String[] dataFormats = {ZKConstantConfig.DATE_FM_0, ZKConstantConfig.DATE_FM_1, ZKConstantConfig.DATE_FM_2, ZKConstantConfig.DATE_FM_3, ZKConstantConfig.DATE_FM_4, ZKConstantConfig.DATE_FM_5, ZKConstantConfig.DATE_FM_6, ZKConstantConfig.DATE_FM_7, ZKConstantConfig.DATE_FM_8, "yyyy-MM-dd"};
        private long time = -1;
        private int time_dtFmt = 9;

        public ZKDateTime(Context context2) {
            this.context = context2;
        }

        public ZKDateTime(long j, Context context2) {
            this.time = j;
            this.context = context2;
        }

        public void setTime_dtFmt(int i) {
            this.time_dtFmt = i;
        }

        private void resetCal() {
            Calendar instance = Calendar.getInstance();
            this.calendar = instance;
            long j = this.time;
            if (j <= 0) {
                j = System.currentTimeMillis();
            }
            instance.setTime(new Date(j));
        }

        public int getYear() {
            resetCal();
            return this.calendar.get(1);
        }

        public int getMonth() {
            resetCal();
            return this.calendar.get(2);
        }

        public boolean is24Hour() {
            return DateFormat.is24HourFormat(this.context);
        }

        public String getDateFormat() {
            try {
                String str = this.dataFormats[this.time_dtFmt];
                if (TextUtils.isEmpty(str)) {
                    return "yyyy-MM-dd";
                }
                return str;
            } catch (Exception unused) {
                return "yyyy-MM-dd";
            }
        }

        public int getDay() {
            resetCal();
            return this.calendar.get(5) + 1;
        }

        public int getHour() {
            int i;
            Calendar calendar2;
            resetCal();
            if (is24Hour()) {
                calendar2 = this.calendar;
                i = 11;
            } else {
                calendar2 = this.calendar;
                i = 10;
            }
            return calendar2.get(i);
        }

        public int getMinute() {
            resetCal();
            return this.calendar.get(12);
        }

        public int getSecond() {
            resetCal();
            return this.calendar.get(13);
        }

        public String getDate() {
            resetCal();
            return new SimpleDateFormat(getDateFormat(), Locale.US).format(this.calendar.getTime());
        }

        public String getTime() {
            resetCal();
            return DateFormat.getTimeFormat(this.context).format(this.calendar.getTime());
        }
    }
}
