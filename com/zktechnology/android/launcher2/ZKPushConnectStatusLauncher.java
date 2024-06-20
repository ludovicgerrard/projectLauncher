package com.zktechnology.android.launcher2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.common.speech.LoggingEvents;
import com.zktechnology.android.event.EventState;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.ZKReceiver;
import com.zktechnology.android.view.StatusBarView;
import com.zktechnology.android.view.controller.ServerConnectStateController;
import com.zkteco.android.db.orm.util.SpeakerHelper;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class ZKPushConnectStatusLauncher extends ZKPalmLauncher {
    public static final String ACTION_RS485_BUZZER = "com.zkteco.android.launcher.action.BUZZER";
    public static final String ACTION_RS485_LED = "com.zkteco.android.launcher.action.LED";
    public static final String ACTION_RS485_TEXT = "com.zkteco.android.launcher.action.TEXT";
    /* access modifiers changed from: private */
    public static String TAG = "com.zktechnology.android.launcher2.ZKPushConnectStatusLauncher";
    protected LinearLayout ll_dontworkroot;
    private Rs485Receiver mRs485Receiver;
    protected LinearLayout rlAttClock;
    private StatusBarView statusBarView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
        initRS485Receiver();
    }

    private void initRS485Receiver() {
        this.mRs485Receiver = new Rs485Receiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_RS485_BUZZER);
        intentFilter.addAction(ACTION_RS485_LED);
        intentFilter.addAction(ACTION_RS485_TEXT);
        registerReceiver(this.mRs485Receiver, intentFilter);
    }

    private void initView() {
        this.statusBarView = (StatusBarView) findViewById(R.id.view_status_bar);
        this.ll_dontworkroot = (LinearLayout) findViewById(R.id.ll_dontworkroot);
        this.rlAttClock = (LinearLayout) findViewById(R.id.rl_att_clock);
        this.statusBarView.setOnServerConnectStateChangedListener(new ServerConnectStateController.OnServerConnectStateChangedListener() {
            public final void onServerConnectStateChanged(int i, boolean z) {
                ZKPushConnectStatusLauncher.this.lambda$initView$0$ZKPushConnectStatusLauncher(i, z);
            }
        });
        this.statusBarView.setOnLocationStateChangedListener($$Lambda$ZKPushConnectStatusLauncher$U3Uj3FgF5mrVFHF5NIn3MenYzjg.INSTANCE);
        this.statusBarView.setClickListener(this);
    }

    public /* synthetic */ void lambda$initView$0$ZKPushConnectStatusLauncher(int i, boolean z) {
        ((LauncherApplication) getApplication()).setPushState(i);
    }

    static /* synthetic */ void lambda$initView$1(boolean z, String str, double d, double d2, float f) {
        if (ZKLauncher.locationFunOn != z) {
            ZKLauncher.locationFunOn = z;
        }
        if (z) {
            ZKLauncher.longitude = d2;
            ZKLauncher.latitude = d;
        }
    }

    /* access modifiers changed from: protected */
    public void changeViewVisibility() {
        this.statusBarView.resetViewVisibility();
    }

    public void initComplete() {
        this.statusBarView.onInitComplete();
    }

    public void startLocation() {
        this.statusBarView.startLocation();
    }

    /* access modifiers changed from: protected */
    public void updateStatus(int i, int i2) {
        this.statusBarView.updateStatus(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.statusBarView.onPause();
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mRs485Receiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getState(EventState eventState) {
        this.statusBarView.setIconState(eventState);
    }

    private static class Rs485Receiver extends ZKReceiver<ZKPushConnectStatusLauncher> {
        public Rs485Receiver(ZKPushConnectStatusLauncher zKPushConnectStatusLauncher) {
            super(zKPushConnectStatusLauncher);
        }

        public void onReceive(ZKPushConnectStatusLauncher zKPushConnectStatusLauncher, Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(ZKPushConnectStatusLauncher.TAG, "onReceive: " + action);
            if (action.equals(ZKPushConnectStatusLauncher.ACTION_RS485_BUZZER)) {
                intent.getByteExtra("reader", (byte) 0);
                intent.getByteExtra("toneCode", (byte) 0);
                intent.getByteExtra("onCount", (byte) 0);
                intent.getByteExtra("offCount", (byte) 0);
                SpeakerHelper.playSound(zKPushConnectStatusLauncher.mContext, "buzzer.wav", false, "", 3);
            } else if (action.equals(ZKPushConnectStatusLauncher.ACTION_RS485_LED)) {
                Bundle bundleExtra = intent.getBundleExtra("temparary");
                bundleExtra.getByte("controlCode");
                bundleExtra.getByte("onCount");
                bundleExtra.getByte("offCount");
                bundleExtra.getByte("onColor");
                bundleExtra.getByte("offColor");
                bundleExtra.getByte("timer100Ms");
                Bundle bundleExtra2 = intent.getBundleExtra("permanent");
                bundleExtra2.getByte("controlCode");
                bundleExtra2.getByte("onCount");
                bundleExtra2.getByte("offCount");
                bundleExtra2.getByte("onColor");
                bundleExtra2.getByte("offColor");
                bundleExtra2.getByte("timer100Ms");
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 0.5f);
                alphaAnimation.setDuration(1000);
                alphaAnimation.setRepeatCount(1);
                alphaAnimation.setRepeatMode(2);
                zKPushConnectStatusLauncher.findViewById(R.id.ll_led_alarm).setAnimation(alphaAnimation);
                alphaAnimation.start();
            } else if (action.equals(ZKPushConnectStatusLauncher.ACTION_RS485_TEXT)) {
                intent.getByteExtra("reader", (byte) 0);
                intent.getByteExtra("cmd", (byte) 0);
                intent.getByteExtra("tempTime", (byte) 0);
                intent.getByteExtra("offsetRow", (byte) 0);
                intent.getByteExtra("offsetCol", (byte) 0);
                intent.getByteExtra(LoggingEvents.VoiceIme.EXTRA_TEXT_MODIFIED_LENGTH, (byte) 0);
                intent.getByteArrayExtra("data");
                zKPushConnectStatusLauncher.findViewById(R.id.tv_rs485_alarm_text).setVisibility(0);
                ((TextView) zKPushConnectStatusLauncher.findViewById(R.id.tv_rs485_alarm_text)).setText("出现文字RS485");
                final ZKPushConnectStatusLauncher zKPushConnectStatusLauncher2 = zKPushConnectStatusLauncher;
                new CountDownTimer(5000, 1000) {
                    public void onTick(long j) {
                    }

                    public void onFinish() {
                        zKPushConnectStatusLauncher2.findViewById(R.id.tv_rs485_alarm_text).setVisibility(8);
                        ((TextView) zKPushConnectStatusLauncher2.findViewById(R.id.tv_rs485_alarm_text)).setText("");
                    }
                }.start();
            }
        }
    }
}
