package com.zktechnology.android.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private boolean mIsOnPause = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        fullScreen();
        View decorView = getWindow().getDecorView();
        decorView.setLayoutDirection(3);
        decorView.setTextDirection(5);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mIsOnPause = true;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mIsOnPause = false;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mIsOnPause) {
            return false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void fullScreen() {
        getWindow().setFlags(1024, 1024);
    }
}
