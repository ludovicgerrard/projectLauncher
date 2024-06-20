package com.zkteco.android.db.orm.util;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public final class FileUtil {
    private static final String TAG = "FileUtil";

    public static void deleteDir(String str) {
        Log.d(TAG, "deleteDir-path" + str);
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    file2.delete();
                } else if (file2.isDirectory()) {
                    deleteDir(str);
                }
            }
        }
    }

    public static String readFileSdcardFile(String str) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(str);
        byte[] bArr = new byte[fileInputStream.available()];
        fileInputStream.read(bArr);
        String str2 = new String(bArr, "UTF-8");
        fileInputStream.close();
        return str2;
    }

    public static boolean createFile(String str, String str2) throws IOException {
        if (str2 == null || str2.equals("")) {
            return false;
        }
        File file = new File(str2);
        if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
            PrintWriter printWriter = new PrintWriter(new FileWriter(str2));
            printWriter.println(str);
            printWriter.flush();
            printWriter.close();
            return true;
        }
        Log.d(TAG, "create-directory-fail");
        return false;
    }
}
