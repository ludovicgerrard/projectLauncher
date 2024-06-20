package com.zkteco.edk.qrcode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.zkteco.edk.qrcode.IZkOnQrCodeScanListener;
import com.zkteco.edk.qrcode.IZkQrCodeScannerInterface;
import com.zkteco.edk.qrcode.IZkQrCodeStateListener;
import com.zkteco.edk.qrcode.ZkQrCodeManager;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ZkQrCodeManager {
    private static final String EDK_SERVICE_APPLICATION_NAME = "com.zkteco.edk.qrcode.service";
    private static final String EDK_SERVICE_NAME = "com.zkteco.edk.qrcode.service.ZkQrCodeService";
    private static volatile ZkQrCodeManager mSingleton;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnected = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public IZkQrCodeScanCallback mQRCodeScanCallback;
    /* access modifiers changed from: private */
    public IZkQrCodeScannerInterface mQRCodeScannerInterface;
    /* access modifiers changed from: private */
    public final IZkQrCodeStateListener mQRCodeStateListener = new IZkQrCodeStateListener.Stub() {
        public void onQrCodeStateListener(String str, int i) throws RemoteException {
            synchronized (ZkQrCodeManager.class) {
                if (ZkQrCodeManager.this.mQrCodeStateCallback != null) {
                    ZkQrCodeManager.this.mHandler.post(new Runnable(str, i) {
                        public final /* synthetic */ String f$1;
                        public final /* synthetic */ int f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            ZkQrCodeManager.AnonymousClass3.this.lambda$onQrCodeStateListener$0$ZkQrCodeManager$3(this.f$1, this.f$2);
                        }
                    });
                }
            }
        }

        public /* synthetic */ void lambda$onQrCodeStateListener$0$ZkQrCodeManager$3(String str, int i) {
            ZkQrCodeManager.this.mQrCodeStateCallback.onQrCodeState(str, i);
        }
    };
    /* access modifiers changed from: private */
    public IZkQrCodeStateCallback mQrCodeStateCallback;
    /* access modifiers changed from: private */
    public final IZkOnQrCodeScanListener mScanResultListener = new IZkOnQrCodeScanListener.Stub() {
        public void onScanQrCode(String str, String str2) {
            synchronized (ZkQrCodeManager.class) {
                if (ZkQrCodeManager.this.mQRCodeScanCallback != null) {
                    ZkQrCodeManager.this.mHandler.post(new Runnable(str, str2) {
                        public final /* synthetic */ String f$1;
                        public final /* synthetic */ String f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            ZkQrCodeManager.AnonymousClass2.this.lambda$onScanQrCode$0$ZkQrCodeManager$2(this.f$1, this.f$2);
                        }
                    });
                }
            }
        }

        public /* synthetic */ void lambda$onScanQrCode$0$ZkQrCodeManager$2(String str, String str2) {
            ZkQrCodeManager.this.mQRCodeScanCallback.onScanQrCode(str, str2);
        }
    };
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IZkQrCodeScannerInterface unused = ZkQrCodeManager.this.mQRCodeScannerInterface = IZkQrCodeScannerInterface.Stub.asInterface(iBinder);
            try {
                if (ZkQrCodeManager.this.isConnectService()) {
                    ZkQrCodeManager.this.mQRCodeScannerInterface.registerQrCodeScanListener(ZkQrCodeManager.this.mScanResultListener);
                    ZkQrCodeManager.this.mQRCodeScannerInterface.registerQrCodeStateListener(ZkQrCodeManager.this.mQRCodeStateListener);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            try {
                if (ZkQrCodeManager.this.isConnectService()) {
                    ZkQrCodeManager.this.mQRCodeScannerInterface.unRegisterQrCodeScanListener(ZkQrCodeManager.this.mScanResultListener);
                    ZkQrCodeManager.this.mQRCodeScannerInterface.unRegisterQrCodeStateListener(ZkQrCodeManager.this.mQRCodeStateListener);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            ZkQrCodeManager.this.mIsConnected.set(false);
            IZkQrCodeScannerInterface unused = ZkQrCodeManager.this.mQRCodeScannerInterface = null;
        }

        public void onBindingDied(ComponentName componentName) {
            ZkQrCodeManager.this.mIsConnected.set(false);
        }
    };

    public static ZkQrCodeManager getInstance() {
        if (mSingleton == null) {
            synchronized (ZkQrCodeManager.class) {
                if (mSingleton == null) {
                    mSingleton = new ZkQrCodeManager();
                }
            }
        }
        return mSingleton;
    }

    public void setQRCodeScanCallback(IZkQrCodeScanCallback iZkQrCodeScanCallback) {
        this.mQRCodeScanCallback = iZkQrCodeScanCallback;
    }

    public void setQRCodeStateCallback(IZkQrCodeStateCallback iZkQrCodeStateCallback) {
        this.mQrCodeStateCallback = iZkQrCodeStateCallback;
    }

    /* access modifiers changed from: private */
    public boolean isConnectService() {
        IZkQrCodeScannerInterface iZkQrCodeScannerInterface = this.mQRCodeScannerInterface;
        return iZkQrCodeScannerInterface != null && iZkQrCodeScannerInterface.asBinder().isBinderAlive();
    }

    public int bindService(Context context) {
        if (this.mIsConnected.get()) {
            return 0;
        }
        if (context == null) {
            return -1004;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(EDK_SERVICE_APPLICATION_NAME, EDK_SERVICE_NAME));
        boolean bindService = context.bindService(intent, this.mServiceConnection, 1);
        this.mIsConnected.set(bindService);
        if (bindService) {
            return 0;
        }
        return -1000;
    }

    public int unbindService(Context context) {
        if (!this.mIsConnected.get()) {
            return 0;
        }
        if (context == null) {
            return -1004;
        }
        context.unbindService(this.mServiceConnection);
        this.mIsConnected.set(false);
        return 0;
    }

    private boolean isBindService() {
        IZkQrCodeScannerInterface iZkQrCodeScannerInterface = this.mQRCodeScannerInterface;
        return iZkQrCodeScannerInterface != null && iZkQrCodeScannerInterface.asBinder().isBinderAlive() && this.mIsConnected.get();
    }

    public int queryOnlineDevice(List<String> list) {
        if (list == null) {
            return -1004;
        }
        if (!isBindService()) {
            return -1001;
        }
        try {
            this.mQRCodeScannerInterface.queryOnlineDevice(list);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public int openDevice() {
        if (!isBindService()) {
            return -1001;
        }
        try {
            return this.mQRCodeScannerInterface.openDevice();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1000;
        }
    }
}
