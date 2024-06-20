package com.zkteco.edk.hardware.encrypt;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Objects;

public class ZkSPUtils {
    private static final String SP_FILE_NAME = "Activation";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, 0);
    }

    public static void setInt(String str, int i) {
        SharedPreferences sharedPreferences2 = sharedPreferences;
        Objects.requireNonNull(sharedPreferences2, "ZkSPUtils not init!");
        sharedPreferences2.edit().putInt(str, i).apply();
    }

    public static int getInt(String str, int i) {
        SharedPreferences sharedPreferences2 = sharedPreferences;
        Objects.requireNonNull(sharedPreferences2, "ZkSPUtils not init!");
        return sharedPreferences2.getInt(str, i);
    }
}
