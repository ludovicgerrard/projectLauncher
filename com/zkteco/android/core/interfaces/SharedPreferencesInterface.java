package com.zkteco.android.core.interfaces;

public interface SharedPreferencesInterface {
    public static final String SHAREDPREFERENCES_GET = "sharedpreferences-get";
    public static final String SHAREDPREFERENCES_REMOVE = "sharedpreferences-remove";
    public static final String SHAREDPREFERENCES_SET = "sharedpreferences-set";

    String get(String str, String str2);

    void remove(String str);

    void set(String str, String str2);
}
