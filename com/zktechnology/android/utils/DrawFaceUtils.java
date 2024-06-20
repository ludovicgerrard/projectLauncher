package com.zktechnology.android.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class DrawFaceUtils {
    public static void drawPreview(Canvas canvas, Paint paint) {
        if (canvas == null) {
        }
    }

    public static void drawFaceRect(Canvas canvas, Rect rect, Paint paint) {
        int i = (rect.bottom - rect.top) / 5;
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        canvas2.drawLine((float) rect.left, (float) rect.top, (float) rect.left, (float) (rect.top + i), paint2);
        canvas2.drawLine((float) rect.left, (float) rect.top, (float) (rect.left + i), (float) rect.top, paint2);
        canvas2.drawLine((float) rect.right, (float) rect.top, (float) rect.right, (float) (rect.top + i), paint2);
        canvas2.drawLine((float) rect.right, (float) rect.top, (float) (rect.right - i), (float) rect.top, paint2);
        canvas2.drawLine((float) rect.right, (float) rect.bottom, (float) rect.right, (float) (rect.bottom - i), paint2);
        canvas2.drawLine((float) rect.right, (float) rect.bottom, (float) (rect.right - i), (float) rect.bottom, paint2);
        canvas2.drawLine((float) rect.left, (float) rect.bottom, (float) rect.left, (float) (rect.bottom - i), paint2);
        canvas2.drawLine((float) rect.left, (float) rect.bottom, (float) (rect.left + i), (float) rect.bottom, paint2);
    }

    public static void drawFaceRect2(Canvas canvas, Rect rect, Paint paint) {
        int i = (rect.bottom - rect.top) / 5;
        int i2 = i / 4;
        int i3 = i * 3;
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        canvas2.drawLine((float) rect.left, (float) rect.top, (float) rect.left, (float) (rect.top + i), paint2);
        canvas2.drawLine((float) rect.left, (float) rect.top, (float) (rect.left + i), (float) rect.top, paint2);
        canvas2.drawLine((float) rect.right, (float) rect.top, (float) rect.right, (float) (rect.top + i), paint2);
        canvas2.drawLine((float) rect.right, (float) rect.top, (float) (rect.right - i), (float) rect.top, paint2);
        canvas2.drawLine((float) rect.right, (float) rect.bottom, (float) rect.right, (float) (rect.bottom - i), paint2);
        canvas2.drawLine((float) rect.right, (float) rect.bottom, (float) (rect.right - i), (float) rect.bottom, paint2);
        canvas2.drawLine((float) rect.left, (float) rect.bottom, (float) rect.left, (float) (rect.bottom - i), paint2);
        canvas2.drawLine((float) rect.left, (float) rect.bottom, (float) (rect.left + i), (float) rect.bottom, paint2);
        rect.left -= i2;
        rect.right += i2;
        rect.bottom += i2;
        rect.top -= i2;
        Canvas canvas3 = canvas;
        Paint paint3 = paint;
        canvas3.drawLine((float) rect.left, (float) rect.top, (float) rect.left, (float) (rect.top + i3), paint3);
        canvas3.drawLine((float) rect.left, (float) rect.top, (float) (rect.left + i3), (float) rect.top, paint3);
        canvas3.drawLine((float) rect.right, (float) rect.top, (float) rect.right, (float) (rect.top + i3), paint3);
        canvas3.drawLine((float) rect.right, (float) rect.top, (float) (rect.right - i3), (float) rect.top, paint3);
        canvas3.drawLine((float) rect.right, (float) rect.bottom, (float) rect.right, (float) (rect.bottom - i3), paint3);
        canvas3.drawLine((float) rect.right, (float) rect.bottom, (float) (rect.right - i3), (float) rect.bottom, paint3);
        canvas3.drawLine((float) rect.left, (float) rect.bottom, (float) rect.left, (float) (rect.bottom - i3), paint3);
        canvas3.drawLine((float) rect.left, (float) rect.bottom, (float) (rect.left + i3), (float) rect.bottom, paint3);
    }
}
