package com.zkteco.android.io;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.zkteco.util.FileComparator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FileHelper {
    private static final String MD5_HASH_FORMAT = "busybox md5sum %s";
    private static final String TAG = "com.zkteco.android.io.FileHelper";

    public static boolean copy(File file, File file2) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    try {
                        fileOutputStream.write(bArr, 0, read);
                    } catch (IOException e) {
                        Log.e(TAG, Log.getStackTraceString(e));
                    }
                } else {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    fileInputStream.close();
                    return true;
                }
            }
        } catch (FileNotFoundException e2) {
            Log.e(TAG, Log.getStackTraceString(e2));
            return false;
        }
    }

    public static boolean delete(File file) {
        return file.delete();
    }

    public static String getFileAsString(String str) throws IOException {
        StringBuilder sb = new StringBuilder(1000);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
        char[] cArr = new char[1024];
        while (true) {
            int read = bufferedReader.read(cArr);
            if (read != -1) {
                sb.append(String.valueOf(cArr, 0, read));
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }

    public static boolean doesDBExist(Context context, String str) {
        return context.getDatabasePath(str).exists();
    }

    public static String getMD5Hash(String str) throws IOException {
        String format = String.format(MD5_HASH_FORMAT, new Object[]{str});
        String str2 = TAG;
        Log.d(str2, "getMD5Hash Command >> " + format);
        List<String> execCommandAndRetrieveStringResult = ShellHelper.execCommandAndRetrieveStringResult(format);
        Log.d(str2, "getMD5Hash Result >> " + execCommandAndRetrieveStringResult);
        if (!execCommandAndRetrieveStringResult.isEmpty()) {
            return execCommandAndRetrieveStringResult.get(0).split(" ")[0];
        }
        throw new IOException("Could not get result for MD5 hash");
    }

    public static File[] getFileList(File file, boolean z, String str, int i) throws IOException {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                File[] removeHiddenFiles = !z ? removeHiddenFiles(listFiles) : listFiles;
                if (str != null && !str.isEmpty()) {
                    removeHiddenFiles = removeNotRegexFiles(listFiles, str, false);
                }
                if (i == -1) {
                    i = 0;
                }
                Arrays.sort(removeHiddenFiles, new FileComparator(i));
                return removeHiddenFiles;
            }
            throw new IOException(file.getAbsolutePath() + ": Cannot list, probably insufficent permissions");
        }
        throw new IOException(file.getAbsolutePath() + ": Not a directory");
    }

    public static byte[] imageFileToByteArray(Uri uri) {
        File file = new File(uri.getPath());
        int length = (int) file.length();
        byte[] bArr = new byte[length];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bArr);
            fileInputStream.close();
            for (int i = 0; i < length; i++) {
                System.out.print((char) bArr[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bArr;
    }

    public static boolean isPathBelow(File file, File file2) {
        return file.compareTo(file2) <= 0;
    }

    public static File[] removeNotRegexFiles(File[] fileArr, String str, boolean z) {
        ArrayList arrayList = new ArrayList();
        Pattern compile = Pattern.compile(str, 2);
        for (File file : fileArr) {
            Matcher matcher = compile.matcher(file.getName());
            if ((z && file.isDirectory()) || matcher.find()) {
                arrayList.add(file);
            }
        }
        return (File[]) arrayList.toArray(new File[arrayList.size()]);
    }

    private static File[] removeHiddenFiles(File[] fileArr) {
        ArrayList arrayList = new ArrayList();
        for (File file : fileArr) {
            if (!file.isHidden()) {
                arrayList.add(file);
            }
        }
        return (File[]) arrayList.toArray(new File[arrayList.size()]);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x002e A[SYNTHETIC, Splitter:B:18:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003e A[SYNTHETIC, Splitter:B:24:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x004f A[SYNTHETIC, Splitter:B:29:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:15:0x0023=Splitter:B:15:0x0023, B:21:0x0033=Splitter:B:21:0x0033} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeToPrivateFile(android.content.Context r2, java.lang.String r3, java.lang.String r4) {
        /*
            r0 = 0
            r1 = 0
            java.io.FileOutputStream r2 = r2.openFileOutput(r3, r0)     // Catch:{ FileNotFoundException -> 0x0032, IOException -> 0x0022 }
            java.io.BufferedWriter r3 = new java.io.BufferedWriter     // Catch:{ FileNotFoundException -> 0x0032, IOException -> 0x0022 }
            java.io.OutputStreamWriter r0 = new java.io.OutputStreamWriter     // Catch:{ FileNotFoundException -> 0x0032, IOException -> 0x0022 }
            r0.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0032, IOException -> 0x0022 }
            r3.<init>(r0)     // Catch:{ FileNotFoundException -> 0x0032, IOException -> 0x0022 }
            r3.write(r4)     // Catch:{ FileNotFoundException -> 0x001d, IOException -> 0x001a, all -> 0x0017 }
            r3.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x004c
        L_0x0017:
            r2 = move-exception
            r1 = r3
            goto L_0x004d
        L_0x001a:
            r2 = move-exception
            r1 = r3
            goto L_0x0023
        L_0x001d:
            r2 = move-exception
            r1 = r3
            goto L_0x0033
        L_0x0020:
            r2 = move-exception
            goto L_0x004d
        L_0x0022:
            r2 = move-exception
        L_0x0023:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x0020 }
            java.lang.String r2 = android.util.Log.getStackTraceString(r2)     // Catch:{ all -> 0x0020 }
            android.util.Log.e(r3, r2)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x004c
        L_0x0032:
            r2 = move-exception
        L_0x0033:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x0020 }
            java.lang.String r2 = android.util.Log.getStackTraceString(r2)     // Catch:{ all -> 0x0020 }
            android.util.Log.e(r3, r2)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x004c
        L_0x0042:
            r2 = move-exception
            java.lang.String r3 = TAG
            java.lang.String r2 = android.util.Log.getStackTraceString(r2)
            android.util.Log.e(r3, r2)
        L_0x004c:
            return
        L_0x004d:
            if (r1 == 0) goto L_0x005d
            r1.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x005d
        L_0x0053:
            r3 = move-exception
            java.lang.String r4 = TAG
            java.lang.String r3 = android.util.Log.getStackTraceString(r3)
            android.util.Log.e(r4, r3)
        L_0x005d:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.io.FileHelper.writeToPrivateFile(android.content.Context, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0046 A[SYNTHETIC, Splitter:B:18:0x0046] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFromPrivateFile(android.content.Context r2, java.lang.String r3) throws java.io.IOException {
        /*
            r0 = 0
            java.io.FileInputStream r2 = r2.openFileInput(r3)     // Catch:{ all -> 0x0043 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x0043 }
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ all -> 0x0043 }
            r1.<init>(r2)     // Catch:{ all -> 0x0043 }
            r3.<init>(r1)     // Catch:{ all -> 0x0043 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0040 }
            r2.<init>()     // Catch:{ all -> 0x0040 }
        L_0x0014:
            java.lang.String r0 = r3.readLine()     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x0024
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0040 }
            java.lang.String r1 = "\n"
            r0.append(r1)     // Catch:{ all -> 0x0040 }
            goto L_0x0014
        L_0x0024:
            int r0 = r2.length()     // Catch:{ all -> 0x0040 }
            int r0 = r0 + -1
            r2.deleteCharAt(r0)     // Catch:{ all -> 0x0040 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0040 }
            r3.close()     // Catch:{ IOException -> 0x0035 }
            goto L_0x003f
        L_0x0035:
            r3 = move-exception
            java.lang.String r0 = TAG
            java.lang.String r3 = android.util.Log.getStackTraceString(r3)
            android.util.Log.e(r0, r3)
        L_0x003f:
            return r2
        L_0x0040:
            r2 = move-exception
            r0 = r3
            goto L_0x0044
        L_0x0043:
            r2 = move-exception
        L_0x0044:
            if (r0 == 0) goto L_0x0054
            r0.close()     // Catch:{ IOException -> 0x004a }
            goto L_0x0054
        L_0x004a:
            r3 = move-exception
            java.lang.String r0 = TAG
            java.lang.String r3 = android.util.Log.getStackTraceString(r3)
            android.util.Log.e(r0, r3)
        L_0x0054:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.io.FileHelper.readFromPrivateFile(android.content.Context, java.lang.String):java.lang.String");
    }

    private FileHelper() {
    }
}
