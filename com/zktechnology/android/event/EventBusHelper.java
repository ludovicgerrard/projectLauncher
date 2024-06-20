package com.zktechnology.android.event;

import com.zktechnology.android.push.util.FileLogUtils;
import org.greenrobot.eventbus.EventBus;

public class EventBusHelper {
    public static void register(Object obj) {
        FileLogUtils.writeLauncherInitRecord("EventBus注册 " + obj.getClass().getSimpleName() + "    object.hashCode() == " + obj.hashCode());
        EventBus.getDefault().register(obj);
    }

    public static void unregister(Object obj) {
        FileLogUtils.writeLauncherInitRecord("EventBus注销 " + obj.getClass().getSimpleName() + "    object.hashCode() == " + obj.hashCode());
        EventBus.getDefault().unregister(obj);
    }

    public static void post(BaseEvent baseEvent) {
        EventBus.getDefault().post(baseEvent);
    }

    public static boolean isRegister(Object obj) {
        return EventBus.getDefault().isRegistered(obj);
    }
}
