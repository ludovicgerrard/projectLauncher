package com.zktechnology.android.utils;

public class MirrorUtils {
    private static MirrorUtils mInstance;

    public static MirrorUtils getInstance() {
        synchronized (MirrorUtils.class) {
            if (mInstance == null) {
                mInstance = new MirrorUtils();
            }
        }
        return mInstance;
    }

    public void Mirror(byte[] bArr, int i, int i2) {
        int i3;
        int i4 = 0;
        int i5 = 0;
        while (i5 < i2) {
            int i6 = i5 * i;
            i5++;
            for (int i7 = (i5 * i) - 1; i6 < i7; i7--) {
                byte b = bArr[i6];
                bArr[i6] = bArr[i7];
                bArr[i7] = b;
                i6++;
            }
        }
        int i8 = i * i2;
        int i9 = 0;
        while (true) {
            i3 = i2 / 2;
            if (i9 >= i3) {
                break;
            }
            int i10 = (i9 * i) / 2;
            i9++;
            for (int i11 = ((i9 * i) / 2) - 1; i10 < i11; i11--) {
                int i12 = i10 + i8;
                byte b2 = bArr[i12];
                int i13 = i11 + i8;
                bArr[i12] = bArr[i13];
                bArr[i13] = b2;
                i10++;
            }
        }
        int i14 = (i8 / 4) * 5;
        while (i4 < i3) {
            int i15 = (i4 * i) / 2;
            i4++;
            for (int i16 = ((i4 * i) / 2) - 1; i15 < i16; i16--) {
                int i17 = i15 + i14;
                byte b3 = bArr[i17];
                int i18 = i16 + i14;
                bArr[i17] = bArr[i18];
                bArr[i18] = b3;
                i15++;
            }
        }
    }
}
