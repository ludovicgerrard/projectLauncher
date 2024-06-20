package com.zkteco.liveface562.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.ExifInterface;
import androidx.core.view.MotionEventCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.zkteco.android.zkcore.view.util.Constant;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ZkBitmapUtils {
    public static byte[] getNV21(int i, int i2, Bitmap bitmap) {
        int i3 = i * i2;
        int[] iArr = new int[i3];
        bitmap.getPixels(iArr, 0, i, 0, 0, i, i2);
        byte[] bArr = new byte[((i3 * 3) / 2)];
        encodeYUV420SP(bArr, iArr, i, i2);
        bitmap.recycle();
        return bArr;
    }

    private static void encodeYUV420SP(byte[] bArr, int[] iArr, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        int i5 = i3 * i4;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < i4; i8++) {
            int i9 = 0;
            while (i9 < i3) {
                int i10 = iArr[i7];
                int i11 = (iArr[i7] & 16711680) >> 16;
                int i12 = (iArr[i7] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                int i13 = 255;
                int i14 = (iArr[i7] & 255) >> 0;
                int i15 = (((((i11 * 66) + (i12 * 129)) + (i14 * 25)) + 128) >> 8) + 16;
                int i16 = (((((i11 * -38) - (i12 * 74)) + (i14 * 112)) + 128) >> 8) + 128;
                int i17 = (((((i11 * 112) - (i12 * 94)) - (i14 * 18)) + 128) >> 8) + 128;
                int i18 = i6 + 1;
                if (i15 < 0) {
                    i15 = 0;
                } else if (i15 > 255) {
                    i15 = 255;
                }
                bArr[i6] = (byte) i15;
                if (i8 % 2 == 0 && i7 % 2 == 0) {
                    int i19 = i5 + 1;
                    if (i17 < 0) {
                        i17 = 0;
                    } else if (i17 > 255) {
                        i17 = 255;
                    }
                    bArr[i5] = (byte) i17;
                    i5 = i19 + 1;
                    if (i16 < 0) {
                        i13 = 0;
                    } else if (i16 <= 255) {
                        i13 = i16;
                    }
                    bArr[i19] = (byte) i13;
                }
                i7++;
                i9++;
                i6 = i18;
            }
        }
    }

    public static Bitmap nv21ToBitmap(byte[] bArr, int i, int i2) {
        Bitmap bitmap = null;
        try {
            YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, (int[]) null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, i, i2), 80, byteArrayOutputStream);
            bitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
            byteArrayOutputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static int getExifOrientation(String str) {
        ExifInterface exifInterface;
        int attributeInt;
        try {
            exifInterface = new ExifInterface(str);
        } catch (IOException e) {
            e.printStackTrace();
            exifInterface = null;
        }
        if (!(exifInterface == null || (attributeInt = exifInterface.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, -1)) == -1)) {
            if (attributeInt == 3) {
                return Opcodes.GETFIELD;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt != 8) {
                return 0;
            }
            return Constant.DEFAULT_START_ANGLE;
        }
        return 0;
    }
}
