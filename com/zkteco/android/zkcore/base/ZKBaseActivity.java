package com.zkteco.android.zkcore.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.util.HandlerHelper;
import com.zkteco.android.zkcore.R;
import com.zkteco.android.zkcore.utils.ZKActivityCollector;

public class ZKBaseActivity extends AppCompatActivity {
    private static final String DB_FIELD_SCREEN_TIMEOUT = "TOMenu";
    /* access modifiers changed from: private */
    public static final String TAG = "com.zkteco.android.zkcore.base.ZKBaseActivity";
    public static final int TIMEOUT_MILLISECOND = 1000;
    private long inactivityTimeout = -1;
    /* access modifiers changed from: private */
    public boolean inactivityTimeoutEnabled = true;
    /* access modifiers changed from: protected */
    public Context mContext;
    private boolean mIsOnPause = false;
    private Handler screenTimeoutHandler = null;
    private ScreenTimeoutRunnable screenTimeoutRunnable = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        View decorView = getWindow().getDecorView();
        decorView.setBackgroundColor(getResources().getColor(R.color.clr_FFFFFF));
        if (decorView != null) {
            decorView.setLayoutDirection(3);
            decorView.setTextDirection(5);
        }
        init();
        initHandler();
        setScreenTimeoutMs();
        hideBottomUIMenu();
    }

    private void hideBottomUIMenu() {
        Intent intent = new Intent();
        intent.setAction("HIDE_NAVIGATION");
        sendBroadcast(intent);
    }

    private void init() {
        Log.d("BaseActivity", getClass().getSimpleName());
        ZKActivityCollector.addActivity(this);
        this.mContext = getApplicationContext();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        ZKActivityCollector.removeActivity(this);
        cleanupInactivityTimeoutHandler();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        cleanupInactivityTimeoutHandler();
        this.mIsOnPause = true;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        postInactivityTimeoutHandler();
        this.mIsOnPause = false;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 1 || action == 2) {
            postInactivityTimeoutHandler();
        }
        if (this.mIsOnPause) {
            return false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private void initHandler() {
        if (HandlerHelper.getHandler() == null) {
            HandlerHelper.initialize();
        }
        this.screenTimeoutHandler = HandlerHelper.getHandler();
    }

    private void setScreenTimeoutMs() {
        try {
            DataManager dataManager = new DataManager();
            dataManager.open(this);
            this.inactivityTimeout = (long) (dataManager.getIntOption("TOMenu", 0) * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postInactivityTimeoutHandler() {
        if (this.inactivityTimeoutEnabled) {
            Log.d(TAG, "Keep the screen on for a while.");
            ScreenTimeoutRunnable screenTimeoutRunnable2 = this.screenTimeoutRunnable;
            if (screenTimeoutRunnable2 != null) {
                screenTimeoutRunnable2.killRunnable();
                this.screenTimeoutHandler.removeCallbacks(this.screenTimeoutRunnable);
            }
            if (this.inactivityTimeout > 0) {
                ScreenTimeoutRunnable screenTimeoutRunnable3 = new ScreenTimeoutRunnable();
                this.screenTimeoutRunnable = screenTimeoutRunnable3;
                this.screenTimeoutHandler.postDelayed(screenTimeoutRunnable3, this.inactivityTimeout);
            }
        }
    }

    public void cleanupInactivityTimeoutHandler() {
        if (this.inactivityTimeoutEnabled) {
            Log.d(TAG, "Reset the screen timeout.");
            ScreenTimeoutRunnable screenTimeoutRunnable2 = this.screenTimeoutRunnable;
            if (screenTimeoutRunnable2 != null) {
                screenTimeoutRunnable2.killRunnable();
                this.screenTimeoutHandler.removeCallbacks(this.screenTimeoutRunnable);
            }
        }
    }

    public void enableInactivityTimeout(boolean z) {
        if (z) {
            this.inactivityTimeoutEnabled = true;
            postInactivityTimeoutHandler();
            return;
        }
        cleanupInactivityTimeoutHandler();
        this.inactivityTimeoutEnabled = false;
    }

    public void updateInactivityTimeout(long j) {
        this.inactivityTimeout = j;
        postInactivityTimeoutHandler();
    }

    /* access modifiers changed from: protected */
    public void onLogout() {
        Log.w(TAG, "closing the activity");
        ZKActivityCollector.finishAll();
    }

    private class ScreenTimeoutRunnable implements Runnable {
        private boolean killRunnable;

        private ScreenTimeoutRunnable() {
            this.killRunnable = false;
        }

        public void killRunnable() {
            this.killRunnable = true;
        }

        public void run() {
            if (!this.killRunnable && ZKBaseActivity.this.inactivityTimeoutEnabled) {
                Log.d(ZKBaseActivity.TAG, "The inactivity timeout expires!");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.custom.home");
                ZKBaseActivity.this.startActivity(intent);
                ZKBaseActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        ZKBaseActivity.this.onLogout();
                    }
                });
            }
        }
    }
}
