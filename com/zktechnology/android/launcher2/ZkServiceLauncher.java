package com.zktechnology.android.launcher2;

import android.content.BroadcastReceiver;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import com.zktechnology.android.helper.CardServiceHelper;
import com.zktechnology.android.helper.FaceServiceHelper;
import com.zktechnology.android.helper.FingerprintServiceHelper;
import com.zktechnology.android.helper.McuServiceHelper;
import com.zktechnology.android.helper.OnCardReadListener;
import com.zktechnology.android.helper.OnFingerprintScanListener;
import com.zktechnology.android.helper.OnMcuReadListener;
import com.zktechnology.android.helper.OnQrcodeReadListener;
import com.zktechnology.android.helper.QrcodeServiceHelper;
import com.zktechnology.android.helper.SystemServiceHelper;
import com.zktechnology.android.launcher2.ZkServiceLauncher;
import com.zktechnology.android.rs485.RS485Helper;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.HexUtils;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;

public abstract class ZkServiceLauncher extends ZkNfcLauncher {
    private static final String ACTION_FINGERPRINT_FUN_ON = "com.zkteco.android.action.FINGERPRINT_FUN_ON";
    private static final String NOTIFY_FINGERPRINT_MODULE_STATE = "notifyFingerModuleState";
    private static final String PROVIDER_AUTHORITY = "com.zkteco.android.core";
    /* access modifiers changed from: private */
    public static final String TAG = "ZkServiceLauncher";
    private final OnCardReadListener mOnCardReadListener = new OnCardReadListener() {
        public final void onCardRead(String str) {
            ZkServiceLauncher.this.onCardRead(str);
        }
    };
    private final OnFingerprintScanListener mOnFingerprintScanListener = new OnFingerprintScanListener() {
        public void onFingerprintCapture(byte[] bArr, int i, int i2) {
            ZkServiceLauncher.this.onFpCapture(bArr, i, i2);
        }

        public void onFingerprintExtract(byte[] bArr) {
            ZkServiceLauncher.this.onFpExtract(bArr);
        }
    };
    private final OnMcuReadListener mOnMcuReadListener = new OnMcuReadListener() {
        public void onRs232Read(byte[] bArr) {
        }

        public void onRs485Read(byte[] bArr) {
            Log.d(ZkServiceLauncher.TAG, "rs485: " + Thread.currentThread().getName());
            ZkThreadPoolManager.getInstance().execute(new Runnable(bArr) {
                public final /* synthetic */ byte[] f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ZkServiceLauncher.AnonymousClass3.this.lambda$onRs485Read$0$ZkServiceLauncher$3(this.f$1);
                }
            });
        }

        public /* synthetic */ void lambda$onRs485Read$0$ZkServiceLauncher$3(byte[] bArr) {
            int length;
            if (bArr != null && bArr.length > 0 && bArr.length - 11 > 0) {
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 9, bArr2, 0, length);
                byte b = bArr[6];
                String str = null;
                if (b == 1) {
                    str = Base64.encodeToString(bArr2, 0);
                } else if (b == 2) {
                    if (length > 4) {
                        str = RS485Helper.cardNumber(bArr2, 0, length);
                    } else {
                        String bytes2HexString = HexUtils.bytes2HexString(bArr2);
                        if (DBManager.getInstance().getIntOption("~CardByteRevert", 0) == 1) {
                            HexUtils.reserveByte(bArr2);
                            bytes2HexString = HexUtils.bytes2HexString(bArr2);
                        }
                        str = String.valueOf(Long.parseLong(bytes2HexString, 16));
                    }
                }
                ZkServiceLauncher.this.onRs485DataReceived(b, str);
            }
        }

        public void onWiegandRead(String str) {
            boolean z = false;
            if (DBManager.getInstance().getIntOption(WiegandConfig.IN, 0) == 1) {
                z = true;
            }
            if (z) {
                ZkServiceLauncher.this.onWiegandIn(str);
            }
        }
    };
    private final OnQrcodeReadListener mOnQrcodeReadListener = new OnQrcodeReadListener() {
        public final void onQrcodeRead(String str) {
            ZkServiceLauncher.this.onQrcodeRead(str);
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean booleanExtra = intent.getBooleanExtra("FingerFunOn", true);
            if (booleanExtra) {
                ZkThreadPoolManager.getInstance().execute($$Lambda$ZkServiceLauncher$1$VvgXQ8ZQeruSGClIPgphn56Poq0.INSTANCE);
            } else {
                FingerprintServiceHelper.getInstance().disconnect();
            }
            ContentProviderClient acquireContentProviderClient = LauncherApplication.getApplication().getContentResolver().acquireContentProviderClient(ZkServiceLauncher.PROVIDER_AUTHORITY);
            if (acquireContentProviderClient != null) {
                try {
                    acquireContentProviderClient.call(ZkServiceLauncher.NOTIFY_FINGERPRINT_MODULE_STATE, booleanExtra ? "1" : "0", (Bundle) null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    acquireContentProviderClient.release();
                    throw th;
                }
                acquireContentProviderClient.release();
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract void onFpCapture(byte[] bArr, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void onFpExtract(byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract void onQrcodeRead(String str);

    /* access modifiers changed from: protected */
    public abstract void onRs485DataReceived(int i, String str);

    /* access modifiers changed from: protected */
    public abstract void onWiegandIn(String str);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        register();
    }

    private void register() {
        registerReceiver(this.mReceiver, new IntentFilter(ACTION_FINGERPRINT_FUN_ON));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        registerEdkListener();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        unregisterEdkListener();
    }

    private void registerEdkListener() {
        CardServiceHelper.getInstance().addOnCardReadListener(this.mOnCardReadListener);
        FingerprintServiceHelper.getInstance().addOnFingerprintScanListener(this.mOnFingerprintScanListener);
        McuServiceHelper.getInstance().addOnMcuReadListener(this.mOnMcuReadListener);
        QrcodeServiceHelper.getInstance().addOnQrcodeReadListener(this.mOnQrcodeReadListener);
    }

    private void unregisterEdkListener() {
        CardServiceHelper.getInstance().removeOnCardReadListener(this.mOnCardReadListener);
        FingerprintServiceHelper.getInstance().removeOnFingerprintScanListener(this.mOnFingerprintScanListener);
        McuServiceHelper.getInstance().removeOnMcuReadListener(this.mOnMcuReadListener);
        QrcodeServiceHelper.getInstance().removeOnQrcodeReadListener(this.mOnQrcodeReadListener);
    }

    public void onDestroy() {
        super.onDestroy();
        FaceServiceHelper.getInstance().disconnect();
        CardServiceHelper.getInstance().disconnect();
        McuServiceHelper.getInstance().disconnect();
        QrcodeServiceHelper.getInstance().disconnect();
        FingerprintServiceHelper.getInstance().disconnect();
        SystemServiceHelper.getInstance().disconnect();
        unregisterReceiver(this.mReceiver);
    }
}
