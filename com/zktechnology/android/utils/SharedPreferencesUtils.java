package com.zktechnology.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.zktechnology.android.launcher2.LauncherApplication;

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils instance = new SharedPreferencesUtils();
    private final String DEFAULT_SHARED_PREFERENCES = "SharedPreferences_Launcher";

    private static synchronized void syncInit() {
        synchronized (SharedPreferencesUtils.class) {
            if (instance == null) {
                instance = new SharedPreferencesUtils();
            }
        }
    }

    public static SharedPreferencesUtils getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    private SharedPreferences getSharedPreferences() {
        return LauncherApplication.getApplication().getSharedPreferences("SharedPreferences_Launcher", 0);
    }

    public int getInt(String str, int i) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                return sharedPreferences.getInt(str, i);
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return i;
        }
    }

    public void putInt(String str, int i) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putInt(str, i);
                edit.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getLong(String str, long j) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                return sharedPreferences.getLong(str, j);
            }
            return j;
        } catch (Exception e) {
            e.printStackTrace();
            return j;
        }
    }

    public void putLong(String str, long j) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putLong(str, j);
                edit.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String str, String str2) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                return sharedPreferences.getString(str, str2);
            }
            return str2;
        } catch (Exception e) {
            e.printStackTrace();
            return str2;
        }
    }

    public void putString(String str, String str2) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(str, str2);
                edit.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String str, boolean z) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                return sharedPreferences.getBoolean(str, z);
            }
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }

    public void putBoolean(String str, boolean z) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean(str, z);
                edit.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String str) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences();
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.remove(str);
                edit.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void set(Context context, String str, Object obj) {
        String simpleName = obj.getClass().getSimpleName();
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        if ("String".equals(simpleName)) {
            edit.putString(str, (String) obj);
        } else if ("Integer".equals(simpleName)) {
            edit.putInt(str, ((Integer) obj).intValue());
        } else if ("Boolean".equals(simpleName)) {
            edit.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if ("Float".equals(simpleName)) {
            edit.putFloat(str, ((Float) obj).floatValue());
        } else if ("Long".equals(simpleName)) {
            edit.putLong(str, ((Long) obj).longValue());
        }
        edit.commit();
    }

    public static Object get(Context context, String str, Object obj) {
        String simpleName = obj.getClass().getSimpleName();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if ("String".equals(simpleName)) {
            return defaultSharedPreferences.getString(str, (String) obj);
        }
        if ("Integer".equals(simpleName)) {
            return Integer.valueOf(defaultSharedPreferences.getInt(str, ((Integer) obj).intValue()));
        }
        if ("Boolean".equals(simpleName)) {
            return Boolean.valueOf(defaultSharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue()));
        }
        if ("Float".equals(simpleName)) {
            return Float.valueOf(defaultSharedPreferences.getFloat(str, ((Float) obj).floatValue()));
        }
        if ("Long".equals(simpleName)) {
            return Long.valueOf(defaultSharedPreferences.getLong(str, ((Long) obj).longValue()));
        }
        return null;
    }
}
