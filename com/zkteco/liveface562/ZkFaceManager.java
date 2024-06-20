package com.zkteco.liveface562;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import com.zkteco.liveface562.IFaceEdkService;
import com.zkteco.liveface562.IFaceInitListener;
import com.zkteco.liveface562.bean.AgeAndGender;
import com.zkteco.liveface562.bean.IdentifyInfo;
import com.zkteco.liveface562.bean.LivenessResult;
import com.zkteco.liveface562.bean.TopKIdentifyInfo;
import com.zkteco.liveface562.bean.ZkCompareResult;
import com.zkteco.liveface562.bean.ZkDetectInfo;
import com.zkteco.liveface562.bean.ZkExtractResult;
import com.zkteco.liveface562.bean.ZkFaceConfig;
import com.zkteco.liveface562.bean.ZkFaceTemplateResult;
import com.zkteco.liveface562.common.ZkFaceStateCallback;
import com.zkteco.liveface562.util.ZkLogUtil;
import com.zkteco.liveface562.util.ZkShareMemoryUtil;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ZkFaceManager {
    private static final String EDK_SERVICE_APPLICATION_NAME = "com.zkteco.edk.face.service";
    private static final String EDK_SERVICE_NAME = "com.zkteco.edk.face.service.ZkFaceService";
    private static final int MAX_PER_QUERY_DATABASE_LIMIT_COUNT = 100;
    private static final int PIN_LIMIT_LENGTH = 24;
    private static volatile ZkFaceManager mInstance;
    /* access modifiers changed from: private */
    public volatile IFaceEdkService mFaceEdkService;
    /* access modifiers changed from: private */
    public final IFaceInitListener mFaceInitListener = new IFaceInitListener.Stub() {
        public void onInitCallback(boolean z) throws RemoteException {
            if (z) {
                if (ZkFaceManager.this.mFaceStateCallback != null) {
                    ZkFaceManager.this.mFaceStateCallback.onInitState(1);
                }
                int unused = ZkFaceManager.this.mRetryInitFaceCount = 0;
                return;
            }
            if (ZkFaceManager.this.mFaceStateCallback != null) {
                ZkFaceManager.this.mFaceStateCallback.onInitState(-1);
            }
            if (ZkFaceManager.this.mRetryInitFaceCount < 3) {
                ZkFaceManager.this.init();
                ZkFaceManager.access$108(ZkFaceManager.this);
            }
        }
    };
    /* access modifiers changed from: private */
    public ZkFaceStateCallback mFaceStateCallback;
    /* access modifiers changed from: private */
    public final AtomicBoolean mIsConnectServiceSuccess = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public int mRetryInitFaceCount = 0;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IFaceEdkService unused = ZkFaceManager.this.mFaceEdkService = IFaceEdkService.Stub.asInterface(iBinder);
            try {
                ZkFaceManager.this.mFaceEdkService.addFaceInitListener(ZkFaceManager.this.mFaceInitListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (ZkFaceManager.this.mFaceStateCallback != null) {
                ZkFaceManager.this.mFaceStateCallback.onServiceConnected();
            }
            ZkFaceManager.this.init();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            try {
                ZkFaceManager.this.mFaceEdkService.removeFaceInitListener(ZkFaceManager.this.mFaceInitListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            IFaceEdkService unused = ZkFaceManager.this.mFaceEdkService = null;
            if (ZkFaceManager.this.mFaceStateCallback != null) {
                ZkFaceManager.this.mFaceStateCallback.onServiceDisconnected();
            }
        }

        public void onBindingDied(ComponentName componentName) {
            ZkLogUtil.iFormat("face manager onBindingDied", new Object[0]);
            IFaceEdkService unused = ZkFaceManager.this.mFaceEdkService = null;
            ZkFaceManager.this.mIsConnectServiceSuccess.set(false);
        }
    };

    static /* synthetic */ int access$108(ZkFaceManager zkFaceManager) {
        int i = zkFaceManager.mRetryInitFaceCount;
        zkFaceManager.mRetryInitFaceCount = i + 1;
        return i;
    }

    private ZkFaceManager() {
    }

    public static ZkFaceManager getInstance() {
        if (mInstance == null) {
            synchronized (ZkFaceManager.class) {
                if (mInstance == null) {
                    mInstance = new ZkFaceManager();
                }
            }
        }
        return mInstance;
    }

    public void setZkFaceStateCallback(ZkFaceStateCallback zkFaceStateCallback) {
        this.mFaceStateCallback = zkFaceStateCallback;
    }

    public int bindService(Context context) {
        if (context == null) {
            return -1001;
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
        return -1004;
    }

    public int unbindService(Context context) {
        if (!this.mIsConnectServiceSuccess.get()) {
            return 0;
        }
        if (context == null) {
            return -1001;
        }
        context.getApplicationContext().unbindService(this.mServiceConnection);
        this.mIsConnectServiceSuccess.set(false);
        return 0;
    }

    /* access modifiers changed from: private */
    public void init() {
        if (isConnectService()) {
            try {
                this.mFaceEdkService.init();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void init(ZkFaceConfig zkFaceConfig) {
        if (!isConnectService() && zkFaceConfig != null) {
            try {
                this.mFaceEdkService.initWithConfig(zkFaceConfig);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public int setConfig(ZkFaceConfig zkFaceConfig) {
        if (!isConnectService()) {
            return -1004;
        }
        if (zkFaceConfig == null) {
            return -1001;
        }
        try {
            return this.mFaceEdkService.setConfig(zkFaceConfig);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int getConfig(ZkFaceConfig[] zkFaceConfigArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (zkFaceConfigArr == null || zkFaceConfigArr.length < 1) {
            return -1001;
        }
        try {
            zkFaceConfigArr[0] = this.mFaceEdkService.getConfig();
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int isInit() {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.isInit();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    /* JADX INFO: finally extract failed */
    public int detectFromNV21(byte[] bArr, int i, int i2, ZkDetectInfo[] zkDetectInfoArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr == null || bArr.length <= 0 || zkDetectInfoArr == null || zkDetectInfoArr.length < 1) {
            return -1001;
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = ZkShareMemoryUtil.writeShareMemory(bArr, bArr.length);
            zkDetectInfoArr[0] = this.mFaceEdkService.detectFromNV21(parcelFileDescriptor, i, i2, bArr.length);
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            return -1005;
        } catch (Throwable th) {
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public int detectFromNV21Ex(byte[] bArr, int i, int i2, int i3, ZkDetectInfo[] zkDetectInfoArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr == null || bArr.length <= 0 || zkDetectInfoArr == null || zkDetectInfoArr.length < 1) {
            return -1001;
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = ZkShareMemoryUtil.writeShareMemory(bArr, bArr.length);
            zkDetectInfoArr[0] = this.mFaceEdkService.detectFromNV21Ex(parcelFileDescriptor, i, i2, bArr.length, i3);
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            return -1005;
        } catch (Throwable th) {
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            throw th;
        }
    }

    public int detectFacesFromRGBIR(byte[] bArr, byte[] bArr2, int i, int i2, ZkDetectInfo[] zkDetectInfoArr) {
        ParcelFileDescriptor parcelFileDescriptor;
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr == null || bArr.length <= 0 || bArr2 == null || bArr2.length <= 0 || zkDetectInfoArr == null || zkDetectInfoArr.length < 1) {
            return -1001;
        }
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        try {
            ParcelFileDescriptor writeShareMemory = ZkShareMemoryUtil.writeShareMemory(bArr, bArr.length);
            try {
                parcelFileDescriptor2 = ZkShareMemoryUtil.writeShareMemory(bArr2, bArr2.length);
                zkDetectInfoArr[0] = this.mFaceEdkService.detectFacesFromRGBIR(writeShareMemory, parcelFileDescriptor2, i, i2, bArr.length);
                ZkShareMemoryUtil.closePfd(writeShareMemory);
                ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
                return 0;
            } catch (Exception e) {
                e = e;
                parcelFileDescriptor = parcelFileDescriptor2;
                parcelFileDescriptor2 = writeShareMemory;
                try {
                    e.printStackTrace();
                    ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
                    ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
                    return -1005;
                } catch (Throwable th) {
                    th = th;
                    ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
                    ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                parcelFileDescriptor = parcelFileDescriptor2;
                parcelFileDescriptor2 = writeShareMemory;
                ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
                ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            parcelFileDescriptor = null;
            e.printStackTrace();
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            return -1005;
        } catch (Throwable th3) {
            th = th3;
            parcelFileDescriptor = null;
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            throw th;
        }
    }

    public int detectFacesFromRGBIREx(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, ZkDetectInfo[] zkDetectInfoArr) {
        Throwable th;
        ParcelFileDescriptor parcelFileDescriptor;
        byte[] bArr3 = bArr;
        byte[] bArr4 = bArr2;
        ZkDetectInfo[] zkDetectInfoArr2 = zkDetectInfoArr;
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr3 == null || bArr3.length <= 0 || bArr4 == null || bArr4.length <= 0 || zkDetectInfoArr2 == null || zkDetectInfoArr2.length < 1) {
            return -1001;
        }
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        try {
            ParcelFileDescriptor writeShareMemory = ZkShareMemoryUtil.writeShareMemory(bArr, bArr3.length);
            try {
                parcelFileDescriptor2 = ZkShareMemoryUtil.writeShareMemory(bArr2, bArr4.length);
                try {
                    zkDetectInfoArr2[0] = this.mFaceEdkService.detectFacesFromRGBIREx(writeShareMemory, parcelFileDescriptor2, i, i2, bArr3.length, i3, i4);
                    ZkShareMemoryUtil.closePfd(writeShareMemory);
                    ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
                    return 0;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    ZkShareMemoryUtil.closePfd(writeShareMemory);
                    ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
                    return -1005;
                } catch (Throwable th2) {
                    th = th2;
                    th = th;
                    parcelFileDescriptor = parcelFileDescriptor2;
                    parcelFileDescriptor2 = writeShareMemory;
                    ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
                    ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                th = th;
                parcelFileDescriptor = parcelFileDescriptor2;
                parcelFileDescriptor2 = writeShareMemory;
                ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
                ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            parcelFileDescriptor = null;
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor2);
            ZkShareMemoryUtil.closePfd(parcelFileDescriptor);
            throw th;
        }
    }

    public int extractFeature(Bitmap bitmap, boolean z, ZkExtractResult[] zkExtractResultArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bitmap == null || bitmap.isRecycled() || zkExtractResultArr == null || zkExtractResultArr.length < 1) {
            return -1001;
        }
        try {
            zkExtractResultArr[0] = this.mFaceEdkService.extractFeature(bitmap, z);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int dbIdentify(byte[] bArr, List<IdentifyInfo> list) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr == null || bArr.length == 0 || list == null) {
            return -1001;
        }
        list.clear();
        try {
            list.addAll(this.mFaceEdkService.dbIdentify(bArr));
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int dbIdentify(byte[] bArr, int i, List<TopKIdentifyInfo> list) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr == null || bArr.length == 0 || list == null) {
            return -1001;
        }
        list.clear();
        try {
            list.addAll(this.mFaceEdkService.dbIdentifyTopK(bArr, i));
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int getAgeAndGender(byte[] bArr, AgeAndGender[] ageAndGenderArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr == null || bArr.length == 0 || ageAndGenderArr == null) {
            return -1001;
        }
        try {
            this.mFaceEdkService.getAgeAndGender(bArr);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int livenessClassify(byte[] bArr, List<LivenessResult> list) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr == null || bArr.length == 0 || list == null) {
            return -1001;
        }
        try {
            return this.mFaceEdkService.livenessClassify(bArr, list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int resetTrackId(long j) {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.resetTrackId(j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int compare(Bitmap bitmap, Bitmap bitmap2, boolean z, ZkCompareResult[] zkCompareResultArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bitmap == null || bitmap.isRecycled() || bitmap2 == null || bitmap2.isRecycled() || zkCompareResultArr == null || zkCompareResultArr.length < 1) {
            return -1001;
        }
        try {
            zkCompareResultArr[0] = this.mFaceEdkService.compare(bitmap, bitmap2, z);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int dbAdd(String str, byte[] bArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (bArr == null || bArr.length <= 0 || TextUtils.isEmpty(str) || str.length() > 24) {
            return -1001;
        }
        try {
            return this.mFaceEdkService.dbAdd(str, bArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int dbDel(String str) {
        if (!isConnectService()) {
            return -1004;
        }
        if (TextUtils.isEmpty(str) || str.length() > 24) {
            return -1001;
        }
        try {
            return this.mFaceEdkService.dbDel(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int dbClear() {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.dbClear();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int dbCount(int[] iArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (iArr == null || iArr.length < 1) {
            return -1001;
        }
        try {
            return this.mFaceEdkService.dbCount(iArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int reset() {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.reset();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int release() {
        try {
            if (!isConnectService()) {
                return -1004;
            }
            return this.mFaceEdkService.release();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int queryFaceTemplate(String str, byte[] bArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (TextUtils.isEmpty(str)) {
            return -1001;
        }
        try {
            return this.mFaceEdkService.queryFaceTemplate(str, bArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int queryFaceTemplatePins(long j, long j2, List<ZkFaceTemplateResult> list) {
        if (!isConnectService()) {
            return -1004;
        }
        long j3 = j < 0 ? 0 : j;
        if (j2 <= 0) {
            j2 = 1;
        }
        try {
            return this.mFaceEdkService.queryFaceTemplatePins(j3, j2 > 100 ? 100 : j2, list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int getFaceEngineSdkVersion(String[] strArr) {
        if (!isConnectService()) {
            return -1004;
        }
        if (strArr == null || strArr.length == 0) {
            return -1001;
        }
        try {
            return this.mFaceEdkService.getFaceEngineSdkVersion(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    private boolean isConnectService() {
        return this.mFaceEdkService != null && this.mFaceEdkService.asBinder().isBinderAlive() && this.mIsConnectServiceSuccess.get();
    }

    public int generateFaceAlgorithmFile() {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.generateFaceAlgorithmFile();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int activateTheFaceAlgorithm() {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.activateFaceAlgorithm();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int checkFaceAlgorithmLicense(long[] jArr, int[] iArr) {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.checkLicense(jArr, iArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int isHaveChip() {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.isHaveChip();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }

    public int isAuthorized() {
        if (!isConnectService()) {
            return -1004;
        }
        try {
            return this.mFaceEdkService.isAuthorized();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1005;
        }
    }
}
