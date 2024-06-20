package com.zkteco.android.zkcore.utils;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class ZKActivityCollector {
    public static List<Activity> activities = new ArrayList();

    public static void addActivity(Activity activity) {
        Log.d("ZKActivityCollector", "addActivity: " + activity.getClass().getName() + "      " + Thread.currentThread().getName());
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        Log.d("ZKActivityCollector", "removeActivity: " + activity.getClass().getName() + "      " + Thread.currentThread().getName());
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : new ArrayList(activities)) {
            Log.d("ZKActivityCollector", "finishAll: " + activity.getClass().getName() + "      " + Thread.currentThread().getName());
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishAllNoThis(Activity activity) {
        for (Activity next : activities) {
            if (next != activity && !next.isFinishing()) {
                next.finish();
            }
        }
    }
}
