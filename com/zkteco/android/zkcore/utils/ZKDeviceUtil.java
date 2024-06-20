package com.zkteco.android.zkcore.utils;

public class ZKDeviceUtil {
    public static String getSerialNumber() {
        String str;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{"ro.serialno"});
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        return "/" + str;
    }
}
