package com.zkteco.edk.common.device;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface ZkDeviceName {
    public static final String CARD = "ZkCard";
    public static final String CARD_B133 = "B133";
    public static final String CARD_B133_NEW = "B133_NEW";
    public static final String CARD_B134 = "B134";
    public static final String CARD_ELATEC = "Elatec";
    public static final String CARD_EM05 = "EM05";
    public static final String CARD_H10A = "CARD_H10A";
    public static final String CARD_H1NFC = "CARD_H1NFC";
    public static final String CARD_HORUS = "CARD_HORUS";
    public static final String CARD_ICO8 = "ICO8";
    public static final String CARD_M210 = "M210";
    public static final String CARD_M330L = "M330-L";
    public static final String FINGER_LIVE20R = "Live20R";
    public static final String MCU_ZIM_200 = "ZIM200";
    public static final String MCU_ZSM_760 = "ZSM760";
    public static final String QRCODE_LV4200 = "LV4200";
    public static final String QRCODE_LV4300 = "LV4300";
    public static final String QRCODE_NLS = "QR_NLS";
    public static final String QRCODE_QRH1 = "QR_H1";
    public static final String QRCODE_QRM10 = "QRM10";
    public static final String TEMPERATURE_CB360 = "CB360";
    public static final String TEMPERATURE_STM32 = "ZkTemperature";
}
