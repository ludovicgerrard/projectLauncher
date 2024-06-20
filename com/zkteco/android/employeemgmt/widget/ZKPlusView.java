package com.zkteco.android.employeemgmt.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ZKPlusView extends View {
    private int heightMode;
    private int heightSize;
    private Paint paint;
    private int widthMode;
    private int widthSize;

    public ZKPlusView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ZKPlusView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZKPlusView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.widthMode = View.MeasureSpec.getMode(i);
        this.widthSize = View.MeasureSpec.getSize(i);
        this.heightMode = View.MeasureSpec.getMode(i2);
        this.heightSize = View.MeasureSpec.getSize(i2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
        this.paint.setColor(-16777216);
        Canvas canvas2 = new Canvas();
        int i = this.widthSize;
        int i2 = this.heightSize;
        canvas2.drawCircle((float) (i / 2), (float) (i2 / 2), (float) (i > i2 ? i2 : i), this.paint);
        this.paint.setStrokeWidth(3.0f);
        int i3 = this.widthSize;
        canvas2.drawLine((float) (i3 / 2), 0.0f, (float) (i3 / 2), (float) this.heightSize, this.paint);
        int i4 = this.heightSize;
        canvas2.drawLine(0.0f, (float) (i4 / 2), (float) this.widthSize, (float) (i4 / 2), this.paint);
    }
}
