package com.zkteco.edk.hardware.encrypt;

import android.content.Context;
import android.content.pm.PackageInfo;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;

final class ZkCrashHandler implements Thread.UncaughtExceptionHandler {
    private static volatile ZkCrashHandler mZkCrashHandler = new ZkCrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private ZkCrashHandler() {
    }

    public static ZkCrashHandler getInstance() {
        if (mZkCrashHandler == null) {
            synchronized (ZkCrashHandler.class) {
                if (mZkCrashHandler == null) {
                    mZkCrashHandler = new ZkCrashHandler();
                }
            }
        }
        return mZkCrashHandler;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable th) {
        th.printStackTrace();
        handleException(th);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mDefaultHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    private boolean handleException(Throwable th) {
        if (th == null) {
            return false;
        }
        saveException(th);
        return true;
    }

    private void saveException(Throwable th) {
        try {
            saveCrashInfoFile(th);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveCrashInfoFile(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
                cause.printStackTrace(printWriter);
            }
            printWriter.flush();
            printWriter.close();
            stringBuffer.append(stringWriter.toString());
            PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 1);
            ZkFileLogUtils.saveLogRecord(stringBuffer.toString(), String.format("%s_%s", new Object[]{packageInfo.packageName, packageInfo.versionName}));
        } catch (Exception e) {
            ZkFileLogUtils.saveLogRecord(" 保存crash info 发生异常 " + e.toString() + " \r\n 保存的部分内容：" + stringBuffer.toString(), "CrashCommonError");
        }
    }
}
