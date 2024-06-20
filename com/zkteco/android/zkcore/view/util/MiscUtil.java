package com.zkteco.android.zkcore.view.util;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

public class MiscUtil {
    public static int measure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        return mode == Integer.MIN_VALUE ? Math.min(i2, size) : i2;
    }

    public static int dipToPx(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + (((float) (f >= 0.0f ? 1 : -1)) * 0.5f));
    }

    public static String getPrecisionFormat(int i) {
        return "%." + i + "f";
    }

    public static <T> T[] reverse(T[] tArr) {
        if (tArr == null) {
            return null;
        }
        int length = tArr.length;
        for (int i = 0; i < length / 2; i++) {
            T t = tArr[i];
            int i2 = (length - i) - 1;
            tArr[i] = tArr[i2];
            tArr[i2] = t;
        }
        return tArr;
    }

    public static float measureTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return Math.abs(fontMetrics.ascent) - fontMetrics.descent;
    }
}
