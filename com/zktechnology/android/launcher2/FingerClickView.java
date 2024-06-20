package com.zktechnology.android.launcher2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.zktechnology.android.launcher.R;

public class FingerClickView extends FrameLayout {
    AlphaAnimation alphaAni;
    private ImageView ivCircle;
    private ImageView ivHand;
    TranslateAnimation translateAnimation;

    public FingerClickView(Context context) {
        this(context, (AttributeSet) null);
    }

    public FingerClickView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate(context, R.layout.finger_layout, this);
        this.ivCircle = (ImageView) findViewById(R.id.ivCircle);
        this.ivHand = (ImageView) findViewById(R.id.ivHand);
    }

    private void initAnimator() {
        this.ivCircle.setAlpha(0.0f);
        TranslateAnimation translateAnimation2 = new TranslateAnimation(0.0f, 0.0f, 0.0f, -20.0f);
        this.translateAnimation = translateAnimation2;
        translateAnimation2.setDuration(500);
        this.translateAnimation.setRepeatCount(1);
        this.translateAnimation.setRepeatMode(2);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        this.alphaAni = alphaAnimation;
        alphaAnimation.setFillAfter(true);
        this.alphaAni.setDuration(500);
    }

    public void startAnimation() {
        initAnimator();
    }
}
