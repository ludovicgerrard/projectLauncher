package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class SharedPreferencesProvider extends AbstractProvider implements SharedPreferencesInterface {
    private SharedPreferencesProvider(Provider provider) {
        super(provider);
    }

    public static SharedPreferencesProvider getInstance(Provider provider) {
        return new SharedPreferencesProvider(provider);
    }

    public void set(String str, String str2) {
        getProvider().invoke(SharedPreferencesInterface.SHAREDPREFERENCES_SET, str, str2);
    }

    public String get(String str, String str2) {
        return (String) getProvider().invoke(SharedPreferencesInterface.SHAREDPREFERENCES_GET, str, str2);
    }

    public void remove(String str) {
        getProvider().invoke(SharedPreferencesInterface.SHAREDPREFERENCES_REMOVE, str);
    }
}
