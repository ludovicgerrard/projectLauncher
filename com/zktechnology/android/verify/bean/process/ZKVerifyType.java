package com.zktechnology.android.verify.bean.process;

import android.text.TextUtils;
import com.zktechnology.android.qrcode.Intents;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;

public enum ZKVerifyType {
    NONE(0),
    FINGER(1),
    PIN(2),
    PASSWORD(3),
    CARD(4),
    FACE(5),
    URGENTPASSWORD(6),
    PALM(7);
    
    private int value;

    private ZKVerifyType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public static ZKVerifyType fromInteger(int i) {
        if (i == 0) {
            return NONE;
        }
        if (i == 1) {
            return FINGER;
        }
        if (i == 2) {
            return PIN;
        }
        if (i == 3) {
            return PASSWORD;
        }
        if (i == 4) {
            return CARD;
        }
        if (i == 5) {
            return FACE;
        }
        if (i == 6) {
            return URGENTPASSWORD;
        }
        if (i == 7) {
            return PALM;
        }
        return null;
    }

    public static ZKVerifyType fromName(String str) {
        if (TextUtils.isEmpty(str)) {
            return NONE;
        }
        if (str.equals("FINGER")) {
            return FINGER;
        }
        if (str.equals(BiometricCommuCMD.FIELD_DESC_TMP_PIN)) {
            return PIN;
        }
        if (str.equals(Intents.WifiConnect.PASSWORD)) {
            return PASSWORD;
        }
        if (str.equals("CARD")) {
            return CARD;
        }
        if (str.equals("FACE")) {
            return FACE;
        }
        if (str.equals("URGENTPASSWORD")) {
            return URGENTPASSWORD;
        }
        if (str.equals("PALM")) {
            return PALM;
        }
        return null;
    }
}
