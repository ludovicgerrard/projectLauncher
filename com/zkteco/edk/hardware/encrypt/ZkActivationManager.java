package com.zkteco.edk.hardware.encrypt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ZkActivationManager {
    private static final String EDK_SERVICE_APPLICATION_NAME = "com.zkteco.edk.hardware.encrypt.service";
    private static final String EDK_SERVICE_NAME = "com.zkteco.edk.hardware.encrypt.service.ZkCheckActivationService";
    private static volatile ZkActivationManager mInstance;
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnectServiceSuccess = new AtomicBoolean(false);
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ZkActivationManager.this.mIsConnectServiceSuccess.set(true);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            ZkActivationManager.this.mIsConnectServiceSuccess.set(false);
        }

        public void onBindingDied(ComponentName componentName) {
            ZkActivationManager.this.mIsConnectServiceSuccess.set(false);
        }
    };

    private ZkActivationManager() {
    }

    public static ZkActivationManager getInstance() {
        if (mInstance == null) {
            synchronized (ZkActivationManager.class) {
                if (mInstance == null) {
                    mInstance = new ZkActivationManager();
                }
            }
        }
        return mInstance;
    }

    public int bindService(Context context) {
        if (context == null || this.mIsConnectServiceSuccess.get()) {
            return -1;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(EDK_SERVICE_APPLICATION_NAME, EDK_SERVICE_NAME));
        boolean bindService = context.getApplicationContext().bindService(intent, this.mServiceConnection, 1);
        this.mIsConnectServiceSuccess.set(bindService);
        if (bindService) {
            return 0;
        }
        return -1;
    }

    public int unbindService(Context context) {
        if (!this.mIsConnectServiceSuccess.get()) {
            return 0;
        }
        if (context == null) {
            return -1;
        }
        context.getApplicationContext().unbindService(this.mServiceConnection);
        this.mIsConnectServiceSuccess.set(false);
        return 0;
    }

    public boolean isConnectService() {
        return this.mIsConnectServiceSuccess.get();
    }
}
