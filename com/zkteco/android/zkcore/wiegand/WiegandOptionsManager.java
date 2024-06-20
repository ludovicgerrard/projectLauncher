package com.zkteco.android.zkcore.wiegand;

import android.content.Context;
import android.content.Intent;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandDeviceID;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandEnable;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandEnum;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandFailedID;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandPulseInterval;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandPulseWidth;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandSiteCode;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandType;

public class WiegandOptionsManager {
    private static final String TAG = "WiegandOptionsManager";
    private Context context;
    private DataManager dataManager = new DataManager();

    public WiegandOptionsManager(Context context2) {
        this.context = context2;
    }

    public boolean isOpenIn() {
        this.dataManager.open(this.context);
        return WiegandEnable.OPEN.getValue() == this.dataManager.getIntOption(WiegandConfig.IN, WiegandEnable.CLOSE_DEF.getValue());
    }

    public boolean isOpenOut() {
        this.dataManager.open(this.context);
        return WiegandEnable.OPEN.getValue() == this.dataManager.getIntOption(WiegandConfig.OUT, WiegandEnable.CLOSE_DEF.getValue());
    }

    public int getDeviceID() {
        this.dataManager.open(this.context);
        int intOption = this.dataManager.getIntOption("DeviceID", WiegandDeviceID.MIN.getValue());
        if (intOption > WiegandDeviceID.MAX.getValue()) {
            intOption = WiegandDeviceID.MAX.getValue();
        }
        return intOption < WiegandDeviceID.MIN.getValue() ? WiegandDeviceID.MIN.getValue() : intOption;
    }

    public WiegandEnum getBit4In() {
        this.dataManager.open(this.context);
        return WiegandEnum.getEnum(this.dataManager.getStrOption(WiegandConfig.IN_CARD_BIT, WiegandEnum.CARD_BIT_26_DEF.getValue()));
    }

    public void setBit4In(WiegandEnum wiegandEnum) {
        if (wiegandEnum == null) {
            WiegandLogUtils.getInstance().d(TAG, "cardBit is null");
            return;
        }
        this.dataManager.open(this.context);
        this.dataManager.setStrOption(WiegandConfig.IN_CARD_BIT, wiegandEnum.getValue());
        sendBroadcast(WiegandConfig.ACTION_WIEGAND_IN_CHANGE_BIT);
    }

    private void sendBroadcast(String str) {
        Intent intent = new Intent();
        intent.setAction(str);
        this.context.sendBroadcast(intent);
    }

    public WiegandEnum getBit4Out() {
        this.dataManager.open(this.context);
        return WiegandEnum.getEnum(this.dataManager.getStrOption(WiegandConfig.OUT_CARD_BIT, WiegandEnum.CARD_BIT_26_DEF.getValue()));
    }

    public void setBit4Out(WiegandEnum wiegandEnum) {
        if (wiegandEnum == null) {
            WiegandLogUtils.getInstance().d(TAG, "cardBit is null");
            return;
        }
        this.dataManager.open(this.context);
        this.dataManager.setStrOption(WiegandConfig.OUT_CARD_BIT, wiegandEnum.getValue());
        sendBroadcast(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_BIT);
    }

    public void setPulseWidth4Out(int i) {
        if (i < WiegandPulseWidth.MIN.getValue() || i > WiegandPulseWidth.MAX.getValue()) {
            i = WiegandPulseWidth.DEF.getValue();
        }
        this.dataManager.open(this.context);
        this.dataManager.setIntOption(WiegandConfig.OUT_PULSE_WIDTH, i);
        sendBroadcast(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_PULSE_WIDTH);
    }

    public int getPulseWidth4Out() {
        this.dataManager.open(this.context);
        return this.dataManager.getIntOption(WiegandConfig.OUT_PULSE_WIDTH, WiegandPulseWidth.DEF.getValue());
    }

    public void setPulseInterval4Out(int i) {
        if (i < WiegandPulseInterval.MIN.getValue() || i > WiegandPulseInterval.MAX.getValue()) {
            i = WiegandPulseInterval.DEF.getValue();
        }
        this.dataManager.open(this.context);
        this.dataManager.setIntOption(WiegandConfig.OUT_PULSE_INTERVAL, i);
        sendBroadcast(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_PULSE_INTERVAL);
    }

    public int getPulseInterval4Out() {
        this.dataManager.open(this.context);
        return this.dataManager.getIntOption(WiegandConfig.OUT_PULSE_INTERVAL, WiegandPulseInterval.DEF.getValue());
    }

