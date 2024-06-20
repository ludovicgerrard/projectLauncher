package com.zktechnology.android.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ScrollView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.view.adapter.DragAdapter;
import java.util.ArrayList;
import java.util.List;

public class DragView<T> extends FrameLayout {
    private static final long ANIM_DURING = 250;
    private static final int TAG_KEY = 2131296873;
    /* access modifiers changed from: private */
    public DragAdapter adapter;
    private ValueAnimator.AnimatorUpdateListener animUpdateListener;
    private ValueAnimator animator;
    /* access modifiers changed from: private */
    public boolean canScroll;
    private int currentDragPosition;
    private boolean hasPositionChange;
    /* access modifiers changed from: private */
    public boolean isViewInitDone;
    protected int mChildCount;
    /* access modifiers changed from: private */
    public List<View> mChilds;
    protected int mColHeight;
    protected int mColWidth;
    /* access modifiers changed from: private */
    public int mCurrentY;
    /* access modifiers changed from: private */
    public DragView<T>.NoScrollGridView mGridView;
    protected int mMaxHeight;
    protected int mNumColumns;
    /* access modifiers changed from: private */
    public DragView<T>.ListenScrollView mScrollView;
    private DataSetObserver observer;

    public DragView(Context context) {
        this(context, (AttributeSet) null);
    }

