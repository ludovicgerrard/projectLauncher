package com.zktechnology.android.utils;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class StrUtil {
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }
        return str.trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static int chineseLength(String str) {
        int i = 0;
        if (isEmpty(str)) {
            return 0;
        }
        int i2 = 0;
        while (i < str.length()) {
            int i3 = i + 1;
            if (str.substring(i, i3).matches("[Α-￥]")) {
                i2 += 2;
            }
            i = i3;
        }
        return i2;
    }

    public static int strLength(String str) {
        int i = 0;
        if (isEmpty(str)) {
            return 0;
        }
        int i2 = 0;
        while (i < str.length()) {
            int i3 = i + 1;
            i2 = str.substring(i, i3).matches("[Α-￥]") ? i2 + 2 : i2 + 1;
            i = i3;
        }
        return i2;
    }

    public static int subStringLength(String str, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < str.length()) {
            int i4 = i2 + 1;
            i3 = str.substring(i2, i4).matches("[Α-￥]") ? i3 + 2 : i3 + 1;
            if (i3 >= i) {
                return i2;
            }
            i2 = i4;
        }
        return 0;
    }

    public static Boolean isMobileNo(String str) {
        try {
            return Boolean.valueOf(Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(str).matches());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean isNumberLetter(String str) {
        if (str.matches("^[A-Za-z0-9]+$")) {
            return true;
        }
        return false;
    }

    public static Boolean isNumber(String str) {
        if (str.matches("^[0-9]+$")) {
            return true;
        }
        return false;
    }

    public static Boolean isEmail(String str) {
        if (str.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {
            return true;
        }
        return false;
    }

    public static Boolean isChinese(String str) {
        boolean z = true;
        if (!isEmpty(str)) {
            int i = 0;
            while (i < str.length()) {
                int i2 = i + 1;
                if (!str.substring(i, i2).matches("[Α-￥]")) {
                    z = false;
                }
                i = i2;
            }
        }
        return z;
    }

    public static Boolean isContainChinese(String str) {
        int i = 0;
        boolean z = false;
        if (!isEmpty(str)) {
            while (i < str.length()) {
                int i2 = i + 1;
                if (str.substring(i, i2).matches("[Α-￥]")) {
                    z = true;
                }
                i = i2;
            }
        }
        return z;
    }

    public static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                inputStream.close();
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                throw th;
            }
        }
        if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
            sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
        }
        try {
            inputStream.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return sb.toString();
    }

    public static String dateTimeFormat(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            if (isEmpty(str)) {
                return null;
            }
            String[] split = str.split(" ");
            if (split.length > 0) {
                for (String str2 : split) {
                    if (str2.indexOf("-") != -1) {
                        String[] split2 = str2.split("-");
                        for (int i = 0; i < split2.length; i++) {
                            sb.append(strFormat2(split2[i]));
                            if (i < split2.length - 1) {
                                sb.append("-");
                            }
                        }
                    } else if (str2.indexOf(":") != -1) {
                        sb.append(" ");
                        String[] split3 = str2.split(":");
                        for (int i2 = 0; i2 < split3.length; i2++) {
                            sb.append(strFormat2(split3[i2]));
                            if (i2 < split3.length - 1) {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                return "0" + str;
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String cutString(String str, int i) {
        return cutString(str, i, "");
    }

    public static String cutString(String str, int i, String str2) {
        if (strlen(str, "GBK") <= i) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char c2 = charArray[i2];
            stringBuffer.append(c2);
            i3 = c2 > 256 ? i3 + 2 : i3 + 1;
            if (i3 < i) {
                i2++;
            } else if (str2 != null) {
                stringBuffer.append(str2);
            }
        }
        return stringBuffer.toString();
    }

    public static String cutStringFromChar(String str, String str2, int i) {
        int indexOf;
        int i2;
        if (!isEmpty(str) && (indexOf = str.indexOf(str2)) != -1 && str.length() > (i2 = indexOf + i)) {
            return str.substring(i2);
        }
        return "";
    }

    public static int strlen(String str, String str2) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        try {
            return str.getBytes(str2).length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getSizeDesc(long j) {
        String str;
        if (j >= 1024) {
            j >>= 10;
            if (j >= 1024) {
                j >>= 10;
                if (j >= 1024) {
                    j >>= 10;
                    str = "G";
                } else {
                    str = "M";
                }
            } else {
                str = "K";
            }
        } else {
            str = "B";
        }
        return j + str;
    }

    public static long ip2int(String str) {
        String[] split = str.replace(".", ",").split(",");
        return (Long.valueOf(split[0]).longValue() << 24) | (Long.valueOf(split[1]).longValue() << 16) | (Long.valueOf(split[2]).longValue() << 8) | Long.valueOf(split[3]).longValue();
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x007e A[SYNTHETIC, Splitter:B:28:0x007e] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0086 A[Catch:{ Exception -> 0x0082 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyToFilesystem(android.content.Context r3, java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            r0.mkdir()
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            boolean r1 = r0.isFile()
            if (r1 == 0) goto L_0x002d
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x002d
            r0.delete()
        L_0x002d:
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            boolean r0 = r0.exists()
            r1 = 0
            if (r0 == 0) goto L_0x008d
            r0 = 0
            r3.getAssets()     // Catch:{ Exception -> 0x0077 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0077 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0077 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0073 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0073 }
            r2.<init>()     // Catch:{ Exception -> 0x0073 }
            java.lang.StringBuilder r5 = r2.append(r5)     // Catch:{ Exception -> 0x0073 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0073 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0073 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0073 }
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x0071 }
        L_0x005c:
            int r6 = r3.read(r5)     // Catch:{ Exception -> 0x0071 }
            if (r6 <= 0) goto L_0x0069
            r4.write(r5, r1, r6)     // Catch:{ Exception -> 0x0071 }
            r4.flush()     // Catch:{ Exception -> 0x0071 }
            goto L_0x005c
        L_0x0069:
            r3.close()     // Catch:{ Exception -> 0x0071 }
            r4.close()     // Catch:{ Exception -> 0x0071 }
            r3 = 1
            return r3
        L_0x0071:
            r5 = move-exception
            goto L_0x0075
        L_0x0073:
            r5 = move-exception
            r4 = r0
        L_0x0075:
            r0 = r3
            goto L_0x0079
        L_0x0077:
            r5 = move-exception
            r4 = r0
        L_0x0079:
            r5.printStackTrace()
            if (r0 == 0) goto L_0x0084
            r0.close()     // Catch:{ Exception -> 0x0082 }
            goto L_0x0084
        L_0x0082:
            r3 = move-exception
            goto L_0x008a
        L_0x0084:
            if (r4 == 0) goto L_0x008d
            r4.close()     // Catch:{ Exception -> 0x0082 }
            goto L_0x008d
        L_0x008a:
            r3.printStackTrace()
        L_0x008d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.StrUtil.copyToFilesystem(android.content.Context, java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public static void main(String[] strArr) {
        System.out.println(dateTimeFormat("2012-3-2 12:2:20"));
    }
}
