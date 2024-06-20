package com.zkteco.android.core.device;

import android.text.TextUtils;
import com.zkteco.android.core.interfaces.IDeviceStore;

public class G6Store implements IDeviceStore {
    public boolean setProp(String str, String str2) {
        boolean z = false;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        synchronized (G6Store.class) {
            if (ZkShellUtils.execCommand(String.format("rm -rf /unrecoverable/%s;touch /unrecoverable/%s;echo %s > /unrecoverable/%s", new Object[]{str, str, str2, str}), false).result == 0) {
                z = true;
            }
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0036, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getProp(java.lang.String r6) {
        /*
            r5 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            java.lang.Class<com.zkteco.android.core.device.G6Store> r0 = com.zkteco.android.core.device.G6Store.class
            monitor-enter(r0)
            java.lang.String r2 = "cat /unrecoverable/%s"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0037 }
            r4 = 0
            r3[r4] = r6     // Catch:{ all -> 0x0037 }
            java.lang.String r6 = java.lang.String.format(r2, r3)     // Catch:{ all -> 0x0037 }
            com.zkteco.android.core.device.ZkShellUtils$CommandResult r6 = com.zkteco.android.core.device.ZkShellUtils.execCommand((java.lang.String) r6, (boolean) r4)     // Catch:{ all -> 0x0037 }
            int r2 = r6.result     // Catch:{ all -> 0x0037 }
            if (r2 != 0) goto L_0x0035
            java.lang.String r2 = r6.successMsg     // Catch:{ all -> 0x0037 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0037 }
            if (r2 != 0) goto L_0x0035
            java.lang.String r2 = "null"
            java.lang.String r3 = r6.successMsg     // Catch:{ all -> 0x0037 }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0037 }
            if (r2 != 0) goto L_0x0035
            java.lang.String r6 = r6.successMsg     // Catch:{ all -> 0x0037 }
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            return r6
        L_0x0035:
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            return r1
        L_0x0037:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.core.device.G6Store.getProp(java.lang.String):java.lang.String");
    }
}
