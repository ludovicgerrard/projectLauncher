package com.zktechnology.android.launcher2;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.AppUtils;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static CrashHandler crashHandler = new CrashHandler();
    private Map<String, String> infos = new HashMap();
    /* access modifiers changed from: private */
    public Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private String time = "";

    private void upLoadErrorFileToServer(File file) {
    }

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            synchronized (CrashHandler.class) {
                if (crashHandler == null) {
                    crashHandler = new CrashHandler();
                }
            }
        }
        return crashHandler;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable th) {
        th.printStackTrace();
        handlelException(th);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mDefaultHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    private boolean handlelException(Throwable th) {
        if (th == null) {
            return false;
        }
        saveException(th);
        return true;
    }

    public void collectDeviceInfo() {
        try {
            PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 1);
            if (packageInfo != null) {
                this.infos.put("versionName", packageInfo.versionName + "");
                this.infos.put("versionCode", packageInfo.versionCode + "");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.infos.put(field.getName(), field.get((Object) null).toString());
            } catch (Exception e2) {
                Log.e(TAG, "an error occured when collect crash info", e2);
            }
        }
    }

    private String saveCrashInfoFile(Throwable th) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append("\r\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()) + "\n");
            for (Map.Entry next : this.infos.entrySet()) {
                stringBuffer.append(((String) next.getKey()) + SimpleComparison.EQUAL_TO_OPERATION + ((String) next.getValue()) + "\n");
            }
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
                cause.printStackTrace(printWriter);
            }
            printWriter.flush();
            printWriter.close();
            stringBuffer.append(stringWriter.toString());
            return writeFile(stringBuffer.toString());
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
            stringBuffer.append("an error occured while writing file...\r\n");
            writeFile(stringBuffer.toString());
            return null;
        }
    }

    private void saveException(Throwable th) {
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(CrashHandler.this.mContext, CrashHandler.this.mContext.getString(R.string.un_handle_exception), 1).show();
                Looper.loop();
            }
        }.start();
        SystemClock.sleep(3000);
        collectDeviceInfo();
        try {
            autoClear();
            saveCrashInfoFile(th);
        } catch (Exception e) {
            e.printStackTrace();
        }
        restartApp();
    }

    private String writeFile(String str) throws Exception {
        this.time = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        String str2 = "crashLog-" + this.time + ".log";
        String str3 = ZKFilePath.LOGCAT_PATH;
        File file = new File(str3);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(str3 + "/" + str2, true);
        fileOutputStream.write(str.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        return str2;
    }

    private void autoClear() {
        delete(ZKFilePath.LOGCAT_PATH, new FilenameFilter() {
            public boolean accept(File file, String str) {
                return new StringBuilder().append("crashLog-").append(DateUtil.getOtherDay(7)).toString().compareTo(CrashHandler.getFileNameWithoutExtension(str)) >= 0;
            }
        });
    }

    public static void delete(String str, FilenameFilter filenameFilter) {
        File[] fileArr;
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (file.exists()) {
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    if (filenameFilter != null) {
                        fileArr = file.listFiles(filenameFilter);
                    } else {
                        fileArr = file.listFiles();
                    }
                    if (fileArr != null) {
                        for (File file2 : fileArr) {
                            if (file2.isFile()) {
                                file2.delete();
                            }
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static String getFileNameWithoutExtension(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(".");
        int lastIndexOf2 = str.lastIndexOf(File.separator);
        if (lastIndexOf2 == -1) {
            return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
        }
        if (lastIndexOf == -1) {
            return str.substring(lastIndexOf2 + 1);
        }
        if (lastIndexOf2 < lastIndexOf) {
            return str.substring(lastIndexOf2 + 1, lastIndexOf);
        }
        return str.substring(lastIndexOf2 + 1);
    }

    private void restartApp() {
        AppUtils.restartLauncher(LauncherApplication.getLauncherApplicationContext(), "Application Encountered a exception");
    }
}
