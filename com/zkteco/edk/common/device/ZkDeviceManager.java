package com.zkteco.edk.common.device;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.zkteco.edk.common.device.IAidlDeviceInterface;
import com.zkteco.edk.common.device.IAidlDeviceStateListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ZkDeviceManager {
    private static final String EDK_SERVICE_APPLICATION_NAME = "com.zkteco.edk.common.device.service";
    private static final String EDK_SERVICE_NAME = "com.zkteco.edk.common.device.service.ZkDeviceService";
    private static volatile ZkDeviceManager mInstance;
    /* access modifiers changed from: private */
    public final IAidlDeviceStateListener mAidlDeviceStateListener = new IAidlDeviceStateListener.Stub() {
        public void onDeviceStateChange(int i, String str) throws RemoteException {
            if (ZkDeviceManager.this.mDeviceStateListener != null) {
                ZkDeviceManager.this.mDeviceStateListener.onDeviceStateChange(i, str);
            }
        }
    };
    /* access modifiers changed from: private */
    public volatile IAidlDeviceInterface mDeviceInterface;
    /* access modifiers changed from: private */
    public IZkDeviceStateListener mDeviceStateListener;
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnectServiceSuccess = new AtomicBoolean(false);
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IAidlDeviceInterface unused = ZkDeviceManager.this.mDeviceInterface = IAidlDeviceInterface.Stub.asInterface(iBinder);
            try {
                ZkDeviceManager.this.mDeviceInterface.addDeviceStateListener(ZkDeviceManager.this.mAidlDeviceStateListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            try {
                ZkDeviceManager.this.mDeviceInterface.removeDeviceStateListener(ZkDeviceManager.this.mAidlDeviceStateListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            IAidlDeviceInterface unused = ZkDeviceManager.this.mDeviceInterface = null;
        }

        public void onBindingDied(ComponentName componentName) {
            IAidlDeviceInterface unused = ZkDeviceManager.this.mDeviceInterface = null;
            ZkDeviceManager.this.mIsConnectServiceSuccess.set(false);
        }
    };

    private ZkDeviceManager() {
    }

    public void setDeviceStateListener(IZkDeviceStateListener iZkDeviceStateListener) {
        this.mDeviceStateListener = iZkDeviceStateListener;
    }

    public static ZkDeviceManager getInstance() {
        if (mInstance == null) {
            synchronized (ZkDeviceManager.class) {
                if (mInstance == null) {
                    mInstance = new ZkDeviceManager();
                }
            }
        }
        return mInstance;
    }

    public int bindService(Context context) {
        if (context == null) {
            return -1003;
        }
        if (this.mIsConnectServiceSuccess.get()) {
            return 0;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(EDK_SERVICE_APPLICATION_NAME, EDK_SERVICE_NAME));
        boolean bindService = context.getApplicationContext().bindService(intent, this.mServiceConnection, 1);
        this.mIsConnectServiceSuccess.set(bindService);
        if (bindService) {
            return 0;
        }
        return -1001;
    }

    public int unbindService(Context context) {
        if (!this.mIsConnectServiceSuccess.get()) {
            return 0;
        }
        if (context == null) {
            return -1003;
        }
        context.getApplicationContext().unbindService(this.mServiceConnection);
        this.mIsConnectServiceSuccess.set(false);
        return 0;
    }

    public int queryOnlineDevice(List<String> list) {
        if (!isConnectService()) {
            return -1001;
        }
        if (list == null) {
            return -1003;
        }
        try {
            this.mDeviceInterface.queryOnlineDevice(list);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1001;
        }
    }

    private boolean isConnectService() {
        return this.mDeviceInterface != null && this.mDeviceInterface.asBinder().isBinderAlive() && this.mIsConnectServiceSuccess.get();
    }
}
