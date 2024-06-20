package com.zkteco.android.zkcore.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.util.HandlerHelper;
import com.zkteco.android.zkcore.utils.ZKActivityCollector;

public class LunrZKBaseActivity extends Activity {
    private static final String DB_FIELD_SCREEN_TIMEOUT = "TOMenu";
    /* access modifiers changed from: private */
    public static final String TAG = "com.zkteco.android.zkcore.base.adapter.LunrZKBaseActivity";
    public static final int TIMEOUT_MILLISECOND = 1000;
    private long inactivityTimeout = -1;
    /* access modifiers changed from: private */
    public boolean inactivityTimeoutEnabled = true;
    protected Context mContext;
    private Handler screenTimeoutHandler = null;
    private ScreenTimeoutRunnable screenTimeoutRunnable = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
        initHandler();
        setScreenTimeoutMs();
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
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        postInactivityTimeoutHandler();
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        postInactivityTimeoutHandler();
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
        finish();
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
            if (!this.killRunnable && LunrZKBaseActivity.this.inactivityTimeoutEnabled) {
                Log.d(LunrZKBaseActivity.TAG, "The inactivity timeout expires!");
                Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                intent.addCategory("android.intent.category.HOME");
                intent.addFlags(270532608);
                LunrZKBaseActivity.this.startActivity(intent);
                LunrZKBaseActivity.this.onLogout();
            }
        }
    }
}
