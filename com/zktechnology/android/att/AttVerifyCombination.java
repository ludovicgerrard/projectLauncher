package com.zktechnology.android.att;

import com.zktechnology.android.qrcode.Intents;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import java.util.ArrayList;
import java.util.List;

public enum AttVerifyCombination {
    ALL(0, "FINGER,PIN,PASSWORD,CARD,FACE,PALM", false),
    FINGER(1, "FINGER"),
    PIN(2, BiometricCommuCMD.FIELD_DESC_TMP_PIN),
    PASSWORD(3, Intents.WifiConnect.PASSWORD),
    CARD(4, "CARD"),
    FINGER_OR_PASSWORD(5, "FINGER,PASSWORD", false),
    CARD_OR_FINGER(6, "CARD,FINGER", false),
    CARD_OR_PASSWORD(7, "CARD,PASSWORD", false),
    PIN_AND_FINGER(8, "PIN,FINGER", true),
    FINGER_AND_PASSWORD(9, "FINGER,PASSWORD", true),
    CARD_AND_FINGER(10, "CARD,FINGER", true),
    CARD_AND_PASSWORD(11, "CARD,PASSWORD", true),
    FINGER_AND_PASSWORD_AND_CARD(12, "FINGER,PASSWORD,CARD", true),
    PIN_AND_FINGER_AND_PASSWORD(13, "PIN,FINGER,PASSWORD", true),
    PIN_AND_FINGER_AND_CARD(14, "PIN,FINGER,CARD", true),
    FACE(15, "FACE"),
    FINGER_AND_FACE(16, "FINGER,FACE", true),
    FACE_AND_PASSWORD(17, "FACE,PASSWORD", true),
    CARD_AND_FACE(18, "CARD,FACE", true),
    FACE_AND_FINGER_AND_CARD(19, "FACE,FINGER,CARD", true),
    FACE_AND_FINGER_AND_PASSWORD(20, "FACE,FINGER,PASSWORD", true),
    FV(21, "FV"),
    FV_AND_PASSWORD(22, "FV,PASSWORD", true),
    FV_AND_CARD(23, "FV,CARD", true),
    FV_AND_CARD_AND_PASSWORD(24, "FV,CARD,PASSWORD", true),
    PALM(25, "PALM"),
    PV_AND_CARD(26, "PV,CARD", true),
    PV_AND_FACE(27, "PV,FACE", true),
    PV_AND_FINGER(28, "PV,FINGER", true),
    PV_AND_FACE_FINGER(29, "PV,FACE,FINGER", true),
    OTHERS(200, "OTHERS");
    
    private boolean and;
    private int value;
    private List<ZKVerifyType> verifyTypes;

    private AttVerifyCombination(int i, String str) {
        this(r7, r8, i, str, false);
    }

    private AttVerifyCombination(int i, String str, boolean z) {
        this.value = i;
        ArrayList arrayList = new ArrayList();
        String[] split = str.split(",");
        if (split.length > 0) {
            for (String fromName : split) {
                ZKVerifyType fromName2 = ZKVerifyType.fromName(fromName);
                if (fromName2 != null) {
                    arrayList.add(fromName2);
                }
            }
        }
        this.verifyTypes = arrayList;
        this.and = z;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public boolean isAnd() {
        return this.and;
    }

    public void setAnd(boolean z) {
        this.and = z;
    }

    public List<ZKVerifyType> getVerifyTypes() {
        return this.verifyTypes;
    }

    public void setVerifyTypes(List<ZKVerifyType> list) {
        this.verifyTypes = list;
    }

    public static AttVerifyCombination fromInteger(int i) {
        if (i == 0) {
            return ALL;
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
            return FINGER_OR_PASSWORD;
        }
        if (i == 6) {
            return CARD_OR_FINGER;
        }
        if (i == 7) {
            return CARD_OR_PASSWORD;
        }
        if (i == 8) {
            return PIN_AND_FINGER;
        }
        if (i == 9) {
            return FINGER_AND_PASSWORD;
        }
        if (i == 10) {
            return CARD_AND_FINGER;
        }
        if (i == 11) {
            return CARD_AND_PASSWORD;
        }
        if (i == 12) {
            return FINGER_AND_PASSWORD_AND_CARD;
        }
        if (i == 13) {
            return PIN_AND_FINGER_AND_PASSWORD;
        }
        if (i == 14) {
            return PIN_AND_FINGER_AND_CARD;
        }
        if (i == 15) {
            return FACE;
        }
        if (i == 16) {
            return FINGER_AND_FACE;
        }
        if (i == 17) {
            return FACE_AND_PASSWORD;
        }
        if (i == 18) {
            return CARD_AND_FACE;
        }
        if (i == 19) {
            return FACE_AND_FINGER_AND_CARD;
        }
        if (i == 20) {
            return FACE_AND_FINGER_AND_PASSWORD;
        }
        if (i == 21) {
            return FV;
        }
        if (i == 22) {
            return FV_AND_PASSWORD;
        }
        if (i == 23) {
            return FV_AND_CARD;
        }
        if (i == 24) {
            return FV_AND_CARD_AND_PASSWORD;
        }
        if (i == 25) {
            return PALM;
        }
        if (i == 26) {
            return PV_AND_CARD;
        }
        if (i == 27) {
            return PV_AND_FACE;
        }
        if (i == 28) {
            return PV_AND_FINGER;
        }
        if (i == 29) {
            return PV_AND_FACE_FINGER;
        }
        if (i == 200) {
            return OTHERS;
        }
        return null;
    }
}
