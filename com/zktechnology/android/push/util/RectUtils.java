package com.zktechnology.android.push.util;

import android.graphics.Rect;
import android.graphics.RectF;

public class RectUtils {
    public static RectF rectToRectF(Rect rect) {
        return new RectF((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
    }

    public static Rect rectFToRect(RectF rectF) {
        return new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
    }
}
