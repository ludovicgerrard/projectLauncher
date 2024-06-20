package com.zktechnology.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.SharedPreferencesUtils;
import com.zktechnology.android.utils.ZKThreadPool;
import com.zkteco.android.zkcore.wiegand.WiegandLogUtils;
import java.io.IOException;

public class LogDebugReceiver extends BroadcastReceiver {
    public static final String DEBUG_MOTION_DETECT_OFF = "com.zkteco.motion_detect.debug.off";
    public static final String DEBUG_MOTION_DETECT_ON = "com.zkteco.motion_detect.debug.on";
    public static final String DEBUG_SWITCH_OFF = "com.zkteco.log.debug.off";
    public static final String DEBUG_SWITCH_ON = "com.zkteco.log.debug.on";
    public static final String KILL_CAMERA_PROCESS = "com.zkteco.kill.camera.progress";
    public static final String SET_CPU_MAX_FREQ = "com.zkteco.cpu.max.freq";
    private static final String TAG = "LogDebugReceiver";
    public static final String VERIFY_CTRL_LOG_SWITCH_NAME = "verify_ctrl_log_switch";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            action.hashCode();
            char c2 = 65535;
            switch (action.hashCode()) {
                case -2065974097:
                    if (action.equals(DEBUG_MOTION_DETECT_ON)) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -1059562757:
                    if (action.equals(SET_CPU_MAX_FREQ)) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -419041577:
                    if (action.equals(DEBUG_SWITCH_OFF)) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 379312287:
                    if (action.equals(DEBUG_MOTION_DETECT_OFF)) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 540671863:
                    if (action.equals(DEBUG_SWITCH_ON)) {
                        c2 = 4;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    ZkFaceLauncher.isDebugFaceLauncher = true;
                    Log.i(TAG, "onReceive: debug motion detect is on");
                    return;
                case 1:
                    final int intExtra = intent.getIntExtra("cpu_max_freq", 1200000);
                    ZKThreadPool.getInstance().commitTask(new Runnable() {
                        public void run() {
                            SharedPreferencesUtils.set(LauncherApplication.getLauncherApplicationContext(), "cpu_max_freq", Integer.valueOf(intExtra));
                            try {
                                AppUtils.execCommands("echo " + intExtra + " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.i(LogDebugReceiver.TAG, "onReceive: set cpu max freq as " + intExtra);
                        }
                    });
                    return;
                case 2:
                    WiegandLogUtils.getInstance().setLog(false);
                    LogUtils.setDebug(false);
                    Log.i(TAG, "onReceive: verify controller log off");
                    SharedPreferencesUtils.getInstance().putString(VERIFY_CTRL_LOG_SWITCH_NAME, "false");
                    return;
                case 3:
                    ZkFaceLauncher.isDebugFaceLauncher = false;
                    Log.i(TAG, "onReceive: debug motion detect is off");
                    return;
                case 4:
                    LogUtils.setDebug(true);
                    WiegandLogUtils.getInstance().setLog(true);
                    Log.i(TAG, "onReceive: verify controller log on");
                    SharedPreferencesUtils.getInstance().putString(VERIFY_CTRL_LOG_SWITCH_NAME, "true");
                    return;
                default:
                    return;
            }
        }
    }
}
