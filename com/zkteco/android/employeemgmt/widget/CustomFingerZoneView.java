package com.zkteco.android.employeemgmt.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.zktechnology.android.launcher.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFingerZoneView extends View {
    public static final int fingerStatus0 = 0;
    public static final int fingerStatus1 = 1;
    public static final int fingerStatus2 = 2;
    public static final int fingerStatus3 = 3;
    public static final int fingerStatus4 = 4;
    private static final int fingerSum = 10;
    private static final List<Region> lRegionList = new ArrayList();
    private static final List<Region> rRegionList = new ArrayList();
    private static final List<Region> regionList = new ArrayList();
    private int currentFingerNum;
    private int currentLeftFinger;
    private int currentRightFinger;
    private Paint fingerPaint;
    public HashMap<Integer, Integer> fingerStatusMap;
    private HashMap<Integer, Integer[]> intIdMap;
    private boolean isChanged;
    private int mCenterCircleX;
    private int mCenterCircleY;
    private FingerStatusChangedListener mFingerChangedListener;
    private Region mRegionLeft;
    private Region mRegionLeftM;
    private Region mRegionMiddle;
    private Region mRegionRight;
    private Region mRegionRightM;
    private Paint sectorPaint;
    private int whichhand;

    public interface FingerStatusChangedListener {
        void changeFingerBgAndState(int i);

        void onFingerStatusChanged(int i, int i2, int i3, boolean z);
    }

    public CustomFingerZoneView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CustomFingerZoneView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomFingerZoneView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.whichhand = 2;
        this.mRegionLeft = new Region();
        this.mRegionLeftM = new Region();
        this.mRegionMiddle = new Region();
        this.mRegionRightM = new Region();
        this.mRegionRight = new Region();
        this.fingerStatusMap = new HashMap<>();
        this.intIdMap = new HashMap<>();
        this.currentFingerNum = 10;
        this.currentLeftFinger = 5;
        this.currentRightFinger = 10;
        this.isChanged = true;
        intiData();
    }

    private void intiData() {
        for (int i = 0; i < 10; i++) {
            this.fingerStatusMap.put(Integer.valueOf(i), 0);
        }
        if (this.intIdMap.size() == 0) {
            initFingerDrawID();
        }
        Paint paint = new Paint();
        this.sectorPaint = paint;
        paint.setAlpha(0);
        this.sectorPaint.setAntiAlias(true);
        this.sectorPaint.setStyle(Paint.Style.STROKE);
        Paint paint2 = new Paint();
        this.fingerPaint = paint2;
        paint2.setAntiAlias(true);
        this.fingerPaint.setStyle(Paint.Style.FILL);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        this.mCenterCircleX = ((getWidth() + getPaddingLeft()) - getPaddingRight()) / 2;
        this.mCenterCircleY = ((getHeight() + getPaddingTop()) - getPaddingBottom()) / 2;
        Canvas canvas2 = canvas;
        int i = width;
        int i2 = height;
        drawSector(canvas2, i, i2, this.sectorPaint, 135.0f, 54.0f, this.mRegionLeft);
        drawSector(canvas2, i, i2, this.sectorPaint, 189.0f, 54.0f, this.mRegionLeftM);
        drawSector(canvas2, i, i2, this.sectorPaint, 243.0f, 54.0f, this.mRegionMiddle);
        drawSector(canvas2, i, i2, this.sectorPaint, 297.0f, 54.0f, this.mRegionRightM);
        drawSector(canvas2, i, i2, this.sectorPaint, 351.0f, 54.0f, this.mRegionRight);
        List<Region> list = regionList;
        if (list.size() == 0) {
            list.add(this.mRegionLeft);
            list.add(this.mRegionLeftM);
            list.add(this.mRegionMiddle);
            list.add(this.mRegionRightM);
            list.add(this.mRegionRight);
            list.add(this.mRegionLeft);
            list.add(this.mRegionLeftM);
            list.add(this.mRegionMiddle);
            list.add(this.mRegionRightM);
            list.add(this.mRegionRight);
        }
        int i3 = this.whichhand;
        if (1 == i3) {
            for (Map.Entry next : this.fingerStatusMap.entrySet()) {
                Integer num = (Integer) next.getKey();
                if (num.intValue() >= 0 && num.intValue() <= 4) {
                    addFinger(num.intValue(), ((Integer) next.getValue()).intValue(), canvas, this.fingerPaint, width, height);
                }
            }
        } else if (2 == i3) {
            for (Map.Entry next2 : this.fingerStatusMap.entrySet()) {
                Integer num2 = (Integer) next2.getKey();
                if (5 <= num2.intValue() && num2.intValue() <= 9) {
                    addFinger(num2.intValue(), ((Integer) next2.getValue()).intValue(), canvas, this.fingerPaint, width, height);
                }
            }
        }
    }

    private void initFingerDrawID() {
        Integer[] numArr = {Integer.valueOf(R.mipmap.left_d01), Integer.valueOf(R.mipmap.left_s01), Integer.valueOf(R.mipmap.left_c01), Integer.valueOf(R.mipmap.left_f01)};
        Integer[] numArr2 = {Integer.valueOf(R.mipmap.left_d02), Integer.valueOf(R.mipmap.left_s02), Integer.valueOf(R.mipmap.left_c02), Integer.valueOf(R.mipmap.left_f02)};
        Integer[] numArr3 = {Integer.valueOf(R.mipmap.left_d03), Integer.valueOf(R.mipmap.left_s03), Integer.valueOf(R.mipmap.left_c03), Integer.valueOf(R.mipmap.left_f03)};
        Integer[] numArr4 = {Integer.valueOf(R.mipmap.left_d04), Integer.valueOf(R.mipmap.left_s04), Integer.valueOf(R.mipmap.left_c04), Integer.valueOf(R.mipmap.left_f04)};
        Integer[] numArr5 = {Integer.valueOf(R.mipmap.left_d05), Integer.valueOf(R.mipmap.left_s05), Integer.valueOf(R.mipmap.left_c05), Integer.valueOf(R.mipmap.left_f05)};
        Integer[] numArr6 = {Integer.valueOf(R.mipmap.right_d01), Integer.valueOf(R.mipmap.right_s01), Integer.valueOf(R.mipmap.right_c01), Integer.valueOf(R.mipmap.right_f01)};
        Integer[] numArr7 = {Integer.valueOf(R.mipmap.right_d02), Integer.valueOf(R.mipmap.right_s02), Integer.valueOf(R.mipmap.right_c02), Integer.valueOf(R.mipmap.right_f02)};
        Integer[] numArr8 = {Integer.valueOf(R.mipmap.right_d03), Integer.valueOf(R.mipmap.right_s03), Integer.valueOf(R.mipmap.right_c03), Integer.valueOf(R.mipmap.right_f03)};
        Integer[] numArr9 = {Integer.valueOf(R.mipmap.right_d04), Integer.valueOf(R.mipmap.right_s04), Integer.valueOf(R.mipmap.right_c04), Integer.valueOf(R.mipmap.right_f04)};
        Integer[] numArr10 = {Integer.valueOf(R.mipmap.right_d05), Integer.valueOf(R.mipmap.right_s05), Integer.valueOf(R.mipmap.right_c05), Integer.valueOf(R.mipmap.right_f05)};
        this.intIdMap.put(0, numArr5);
        this.intIdMap.put(1, numArr4);
        this.intIdMap.put(2, numArr3);
        this.intIdMap.put(3, numArr2);
        this.intIdMap.put(4, numArr);
        this.intIdMap.put(5, numArr6);
        this.intIdMap.put(6, numArr7);
        this.intIdMap.put(7, numArr8);
        this.intIdMap.put(8, numArr9);
        this.intIdMap.put(9, numArr10);
    }

    private void drawSector(Canvas canvas, int i, int i2, Paint paint, float f, float f2, Region region) {
        Canvas canvas2 = canvas;
        int i3 = i;
        int i4 = i2;
        canvas.save();
        canvas.translate(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        int i5 = -i3;
        int i6 = -i4;
        path.addArc(new RectF(((float) i5) / 2.0f, ((float) i6) / 2.0f, ((float) i3) / 2.0f, ((float) i4) / 2.0f), f, f2);
        path.lineTo(0.0f, 0.0f);
        path.close();
        canvas.drawPath(path, paint);
        path.computeBounds(new RectF(), true);
        region.setPath(path, new Region(i5 / 2, i6 / 2, i3 / 2, i4 / 2));
        canvas.restore();
    }

    private void addFinger(int i, int i2, Canvas canvas, Paint paint, int i3, int i4) {
        int i5 = i;
        int i6 = i2;
        int i7 = this.whichhand;
        if (1 == i7) {
            for (Map.Entry next : this.intIdMap.entrySet()) {
                Integer num = (Integer) next.getKey();
                if (num.intValue() >= 0 && num.intValue() <= 4 && i5 == num.intValue()) {
                    Integer[] numArr = (Integer[]) next.getValue();
                    if (i6 >= 1) {
                        drawfinger(BitmapFactory.decodeResource(getResources(), numArr[i6 - 1].intValue()), canvas, paint, i3, i4);
                    }
                }
            }
        } else if (2 == i7) {
            for (Map.Entry next2 : this.intIdMap.entrySet()) {
                Integer num2 = (Integer) next2.getKey();
                if (5 <= num2.intValue() && num2.intValue() <= 9 && i5 == num2.intValue()) {
                    Integer[] numArr2 = (Integer[]) next2.getValue();
                    if (i6 >= 1) {
                        drawfinger(BitmapFactory.decodeResource(getResources(), numArr2[i6 - 1].intValue()), canvas, paint, i3, i4);
                    }
                }
            }
        }
    }

    private void drawfinger(Bitmap bitmap, Canvas canvas, Paint paint, int i, int i2) {
        canvas.save();
        canvas.translate(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
        canvas.drawBitmap(bitmap, (Rect) null, new RectF(((float) (-i)) / 2.0f, ((float) (-i2)) / 2.0f, ((float) i) / 2.0f, ((float) i2) / 2.0f), paint);
        canvas.restore();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            int x = ((int) motionEvent.getX()) - this.mCenterCircleX;
            int y = ((int) motionEvent.getY()) - this.mCenterCircleY;
            int i = this.whichhand;
            int i2 = 5;
            if (i == 1) {
                int i3 = 0;
                while (true) {
                    List<Region> list = regionList;
                    if (i3 >= list.size() - 5) {
                        break;
                    } else if (!this.isChanged || !list.get(i3).contains(x, y)) {
                        i3++;
                    } else {
                        FingerStatusChangedListener fingerStatusChangedListener = this.mFingerChangedListener;
                        if (fingerStatusChangedListener != null) {
                            fingerStatusChangedListener.onFingerStatusChanged(this.whichhand, this.currentFingerNum, i3, false);
                        }
                    }
                }
            } else if (i == 2) {
                while (true) {
                    List<Region> list2 = regionList;
                    if (i2 >= list2.size()) {
                        break;
                    } else if (!this.isChanged || !list2.get(i2).contains(x, y)) {
                        i2++;
                    } else {
                        FingerStatusChangedListener fingerStatusChangedListener2 = this.mFingerChangedListener;
                        if (fingerStatusChangedListener2 != null) {
                            fingerStatusChangedListener2.onFingerStatusChanged(this.whichhand, this.currentFingerNum, i2, false);
                        }
                    }
                }
            }
        }
        return true;
    }

    public void setFingerStatusChangedListener(FingerStatusChangedListener fingerStatusChangedListener) {
        this.mFingerChangedListener = fingerStatusChangedListener;
    }

    public void setWhichHandAndFinger(int i, int i2) {
        this.whichhand = i;
        if (i == 1) {
            this.currentLeftFinger = i2;
            this.currentFingerNum = i2;
        } else if (i == 2) {
            this.currentRightFinger = i2;
            this.currentFingerNum = i2;
        }
        invalidate();
    }

    public void setClickFingerStatus(int i, int i2, int i3) {
        if (this.fingerStatusMap.size() == 0) {
            return;
        }
        if (i == 1) {
            if (this.fingerStatusMap.get(Integer.valueOf(i2)).intValue() == 1) {
                this.fingerStatusMap.remove(Integer.valueOf(i2));
                this.fingerStatusMap.put(Integer.valueOf(i2), 0);
            } else if (this.fingerStatusMap.get(Integer.valueOf(i2)).intValue() == 3) {
                this.fingerStatusMap.remove(Integer.valueOf(i2));
                this.fingerStatusMap.put(Integer.valueOf(i2), 2);
            }
            if (this.fingerStatusMap.get(Integer.valueOf(i3)).intValue() == 2) {
                this.fingerStatusMap.remove(Integer.valueOf(i3));
                this.fingerStatusMap.put(Integer.valueOf(i3), 1);
            }
        } else if (i == 2) {
            if (this.fingerStatusMap.get(Integer.valueOf(i2)).intValue() == 1) {
                this.fingerStatusMap.remove(Integer.valueOf(i2));
                this.fingerStatusMap.put(Integer.valueOf(i2), 0);
            } else if (this.fingerStatusMap.get(Integer.valueOf(i2)).intValue() == 3) {
                this.fingerStatusMap.remove(Integer.valueOf(i2));
                this.fingerStatusMap.put(Integer.valueOf(i2), 2);
            }
            if (this.fingerStatusMap.get(Integer.valueOf(i3)).intValue() == 0) {
                this.fingerStatusMap.remove(Integer.valueOf(i3));
                this.fingerStatusMap.put(Integer.valueOf(i3), 1);
            } else if (this.fingerStatusMap.get(Integer.valueOf(i3)).intValue() == 2) {
                this.fingerStatusMap.remove(Integer.valueOf(i3));
                this.fingerStatusMap.put(Integer.valueOf(i3), 3);
            }
        } else if (i != 3) {
            int i4 = 4;
            if (i == 4) {
                if (this.fingerStatusMap.get(Integer.valueOf(i3)).intValue() == 1 || this.fingerStatusMap.get(Integer.valueOf(i3)).intValue() == 3) {
                    this.fingerStatusMap.remove(Integer.valueOf(i3));
                    this.fingerStatusMap.put(Integer.valueOf(i3), 2);
                }
                int i5 = this.whichhand;
                if (i5 == 1) {
                    boolean z = true;
                    for (Map.Entry next : this.fingerStatusMap.entrySet()) {
                        if (((Integer) next.getKey()).intValue() < 5 && ((Integer) next.getValue()).intValue() == 0 && ((Integer) next.getKey()).intValue() <= i4) {
                            i4 = ((Integer) next.getKey()).intValue();
                            z = false;
                        }
                    }
                    if (z) {
                        FingerStatusChangedListener fingerStatusChangedListener = this.mFingerChangedListener;
                        if (fingerStatusChangedListener != null) {
                            fingerStatusChangedListener.changeFingerBgAndState(this.whichhand);
                        }
                    } else {
                        this.fingerStatusMap.remove(Integer.valueOf(i4));
                        this.fingerStatusMap.put(Integer.valueOf(i4), 1);
                    }
                    FingerStatusChangedListener fingerStatusChangedListener2 = this.mFingerChangedListener;
                    if (!(fingerStatusChangedListener2 == null || z)) {
                        fingerStatusChangedListener2.onFingerStatusChanged(this.whichhand, i2, i4, false);
                    }
                    setCurrentFingerNum(i4);
                } else if (i5 == 2) {
                    int i6 = 9;
                    boolean z2 = true;
                    for (Map.Entry next2 : this.fingerStatusMap.entrySet()) {
                        if (((Integer) next2.getKey()).intValue() > 4 && ((Integer) next2.getValue()).intValue() == 0 && ((Integer) next2.getKey()).intValue() <= i6) {
                            i6 = ((Integer) next2.getKey()).intValue();
                            z2 = false;
                        }
                    }
                    if (z2) {
                        FingerStatusChangedListener fingerStatusChangedListener3 = this.mFingerChangedListener;
                        if (fingerStatusChangedListener3 != null) {
                            fingerStatusChangedListener3.changeFingerBgAndState(this.whichhand);
                        }
                    } else {
                        this.fingerStatusMap.remove(Integer.valueOf(i6));
                        this.fingerStatusMap.put(Integer.valueOf(i6), 1);
                    }
                    FingerStatusChangedListener fingerStatusChangedListener4 = this.mFingerChangedListener;
                    if (!(fingerStatusChangedListener4 == null || z2)) {
                        fingerStatusChangedListener4.onFingerStatusChanged(this.whichhand, i2, i6, false);
                    }
                    setCurrentFingerNum(i6);
                }
            }
        } else {
            if (this.fingerStatusMap.get(Integer.valueOf(i2)).intValue() == 1) {
                this.fingerStatusMap.remove(Integer.valueOf(i2));
                this.fingerStatusMap.put(Integer.valueOf(i2), 0);
            } else if (this.fingerStatusMap.get(Integer.valueOf(i2)).intValue() == 3) {
                this.fingerStatusMap.remove(Integer.valueOf(i2));
                this.fingerStatusMap.put(Integer.valueOf(i2), 2);
            }
            if (this.fingerStatusMap.get(Integer.valueOf(i3)).intValue() == 2) {
                this.fingerStatusMap.remove(Integer.valueOf(i3));
                this.fingerStatusMap.put(Integer.valueOf(i3), 3);
            }
        }
    }

    public void setClickForceFingerStatus(int i, int i2) {
        if (this.fingerStatusMap.size() == 0) {
            return;
        }
        if (this.fingerStatusMap.get(Integer.valueOf(i2)).intValue() == 2) {
            this.fingerStatusMap.remove(Integer.valueOf(i2));
            this.fingerStatusMap.put(Integer.valueOf(i2), 4);
        } else if (this.fingerStatusMap.get(Integer.valueOf(i2)).intValue() == 4) {
            this.fingerStatusMap.remove(Integer.valueOf(i2));
            this.fingerStatusMap.put(Integer.valueOf(i2), 2);
        }
    }

    public void setChangeStatus(boolean z) {
        this.isChanged = z;
    }

    public boolean getChangeStatus() {
        return this.isChanged;
    }

    public void setFingerFirstShow(int i, int i2) {
        this.fingerStatusMap.remove(Integer.valueOf(i));
        this.fingerStatusMap.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void setFingerFirstSelectedNum() {
        if (this.fingerStatusMap.size() > 0) {
            boolean z = false;
            boolean z2 = false;
            for (Map.Entry next : this.fingerStatusMap.entrySet()) {
                Integer num = (Integer) next.getKey();
                if (num.intValue() >= 5 && ((Integer) next.getValue()).intValue() == 0 && num.intValue() < this.currentRightFinger) {
                    int intValue = num.intValue();
                    this.currentFingerNum = intValue;
                    this.currentRightFinger = intValue;
                    z2 = true;
                }
                if (num.intValue() < 5 && ((Integer) next.getValue()).intValue() == 0 && num.intValue() < this.currentLeftFinger) {
                    this.currentLeftFinger = num.intValue();
                    z = true;
                }
            }
            if (z) {
                this.fingerStatusMap.remove(Integer.valueOf(this.currentLeftFinger));
                this.fingerStatusMap.put(Integer.valueOf(this.currentLeftFinger), 1);
            } else {
                this.currentLeftFinger = 0;
            }
            FingerStatusChangedListener fingerStatusChangedListener = this.mFingerChangedListener;
            if (fingerStatusChangedListener != null) {
                int i = this.currentLeftFinger;
                fingerStatusChangedListener.onFingerStatusChanged(1, i, i, true);
            }
            if (z2) {
                this.fingerStatusMap.remove(Integer.valueOf(this.currentRightFinger));
                this.fingerStatusMap.put(Integer.valueOf(this.currentRightFinger), 1);
            } else {
                this.currentFingerNum = 5;
                this.currentRightFinger = 5;
            }
            FingerStatusChangedListener fingerStatusChangedListener2 = this.mFingerChangedListener;
            if (fingerStatusChangedListener2 != null) {
                int i2 = this.currentRightFinger;
                fingerStatusChangedListener2.onFingerStatusChanged(2, i2, i2, true);
            }
        }
    }

    public Integer getFingerStatus(int i) {
        return this.fingerStatusMap.get(Integer.valueOf(i));
    }

    public void setCurrentFingerNum(int i) {
        this.currentFingerNum = i;
    }

    public int getCurrentFingerNum() {
        return this.currentFingerNum;
    }

    public boolean isFinAboveOne() {
        int i = 0;
        for (Map.Entry next : this.fingerStatusMap.entrySet()) {
            Integer num = (Integer) next.getKey();
            Integer num2 = (Integer) next.getValue();
            if (num2.intValue() == 2 || num2.intValue() == 3) {
                i++;
            }
        }
        if (i > 1) {
            return true;
        }
        return false;
    }
}
