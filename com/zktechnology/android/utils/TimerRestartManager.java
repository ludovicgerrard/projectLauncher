package com.zktechnology.android.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.zktechnology.android.receiver.AlarmReceiver;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import java.util.Calendar;

public class TimerRestartManager {
    /* access modifiers changed from: private */
    public static String ACTION_TIMER_RESTART = "com.zkteco.android.launcher.ACTION_TIMER_RESTART";
    private DataManager dataManager = DBManager.getInstance();
    private Context mContext;
    private TimerRestartReceiver timerRestartReceiver = new TimerRestartReceiver(this);

    public TimerRestartManager(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TIMER_RESTART);
        this.mContext.registerReceiver(this.timerRestartReceiver, intentFilter);
        initAlarm();
    }

    public void initAlarm() {
        try {
            String[] split = this.dataManager.getStrOption(DBConfig.RESTART_TIME, "00:00").split(":");
            String str = split[0];
            String str2 = split[1];
            long currentTimeMillis = System.currentTimeMillis();
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(currentTimeMillis);
            instance.set(11, Integer.valueOf(str).intValue());
            instance.set(12, Integer.valueOf(str2).intValue());
            instance.set(13, 0);
            if (currentTimeMillis > instance.getTimeInMillis()) {
                instance.add(5, 1);
            }
            PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.mContext, AlarmReceiver.class), 0);
            AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (alarmManager != null) {
                alarmManager.setExact(0, instance.getTimeInMillis(), broadcast);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class TimerRestartReceiver extends ZKReceiver<TimerRestartManager> {
        public TimerRestartReceiver(TimerRestartManager timerRestartManager) {
            super(timerRestartManager);
        }

        public void onReceive(TimerRestartManager timerRestartManager, Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equals(TimerRestartManager.ACTION_TIMER_RESTART)) {
                timerRestartManager.initAlarm();
            }
        }
    }

    public void onDestroy() {
        this.mContext.unregisterReceiver(this.timerRestartReceiver);
    }
}
