package com.zktechnology.android.wiegand.bean;

import android.text.TextUtils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.wiegand.WiegandUtil;
import com.zkteco.android.db.orm.tna.HidFormat;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandFormatType;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandType;

public class WiegandInData extends WiegandBaseData {
    private byte[] data;
    private boolean isCan = false;
    private int siteCodeOrDeviceID;
    private WiegandType type;

    public WiegandType getType() {
        return this.type;
    }

    public void setType(WiegandType wiegandType) {
        this.type = wiegandType;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public int getSiteCodeOrDeviceID() {
        return this.siteCodeOrDeviceID;
    }

    public void setSiteCodeOrDeviceID(int i) {
        this.siteCodeOrDeviceID = i;
    }

    public boolean isCan() {
        return this.isCan;
    }

    public void getWiegandData() {
        char c2;
        char c3;
        char c4;
        char c5;
        if (this.data == null) {
            LogUtils.e(WiegandUtil.WG_TAG, "wiegandIn###data is null");
            return;
        }
        HidFormat hidFormat4CardBitAndStatusYes = this.hidFormatDao.getHidFormat4CardBitAndStatusYes(this.cardBit, WiegandFormatType.FORMAT_TYPE_IN);
        if (hidFormat4CardBitAndStatusYes == null) {
            LogUtils.e(WiegandUtil.WG_TAG, "wiegandIn###hidFormat is null");
            return;
        }
        int byteToInt = WiegandUtil.byteToInt(this.data[0]);
        int card_Bit = hidFormat4CardBitAndStatusYes.getCard_Bit();
        if (byteToInt != card_Bit) {
            LogUtils.e(WiegandUtil.WG_TAG, "wiegandIn###data_0 = %d <> cardBit = %d", Integer.valueOf(byteToInt), Integer.valueOf(card_Bit));
            return;
        }
        int i = (byteToInt / 8) + (byteToInt % 8 == 0 ? 0 : 1);
        byte[] bArr = new byte[i];
        System.arraycopy(this.data, 1, bArr, 0, i);
        char[] charArray = WiegandUtil.byteArrToBinStr4Long(bArr).substring(0, card_Bit).toCharArray();
        String card_Format = hidFormat4CardBitAndStatusYes.getCard_Format();
        String first_Even = hidFormat4CardBitAndStatusYes.getFirst_Even();
        String first_Odd = hidFormat4CardBitAndStatusYes.getFirst_Odd();
        String second_Even = hidFormat4CardBitAndStatusYes.getSecond_Even();
        String second_Odd = hidFormat4CardBitAndStatusYes.getSecond_Odd();
        char[] charArray2 = TextUtils.isEmpty(card_Format) ? new char[card_Bit] : card_Format.toCharArray();
        char[] charArray3 = TextUtils.isEmpty(first_Even) ? new char[card_Bit] : first_Even.toCharArray();
        char[] charArray4 = TextUtils.isEmpty(first_Odd) ? new char[card_Bit] : first_Odd.toCharArray();
        char[] charArray5 = TextUtils.isEmpty(second_Even) ? new char[card_Bit] : second_Even.toCharArray();
        char[] charArray6 = TextUtils.isEmpty(second_Odd) ? new char[card_Bit] : second_Odd.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        StringBuffer stringBuffer4 = new StringBuffer();
        int i2 = 0;
        char c6 = '2';
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        char c7 = '2';
        char c8 = '2';
        char c9 = '2';
        while (i2 < card_Bit) {
            int i7 = card_Bit;
            char c10 = charArray2[i2];
            char[] cArr = charArray2;
            if (charArray3[i2] == '1' && c10 == '1') {
                i3++;
            }
            if (charArray4[i2] == '1' && c10 == '1') {
                i4++;
            }
            if (charArray5[i2] == '1' && c10 == '1') {
                i5++;
            }
            if (charArray6[i2] == '1' && c10 == '1') {
                i6++;
            }
            if (c10 != 'C') {
                if (c10 != 'M') {
                    if (c10 != 'O') {
                        if (c10 != 'S') {
                            if (c10 != 'c') {
                                if (c10 != 'm') {
                                    if (c10 == 'o') {
                                        c9 = charArray[i2];
                                    } else if (c10 != 's') {
                                        if (c10 != 'E') {
                                            if (c10 != 'F') {
                                                if (c10 == 'e') {
                                                    c8 = charArray[i2];
                                                } else if (c10 != 'f') {
                                                }
                                            }
                                            stringBuffer3.append(charArray[i2]);
                                        } else {
                                            c6 = charArray[i2];
                                        }
                                    }
                                }
                            }
                        }
                        stringBuffer.append(charArray[i2]);
                    } else {
                        c7 = charArray[i2];
                    }
                    i2++;
                    card_Bit = i7;
                    charArray2 = cArr;
                }
                stringBuffer2.append(charArray[i2]);
                i2++;
                card_Bit = i7;
                charArray2 = cArr;
            }
            stringBuffer4.append(charArray[i2]);
            i2++;
            card_Bit = i7;
            charArray2 = cArr;
        }
        if (c6 == '2') {
            c2 = '2';
            c3 = c7;
        } else if (i3 == 0 || i3 % 2 != 1 || c6 == '1') {
            c3 = c7;
            c2 = '2';
        } else {
            LogUtils.e(WiegandUtil.WG_TAG, "wiegandIn###firstEvenSum = %d - firstEvenValue = %s", Integer.valueOf(i3), String.valueOf(c6));
            return;
        }
        if (c3 == c2) {
            c4 = c8;
        } else if (i4 == 0 || i4 % 2 != 0 || c3 == '1') {
            c4 = c8;
            c2 = '2';
        } else {
            LogUtils.e(WiegandUtil.WG_TAG, "wiegandIn###firstOddSum = %d - firstOddValue = %s", Integer.valueOf(i4), String.valueOf(c3));
            return;
        }
        if (c4 == c2) {
            c5 = c9;
        } else if (i5 == 0 || i5 % 2 != 1 || c4 == '1') {
            c5 = c9;
            c2 = '2';
        } else {
            LogUtils.e(WiegandUtil.WG_TAG, "wiegandIn###secondEvenSum = %d - secondEvenValue = %s", Integer.valueOf(i5), String.valueOf(c4));
            return;
        }
        if (c5 == c2 || i6 == 0 || i6 % 2 != 0 || c5 == '1') {
            if (stringBuffer.length() > 0) {
                setSiteCodeOrDeviceID(WiegandUtil.binaryStrToInt(stringBuffer.toString()));
            }
            if (stringBuffer2.length() > 0) {
                setManufactureCode(WiegandUtil.binaryStrToInt(stringBuffer2.toString()));
            }
            if (stringBuffer3.length() > 0) {
                setFacilityCode(WiegandUtil.binaryStrToInt(stringBuffer3.toString()));
            }
            if (stringBuffer4.length() > 0) {
                setNumCode(WiegandUtil.binaryStrToLong(stringBuffer4.toString()));
            }
            this.isCan = true;
            LogUtils.e(WiegandUtil.WG_TAG, "wiegandIn###SiteCode = %dManufactureCode = %dFacilityCode = %dNumCode = %d", Integer.valueOf(getSiteCode()), Integer.valueOf(getManufactureCode()), Integer.valueOf(getFacilityCode()), Long.valueOf(getNumCode()));
            return;
        }
        LogUtils.e(WiegandUtil.WG_TAG, "wiegandIn###secondOddSum = %d - secondOddValue = %s", Integer.valueOf(i6), String.valueOf(c5));
    }
}
