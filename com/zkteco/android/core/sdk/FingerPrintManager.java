package com.zkteco.android.core.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import com.google.common.base.Ascii;
import com.zkteco.android.core.interfaces.FingerPrintInterface;
import com.zkteco.android.core.interfaces.FingerPrintProvider;
import com.zkteco.android.core.interfaces.FingerPrintReceiver;
import com.zkteco.android.core.interfaces.FingerprintListener;
import com.zkteco.android.core.library.CoreProvider;

public class FingerPrintManager implements FingerPrintInterface {
    private Context context;
    private FingerPrintProvider provider;
    private FingerPrintReceiver receiver;

    public FingerPrintManager(Context context2) {
        this.context = context2;
        this.provider = FingerPrintProvider.getInstance(new CoreProvider(context2));
    }

    public void setListener(FingerprintListener fingerprintListener) {
        this.receiver = new FingerPrintReceiver(fingerprintListener);
    }

    public void register() {
        this.receiver.register(this.context);
    }

    public void unregister() {
        this.receiver.unregister(this.context);
    }

    public static Bitmap renderCroppedGreyScaleBitmap(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        int[] iArr = new int[(i * i2)];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i4 * i;
            for (int i6 = 0; i6 < i; i6++) {
                iArr[i5 + i6] = ((bArr[i3 + i6] & 255) * Ascii.SOH) | -16777216;
            }
            i3 += i;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.RGB_565);
        createBitmap.setPixels(iArr, 0, i, 0, 0, i, i2);
        return createBitmap;
    }

    public boolean startSensor() {
        return this.provider.startSensor();
    }

    public void close() {
        this.provider.close();
    }
}
