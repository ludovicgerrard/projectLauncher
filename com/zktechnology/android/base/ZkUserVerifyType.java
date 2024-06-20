package com.zktechnology.android.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ZkUserVerifyType {
    public static final int USER_VT_CARD = 4;
    public static final int USER_VT_FACE = 15;
    public static final int USER_VT_FACE_AND_CARD = 18;
    public static final int USER_VT_FACE_AND_FINGER = 16;
    public static final int USER_VT_FACE_AND_FINGER_AND_CARD = 19;
    public static final int USER_VT_FACE_AND_FINGER_AND_PW = 20;
    public static final int USER_VT_FACE_AND_PASSWORD = 17;
    public static final int USER_VT_FINGER = 1;
    public static final int USER_VT_FINGER_AND_CARD = 10;
    public static final int USER_VT_FINGER_AND_PW = 9;
    public static final int USER_VT_FINGER_AND_PW_AND_CARD = 12;
    public static final int USER_VT_FINGER_CARD_OR_PIN = 14;
    public static final int USER_VT_FINGER_OR_CARD = 6;
    public static final int USER_VT_FINGER_OR_PW = 5;
    public static final int USER_VT_FINGER_OR_PW_OR_CARD_OR_FACE_OR_PALM = 0;
    public static final int USER_VT_GROUP = -1;
    public static final int USER_VT_PALM = 25;
    public static final int USER_VT_PIN = 2;
    public static final int USER_VT_PIN_AND_FINGER = 8;
    public static final int USER_VT_PIN_AND_FINGER_AND_PW = 13;
    public static final int USER_VT_PW = 3;
    public static final int USER_VT_PW_AND_CARD = 11;
    public static final int USER_VT_PW_OR_CARD = 7;
}
