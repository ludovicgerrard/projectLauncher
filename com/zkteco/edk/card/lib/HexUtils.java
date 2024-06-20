package com.zkteco.edk.card.lib;

import android.text.TextUtils;
import com.google.common.base.Ascii;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;

public class HexUtils {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', WiegandConfig.CARD_FORMAT_C, 'D', WiegandConfig.CARD_FORMAT_E, WiegandConfig.CARD_FORMAT_F};

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
            char[] cArr2 = HEX_DIGITS;
            cArr[i2] = cArr2[(bArr[i3] >>> 4) & 15];
            i2 = i4 + 1;
            cArr[i4] = cArr2[bArr[i3] & Ascii.SI];
        }
        return new String(cArr);
    }

    public static String bytes2HexString(byte[] bArr) {
        return bytes2HexString(bArr, bArr.length);
    }

    public static byte[] hexStringToByteArray(String str) {
        if (TextUtils.isEmpty(str)) {
            return new byte[0];
        }
        String replaceAll = str.replaceAll(" ", "");
        int length = replaceAll.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(replaceAll.charAt(i), 16) << 4) + Character.digit(replaceAll.charAt(i + 1), 16));
        }
        return bArr;
    }
}
