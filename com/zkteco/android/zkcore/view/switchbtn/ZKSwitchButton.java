package com.zkteco.android.zkcore.view.switchbtn;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import androidx.core.content.ContextCompat;
import com.zkteco.android.zkcore.R;

public class ZKSwitchButton extends CompoundButton {
    private static int[] CHECKED_PRESSED_STATE = {16842912, 16842910, 16842919};
    public static final int DEFAULT_ANIMATION_DURATION = 250;
    public static final float DEFAULT_BACK_MEASURE_RATIO = 1.8f;
    public static final int DEFAULT_TEXT_MARGIN_DP = 2;
    public static final int DEFAULT_THUMB_MARGIN_DP = 2;
    public static final int DEFAULT_THUMB_SIZE_DP = 20;
    public static final int DEFAULT_TINT_COLOR = 3309506;
    private static int[] UNCHECKED_PRESSED_STATE = {-16842912, 16842910, 16842919};
    private long mAnimationDuration;
    private boolean mAutoAdjustTextPosition = true;
    private ColorStateList mBackColor;
    private Drawable mBackDrawable;
    private float mBackMeasureRatio;
    private float mBackRadius;
    private RectF mBackRectF;
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private int mClickTimeout;
    private int mCurrBackColor;
    private int mCurrThumbColor;
    private Drawable mCurrentBackDrawable;
    private boolean mDrawDebugRect = false;
    private boolean mFadeBack;
    private boolean mIsBackUseDrawable;
    private boolean mIsThumbUseDrawable;
    private float mLastX;
    private int mNextBackColor;
    private Drawable mNextBackDrawable;
    private Layout mOffLayout;
    private int mOffTextColor;
    private Layout mOnLayout;
    private int mOnTextColor;
    private Paint mPaint;
    private RectF mPresentThumbRectF;
    private float mProcess;
    private ObjectAnimator mProcessAnimator;
    private Paint mRectPaint;
    private boolean mRestoring = false;
    private RectF mSafeRectF;
    private float mStartX;
    private float mStartY;
    private float mTextHeight;
    private float mTextMarginH;
    private CharSequence mTextOff;
    private RectF mTextOffRectF;
    private CharSequence mTextOn;
    private RectF mTextOnRectF;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private ColorStateList mThumbColor;
    private Drawable mThumbDrawable;
    private RectF mThumbMargin;
    private float mThumbRadius;
    private RectF mThumbRectF;
    private PointF mThumbSizeF;
    private int mTintColor;
    private int mTouchSlop;

