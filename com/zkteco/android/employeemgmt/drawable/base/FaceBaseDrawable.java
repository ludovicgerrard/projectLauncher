package com.zkteco.android.employeemgmt.drawable.base;

import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.SurfaceView;
import java.lang.ref.WeakReference;

public abstract class FaceBaseDrawable extends Drawable {
    protected WeakReference<SurfaceView> surfaceView;

    public abstract void closeInfinite();

    public int getOpacity() {
        return 0;
    }

    public abstract boolean isCanEnrollVL(Rect rect);

    public abstract void openInfinite();

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setSurfaceView(SurfaceView surfaceView2) {
        this.surfaceView = new WeakReference<>(surfaceView2);
    }
}
