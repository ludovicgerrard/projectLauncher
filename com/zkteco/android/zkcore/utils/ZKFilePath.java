package com.zkteco.android.zkcore.utils;

import android.os.Environment;

public class ZKFilePath {
    public static String APP_PATH = (SDCarePath + "/ZKTeco/");
    public static String ATT_ENCRYPT_LOG_PATH = "_AttEncryptLog.dat";
    public static String ATT_LOG_PATH = "_attlog.dat";
    public static String ATT_PHOTO_PATH = (IMAGE_PATH + "capture/pass/");
    public static String BACKUP_PATH = (APP_PATH + "backup/");
    public static String BIO_PICTURE_PATH = (IMAGE_PATH + "biophoto/face/");
    public static String BLACK_LIST_PATH = (IMAGE_PATH + "capture/bad/");
    public static final String CACHE_PATH = (SDCarePath + "/tombstones/");
    public static String DATABASE_PATH = "/data/data/com.zkteco.android.core/databases/";
    public static final String FACE_PATH = (IMAGE_PATH + "biophoto/face/");
    public static String FACE_PIC_PATH = "/biophoto/face";
    public static String IMAGE_PATH = (APP_PATH + "data/");
    public static String LOGCAT_PATH = (APP_PATH + "trace/");
    public static String PICTURE_PATH = (IMAGE_PATH + "photo/");
    private static String SDCarePath = Environment.getExternalStorageDirectory().toString();
    public static String SHORT_MESSAGE_PATH = "/sms.dat";
    public static String SOUND_PATH = (APP_PATH + "sounds/");
    public static String SUFFIX_IMAGE = ".jpg";
    public static String USERFACEDATA_PATH = "/faceTemplateData.dat";
    public static String USERFACE_PATH = "/faceTemplate.dat";
    public static String USERFINGER_PATH = "/template.fp10";
    public static String USERFINGER_PATH2 = "/template.fp10.1";
    public static String USER_FINGER_12_PATH = "/fingerTemplate12.dat";
    public static String USER_FINGER_DATA_12_PATH = "/fingerTemplateData12.dat";
    public static String USER_MESSAGE_PATH = "/udata.dat";
    public static String USER_PATH = "/user.dat";
    public static String U_ATT_PHOTO_PATH = "/attPhoto/";
    public static String U_BLACK_LIST_PATH = "/blacklist/";
    public static String U_PICTURE_PATH = "/photo/";
    public static String U_WALLPAPER_PATH = "/wallpaper/";
    public static String WALLPAPER_PATH = (IMAGE_PATH + "wallpaper/");
    public static String WORK_CODE_PATH = "/workcode.dat";
}
