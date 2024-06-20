package com.zkteco.edk.camera.lib.util;

import android.os.Environment;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
    private static final int D = 2;
    private static final int E = 3;
    private static final int I = 0;
    private static String SDCarePath = Environment.getExternalStorageDirectory().toString();
    private static final String TAG = "ZkCamera";
    private static final int V = 1;
    private static final int W = 4;
    private static boolean mIsShowLog = false;
    private static String path = (SDCarePath + "/ZKTeco/trace/");

    private static String formatTag(String str) {
        return str;
    }

    public static void initLog(boolean z) {
        mIsShowLog = z;
    }

    public static void e(String str) {
        print(3, "ZkCamera", str);
    }

    public static void e(String str, Throwable th) {
        print(3, str, th.getMessage());
    }

    public static void e(String str, String str2) {
        print(3, str, str2);
    }

    public static void e(String str, String str2, Object... objArr) {
        print(3, str, String.format(str2, objArr));
    }

    public static void d(String str) {
        print(2, "ZkCamera", str);
    }

    public static void d(String str, String str2) {
        print(2, str, str2);
    }

    public static void d(String str, String str2, Object... objArr) {
        print(2, str, String.format(str2, objArr));
    }

    public static void i(String str) {
        print(0, "ZkCamera", str);
    }

    public static void i(String str, String str2) {
        print(0, str, str2);
    }

    public static void i(String str, String str2, Object... objArr) {
        print(0, str, String.format(str2, objArr));
    }

    public static void v(String str) {
        print(1, "ZkCamera", str);
    }

    public static void v(String str, String str2) {
        print(1, str, str2);
    }

    public static void v(String str, String str2, Object... objArr) {
        print(1, str, String.format(str2, objArr));
    }

    public static void w(String str) {
        print(4, "ZkCamera", str);
    }

    public static void w(String str, String str2) {
        print(4, str, str2);
    }

    public static void w(String str, String str2, Object... objArr) {
        print(4, str, String.format(str2, objArr));
    }

    private static void print(int i, String str, String str2) {
        if (mIsShowLog) {
            if (i == 0) {
                Log.i(formatTag(str), formatValue(str2));
            } else if (i == 1) {
                Log.v(formatTag(str), formatValue(str2));
            } else if (i == 2) {
                Log.d(formatTag(str), formatValue(str2));
            } else if (i == 3) {
                Log.e(formatTag(str), formatValue(str2));
            } else if (i == 4) {
                Log.w(formatTag(str), formatValue(str2));
            }
        }
    }

    private static String formatValue(String str) {
        return str + getLineNum();
    }

    private static String getSimpleClassName() {
        String className = Thread.currentThread().getStackTrace()[6].getClassName();
        return className.substring(className.lastIndexOf(".") + 1);
    }

    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[6].getMethodName();
    }

    private static String getLineNum() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[6];
        return ".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
    }

    public static void writeLog(String str, String str2) {
        synchronized (LogUtils.class) {
            if (mIsShowLog) {
                File file = new File(path + str);
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    if (file.length() > 3145728) {
                        file.delete();
                        file.createNewFile();
                    }
                    methodBufferedWriter(file, (str2 + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "    ") + "    \r\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void methodBufferedWriter(File file, String str) {
        BufferedWriter bufferedWriter = null;
        try {
            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            try {
                bufferedWriter2.write(str);
                try {
                    bufferedWriter2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e = e2;
                bufferedWriter = bufferedWriter2;
                try {
                    e.printStackTrace();
                    bufferedWriter.close();
                } catch (Throwable th) {
                    th = th;
                    try {
                        bufferedWriter.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedWriter = bufferedWriter2;
                bufferedWriter.close();
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            bufferedWriter.close();
        }
    }
}
