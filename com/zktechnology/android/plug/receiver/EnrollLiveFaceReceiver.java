package com.zktechnology.android.plug.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.ZKReceiver;

public abstract class EnrollLiveFaceReceiver<T> extends ZKReceiver<T> {
    private static final String TAG = "EnrollLiveFaceReceiver";
    String ACTION_ENROLL_LIVE_FACE = "";
    String EXTRA_PHOTO_PATH = "extra_key_PHOTO_PATH";
    String EXTRA_USER_PIN = "extra_key_USER_PIN";
    private boolean isRegister = false;

    public abstract boolean onEnroll(T t, Context context, String str, String str2);

    public EnrollLiveFaceReceiver(T t) {
        super(t);
    }

    public void registerReceiver(Context context) {
        context.registerReceiver(this, new IntentFilter(this.ACTION_ENROLL_LIVE_FACE));
        this.isRegister = true;
    }

    public void unregisterReceiver(Context context) {
        if (this.isRegister) {
            context.unregisterReceiver(this);
        }
    }

    public void onReceive(T t, Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.e(LogUtils.TAG_VERIFY, "EnrollLiveFace Action:%s", intent.getAction());
        if (this.ACTION_ENROLL_LIVE_FACE.equalsIgnoreCase(action)) {
            LogUtils.e(TAG, "Enroll live face");
            onEnroll(t, context, intent.getStringExtra(this.EXTRA_USER_PIN), intent.getStringExtra(this.EXTRA_PHOTO_PATH));
        }
    }
}
