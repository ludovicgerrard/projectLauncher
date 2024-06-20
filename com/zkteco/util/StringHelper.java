package com.zkteco.util;

import com.google.common.base.Ascii;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class StringHelper {
    private static final int BUFFER_SIZE = 8192;
    private static final String COMMA_LIST = " *, *";
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', WiegandConfig.CARD_FORMAT_C, 'D', WiegandConfig.CARD_FORMAT_E, WiegandConfig.CARD_FORMAT_F};
    private static final String NEWLINE_LIST = "\n";
    public static final String STRING_ENCODING = "UTF8";
    private static final String TAB_LIST = "\t";

    private StringHelper() {
    }

    public static String toTitleCase(String str) {
        if (str.length() == 0) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i] & 255;
            int i2 = i * 2;
            char[] cArr2 = HEX_CHARS;
            cArr[i2] = cArr2[b >>> 4];
            cArr[i2 + 1] = cArr2[b & Ascii.SI];
        }
        return new String(cArr);
    }

    public static String compress(String str) {
        byte[] bytes = str.getBytes();
        Deflater deflater = new Deflater(9, true);
        deflater.setInput(bytes);
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (!deflater.finished()) {
            byteArrayOutputStream.write(bArr, 0, deflater.deflate(bArr));
        }
        deflater.end();
        return bytesToHex(byteArrayOutputStream.toByteArray());
    }

    public static String decompress(String str) throws DataFormatException {
        byte[] hexToBytes = hexToBytes(str);
        Inflater inflater = new Inflater(true);
        inflater.setInput(hexToBytes, 0, hexToBytes.length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (!inflater.finished()) {
            byteArrayOutputStream.write(bArr, 0, inflater.inflate(bArr));
        }
        inflater.end();
        return new String(byteArrayOutputStream.toByteArray());
    }

    public static String escapeFormatString(String str) {
        return str.replaceAll("%", "%%");
    }

    public static String getCommaSeparatedElements(List<String> list) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static List<String> getCommaSeparatedElements(String str) {
        return Arrays.asList(str.split(COMMA_LIST));
    }

    public static String getFullName(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        StringBuilder append = sb.append(str).append(" ");
        if (str2 == null) {
            str2 = "";
        }
        return append.append(str2).toString();
    }

    public static Map<String, String> getMapFromStringArray(String[] strArr) {
        HashMap hashMap = new HashMap();
        for (String split : strArr) {
            String[] split2 = split.split(SimpleComparison.EQUAL_TO_OPERATION, 2);
            String str = split2[0];
            String str2 = null;
            if (split2.length == 2 && !split2[1].isEmpty()) {
                str2 = split2[1];
            }
            hashMap.put(str, str2);
        }
        return hashMap;
    }

    public static List<String> getNewLineSeparatedElements(String str) {
        return Arrays.asList(str.split("\n"));
    }

    public static String getRandomSequence(int i) {
        return new BigInteger(i, new SecureRandom()).toString(32);
    }

    public static String[] getStringArrayFrom(long[] jArr) {
        int length = jArr.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = String.valueOf(jArr[i]);
        }
        return strArr;
    }

    public static String getStringFromUTF8(byte[] bArr) {
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static List<String> getTabSeparatedElements(String str) {
        return Arrays.asList(str.split(TAB_LIST));
    }

    public static byte[] hexToBytes(String str) {
        byte[] bArr = new byte[(str.length() / 2)];
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 2;
            bArr[i / 2] = (byte) Integer.parseInt(str.substring(i, i2), 16);
            i = i2;
        }
        return bArr;
    }

    public static String padLeft(String str, String str2, int i) {
        int length = i - str.length();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(str2);
        }
        sb.append(str);
        return sb.toString();
    }

    public static String removeLineBreaks(String str) {
        return str.replaceAll("\n", " ").trim();
    }

    public static String toString(Object obj) {
        return (obj == null || obj.equals("null")) ? "" : String.valueOf(obj);
    }
}
