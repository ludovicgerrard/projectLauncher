package com.zkteco.liveface562.util;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class RectRotateUtil {
    public static Rect rotateRect(Rect rect, int i, boolean z, int i2, int i3, int i4, int i5) {
        float f;
        float f2;
        float f3;
        int i6;
        RectF rectF = new RectF();
        Matrix matrix = new Matrix();
        float f4 = -1.0f;
        float f5 = 0.0f;
        if (i == 0) {
            float f6 = (float) rect.left;
            float f7 = (float) rect.top;
            float f8 = (float) rect.right;
            f3 = (float) rect.bottom;
            if (!z) {
                f4 = 1.0f;
            }
            matrix.setScale(f4, 1.0f);
            matrix.postTranslate(z ? (float) i4 : 0.0f, 0.0f);
            matrix.postScale(((float) i2) / ((float) i4), ((float) i3) / ((float) i5));
            f5 = f6;
            f2 = f7;
            f = f8;
        } else if (i != 90) {
            if (i == 180) {
                if (!z) {
                    f4 = 1.0f;
                }
                matrix.setScale(1.0f, f4);
                matrix.postTranslate(0.0f, z ? (float) i5 : 0.0f);
                matrix.postScale(((float) i2) / ((float) i4), ((float) i3) / ((float) i5));
                f5 = (float) rect.right;
                f2 = (float) rect.bottom;
                f = (float) rect.left;
                i6 = rect.top;
            } else if (i != 270) {
                f3 = 0.0f;
                f2 = 0.0f;
                f = 0.0f;
            } else {
                if (!z) {
                    f4 = 1.0f;
                }
                matrix.setScale(f4, 1.0f);
                matrix.postTranslate(z ? (float) i5 : 0.0f, 0.0f);
                matrix.postScale(((float) i2) / ((float) i5), ((float) i3) / ((float) i4));
                f5 = (float) (i5 - rect.bottom);
                f2 = (float) rect.left;
                f = (float) (i5 - rect.top);
                i6 = rect.right;
            }
            f3 = (float) i6;
        } else {
            if (!z) {
                f4 = 1.0f;
            }
            matrix.setScale(f4, 1.0f);
            matrix.postTranslate(z ? (float) i5 : 0.0f, 0.0f);
            matrix.postScale(((float) i2) / ((float) i5), ((float) i3) / ((float) i4));
            f5 = (float) rect.top;
            f2 = (float) (i4 - rect.right);
            f = (float) rect.bottom;
            f3 = (float) (i4 - rect.left);
        }
        matrix.mapRect(rectF, new RectF(f5, f2, f, f3));
        return new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
    }
}
