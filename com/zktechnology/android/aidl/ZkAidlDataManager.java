package com.zktechnology.android.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.zkteco.android.core.IDataManagerInterface;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZkAidlDataManager {
    private static final String EDK_SERVICE_APPLICATION_NAME = "com.zkteco.android.core";
    private static final String EDK_SERVICE_NAME = "com.zkteco.android.core.ZkDataManagerService";
    private static volatile ZkAidlDataManager mInstance;
    /* access modifiers changed from: private */
    public volatile IDataManagerInterface mDataManager;
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnectServiceSuccess = new AtomicBoolean(false);
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IDataManagerInterface unused = ZkAidlDataManager.this.mDataManager = IDataManagerInterface.Stub.asInterface(iBinder);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            IDataManagerInterface unused = ZkAidlDataManager.this.mDataManager = null;
        }

        public void onBindingDied(ComponentName componentName) {
            IDataManagerInterface unused = ZkAidlDataManager.this.mDataManager = null;
            ZkAidlDataManager.this.mIsConnectServiceSuccess.set(false);
        }
    };

    private ZkAidlDataManager() {
    }

    public static ZkAidlDataManager getInstance() {
        if (mInstance == null) {
            synchronized (ZkAidlDataManager.class) {
                if (mInstance == null) {
                    mInstance = new ZkAidlDataManager();
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

    private boolean isConnectService() {
        return this.mDataManager != null && this.mDataManager.asBinder().isBinderAlive() && this.mIsConnectServiceSuccess.get();
    }

    public int getIntOption(String str, int i) {
        if (!isConnectService() || TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            return this.mDataManager.getIntOption(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getStrOption(String str, String str2) {
        if (!isConnectService() || TextUtils.isEmpty(str)) {
            return str2;
        }
        try {
            return this.mDataManager.getStrOption(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return str2;
        }
    }

    public int setIntOption(String str, int i) {
        if (!isConnectService() || TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            return this.mDataManager.setIntOption(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int setStrOption(String str, String str2) {
        if (!isConnectService() || TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            return this.mDataManager.setStrOption(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
