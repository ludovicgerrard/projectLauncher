package com.zkteco.android.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    private static final String COMMA_LIST = " *, *";
    private static final String NEWLINE_LIST = "\n";
    private static final Pattern STRING_ID = Pattern.compile("^@string/(\\w+)$");
    private static final String TAB_LIST = "\t";
    private static final String TAG = "com.zkteco.android.util.StringHelper";

    public static List<String> getCommaSeparatedElements(String str) {
        return Arrays.asList(str.split(COMMA_LIST));
    }

    public static Map<String, String> getMapFromStringArray(String[] strArr) {
        Log.d(TAG, "getMapFromStringArray: " + strArr);
        HashMap hashMap = new HashMap();
        for (String split : strArr) {
            String[] split2 = split.split(SimpleComparison.EQUAL_TO_OPERATION, 2);
            if (!split2[1].isEmpty()) {
                hashMap.put(split2[0], split2[1]);
            }
        }
        return hashMap;
    }

    public static List<String> getNewLineSeparatedElements(String str) {
        return Arrays.asList(str.split("\n"));
    }

    public static String[] getStringArrayFrom(long[] jArr) {
        int length = jArr.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = String.valueOf(jArr[i]);
        }
        return strArr;
    }

    public static String getStringByFullIdentifier(Context context, String str) {
        String stringIdentifier = getStringIdentifier(str);
        return stringIdentifier != null ? getStringByName(context, stringIdentifier) : str;
    }

    public static String getStringByName(Context context, String str) {
        try {
            return context.getString(getStringIdByName(context, str));
        } catch (Resources.NotFoundException unused) {
            return str;
        }
    }

    public static int getStringIdByName(Context context, String str) {
        return context.getResources().getIdentifier(str, "string", context.getPackageName());
    }

    public static String getStringFromUTF8(byte[] bArr) {
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }

    public static String getStringIdentifier(String str) {
        if (str != null && !str.isEmpty() && str.charAt(0) == '@') {
            Matcher matcher = STRING_ID.matcher(str);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public static List<String> getTabSeparatedElements(String str) {
        return Arrays.asList(str.split(TAB_LIST));
    }

    public static String removeLineBreaks(String str) {
        return str.replaceAll("\n", " ").trim();
    }
}
