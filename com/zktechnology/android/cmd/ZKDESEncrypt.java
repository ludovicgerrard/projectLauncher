package com.zktechnology.android.cmd;

import android.util.Log;
import com.zktechnology.android.utils.HexUtils;
import java.util.Arrays;

public class ZKDESEncrypt {
    private static final String TAG = "ZKDESEncrypt";

    public static native int decryptTDES(byte[] bArr, int i, byte[] bArr2, int[] iArr, byte[] bArr3, int i2);

    public static native int encryptTDES(byte[] bArr, int i, byte[] bArr2, int[] iArr, byte[] bArr3, int i2);

    static {
        System.loadLibrary("zkandroidcrypto");
    }

    public static byte[] encrypt(byte[] bArr, int i) {
        byte[] bArr2 = new byte[1024];
        int[] iArr = new int[1];
        if (EncryptProtocol.HEX_KEY == null) {
            return null;
        }
        encryptTDES(bArr, i, bArr2, iArr, EncryptProtocol.HEX_KEY, EncryptProtocol.HEX_KEY.length);
        byte[] copyOfRange = Arrays.copyOfRange(bArr2, 0, iArr[0]);
        Log.i(TAG, "encrypt result=" + HexUtils.bytes2HexString(copyOfRange));
        return copyOfRange;
    }
}
