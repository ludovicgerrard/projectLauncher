package com.zkteco.android.io;

import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;

public class PendriveMountHelper {
    private static String PENDRIVE_COMMAND_CHECK_IF_IT_IS_MOUNT = null;
    private static final String PENDRIVE_COMMAND_MOUNT = "mount -t vfat -o rw,fmask=0000,dmask=0000 %s %s";
    private static String PENDRIVE_COMMAND_UMOUNT = null;
    public static String PENDRIVE_MOUNT_POINT = null;
    public static final String TAG = "com.zkteco.android.io.PendriveMountHelper";
    private static final int UNMOUNT_ATTEMPTS = 10;
    private static String lastKnownPath;

    private static String getPendriveDevicePath() {
        try {
            ArrayList arrayList = (ArrayList) ShellHelper.execCommandAndRetrieveStringResult(PENDRIVE_COMMAND_CHECK_IF_IT_IS_MOUNT);
            if (arrayList == null || arrayList.size() <= 0) {
                return null;
            }
            return (String) arrayList.get(0);
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }

    private static boolean isMounted() {
        try {
            ArrayList arrayList = (ArrayList) ShellHelper.execCommandAndRetrieveStringResult(PENDRIVE_COMMAND_CHECK_IF_IT_IS_MOUNT);
            if (arrayList == null || arrayList.size() != 1) {
                return false;
            }
            return true;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }

    public static boolean mount(String str) {
        initVariables(str);
        if (isMounted()) {
            Log.d(TAG, "Pendrive is already mounted");
            return true;
        } else if (lastKnownPath != null) {
            Log.d(TAG, "Pendrive is not mounted, system is going to try to mount it again");
            if (!mountFromPath(lastKnownPath) || !isMounted()) {
                return false;
            }
            return true;
        } else {
            Log.w(TAG, "No pendrive found");
            return false;
        }
    }

    private static void initVariables(String str) {
        PENDRIVE_MOUNT_POINT = str;
        PENDRIVE_COMMAND_CHECK_IF_IT_IS_MOUNT = "mount | busybox grep \"" + PENDRIVE_MOUNT_POINT + "\" | busybox cut -f1 -d' ' ";
        PENDRIVE_COMMAND_UMOUNT = "umount `mount | busybox grep \"" + PENDRIVE_MOUNT_POINT + "\" | busybox cut -f2 -d' ' `";
    }

    private static boolean mountFromPath(String str) {
        try {
            return ShellHelper.execCommands(String.format(PENDRIVE_COMMAND_MOUNT, new Object[]{str, PENDRIVE_MOUNT_POINT})) == 0;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean unmount() {
        try {
            lastKnownPath = getPendriveDevicePath();
            int i = 0;
            while (true) {
                if (ShellHelper.execCommands(PENDRIVE_COMMAND_UMOUNT) == 0 || i >= 10) {
                    break;
                }
                Log.w(TAG, "Pendrive is busy, attempting again in 500ms");
                Thread.sleep(500);
                i++;
            }
        } catch (InterruptedException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (IOException e2) {
            Log.e(TAG, Log.getStackTraceString(e2));
        }
        return true;
    }
}
