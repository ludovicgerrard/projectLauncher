package com.zkteco.android.employeemgmt.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import com.zktechnology.android.launcher2.LauncherApplication;

public class BitmapUtils {
    private static Allocation in;
    private static Allocation out;
    private static Type.Builder rgbaType;
    private static RenderScript rs;
    private static ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic;
    private static Type.Builder yuvType;

    static {
        RenderScript create = RenderScript.create(LauncherApplication.getLauncherApplicationContext());
        rs = create;
        yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(create, Element.U8_4(create));
    }

    private static void setYuv(int i, int i2, int i3) {
        if (yuvType == null) {
            RenderScript renderScript = rs;
            Type.Builder x = new Type.Builder(renderScript, Element.U8(renderScript)).setX(i);
            yuvType = x;
            in = Allocation.createTyped(rs, x.create(), 1);
            RenderScript renderScript2 = rs;
            Type.Builder y = new Type.Builder(renderScript2, Element.RGBA_8888(renderScript2)).setX(i2).setY(i3);
            rgbaType = y;
            out = Allocation.createTyped(rs, y.create(), 1);
        }
    }

    public static Bitmap nv21ToBitmap(byte[] bArr, int i, int i2) {
        setYuv(bArr.length, i, i2);
        in.copyFrom(bArr);
        yuvToRgbIntrinsic.setInput(in);
        yuvToRgbIntrinsic.forEach(out);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        out.copyTo(createBitmap);
        return createBitmap;
    }

    public static Bitmap convertBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap scaledBitmap(Bitmap bitmap) {
        int i;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i2 = 640;
        if (width > height) {
            i = (height * 640) / width;
        } else {
            int i3 = (width * 640) / height;
            i = 640;
            i2 = i3;
        }
        return Bitmap.createScaledBitmap(bitmap, i2, i, true);
    }

    public static synchronized Bitmap cropAvatar(Bitmap bitmap, Rect rect) {
        Bitmap bitmap2;
        synchronized (BitmapUtils.class) {
            bitmap2 = null;
            if (!(bitmap == null || rect == null)) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int i = rect.bottom - rect.top;
                int i2 = i / 3;
                rect.bottom = Math.min(height, rect.bottom + i2);
                rect.left = Math.max(0, rect.left - i2);
                rect.right = Math.min(width, rect.right + i2);
                rect.top = Math.max(0, (rect.top - (i / 2)) - i2);
                int i3 = rect.right - rect.left;
                int i4 = rect.bottom - rect.top;
                try {
                    if (!bitmap.isRecycled()) {
                        bitmap2 = Bitmap.createBitmap(bitmap, rect.left, rect.top, i3, i4);
                    }
                } catch (Exception | OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap2;
    }

    public static int calRectArea(Rect rect) {
        return Math.abs(rect.right - rect.left) * Math.abs(rect.bottom - rect.top);
    }
}
