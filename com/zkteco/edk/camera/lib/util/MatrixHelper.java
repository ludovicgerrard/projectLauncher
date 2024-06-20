package com.zkteco.edk.camera.lib.util;

import android.opengl.Matrix;

public class MatrixHelper {
    public static void perspectiveM(float[] fArr, float f, float f2, float f3, float f4) {
        float tan = (float) (1.0d / Math.tan(((double) ((float) ((((double) f) * 3.141592653589793d) / 180.0d))) / 2.0d));
        fArr[0] = tan / f2;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = 0.0f;
        fArr[5] = tan;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        float f5 = f4 - f3;
        fArr[10] = -((f4 + f3) / f5);
        fArr[11] = -1.0f;
        fArr[12] = 0.0f;
        fArr[13] = 0.0f;
        fArr[14] = -(((f4 * 2.0f) * f3) / f5);
        fArr[15] = 0.0f;
    }

    public static void changeModelMatrixInside(float[] fArr, float f, float f2, float f3, float f4) {
        float f5 = ((f * f4) / f2) / f3;
        Matrix.setIdentityM(fArr, 0);
        int i = (f5 > 1.0f ? 1 : (f5 == 1.0f ? 0 : -1));
        float f6 = i > 0 ? 1.0f / f5 : 1.0f;
        if (i > 0) {
            f5 = 1.0f;
        }
        Matrix.scaleM(fArr, 0, f6, f5, 1.0f);
    }

    public static float[] changeMvpMatrixCrop(float f, float f2, float f3, float f4) {
        float f5 = ((f * f4) / f2) / f3;
        float[] fArr = new float[16];
        Matrix.setIdentityM(fArr, 0);
        int i = (f5 > 1.0f ? 1 : (f5 == 1.0f ? 0 : -1));
        float f6 = i > 0 ? 1.0f : 1.0f / f5;
        if (i <= 0) {
            f5 = 1.0f;
        }
        Matrix.scaleM(fArr, 0, f6, f5, 1.0f);
        return fArr;
    }

    public static void flip(float[] fArr, boolean z, boolean z2) {
        if (z || z2) {
            float f = -1.0f;
            float f2 = z ? -1.0f : 1.0f;
            if (!z2) {
                f = 1.0f;
            }
            Matrix.scaleM(fArr, 0, f2, f, 1.0f);
        }
    }
}
