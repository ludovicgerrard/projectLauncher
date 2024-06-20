package com.zktechnology.android.utils;

import android.text.TextUtils;
import android.util.Log;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZkDigitConvertUtils {
    private static final String[] FA_DIGIT = {"‭٠‬", "‭١‬", "‭٢‬", "‭٣‬", "‭۴‬", "‭۵‬", "‭۶‬", "‭٧‬", "‭٨‬", "‭٩‬"};
    private static final String TAG = "ZkDigitConvertUtils";

    public static String getFaDigit(String str) {
        boolean z = true;
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 1) {
            z = false;
        }
        Log.d(TAG, "faToAr: isRtl = " + z + ", faDigit = " + str);
        if (!TextUtils.isEmpty(str) && z) {
            try {
                Matcher matcher = Pattern.compile("\\d").matcher(str);
                matcher.reset();
                StringBuffer stringBuffer = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(stringBuffer, FA_DIGIT[Integer.valueOf(matcher.group()).intValue()]);
                }
                return matcher.appendTail(stringBuffer).toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "faToAr: e = " + e.getMessage());
            }
        }
        return str;
    }
}
