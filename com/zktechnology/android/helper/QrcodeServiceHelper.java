package com.zktechnology.android.helper;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zkteco.edk.qrcode.IZkQrCodeScanCallback;
import com.zkteco.edk.qrcode.IZkQrCodeStateCallback;
import com.zkteco.edk.qrcode.ZkQrCodeManager;
import java.util.ArrayList;
import java.util.List;

public class QrcodeServiceHelper {
    private static final String TAG = "QrcodeServiceHelper";
    private final Context mContext;
    private List<OnQrcodeReadListener> mListeners;

    static class SingletonHolder {
        static final QrcodeServiceHelper INSTANCE = new QrcodeServiceHelper();

        SingletonHolder() {
        }
    }

    private QrcodeServiceHelper() {
        this.mContext = LauncherApplication.getLauncherApplicationContext().getApplicationContext();
    }

    public static QrcodeServiceHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public final void init() {
        Log.d(TAG, "initQrcode: [start init qrcode]");
        ZkQrCodeManager.getInstance().setQRCodeStateCallback($$Lambda$QrcodeServiceHelper$C3JtM_9ACVQkTnUH3LQW3tkXho.INSTANCE);
        ZkQrCodeManager.getInstance().setQRCodeScanCallback(new IZkQrCodeScanCallback() {
            public final void onScanQrCode(String str, String str2) {
                QrcodeServiceHelper.this.lambda$init$1$QrcodeServiceHelper(str, str2);
            }
        });
        int bindService = ZkQrCodeManager.getInstance().bindService(this.mContext);
        Log.d(TAG, String.format("initQrcode: [bindService result %d]", new Object[]{Integer.valueOf(bindService)}));
        if (bindService == 0) {
            SystemClock.sleep(3000);
            ArrayList arrayList = new ArrayList();
            if (ZkQrCodeManager.getInstance().queryOnlineDevice(arrayList) != 0 || arrayList.isEmpty()) {
                Log.e(TAG, "未连接二维码设备");
            } else {
                Log.d(TAG, "已连接二维码设备: " + ((String) arrayList.get(0)));
            }
        }
    }

    public /* synthetic */ void lambda$init$1$QrcodeServiceHelper(String str, String str2) {
        Log.d(TAG, "接收到二维码数据: " + str2 + ", current->" + Thread.currentThread().getName());
        List<OnQrcodeReadListener> list = this.mListeners;
        if (list != null) {
            for (OnQrcodeReadListener onQrcodeRead : list) {
                onQrcodeRead.onQrcodeRead(str2);
            }
        }
    }

    public final void addOnQrcodeReadListener(OnQrcodeReadListener onQrcodeReadListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(onQrcodeReadListener);
    }

    public final void removeOnQrcodeReadListener(OnQrcodeReadListener onQrcodeReadListener) {
        List<OnQrcodeReadListener> list = this.mListeners;
        if (list != null) {
            list.remove(onQrcodeReadListener);
        }
    }

    public final void openDevice() {
        ZkQrCodeManager.getInstance().openDevice();
    }

    public final void disconnect() {
        ZkQrCodeManager.getInstance().setQRCodeStateCallback((IZkQrCodeStateCallback) null);
        ZkQrCodeManager.getInstance().setQRCodeScanCallback((IZkQrCodeScanCallback) null);
        ZkQrCodeManager.getInstance().unbindService(this.mContext);
    }
}
