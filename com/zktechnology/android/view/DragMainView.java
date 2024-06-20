package com.zktechnology.android.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.view.adapter.DragAdapter;

public class DragMainView extends FrameLayout {
    /* access modifiers changed from: private */
    public int START_DRAG_BOTTOM;
    /* access modifiers changed from: private */
    public int START_DRAG_TOP;
    /* access modifiers changed from: private */
    public boolean canAddViewWhenDragChange;
    private GestureDetector detector;
    /* access modifiers changed from: private */
    public long dragLongPressTime;
    private View dragSlider;
    /* access modifiers changed from: private */
    public Handler handler;
    /* access modifiers changed from: private */
    public boolean hasSendDragMsg;
    /* access modifiers changed from: private */
    public View hideView;
    /* access modifiers changed from: private */
    public boolean isDragable;
    /* access modifiers changed from: private */
    public float[] lastLocation;
    /* access modifiers changed from: private */
    public View mCopyView;
    /* access modifiers changed from: private */
    public DragView mDragBottom;
    private FrameLayout mDragFrame;
    /* access modifiers changed from: private */
    public DragView mDragTop;
    /* access modifiers changed from: private */
    public Point mMovePoint;
    /* access modifiers changed from: private */
    public int mStartPoint;
    private int mTouchArea;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    /* access modifiers changed from: private */
    public boolean isDragBack(boolean z) {
        return (z && this.mStartPoint == this.START_DRAG_TOP) || (!z && this.mStartPoint == this.START_DRAG_BOTTOM);
    }

    /* access modifiers changed from: private */
    public boolean isDragFromTop() {
        Point point = this.mMovePoint;
        return point != null && this.mDragTop != null && ((float) point.x) > this.mDragTop.getX() && ((float) this.mMovePoint.x) < this.mDragTop.getX() + ((float) this.mDragTop.getWidth()) && ((float) this.mMovePoint.y) > this.mDragTop.getY() && ((float) this.mMovePoint.y) < this.mDragTop.getY() + ((float) this.mDragTop.getHeight());
    }

    /* access modifiers changed from: private */
    public Point getDragViewCenterPoint(DragView dragView) {
        Point point = new Point();
        if (dragView != null) {
            int height = dragView.getHeight();
            int width = dragView.getWidth();
            point.set((int) (dragView.getX() + ((float) (width / 2))), (int) (dragView.getY() + ((float) (height / 2))));
        }
        return point;
    }

    /* access modifiers changed from: private */
    public boolean isDragFromBottom() {
        Point point = this.mMovePoint;
        return point != null && this.mDragBottom != null && ((float) point.x) > this.mDragBottom.getX() && ((float) this.mMovePoint.x) < this.mDragBottom.getX() + ((float) this.mDragBottom.getWidth()) && ((float) this.mMovePoint.y) > this.mDragBottom.getY() && ((float) this.mMovePoint.y) < this.mDragBottom.getY() + ((float) this.mDragBottom.getHeight());
    }

    /* access modifiers changed from: private */
    public boolean isTouchInTop(MotionEvent motionEvent) {
        return isTouchInTop(motionEvent.getY());
    }

    /* access modifiers changed from: private */
    public boolean isTouchInTop(float f) {
        return f > this.mDragTop.getY() && f < this.mDragTop.getY() + ((float) this.mDragTop.getHeight());
    }

    private boolean isTouchInBottom(MotionEvent motionEvent) {
        return isTouchInBottom(motionEvent.getY());
    }

    private boolean isTouchInBottom(float f) {
        return f > this.mDragBottom.getY() && f < this.mDragBottom.getY() + ((float) this.mDragBottom.getHeight());
    }

    /* access modifiers changed from: private */
    public void dragChangePosition(DragView dragView, int i) {
        if (i != dragView.getCurrentDragPosition() && isCanDragMove(dragView, i)) {
            dragView.onDragPositionChange(dragView.getCurrentDragPosition(), i);
        }
    }

    /* access modifiers changed from: private */
    public boolean isCanDragMove(DragView dragView, int i) {
        return i >= 0 && i < dragView.getGridChildCount();
    }

    public DragMainView(Context context) {
        this(context, (AttributeSet) null);
    }

