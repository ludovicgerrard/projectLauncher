package com.zkteco.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static final String DATE_PATTERN_1 = "dd/MM/yyyy HH:mm";

    private DateHelper() {
    }

    public static String convert(Date date, String str) {
        return new SimpleDateFormat(str, Locale.getDefault()).format(date);
    }

    public static String convert(long j, String str) {
        return new SimpleDateFormat(str, Locale.getDefault()).format(new Date(j));
    }
}
