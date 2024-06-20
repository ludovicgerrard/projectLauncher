package com.zkteco.android.util;

import android.content.res.Configuration;
import android.util.Log;
import com.zkteco.util.StringHelper;
import java.text.Collator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocaleHelper {
    private static final String TAG = "com.zkteco.android.util.LocaleHelper";

    public static class ComparableLocale implements Comparable<ComparableLocale> {
        private static Map<String, String> SPECIAL_CODES;
        private String label;
        private Locale locale;

        static {
            HashMap hashMap = new HashMap();
            SPECIAL_CODES = hashMap;
            hashMap.put("zh_CN", "简体");
            SPECIAL_CODES = Collections.unmodifiableMap(SPECIAL_CODES);
        }

        public ComparableLocale(String str) {
            String[] split = str.split("_");
            if (split.length == 1) {
                this.locale = new Locale(str);
            } else if (split.length == 2) {
                this.locale = new Locale(split[0], split[1]);
            } else {
                throw new IllegalArgumentException("Unsupported/wrong code format: " + str);
            }
            if (SPECIAL_CODES.containsKey(str)) {
                StringBuilder sb = new StringBuilder();
                Locale locale2 = this.locale;
                this.label = sb.append(StringHelper.toTitleCase(locale2.getDisplayLanguage(locale2))).append(" (").append(SPECIAL_CODES.get(str)).append(")").toString();
                return;
            }
            Locale locale3 = this.locale;
            this.label = StringHelper.toTitleCase(locale3.getDisplayName(locale3));
        }

        public Locale getLocale() {
            return this.locale;
        }

        public int compareTo(ComparableLocale comparableLocale) {
            return Collator.getInstance().compare(this.label, comparableLocale.label);
        }

        public String toString() {
            return this.label;
        }
    }

    public static void updateLocale(Locale locale) {
        if (locale == null) {
            Log.w(TAG, "updateLocale >> Null locale!");
            return;
        }
        try {
            Class<?> cls = Class.forName("android.app.IActivityManager");
            Class<?> cls2 = Class.forName("android.app.ActivityManagerNative");
            Object invoke = cls2.getDeclaredMethod("getDefault", new Class[0]).invoke(cls2, new Object[0]);
            Configuration configuration = (Configuration) cls.getDeclaredMethod("getConfiguration", new Class[0]).invoke(invoke, new Object[0]);
            configuration.locale = locale;
            configuration.getClass().getDeclaredField("userSetLocale").setBoolean(configuration, true);
            cls.getDeclaredMethod("updateConfiguration", new Class[]{Configuration.class}).invoke(invoke, new Object[]{configuration});
        } catch (Exception e) {
            String str = TAG;
            Log.e(str, "Could not set system-wide Locale. This method uses an undocumented API by reflection, might fail in Android versions that are not 4.1.");
            Log.e(str, Log.getStackTraceString(e));
        }
    }

    private LocaleHelper() {
    }
}
