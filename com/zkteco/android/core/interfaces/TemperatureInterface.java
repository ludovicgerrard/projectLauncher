package com.zkteco.android.core.interfaces;

public interface TemperatureInterface {
    public static final String allTemperature = "all-temperature";
    public static final String calibration = "calibration-value";
    public static final String firmwareupgrade = "firmware-upgrade";
    public static final String getAmbientTemperatureValue = "get-ambient-temperature-value";
    public static final String getEmissivityValue = "get-emissivity-value";
    public static final String getTemperatureOffsetValue = "get-temperature-offset-value";
    public static final String maxtemperature = "max-temperature";
    public static final String mcuversion = "mcu-version";
    public static final String resolution = "resolution-value";
    public static final String sdkversion = "sdk-version";
    public static final String setDefaultEmissivityValue = "set-default-emissivity-value";
    public static final String setDefaultTemperatureOffsetValue = "set-default-temperature-offset-value";
    public static final String setEmissivityValue = "set-emissivity-value";
    public static final String setTemperatureOffsetValue = "set-temperature-offset-value";
    public static final String setcalibration = "set-calibration";
    public static final String setcalibrationdefault = "set-calibration-default";
    public static final String setresolution = "set-resolution";

    String firmwareUpgrade();

    byte[] getAllTemperature();

    byte[] getAmbientTemperatureValue();

    byte[] getCalibrationValue();

    byte[] getEmissivityValue();

    String getMCUVersion();

    byte[] getMaxTemperature();

    int getResolutionValue();

    String getSdkVersion();

    byte[] getTemperatureOffsetValue();

    Boolean setCalibrationDefaultValue();

    Boolean setCalibrationValue(int i);

    Boolean setDefaultEmissivityValue();

    Boolean setDefaultTemperatureOffsetValue();

    Boolean setEmissivityValue(int i);

    Boolean setResolutionValue(int i);

    Boolean setTemperatureOffsetValue(int i);
}
