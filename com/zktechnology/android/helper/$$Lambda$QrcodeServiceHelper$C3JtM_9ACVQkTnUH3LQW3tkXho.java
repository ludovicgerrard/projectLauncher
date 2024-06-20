package com.zktechnology.android.helper;

import android.util.Log;
import com.zkteco.edk.qrcode.IZkQrCodeStateCallback;

/* renamed from: com.zktechnology.android.helper.-$$Lambda$QrcodeServiceHelper$C3JtM_9ACVQkTnUH3LQW3tk-Xho  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$QrcodeServiceHelper$C3JtM_9ACVQkTnUH3LQW3tkXho implements IZkQrCodeStateCallback {
    public static final /* synthetic */ $$Lambda$QrcodeServiceHelper$C3JtM_9ACVQkTnUH3LQW3tkXho INSTANCE = new $$Lambda$QrcodeServiceHelper$C3JtM_9ACVQkTnUH3LQW3tkXho();

    private /* synthetic */ $$Lambda$QrcodeServiceHelper$C3JtM_9ACVQkTnUH3LQW3tkXho() {
    }

    public final void onQrCodeState(String str, int i) {
        Log.d(QrcodeServiceHelper.TAG, str + " state--->" + i);
    }
}
