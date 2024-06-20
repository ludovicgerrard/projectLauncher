package com.zktechnology.android.view.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.strategy.MainThreadExecutor;
import java.util.ArrayList;
import java.util.List;

public class ServerConnectStateController extends BroadcastReceiver {
    public static final String ACTION_CONNECT_STATE = "com.zkteco.android.core.ACTION_CONNECT_STATE";
    private boolean isPushing = false;
    private final OnServerConnectStateChangedListener listener;
    private List<Integer> stateList = new ArrayList();

    public interface OnServerConnectStateChangedListener {
        void onServerConnectStateChanged(int i, boolean z);
    }

    public ServerConnectStateController(OnServerConnectStateChangedListener onServerConnectStateChangedListener) {
        this.listener = onServerConnectStateChangedListener;
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CONNECT_STATE);
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public void onReceive(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("ConnectState", 0);
        FileLogUtils.writeStateLog("launcher get ConnectState: " + intExtra);
        if (!this.isPushing) {
            this.listener.onServerConnectStateChanged(intExtra, true);
            if (2 == intExtra) {
                this.isPushing = true;
                MainThreadExecutor.getInstance().executeDelayed(new Runnable(context) {
                    public final /* synthetic */ Context f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        ServerConnectStateController.this.lambda$onReceive$0$ServerConnectStateController(this.f$1);
                    }
                }, 1000);
                return;
            }
            return;
        }
        this.stateList.add(Integer.valueOf(intExtra));
        this.listener.onServerConnectStateChanged(intExtra, false);
    }

    /* access modifiers changed from: private */
    /* renamed from: changeStateReceiver */
    public void lambda$onReceive$0$ServerConnectStateController(Context context) {
        this.isPushing = false;
        if (this.stateList.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(ACTION_CONNECT_STATE);
            List<Integer> list = this.stateList;
            intent.putExtra("ConnectState", list.get(list.size() - 1));
            StringBuilder append = new StringBuilder().append("launcher send ConnectState: ");
            List<Integer> list2 = this.stateList;
            FileLogUtils.writeStateLog(append.append(list2.get(list2.size() - 1)).toString());
            context.sendBroadcast(intent);
        }
        this.stateList.clear();
    }
}
