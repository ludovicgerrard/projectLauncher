package com.zktechnology.android.verify.bean.process;

import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.ZkDigitConvertUtils;
import java.util.ArrayList;

public class ZKVerViewBean {
    public static final int UI_1V1_SELECT_VERIFY_TYPE = 1;
    public static final int UI_CARD_FAILED = 42;
    public static final int UI_CARD_INVALID = 43;
    public static final int UI_CARD_START = 40;
    public static final int UI_CARD_SUCCESS = 41;
    public static final int UI_FACE_FAILED = 52;
    public static final int UI_FACE_INVALID = 53;
    public static final int UI_FACE_START = 50;
    public static final int UI_FACE_SUCCESS = 51;
    public static final int UI_FINGER_FAILED = 12;
    public static final int UI_FINGER_INVALID = 13;
    public static final int UI_FINGER_START = 10;
    public static final int UI_FINGER_SUCCESS = 11;
    public static final int UI_MULTI_VERIFY_WAIT = 100;
    public static final int UI_PALM_FAILED = 82;
    public static final int UI_PALM_INVALID = 83;
    public static final int UI_PALM_START = 80;
    public static final int UI_PALM_SUCCESS = 81;
    public static final int UI_PIN_FAILED = 22;
    public static final int UI_PIN_INVALID = 23;
    public static final int UI_PIN_START = 20;
    public static final int UI_PIN_SUCCESS = 21;
    public static final int UI_PW_FAILED = 32;
    public static final int UI_PW_INVALID = 33;
    public static final int UI_PW_START = 30;
    public static final int UI_PW_SUCCESS = 31;
    public static final int UI_REMOTE_DELAY = 999;
    public static final int UI_SELECT_VERIFY_TYPE = 0;
    public static final int UI_UN_REGISTER_VT = -1;
    public static final int UI_URGENT_PASSWORD_FAILED = 72;
    public static final int UI_URGENT_PASSWORD_START = 73;
    public static final int UI_URGENT_PASSWORD_SUCCESS = 71;
    public static final int UI_WG_FAILED_CARD = 61;
    public static final int UI_WG_FAILED_PIN = 63;
    public static final int UI_WG_SUCCESS_CARD = 60;
    public static final int UI_WG_SUCCESS_PIN = 62;
    private String failMsg = "";
    private ArrayList<Boolean> register;
    private String uiAttFull;
    private int uiFPHeight;
    private int uiFPWidth;
    private String uiFingerprint;
    private String uiName;
    private String uiPin;
    private String uiRepeatTime;
    private String uiShortMessage;
    private String uiSignInTime;
    private String uiSpecialState;
    private int uiStatus;
    private boolean uiTFPhoto;
    private int uiType;
    private String uiWayLogin;

    public String getFailMsg() {
        return this.failMsg;
    }

    public void setFailMsg(String str) {
        this.failMsg = str;
    }

    public String getErrorMessage(String str, int i) {
        if (i <= 0) {
            return str;
        }
        return String.format("%s E%d", new Object[]{str, Integer.valueOf(i)});
    }

    public ArrayList<Boolean> getRegister() {
        return this.register;
    }

    public void setRegister(ArrayList<Boolean> arrayList) {
        this.register = arrayList;
    }

    public ZKVerViewBean() {
    }

    public ZKVerViewBean(int i) {
        this.uiType = i;
    }

    public int getUiType() {
        return this.uiType;
    }

    public void setUiType(int i) {
        this.uiType = i;
    }

    public String getUiName() {
        return this.uiName;
    }

    public void setUiName(String str) {
        this.uiName = str;
    }

    public String getUiPin() {
        return this.uiPin;
    }

    public void setUiPin(String str) {
        this.uiPin = str;
    }

    public String getUiSignInTime() {
        return ZkDigitConvertUtils.getFaDigit(this.uiSignInTime);
    }

    public void setUiSignInTime(String str) {
        this.uiSignInTime = str;
    }

    public String getUiRepeatTime() {
        return this.uiRepeatTime;
    }

    public void setUiRepeatTime(String str) {
        this.uiRepeatTime = str;
    }

    public int getUiFPWidth() {
        return this.uiFPWidth;
    }

    public void setUiFPWidth(int i) {
        this.uiFPWidth = i;
    }

    public int getUiFPHeight() {
        return this.uiFPHeight;
    }

    public void setUiFPHeight(int i) {
        this.uiFPHeight = i;
    }

    public String getUiFingerprint() {
        return this.uiFingerprint;
    }

    public void setUiFingerprint(String str) {
        this.uiFingerprint = str;
    }

    public boolean isUiTFPhoto() {
        return this.uiTFPhoto;
    }

    public void setUiTFPhoto(boolean z) {
        this.uiTFPhoto = z;
    }

    public String getUiSpecialState() {
        return this.uiSpecialState;
    }

    public void setUiSpecialState(String str) {
        this.uiSpecialState = str;
    }

    public int getUiStatus() {
        return this.uiStatus;
    }

    public void setUiStatus(int i) {
        this.uiStatus = i;
    }

    public String getUiShortMessage() {
        return this.uiShortMessage;
    }

    public void setUiShortMessage(String str) {
        this.uiShortMessage = str;
    }

    public String getUiWayLogin() {
        return this.uiWayLogin;
    }

    public void setUiWayLogin(String str) {
        this.uiWayLogin = str;
    }

    public String getUiAttFull() {
        return this.uiAttFull;
    }

    public void setUiAttFull(String str) {
        this.uiAttFull = str;
    }

    public String getPrivacyName() {
        return !"1".equals(ZKLauncher.sHideNameFunOn) ? this.uiName : "...";
    }

    public String getPrivacyPin() {
        String str;
        if (!"1".equals(ZKLauncher.sHidePinFunOn) || (str = this.uiPin) == null) {
            return this.uiPin;
        }
        switch (str.length()) {
            case 0:
            case 1:
                return this.uiPin;
            case 2:
                return this.uiPin.substring(0, 1) + "*";
            case 3:
                return this.uiPin.substring(0, 1) + "*" + this.uiPin.substring(2);
            case 4:
                return this.uiPin.substring(0, 2) + "*" + this.uiPin.substring(3);
            case 5:
                return this.uiPin.substring(0, 2) + "*" + this.uiPin.substring(3);
            case 6:
                return this.uiPin.substring(0, 2) + "**" + this.uiPin.substring(4);
            case 7:
                return this.uiPin.substring(0, 2) + "**" + this.uiPin.substring(4);
            case 8:
                return this.uiPin.substring(0, 2) + "***" + this.uiPin.substring(5);
            case 9:
                return this.uiPin.substring(0, 2) + "***" + this.uiPin.substring(5);
            case 10:
                return this.uiPin.substring(0, 2) + "****" + this.uiPin.substring(6);
            case 11:
                return this.uiPin.substring(0, 2) + "****" + this.uiPin.substring(6);
            case 12:
                return this.uiPin.substring(0, 2) + "****" + this.uiPin.substring(6);
            default:
                return this.uiPin.substring(0, 2) + "****" + this.uiPin.substring(6);
        }
    }

    public String toString() {
        return super.toString();
    }
}
