package com.zkteco.edk.hardware.encrypt.task;

import android.content.Context;
import android.os.Debug;
import android.os.Process;
import android.util.Log;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import com.zkteco.edk.hardware.encrypt.ZkHardwareUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

final class ZkDebuggerUtils {
    ZkDebuggerUtils() {
    }

    public static boolean isDebuggable(Context context) {
        if (context == null) {
            return false;
        }
        try {
            if ((context.getApplicationInfo().flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void checkDebuggableInNotDebugModel(Context context) {
        if (!ZkHardwareUtils.isDebug()) {
            ZkThreadPoolManager.getInstance().execute(new Runnable(context) {
                public final /* synthetic */ Context f$0;

                {
                    this.f$0 = r1;
                }

                public final void run() {
                    ZkDebuggerUtils.lambda$checkDebuggableInNotDebugModel$0(this.f$0);
                }
            });
        }
    }

    static /* synthetic */ void lambda$checkDebuggableInNotDebugModel$0(Context context) {
        if (isDebuggable(context)) {
            killProcess("已被动态调试");
        }
        if (Debug.isDebuggerConnected()) {
            killProcess("已被动态调试");
        }
        if (isUnderTraced()) {
            killProcess("已被其他恶意进程跟踪");
        }
    }

    static void killProcess(String str) {
        Log.e(BiometricCommuCMD.FIELD_DESC_ERROR, String.format("%s pid:%s uid:%s", new Object[]{str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myUid())}));
        System.exit(0);
    }

    private static boolean isUnderTraced() {
        FileReader fileReader;
        BufferedReader bufferedReader;
        Exception e;
        BufferedReader bufferedReader2;
        try {
            fileReader = new FileReader(new File(String.format(Locale.US, "/proc/%d/status", new Object[]{Integer.valueOf(Process.myPid())})));
            try {
                bufferedReader = new BufferedReader(fileReader);
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        } else if (readLine.contains("TracerPid")) {
                            String[] split = readLine.split(":");
                            if (split.length == 2 && Integer.parseInt(split[1].trim()) != 0) {
                                close(bufferedReader);
                                close(fileReader);
                                return true;
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            e.printStackTrace();
                            close(bufferedReader);
                            close(fileReader);
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            close(bufferedReader);
                            close(fileReader);
                            throw th;
                        }
                    }
                }
            } catch (Exception e3) {
                e = e3;
                bufferedReader2 = null;
                e = e;
                e.printStackTrace();
                close(bufferedReader);
                close(fileReader);
                return false;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                th = th;
                close(bufferedReader);
                close(fileReader);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            bufferedReader2 = null;
            fileReader = null;
            e = e;
            e.printStackTrace();
            close(bufferedReader);
            close(fileReader);
            return false;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            fileReader = null;
            th = th;
            close(bufferedReader);
            close(fileReader);
            throw th;
        }
        close(bufferedReader);
        close(fileReader);
        return false;
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
