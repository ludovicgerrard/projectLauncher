package com.zktechnology.android.utils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.PowerManager;
import android.os.Process;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.verify.server.BaseServerImpl;
import com.zkteco.android.db.DBConfig;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppUtils {
    public static int dp2px(Context context, float f) {
        return Math.round(f * context.getResources().getDisplayMetrics().density);
    }

    public static int px2dp(Context context, float f) {
        return Math.round(f / context.getResources().getDisplayMetrics().density);
    }

    public static int px2sp(int i) {
        return Math.round(((float) i) / LauncherApplication.getLauncherApplicationContext().getResources().getDisplayMetrics().scaledDensity);
    }

    public static float sp2px(int i) {
        return (float) Math.round(((float) i) * LauncherApplication.getLauncherApplicationContext().getResources().getDisplayMetrics().scaledDensity);
    }

    public static String getString(int i) {
        return LauncherApplication.getLauncherApplicationContext().getResources().getString(i);
    }

    public static void restartLauncher(Context context, String str) {
        FileLogUtils.writeLauncherInitRecord("restart launcher -> reason: " + str);
        Intent intent = new Intent(context, ZKLauncher.class);
        intent.putExtra("crash", true);
        intent.addFlags(268435456);
        ((AlarmManager) LauncherApplication.getApplication().getBaseContext().getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + 100, PendingIntent.getActivity(LauncherApplication.getApplication().getBaseContext(), 0, intent, 1073741824));
        Process.killProcess(Process.myPid());
    }

    public static void recordZkFaceException(String str) {
        try {
            String format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(new Date());
            StringBuilder sb = new StringBuilder(2048);
            sb.append("---------------------------------------").append("\n");
            sb.append("time:").append(format).append("\n");
            sb.append("reboot reason:").append(str).append("\n");
            FileLogUtils.writeVerifyExceptionLog(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rebootSystem(Context context, String str) {
        try {
            String format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(new Date());
            StringBuilder sb = new StringBuilder(2048);
            sb.append("---------------------------------------").append("\n");
            sb.append("time:").append(format).append("\n");
            sb.append("reboot reason:").append(str).append("\n");
            FileLogUtils.writeRebootLog(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((PowerManager) context.getSystemService("power")).reboot((String) null);
    }

    public static int execCommands(String... strArr) throws IOException {
        Process exec = Runtime.getRuntime().exec("sh");
        DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
        int i = 0;
        while (i < strArr.length) {
            try {
                dataOutputStream.writeBytes(strArr[i] + "\n");
                dataOutputStream.flush();
                i++;
            } catch (InterruptedException unused) {
                return (byte) 1;
            } finally {
                dataOutputStream.close();
            }
        }
        dataOutputStream.writeBytes("exit\n");
        dataOutputStream.flush();
        exec.waitFor();
        int exitValue = exec.exitValue();
        Log.d(BaseServerImpl.TAG, "process result is " + exitValue);
        return exitValue;
    }

    public static void startUpgradeService(Context context) {
        boolean isInstallUpgradeApplication = isInstallUpgradeApplication(context);
        boolean isUpgradeServiceRunning = isUpgradeServiceRunning(context);
        if (isInstallUpgradeApplication && !isUpgradeServiceRunning) {
            try {
                boolean z = false;
                if (DBManager.getInstance().getIntOption(DBConfig.IS_UPGRADE_BY_CLOUDS, 0) == 1) {
                    z = true;
                }
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.zkteco.android.zkupgrade", "com.zkteco.android.zkupgrade.UpgradeActivity"));
                intent.putExtra("needConnect", z);
                intent.setFlags(268435456);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isInstallUpgradeApplication(Context context) {
        return isInstalledApplication(context, "com.zkteco.android.zkupgrade");
    }

    public static boolean isInstalledApplication(Context context, String str) {
        List<PackageInfo> installedPackages;
        if (!(context == null || str == null || str.isEmpty() || (installedPackages = context.getPackageManager().getInstalledPackages(0)) == null || installedPackages.isEmpty())) {
            for (int i = 0; i < installedPackages.size(); i++) {
                if (str.equals(installedPackages.get(i).packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isUpgradeServiceRunning(Context context) {
        return isServiceRunning(context, "com.zkteco.android.zkupgrade.UpgradeService");
    }

    public static boolean isServiceRunning(Context context, String str) {
        ActivityManager activityManager;
        if ("".equals(str) || str == null || (activityManager = (ActivityManager) context.getSystemService("activity")) == null) {
            return false;
        }
        ArrayList arrayList = (ArrayList) activityManager.getRunningServices(500);
        for (int i = 0; i < arrayList.size(); i++) {
            if (((ActivityManager.RunningServiceInfo) arrayList.get(i)).service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }
}
