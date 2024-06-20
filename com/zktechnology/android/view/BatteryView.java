package com.zktechnology.android.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import com.zktechnology.android.launcher.R;
import java.util.Locale;

public class BatteryView extends View {
    private static final int BATTERY_LINE_EXTREMELY_LOW = 10;
    private static final int BATTERY_LINE_LOW = 20;
    private Paint mHeadPaint;
    private RectF mHeadRect;
    private int mHeight;
    private Paint mInnerPaint;
    private RectF mInnerRect;
    /* access modifiers changed from: private */
    public int mLevel;
    private Paint mOutPaint;
    private RectF mOutRect;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int unused = BatteryView.this.mLevel = intent.getIntExtra("level", 0);
            int unused2 = BatteryView.this.mScale = intent.getIntExtra("scale", 0);
            int unused3 = BatteryView.this.mStatus = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, 1);
            BatteryView.this.invalidate();
            String stringExtra = intent.getStringExtra("technology");
            int intExtra = intent.getIntExtra("plugged", 0);
            int intExtra2 = intent.getIntExtra("health", 1);
            int intExtra3 = intent.getIntExtra("voltage", 0);
            Log.e("TAG", "onReceive: 电池变化: \nLevel=" + BatteryView.this.mLevel + "\nScale=" + BatteryView.this.mScale + "\ntechnology=" + stringExtra + "\nstatus=" + BatteryView.this.mStatus + "\nplugged=" + intExtra + "\nhealth=" + intExtra2 + "\nvoltage=" + intExtra3 + "\ntemperature=" + intent.getIntExtra("temperature", 0));
        }
    };
    /* access modifiers changed from: private */
    public int mScale;
    /* access modifiers changed from: private */
    public int mStatus;
    private int mWidth;

    public BatteryView(Context context) {
        super(context);
        init();
    }

    public BatteryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BatteryView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mOutPaint = new Paint();
        this.mHeadPaint = new Paint();
        this.mInnerPaint = new Paint();
        this.mOutPaint.setAntiAlias(true);
        this.mHeadPaint.setAntiAlias(true);
        this.mInnerPaint.setAntiAlias(true);
        this.mOutPaint.setStyle(Paint.Style.STROKE);
        this.mHeadPaint.setStyle(Paint.Style.FILL);
        this.mInnerPaint.setStyle(Paint.Style.FILL);
        this.mOutPaint.setStrokeWidth(2.0f);
        this.mOutPaint.setColor(-1);
        this.mHeadPaint.setColor(-1);
        this.mHeadPaint.setTextSize(20.0f);
        this.mHeadPaint.setTextAlign(Paint.Align.LEFT);
        this.mOutRect = new RectF();
        this.mInnerRect = new RectF();
        this.mHeadRect = new RectF();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        String str;
        super.onDraw(canvas);
        int i = this.mScale;
        if (i != 0) {
            int i2 = (this.mLevel * 100) / i;
            this.mOutRect.set(0.0f, 0.0f, (float) (this.mWidth - 10), (float) this.mHeight);
            int i3 = this.mHeight;
            float f = ((float) i3) / 3.0f;
            RectF rectF = this.mHeadRect;
            int i4 = this.mWidth;
            rectF.set((float) (i4 - 5), (((float) i3) - f) / 2.0f, (float) i4, (((float) i3) + f) / 2.0f);
            this.mInnerRect.set(5.0f, 5.0f, (((float) ((this.mWidth - 15) * this.mLevel)) * 1.0f) / ((float) this.mScale), (float) (this.mHeight - 5));
            this.mInnerPaint.setColor(getColor(getContext(), i2));
            canvas.drawRoundRect(this.mOutRect, 10.0f, 10.0f, this.mOutPaint);
            canvas.drawRoundRect(this.mHeadRect, 8.0f, 8.0f, this.mHeadPaint);
            canvas.drawRoundRect(this.mInnerRect, 4.0f, 4.0f, this.mInnerPaint);
            if (this.mStatus == 2) {
                str = String.format(Locale.US, "%d⚡", new Object[]{Integer.valueOf(i2)});
            } else {
                str = String.valueOf(i2);
            }
            float measureText = this.mHeadPaint.measureText(str);
            Paint.FontMetrics fontMetrics = this.mHeadPaint.getFontMetrics();
            canvas.drawText(str, (((float) (this.mWidth - 10)) - measureText) / 2.0f, (((((float) this.mHeight) + fontMetrics.descent) - fontMetrics.ascent) / 2.0f) - fontMetrics.bottom, this.mHeadPaint);
        }
    }

    private static int getColor(Context context, int i) {
        if (i > 20) {
            return ContextCompat.getColor(context, R.color.clr_9B9B9F);
        }
        return i > 10 ? ContextCompat.getColor(context, 17170456) : SupportMenu.CATEGORY_MASK;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getContext().registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        getContext().unregisterReceiver(this.mReceiver);
        super.onDetachedFromWindow();
    }
}
