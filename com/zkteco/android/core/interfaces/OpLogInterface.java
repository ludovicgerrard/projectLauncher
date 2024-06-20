package com.zkteco.android.core.interfaces;

public interface OpLogInterface {
    public static final String OPLOG_ADD = "oplog-add";
    public static final int OPTYPE_CHANGE_SETTING = 5;
    public static final int OPTYPE_COPY_DATATODEVICE = 20;
    public static final int OPTYPE_CREATE_MF = 14;
    public static final int OPTYPE_DEL_ADMINPERMISSION = 24;
    public static final int OPTYPE_DEL_ATTPHOTO = 35;
    public static final int OPTYPE_DEL_DATA = 13;
    public static final int OPTYPE_DEL_FINGER = 10;
    public static final int OPTYPE_DEL_MFCONTEXT = 18;
    public static final int OPTYPE_DEL_MFENREGISTER = 17;
    public static final int OPTYPE_DEL_PASSWORD = 11;
    public static final int OPTYPE_DEL_RFCARD = 12;
    public static final int OPTYPE_DEL_TRANSFERRECORD = 23;
    public static final int OPTYPE_DEL_USER = 9;
    public static final int OPTYPE_ENREGISTER_MF = 16;
    public static final int OPTYPE_GOTO_MENU = 4;
    public static final int OPTYPE_MODIFY_ACCGROUPSET = 25;
    public static final int OPTYPE_MODIFY_ACCTIME = 27;
    public static final int OPTYPE_MODIFY_FPPROPERTY = 31;
    public static final int OPTYPE_MODIFY_UNLOCKCOMBINATIONSET = 28;
    public static final int OPTYPE_MODIFY_USERACCSET = 26;
    public static final int OPTYPE_MODIFY_USEROTHERINFO = 36;
    public static final int OPTYPE_MOVE_DATATOSD = 19;
    public static final int OPTYPE_REGISTER_FINGER = 6;
    public static final int OPTYPE_REGISTER_HID = 8;
    public static final int OPTYPE_REGISTER_MF = 15;
    public static final int OPTYPE_REGISTER_PASSWORD = 7;
    public static final int OPTYPE_REGISTER_USER = 30;
    public static final int OPTYPE_RESISTDIVE = 34;
    public static final int OPTYPE_RESTORE_FACTORY = 22;
    public static final int OPTYPE_SET_TIME = 21;
    public static final int OPTYPE_STRESSALARM = 32;
    public static final int OPTYPE_UNLOCK = 29;

    void addOpLog(int i, String str, int i2, int i3, int i4);
}
