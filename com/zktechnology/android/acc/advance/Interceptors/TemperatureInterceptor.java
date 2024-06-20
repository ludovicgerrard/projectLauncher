package com.zktechnology.android.acc.advance.Interceptors;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.utils.ZKTemperatureUtil;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

public class TemperatureInterceptor extends BaseInterceptor implements Interceptor {
    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        double d;
        DoorAccessResponse doorAccessResponse2 = doorAccessResponse;
        if (!ZKLauncher.sIRTempDetectionFunOn) {
        } else if (ZKLauncher.sEnalbeIRTempDetection) {
            if (doorAccessRequest.getVerifyInfo().getVerifyType().getValue() != ZKVerifyType.FACE.getValue() && ZKTemperatureUtil.getInstance(doorAccessRequest.getContext()).getTemperature() == -1.0d) {
                Intent intent = new Intent();
                intent.setAction(ZkFaceLauncher.SHOW_TEMPERATURE_UI);
                doorAccessRequest.getContext().sendBroadcast(intent);
            }
            double temperature = getTemperature(doorAccessRequest.getContext());
            if (ZKLauncher.deviceType == 1) {
                temperature -= ZKLauncher.irTempCFP;
            }
            if (doorAccessResponse.getWearMask() == 0) {
                doorAccessResponse2.setWearMask(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WEAR_MASK));
            }
            if (temperature > -1.0d) {
                String format = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US)).format(temperature + (((double) ZKLauncher.correction) / 100.0d));
                if (format.contains(",")) {
                    format = format.replace(",", ".");
                }
                d = Double.parseDouble(format);
            } else {
                d = -1.0d;
            }
            doorAccessResponse2.setTemperature(d);
            boolean z = false;
            if (ZKLauncher.sIRTempUnit != 0 ? ZKTemperatureUtil.getInstance(doorAccessRequest.getContext()).celsiustoFahrenheit(d) > ((double) ZKLauncher.temperHigh) / 100.0d : d > ((double) ZKLauncher.temperHigh) / 100.0d) {
                z = true;
            }
            if (z) {
                doorAccessResponse2.setTempHigh(true);
            }
            if (d == -1.0d) {
                processResponse(doorAccessRequest.getContext().getResources().getString(R.string.get_temperature_failed), 1001, doorAccessRequest, doorAccessResponse, date);
            } else if (ZKLauncher.enableUnregisterPass == 0 && doorAccessRequest.getUserInfo() == null) {
                processResponse(doorAccessRequest.getContext().getResources().getString(R.string.dlg_ver_process_fa_title), 27, doorAccessRequest, doorAccessResponse, date);
            } else if (z && ZKLauncher.enableNormalIRTempPass != 0) {
                processResponse(doorAccessRequest.getContext().getResources().getString(R.string.high_body_temperature), 68, doorAccessRequest, doorAccessResponse, date);
            }
        } else {
            doorAccessResponse2.setTemperature(255.0d);
        }
    }

    private double getTemperature(Context context) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean z = true;
        double d = -1.0d;
        while (z) {
            if (Math.abs(elapsedRealtime - SystemClock.elapsedRealtime()) < 5000) {
                d = ZKTemperatureUtil.getInstance(context).getTemperature();
                if (d != -1.0d) {
                    ZKTemperatureUtil.getInstance(context);
                    ZKTemperatureUtil.setTemperature(-1.0d);
                }
            } else {
                ZKTemperatureUtil.getInstance(context);
                ZKTemperatureUtil.setRunning(false);
            }
            z = false;
        }
        return d;
    }
}
