package com.zktechnology.android.verify.dialog.view.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ZKListenableActionLayout extends LinearLayout {
    private OnUserInteractionListener mListener;

    public interface OnUserInteractionListener {
        void onUserInteraction();
    }

    public ZKListenableActionLayout(Context context) {
        super(context);
    }

    public ZKListenableActionLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ZKListenableActionLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!(this.mListener == null || motionEvent == null || motionEvent.getAction() != 0)) {
            this.mListener.onUserInteraction();
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setOnUserInteractiveListener(OnUserInteractionListener onUserInteractionListener) {
        this.mListener = onUserInteractionListener;
    }
}
