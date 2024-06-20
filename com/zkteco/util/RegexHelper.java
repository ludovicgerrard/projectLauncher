package com.zkteco.util;

import java.util.regex.Pattern;

public final class RegexHelper {
    public static final String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    private RegexHelper() {
    }

    public static String convertFileNameRegex(String str) {
        return str.replaceAll("\\.", "\\\\.").replaceAll("\\*", "(.*)");
    }

    public static boolean checkIp(String str) {
        return Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").matcher(str).matches();
    }
}
