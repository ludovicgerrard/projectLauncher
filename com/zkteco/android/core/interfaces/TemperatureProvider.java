package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class TemperatureProvider extends AbstractProvider implements TemperatureInterface {
    public TemperatureProvider(Provider provider) {
        super(provider);
    }

    public static TemperatureProvider getInstance(Provider provider) {
        return new TemperatureProvider(provider);
    }

    public byte[] getAllTemperature() {
        return (byte[]) getProvider().invoke(TemperatureInterface.allTemperature, new Object[0]);
    }

    public byte[] getCalibrationValue() {
        return (byte[]) getProvider().invoke(TemperatureInterface.calibration, new Object[0]);
    }

    public byte[] getMaxTemperature() {
        return (byte[]) getProvider().invoke(TemperatureInterface.maxtemperature, new Object[0]);
    }

    public int getResolutionValue() {
        return ((Integer) getProvider().invoke(TemperatureInterface.resolution, new Object[0])).intValue();
    }

    public Boolean setCalibrationDefaultValue() {
        return (Boolean) getProvider().invoke(TemperatureInterface.setcalibrationdefault, new Object[0]);
    }

    public Boolean setCalibrationValue(int i) {
        return (Boolean) getProvider().invoke(TemperatureInterface.setcalibration, Integer.valueOf(i));
    }

    public Boolean setResolutionValue(int i) {
        return (Boolean) getProvider().invoke(TemperatureInterface.setresolution, Integer.valueOf(i));
    }

    public String firmwareUpgrade() {
        return (String) getProvider().invoke(TemperatureInterface.firmwareupgrade, new Object[0]);
    }

    public String getSdkVersion() {
        return (String) getProvider().invoke(TemperatureInterface.sdkversion, new Object[0]);
    }

    public String getMCUVersion() {
        return (String) getProvider().invoke(TemperatureInterface.mcuversion, new Object[0]);
    }

    public Boolean setEmissivityValue(int i) {
        return (Boolean) getProvider().invoke(TemperatureInterface.setEmissivityValue, Integer.valueOf(i));
    }

    public Boolean setDefaultEmissivityValue() {
        return (Boolean) getProvider().invoke(TemperatureInterface.setDefaultEmissivityValue, new Object[0]);
    }

    public byte[] getEmissivityValue() {
        return (byte[]) getProvider().invoke(TemperatureInterface.getEmissivityValue, new Object[0]);
    }

    public Boolean setTemperatureOffsetValue(int i) {
        return (Boolean) getProvider().invoke(TemperatureInterface.setTemperatureOffsetValue, Integer.valueOf(i));
    }

    public Boolean setDefaultTemperatureOffsetValue() {
        return (Boolean) getProvider().invoke(TemperatureInterface.setDefaultTemperatureOffsetValue, new Object[0]);
    }

    public byte[] getTemperatureOffsetValue() {
        return (byte[]) getProvider().invoke(TemperatureInterface.getTemperatureOffsetValue, new Object[0]);
    }

    public byte[] getAmbientTemperatureValue() {
        return (byte[]) getProvider().invoke(TemperatureInterface.getAmbientTemperatureValue, new Object[0]);
    }
}
