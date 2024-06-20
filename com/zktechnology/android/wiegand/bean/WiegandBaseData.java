package com.zktechnology.android.wiegand.bean;

import com.zktechnology.android.wiegand.HidFormatDao;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandEnum;

public class WiegandBaseData {
    protected String cardBit = WiegandEnum.CARD_BIT_26_DEF.getValue();
    protected int facilityCode = 0;
    protected HidFormatDao hidFormatDao = new HidFormatDao();
    protected int manufactureCode = 0;
    protected long numCode = 0;
    protected int siteCode = 1;

    public void setCardBit(String str) {
        this.cardBit = str;
    }

    public int getManufactureCode() {
        return this.manufactureCode;
    }

    public void setManufactureCode(int i) {
        this.manufactureCode = i;
    }

    public int getSiteCode() {
        return this.siteCode;
    }

    public void setSiteCode(int i) {
        this.siteCode = i;
    }

    public int getFacilityCode() {
        return this.facilityCode;
    }

    public void setFacilityCode(int i) {
        this.facilityCode = i;
    }

    public long getNumCode() {
        return this.numCode;
    }

    public void setNumCode(long j) {
        this.numCode = j;
    }
}
