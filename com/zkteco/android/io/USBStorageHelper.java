package com.zkteco.android.io;

import android.os.Build;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\u0004J\u0006\u0010\t\u001a\u00020\nR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/zkteco/android/io/USBStorageHelper;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "ignoreDirsInZpadV2", "", "getPath", "isConnected", "", "HelpersAndroidIO_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: USBStorageHelper.kt */
public final class USBStorageHelper {
    public static final USBStorageHelper INSTANCE;
    private static final String TAG;
    private static final List<String> ignoreDirsInZpadV2 = CollectionsKt.listOf("emulated", "self");

    static {
        USBStorageHelper uSBStorageHelper = new USBStorageHelper();
        INSTANCE = uSBStorageHelper;
        TAG = uSBStorageHelper.getClass().getSimpleName();
    }

    private USBStorageHelper() {
    }

    public final String getPath() {
        Object obj;
        if (Build.VERSION.SDK_INT <= 16) {
            return "/storage/usb_host/disk-1";
        }
        Iterator it = StringsKt.split$default((CharSequence) SystemUtilsKt.runAsCommand("ls /storage"), new String[]{"\n"}, false, 0, 6, (Object) null).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (!ignoreDirsInZpadV2.contains((String) obj)) {
                break;
            }
        }
        String str = (String) obj;
        if (str == null) {
            return "";
        }
        return "/storage/" + str;
    }

    public final boolean isConnected() {
        return new File(getPath()).exists();
    }
}
