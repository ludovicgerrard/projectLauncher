package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.TemperatureInterface;
import com.zkteco.android.core.interfaces.TemperatureProvider;
import com.zkteco.android.core.library.CoreProvider;

public class TemperatureManager implements TemperatureInterface {
    private Context context;
    private TemperatureProvider provider;

    public TemperatureManager(Context context2) {
        this.context = context2;
        this.provider = TemperatureProvider.getInstance(new CoreProvider(context2));
    }

    public byte[] getAllTemperature() {
        return this.provider.getAllTemperature();
    }

    public byte[] getCalibrationValue() {
        return this.provider.getCalibrationValue();
    }

    public byte[] getMaxTemperature() {
        return this.provider.getMaxTemperature();
    }

    public int getResolutionValue() {
        return this.provider.getResolutionValue();
    }

    public Boolean setCalibrationDefaultValue() {
        return this.provider.setCalibrationDefaultValue();
    }

    public Boolean setCalibrationValue(int i) {
        return this.provider.setCalibrationValue(i);
    }

    public Boolean setResolutionValue(int i) {
        return this.provider.setResolutionValue(i);
    }

    public String firmwareUpgrade() {
        return this.provider.firmwareUpgrade();
    }

    public String getSdkVersion() {
        return this.provider.getSdkVersion();
    }

    public String getMCUVersion() {
        return this.provider.getMCUVersion();
    }

    public Boolean setEmissivityValue(int i) {
        return this.provider.setEmissivityValue(i);
    }

    public Boolean setDefaultEmissivityValue() {
        return this.provider.setDefaultEmissivityValue();
    }

    public byte[] getEmissivityValue() {
        return this.provider.getEmissivityValue();
    }

    public Boolean setTemperatureOffsetValue(int i) {
        return this.provider.setTemperatureOffsetValue(i);
    }

    public Boolean setDefaultTemperatureOffsetValue() {
        return this.provider.setDefaultTemperatureOffsetValue();
    }

    public byte[] getTemperatureOffsetValue() {
        return this.provider.getTemperatureOffsetValue();
    }

    public byte[] getAmbientTemperatureValue() {
        return this.provider.getAmbientTemperatureValue();
    }
}