    public ZKSwitchButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public ZKSwitchButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public ZKSwitchButton(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    private void init(AttributeSet attributeSet) {
        int i;
        float f;
        float f2;
        float f3;
        float f4;
        boolean z;
        float f5;
        float f6;
        float f7;
        ColorStateList colorStateList;
        Drawable drawable;
        Drawable drawable2;
        float f8;
        float f9;
        int i2;
        String str;
        float f10;
        ColorStateList colorStateList2;
        boolean z2;
        String str2;
        float f11;
        TypedArray typedArray;
        float f12;
        boolean z3;
        AttributeSet attributeSet2 = attributeSet;
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.mRectPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mRectPaint.setStrokeWidth(getResources().getDisplayMetrics().density);
        this.mTextPaint = getPaint();
        this.mThumbRectF = new RectF();
        this.mBackRectF = new RectF();
        this.mSafeRectF = new RectF();
        this.mThumbSizeF = new PointF();
        this.mThumbMargin = new RectF();
        this.mTextOnRectF = new RectF();
        this.mTextOffRectF = new RectF();
        ObjectAnimator duration = ObjectAnimator.ofFloat(this, "process", new float[]{0.0f, 0.0f}).setDuration(250);
        this.mProcessAnimator = duration;
        duration.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mPresentThumbRectF = new RectF();
        float f13 = getResources().getDisplayMetrics().density;
        float f14 = f13 * 2.0f;
        float f15 = f13 * 20.0f;
        float f16 = f15 / 2.0f;
        TypedArray obtainStyledAttributes = attributeSet2 == null ? null : getContext().obtainStyledAttributes(attributeSet2, R.styleable.SwitchButton);
        if (obtainStyledAttributes != null) {
            Drawable drawable3 = obtainStyledAttributes.getDrawable(R.styleable.SwitchButton_kswThumbDrawable);
            ColorStateList colorStateList3 = obtainStyledAttributes.getColorStateList(R.styleable.SwitchButton_kswThumbColor);
            float dimension = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswThumbMargin, f14);
            float dimension2 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswThumbMarginLeft, dimension);
            float dimension3 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswThumbMarginRight, dimension);
            float dimension4 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswThumbMarginTop, dimension);
            float dimension5 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswThumbMarginBottom, dimension);
            f5 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswThumbWidth, f15);
            float dimension6 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswThumbHeight, f15);
            float dimension7 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswThumbRadius, Math.min(f5, dimension6) / 2.0f);
            float dimension8 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswBackRadius, dimension7 + f14);
            Drawable drawable4 = obtainStyledAttributes.getDrawable(R.styleable.SwitchButton_kswBackDrawable);
            colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.SwitchButton_kswBackColor);
            float f17 = dimension4;
            float f18 = dimension7;
            float f19 = obtainStyledAttributes.getFloat(R.styleable.SwitchButton_kswBackMeasureRatio, 1.8f);
            int integer = obtainStyledAttributes.getInteger(R.styleable.SwitchButton_kswAnimationDuration, 250);
            boolean z4 = obtainStyledAttributes.getBoolean(R.styleable.SwitchButton_kswFadeBack, true);
            int color = obtainStyledAttributes.getColor(R.styleable.SwitchButton_kswTintColor, 0);
            String string = obtainStyledAttributes.getString(R.styleable.SwitchButton_kswTextOn);
            int i3 = color;
            String string2 = obtainStyledAttributes.getString(R.styleable.SwitchButton_kswTextOff);
            float dimension9 = obtainStyledAttributes.getDimension(R.styleable.SwitchButton_kswTextMarginH, Math.max(f14, dimension8 / 2.0f));
            String str3 = string;
            boolean z5 = obtainStyledAttributes.getBoolean(R.styleable.SwitchButton_kswAutoAdjustTextPosition, true);
            obtainStyledAttributes.recycle();
            z2 = z5;
            f8 = dimension9;
            str = str3;
            i = integer;
            z = z4;
            str2 = string2;
            f4 = dimension8;
            f10 = dimension3;
            drawable = drawable4;
            f7 = dimension2;
            f9 = dimension6;
            colorStateList2 = colorStateList3;
            f6 = dimension5;
            drawable2 = drawable3;
            i2 = i3;
            float f20 = f17;
            f2 = f19;
            f3 = f18;
            f = f20;
        } else {
            i = 250;
            f2 = 1.8f;
            f9 = f15;
            f5 = f9;
            f8 = f14;
            f4 = f16;
            f3 = f4;
            str2 = null;
            z2 = true;
            colorStateList2 = null;
            f10 = 0.0f;
            str = null;
            i2 = 0;
            drawable2 = null;
            drawable = null;
            colorStateList = null;
            f7 = 0.0f;
            f6 = 0.0f;
            z = true;
            f = 0.0f;
        }
        float f21 = f10;
        if (attributeSet2 == null) {
            f11 = f7;
            typedArray = null;
        } else {
            f11 = f7;
            typedArray = getContext().obtainStyledAttributes(attributeSet2, new int[]{16842970, 16842981});
        }
        if (typedArray != null) {
            f12 = f6;
            boolean z6 = typedArray.getBoolean(0, true);
            boolean z7 = typedArray.getBoolean(1, z6);
            setFocusable(z6);
            setClickable(z7);
            typedArray.recycle();
        } else {
            f12 = f6;
            setFocusable(true);
            setClickable(true);
        }
        this.mTextOn = str;
        this.mTextOff = str2;
        this.mTextMarginH = f8;
        this.mAutoAdjustTextPosition = z2;
        this.mThumbDrawable = drawable2;
        this.mThumbColor = colorStateList2;
        this.mIsThumbUseDrawable = drawable2 != null;
        this.mTintColor = i2;
        if (i2 == 0) {
            TypedValue typedValue = new TypedValue();
            z3 = true;
            if (getContext().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true)) {
                this.mTintColor = typedValue.data;
            } else {
                this.mTintColor = DEFAULT_TINT_COLOR;
            }
        } else {
            z3 = true;
        }
        if (!this.mIsThumbUseDrawable && this.mThumbColor == null) {
            ColorStateList generateThumbColorWithTintColor = ZKColorUtils.generateThumbColorWithTintColor(this.mTintColor);
            this.mThumbColor = generateThumbColorWithTintColor;
            this.mCurrThumbColor = generateThumbColorWithTintColor.getDefaultColor();
        }
        if (this.mIsThumbUseDrawable) {
            f5 = Math.max(f5, (float) this.mThumbDrawable.getMinimumWidth());
            f9 = Math.max(f9, (float) this.mThumbDrawable.getMinimumHeight());
        }
        this.mThumbSizeF.set(f5, f9);
        this.mBackDrawable = drawable;
        this.mBackColor = colorStateList;
        boolean z8 = drawable != null ? z3 : false;
        this.mIsBackUseDrawable = z8;
        if (!z8 && colorStateList == null) {
            ColorStateList generateBackColorWithTintColor = ZKColorUtils.generateBackColorWithTintColor(this.mTintColor);
            this.mBackColor = generateBackColorWithTintColor;
            int defaultColor = generateBackColorWithTintColor.getDefaultColor();
            this.mCurrBackColor = defaultColor;
            this.mNextBackColor = this.mBackColor.getColorForState(CHECKED_PRESSED_STATE, defaultColor);
        }
        this.mThumbMargin.set(f11, f, f21, f12);
        float f22 = f2;
        this.mBackMeasureRatio = this.mThumbMargin.width() >= 0.0f ? Math.max(f22, 1.0f) : f22;
        this.mThumbRadius = f3;
        this.mBackRadius = f4;
        long j = (long) i;
        this.mAnimationDuration = j;
        this.mFadeBack = z;
        this.mProcessAnimator.setDuration(j);
        if (isChecked()) {
            setProcess(1.0f);
        }
    }

    private Layout makeLayout(CharSequence charSequence) {
        TextPaint textPaint = this.mTextPaint;
        return new StaticLayout(charSequence, textPaint, (int) Math.ceil((double) Layout.getDesiredWidth(charSequence, textPaint)), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        CharSequence charSequence;
        CharSequence charSequence2;
        if (this.mOnLayout == null && (charSequence2 = this.mTextOn) != null) {
            this.mOnLayout = makeLayout(charSequence2);
        }
        if (this.mOffLayout == null && (charSequence = this.mTextOff) != null) {
            this.mOffLayout = makeLayout(charSequence);
        }
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    private int measureWidth(int i) {
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        int ceil = ceil((double) (this.mThumbSizeF.x * this.mBackMeasureRatio));
        if (this.mIsBackUseDrawable) {
            ceil = Math.max(ceil, this.mBackDrawable.getMinimumWidth());
        }
        Layout layout = this.mOnLayout;
        float width = layout != null ? (float) layout.getWidth() : 0.0f;
        Layout layout2 = this.mOffLayout;
        float width2 = layout2 != null ? (float) layout2.getWidth() : 0.0f;
        if (width == 0.0f && width2 == 0.0f) {
            this.mTextWidth = 0.0f;
        } else {
            this.mTextWidth = Math.max(width, width2) + (this.mTextMarginH * 2.0f);
            float f = (float) ceil;
            float f2 = f - this.mThumbSizeF.x;
            float f3 = this.mTextWidth;
            if (f2 < f3) {
                ceil = (int) (f + (f3 - f2));
            }
        }
        int max = Math.max(ceil, ceil((double) (((float) ceil) + this.mThumbMargin.left + this.mThumbMargin.right)));
        int max2 = Math.max(Math.max(max, getPaddingLeft() + max + getPaddingRight()), getSuggestedMinimumWidth());
        if (mode == 1073741824) {
            return Math.max(max2, size);
        }
        return mode == Integer.MIN_VALUE ? Math.min(max2, size) : max2;
    }

    private int measureHeight(int i) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int ceil = ceil((double) Math.max(this.mThumbSizeF.y, this.mThumbSizeF.y + this.mThumbMargin.top + this.mThumbMargin.right));
        Layout layout = this.mOnLayout;
        float height = layout != null ? (float) layout.getHeight() : 0.0f;
        Layout layout2 = this.mOffLayout;
        float height2 = layout2 != null ? (float) layout2.getHeight() : 0.0f;
        if (height == 0.0f && height2 == 0.0f) {
            this.mTextHeight = 0.0f;
        } else {
            float max = Math.max(height, height2);
            this.mTextHeight = max;
            ceil = ceil((double) Math.max((float) ceil, max));
        }
        int max2 = Math.max(ceil, getSuggestedMinimumHeight());
        int max3 = Math.max(max2, getPaddingTop() + max2 + getPaddingBottom());
        if (mode == 1073741824) {
            return Math.max(max3, size);
        }
        return mode == Integer.MIN_VALUE ? Math.min(max3, size) : max3;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            setup();
        }
    }

    private int ceil(double d) {
        return (int) Math.ceil(d);
    }

    private void setup() {
        float f = 0.0f;
        float paddingTop = ((float) getPaddingTop()) + Math.max(0.0f, this.mThumbMargin.top);
        float paddingLeft = ((float) getPaddingLeft()) + Math.max(0.0f, this.mThumbMargin.left);
        if (!(this.mOnLayout == null || this.mOffLayout == null || this.mThumbMargin.top + this.mThumbMargin.bottom <= 0.0f)) {
            paddingTop += (((((float) ((getMeasuredHeight() - getPaddingBottom()) - getPaddingTop())) - this.mThumbSizeF.y) - this.mThumbMargin.top) - this.mThumbMargin.bottom) / 2.0f;
        }
        if (this.mIsThumbUseDrawable) {
            PointF pointF = this.mThumbSizeF;
            pointF.x = Math.max(pointF.x, (float) this.mThumbDrawable.getMinimumWidth());
            PointF pointF2 = this.mThumbSizeF;
            pointF2.y = Math.max(pointF2.y, (float) this.mThumbDrawable.getMinimumHeight());
        }
        this.mThumbRectF.set(paddingLeft, paddingTop, this.mThumbSizeF.x + paddingLeft, this.mThumbSizeF.y + paddingTop);
        float f2 = this.mThumbRectF.left - this.mThumbMargin.left;
        float min = Math.min(0.0f, ((Math.max(this.mThumbSizeF.x * this.mBackMeasureRatio, this.mThumbSizeF.x + this.mTextWidth) - this.mThumbRectF.width()) - this.mTextWidth) / 2.0f);
        float min2 = Math.min(0.0f, (((this.mThumbRectF.height() + this.mThumbMargin.top) + this.mThumbMargin.bottom) - this.mTextHeight) / 2.0f);
        this.mBackRectF.set(f2 + min, (this.mThumbRectF.top - this.mThumbMargin.top) + min2, (((f2 + this.mThumbMargin.left) + Math.max(this.mThumbSizeF.x * this.mBackMeasureRatio, this.mThumbSizeF.x + this.mTextWidth)) + this.mThumbMargin.right) - min, (this.mThumbRectF.bottom + this.mThumbMargin.bottom) - min2);
        this.mSafeRectF.set(this.mThumbRectF.left, 0.0f, (this.mBackRectF.right - this.mThumbMargin.right) - this.mThumbRectF.width(), 0.0f);
        this.mBackRadius = Math.min(Math.min(this.mBackRectF.width(), this.mBackRectF.height()) / 2.0f, this.mBackRadius);
        Drawable drawable = this.mBackDrawable;
        if (drawable != null) {
            drawable.setBounds((int) this.mBackRectF.left, (int) this.mBackRectF.top, ceil((double) this.mBackRectF.right), ceil((double) this.mBackRectF.bottom));
        }
        if (this.mOnLayout != null) {
            float width = this.mBackRectF.left + ((((this.mBackRectF.width() - this.mThumbRectF.width()) - this.mThumbMargin.right) - ((float) this.mOnLayout.getWidth())) / 2.0f) + (this.mThumbMargin.left < 0.0f ? this.mThumbMargin.left * -0.5f : 0.0f);
            if (!this.mIsBackUseDrawable && this.mAutoAdjustTextPosition) {
                width += this.mBackRadius / 4.0f;
            }
            float height = this.mBackRectF.top + ((this.mBackRectF.height() - ((float) this.mOnLayout.getHeight())) / 2.0f);
            this.mTextOnRectF.set(width, height, ((float) this.mOnLayout.getWidth()) + width, ((float) this.mOnLayout.getHeight()) + height);
        }
        if (this.mOffLayout != null) {
            float width2 = (this.mBackRectF.right - ((((this.mBackRectF.width() - this.mThumbRectF.width()) - this.mThumbMargin.left) - ((float) this.mOffLayout.getWidth())) / 2.0f)) - ((float) this.mOffLayout.getWidth());
            if (this.mThumbMargin.right < 0.0f) {
                f = this.mThumbMargin.right * 0.5f;
            }
            float f3 = width2 + f;
            if (!this.mIsBackUseDrawable && this.mAutoAdjustTextPosition) {
                f3 -= this.mBackRadius / 4.0f;
            }
            float height2 = this.mBackRectF.top + ((this.mBackRectF.height() - ((float) this.mOffLayout.getHeight())) / 2.0f);
            this.mTextOffRectF.set(f3, height2, ((float) this.mOffLayout.getWidth()) + f3, ((float) this.mOffLayout.getHeight()) + height2);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mIsBackUseDrawable) {
            if (!this.mFadeBack || this.mCurrentBackDrawable == null || this.mNextBackDrawable == null) {
                this.mBackDrawable.setAlpha(255);
                this.mBackDrawable.draw(canvas);
            } else {
                Drawable drawable = isChecked() ? this.mCurrentBackDrawable : this.mNextBackDrawable;
                Drawable drawable2 = isChecked() ? this.mNextBackDrawable : this.mCurrentBackDrawable;
                int process = (int) (getProcess() * 255.0f);
                drawable.setAlpha(process);
                drawable.draw(canvas);
                drawable2.setAlpha(255 - process);
                drawable2.draw(canvas);
            }
        } else if (this.mFadeBack) {
            int i = isChecked() ? this.mCurrBackColor : this.mNextBackColor;
            int i2 = isChecked() ? this.mNextBackColor : this.mCurrBackColor;
            int process2 = (int) (getProcess() * 255.0f);
            this.mPaint.setARGB((Color.alpha(i) * process2) / 255, Color.red(i), Color.green(i), Color.blue(i));
            RectF rectF = this.mBackRectF;
            float f = this.mBackRadius;
            canvas.drawRoundRect(rectF, f, f, this.mPaint);
            this.mPaint.setARGB((Color.alpha(i2) * (255 - process2)) / 255, Color.red(i2), Color.green(i2), Color.blue(i2));
            RectF rectF2 = this.mBackRectF;
            float f2 = this.mBackRadius;
            canvas.drawRoundRect(rectF2, f2, f2, this.mPaint);
            this.mPaint.setAlpha(255);
        } else {
            this.mPaint.setColor(this.mCurrBackColor);
            RectF rectF3 = this.mBackRectF;
            float f3 = this.mBackRadius;
            canvas.drawRoundRect(rectF3, f3, f3, this.mPaint);
        }
        Layout layout = ((double) getProcess()) > 0.5d ? this.mOnLayout : this.mOffLayout;
        RectF rectF4 = ((double) getProcess()) > 0.5d ? this.mTextOnRectF : this.mTextOffRectF;
        if (!(layout == null || rectF4 == null)) {
            int process3 = (int) ((((double) getProcess()) >= 0.75d ? (getProcess() * 4.0f) - 3.0f : ((double) getProcess()) < 0.25d ? 1.0f - (getProcess() * 4.0f) : 0.0f) * 255.0f);
            int i3 = ((double) getProcess()) > 0.5d ? this.mOnTextColor : this.mOffTextColor;
            layout.getPaint().setARGB((Color.alpha(i3) * process3) / 255, Color.red(i3), Color.green(i3), Color.blue(i3));
            canvas.save();
            canvas.translate(rectF4.left, rectF4.top);
            layout.draw(canvas);
            canvas.restore();
        }
        this.mPresentThumbRectF.set(this.mThumbRectF);
        this.mPresentThumbRectF.offset(this.mProcess * this.mSafeRectF.width(), 0.0f);
        if (this.mIsThumbUseDrawable) {
            this.mThumbDrawable.setBounds((int) this.mPresentThumbRectF.left, (int) this.mPresentThumbRectF.top, ceil((double) this.mPresentThumbRectF.right), ceil((double) this.mPresentThumbRectF.bottom));
            this.mThumbDrawable.draw(canvas);
        } else {
            this.mPaint.setColor(this.mCurrThumbColor);
            RectF rectF5 = this.mPresentThumbRectF;
            float f4 = this.mThumbRadius;
            canvas.drawRoundRect(rectF5, f4, f4, this.mPaint);
        }
        if (this.mDrawDebugRect) {
            this.mRectPaint.setColor(Color.parseColor("#AA0000"));
            canvas.drawRect(this.mBackRectF, this.mRectPaint);
            this.mRectPaint.setColor(Color.parseColor("#0000FF"));
            canvas.drawRect(this.mPresentThumbRectF, this.mRectPaint);
            this.mRectPaint.setColor(Color.parseColor("#00CC00"));
            canvas.drawRect(((double) getProcess()) > 0.5d ? this.mTextOnRectF : this.mTextOffRectF, this.mRectPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        super.drawableStateChanged();
        if (this.mIsThumbUseDrawable || (colorStateList2 = this.mThumbColor) == null) {
            setDrawableState(this.mThumbDrawable);
        } else {
            this.mCurrThumbColor = colorStateList2.getColorForState(getDrawableState(), this.mCurrThumbColor);
        }
        int[] iArr = isChecked() ? UNCHECKED_PRESSED_STATE : CHECKED_PRESSED_STATE;
        ColorStateList textColors = getTextColors();
        if (textColors != null) {
            int defaultColor = textColors.getDefaultColor();
            this.mOnTextColor = textColors.getColorForState(CHECKED_PRESSED_STATE, defaultColor);
            this.mOffTextColor = textColors.getColorForState(UNCHECKED_PRESSED_STATE, defaultColor);
        }
        if (this.mIsBackUseDrawable || (colorStateList = this.mBackColor) == null) {
            Drawable drawable = this.mBackDrawable;
            if (!(drawable instanceof StateListDrawable) || !this.mFadeBack) {
                this.mNextBackDrawable = null;
            } else {
                drawable.setState(iArr);
                this.mNextBackDrawable = this.mBackDrawable.getCurrent().mutate();
            }
            setDrawableState(this.mBackDrawable);
            Drawable drawable2 = this.mBackDrawable;
            if (drawable2 != null) {
                this.mCurrentBackDrawable = drawable2.getCurrent().mutate();
                return;
            }
            return;
        }
        int colorForState = colorStateList.getColorForState(getDrawableState(), this.mCurrBackColor);
        this.mCurrBackColor = colorForState;
        this.mNextBackColor = this.mBackColor.getColorForState(iArr, colorForState);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0030, code lost:
        if (r0 != 3) goto L_0x009c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r10) {
        /*
            r9 = this;
            boolean r0 = r9.isEnabled()
            r1 = 0
            if (r0 == 0) goto L_0x009d
            boolean r0 = r9.isClickable()
            if (r0 == 0) goto L_0x009d
            boolean r0 = r9.isFocusable()
            if (r0 != 0) goto L_0x0015
            goto L_0x009d
        L_0x0015:
            int r0 = r10.getAction()
            float r2 = r10.getX()
            float r3 = r9.mStartX
            float r2 = r2 - r3
            float r3 = r10.getY()
            float r4 = r9.mStartY
            float r3 = r3 - r4
            r4 = 1
            if (r0 == 0) goto L_0x0086
            if (r0 == r4) goto L_0x004d
            r5 = 2
            if (r0 == r5) goto L_0x0033
            r5 = 3
            if (r0 == r5) goto L_0x004d
            goto L_0x009c
        L_0x0033:
            float r10 = r10.getX()
            float r0 = r9.getProcess()
            float r1 = r9.mLastX
            float r1 = r10 - r1
            android.graphics.RectF r2 = r9.mSafeRectF
            float r2 = r2.width()
            float r1 = r1 / r2
            float r0 = r0 + r1
            r9.setProcess(r0)
            r9.mLastX = r10
            goto L_0x009c
        L_0x004d:
            r9.setPressed(r1)
            boolean r0 = r9.getStatusBasedOnPos()
            long r5 = r10.getEventTime()
            long r7 = r10.getDownTime()
            long r5 = r5 - r7
            float r10 = (float) r5
            int r5 = r9.mTouchSlop
            float r6 = (float) r5
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 >= 0) goto L_0x0075
            float r2 = (float) r5
            int r2 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0075
            int r2 = r9.mClickTimeout
            float r2 = (float) r2
            int r10 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r10 >= 0) goto L_0x0075
            r9.performClick()
            goto L_0x009c
        L_0x0075:
            boolean r10 = r9.isChecked()
            if (r0 == r10) goto L_0x0082
            r9.playSoundEffect(r1)
            r9.setChecked(r0)
            goto L_0x009c
        L_0x0082:
            r9.animateToState(r0)
            goto L_0x009c
        L_0x0086:
            r9.catchView()
            float r0 = r10.getX()
            r9.mStartX = r0
            float r10 = r10.getY()
            r9.mStartY = r10
            float r10 = r9.mStartX
            r9.mLastX = r10
            r9.setPressed(r4)
        L_0x009c:
            return r4
        L_0x009d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.zkcore.view.switchbtn.ZKSwitchButton.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean getStatusBasedOnPos() {
        return getProcess() > 0.5f;
    }

    public final float getProcess() {
        return this.mProcess;
    }

    public final void setProcess(float f) {
        if (f > 1.0f) {
            f = 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        this.mProcess = f;
        invalidate();
    }

    public boolean performClick() {
        return super.performClick();
    }

    /* access modifiers changed from: protected */
    public void animateToState(boolean z) {
        ObjectAnimator objectAnimator = this.mProcessAnimator;
        if (objectAnimator != null) {
            if (objectAnimator.isRunning()) {
                this.mProcessAnimator.cancel();
            }
            this.mProcessAnimator.setDuration(this.mAnimationDuration);
            if (z) {
                this.mProcessAnimator.setFloatValues(new float[]{this.mProcess, 1.0f});
            } else {
                this.mProcessAnimator.setFloatValues(new float[]{this.mProcess, 0.0f});
            }
            this.mProcessAnimator.start();
        }
    }

    private void catchView() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public void setChecked(boolean z) {
        if (isChecked() != z) {
            animateToState(z);
        }
        if (this.mRestoring) {
            setCheckedImmediatelyNoEvent(z);
        } else {
            super.setChecked(z);
        }
    }

    public void setCheckedNoEvent(boolean z) {
        if (this.mChildOnCheckedChangeListener == null) {
            setChecked(z);
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        setChecked(z);
        super.setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void setCheckedImmediatelyNoEvent(boolean z) {
        if (this.mChildOnCheckedChangeListener == null) {
            setCheckedImmediately(z);
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        setCheckedImmediately(z);
        super.setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void toggleNoEvent() {
        if (this.mChildOnCheckedChangeListener == null) {
            toggle();
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        toggle();
        super.setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void toggleImmediatelyNoEvent() {
        if (this.mChildOnCheckedChangeListener == null) {
            toggleImmediately();
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        toggleImmediately();
        super.setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        super.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mChildOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setCheckedImmediately(boolean z) {
        super.setChecked(z);
        ObjectAnimator objectAnimator = this.mProcessAnimator;
        if (objectAnimator != null && objectAnimator.isRunning()) {
            this.mProcessAnimator.cancel();
        }
        setProcess(z ? 1.0f : 0.0f);
        invalidate();
    }

    public void toggleImmediately() {
        setCheckedImmediately(!isChecked());
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(getDrawableState());
            invalidate();
        }
    }

    public boolean isDrawDebugRect() {
        return this.mDrawDebugRect;
    }

    public void setDrawDebugRect(boolean z) {
        this.mDrawDebugRect = z;
        invalidate();
    }

    public long getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public void setAnimationDuration(long j) {
        this.mAnimationDuration = j;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public void setThumbDrawable(Drawable drawable) {
        this.mThumbDrawable = drawable;
        this.mIsThumbUseDrawable = drawable != null;
        setup();
        refreshDrawableState();
        requestLayout();
        invalidate();
    }

    public void setThumbDrawableRes(int i) {
        setThumbDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public Drawable getBackDrawable() {
        return this.mBackDrawable;
    }

    public void setBackDrawable(Drawable drawable) {
        this.mBackDrawable = drawable;
        this.mIsBackUseDrawable = drawable != null;
        setup();
        refreshDrawableState();
        requestLayout();
        invalidate();
    }

    public void setBackDrawableRes(int i) {
        setBackDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public ColorStateList getBackColor() {
        return this.mBackColor;
    }

    public void setBackColor(ColorStateList colorStateList) {
        this.mBackColor = colorStateList;
        if (colorStateList != null) {
            setBackDrawable((Drawable) null);
        }
        invalidate();
    }

    public void setBackColorRes(int i) {
        setBackColor(ContextCompat.getColorStateList(getContext(), i));
    }

    public ColorStateList getThumbColor() {
        return this.mThumbColor;
    }

    public void setThumbColor(ColorStateList colorStateList) {
        this.mThumbColor = colorStateList;
        if (colorStateList != null) {
            setThumbDrawable((Drawable) null);
        }
    }

    public void setThumbColorRes(int i) {
        setThumbColor(ContextCompat.getColorStateList(getContext(), i));
    }

    public float getBackMeasureRatio() {
        return this.mBackMeasureRatio;
    }

    public void setBackMeasureRatio(float f) {
        this.mBackMeasureRatio = f;
        requestLayout();
    }

    public RectF getThumbMargin() {
        return this.mThumbMargin;
    }

    public void setThumbMargin(RectF rectF) {
        if (rectF == null) {
            setThumbMargin(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            setThumbMargin(rectF.left, rectF.top, rectF.right, rectF.bottom);
        }
    }

    public void setThumbMargin(float f, float f2, float f3, float f4) {
        this.mThumbMargin.set(f, f2, f3, f4);
        requestLayout();
    }

    public void setThumbSize(float f, float f2) {
        this.mThumbSizeF.set(f, f2);
        setup();
        requestLayout();
    }

    public float getThumbWidth() {
        return this.mThumbSizeF.x;
    }

    public float getThumbHeight() {
        return this.mThumbSizeF.y;
    }

    public void setThumbSize(PointF pointF) {
        if (pointF == null) {
            float f = getResources().getDisplayMetrics().density * 20.0f;
            setThumbSize(f, f);
            return;
        }
        setThumbSize(pointF.x, pointF.y);
    }

    public PointF getThumbSizeF() {
        return this.mThumbSizeF;
    }

    public float getThumbRadius() {
        return this.mThumbRadius;
    }

    public void setThumbRadius(float f) {
        this.mThumbRadius = f;
        if (!this.mIsThumbUseDrawable) {
            invalidate();
        }
    }

    public PointF getBackSizeF() {
        return new PointF(this.mBackRectF.width(), this.mBackRectF.height());
    }

    public float getBackRadius() {
        return this.mBackRadius;
    }

    public void setBackRadius(float f) {
        this.mBackRadius = f;
        if (!this.mIsBackUseDrawable) {
            invalidate();
        }
    }

    public boolean isFadeBack() {
        return this.mFadeBack;
    }

    public void setFadeBack(boolean z) {
        this.mFadeBack = z;
    }

    public int getTintColor() {
        return this.mTintColor;
    }

    public void setTintColor(int i) {
        this.mTintColor = i;
        this.mThumbColor = ZKColorUtils.generateThumbColorWithTintColor(i);
        this.mBackColor = ZKColorUtils.generateBackColorWithTintColor(this.mTintColor);
        this.mIsBackUseDrawable = false;
        this.mIsThumbUseDrawable = false;
        refreshDrawableState();
        invalidate();
    }

    public void setText(CharSequence charSequence, CharSequence charSequence2) {
        this.mTextOn = charSequence;
        this.mTextOff = charSequence2;
        this.mOnLayout = null;
        this.mOffLayout = null;
        requestLayout();
        invalidate();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.onText = this.mTextOn;
        savedState.offText = this.mTextOff;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        setText(savedState.onText, savedState.offText);
        this.mRestoring = true;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mRestoring = false;
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        CharSequence offText;
        CharSequence onText;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.onText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.offText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.onText, parcel, i);
            TextUtils.writeToParcel(this.offText, parcel, i);
        }
    }
}
