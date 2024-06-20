package com.zktechnology.android.utils;

import android.app.backup.BackupManager;
import android.content.res.Configuration;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import java.util.Locale;

public class LanguageUtil {
    private static LanguageUtil mSingleton;

    public static LanguageUtil getInstance() {
        synchronized (LanguageUtil.class) {
            if (mSingleton == null) {
                mSingleton = new LanguageUtil();
            }
        }
        return mSingleton;
    }

    public void setLanguage(int i) {
        boolean z = true;
        if (DBManager.getInstance().getIntOption("~ML", 1) != 1) {
            z = false;
        }
        Locale languageCode = getLanguageCode(i);
        if (z) {
            if (languageCode == null) {
                DBManager.getInstance().setIntOption("Language", 69);
                languageCode = new Locale("en", "US");
            }
            if (!Locale.getDefault().toString().equals(languageCode.toString())) {
                setLocaleLanguage(languageCode);
            }
        } else if (languageCode == null) {
            DBManager.getInstance().setIntOption("Language", 69);
        }
    }

    private Locale getLanguageCode(int i) {
        if (i == 65) {
            return new Locale("fa", "IR");
        }
        if (i == 66) {
            return new Locale("ar");
        }
        if (i == 69) {
            return new Locale("en", "US");
        }
        if (i == 70) {
            return new Locale("fr", "FR");
        }
        if (i == 78) {
            return new Locale("es", "AR");
        }
        if (i == 80) {
            return new Locale("pt", "BR");
        }
        if (i == 86) {
            return new Locale("vi", "VN");
        }
        if (i == 97) {
            return new Locale("es", "ES");
        }
        if (i == 101) {
            return new Locale("es", "MX");
        }
        if (i == 116) {
            return new Locale("tr", "TR");
        }
        if (i == 119) {
            return new Locale("zh", "TW");
        }
        switch (i) {
            case 73:
                return new Locale(Common.GPIO_DIRCTION_IN, BiometricCommuCMD.FIELD_DESC_TMP_ID);
            case 74:
                return new Locale("ja", "JP");
            case 75:
                return new Locale("ko", "KR");
            case 76:
                return new Locale("th", "TH");
            default:
                switch (i) {
                    case 82:
                        return new Locale("ru", "RU");
                    case 83:
                        return new Locale("zh", "CN");
                    case 84:
                        return new Locale("zh", "HK");
                    default:
                        return null;
                }
        }
    }

    public void setLocaleLanguage(Locale locale) {
        try {
            Class<?> cls = Class.forName("android.app.IActivityManager");
            Class<?> cls2 = Class.forName("android.app.ActivityManagerNative");
            Object invoke = cls2.getDeclaredMethod("getDefault", new Class[0]).invoke(cls2, new Object[0]);
            Configuration configuration = (Configuration) cls.getDeclaredMethod("getConfiguration", new Class[0]).invoke(invoke, new Object[0]);
            configuration.locale = locale;
            Class.forName("android.content.res.Configuration").getField("userSetLocale").set(configuration, true);
            cls.getDeclaredMethod("updateConfiguration", new Class[]{Configuration.class}).invoke(invoke, new Object[]{configuration});
            BackupManager.dataChanged("com.android.providers.settings");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
