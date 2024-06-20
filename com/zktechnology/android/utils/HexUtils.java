package com.zktechnology.android.utils;

import com.google.common.base.Ascii;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;

public class HexUtils {
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', WiegandConfig.CARD_FORMAT_C, 'D', WiegandConfig.CARD_FORMAT_E, WiegandConfig.CARD_FORMAT_F};

    public static byte[] intToBytes(int i, int i2) {
        if (i2 < 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((i >> (((i2 - i3) - 1) * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || "".equals(str)) {
            return new byte[0];
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (Integer.parseInt(str.substring(i2, i2 + 2), 16) & 255);
        }
        return bArr;
    }

    public static void reserveByte(byte[] bArr) {
        int i = 0;
        for (int length = bArr.length - 1; i < length; length--) {
            byte b = bArr[length];
            bArr[length] = bArr[i];
            bArr[i] = b;
            i++;
        }
    }

    private static String bytes2HexString(byte[] bArr, int i) {
        if (bArr == null || i <= 0) {
            return null;
        }
        char[] cArr = new char[(i << 1)];
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + 1;
            char[] cArr2 = hexDigits;
            cArr[i2] = cArr2[(bArr[i3] >>> 4) & 15];
            i2 = i4 + 1;
            cArr[i4] = cArr2[bArr[i3] & Ascii.SI];
        }
        return new String(cArr);
    }

    public static String bytes2HexString(byte[] bArr) {
        return bytes2HexString(bArr, bArr.length);
    }

    public String convertStringToHex(String str) {
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (char hexString : charArray) {
            stringBuffer.append(Integer.toHexString(hexString));
        }
        return stringBuffer.toString();
    }

    public static String convertHexToString(String str) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int i = 0;
        while (i < str.length() - 1) {
            int i2 = i + 2;
            int parseInt = Integer.parseInt(str.substring(i, i2), 16);
            sb.append((char) parseInt);
            sb2.append(parseInt);
            i = i2;
        }
        return sb.toString();
    }
}
