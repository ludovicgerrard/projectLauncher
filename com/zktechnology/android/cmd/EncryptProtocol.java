package com.zktechnology.android.cmd;

import android.util.Log;
import com.google.common.base.Ascii;
import com.zktechnology.android.utils.HexUtils;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class EncryptProtocol {
    public static final int CMD_156 = 156;
    public static final int CMD_157 = 157;
    public static final int CMD_158 = 158;
    public static final int CMD_END = 85;
    public static final int CMD_HEAD = 170;
    public static final byte[] HEX_KEY = null;
    public static final int KEY_PUB = 114;
    public static final int MSG_200 = 200;
    public static final int PORT_ADDRESS = 1;
    private static final String TAG = "EncryptProtocol";
    public static final byte VERIFY_CONTINUE_VERIFY = -3;
    public static final byte VERIFY_FAILED = -2;
    public static final byte VERIFY_NO_PERMISSION = -1;
    public static final byte VERIFY_SUCCESS = 0;
    private static int[] actualLengthFor157 = new int[1];
    private static byte[] encryptDataEmpFor157 = new byte[1024];

    private static byte[] intToBytes4H(int i) {
        byte[] bArr = new byte[4];
        bArr[3] = (byte) ((i >> 24) & 255);
        bArr[2] = (byte) ((i >> 16) & 255);
        bArr[1] = (byte) ((i >> 8) & 255);
        bArr[0] = (byte) (i & 255);
        return bArr;
    }

    private static byte[] buildNormalMessage(byte b, byte b2, byte b3, byte b4, byte b5, byte[] bArr, byte[] bArr2, byte b6) {
        if (bArr == null || bArr2 == null || bArr2.length < 2) {
            return new byte[0];
        }
        int length = bArr.length + 8;
        byte[] bArr3 = new byte[length];
        bArr3[0] = b;
        bArr3[1] = b2;
        bArr3[2] = b3;
        bArr3[3] = b4;
        bArr3[4] = b5;
        for (int i = 5; i <= length - 4; i++) {
            bArr3[i] = bArr[i - 5];
        }
        bArr3[length - 3] = bArr2[0];
        bArr3[length - 2] = bArr2[1];
        bArr3[length - 1] = b6;
        return bArr3;
    }

    public static byte[] buildNormalMessage(byte b, byte b2, byte b3, byte[] bArr, byte b4) {
        if (bArr == null || b3 < 0) {
            return new byte[0];
        }
        int length = bArr.length;
        byte[] intToBytes = HexUtils.intToBytes(length, 2);
        HexUtils.reserveByte(intToBytes);
        int i = length + 8;
        byte[] bArr2 = new byte[i];
        bArr2[0] = b;
        bArr2[1] = b2;
        bArr2[2] = b3;
        bArr2[3] = intToBytes[0];
        bArr2[4] = intToBytes[1];
        byte[] cRCBytes = CRC16Standard.getCRCBytes(byteMergerAll(new byte[]{1}, new byte[]{b3}, intToBytes, bArr));
        for (int i2 = 5; i2 <= i - 4; i2++) {
            bArr2[i2] = bArr[i2 - 5];
        }
        bArr2[i - 3] = cRCBytes[0];
        bArr2[i - 2] = cRCBytes[1];
        bArr2[i - 1] = b4;
        return bArr2;
    }

    public static byte[] buildCommand156WithSyncTime() {
        byte[] time = getTime();
        return buildNormalMessage((byte) -86, (byte) 1, (byte) -100, (byte) 44, (byte) 0, time, CRC16Standard.getCRCBytes(byteMergerAll(new byte[]{1}, new byte[]{-100}, new byte[]{44}, new byte[]{0}, time)), (byte) 85);
    }

    public static byte[] buildCommand157(byte b, byte b2, byte[] bArr) {
        byte[] bArr2 = bArr;
        if (b < 0) {
            return new byte[0];
        }
        byte[] hexStringToBytes = HexUtils.hexStringToBytes(Long.toHexString(System.currentTimeMillis() / 1000));
        byte[] bArr3 = new byte[4];
        new Random().nextBytes(bArr3);
        byte[] intToBytes = HexUtils.intToBytes(bArr2.length + 10, 2);
        HexUtils.reserveByte(intToBytes);
        byte[] byteMergerAll = byteMergerAll(intToBytes, new byte[]{b}, hexStringToBytes, bArr3, new byte[]{b2}, bArr2);
        byte[] bArr4 = HEX_KEY;
        if (bArr4 == null) {
            return null;
        }
        ZKDESEncrypt.encryptTDES(byteMergerAll, byteMergerAll.length, encryptDataEmpFor157, actualLengthFor157, bArr4, bArr4.length);
        byte[] copyOfRange = Arrays.copyOfRange(encryptDataEmpFor157, 0, actualLengthFor157[0]);
        Log.i(TAG, "onClick: encrypt cmd157:" + HexUtils.bytes2HexString(copyOfRange));
        return buildNormalMessage((byte) -86, (byte) 1, (byte) -99, copyOfRange, (byte) 85);
    }

    public static byte[] buildCommand158(byte b) {
        byte[] bArr = {1};
        byte[] bArr2 = {1};
        byte[] byteMergerAll = byteMergerAll(bArr, bArr2, new byte[]{b});
        byte[] intToBytes = HexUtils.intToBytes(byteMergerAll.length, 2);
        HexUtils.reserveByte(intToBytes);
        byte[] byteMergerAll2 = byteMergerAll(intToBytes, byteMergerAll);
        Log.i("Magic", "buildCommand158: cmd158加密前=" + HexUtils.bytes2HexString(byteMergerAll2));
        byte[] encrypt = ZKDESEncrypt.encrypt(byteMergerAll2, byteMergerAll2.length);
        byte[] intToBytes2 = HexUtils.intToBytes(encrypt.length, 2);
        HexUtils.reserveByte(intToBytes2);
        return buildGeneralCommand((byte) -86, (byte) 1, (byte) -98, intToBytes2, encrypt, CRC16Standard.getCRCBytes(byteMergerAll(new byte[]{1}, new byte[]{-98}, intToBytes2, encrypt)), (byte) 85);
    }

    public static byte[] buildCommand158ForRelay(byte b, byte b2, byte b3, byte b4) {
        byte[] bArr = {Ascii.VT};
        byte[] bArr2 = {4};
        byte[] byteMergerAll = byteMergerAll(bArr, bArr2, new byte[]{b, b2, b3, b4});
        byte[] intToBytes = HexUtils.intToBytes(byteMergerAll.length, 2);
        HexUtils.reserveByte(intToBytes);
        byte[] byteMergerAll2 = byteMergerAll(intToBytes, byteMergerAll);
        Log.i("Magic", "buildCommand158: cmd158加密前=" + HexUtils.bytes2HexString(byteMergerAll2));
        byte[] encrypt = ZKDESEncrypt.encrypt(byteMergerAll2, byteMergerAll2.length);
        byte[] intToBytes2 = HexUtils.intToBytes(encrypt.length, 2);
        HexUtils.reserveByte(intToBytes2);
        return buildGeneralCommand((byte) -86, (byte) 1, (byte) -98, intToBytes2, encrypt, CRC16Standard.getCRCBytes(byteMergerAll(new byte[]{1}, new byte[]{-98}, intToBytes2, encrypt)), (byte) 85);
    }

    public static byte[] buildCommand158ForBuzzer(byte b, byte b2, byte b3) {
        byte[] bArr = {Ascii.FF};
        byte[] bArr2 = {3};
        byte[] byteMergerAll = byteMergerAll(bArr, bArr2, new byte[]{b, b2, b3});
        byte[] intToBytes = HexUtils.intToBytes(byteMergerAll.length, 2);
        HexUtils.reserveByte(intToBytes);
        byte[] byteMergerAll2 = byteMergerAll(intToBytes, byteMergerAll);
        Log.i("Magic", "buildCommand158: cmd158加密前=" + HexUtils.bytes2HexString(byteMergerAll2));
        byte[] encrypt = ZKDESEncrypt.encrypt(byteMergerAll2, byteMergerAll2.length);
        byte[] intToBytes2 = HexUtils.intToBytes(encrypt.length, 2);
        HexUtils.reserveByte(intToBytes2);
        return buildGeneralCommand((byte) -86, (byte) 1, (byte) -98, intToBytes2, encrypt, CRC16Standard.getCRCBytes(byteMergerAll(new byte[]{1}, new byte[]{-98}, intToBytes2, encrypt)), (byte) 85);
    }

    public static byte[] buildCommand158ForLED(byte b, byte b2, byte b3) {
        byte[] bArr = {Ascii.CR};
        byte[] bArr2 = {3};
        byte[] byteMergerAll = byteMergerAll(bArr, bArr2, new byte[]{b, b2, b3});
        byte[] intToBytes = HexUtils.intToBytes(byteMergerAll.length, 2);
        HexUtils.reserveByte(intToBytes);
        byte[] byteMergerAll2 = byteMergerAll(intToBytes, byteMergerAll);
        Log.i("Magic", "buildCommand158: cmd158加密前=" + HexUtils.bytes2HexString(byteMergerAll2));
        byte[] encrypt = ZKDESEncrypt.encrypt(byteMergerAll2, byteMergerAll2.length);
        byte[] intToBytes2 = HexUtils.intToBytes(encrypt.length, 2);
        HexUtils.reserveByte(intToBytes2);
        return buildGeneralCommand((byte) -86, (byte) 1, (byte) -98, intToBytes2, encrypt, CRC16Standard.getCRCBytes(byteMergerAll(new byte[]{1}, new byte[]{-98}, intToBytes2, encrypt)), (byte) 85);
    }

    public static byte[] buildCommand158ForReboot() {
        byte[] byteMergerAll = byteMergerAll(new byte[]{17}, new byte[]{0}, new byte[0]);
        byte[] intToBytes = HexUtils.intToBytes(byteMergerAll.length, 2);
        HexUtils.reserveByte(intToBytes);
        byte[] byteMergerAll2 = byteMergerAll(intToBytes, byteMergerAll);
        Log.i("Magic", "buildCommand158: cmd158加密前=" + HexUtils.bytes2HexString(byteMergerAll2));
        byte[] encrypt = ZKDESEncrypt.encrypt(byteMergerAll2, byteMergerAll2.length);
        byte[] intToBytes2 = HexUtils.intToBytes(encrypt.length, 2);
        HexUtils.reserveByte(intToBytes2);
        return buildGeneralCommand((byte) -86, (byte) 1, (byte) -98, intToBytes2, encrypt, CRC16Standard.getCRCBytes(byteMergerAll(new byte[]{1}, new byte[]{-98}, intToBytes2, encrypt)), (byte) 85);
    }

    static byte[] buildGeneralCommand(byte b, byte b2, byte b3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte b4) {
        return byteMergerAll(new byte[]{b}, new byte[]{b2}, new byte[]{b3}, bArr, bArr2, bArr3, new byte[]{b4});
    }

    private static byte[] getTime() {
        Calendar instance = Calendar.getInstance();
        TTime tTime = new TTime();
        tTime.setTm_sec(instance.get(13));
        tTime.setTm_min(instance.get(12));
        tTime.setTm_hour(instance.get(11));
        tTime.setTm_mday(instance.get(5));
        tTime.setTm_mon(instance.get(2));
        tTime.setTm_year(instance.get(1) - 1900);
        tTime.setTm_wday(instance.get(7));
        tTime.setTm_yday(instance.get(6));
        tTime.setTm_isdst(0);
        tTime.setTm_gmtoff(0);
        byte[] intToBytes4H = intToBytes4H(tTime.getTm_sec());
        byte[] intToBytes4H2 = intToBytes4H(tTime.getTm_min());
        byte[] intToBytes4H3 = intToBytes4H(tTime.getTm_hour());
        byte[] intToBytes4H4 = intToBytes4H(tTime.getTm_mday());
        byte[] intToBytes4H5 = intToBytes4H(tTime.getTm_mon());
        byte[] intToBytes4H6 = intToBytes4H(tTime.getTm_year());
        byte[] intToBytes4H7 = intToBytes4H(tTime.getTm_wday());
        byte[] intToBytes4H8 = intToBytes4H(tTime.getTm_isdst());
        return byteMergerAll(intToBytes4H, intToBytes4H2, intToBytes4H3, intToBytes4H4, intToBytes4H5, intToBytes4H6, intToBytes4H7, intToBytes4H(tTime.getTm_yday()), intToBytes4H8, new byte[8]);
    }

    public static byte[] getPubKey(byte[] bArr) {
        Log.i("Magic", "getPubKey: srcData=" + HexUtils.bytes2HexString(bArr));
        if (!(bArr == null || bArr.length == 0)) {
            if (bArr[13] == 114) {
                int intValue = Integer.valueOf(HexUtils.bytes2HexString(new byte[]{bArr[15], bArr[14]}), 16).intValue();
                byte[] bArr2 = new byte[intValue];
                System.arraycopy(bArr, 16, bArr2, 0, intValue);
                return bArr2;
            }
            Log.e("Magic", "getPubKey: error src data");
        }
        return null;
    }

    public static byte[] getDataFrom200(byte[] bArr) {
        if (bArr == null || bArr.length == 0 || bArr[2] != -56) {
            return null;
        }
        int intValue = Integer.valueOf(HexUtils.bytes2HexString(new byte[]{bArr[4], bArr[3]}), 16).intValue();
        byte[] bArr2 = new byte[intValue];
        System.arraycopy(bArr, 5, bArr2, 0, intValue);
        return bArr2;
    }

    private static byte[] byteMergerAll(byte[]... bArr) {
        int i = 0;
        for (byte[] length : bArr) {
            i += length.length;
        }
        byte[] bArr2 = new byte[i];
        int i2 = 0;
        for (byte[] bArr3 : bArr) {
            System.arraycopy(bArr3, 0, bArr2, i2, bArr3.length);
            i2 += bArr3.length;
        }
        return bArr2;
    }

    private void test() {
        System.out.println(HexUtils.bytes2HexString(buildNormalMessage((byte) -86, (byte) 1, (byte) -100, (byte) 0, (byte) 0, new byte[0], CRC16Standard.getCRCBytes(new byte[]{1, -100, 0, 0}), (byte) 85)));
    }
}
