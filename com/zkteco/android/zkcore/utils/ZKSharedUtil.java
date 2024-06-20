package com.zkteco.android.zkcore.utils;

import android.content.Context;
import com.zkteco.android.core.interfaces.SharedPreferencesProvider;
import com.zkteco.android.core.library.CoreProvider;

public class ZKSharedUtil {
    private SharedPreferencesProvider provider;

    public ZKSharedUtil(Context context) {
        this.provider = SharedPreferencesProvider.getInstance(new CoreProvider(context));
    }

    public void putInt(String str, int i) {
        this.provider.set(str, String.valueOf(i));
    }

    public int getInt(String str, int i) {
        return Integer.valueOf(this.provider.get(str, String.valueOf(i))).intValue();
    }

    public void putBoolean(String str, boolean z) {
        this.provider.set(str, String.valueOf(z));
    }

    public boolean getBoolean(String str, boolean z) {
        return Boolean.valueOf(this.provider.get(str, String.valueOf(z))).booleanValue();
    }

    public void putString(String str, String str2) {
        this.provider.set(str, String.valueOf(str2));
    }

    public String getString(String str, String str2) {
        return this.provider.get(str, String.valueOf(str2));
    }

    public void removeKey(String str) {
        this.provider.remove(str);
    }
}
