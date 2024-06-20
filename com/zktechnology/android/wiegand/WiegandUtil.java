package com.zktechnology.android.wiegand;

import java.util.regex.Pattern;

public class WiegandUtil {
    public static final String WG_TAG = "TAG_WIEGAND_";

    public static int byteToInt(byte b) {
        return b & 255;
    }

    public static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static String beforeBinaryStrAddZero(String str, int i) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() >= i) {
            return str;
        }
        int length = i - str.length();
        for (int i2 = 0; i2 < length; i2++) {
            stringBuffer.append("0");
        }
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    public static long binaryStrToLong(String str) {
        return Long.parseLong(str, 2);
    }

    public static int binaryStrToInt(String str) {
        return Integer.parseInt(str, 2);
    }

    public static String longToBinStr(long j) {
        return Long.toBinaryString(j);
    }

    public static String byteArrToBinStr4Long(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(Long.toBinaryString((long) ((b & 255) + 256)).substring(1));
        }
        return stringBuffer.toString();
    }

    public static byte[] binStrToByteArr4Long(String str) {
        int i = 0;
        int length = (str.length() / 8) + (str.length() % 8 == 0 ? 0 : 1);
        byte[] bArr = new byte[length];
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 8;
            int length2 = i3 > str.length() ? str.length() : i3;
            bArr[i] = Long.valueOf(str.substring(i2, length2), 2).byteValue();
            if (length2 - i2 < 8) {
                bArr[i] = (byte) (bArr[i] << ((8 - length2) + i2));
            }
            i++;
            i2 = i3;
        }
        return bArr;
    }
}
