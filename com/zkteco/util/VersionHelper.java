package com.zkteco.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionHelper {
    private static final Pattern PATTERN = Pattern.compile(".*versionName=\"(.*)\".*", 32);

    private VersionHelper() {
    }

    public static String getVersion(String str) {
        String str2;
        try {
            str2 = FileHelper.getFileAsString(VersionHelper.class.getResourceAsStream("/version"));
        } catch (IOException e) {
            e.printStackTrace();
            str2 = null;
        }
        return getVersion(str, str2);
    }

    public static String getVersion(String str, String str2) {
        if (str2 == null) {
            return str;
        }
        Matcher matcher = PATTERN.matcher(str2);
        return matcher.matches() ? matcher.group(1) : str;
    }
}
