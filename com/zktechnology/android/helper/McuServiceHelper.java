package com.zktechnology.android.helper;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.edk.mcu.lib.IRs232Listener;
import com.zkteco.edk.mcu.lib.IRs485Listener;
import com.zkteco.edk.mcu.lib.IWiegandReaderListener;
import com.zkteco.edk.mcu.lib.IZkServiceConnectionCallback;
import com.zkteco.edk.mcu.lib.ZkMcuManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class McuServiceHelper {
    private static final String TAG = "McuServiceHelper";
    private final Context mContext;
    /* access modifiers changed from: private */
    public final AtomicBoolean mDisconnect;
    private List<OnMcuReadListener> mListeners;

    public final boolean sentRS485Data(byte[] bArr) {
        try {
            return ZkMcuManager.getInstance().sentRS485Data(bArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean setRS485Properties(int i, int i2, int i3, int i4) {
        try {
            return ZkMcuManager.getInstance().setRS485Properties(i, i2, i3, i4);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final String getSerialNumber() {
        try {
            return ZkMcuManager.getInstance().getSerialNumber();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setWiegandOutProperty(int i, int i2, int i3) {
        try {
            return ZkMcuManager.getInstance().setWiegandOutProperty(i, i2, i3);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean sentWiegandData(byte[] bArr) {
        try {
            return ZkMcuManager.getInstance().sentWiegandData(bArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    static class SingletonHolder {
        static final McuServiceHelper INSTANCE = new McuServiceHelper();

        SingletonHolder() {
        }
    }

    private McuServiceHelper() {
        this.mDisconnect = new AtomicBoolean();
        this.mContext = LauncherApplication.getLauncherApplicationContext().getApplicationContext();
    }

    public static McuServiceHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public final void init() {
        this.mDisconnect.compareAndSet(true, false);
        Log.d(TAG, "initMcu: [start init mcu]");
        ZkMcuManager.getInstance().setRs232Listener(new IRs232Listener() {
            public final void onRs232Detected(byte[] bArr) {
                McuServiceHelper.this.lambda$init$0$McuServiceHelper(bArr);
            }
        });
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ZkMcuManager.getInstance().setRs485Listener(new IRs485Listener() {
            public final void onRs485Detected(byte[] bArr) {
                McuServiceHelper.this.onRs485Read(bArr);
            }
        });
        ZkMcuManager.getInstance().setWiegandListener(new IWiegandReaderListener() {
            public final void onWiegandRead(String str) {
                McuServiceHelper.this.onWiegandRead(str);
            }
        });
        ZkMcuManager.getInstance().setServiceConnectionCallback(new IZkServiceConnectionCallback() {
            public void onServiceConnected(ComponentName componentName) {
                countDownLatch.countDown();
            }

            public void onServiceDisconnected(ComponentName componentName) {
                if (!McuServiceHelper.this.mDisconnect.get()) {
                    ZkThreadPoolManager.getInstance().execute(new Runnable() {
                        public final void run() {
                            McuServiceHelper.this.init();
                        }
                    });
                }
            }
        });
        int connectService = ZkMcuManager.getInstance().connectService(this.mContext);
        Log.d(TAG, String.format("initMcu: [bindService result %d]", new Object[]{Integer.valueOf(connectService)}));
        if (connectService == 1) {
            try {
                countDownLatch.await(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public /* synthetic */ void lambda$init$0$McuServiceHelper(byte[] bArr) {
        List<OnMcuReadListener> list = this.mListeners;
        if (list != null) {
            for (OnMcuReadListener onRs232Read : list) {
                onRs232Read.onRs232Read(bArr);
            }
        }
    }

    /* access modifiers changed from: private */
    public void onWiegandRead(String str) {
        Log.d(TAG, "Wiegand: " + Thread.currentThread().getName());
        List<OnMcuReadListener> list = this.mListeners;
        if (list != null) {
            for (OnMcuReadListener onWiegandRead : list) {
                onWiegandRead.onWiegandRead(str);
            }
        }
    }

    /* access modifiers changed from: private */
    public void onRs485Read(byte[] bArr) {
        Log.d(TAG, "rs485: " + Thread.currentThread().getName());
        List<OnMcuReadListener> list = this.mListeners;
        if (list != null) {
            for (OnMcuReadListener onRs485Read : list) {
                onRs485Read.onRs485Read(bArr);
            }
        }
    }

    public final void addOnMcuReadListener(OnMcuReadListener onMcuReadListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(onMcuReadListener);
    }

    public final void removeOnMcuReadListener(OnMcuReadListener onMcuReadListener) {
        List<OnMcuReadListener> list = this.mListeners;
        if (list != null) {
            list.remove(onMcuReadListener);
        }
    }

    public final void disconnect() {
        this.mDisconnect.compareAndSet(false, true);
        ZkMcuManager.getInstance().setServiceConnectionCallback((IZkServiceConnectionCallback) null);
        ZkMcuManager.getInstance().disconnectService(this.mContext);
        ZkMcuManager.getInstance().setRs232Listener((IRs232Listener) null);
        ZkMcuManager.getInstance().setRs485Listener((IRs485Listener) null);
        ZkMcuManager.getInstance().setWiegandListener((IWiegandReaderListener) null);
    }
}
