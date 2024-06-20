package com.zkteco.android.zkcore.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.zkteco.android.zkcore.R;
import com.zkteco.android.zkcore.view.util.Constant;
import com.zkteco.android.zkcore.view.util.MiscUtil;

public class ZKCustomProgress extends View {
    private static final String TAG = "ZKCustomProgress";
    private boolean antiAlias;
    private long mAnimTime;
    private ValueAnimator mAnimator;
    private Paint mArcPaint;
    private float mArcWidth;
    private int mBgArcColor;
    private Paint mBitCirclePaint;
    private Paint mBitPaint;
    private Bitmap mBitmap;
    private Bitmap mBitmapCircle;
    private Point mCenterPoint;
    private Paint mColorLinePaint;
    private int mDefaultSize;
    private int mDialColor;
    private float mDialHeight;
    private int mDialIntervalDegree;
    private Paint mDialPaint;
    private float mDialWidth;
    private int mGradientColors = -1;
    private RectF mInerRect;
    private int mLineColor;
    private Paint mLinePaint;
    private float mLineWidth;
    /* access modifiers changed from: private */
    public float mMaxValue;
    /* access modifiers changed from: private */
    public float mPercent;
    private Paint mPointCirclePaint;
    private Paint mPointPaint;
    private String mPrecisionFormat;
    private float mRadius;
    private RectF mRectF;
    private Resources mResources;
    private float mStartAngle;
    private float mSweepAngle;
    /* access modifiers changed from: private */
    public float mValue;
    private int mValueColor;
    private float mValueOffset;
    private Paint mValuePaint;
    private float mValueSize;

