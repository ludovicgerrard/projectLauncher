package com.zktechnology.android.utils;

public interface GlobalConfig {
    public static final String ACTION_GET = "get ";
    public static final String ACTION_PUT = "put ";
    public static final String BASE_COMMAND = "device_config ";
    public static final int CAMERA_TYPE_ANDROID_COLOR_AND_GRAY = 2;
    public static final int CAMERA_TYPE_COLOR = 1;
    public static final String DEVICE_NAME_COMMAND = "DeviceName";
    public static final int FACE_ALGO_MAJOR_VER = 5;
    public static final int FACE_ALGO_MINOR_VER = 622;
    public static final int FACE_FEATURE_SIZE = 256;
    public static final String GET_DEVICE_NAME_COMMAND = "device_config get ZKTECO DeviceName";
    public static final String GET_DEVICE_SN_COMMAND = "device_config get ZKTECO SerialNumber";
    public static final String IS_FACE_AUTHORIZED = "isAuthorized";
    public static final int MESSAGE_ATT_STATE_CHANGE_X = 16;
    public static final int MESSAGE_ATT_STATE_CHANGE_Y = 32;
    public static final int MESSAGE_ATT_STATE_DEFAULT = 64;
    public static final int MESSAGE_DISMISS_DIALOG = 128;
    public static final int MESSAGE_ENTER_WORK_MODE = 4;
    public static final int MESSAGE_EXIT_WORK_MODE = 8;
    public static final int MESSAGE_MASK_TYPE = 2;
    public static final int MESSAGE_TEMPERATURE_GETTING = 1;
    public static final String NAMESPACE = "ZKTECO ";
    public static final String SERIAL_NUMBER_COMMAND = "SerialNumber";
    public static final String SHARED_KEY_CAMERA_CONFIG = "sp_key_camera_config";
    public static final String SHARED_KEY_DEVICE_TYPE = "device-type";
    public static final String SHARED_KEY_SUPPORT_FACE_ANTI_FAKE = "sp_key_support_face_anti_fake";
    public static final String SHARED_KEY_SUPPORT_PALM = "sp_key_support_palm";
}
