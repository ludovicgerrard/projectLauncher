package com.zktechnology.android.service;

public interface IPCCallBack {
    public static final String BIO_TYPE = "bio_type";
    public static final String CMD_ID = "cmdId";
    public static final String PIC_NAME = "picName";
    public static final String PIC_PATH = "picPath";
    public static final String TYPE = "type";
    public static final String USER_PIN = "user_pin";

    void deleteAll();

    void deleteSingleUser(String str);

    void onTemplateUpdate(String str, int i);

    void onUserUpdate(String str, String str2, String str3, String str4);
}