    public ZKCustomProgress(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public ZKCustomProgress(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public ZKCustomProgress(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mResources = getResources();
        this.mDefaultSize = MiscUtil.dipToPx(context, 150.0f);
        this.mRectF = new RectF();
        this.mCenterPoint = new Point();
        initConfig(context, attributeSet);
        initPaint();
        initBitmap();
        setValue(this.mValue);
    }

    private void initBitmap() {
        this.mBitmap = ((BitmapDrawable) this.mResources.getDrawable(R.mipmap.ic_scale)).getBitmap();
        this.mBitmapCircle = ((BitmapDrawable) this.mResources.getDrawable(R.mipmap.circle)).getBitmap();
    }

    private void initPoint() {
        Paint paint = new Paint();
        this.mPointPaint = paint;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPointPaint.setAntiAlias(true);
    }

    private void initConfig(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ZKCustomProgress);
        this.antiAlias = obtainStyledAttributes.getBoolean(R.styleable.ZKCustomProgress_antiAlias, true);
        this.mMaxValue = obtainStyledAttributes.getFloat(R.styleable.ZKCustomProgress_maxValue, 100.0f);
        this.mValue = obtainStyledAttributes.getFloat(R.styleable.ZKCustomProgress_value, 50.0f);
        this.mValueSize = obtainStyledAttributes.getDimension(R.styleable.ZKCustomProgress_valueSize, 15.0f);
        this.mValueColor = obtainStyledAttributes.getColor(R.styleable.ZKCustomProgress_valueColor, -16777216);
        this.mDialIntervalDegree = obtainStyledAttributes.getInt(R.styleable.ZKCustomProgress_dialIntervalDegree, 10);
        this.mPrecisionFormat = MiscUtil.getPrecisionFormat(obtainStyledAttributes.getInt(R.styleable.ZKCustomProgress_precision, 0));
        this.mArcWidth = obtainStyledAttributes.getDimension(R.styleable.ZKCustomProgress_arcWidth, 15.0f);
        this.mStartAngle = obtainStyledAttributes.getFloat(R.styleable.ZKCustomProgress_startAngle, 270.0f);
        this.mSweepAngle = obtainStyledAttributes.getFloat(R.styleable.ZKCustomProgress_sweepAngle, 360.0f);
        this.mAnimTime = (long) obtainStyledAttributes.getInt(R.styleable.ZKCustomProgress_animTime, 1000);
        this.mBgArcColor = obtainStyledAttributes.getColor(R.styleable.ZKCustomProgress_bgArcColor, -7829368);
        this.mDialWidth = obtainStyledAttributes.getDimension(R.styleable.ZKCustomProgress_dialWidth, 3.0f);
        this.mDialHeight = obtainStyledAttributes.getDimension(R.styleable.ZKCustomProgress_dialHeight, 30.0f);
        this.mDialColor = obtainStyledAttributes.getColor(R.styleable.ZKCustomProgress_dialColor, -1);
        this.mLineWidth = obtainStyledAttributes.getDimension(R.styleable.ZKCustomProgress_dialWidth, 3.0f);
        this.mLineColor = obtainStyledAttributes.getColor(R.styleable.ZKCustomProgress_dialColor, -1);
        obtainStyledAttributes.getResourceId(R.styleable.ZKCustomProgress_arcColors, 0);
        obtainStyledAttributes.recycle();
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mValuePaint = paint;
        paint.setAntiAlias(this.antiAlias);
        this.mValuePaint.setTextSize(this.mValueSize);
        this.mValuePaint.setColor(this.mValueColor);
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        Paint paint2 = new Paint();
        this.mArcPaint = paint2;
        paint2.setAntiAlias(this.antiAlias);
        this.mArcPaint.setStyle(Paint.Style.STROKE);
        this.mArcPaint.setStrokeWidth(this.mArcWidth);
        this.mArcPaint.setStrokeCap(Paint.Cap.BUTT);
        Paint paint3 = new Paint();
        this.mDialPaint = paint3;
        paint3.setAntiAlias(this.antiAlias);
        this.mDialPaint.setColor(this.mDialColor);
        this.mDialPaint.setStrokeWidth(this.mDialWidth);
        Paint paint4 = new Paint();
        this.mLinePaint = paint4;
        paint4.setAntiAlias(this.antiAlias);
        this.mLinePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setColor(this.mLineColor);
        this.mLinePaint.setStrokeWidth(this.mLineWidth);
        Paint paint5 = new Paint();
        this.mPointCirclePaint = paint5;
        paint5.setAntiAlias(this.antiAlias);
        this.mPointCirclePaint.setColor(this.mDialColor);
        this.mPointCirclePaint.setStrokeWidth(this.mDialWidth);
        this.mPointCirclePaint.setStyle(Paint.Style.STROKE);
        this.mPointCirclePaint.setStrokeCap(Paint.Cap.BUTT);
        Paint paint6 = new Paint();
        this.mColorLinePaint = paint6;
        paint6.setAntiAlias(this.antiAlias);
        this.mColorLinePaint.setColor(this.mDialColor);
        this.mColorLinePaint.setStrokeWidth(this.mDialWidth);
        this.mColorLinePaint.setStyle(Paint.Style.STROKE);
        this.mColorLinePaint.setStrokeCap(Paint.Cap.BUTT);
        Paint paint7 = new Paint();
        this.mPointPaint = paint7;
        paint7.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPointPaint.setAntiAlias(true);
        Paint paint8 = new Paint();
        this.mBitCirclePaint = paint8;
        paint8.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mBitCirclePaint.setAntiAlias(true);
        initPoint();
    }

    public void setValue(float f) {
        float f2 = this.mMaxValue;
        if (f > f2) {
            f = f2;
        }
        startAnimator(this.mPercent, f / f2, this.mAnimTime);
    }

    private void startAnimator(float f, float f2, long j) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f, f2});
        this.mAnimator = ofFloat;
        ofFloat.setDuration(j);
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float unused = ZKCustomProgress.this.mPercent = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ZKCustomProgress zKCustomProgress = ZKCustomProgress.this;
                float unused2 = zKCustomProgress.mValue = zKCustomProgress.mPercent * ZKCustomProgress.this.mMaxValue;
                ZKCustomProgress.this.invalidate();
            }
        });
        this.mAnimator.start();
    }

    private void updateArcPaint() {
        int i = this.mGradientColors;
        this.mArcPaint.setShader(new SweepGradient((float) this.mCenterPoint.x, (float) this.mCenterPoint.y, i, i));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(MiscUtil.measure(i, this.mDefaultSize), MiscUtil.measure(i2, this.mDefaultSize));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        String str = TAG;
        Log.d(str, "onSizeChanged: w = " + i + "; h = " + i2 + "; oldw = " + i3 + "; oldh = " + i4);
        this.mRadius = (float) (Math.min(((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - (((int) this.mArcWidth) * 2), ((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - (((int) this.mArcWidth) * 2)) / 2);
        this.mCenterPoint.x = getMeasuredWidth() / 2;
        this.mCenterPoint.y = getMeasuredHeight() / 2;
        this.mRectF.left = (((float) this.mCenterPoint.x) - this.mRadius) - (this.mArcWidth / 2.0f);
        this.mRectF.top = (((float) this.mCenterPoint.y) - this.mRadius) - (this.mArcWidth / 2.0f);
        this.mRectF.right = ((float) this.mCenterPoint.x) + this.mRadius + (this.mArcWidth / 2.0f);
        this.mRectF.bottom = ((float) this.mCenterPoint.y) + this.mRadius + (this.mArcWidth / 2.0f);
        this.mInerRect = new RectF(this.mRectF.left + ((this.mArcWidth * 3.0f) / 2.0f), this.mRectF.top + ((this.mArcWidth * 3.0f) / 2.0f), this.mRectF.right - ((this.mArcWidth * 3.0f) / 2.0f), this.mRectF.bottom - ((this.mArcWidth * 3.0f) / 2.0f));
        this.mValueOffset = ((float) this.mCenterPoint.y) + getBaselineOffsetFromY(this.mValuePaint);
        updateArcPaint();
        Log.d(str, "onMeasure: 控件大小 = (" + getMeasuredWidth() + ", " + getMeasuredHeight() + ");圆心坐标 = " + this.mCenterPoint.toString() + ";圆半径 = " + this.mRadius + ";圆的外接矩形 = " + this.mRectF.toString());
    }

    private float getBaselineOffsetFromY(Paint paint) {
        return MiscUtil.measureTextHeight(paint) / 2.0f;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDial(canvas);
        drawArc(canvas);
        drawText(canvas);
        drawBitmap(canvas);
        drawPointer(canvas);
        drawCircleBitmap(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.restore();
    }

    private void drawCircleBitmap(Canvas canvas) {
        canvas.save();
        canvas.scale((float) ((((double) this.mBitmap.getWidth()) / 9.0d) / ((double) this.mBitmapCircle.getWidth())), (float) ((((double) this.mBitmap.getHeight()) / 9.0d) / ((double) this.mBitmapCircle.getHeight())), (float) (((double) this.mBitmap.getWidth()) / 2.0d), (float) (((double) this.mBitmap.getHeight()) / 2.0d));
        canvas.drawBitmap(this.mBitmapCircle, (float) (((double) (this.mBitmap.getWidth() - this.mBitmapCircle.getWidth())) / 2.0d), (float) (((double) (this.mBitmap.getHeight() - this.mBitmapCircle.getHeight())) / 2.0d), (Paint) null);
        canvas.restore();
    }

    private void drawArc(Canvas canvas) {
        float f = this.mSweepAngle * this.mPercent;
        canvas.save();
        canvas.rotate(this.mStartAngle, (float) this.mCenterPoint.x, (float) this.mCenterPoint.y);
        canvas.drawArc(this.mRectF, 0.0f, f, false, this.mArcPaint);
        canvas.restore();
    }

    private void drawDial(Canvas canvas) {
        canvas.save();
        canvas.rotate(this.mStartAngle, (float) this.mCenterPoint.x, (float) this.mCenterPoint.y);
        for (int i = 0; i <= 4; i++) {
            canvas.rotate((float) this.mDialIntervalDegree, (float) this.mCenterPoint.x, (float) this.mCenterPoint.y);
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.drawText(String.format(this.mPrecisionFormat, new Object[]{Float.valueOf(this.mValue)}) + "%", this.mValueOffset, ((float) this.mCenterPoint.y) + ((this.mRadius * 99.0f) / 100.0f), this.mValuePaint);
    }

    private void drawPointer(Canvas canvas) {
        canvas.drawCircle((float) this.mCenterPoint.x, (float) this.mCenterPoint.y, 15.0f, this.mPointCirclePaint);
        int i = ((int) (this.mValue * 2.4f)) + Constant.DEFAULT_SIZE;
        this.mPointPaint.setStyle(Paint.Style.FILL);
        this.mPointPaint.setColor(getResources().getColor(R.color.clr_7AC143));
        Path path = new Path();
        path.reset();
        float[] coordinatePoint = getCoordinatePoint(5, (float) (i + 90));
        path.moveTo(coordinatePoint[0], coordinatePoint[1]);
        float[] coordinatePoint2 = getCoordinatePoint(5, (float) (i - 90));
        path.lineTo(coordinatePoint2[0], coordinatePoint2[1]);
        float[] coordinatePoint3 = getCoordinatePoint(((int) this.mRadius) + (((int) this.mArcWidth) / 2), (float) i);
        path.lineTo(coordinatePoint3[0], coordinatePoint3[1]);
        path.close();
        canvas.drawPath(path, this.mPointPaint);
        canvas.drawCircle((coordinatePoint[0] + coordinatePoint2[0]) / 2.0f, (coordinatePoint[1] + coordinatePoint2[1]) / 2.0f, 5.0f, this.mPointPaint);
    }

    public float[] getCoordinatePoint(int i, float f) {
        float[] fArr = new float[2];
        double radians = Math.toRadians((double) f);
        if (f < 90.0f) {
            double d = (double) i;
            fArr[0] = (float) (((double) this.mCenterPoint.x) + (Math.cos(radians) * d));
            fArr[1] = (float) (((double) this.mCenterPoint.y) + (Math.sin(radians) * d));
        } else {
            int i2 = (f > 90.0f ? 1 : (f == 90.0f ? 0 : -1));
            if (i2 == 0) {
                fArr[0] = (float) this.mCenterPoint.x;
                fArr[1] = (float) (this.mCenterPoint.y + i);
            } else if (i2 <= 0 || f >= 180.0f) {
                int i3 = (f > 180.0f ? 1 : (f == 180.0f ? 0 : -1));
                if (i3 == 0) {
                    fArr[0] = (float) (this.mCenterPoint.x - i);
                    fArr[1] = (float) this.mCenterPoint.y;
                } else if (i3 > 0 && f < 270.0f) {
                    double d2 = (((double) (f - 180.0f)) * 3.141592653589793d) / 180.0d;
                    double d3 = (double) i;
                    fArr[0] = (float) (((double) this.mCenterPoint.x) - (Math.cos(d2) * d3));
                    fArr[1] = (float) (((double) this.mCenterPoint.y) - (Math.sin(d2) * d3));
                } else if (f == 270.0f) {
                    fArr[0] = (float) this.mCenterPoint.x;
                    fArr[1] = (float) (this.mCenterPoint.y - i);
                } else {
                    double d4 = (((double) (360.0f - f)) * 3.141592653589793d) / 180.0d;
                    double d5 = (double) i;
                    fArr[0] = (float) (((double) this.mCenterPoint.x) + (Math.cos(d4) * d5));
                    fArr[1] = (float) (((double) this.mCenterPoint.y) - (Math.sin(d4) * d5));
                }
            } else {
                double d6 = (((double) (180.0f - f)) * 3.141592653589793d) / 180.0d;
                double d7 = (double) i;
                fArr[0] = (float) (((double) this.mCenterPoint.x) - (Math.cos(d6) * d7));
                fArr[1] = (float) (((double) this.mCenterPoint.y) + (Math.sin(d6) * d7));
            }
        }
        return fArr;
    }
}
