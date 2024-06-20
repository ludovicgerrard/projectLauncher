package com.zkteco.android.employeemgmt.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.zktechnology.android.launcher.R;
import java.util.HashMap;

public class ZKProgressBar extends View {
    private int bgColor;
    private Paint bgPaint;
    private int distance;
    private int nodeRadius;
    private int nowStage;
    private int padding;
    private int progressColor1;
    private int progressColor2;
    private Paint progressPaint1;
    private Paint progressPaint2;
    private int scaleClr;
    private Paint scalePaint;
    private int scaleSize;
    private int stage;
    private HashMap<Integer, Integer> statusProMap;

    public ZKProgressBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public ZKProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZKProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.nowStage = 0;
        this.statusProMap = new HashMap<>();
        init(context, attributeSet);
        initData();
    }

    private void initData() {
        if (this.statusProMap.size() == 0) {
            this.statusProMap.put(0, 0);
            this.statusProMap.put(1, 0);
            this.statusProMap.put(2, 0);
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        initAttrs(context, attributeSet);
        initPaths();
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StaffNodeProgressBarCus);
        this.stage = obtainStyledAttributes.getInteger(9, 3);
        this.nowStage = obtainStyledAttributes.getInteger(1, 0);
        this.bgColor = obtainStyledAttributes.getColor(0, -12627531);
        this.progressColor1 = obtainStyledAttributes.getColor(3, -16711936);
        this.progressColor2 = obtainStyledAttributes.getColor(4, -16711936);
        this.padding = obtainStyledAttributes.getDimensionPixelSize(2, 2);
        this.nodeRadius = obtainStyledAttributes.getDimensionPixelSize(5, 5);
        this.scaleSize = obtainStyledAttributes.getDimensionPixelSize(8, 24);
        this.scaleClr = obtainStyledAttributes.getColor(6, -1);
        this.distance = obtainStyledAttributes.getDimensionPixelSize(7, 15);
        obtainStyledAttributes.recycle();
    }

    private void initPaths() {
        Paint paint = new Paint();
        this.progressPaint1 = paint;
        paint.setColor(this.progressColor1);
        this.progressPaint1.setStyle(Paint.Style.FILL);
        this.progressPaint1.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.progressPaint2 = paint2;
        paint2.setColor(this.progressColor2);
        this.progressPaint2.setStyle(Paint.Style.FILL);
        this.progressPaint2.setAntiAlias(true);
        Paint paint3 = new Paint();
        this.bgPaint = paint3;
        paint3.setColor(this.bgColor);
        this.bgPaint.setStyle(Paint.Style.FILL);
        this.bgPaint.setAntiAlias(true);
        Paint paint4 = new Paint();
        paint4.setColor(getResources().getColor(R.color.clr_3B3B49));
        paint4.setStyle(Paint.Style.FILL);
        paint4.setAntiAlias(true);
        Paint paint5 = new Paint();
        this.scalePaint = paint5;
        paint5.setTextSize((float) this.scaleSize);
        this.scalePaint.setColor(this.scaleClr);
        this.scalePaint.setStyle(Paint.Style.FILL);
        this.scalePaint.setAntiAlias(true);
        this.scalePaint.setTextAlign(Paint.Align.CENTER);
        Paint paint6 = new Paint();
        paint6.setColor(getResources().getColor(R.color.clr_F78254));
        paint6.setStyle(Paint.Style.FILL);
        paint6.setAntiAlias(true);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int i = this.stage;
        int i2 = this.scaleSize;
        int i3 = this.padding;
        int i4 = ((height - (i3 * 2)) - i2) - this.distance;
        if (i4 % 2 != 0) {
            i4--;
        }
        int i5 = (width - (i3 * 2)) - i4;
        int i6 = i5 / 3;
        int i7 = i3 + i4 + i6;
        int i8 = i4 / 2;
        int i9 = i3 + i4 + (i6 * 2);
        int i10 = width - i3;
        int i11 = i3 + i4;
        if (i2 % 2 != 0) {
            i2--;
        }
        if (this.statusProMap.containsKey(2) && this.statusProMap.get(2).intValue() != 0 && this.statusProMap.get(2).intValue() == 2) {
            RectF rectF = new RectF((float) i3, (float) i3, (float) i10, (float) i11);
            float f = ((float) i4) / 2.0f;
            canvas.drawRoundRect(rectF, f, f, this.progressPaint2);
        }
        if (this.statusProMap.containsKey(1) && this.statusProMap.get(1).intValue() != 0) {
            RectF rectF2 = new RectF((float) i3, (float) i3, (float) i9, (float) i11);
            if (this.statusProMap.get(1).intValue() == 1) {
                float f2 = ((float) i4) / 2.0f;
                canvas.drawRoundRect(rectF2, f2, f2, this.progressPaint1);
            } else if (this.statusProMap.get(1).intValue() == 2) {
                float f3 = ((float) i4) / 2.0f;
                canvas.drawRoundRect(rectF2, f3, f3, this.progressPaint2);
            }
        }
        int i12 = 0;
        if (this.statusProMap.containsKey(0) && this.statusProMap.get(0).intValue() != 0) {
            RectF rectF3 = new RectF((float) i3, (float) i3, (float) i7, (float) i11);
            if (this.statusProMap.get(0).intValue() == 1) {
                float f4 = ((float) i4) / 2.0f;
                canvas.drawRoundRect(rectF3, f4, f4, this.progressPaint1);
            } else if (this.statusProMap.get(0).intValue() == 2) {
                float f5 = ((float) i4) / 2.0f;
                canvas.drawRoundRect(rectF3, f5, f5, this.progressPaint2);
            }
        }
        if (2 <= this.stage) {
            for (int i13 = 0; i13 <= 2; i13++) {
                if (i13 > this.nowStage) {
                    float f6 = ((float) i5) * ((((float) i13) * 1.0f) / ((float) this.stage));
                    int i14 = this.padding;
                    float f7 = ((float) i4) / 2.0f;
                    canvas.drawCircle(((float) i14) + f7 + f6, ((float) i14) + f7, (float) this.nodeRadius, this.progressPaint1);
                }
            }
        }
        while (true) {
            int i15 = this.stage;
            if (i12 < i15) {
                float f8 = (((float) i12) * 1.0f) / ((float) i15);
                float f9 = (float) i5;
                float f10 = (f8 * f9) + (f9 / 6.0f);
                i12++;
                String valueOf = String.valueOf(i12);
                int i16 = this.padding;
                canvas.drawText(valueOf, ((float) i16) + (((float) i4) / 2.0f) + f10, ((float) (this.distance + i16 + i4)) + (((float) i2) / 2.0f), this.scalePaint);
            } else {
                return;
            }
        }
    }

    private void drawProgress(Bitmap bitmap, Canvas canvas, Paint paint, int i, int i2, int i3, int i4) {
        canvas.save();
        canvas.drawBitmap(bitmap, (Rect) null, new RectF((float) i, (float) i2, (float) i3, (float) i4), paint);
        canvas.restore();
    }

    private void drawBackground(Canvas canvas) {
        int width = getWidth();
        int height = ((getHeight() - (this.padding * 2)) - this.scaleSize) - this.distance;
        if (height % 2 != 0) {
            height--;
        }
        canvas.save();
        int i = this.padding;
        float f = ((float) height) / 2.0f;
        canvas.drawCircle(((float) i) + f, ((float) i) + f, f, this.bgPaint);
        int i2 = this.padding;
        canvas.drawCircle(((float) (width - i2)) - f, ((float) i2) + f, f, this.bgPaint);
        int i3 = this.padding;
        canvas.drawRect(new RectF(((float) i3) + f, (float) i3, ((float) (width - i3)) - f, (float) (height + i3)), this.bgPaint);
        canvas.restore();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public void setStatusProNew() {
        this.statusProMap.remove(0);
        this.statusProMap.remove(1);
        this.statusProMap.remove(2);
        this.statusProMap.put(0, 0);
        this.statusProMap.put(1, 0);
        this.statusProMap.put(2, 0);
        this.nowStage = 0;
    }

    public void setStatusStage1(int i) {
        this.statusProMap.remove(0);
        this.statusProMap.put(0, Integer.valueOf(i));
        if (i >= 1) {
            this.nowStage = 1;
        }
    }

    public void setStatusStage2(int i) {
        this.statusProMap.remove(1);
        this.statusProMap.put(1, Integer.valueOf(i));
        if (i >= 1) {
            this.nowStage = 2;
        }
    }

    public void setStatusStage3(int i) {
        this.statusProMap.remove(2);
        this.statusProMap.put(2, Integer.valueOf(i));
    }
}