    public DragView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public DragView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChilds = new ArrayList();
        this.mNumColumns = 3;
        this.mColHeight = 0;
        this.mColWidth = 0;
        this.mChildCount = 0;
        this.mMaxHeight = 0;
        this.isViewInitDone = false;
        this.mCurrentY = 0;
        this.currentDragPosition = -1;
        this.hasPositionChange = false;
        this.canScroll = true;
        this.observer = new DataSetObserver() {
            public void onChanged() {
                DragView dragView = DragView.this;
                dragView.mChildCount = dragView.adapter.getCount();
                DragView.this.mChilds.clear();
                DragView dragView2 = DragView.this;
                dragView2.mMaxHeight = 0;
                dragView2.mColWidth = 0;
                dragView2.mColHeight = 0;
                boolean unused = DragView.this.isViewInitDone = false;
            }

            public void onInvalidated() {
                DragView dragView = DragView.this;
                dragView.mChildCount = dragView.adapter.getCount();
            }
        };
        this.animUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int round = Math.round(((Float) valueAnimator.getAnimatedValue()).floatValue());
                if (round < 0) {
                    round = 0;
                } else if (round > DragView.this.mMaxHeight - DragView.this.getHeight()) {
                    round = DragView.this.mMaxHeight - DragView.this.getHeight();
                }
                DragView.this.mScrollView.smoothScrollTo(0, round);
            }
        };
        init();
    }

    private void init() {
        Context context = getContext();
        DragView<T>.NoScrollGridView noScrollGridView = new NoScrollGridView(context);
        this.mGridView = noScrollGridView;
        noScrollGridView.setVerticalScrollBarEnabled(false);
        this.mGridView.setStretchMode(2);
        this.mGridView.setSelector(new ColorDrawable());
        this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                boolean z = false;
                if (DragView.this.mChilds.isEmpty()) {
                    for (int i = 0; i < DragView.this.mGridView.getChildCount(); i++) {
                        View childAt = DragView.this.mGridView.getChildAt(i);
                        childAt.setTag(R.id.tag_drag_view, new int[]{0, 0});
                        childAt.clearAnimation();
                        DragView.this.mChilds.add(childAt);
                    }
                }
                if (!DragView.this.mChilds.isEmpty()) {
                    DragView dragView = DragView.this;
                    dragView.mColHeight = ((View) dragView.mChilds.get(0)).getHeight();
                }
                DragView dragView2 = DragView.this;
                dragView2.mColWidth = dragView2.mGridView.getColumnWidth();
                if (DragView.this.mChildCount % DragView.this.mNumColumns == 0) {
                    DragView dragView3 = DragView.this;
                    dragView3.mMaxHeight = (dragView3.mColHeight * DragView.this.mChildCount) / DragView.this.mNumColumns;
                } else {
                    DragView dragView4 = DragView.this;
                    dragView4.mMaxHeight = dragView4.mColHeight * ((DragView.this.mChildCount / DragView.this.mNumColumns) + 1);
                }
                DragView dragView5 = DragView.this;
                if (dragView5.mMaxHeight - DragView.this.getHeight() > 0) {
                    z = true;
                }
                boolean unused = dragView5.canScroll = z;
                boolean unused2 = DragView.this.isViewInitDone = true;
            }
        });
        this.mScrollView = new ListenScrollView(context);
        this.mGridView.setNumColumns(this.mNumColumns);
        this.mScrollView.addView(this.mGridView, -1, -1);
        addView(this.mScrollView, -1, -1);
    }

    public void setAdapter(DragAdapter dragAdapter) {
        DataSetObserver dataSetObserver;
        DragAdapter dragAdapter2 = this.adapter;
        if (!(dragAdapter2 == null || (dataSetObserver = this.observer) == null)) {
            dragAdapter2.unregisterDataSetObserver(dataSetObserver);
        }
        this.adapter = dragAdapter;
        this.mGridView.setAdapter(dragAdapter);
        dragAdapter.registerDataSetObserver(this.observer);
        this.mChildCount = dragAdapter.getCount();
    }

    public void translateView(int i, int i2) {
        int i3 = i;
        View view = this.mChilds.get(i3);
        int i4 = ((int[]) view.getTag(R.id.tag_drag_view))[0];
        int i5 = ((int[]) view.getTag(R.id.tag_drag_view))[1];
        int i6 = this.mNumColumns;
        int i7 = ((i2 % i6) - (i3 % i6)) + i4;
        int i8 = ((i2 / i6) - (i3 / i6)) + i5;
        TranslateAnimation translateAnimation = new TranslateAnimation(1, (float) i4, 1, (float) i7, 1, (float) i5, 1, (float) i8);
        translateAnimation.setDuration(ANIM_DURING);
        translateAnimation.setFillAfter(true);
        view.setTag(R.id.tag_drag_view, new int[]{i7, i8});
        view.startAnimation(translateAnimation);
    }

    public void onDragPositionChange(int i, int i2) {
        if (i > i2) {
            int i3 = i2;
            while (i3 < i) {
                int i4 = i3 + 1;
                translateView(i3, i4);
                i3 = i4;
            }
        } else {
            for (int i5 = i2; i5 > i; i5--) {
                translateView(i5, i5 - 1);
            }
        }
        if (!this.hasPositionChange) {
            this.hasPositionChange = true;
        }
        if (i < this.mChilds.size() && i >= 0 && i2 < this.mChilds.size() && i2 >= 0) {
            this.adapter.onDataModelMove(i, i2);
            this.mChilds.add(i2, this.mChilds.remove(i));
            this.currentDragPosition = i2;
        }
    }

    public void onTouchAreaChange(int i) {
        if (this.canScroll) {
            ValueAnimator valueAnimator = this.animator;
            if (valueAnimator != null) {
                valueAnimator.removeUpdateListener(this.animUpdateListener);
            }
            if (i == 1) {
                int height = this.mMaxHeight - getHeight();
                int i2 = this.mCurrentY;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{(float) i2, (float) (this.mMaxHeight - getHeight())});
                this.animator = ofFloat;
                ofFloat.setDuration((long) (((float) (height - i2)) / 0.5f));
                this.animator.setTarget(this.mGridView);
                this.animator.addUpdateListener(this.animUpdateListener);
                this.animator.start();
            } else if (i == -1) {
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{(float) this.mCurrentY, 0.0f});
                this.animator = ofFloat2;
                ofFloat2.setDuration((long) (((float) this.mCurrentY) / 0.5f));
                this.animator.setTarget(this.mGridView);
                this.animator.addUpdateListener(this.animUpdateListener);
                this.animator.start();
            }
        }
    }

    public int decodeTouchArea(MotionEvent motionEvent) {
        if (((double) motionEvent.getY()) > ((double) ((((float) getHeight()) + getY()) * 4.0f)) / 5.0d) {
            return 1;
        }
        return ((double) motionEvent.getY()) < ((double) (((float) getHeight()) + getY())) / 5.0d ? -1 : 0;
    }

    public boolean isViewInitDone() {
        return this.isViewInitDone;
    }

    public int getGridChildCount() {
        return this.mChilds.size();
    }

    public View getGridChildAt(int i) {
        if (i < 0 || i >= this.mChilds.size()) {
            return null;
        }
        return this.mChilds.get(i);
    }

    public int getGridChildPos(View view) {
        return this.mGridView.indexOfChild(view);
    }

    public void setCurrentDragPosition(int i) {
        this.currentDragPosition = i;
    }

    public int getCurrentDragPosition() {
        return this.currentDragPosition;
    }

    public int getmColHeight() {
        return this.mColHeight;
    }

    public int getmColWidth() {
        return this.mColWidth;
    }

    public DragAdapter getAdapter() {
        return this.adapter;
    }

    public boolean isHasPositionChange() {
        return this.hasPositionChange;
    }

    public void setHasPositionChange(boolean z) {
        this.hasPositionChange = z;
    }

    public boolean isCanScroll() {
        return this.canScroll;
    }

    public void dispatchEvent(MotionEvent motionEvent) {
        if (this.canScroll) {
            this.mScrollView.dispatchTouchEvent(motionEvent);
        } else {
            this.mGridView.dispatchTouchEvent(motionEvent);
        }
    }

    public int eventToPosition(MotionEvent motionEvent) {
        int i = 0;
        if (motionEvent != null) {
            int x = ((int) motionEvent.getX()) / this.mColWidth;
            if (this.mColHeight != 0) {
                i = ((int) ((motionEvent.getY() - getY()) + ((float) this.mCurrentY))) / this.mColHeight;
            }
            i = (i * this.mNumColumns) + x;
            int i2 = this.mChildCount;
            if (i >= i2) {
                return i2 - 1;
            }
        }
        return i;
    }

    public void addSwapView(Object obj) {
        this.adapter.addNewData(obj);
        this.adapter.notifyDataSetChanged();
    }

    public Object getSwapData() {
        return this.adapter.getSwapData(this.currentDragPosition);
    }

    public void removeSwapView() {
        DragAdapter dragAdapter = this.adapter;
        if (dragAdapter != null) {
            dragAdapter.removeData(this.currentDragPosition);
            this.adapter.notifyDataSetChanged();
        }
    }

    class ListenScrollView extends ScrollView {
        public ListenScrollView(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public void onScrollChanged(int i, int i2, int i3, int i4) {
            super.onScrollChanged(i, i2, i3, i4);
            int unused = DragView.this.mCurrentY = getScrollY();
        }
    }

    class NoScrollGridView extends GridView {
        public NoScrollGridView(Context context) {
            super(context);
        }

        public int getColumnWidth() {
            return getWidth() / getNumColumns();
        }

        public NoScrollGridView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
        }
    }
}
