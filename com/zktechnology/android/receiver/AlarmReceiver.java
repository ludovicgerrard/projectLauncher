package com.zktechnology.android.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    private static String TIMER_RESTART_FUN = "TimerRestartFun";

    public void onReceive(Context context, Intent intent) {
        try {
            DataManager instance = DBManager.getInstance();
            if (instance.getStrOption(TIMER_RESTART_FUN, "0").equals("1")) {
                String[] split = instance.getStrOption(DBConfig.RESTART_TIME, "00:00").split(":");
                String str = split[0];
                String str2 = split[1];
                long currentTimeMillis = System.currentTimeMillis();
                Calendar instance2 = Calendar.getInstance();
                instance2.setTimeInMillis(currentTimeMillis);
                instance2.set(11, Integer.valueOf(str).intValue());
                instance2.set(12, Integer.valueOf(str2).intValue());
                instance2.set(13, 0);
                if (Math.abs(currentTimeMillis - instance2.getTimeInMillis()) <= 15000) {
                    AppUtils.rebootSystem(context, "alarm reboot");
                    return;
                }
                if (currentTimeMillis > instance2.getTimeInMillis()) {
                    instance2.add(5, 1);
                }
                PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
                if (alarmManager != null) {
                    alarmManager.setExact(0, instance2.getTimeInMillis(), broadcast);
                    return;
                }
                return;
            }
            cancelAlarm(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAlarm(Context context) {
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager != null) {
            alarmManager.cancel(broadcast);
        }
    }
}
