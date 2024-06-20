package com.zkteco.android.employeemgmt.activity.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.zkteco.android.zkcore.base.ZKBaseActivity;
import com.zkteco.android.zkcore.view.ZKToast;

public class ZKStaffBaseActivity extends ZKBaseActivity {
    private HomeWatcherReceiver homeWatcherReceiver = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerReceiver();
    }

    public void hideSoftKeyBoard(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return ZKStaffBaseActivity.this.lambda$hideSoftKeyBoard$0$ZKStaffBaseActivity(view, motionEvent);
            }
        });
    }

    public /* synthetic */ boolean lambda$hideSoftKeyBoard$0$ZKStaffBaseActivity(View view, MotionEvent motionEvent) {
        if (getCurrentFocus() != null) {
            return ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return false;
    }

    private void registerReceiver() {
        this.homeWatcherReceiver = new HomeWatcherReceiver();
        registerReceiver(this.homeWatcherReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    public class HomeWatcherReceiver extends BroadcastReceiver {
        public static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        public static final String SYSTEM_DIALOG_REASON_KEY = "reason";

        public HomeWatcherReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), "android.intent.action.CLOSE_SYSTEM_DIALOGS") && TextUtils.equals(SYSTEM_DIALOG_REASON_HOME_KEY, intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY))) {
                ZKStaffBaseActivity.this.finish();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        HomeWatcherReceiver homeWatcherReceiver2 = this.homeWatcherReceiver;
        if (homeWatcherReceiver2 != null) {
            try {
                unregisterReceiver(homeWatcherReceiver2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void showToast(Context context, int i) {
        if (!isFinishing() && !isDestroyed()) {
            runOnUiThread(new Runnable(context, i) {
                public final /* synthetic */ Context f$0;
                public final /* synthetic */ int f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKToast.showToast(this.f$0, this.f$1);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void showToast(Context context, String str) {
        if (!isFinishing() && !isDestroyed()) {
            runOnUiThread(new Runnable(context, str) {
                public final /* synthetic */ Context f$0;
                public final /* synthetic */ String f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKToast.showToast(this.f$0, this.f$1);
                }
            });
        }
    }
}
