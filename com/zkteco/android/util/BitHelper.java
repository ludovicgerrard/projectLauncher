package com.zkteco.android.util;

import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public final class BitHelper {
    private static final String TAG = "com.zkteco.android.util.BitHelper";

    public static byte[] getBytes(byte b) {
        return new byte[]{(byte) (b & 255)};
    }

    private BitHelper() {
    }

    public static void byteListToArray(List<Byte> list, byte[] bArr) {
        for (int i = 0; i < list.size(); i++) {
            bArr[i] = list.get(i).byteValue();
        }
    }

    public static Number fromBytes(ByteOrder byteOrder, byte... bArr) {
        if (byteOrder == null) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        }
        if (byteOrder == null) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        }
        int length = bArr.length;
        if (length == 1) {
            return Byte.valueOf(bArr[0]);
        }
        if (length == 2) {
            return Short.valueOf(fromBytes(byteOrder, bArr[0], bArr[1]));
        }
        if (length == 3) {
            return Integer.valueOf(fromBytes(byteOrder, bArr[0], bArr[1], bArr[2]));
        }
        if (length == 4) {
            return Integer.valueOf(fromBytes(byteOrder, bArr[0], bArr[1], bArr[2], bArr[3]));
        }
        String str = TAG;
        Log.w(str, "Data type length not supported in fromBytes()");
        Log.e(str, "Unknown byte size number");
        return null;
    }

    private static short fromBytes(ByteOrder byteOrder, byte b, byte b2) {
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.order(byteOrder);
        allocate.position(0);
        allocate.put(b);
        allocate.put(b2);
        allocate.position(0);
        return allocate.getShort();
    }

    public static int fromBytes(ByteOrder byteOrder, byte b, byte b2, byte b3) {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(byteOrder);
        allocate.position(0);
        allocate.put(b);
        allocate.put(b2);
        allocate.put(b3);
        allocate.put((byte) 0);
        allocate.position(0);
        return allocate.getInt();
    }

    public static int fromBytes(ByteOrder byteOrder, byte b, byte b2, byte b3, byte b4) {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(byteOrder);
        allocate.position(0);
        allocate.put(b);
        allocate.put(b2);
        allocate.put(b3);
        allocate.put(b4);
        allocate.position(0);
        return allocate.getInt();
    }

    public static byte[] getBytes(int i, ByteOrder byteOrder) {
        byte[] bArr = new byte[4];
        if (byteOrder == null || byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
            bArr[0] = (byte) (i & 255);
            bArr[1] = (byte) ((i >> 8) & 255);
            bArr[2] = (byte) ((i >> 16) & 255);
            bArr[3] = (byte) ((i >> 24) & 255);
        } else {
            bArr[3] = (byte) (i & 255);
            bArr[2] = (byte) ((i >> 8) & 255);
            bArr[1] = (byte) ((i >> 16) & 255);
            bArr[0] = (byte) ((i >> 24) & 255);
        }
        return bArr;
    }

    public static byte[] getBytes(short s, ByteOrder byteOrder) {
        byte[] bArr = new byte[2];
        byte b = (byte) (s & 255);
        byte b2 = (byte) ((s >> 8) & 255);
        if (byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
            bArr[0] = b;
            bArr[1] = b2;
        } else {
            bArr[0] = b2;
            bArr[1] = b;
        }
        return bArr;
    }
}
