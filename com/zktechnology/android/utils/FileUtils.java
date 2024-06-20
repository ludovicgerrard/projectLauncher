package com.zktechnology.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static final String CONFIG_YML_PATH = (SDCarePath + "/config/config.yml");
    private static String SDCarePath = Environment.getExternalStorageDirectory().toString();
    private static final String TAG = "FileUtils";
    public static final String avatarPath = (SDCarePath + "/Result/Photo/");
    public static final String idCardPath = (SDCarePath + "/Result/IDCardPhoto/");
    public static final String livePath = (SDCarePath + "/Result/LiveTest/");
    public static final String recordPath = (SDCarePath + "/Result/Record/");
    public static final String resultPath = (SDCarePath + "/Result");
    public static final String speedPhotoPath = (SDCarePath + "/TestSpeed");
    public static final String testPhotoPath = (SDCarePath + "/Test25");
    public static final String testSpeedResultPath = (SDCarePath + "/Result/SpeedTest");

    public static void writeStringAsFile(String str, String str2, boolean z) {
        try {
            File file = new File(str2);
            if (file.exists()) {
                FileWriter fileWriter = new FileWriter(file, z);
                fileWriter.write(str);
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003a A[SYNTHETIC, Splitter:B:21:0x003a] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0049 A[SYNTHETIC, Splitter:B:28:0x0049] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFileAsString(java.lang.String r4) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            boolean r4 = r0.exists()
            if (r4 != 0) goto L_0x000e
            java.lang.String r4 = ""
            return r4
        L_0x000e:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0034 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ IOException -> 0x0034 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x0034 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0034 }
        L_0x001e:
            java.lang.String r0 = r2.readLine()     // Catch:{ IOException -> 0x002f, all -> 0x002c }
            if (r0 == 0) goto L_0x0028
            r4.append(r0)     // Catch:{ IOException -> 0x002f, all -> 0x002c }
            goto L_0x001e
        L_0x0028:
            r2.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x0042
        L_0x002c:
            r4 = move-exception
            r1 = r2
            goto L_0x0047
        L_0x002f:
            r0 = move-exception
            r1 = r2
            goto L_0x0035
        L_0x0032:
            r4 = move-exception
            goto L_0x0047
        L_0x0034:
            r0 = move-exception
        L_0x0035:
            r0.printStackTrace()     // Catch:{ all -> 0x0032 }
            if (r1 == 0) goto L_0x0042
            r1.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x0042
        L_0x003e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0042:
            java.lang.String r4 = r4.toString()
            return r4
        L_0x0047:
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch:{ IOException -> 0x004d }
            goto L_0x0051
        L_0x004d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0051:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.FileUtils.readFileAsString(java.lang.String):java.lang.String");
    }

    public static void deleteAllFile(String str) {
        File[] files = getFiles(str);
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].exists()) {
                    if (files[i].delete()) {
                        Log.i(TAG, "deleteAllFile: delete success");
                    } else {
                        Log.i(TAG, "deleteAllFile: delete failed");
                    }
                }
            }
        }
    }

    public static File[] getFiles(String str) {
        return new File(str).listFiles();
    }

    public static String saveBitmap(Bitmap bitmap, String str) {
        String str2 = avatarPath;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdir();
        }
        String str3 = str2 + str + ".jpeg";
        File file2 = new File(str3);
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return str3;
    }

    public static String saveLiveFaceBitmap(Bitmap bitmap, String str) {
        String str2 = livePath;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdir();
        }
        String str3 = str2 + str + ".jpeg";
        File file2 = new File(str3);
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return str3;
    }

    public static String saveIDCardPhotoBitmap(Bitmap bitmap, String str) {
        String str2 = idCardPath;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdir();
        }
        String str3 = str2 + str + ".jpeg";
        File file2 = new File(str3);
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return str3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0082 A[SYNTHETIC, Splitter:B:24:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008a A[SYNTHETIC, Splitter:B:28:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void savePersonRecord(java.lang.String r4, java.lang.String r5) {
        /*
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = SDCarePath
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "/Result/Record/"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = java.io.File.separator
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = ""
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r5 = r2.append(r5)
            java.lang.String r2 = ".txt"
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r5 = r5.toString()
            r0.<init>(r1, r5)
            boolean r5 = r0.exists()     // Catch:{ Exception -> 0x0093 }
            if (r5 != 0) goto L_0x004e
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0093 }
            java.lang.String r1 = r0.getParent()     // Catch:{ Exception -> 0x0093 }
            r5.<init>(r1)     // Catch:{ Exception -> 0x0093 }
            r5.mkdirs()     // Catch:{ Exception -> 0x0093 }
            r0.createNewFile()     // Catch:{ Exception -> 0x0093 }
        L_0x004e:
            r5 = 0
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ IOException -> 0x007c }
            r2 = 1
            r1.<init>(r0, r2)     // Catch:{ IOException -> 0x007c }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            r5.<init>()     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            java.lang.String r5 = "\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            r1.write(r4)     // Catch:{ IOException -> 0x0077, all -> 0x0074 }
            r1.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x00b5
        L_0x006f:
            r4 = move-exception
        L_0x0070:
            r4.printStackTrace()     // Catch:{ Exception -> 0x0093 }
            goto L_0x00b5
        L_0x0074:
            r4 = move-exception
            r5 = r1
            goto L_0x0088
        L_0x0077:
            r4 = move-exception
            r5 = r1
            goto L_0x007d
        L_0x007a:
            r4 = move-exception
            goto L_0x0088
        L_0x007c:
            r4 = move-exception
        L_0x007d:
            r4.printStackTrace()     // Catch:{ all -> 0x007a }
            if (r5 == 0) goto L_0x00b5
            r5.close()     // Catch:{ IOException -> 0x0086 }
            goto L_0x00b5
        L_0x0086:
            r4 = move-exception
            goto L_0x0070
        L_0x0088:
            if (r5 == 0) goto L_0x0092
            r5.close()     // Catch:{ IOException -> 0x008e }
            goto L_0x0092
        L_0x008e:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ Exception -> 0x0093 }
        L_0x0092:
            throw r4     // Catch:{ Exception -> 0x0093 }
        L_0x0093:
            r4 = move-exception
            java.lang.String r5 = "记录保存结果："
            java.lang.String r0 = "失败"
            android.util.Log.e(r5, r0)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r4 = r4.getMessage()
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "FileUtils"
            android.util.Log.e(r5, r4)
        L_0x00b5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.FileUtils.savePersonRecord(java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0065 A[SYNTHETIC, Splitter:B:24:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x006d A[SYNTHETIC, Splitter:B:28:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void saveSpeedTestRecord(java.lang.String r4) {
        /*
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = testSpeedResultPath
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = java.io.File.separator
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "Speed.txt"
            r0.<init>(r1, r2)
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x0076 }
            if (r1 != 0) goto L_0x0031
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0076 }
            java.lang.String r2 = r0.getParent()     // Catch:{ Exception -> 0x0076 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0076 }
            r1.mkdirs()     // Catch:{ Exception -> 0x0076 }
            r0.createNewFile()     // Catch:{ Exception -> 0x0076 }
        L_0x0031:
            r1 = 0
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ IOException -> 0x005f }
            r3 = 1
            r2.<init>(r0, r3)     // Catch:{ IOException -> 0x005f }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            r0.<init>()     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            java.lang.StringBuilder r4 = r0.append(r4)     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            java.lang.String r0 = "\n"
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            r2.write(r4)     // Catch:{ IOException -> 0x005a, all -> 0x0057 }
            r2.close()     // Catch:{ IOException -> 0x0052 }
            goto L_0x009a
        L_0x0052:
            r4 = move-exception
        L_0x0053:
            r4.printStackTrace()     // Catch:{ Exception -> 0x0076 }
            goto L_0x009a
        L_0x0057:
            r4 = move-exception
            r1 = r2
            goto L_0x006b
        L_0x005a:
            r4 = move-exception
            r1 = r2
            goto L_0x0060
        L_0x005d:
            r4 = move-exception
            goto L_0x006b
        L_0x005f:
            r4 = move-exception
        L_0x0060:
            r4.printStackTrace()     // Catch:{ all -> 0x005d }
            if (r1 == 0) goto L_0x009a
            r1.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x009a
        L_0x0069:
            r4 = move-exception
            goto L_0x0053
        L_0x006b:
            if (r1 == 0) goto L_0x0075
            r1.close()     // Catch:{ IOException -> 0x0071 }
            goto L_0x0075
        L_0x0071:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0076 }
        L_0x0075:
            throw r4     // Catch:{ Exception -> 0x0076 }
        L_0x0076:
            r4 = move-exception
            java.lang.String r0 = "记录保存结果："
            java.lang.String r1 = "失败"
            android.util.Log.e(r0, r1)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = ""
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r4 = r4.getMessage()
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.String r4 = r4.toString()
            java.lang.String r0 = "FileUtils"
            android.util.Log.e(r0, r4)
        L_0x009a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.FileUtils.saveSpeedTestRecord(java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x006b A[SYNTHETIC, Splitter:B:24:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0073 A[SYNTHETIC, Splitter:B:28:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void saveAllRecord(java.lang.String r4) {
        /*
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = SDCarePath
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "/Result"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = java.io.File.separator
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "record.txt"
            r0.<init>(r1, r2)
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x007c }
            if (r1 != 0) goto L_0x0037
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x007c }
            java.lang.String r2 = r0.getParent()     // Catch:{ Exception -> 0x007c }
            r1.<init>(r2)     // Catch:{ Exception -> 0x007c }
            r1.mkdirs()     // Catch:{ Exception -> 0x007c }
            r0.createNewFile()     // Catch:{ Exception -> 0x007c }
        L_0x0037:
            r1 = 0
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ IOException -> 0x0065 }
            r3 = 1
            r2.<init>(r0, r3)     // Catch:{ IOException -> 0x0065 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0060, all -> 0x005d }
            r0.<init>()     // Catch:{ IOException -> 0x0060, all -> 0x005d }
            java.lang.StringBuilder r4 = r0.append(r4)     // Catch:{ IOException -> 0x0060, all -> 0x005d }
            java.lang.String r0 = "\n"
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ IOException -> 0x0060, all -> 0x005d }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0060, all -> 0x005d }
            r2.write(r4)     // Catch:{ IOException -> 0x0060, all -> 0x005d }
            r2.close()     // Catch:{ IOException -> 0x0058 }
            goto L_0x00a0
        L_0x0058:
            r4 = move-exception
        L_0x0059:
            r4.printStackTrace()     // Catch:{ Exception -> 0x007c }
            goto L_0x00a0
        L_0x005d:
            r4 = move-exception
            r1 = r2
            goto L_0x0071
        L_0x0060:
            r4 = move-exception
            r1 = r2
            goto L_0x0066
        L_0x0063:
            r4 = move-exception
            goto L_0x0071
        L_0x0065:
            r4 = move-exception
        L_0x0066:
            r4.printStackTrace()     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x00a0
            r1.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x00a0
        L_0x006f:
            r4 = move-exception
            goto L_0x0059
        L_0x0071:
            if (r1 == 0) goto L_0x007b
            r1.close()     // Catch:{ IOException -> 0x0077 }
            goto L_0x007b
        L_0x0077:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x007c }
        L_0x007b:
            throw r4     // Catch:{ Exception -> 0x007c }
        L_0x007c:
            r4 = move-exception
            java.lang.String r0 = "记录保存结果："
            java.lang.String r1 = "失败"
            android.util.Log.e(r0, r1)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = ""
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r4 = r4.getMessage()
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.String r4 = r4.toString()
            java.lang.String r0 = "FileUtils"
            android.util.Log.e(r0, r4)
        L_0x00a0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.FileUtils.saveAllRecord(java.lang.String):void");
    }

    public static void checkDir() {
        checkDir(resultPath);
        checkDir(avatarPath);
        checkDir(recordPath);
        checkDir(idCardPath);
        checkDir(testSpeedResultPath);
        checkDir(livePath);
    }

    private static void checkDir(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file.delete();
                return;
            }
            for (File deleteFile : listFiles) {
                deleteFile(deleteFile);
            }
            file.delete();
        }
    }

    public static void copyAssets(Context context, String str, String str2) {
        try {
            String[] list = context.getAssets().list(str);
            if (list.length > 0) {
                new File(str2).mkdirs();
                for (String str3 : list) {
                    copyAssets(context, str + "/" + str3, str2 + "/" + str3);
                }
                return;
            }
            InputStream open = context.getAssets().open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str2));
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    open.close();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0040 A[SYNTHETIC, Splitter:B:26:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0047 A[SYNTHETIC, Splitter:B:30:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean writeYuvFile(java.lang.String r4, byte[] r5) {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x003a }
            r1.<init>(r4)     // Catch:{ Exception -> 0x003a }
            java.io.File r2 = r1.getParentFile()     // Catch:{ Exception -> 0x003a }
            if (r2 == 0) goto L_0x0015
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x003a }
            if (r3 != 0) goto L_0x0015
            r2.mkdirs()     // Catch:{ Exception -> 0x003a }
        L_0x0015:
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x003a }
            if (r2 != 0) goto L_0x0043
            r1.createNewFile()     // Catch:{ Exception -> 0x003a }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x003a }
            r1.<init>(r4)     // Catch:{ Exception -> 0x003a }
            r1.write(r5)     // Catch:{ Exception -> 0x0035, all -> 0x0032 }
            java.io.FileDescriptor r4 = r1.getFD()     // Catch:{ Exception -> 0x0035, all -> 0x0032 }
            r4.sync()     // Catch:{ Exception -> 0x0035, all -> 0x0032 }
            r4 = 1
            r1.close()     // Catch:{ Exception -> 0x0031 }
        L_0x0031:
            return r4
        L_0x0032:
            r4 = move-exception
            r0 = r1
            goto L_0x0045
        L_0x0035:
            r4 = move-exception
            r0 = r1
            goto L_0x003b
        L_0x0038:
            r4 = move-exception
            goto L_0x0045
        L_0x003a:
            r4 = move-exception
        L_0x003b:
            r4.printStackTrace()     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x0043
            r0.close()     // Catch:{ Exception -> 0x0043 }
        L_0x0043:
            r4 = 0
            return r4
        L_0x0045:
            if (r0 == 0) goto L_0x004a
            r0.close()     // Catch:{ Exception -> 0x004a }
        L_0x004a:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.FileUtils.writeYuvFile(java.lang.String, byte[]):boolean");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: java.io.FileInputStream} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0029 A[SYNTHETIC, Splitter:B:19:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0035 A[SYNTHETIC, Splitter:B:25:0x0035] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readYuvFile(java.lang.String r3) {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0022 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x0022 }
            int r3 = r1.available()     // Catch:{ Exception -> 0x001b, all -> 0x0018 }
            byte[] r0 = new byte[r3]     // Catch:{ Exception -> 0x001b, all -> 0x0018 }
            r1.read(r0)     // Catch:{ Exception -> 0x001b, all -> 0x0018 }
            r1.close()     // Catch:{ Exception -> 0x0013 }
            goto L_0x0032
        L_0x0013:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0032
        L_0x0018:
            r3 = move-exception
            r0 = r1
            goto L_0x0033
        L_0x001b:
            r3 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
            goto L_0x0024
        L_0x0020:
            r3 = move-exception
            goto L_0x0033
        L_0x0022:
            r3 = move-exception
            r1 = r0
        L_0x0024:
            r3.printStackTrace()     // Catch:{ all -> 0x0020 }
            if (r0 == 0) goto L_0x0031
            r0.close()     // Catch:{ Exception -> 0x002d }
            goto L_0x0031
        L_0x002d:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0031:
            r0 = r1
        L_0x0032:
            return r0
        L_0x0033:
            if (r0 == 0) goto L_0x003d
            r0.close()     // Catch:{ Exception -> 0x0039 }
            goto L_0x003d
        L_0x0039:
            r0 = move-exception
            r0.printStackTrace()
        L_0x003d:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.FileUtils.readYuvFile(java.lang.String):byte[]");
    }

    public static void generateJpgFile(byte[] bArr, int i, int i2, String str) {
        Bitmap nv21ToBitmap = BitmapHelper.nv21ToBitmap(bArr, i, i2);
        if (nv21ToBitmap != null) {
            BitmapHelper.writeBitmap(str, nv21ToBitmap, 30);
            nv21ToBitmap.recycle();
            return;
        }
        Log.e(TAG, "SavePhotoTask: bitmap is null");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x004a A[SYNTHETIC, Splitter:B:27:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0055 A[SYNTHETIC, Splitter:B:33:0x0055] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readText(java.lang.String r5, boolean r6) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            boolean r5 = r0.exists()
            java.lang.String r1 = ""
            if (r5 != 0) goto L_0x000e
            return r1
        L_0x000e:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r2 = 0
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0044 }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ IOException -> 0x0044 }
            r4.<init>(r0)     // Catch:{ IOException -> 0x0044 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0044 }
        L_0x001e:
            java.lang.String r0 = r3.readLine()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            if (r0 == 0) goto L_0x002f
            r5.append(r0)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            if (r6 == 0) goto L_0x001e
            java.lang.String r0 = "\n"
            r5.append(r0)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            goto L_0x001e
        L_0x002f:
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r3.close()     // Catch:{ IOException -> 0x0037 }
            goto L_0x003b
        L_0x0037:
            r6 = move-exception
            r6.printStackTrace()
        L_0x003b:
            return r5
        L_0x003c:
            r5 = move-exception
            r2 = r3
            goto L_0x0053
        L_0x003f:
            r5 = move-exception
            r2 = r3
            goto L_0x0045
        L_0x0042:
            r5 = move-exception
            goto L_0x0053
        L_0x0044:
            r5 = move-exception
        L_0x0045:
            r5.printStackTrace()     // Catch:{ all -> 0x0042 }
            if (r2 == 0) goto L_0x0052
            r2.close()     // Catch:{ IOException -> 0x004e }
            goto L_0x0052
        L_0x004e:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0052:
            return r1
        L_0x0053:
            if (r2 == 0) goto L_0x005d
            r2.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x005d
        L_0x0059:
            r6 = move-exception
            r6.printStackTrace()
        L_0x005d:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.FileUtils.readText(java.lang.String, boolean):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0020 A[SYNTHETIC, Splitter:B:16:0x0020] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002b A[SYNTHETIC, Splitter:B:21:0x002b] */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeStringData(java.lang.String r3, java.lang.String r4) {
        /*
            r0 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x001a }
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ IOException -> 0x001a }
            r2.<init>(r4)     // Catch:{ IOException -> 0x001a }
            r1.<init>(r2)     // Catch:{ IOException -> 0x001a }
            r1.write(r3)     // Catch:{ IOException -> 0x0015, all -> 0x0012 }
            r1.close()     // Catch:{ IOException -> 0x0024 }
            goto L_0x0028
        L_0x0012:
            r3 = move-exception
            r0 = r1
            goto L_0x0029
        L_0x0015:
            r3 = move-exception
            r0 = r1
            goto L_0x001b
        L_0x0018:
            r3 = move-exception
            goto L_0x0029
        L_0x001a:
            r3 = move-exception
        L_0x001b:
            r3.printStackTrace()     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x0028
            r0.close()     // Catch:{ IOException -> 0x0024 }
            goto L_0x0028
        L_0x0024:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0028:
            return
        L_0x0029:
            if (r0 == 0) goto L_0x0033
            r0.close()     // Catch:{ IOException -> 0x002f }
            goto L_0x0033
        L_0x002f:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0033:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.utils.FileUtils.writeStringData(java.lang.String, java.lang.String):void");
    }
}
