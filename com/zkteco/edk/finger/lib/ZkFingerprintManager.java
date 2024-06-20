package com.zkteco.edk.finger.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Base64;
import com.zkteco.edk.finger.lib.IAidlFingerprintInterface;
import com.zkteco.edk.finger.lib.IAidlFingerprintListener;
import com.zkteco.edk.finger.lib.bean.ZkFingerprintLogResult;
import com.zkteco.edk.finger.lib.bean.ZkFingerprintTemplateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ZkFingerprintManager {
    private static final String EDK_SERVICE_APPLICATION_NAME = "com.zkteco.edk.finger.service";
    private static final String EDK_SERVICE_NAME = "com.zkteco.edk.finger.service.aidl.ZkFingerprintService";
    private static ZkFingerprintManager mInstance;
    /* access modifiers changed from: private */
    public List<IFingerprintVerifyListener> mFingerprintListener = new ArrayList();
    /* access modifiers changed from: private */
    public IAidlFingerprintInterface mFpAidlInterface;
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnectedService = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public final IAidlFingerprintListener.Stub mSensorListener = new IAidlFingerprintListener.Stub() {
        public void onFingerprintStateCallback(int i) {
            List<IFingerprintVerifyListener> access$300 = ZkFingerprintManager.this.mFingerprintListener;
            if (access$300 != null) {
                for (IFingerprintVerifyListener onFingerprintStateCallback : access$300) {
                    onFingerprintStateCallback.onFingerprintStateCallback(i);
                }
            }
        }

        public void onFingerprintSensorCaptureSuccess(byte[] bArr, int i, int i2) {
            List<IFingerprintVerifyListener> access$300 = ZkFingerprintManager.this.mFingerprintListener;
            if (access$300 != null) {
                for (IFingerprintVerifyListener onFingerprintCaptureSuccess : access$300) {
                    onFingerprintCaptureSuccess.onFingerprintCaptureSuccess(bArr, i, i2);
                }
            }
        }

        public void onFingerprintSensorCaptureError(String str) {
            List<IFingerprintVerifyListener> access$300 = ZkFingerprintManager.this.mFingerprintListener;
            if (access$300 != null) {
                for (IFingerprintVerifyListener onFingerprintCaptureError : access$300) {
                    onFingerprintCaptureError.onFingerprintCaptureError(str);
                }
            }
        }

        public void onFingerprintSensorExtractSuccess(byte[] bArr) {
            List<IFingerprintVerifyListener> access$300 = ZkFingerprintManager.this.mFingerprintListener;
            if (access$300 != null) {
                for (IFingerprintVerifyListener onFingerprintSensorExtractSuccess : access$300) {
                    onFingerprintSensorExtractSuccess.onFingerprintSensorExtractSuccess(bArr);
                }
            }
        }

        public void onFingerprintSensorExtractFail(int i) {
            List<IFingerprintVerifyListener> access$300 = ZkFingerprintManager.this.mFingerprintListener;
            if (access$300 != null) {
                for (IFingerprintVerifyListener onFingerprintExtractFail : access$300) {
                    onFingerprintExtractFail.onFingerprintExtractFail(i);
                }
            }
        }
    };
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IAidlFingerprintInterface unused = ZkFingerprintManager.this.mFpAidlInterface = IAidlFingerprintInterface.Stub.asInterface(iBinder);
            ZkFingerprintManager.this.mIsConnectedService.set(true);
            try {
                ZkFingerprintManager.this.mFpAidlInterface.addFingerprintListener(ZkFingerprintManager.this.mSensorListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (ZkFingerprintManager.this.mFpAidlInterface != null) {
                try {
                    ZkFingerprintManager.this.mFpAidlInterface.removeFingerprintListener(ZkFingerprintManager.this.mSensorListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            ZkFingerprintManager.this.mIsConnectedService.set(false);
        }
    };

    private ZkFingerprintManager() {
    }

    public static ZkFingerprintManager getInstance() {
        if (mInstance == null) {
            synchronized (ZkFingerprintManager.class) {
                if (mInstance == null) {
                    mInstance = new ZkFingerprintManager();
                }
            }
        }
        return mInstance;
    }

    public int bindService(Context context) {
        Objects.requireNonNull(context, "connectService context not be null!");
        if (this.mIsConnectedService.get()) {
            return 0;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(EDK_SERVICE_APPLICATION_NAME, EDK_SERVICE_NAME));
        if (context.getApplicationContext().bindService(intent, this.mServiceConnection, 1)) {
            return 0;
        }
        return -1000;
    }

    public int unbindService(Context context) {
        if (context == null) {
            return -1004;
        }
        if (!isConnectService()) {
            return 0;
        }
        try {
            this.mFingerprintListener = null;
            if (this.mIsConnectedService.get()) {
                IAidlFingerprintInterface iAidlFingerprintInterface = this.mFpAidlInterface;
                if (iAidlFingerprintInterface != null) {
                    iAidlFingerprintInterface.removeFingerprintListener(this.mSensorListener);
                }
                context.getApplicationContext().unbindService(this.mServiceConnection);
                this.mFpAidlInterface = null;
                this.mIsConnectedService.set(false);
            }
            return 0;
        } catch (Exception unused) {
            return -1000;
        }
    }

    public int setParameter(int i) {
        if (!isConnectService()) {
            return -1001;
        }
        try {
            return this.mFpAidlInterface.setParameter(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public void addFingerprintListener(IFingerprintVerifyListener iFingerprintVerifyListener) {
        if (this.mFingerprintListener == null) {
            this.mFingerprintListener = new ArrayList();
        }
        if (!this.mFingerprintListener.contains(iFingerprintVerifyListener)) {
            this.mFingerprintListener.add(iFingerprintVerifyListener);
        }
    }

    public void removeFingerprintListener() {
        List<IFingerprintVerifyListener> list = this.mFingerprintListener;
        if (list != null) {
            list.clear();
        }
    }

    public void removeFingerprintListener(IFingerprintVerifyListener iFingerprintVerifyListener) {
        List<IFingerprintVerifyListener> list = this.mFingerprintListener;
        if (list != null) {
            list.remove(iFingerprintVerifyListener);
        }
    }

    public int enroll(String str, List<byte[]> list, byte[] bArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (isEmptyByteArray(bArr) || TextUtils.isEmpty(str) || list == null) {
            return -1004;
        }
        try {
            ArrayList arrayList = new ArrayList();
            for (byte[] encodeToString : list) {
                arrayList.add(Base64.encodeToString(encodeToString, 0));
            }
            return this.mFpAidlInterface.enroll(str, arrayList, bArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int extract(byte[] bArr, int i, int i2, byte[] bArr2, int[] iArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (isEmptyByteArray(bArr) || isEmptyByteArray(bArr2) || isEmptyIntArray(iArr)) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.extract(bArr, i, i2, bArr2, iArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int identify(byte[] bArr, int i, String[] strArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (bArr == null || bArr.length == 0 || strArr == null || strArr.length < 1) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.identify(bArr, i, strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int merge(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        if (!isConnectService()) {
            return -1001;
        }
        if (isEmptyByteArray(bArr) || isEmptyByteArray(bArr2) || isEmptyByteArray(bArr3) || isEmptyByteArray(bArr4)) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.merge(bArr, bArr2, bArr3, bArr4);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int insertTemplate(String str, byte[] bArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (bArr == null || bArr.length == 0 || TextUtils.isEmpty(str)) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.saveTemplate(str, bArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int deleteTemplate(String str) {
        if (!isConnectService()) {
            return -1001;
        }
        if (TextUtils.isEmpty(str)) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.deleteTemplate(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int verify(byte[] bArr, byte[] bArr2, int i) {
        if (!isConnectService()) {
            return -1001;
        }
        if (isEmptyByteArray(bArr) || isEmptyByteArray(bArr2)) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.verify(bArr, bArr2, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int clear() {
        if (!isConnectService()) {
            return -1001;
        }
        try {
            return this.mFpAidlInterface.clearTemplate();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    private boolean isEmptyByteArray(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    private boolean isEmptyIntArray(int[] iArr) {
        return iArr == null || iArr.length == 0;
    }

    private boolean isConnectService() {
        IAidlFingerprintInterface iAidlFingerprintInterface = this.mFpAidlInterface;
        return iAidlFingerprintInterface != null && iAidlFingerprintInterface.asBinder().isBinderAlive() && this.mIsConnectedService.get();
    }

    public int queryOnlineDevice(List<String> list) {
        if (!isConnectService()) {
            return -1001;
        }
        if (list == null) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.queryOnlineDevice(list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int getAlgorithmVersion(String str, String[] strArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (TextUtils.isEmpty(str) || strArr == null || strArr.length < 1) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.getAlgorithmVersion(str, strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int getFirmwareVersion(String str, String[] strArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (TextUtils.isEmpty(str) || strArr == null || strArr.length < 1) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.getFirmwareVersion(str, strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int insertFingerprintVerifyLog(String str) {
        if (!isConnectService()) {
            return -1001;
        }
        if (TextUtils.isEmpty(str)) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.insertFingerprintVerifyLog(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int queryFingerprintVerifyLog(String str, String str2, String str3, long j, long j2, List<ZkFingerprintLogResult> list) {
        if (!isConnectService()) {
            return -1001;
        }
        if (list == null) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.queryFingerprintVerifyLog(str, str2, str3, j, j2, list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int likeQueryFingerprintTemplate(String str, long j, long j2, List<ZkFingerprintTemplateResult> list) {
        if (!isConnectService()) {
            return -1001;
        }
        if (list == null) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.likeQueryFingerprintTemplate(str, j, j2, list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int queryFingerprintTemplate(String str, List<ZkFingerprintTemplateResult> list) {
        if (!isConnectService()) {
            return -1001;
        }
        if (list == null) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.queryFingerprintTemplate(str, list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int deleteFingerprintVerifyLog(String str, String str2) {
        if (!isConnectService()) {
            return -1001;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.deleteFingerprintVerifyLog(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int isInit() {
        if (!isConnectService()) {
            return -1001;
        }
        try {
            return this.mFpAidlInterface.isInit();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }

    public int queryTemplateCount(int[] iArr) {
        if (!isConnectService()) {
            return -1001;
        }
        if (iArr == null || iArr.length <= 0) {
            return -1004;
        }
        try {
            return this.mFpAidlInterface.queryTemplateCount(iArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1002;
        }
    }
}
