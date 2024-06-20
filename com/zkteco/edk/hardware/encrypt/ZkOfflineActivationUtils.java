package com.zkteco.edk.hardware.encrypt;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.zkteco.LicenseManager.LicenseManager;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ZkOfflineActivationUtils {
    private static final String LICENSE_FILE_NAME = "ZKLicenseManager.lic";
    private static final LicenseManager LICENSE_MANAGER = new LicenseManager();
    private static final String LICENSE_PATH = (Environment.getExternalStorageDirectory().getPath() + "/ZKBioCVLicense/");
    private static final String LICENSE_SN_FILE_NAME = "ZKDeviceSn.txt";
    private static final String TAG_OFFLINE_ACTIVE = "EDK-ACTIVATION";

    public static boolean initLicence() {
        return LICENSE_MANAGER.InitResource() == 0;
    }

    public static boolean createDeviceSN() {
        StringBuilder sb = new StringBuilder();
        String str = LICENSE_PATH;
        if (new File(sb.append(str).append(LICENSE_SN_FILE_NAME).toString()).exists()) {
            return true;
        }
        byte[] bArr = new byte[128];
        int[] iArr = new int[4];
        Log.i("EDK-ACTIVATION", "GetHardwareId ret: " + LICENSE_MANAGER.GetHardwareId(bArr, iArr));
        String str2 = new String(bArr, 0, iArr[0]);
        if (TextUtils.isEmpty(str2) || str2.length() <= 5) {
            return false;
        }
        if (writeTxtToFile(str2, str, LICENSE_SN_FILE_NAME) != null) {
            return true;
        }
        return false;
    }

    public static boolean activateLicense() {
        LicenseManager licenseManager = LICENSE_MANAGER;
        String str = LICENSE_PATH;
        Log.i("EDK-ACTIVATION", "SetLicensePath ret: " + licenseManager.SetLicensePath(str));
        File file = new File(str + LICENSE_FILE_NAME);
        if (!file.exists()) {
            Log.i("EDK-ACTIVATION", "Cannot find license file, please check file and APP read SDCard permission!");
            return false;
        }
        String fileContent = getFileContent(file);
        int length = fileContent.length();
        try {
            Log.i("EDK-ACTIVATION", "len: " + length + " data: " + fileContent);
            Log.i("EDK-ACTIVATION", "SetLicense ret: " + licenseManager.SetLicense(fileContent, length - 100));
            return offlineActivateLicense();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean offlineActivateLicense() {
        byte[] bArr = new byte[2048];
        int[] iArr = new int[4];
        byte[] bArr2 = new byte[1024];
        int[] iArr2 = new int[4];
        try {
            int CheckLicense = LICENSE_MANAGER.CheckLicense(bArr, iArr, bArr2, iArr2);
            Log.i("EDK-ACTIVATION", "CheckLicense ret: " + CheckLicense);
            if (CheckLicense == 0) {
                new String(bArr, 0, iArr[0]);
                new String(bArr2, 0, iArr2[0]);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: java.io.InputStreamReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.io.InputStreamReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.io.InputStreamReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: java.io.InputStreamReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.io.InputStreamReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: java.io.InputStreamReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: java.io.InputStreamReader} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getFileContent(java.io.File r6) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            boolean r1 = r6.isDirectory()
            if (r1 != 0) goto L_0x0076
            java.lang.String r1 = r6.getName()
            java.lang.String r2 = "txt"
            boolean r1 = r1.endsWith(r2)
            if (r1 != 0) goto L_0x0023
            java.lang.String r1 = r6.getName()
            java.lang.String r2 = "lic"
            boolean r1 = r1.endsWith(r2)
            if (r1 == 0) goto L_0x0076
        L_0x0023:
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0058, all -> 0x0054 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x0058, all -> 0x0054 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0051, all -> 0x004e }
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch:{ IOException -> 0x0051, all -> 0x004e }
            r6.<init>(r2, r3)     // Catch:{ IOException -> 0x0051, all -> 0x004e }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0049, all -> 0x0047 }
            r3.<init>(r6)     // Catch:{ IOException -> 0x0049, all -> 0x0047 }
        L_0x0035:
            java.lang.String r1 = r3.readLine()     // Catch:{ IOException -> 0x0045 }
            if (r1 == 0) goto L_0x0060
            java.lang.StringBuilder r1 = r0.append(r1)     // Catch:{ IOException -> 0x0045 }
            java.lang.String r4 = "\n"
            r1.append(r4)     // Catch:{ IOException -> 0x0045 }
            goto L_0x0035
        L_0x0045:
            r1 = move-exception
            goto L_0x005d
        L_0x0047:
            r0 = move-exception
            goto L_0x006c
        L_0x0049:
            r3 = move-exception
            r5 = r3
            r3 = r1
            r1 = r5
            goto L_0x005d
        L_0x004e:
            r0 = move-exception
            r6 = r1
            goto L_0x006c
        L_0x0051:
            r6 = move-exception
            r3 = r1
            goto L_0x005b
        L_0x0054:
            r0 = move-exception
            r6 = r1
            r2 = r6
            goto L_0x006c
        L_0x0058:
            r6 = move-exception
            r2 = r1
            r3 = r2
        L_0x005b:
            r1 = r6
            r6 = r3
        L_0x005d:
            r1.printStackTrace()     // Catch:{ all -> 0x006a }
        L_0x0060:
            closeStream(r3)
            closeStream(r6)
            closeStream(r2)
            goto L_0x0076
        L_0x006a:
            r0 = move-exception
            r1 = r3
        L_0x006c:
            closeStream(r1)
            closeStream(r6)
            closeStream(r2)
            throw r0
        L_0x0076:
            java.lang.String r6 = r0.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.edk.hardware.encrypt.ZkOfflineActivationUtils.getFileContent(java.io.File):java.lang.String");
    }

    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void makeRootDirectory(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void makeFilePath(String str, String str2) {
        makeRootDirectory(str);
        try {
            File file = new File(str + str2);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void clearInfoForFile(String str) {
        File file = new File(str);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File writeTxtToFile(String str, String str2, String str3) {
        makeFilePath(str2, str3);
        String str4 = str2 + str3;
        File file = new File(str4);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } else {
                clearInfoForFile(str4);
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(file.length());
            randomAccessFile.write(str.getBytes());
            randomAccessFile.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
