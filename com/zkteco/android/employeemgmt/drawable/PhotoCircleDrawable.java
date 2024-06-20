package com.zkteco.android.employeemgmt.drawable;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceView;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.employeemgmt.drawable.base.FaceBaseDrawable;

public class PhotoCircleDrawable extends FaceBaseDrawable {
    private Drawable drawBackground;
    private Rect drawFrameRect = new Rect();
    private Drawable drawLine;
    private Drawable drawPhotoFrame;
    private int heightLine;
    private int heightPF;
    /* access modifiers changed from: private */
    public int offsetVerticalLine = 0;
    private Rect surfaceViewRect;
    private int topPF;
    private ValueAnimator valueAnimator;
    private int widthPF;

    public PhotoCircleDrawable(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.drawPhotoFrame = context.getResources().getDrawable(R.mipmap.ic_yuan, (Resources.Theme) null);
            this.drawLine = context.getResources().getDrawable(R.mipmap.line, (Resources.Theme) null);
            this.drawBackground = context.getResources().getDrawable(R.mipmap.background2, (Resources.Theme) null);
        } else {
            this.drawPhotoFrame = context.getResources().getDrawable(R.mipmap.ic_yuan);
            this.drawLine = context.getResources().getDrawable(R.mipmap.line);
            this.drawBackground = context.getResources().getDrawable(R.mipmap.background2);
        }
        this.topPF = context.getResources().getDimensionPixelOffset(R.dimen.zk_staff_face_circle_top);
        this.widthPF = context.getResources().getDimensionPixelSize(R.dimen.zk_staff_face_photo_circle_width);
        this.heightPF = context.getResources().getDimensionPixelSize(R.dimen.zk_staff_face_photo_frame_width);
        this.heightLine = context.getResources().getDimensionPixelSize(R.dimen.zk_staff_face_line_height);
    }

    public void openInfinite() {
        ValueAnimator valueAnimator2 = this.valueAnimator;
        if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
            int i = this.drawFrameRect.top + 20;
            int i2 = (this.drawFrameRect.bottom - 20) - this.heightLine;
            if (i > 0 && i2 > 0) {
                ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{i, i2});
                this.valueAnimator = ofInt;
                ofInt.setDuration(1000);
                this.valueAnimator.setRepeatCount(-1);
                this.valueAnimator.setRepeatMode(1);
                this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int unused = PhotoCircleDrawable.this.offsetVerticalLine = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        PhotoCircleDrawable.this.invalidateSelf();
                    }
                });
                this.valueAnimator.start();
            }
        }
    }

    public void closeInfinite() {
        ValueAnimator valueAnimator2 = this.valueAnimator;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.valueAnimator.cancel();
        }
    }

    private void drawLine(Canvas canvas) {
        if (this.drawLine != null) {
            int i = this.widthPF / 2;
            int centerY = this.drawFrameRect.centerY();
            int centerX = this.drawFrameRect.centerX();
            int i2 = this.offsetVerticalLine - 10;
            double d = (double) centerX;
            int i3 = centerY - i2;
            double pow = d - Math.pow((double) ((i * i) - (Math.abs(i3) * Math.abs(i3))), 0.5d);
            double d2 = d - pow;
            double abs = (Math.abs(d2) * 2.0d) + pow;
            Log.d("lineWidth", (Math.abs(d2) * 2.0d) + "");
            this.drawLine.setBounds((int) pow, i2, (int) abs, this.heightLine + i2);
            this.drawLine.draw(canvas);
        }
    }

    private void drawPhotoFrame(Canvas canvas) {
        if (this.drawPhotoFrame != null) {
            this.drawFrameRect.left = ((this.surfaceViewRect.width() - this.widthPF) / 2) + this.surfaceViewRect.left + 2;
            Rect rect = this.drawFrameRect;
            rect.right = (rect.left + this.widthPF) - 12;
            this.drawFrameRect.top = this.topPF + this.surfaceViewRect.top + 2;
            Rect rect2 = this.drawFrameRect;
            rect2.bottom = (rect2.top + this.heightPF) - 7;
            this.drawPhotoFrame.setBounds(this.drawFrameRect);
            this.drawPhotoFrame.draw(canvas);
        }
    }

    private void drawBG(Canvas canvas) {
        Drawable drawable = this.drawBackground;
        if (drawable != null) {
            drawable.setBounds(this.surfaceViewRect);
            this.drawBackground.draw(canvas);
        }
    }

    public void draw(Canvas canvas) {
        if (this.surfaceView != null && this.surfaceView.get() != null) {
            Rect surfaceFrame = ((SurfaceView) this.surfaceView.get()).getHolder().getSurfaceFrame();
            this.surfaceViewRect = surfaceFrame;
            if (surfaceFrame != null) {
                drawBG(canvas);
                drawPhotoFrame(canvas);
                drawLine(canvas);
                openInfinite();
            }
        }
    }

    public boolean isCanEnrollVL(Rect rect) {
        if (this.drawFrameRect.width() == 0 || this.drawFrameRect.height() == 0 || rect == null || Math.pow(Math.pow((double) Math.abs(this.drawFrameRect.centerX() - rect.centerX()), 2.0d) + Math.pow((double) Math.abs(this.drawFrameRect.centerY() - rect.centerY()), 2.0d), 0.5d) > 60.0d) {
            return false;
        }
        return true;
    }
}
