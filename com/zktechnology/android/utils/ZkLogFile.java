package com.zktechnology.android.utils;

import android.os.Environment;

public interface ZkLogFile {
    public static final String CAMERA_LOG = "Camera.txt";
    public static final String CLEAR_PHOTO_LOG = "ClearPhoto.txt";
    public static final String DATABASE_LOG = "Database.txt";
    public static final String EXTRACT_LOG = "Extract.txt";
    public static final String LAUNCHER_INIT_EXCEPTION_LOG = "LauncherInitException.txt";
    public static final String LAUNCHER_INIT_LOG = "LauncherInit.txt";
    public static final String NETWORK_LOG = "Network.txt";
    public static final String REBOOT_LOG = "Reboot.txt";
    public static final String SDCARD_PATH;
    public static final int SIZE_1M = 1048576;
    public static final int SIZE_3M = 3145728;
    public static final int SIZE_50M = 52428800;
    public static final int SIZE_5M = 3145728;
    public static final String STATE_LOG = "State.txt";
    public static final String TEMPLATE_LOG = "Template.txt";
    public static final String TOUCH_LOG = "Touch.txt";
    public static final String TRACE_PATH;
    public static final String VERIFY_EXCEPTION_LOG = "VerifyException.txt";
    public static final String VERIFY_LOG = "Verify.txt";
    public static final String VERIFY_SUCCESS_LOG = "VerifySuccess.txt";
    public static final String WIEGAND_LOG = "Wiegand.txt";
    public static final String ZKTECO_PATH;

    static {
        String file = Environment.getExternalStorageDirectory().toString();
        SDCARD_PATH = file;
        String str = file + "/ZKTeco/";
        ZKTECO_PATH = str;
        TRACE_PATH = str + "/trace/";
    }
}
