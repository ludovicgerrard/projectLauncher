package com.zkteco.android.io;

import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class IOUtils {
    private static final String ECHO_FILE = "echo '%s' > %s";
    private static final String REMOUNT_SYSTEM = "mount -o rw,remount /system";
    private static final String TAG = "IOUtils";

    public static boolean copyFile(FileInputStream fileInputStream, OutputStream outputStream) {
        byte[] bArr = new byte[2048];
        try {
            int read = fileInputStream.read(bArr);
            while (read > 0) {
                outputStream.write(bArr, 0, read);
                read = fileInputStream.read(bArr);
            }
            outputStream.flush();
            closeStream(outputStream);
            closeStream(fileInputStream);
            return true;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            closeStream(outputStream);
            closeStream(fileInputStream);
            return false;
        } catch (Throwable th) {
            closeStream(outputStream);
            closeStream(fileInputStream);
            throw th;
        }
    }

    public static boolean copyFile(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[4096];
        if (inputStream != null) {
            while (true) {
                try {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        outputStream.write(bArr, 0, read);
                    } else {
                        closeStream(inputStream);
                        closeStream(outputStream);
                        return true;
                    }
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                } catch (Throwable th) {
                    closeStream(inputStream);
                    closeStream(outputStream);
                    throw th;
                }
            }
        }
        closeStream(inputStream);
        closeStream(outputStream);
        return false;
    }

    public static boolean copyFile(String str, String str2) throws FileNotFoundException {
        Log.d(TAG, "Moving database from " + str + " " + str2);
        return copyFile(new FileInputStream(str), (OutputStream) new FileOutputStream(str2));
    }

    public static String inputStrToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        try {
            for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                sb.append(readLine).append(10);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return sb.toString();
    }

    public static InputStream stringToInputStream(String str) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(str.getBytes("UTF-8"));
    }

    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    public static String getFileNameFromPath(String str) {
        return new File(str).getName();
    }

    private IOUtils() {
    }
}
