package com.zktechnology.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.zktechnology.android.launcher.R;

public class MobileDataSignalView extends View {
    private static final int GAP_PADDING = 2;
    private static final int SIGNAL_COUNT = 5;
    private int mHeight;
    private Paint mPaint;
    private Rect[] mRects;
    private Paint mSecondPaint;
    private int mSlot;
    private final int mSlotCount = 5;
    private Paint mTextPaint;
    private String mType = "x";
    private int mWidth;

    public MobileDataSignalView(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public MobileDataSignalView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public MobileDataSignalView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mPaint = new Paint(1);
        this.mSecondPaint = new Paint(1);
        this.mTextPaint = new Paint(33);
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mSecondPaint.setColor(ContextCompat.getColor(getContext(), R.color.clr_787878));
        this.mSecondPaint.setStyle(Paint.Style.FILL);
        this.mRects = new Rect[5];
        for (int i = 0; i < 5; i++) {
            this.mRects[i] = new Rect();
        }
        this.mTextPaint.setColor(-1);
        this.mTextPaint.setTextSize(16.0f);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i = (this.mWidth - 8) / 5;
        int i2 = 0;
        while (i2 < 5) {
            Rect rect = this.mRects[i2];
            int i3 = (i + 2) * i2;
            int i4 = this.mHeight;
            int i5 = i2 + 1;
            rect.set(i3, i4 - ((i5 * i4) / 5), i3 + i, i4);
            canvas.drawRect(this.mRects[i2], i2 < this.mSlot ? this.mPaint : this.mSecondPaint);
            i2 = i5;
        }
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        canvas.drawText(this.mType, 0.0f, (((((float) this.mHeight) + fontMetrics.descent) / 2.0f) - fontMetrics.bottom) - 2.0f, this.mTextPaint);
    }

    public void setLevelAndType(int i, int i2) {
        this.mSlot = i + 1;
        if (i2 != 20) {
            switch (i2) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                case 16:
                    this.mType = "2G";
                    break;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                case 17:
                    this.mType = "3G";
                    break;
                case 13:
                    this.mType = "4G";
                    break;
                default:
                    this.mType = "x";
                    this.mSlot = 0;
                    break;
            }
        } else {
            this.mType = "5G";
        }
        invalidate();
    }
}