    public void setType4In(WiegandType wiegandType) {
        setType4InNoBroadcast(wiegandType);
        sendBroadcast(WiegandConfig.ACTION_WIEGAND_IN_CHANGE_VERIFY_TYPE);
    }

    private void setType4InNoBroadcast(WiegandType wiegandType) {
        if (wiegandType == null) {
            WiegandLogUtils.getInstance().d(TAG, "type4In is null");
            return;
        }
        int value = wiegandType.getValue();
        this.dataManager.open(this.context);
        this.dataManager.setIntOption(WiegandConfig.IN_TYPE, value);
    }

    public void setType4Out(WiegandType wiegandType) {
        setType4OutNoBoradcast(wiegandType);
        sendBroadcast(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_VERIFY_TYPE);
    }

    public void setType4OutNoBoradcast(WiegandType wiegandType) {
        if (wiegandType == null) {
            WiegandLogUtils.getInstance().d(TAG, "type4Out is null");
            return;
        }
        int value = wiegandType.getValue();
        this.dataManager.open(this.context);
        this.dataManager.setIntOption(WiegandConfig.OUT_TYPE, value);
    }

    public WiegandType getType4In() {
        this.dataManager.open(this.context);
        return WiegandType.getEnum(this.dataManager.getIntOption(WiegandConfig.IN_TYPE, WiegandType.CARD_NUM.getValue()));
    }

    public WiegandType getType4Out() {
        this.dataManager.open(this.context);
        return WiegandType.getEnum(this.dataManager.getIntOption(WiegandConfig.OUT_TYPE, WiegandType.CARD_NUM.getValue()));
    }

    public void setSiteCode(int i) {
        if ((i < WiegandSiteCode.MIN.getValue() || i > WiegandSiteCode.MAX.getValue()) && i != WiegandSiteCode.DEF.getValue()) {
            i = WiegandSiteCode.DEF.getValue();
        }
        this.dataManager.open(this.context);
        this.dataManager.setIntOption(WiegandConfig.SITE_CODE, i);
        sendBroadcast(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_SITE_CODE);
    }

    public int getSiteCode() {
        this.dataManager.open(this.context);
        return this.dataManager.getIntOption(WiegandConfig.SITE_CODE, WiegandSiteCode.DEF.getValue());
    }

    public void setFailedID(int i) {
        if ((i < WiegandFailedID.MIN.getValue() || i > WiegandFailedID.MAX.getValue()) && i != WiegandFailedID.DEF.getValue()) {
            i = WiegandFailedID.DEF.getValue();
        }
        this.dataManager.open(this.context);
        this.dataManager.setIntOption(WiegandConfig.FAILED_ID, i);
        sendBroadcast(WiegandConfig.ACTION_WIEGAND_OUT_CHANGE_FAILED_ID);
    }

    public int getFailedID() {
        this.dataManager.open(this.context);
        return this.dataManager.getIntOption(WiegandConfig.FAILED_ID, WiegandFailedID.DEF.getValue());
    }

    public boolean isABC() {
        this.dataManager.open(this.context);
        int intOption = this.dataManager.getIntOption("IMEFunOn", WiegandEnable.CLOSE_DEF.getValue());
        int intOption2 = this.dataManager.getIntOption("HasSupportABCPin", WiegandEnable.CLOSE_DEF.getValue());
        if (intOption == WiegandEnable.OPEN.getValue() && intOption2 == WiegandEnable.OPEN.getValue() && this.dataManager.getIntOption("IsSupportABCPin", WiegandEnable.CLOSE_DEF.getValue()) == WiegandEnable.OPEN.getValue()) {
            return true;
        }
        return false;
    }

    public void refreshOption() {
        if (isABC()) {
            setType4InNoBroadcast(WiegandType.CARD_NUM);
            setType4OutNoBoradcast(WiegandType.CARD_NUM);
        }
    }

    @Deprecated
    public boolean isRFCardOn() {
        this.dataManager.open(this.context);
        return this.dataManager.getIntOption("~RFCardOn", WiegandEnable.CLOSE_DEF.getValue()) == WiegandEnable.OPEN.getValue() || this.dataManager.getIntOption(WiegandConfig.MIFARE, WiegandEnable.CLOSE_DEF.getValue()) == WiegandEnable.OPEN.getValue() || this.dataManager.getIntOption(WiegandConfig.IsSupportSFZ, WiegandEnable.CLOSE_DEF.getValue()) == WiegandEnable.OPEN.getValue();
    }
}
