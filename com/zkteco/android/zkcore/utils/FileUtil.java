package com.zkteco.android.zkcore.utils;

import android.app.Activity;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import java.io.File;
import java.util.HashMap;

public class FileUtil {
    private static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_EXTERNAL_STORAGE_READ = 0;
    private static final int REQUEST_EXTERNAL_STORAGE_WRITE = 1;

    /* JADX WARNING: Removed duplicated region for block: B:24:0x005d A[SYNTHETIC, Splitter:B:24:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0069 A[SYNTHETIC, Splitter:B:33:0x0069] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean savePhotoToSDCard(java.lang.String r3, java.lang.String r4, android.app.Activity r5, android.graphics.Bitmap r6) {
        /*
            boolean r5 = verifyStoragePermissions(r5)
            if (r5 == 0) goto L_0x0072
            r5 = 0
            java.io.File r0 = new java.io.File
            r0.<init>(r3)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x0015
            r0.mkdirs()
        L_0x0015:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r4 = r1.append(r4)
            java.lang.String r1 = ".jpg"
            java.lang.StringBuilder r4 = r4.append(r1)
            java.lang.String r4 = r4.toString()
            r0.<init>(r3, r4)
            r3 = 1
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0051, all -> 0x0067 }
            r4.<init>(r0)     // Catch:{ IOException -> 0x0051, all -> 0x0067 }
            if (r6 == 0) goto L_0x0048
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ IOException -> 0x0046 }
            r1 = 100
            boolean r5 = r6.compress(r5, r1, r4)     // Catch:{ IOException -> 0x0046 }
            if (r5 == 0) goto L_0x0048
            r4.flush()     // Catch:{ IOException -> 0x0046 }
            r4.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x0048
        L_0x0046:
            r5 = move-exception
            goto L_0x0055
        L_0x0048:
            r4.close()     // Catch:{ IOException -> 0x004c }
            goto L_0x0050
        L_0x004c:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0050:
            return r3
        L_0x0051:
            r4 = move-exception
            r2 = r5
            r5 = r4
            r4 = r2
        L_0x0055:
            r0.delete()     // Catch:{ all -> 0x0066 }
            r5.printStackTrace()     // Catch:{ all -> 0x0066 }
            if (r4 == 0) goto L_0x0065
            r4.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0065:
            return r3
        L_0x0066:
            r5 = r4
        L_0x0067:
            if (r5 == 0) goto L_0x0071
            r5.close()     // Catch:{ IOException -> 0x006d }
            goto L_0x0071
        L_0x006d:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0071:
            return r3
        L_0x0072:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.zkcore.utils.FileUtil.savePhotoToSDCard(java.lang.String, java.lang.String, android.app.Activity, android.graphics.Bitmap):boolean");
    }

    public static boolean deleteFile(String str, Activity activity) {
        if (!verifyStoragePermissions(activity)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || file.delete()) {
            return true;
        }
        return false;
    }

    public static void deleteBlackListPhoto(String str, String str2, Activity activity) {
        File[] listFiles;
        if (verifyStoragePermissions(activity) && (listFiles = new File(str).listFiles()) != null && listFiles.length > 0) {
            for (File file : listFiles) {
                String name = file.getName();
                if (4 < name.length()) {
                    String substring = name.substring(name.indexOf("-"), name.length() - 4);
                    if (checkIsJPGImage(file.getPath()) && substring.equals(str2)) {
                        try {
                            file.delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static String getPhotoPathFromSDCard(String str, String str2, Activity activity) {
        if (verifyStoragePermissions(activity)) {
            File[] listFiles = new File(str).listFiles();
            if (listFiles.length > 0) {
                for (File file : listFiles) {
                    String name = file.getName();
                    if (4 < name.length()) {
                        String substring = name.substring(0, name.length() - 4);
                        if (checkIsJPGImage(file.getPath()) && !str2.equals("") && substring.equals(str2)) {
                            return file.getAbsolutePath();
                        }
                    }
                }
            }
        }
        return null;
    }

    public static HashMap<String, String> getAllImagePath(String str, Activity activity) {
        String str2 = null;
        if (verifyStoragePermissions(activity)) {
            File file = new File(str);
            HashMap<String, String> hashMap = new HashMap<>();
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    String absolutePath = file2.getAbsolutePath();
                    if (checkIsJPGImage(absolutePath)) {
                        String name = file2.getName();
                        if (4 < name.length()) {
                            str2 = name.substring(0, name.length() - 4);
                        }
                    }
                    if (str2 != null) {
                        hashMap.put(str2, absolutePath);
                    }
                }
                return hashMap;
            }
        }
        return null;
    }

    private static boolean verifyStoragePermissions(Activity activity) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return false;
        }
        int checkSelfPermission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
        int checkSelfPermission2 = ActivityCompat.checkSelfPermission(activity, "android.permission.READ_EXTERNAL_STORAGE");
        if (checkSelfPermission != 0) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 1);
        }
        if (checkSelfPermission2 != 0) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 0);
        }
        return true;
    }

    private static boolean checkIsJPGImage(String str) {
        return str.substring(str.lastIndexOf(".") + 1, str.length()).toLowerCase().equals("jpg");
    }

    public static boolean isUserPhotoExist(String str, String str2, Activity activity) {
        if (verifyStoragePermissions(activity)) {
            File[] listFiles = new File(str).listFiles();
            if (listFiles.length > 0) {
                for (File file : listFiles) {
                    String name = file.getName();
                    if (4 < name.length()) {
                        String substring = name.substring(0, name.length() - 4);
                        if (checkIsJPGImage(file.getPath()) && !str2.equals("") && substring.equals(str2)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
