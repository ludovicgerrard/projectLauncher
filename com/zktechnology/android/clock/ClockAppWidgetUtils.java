package com.zktechnology.android.clock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.ZkDigitConvertUtils;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClockAppWidgetUtils {
    private static final int FIELD_CURRENT_HOUR_INDEX = 0;
    private static final int FIELD_CURRENT_MINUTE_INDEX = 2;
    private static final int FIELD_PREV_HOUR_INDEX = 1;
    private static final int FIELD_PREV_MINUTE_INDEX = 3;
    private static final int TIME_FIELD_COUNT = 4;
    private static Context cawContext;
    private static ClockAppWidgetUtils sInstance;
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 0) {
                TimeBean timeBean = (TimeBean) message.getData().getSerializable("timebean");
                ClockAppWidgetUtils.this.updateTime(timeBean.getView(), timeBean.getTimeTxt(), timeBean.getTimeFormat());
            }
        }
    };
    /* access modifiers changed from: private */
    public Calendar mCalendar = Calendar.getInstance(TimeZone.getDefault());
    private Context mContext;
    private ExecutorService mSingleService = Executors.newSingleThreadExecutor();

    public static ClockAppWidgetUtils getInstance(Context context) {
        ClockAppWidgetUtils clockAppWidgetUtils = sInstance;
        if (clockAppWidgetUtils == null || !context.equals(clockAppWidgetUtils.mContext)) {
            sInstance = new ClockAppWidgetUtils(context);
            cawContext = context;
        }
        return sInstance;
    }

    private ClockAppWidgetUtils(Context context) {
        this.mContext = context;
    }

    private Long getStartTime() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 1);
        return Long.valueOf(instance.getTime().getTime());
    }

    private Long getEndTime() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 23);
        instance.set(12, 59);
        instance.set(13, 59);
        instance.set(14, 999);
        return Long.valueOf(instance.getTime().getTime());
    }

    public String getDate() {
        String strOption = DBManager.getInstance().getStrOption("DtFmt", "9");
        if (strOption.equals("0")) {
            strOption = ZKConstantConfig.DATE_FM_0;
        }
        if (strOption.equals("1")) {
            strOption = ZKConstantConfig.DATE_FM_1;
        }
        if (strOption.equals("2")) {
            strOption = ZKConstantConfig.DATE_FM_2;
        }
        if (strOption.equals(ExifInterface.GPS_MEASUREMENT_3D)) {
            strOption = ZKConstantConfig.DATE_FM_3;
        }
        if (strOption.equals("4")) {
            strOption = ZKConstantConfig.DATE_FM_4;
        }
        if (strOption.equals("5")) {
            strOption = ZKConstantConfig.DATE_FM_5;
        }
        if (strOption.equals("6")) {
            strOption = ZKConstantConfig.DATE_FM_6;
        }
        if (strOption.equals("7")) {
            strOption = ZKConstantConfig.DATE_FM_7;
        }
        if (strOption.equals("8")) {
            strOption = ZKConstantConfig.DATE_FM_8;
        }
        if (strOption.equals("9")) {
            strOption = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(strOption, Locale.getDefault()).format(this.mCalendar.getTime());
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001d A[Catch:{ Exception -> 0x0031 }] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0024 A[Catch:{ Exception -> 0x0031 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getHour() {
        /*
            r6 = this;
            r0 = 11
            boolean r1 = com.zktechnology.android.launcher2.ZKEventLauncher.isDBOk     // Catch:{ Exception -> 0x0031 }
            r2 = 12
            r3 = 24
            if (r1 == 0) goto L_0x001a
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0031 }
            java.lang.String r4 = "timeFormat"
            r5 = 1
            int r1 = r1.getIntOption(r4, r5)     // Catch:{ Exception -> 0x0031 }
            if (r1 != r5) goto L_0x0018
            goto L_0x001a
        L_0x0018:
            r1 = r2
            goto L_0x001b
        L_0x001a:
            r1 = r3
        L_0x001b:
            if (r1 != r3) goto L_0x0024
            java.util.Calendar r1 = r6.mCalendar     // Catch:{ Exception -> 0x0031 }
            int r2 = r1.get(r0)     // Catch:{ Exception -> 0x0031 }
            goto L_0x003b
        L_0x0024:
            java.util.Calendar r1 = r6.mCalendar     // Catch:{ Exception -> 0x0031 }
            r3 = 10
            int r0 = r1.get(r3)     // Catch:{ Exception -> 0x0031 }
            if (r0 != 0) goto L_0x002f
            goto L_0x003b
        L_0x002f:
            r2 = r0
            goto L_0x003b
        L_0x0031:
            r1 = move-exception
            r1.printStackTrace()
            java.util.Calendar r1 = r6.mCalendar
            int r2 = r1.get(r0)
        L_0x003b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.clock.ClockAppWidgetUtils.getHour():int");
    }

    public int getMinute() {
        return this.mCalendar.get(12);
    }

    public String getWeek() {
        return this.mCalendar.getDisplayName(7, 2, Locale.getDefault());
    }

    public void updateCalendar(String str) {
        this.mCalendar = Calendar.getInstance(TimeZone.getTimeZone(str));
    }

    /* access modifiers changed from: private */
    public void updateTime(View view, String str, int i) {
        if (i == 12) {
            view.findViewById(R.id.am_pm_txt).setVisibility(0);
            if (Calendar.getInstance(TimeZone.getDefault()).get(9) == 0) {
                ((TextView) view.findViewById(R.id.am_pm_txt)).setText(new SimpleDateFormat("aa", Locale.getDefault()).format(new Date(getStartTime().longValue())));
            } else {
                ((TextView) view.findViewById(R.id.am_pm_txt)).setText(new SimpleDateFormat("aa", Locale.getDefault()).format(new Date(getEndTime().longValue())));
            }
        } else {
            view.findViewById(R.id.am_pm_txt).setVisibility(8);
            ((TextView) view.findViewById(R.id.am_pm_txt)).setText("");
        }
        ((TextView) view.findViewById(R.id.hour_minute_txt)).setText(str);
        ((TextView) view.findViewById(R.id.week)).setText(getWeek());
        ((TextView) view.findViewById(R.id.date)).setText(getDate());
        view.findViewById(R.id.date).setVisibility(0);
        SharedPreferences sharedPreferences = cawContext.getSharedPreferences("clock_data", 0);
        sharedPreferences.edit();
        boolean z = sharedPreferences.getBoolean("timeState", true);
        boolean z2 = sharedPreferences.getBoolean("dateState", true);
        boolean z3 = sharedPreferences.getBoolean("weekState", true);
        if (z) {
            view.findViewById(R.id.time_ll).setVisibility(0);
            view.findViewById(R.id.am_pm_txt).setVisibility(0);
        } else {
            view.findViewById(R.id.time_ll).setVisibility(8);
            view.findViewById(R.id.am_pm_txt).setVisibility(8);
        }
        if (z2) {
            view.findViewById(R.id.date).setVisibility(0);
        } else {
            view.findViewById(R.id.date).setVisibility(8);
        }
        if (z3) {
            view.findViewById(R.id.week).setVisibility(0);
        } else {
            view.findViewById(R.id.week).setVisibility(8);
        }
    }

    public void updateWidget(boolean z, View view) {
        this.mSingleService.submit(new UpdateWidgetTask(z, view));
    }

    public int get24Or12() {
        return DateFormat.is24HourFormat(this.mContext) ? 24 : 12;
    }

    public class UpdateWidgetTask implements Runnable {
        private boolean tick;
        private View view;

        public UpdateWidgetTask(boolean z, View view2) {
            this.tick = z;
            this.view = view2;
        }

        public void run() {
            String str;
            String str2;
            ClockAppWidgetUtils.this.mCalendar.setTimeInMillis(System.currentTimeMillis());
            int[] iArr = new int[4];
            int i = 12;
            if (this.tick) {
                Calendar instance = Calendar.getInstance(TimeZone.getDefault());
                instance.add(12, -1);
                int i2 = instance.get(10);
                if (i2 == 0) {
                    i2 = 12;
                }
                iArr[1] = i2;
                iArr[0] = ClockAppWidgetUtils.this.getHour();
                iArr[3] = instance.get(12);
                iArr[2] = ClockAppWidgetUtils.this.getMinute();
            } else {
                int hour = ClockAppWidgetUtils.this.getHour();
                iArr[1] = hour;
                iArr[0] = hour;
                int minute = ClockAppWidgetUtils.this.getMinute();
                iArr[3] = minute;
                iArr[2] = minute;
            }
            if (iArr[0] < 10) {
                str = "0" + iArr[0];
            } else {
                str = String.valueOf(iArr[0]);
            }
            if (iArr[2] < 10) {
                str2 = "0" + iArr[2];
            } else {
                str2 = String.valueOf(iArr[2]);
            }
            String str3 = ZkDigitConvertUtils.getFaDigit(str) + ":" + ZkDigitConvertUtils.getFaDigit(str2);
            if (DBManager.getInstance().getIntOption("timeFormat", 1) == 1) {
                i = 24;
            }
            TimeBean timeBean = new TimeBean(this.view, str3, i);
            Bundle bundle = new Bundle();
            bundle.putSerializable("timebean", timeBean);
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.setData(bundle);
            ClockAppWidgetUtils.this.handler.sendMessage(obtain);
        }
    }

    public class TimeBean implements Serializable {
        int timeFormat;
        String timeTxt;
        View view;

        public TimeBean(View view2, String str, int i) {
            this.view = view2;
            this.timeTxt = str;
            this.timeFormat = i;
        }

        public View getView() {
            return this.view;
        }

        public void setView(View view2) {
            this.view = view2;
        }

        public String getTimeTxt() {
            return this.timeTxt;
        }

        public void setTimeTxt(String str) {
            this.timeTxt = str;
        }

        public int getTimeFormat() {
            return this.timeFormat;
        }

        public void setTimeFormat(int i) {
            this.timeFormat = i;
        }
    }
}
