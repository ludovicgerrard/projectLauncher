package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.SharedPreferencesInterface;
import com.zkteco.android.core.interfaces.SharedPreferencesProvider;
import com.zkteco.android.core.library.CoreProvider;

public class SharedPreferencesManager implements SharedPreferencesInterface {
    private SharedPreferencesProvider provider;

    public SharedPreferencesManager(Context context) {
        this.provider = SharedPreferencesProvider.getInstance(new CoreProvider(context));
    }

    public void set(String str, String str2) {
        this.provider.set(str, str2);
    }

    public String get(String str, String str2) {
        return this.provider.get(str, str2);
    }

    public void remove(String str) {
        this.provider.remove(str);
    }
}
