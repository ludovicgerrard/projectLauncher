package com.zktechnology.android.helper;

import android.content.ContentProviderClient;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.zktechnology.android.helper.FingerprintServiceHelper;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.TemplateUtil;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.edk.finger.lib.IFingerprintVerifyListener;
import com.zkteco.edk.finger.lib.ZkFingerprintManager;
import java.util.ArrayList;
import java.util.List;

public class FingerprintServiceHelper {
    private static final String NOTIFY_FINGERPRINT_MODULE_STATE = "notifyFingerModuleState";
    private static final String PROVIDER_AUTHORITY = "com.zkteco.android.core";
    private static final String TAG = "FingerprintServiceHelper";
    /* access modifiers changed from: private */
    public final Context mContext;
    private final IFingerprintVerifyListener mFingerprintVerifyListener;
    /* access modifiers changed from: private */
    public List<OnFingerprintScanListener> mListeners;

    static class SingletonHolder {
        static final FingerprintServiceHelper INSTANCE = new FingerprintServiceHelper();

        SingletonHolder() {
        }
    }

    private FingerprintServiceHelper() {
        this.mFingerprintVerifyListener = new IFingerprintVerifyListener() {
            public void onFingerprintCaptureError(String str) {
            }

            public void onFingerprintExtractFail(int i) {
            }

            public void onFingerprintCaptureSuccess(byte[] bArr, int i, int i2) {
                List<OnFingerprintScanListener> access$100 = FingerprintServiceHelper.this.mListeners;
                if (access$100 != null) {
                    for (OnFingerprintScanListener onFingerprintCapture : access$100) {
                        onFingerprintCapture.onFingerprintCapture(bArr, i, i2);
                    }
                }
            }

            public void onFingerprintSensorExtractSuccess(byte[] bArr) {
                List<OnFingerprintScanListener> access$100 = FingerprintServiceHelper.this.mListeners;
                if (access$100 != null) {
                    for (OnFingerprintScanListener onFingerprintExtract : access$100) {
                        onFingerprintExtract.onFingerprintExtract(bArr);
                    }
                }
            }

            public void onFingerprintStateCallback(int i) {
                Log.d(FingerprintServiceHelper.TAG, "onFingerprintStateCallback: state=" + i + ", threadName=" + Thread.currentThread().getName());
                if (i == 2 || i == -2) {
                    ZkThreadPoolManager.getInstance().execute(new Runnable() {
                        public final void run() {
                            FingerprintServiceHelper.AnonymousClass1.this.lambda$onFingerprintStateCallback$0$FingerprintServiceHelper$1();
                        }
                    });
                } else if (i == -3) {
                    DBManager.getInstance().setIntOption("hasFingerModule", 0);
                }
            }

            public /* synthetic */ void lambda$onFingerprintStateCallback$0$FingerprintServiceHelper$1() {
                boolean z = false;
                boolean z2 = ZkFingerprintManager.getInstance().isInit() == 0;
                Log.d(FingerprintServiceHelper.TAG, "initFingerprintFuture: isInit====>" + z2);
                String str = "1";
                DBManager.getInstance().setStrOption("hasFingerModule", z2 ? str : "0");
                boolean z3 = DBManager.getInstance().getIntOption("FingerFunOn", 1) == 1;
                if (!z3) {
                    FingerprintServiceHelper.this.disconnect();
                }
                if (z2 && z3) {
                    z = true;
                }
                ContentProviderClient acquireContentProviderClient = FingerprintServiceHelper.this.mContext.getContentResolver().acquireContentProviderClient(FingerprintServiceHelper.PROVIDER_AUTHORITY);
                if (acquireContentProviderClient != null) {
                    if (!z) {
                        str = "0";
                    }
                    try {
                        acquireContentProviderClient.call(FingerprintServiceHelper.NOTIFY_FINGERPRINT_MODULE_STATE, str, (Bundle) null);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (Throwable th) {
                        acquireContentProviderClient.release();
                        throw th;
                    }
                    acquireContentProviderClient.release();
                }
                if (z) {
                    Log.i(FingerprintServiceHelper.TAG, "指纹功能正常");
                    TemplateUtil.compareFpCounts(FingerprintServiceHelper.this.mContext);
                }
            }
        };
        this.mContext = LauncherApplication.getLauncherApplicationContext().getApplicationContext();
    }

    public static FingerprintServiceHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public final void init() {
        Log.d(TAG, "initFingerprint: [start init finger]");
        ZkFingerprintManager.getInstance().addFingerprintListener(this.mFingerprintVerifyListener);
        int bindService = ZkFingerprintManager.getInstance().bindService(this.mContext);
        boolean z = true;
        Log.d(TAG, String.format("initFingerprint: [bindService result %d]", new Object[]{Integer.valueOf(bindService)}));
        if (bindService != 0) {
            z = false;
        }
        if (!z) {
            DBManager.getInstance().setIntOption("hasFingerModule", 0);
        }
    }

    public final void disconnect() {
        ZkFingerprintManager.getInstance().removeFingerprintListener();
        ZkFingerprintManager.getInstance().unbindService(this.mContext);
    }

    public final void addOnFingerprintScanListener(OnFingerprintScanListener onFingerprintScanListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(onFingerprintScanListener);
    }

    public final void removeOnFingerprintScanListener(OnFingerprintScanListener onFingerprintScanListener) {
        List<OnFingerprintScanListener> list = this.mListeners;
        if (list != null) {
            list.remove(onFingerprintScanListener);
        }
    }

    public final int deleteTemplate(String str) {
        return ZkFingerprintManager.getInstance().deleteTemplate(str);
    }
}
