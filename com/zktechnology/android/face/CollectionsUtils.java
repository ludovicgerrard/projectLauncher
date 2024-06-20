package com.zktechnology.android.face;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class CollectionsUtils {
    private CollectionsUtils() {
    }

    public static boolean isEmptyList(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static String[] removeEmptyString(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return strArr;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static boolean isEmptyStringArray(String[] strArr) {
        if (!(strArr == null || strArr.length == 0)) {
            for (String isEmpty : strArr) {
                if (!TextUtils.isEmpty(isEmpty)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static byte[][] removeEmptyBlob(byte[][] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        ArrayList arrayList = new ArrayList();
        for (byte[] bArr2 : bArr) {
            if (!(bArr2 == null || bArr2.length == 0)) {
                arrayList.add(bArr2);
            }
        }
        return (byte[][]) arrayList.toArray(new byte[0][]);
    }

    public static boolean isEmptyBlobArray(byte[][] bArr) {
        if (!(bArr == null || bArr.length == 0)) {
            for (byte[] bArr2 : bArr) {
                if (bArr2 == null || bArr2.length == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x001b, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String convertBytesToString(byte[] r3) {
        /*
            java.lang.Class<com.zktechnology.android.face.CollectionsUtils> r0 = com.zktechnology.android.face.CollectionsUtils.class
            monitor-enter(r0)
            r1 = 0
            if (r3 == 0) goto L_0x001a
            int r2 = r3.length     // Catch:{ all -> 0x0017 }
            if (r2 > 0) goto L_0x000a
            goto L_0x001a
        L_0x000a:
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x0011 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0011 }
            monitor-exit(r0)
            return r2
        L_0x0011:
            r3 = move-exception
            r3.printStackTrace()     // Catch:{ all -> 0x0017 }
            monitor-exit(r0)
            return r1
        L_0x0017:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        L_0x001a:
            monitor-exit(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.face.CollectionsUtils.convertBytesToString(byte[]):java.lang.String");
    }
}