    public DragMainView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public DragMainView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.hasSendDragMsg = false;
        this.handler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message message) {
                if (message.what == 291) {
                    boolean unused = DragMainView.this.isDragable = true;
                    if (DragMainView.this.isTouchInTop((float) message.arg2)) {
                        DragMainView.this.mDragTop.setCurrentDragPosition(message.arg1);
                        DragMainView dragMainView = DragMainView.this;
                        dragMainView.copyView(dragMainView.mDragTop);
                    } else {
                        DragMainView.this.mDragBottom.setCurrentDragPosition(message.arg1);
                        DragMainView dragMainView2 = DragMainView.this;
                        dragMainView2.copyView(dragMainView2.mDragBottom);
                    }
                    boolean unused2 = DragMainView.this.hasSendDragMsg = false;
                }
                return false;
            }
        });
        this.isDragable = false;
        this.lastLocation = null;
        this.mTouchArea = 0;
        this.canAddViewWhenDragChange = true;
        this.START_DRAG_TOP = 0;
        this.START_DRAG_BOTTOM = 1;
        this.simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (DragMainView.this.hasSendDragMsg) {
                    boolean unused = DragMainView.this.hasSendDragMsg = false;
                    DragMainView.this.handler.removeMessages(291);
                }
                if (DragMainView.this.isDragable && DragMainView.this.mCopyView != null) {
                    if (DragMainView.this.lastLocation == null && motionEvent != null) {
                        float[] unused2 = DragMainView.this.lastLocation = new float[]{motionEvent.getRawX(), motionEvent.getRawY()};
                    }
                    if (DragMainView.this.lastLocation == null) {
                        float[] unused3 = DragMainView.this.lastLocation = new float[]{0.0f, 0.0f};
                    }
                    float rawX = DragMainView.this.lastLocation[0] - motionEvent2.getRawX();
                    float rawY = DragMainView.this.lastLocation[1] - motionEvent2.getRawY();
                    DragMainView.this.lastLocation[0] = motionEvent2.getRawX();
                    DragMainView.this.lastLocation[1] = motionEvent2.getRawY();
                    DragMainView.this.mCopyView.setX(DragMainView.this.mCopyView.getX() - rawX);
                    DragMainView.this.mCopyView.setY(DragMainView.this.mCopyView.getY() - rawY);
                    int eventToPosition = DragMainView.this.eventToPosition(motionEvent2);
                    DragMainView.this.mCopyView.invalidate();
                    if (DragMainView.this.isDragInTop()) {
                        if (DragMainView.this.isDragFromBottom()) {
                            DragMainView dragMainView = DragMainView.this;
                            if (dragMainView.isDragBack(dragMainView.isDragInTop())) {
                                DragMainView dragMainView2 = DragMainView.this;
                                int unused4 = dragMainView2.mStartPoint = dragMainView2.START_DRAG_BOTTOM;
                                boolean unused5 = DragMainView.this.canAddViewWhenDragChange = true;
                            }
                            if (DragMainView.this.canAddViewWhenDragChange) {
                                DragMainView.this.mDragTop.addSwapView(DragMainView.this.mDragBottom.getSwapData());
                                DragMainView.this.mDragBottom.removeSwapView();
                                boolean unused6 = DragMainView.this.canAddViewWhenDragChange = false;
                                if (DragMainView.this.hideView != null) {
                                    DragMainView.this.hideView.setVisibility(0);
                                }
                            }
                            if (DragMainView.this.mDragTop.isViewInitDone()) {
                                DragMainView.this.mDragTop.setCurrentDragPosition(DragMainView.this.mDragTop.getGridChildCount() - 1);
                                DragMainView dragMainView3 = DragMainView.this;
                                View unused7 = dragMainView3.hideView = dragMainView3.mDragTop.getGridChildAt(DragMainView.this.mDragTop.getCurrentDragPosition());
                                if (DragMainView.this.hideView != null) {
                                    DragMainView.this.hideView.setVisibility(4);
                                }
                                DragMainView dragMainView4 = DragMainView.this;
                                Point unused8 = dragMainView4.mMovePoint = dragMainView4.getDragViewCenterPoint(dragMainView4.mDragTop);
                            }
                        }
                        if (DragMainView.this.mDragTop.isViewInitDone()) {
                            DragMainView dragMainView5 = DragMainView.this;
                            dragMainView5.dragChangePosition(dragMainView5.mDragTop, eventToPosition);
                        }
                    } else if (DragMainView.this.isDragInButtom()) {
                        if (DragMainView.this.isDragFromTop()) {
                            DragMainView dragMainView6 = DragMainView.this;
                            if (dragMainView6.isDragBack(dragMainView6.isTouchInTop(motionEvent2))) {
                                DragMainView dragMainView7 = DragMainView.this;
                                int unused9 = dragMainView7.mStartPoint = dragMainView7.START_DRAG_TOP;
                                boolean unused10 = DragMainView.this.canAddViewWhenDragChange = true;
                            }
                            if (DragMainView.this.canAddViewWhenDragChange) {
                                DragMainView.this.mDragBottom.addSwapView(DragMainView.this.mDragTop.getSwapData());
                                DragMainView.this.mDragTop.removeSwapView();
                                boolean unused11 = DragMainView.this.canAddViewWhenDragChange = false;
                                if (DragMainView.this.hideView != null) {
                                    DragMainView.this.hideView.setVisibility(0);
                                }
                            }
                            if (DragMainView.this.mDragBottom.isViewInitDone()) {
                                DragMainView.this.mDragBottom.setCurrentDragPosition(DragMainView.this.mDragBottom.getGridChildCount() - 1);
                                DragMainView dragMainView8 = DragMainView.this;
                                View unused12 = dragMainView8.hideView = dragMainView8.mDragBottom.getGridChildAt(DragMainView.this.mDragBottom.getCurrentDragPosition());
                                if (DragMainView.this.hideView != null) {
                                    DragMainView.this.hideView.setVisibility(4);
                                }
                                DragMainView dragMainView9 = DragMainView.this;
                                Point unused13 = dragMainView9.mMovePoint = dragMainView9.getDragViewCenterPoint(dragMainView9.mDragBottom);
                            }
                        }
                        if (DragMainView.this.mDragBottom.isViewInitDone()) {
                            DragMainView dragMainView10 = DragMainView.this;
                            dragMainView10.dragChangePosition(dragMainView10.mDragBottom, eventToPosition);
                        }
                    }
                }
                return true;
            }

            public void onShowPress(MotionEvent motionEvent) {
                DragMainView.this.getParent().requestDisallowInterceptTouchEvent(true);
                int eventToPosition = DragMainView.this.eventToPosition(motionEvent);
                DragMainView dragMainView = DragMainView.this;
                if (dragMainView.isCanDragMove(dragMainView.isTouchInTop(motionEvent) ? DragMainView.this.mDragTop : DragMainView.this.mDragBottom, eventToPosition)) {
                    DragMainView.this.handler.sendMessageDelayed(DragMainView.this.handler.obtainMessage(291, eventToPosition, (int) motionEvent.getY()), DragMainView.this.dragLongPressTime - 170);
                    Point unused = DragMainView.this.mMovePoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
                    DragMainView dragMainView2 = DragMainView.this;
                    int unused2 = dragMainView2.mStartPoint = dragMainView2.isTouchInTop(motionEvent) ? DragMainView.this.START_DRAG_TOP : DragMainView.this.START_DRAG_BOTTOM;
                    boolean unused3 = DragMainView.this.hasSendDragMsg = true;
                }
            }
        };
        this.dragLongPressTime = 170;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        Context context = getContext();
        GestureDetector gestureDetector = new GestureDetector(context, this.simpleOnGestureListener);
        this.detector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
        this.mDragFrame = new FrameLayout(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_drag_main, this, false);
        this.dragSlider = inflate;
        this.mDragTop = (DragView) inflate.findViewById(R.id.drag_top);
        this.mDragBottom = (DragView) this.dragSlider.findViewById(R.id.drag_bottom);
        addView(this.dragSlider, -1, -1);
        addView(this.mDragFrame, -1, -1);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.isDragable) {
            handleScrollAndCreMirror(motionEvent);
        } else {
            dispatchEvent(isTouchInTop(motionEvent) ? this.mDragTop : this.mDragBottom, motionEvent);
        }
        this.detector.onTouchEvent(motionEvent);
        if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
            this.lastLocation = null;
            if (this.hasSendDragMsg) {
                this.hasSendDragMsg = false;
                this.handler.removeMessages(291);
            }
        }
        return true;
    }

    private void dispatchEvent(DragView dragView, MotionEvent motionEvent) {
        dragView.dispatchEvent(motionEvent);
    }

    /* access modifiers changed from: private */
    public boolean isDragInTop() {
        View view = this.mCopyView;
        if (view != null && view.getY() + ((float) (this.mCopyView.getHeight() / 2)) < this.mDragTop.getY() + ((float) this.mDragTop.getBottom())) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean isDragInButtom() {
        View view = this.mCopyView;
        if (view != null && view.getY() + ((float) (this.mCopyView.getHeight() / 2)) > this.mDragBottom.getY()) {
            return true;
        }
        return false;
    }

    private void handleScrollAndCreMirror(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (isDragInTop()) {
                        decodeScrollArea(this.mDragTop, motionEvent);
                        return;
                    } else if (isDragInButtom()) {
                        decodeScrollArea(this.mDragBottom, motionEvent);
                        return;
                    } else {
                        return;
                    }
                } else if (action != 3) {
                    return;
                }
            }
            View view = this.hideView;
            if (view != null) {
                view.setVisibility(0);
            }
            this.mDragFrame.removeAllViews();
            if (isDragInTop()) {
                updateUI(this.mDragTop, motionEvent);
            } else if (isDragInButtom()) {
                updateUI(this.mDragBottom, motionEvent);
            } else {
                updateUI(this.mDragTop, motionEvent);
                updateUI(this.mDragBottom, motionEvent);
            }
            this.mCopyView = null;
            this.canAddViewWhenDragChange = true;
            this.isDragable = false;
            return;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        makeCopyView(isTouchInTop(motionEvent) ? this.mDragTop : this.mDragBottom, eventToPosition(motionEvent));
    }

    private void updateUI(DragView dragView, MotionEvent motionEvent) {
        if (dragView.isHasPositionChange()) {
            dragView.setHasPositionChange(false);
            dragView.getAdapter().notifyDataSetChanged();
        }
        if (dragView.isCanScroll() && dragView.decodeTouchArea(motionEvent) != 0) {
            dragView.onTouchAreaChange(0);
            this.mTouchArea = 0;
        }
    }

    private void decodeScrollArea(DragView dragView, MotionEvent motionEvent) {
        int decodeTouchArea;
        if (dragView.isCanScroll() && (decodeTouchArea = dragView.decodeTouchArea(motionEvent)) != this.mTouchArea) {
            dragView.onTouchAreaChange(decodeTouchArea);
            this.mTouchArea = decodeTouchArea;
        }
    }

    private void makeCopyView(DragView dragView, int i) {
        if (i >= 0 && i < dragView.getGridChildCount()) {
            dragView.setCurrentDragPosition(i);
            copyView(dragView);
        }
    }

    public int eventToPosition(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return 0;
        }
        if (isTouchInTop(motionEvent)) {
            return this.mDragTop.eventToPosition(motionEvent);
        }
        return this.mDragBottom.eventToPosition(motionEvent);
    }

    /* access modifiers changed from: private */
    public void copyView(DragView dragView) {
        View gridChildAt = dragView.getGridChildAt(dragView.getCurrentDragPosition());
        this.hideView = gridChildAt;
        int gridChildPos = dragView.getGridChildPos(gridChildAt);
        DragAdapter adapter = dragView.getAdapter();
        if (!adapter.isUseCopyView()) {
            this.mCopyView = adapter.getView(gridChildPos, this.mCopyView, this.mDragFrame);
        } else {
            this.mCopyView = adapter.copyView(gridChildPos, this.mCopyView, this.mDragFrame);
        }
        this.hideView.setVisibility(4);
        if (this.mCopyView.getParent() == null) {
            this.mDragFrame.addView(this.mCopyView, dragView.getmColWidth(), dragView.getmColHeight());
        }
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        this.hideView.getLocationOnScreen(iArr);
        this.mDragFrame.getLocationOnScreen(iArr2);
        this.mCopyView.setX((float) (iArr[0] - iArr2[0]));
        this.mCopyView.setY((float) (iArr[1] - iArr2[1]));
        this.mCopyView.setScaleX(1.2f);
        this.mCopyView.setScaleY(1.2f);
    }

    public void setBottomAdapter(DragAdapter dragAdapter) {
        this.mDragBottom.setAdapter(dragAdapter);
    }

    public boolean isViewInitDone() {
        boolean isViewInitDone = this.mDragBottom.isViewInitDone();
        return this.mDragTop.getVisibility() == 0 ? isViewInitDone & this.mDragTop.isViewInitDone() : isViewInitDone;
    }

    public void setTopAdapter(DragAdapter dragAdapter) {
        this.mDragTop.setAdapter(dragAdapter);
    }
}
