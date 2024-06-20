package com.zktechnology.android.att.AttInterceptors;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.zktechnology.android.att.AttRequest;
import com.zktechnology.android.att.AttResponse;
import com.zktechnology.android.att.types.AttAlarmType;
import com.zktechnology.android.att.types.AttDoorType;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.utils.ZKTemperatureUtil;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class AttTemperatureInterceptor extends BaseAttInterceptor implements AttInterceptor {
    public void interceptor(AttRequest attRequest, AttResponse attResponse) {
        double d;
        if (!ZKLauncher.sIRTempDetectionFunOn) {
            return;
        }
        if (ZKLauncher.sEnalbeIRTempDetection) {
            if (attRequest.getCurrentTypy() != ZKVerifyType.FACE.getValue() && ZKTemperatureUtil.getInstance(attRequest.getContext()).getTemperature() == -1.0d) {
                Intent intent = new Intent();
                intent.setAction(ZkFaceLauncher.SHOW_TEMPERATURE_UI);
                attRequest.getContext().sendBroadcast(intent);
            }
            double temperature = getTemperature(attRequest.getContext());
            if (ZKLauncher.deviceType == 1 && ZKLauncher.sIRTempUnit == 0) {
                temperature -= 0.3d;
            }
            if (attResponse.getWearMask() == 0) {
                attResponse.setWearMask(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WEAR_MASK));
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
            attResponse.setTemperature(d);
            boolean z = false;
            if (ZKLauncher.sIRTempUnit != 0 ? ZKTemperatureUtil.getInstance(attRequest.getContext()).celsiustoFahrenheit(d) > ((double) ZKLauncher.temperHigh) / 100.0d : d > ((double) ZKLauncher.temperHigh) / 100.0d) {
                z = true;
            }
            if (z) {
                attResponse.setTempHigh(true);
            }
            if (d == -1.0d) {
                setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.get_temperature_failed), 1001, AttAlarmType.NONE, AttDoorType.NONE, false);
            } else if (ZKLauncher.enableUnregisterPass == 0 && attRequest.getUserInfo() == null) {
                setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.dlg_ver_process_fa_title), 27, AttAlarmType.NONE, AttDoorType.NONE, false);
            } else if (z && ZKLauncher.enableNormalIRTempPass != 0) {
                setAttResponse(attResponse, false, attRequest.getContext().getResources().getString(R.string.high_body_temperature), 68, AttAlarmType.NONE, AttDoorType.NONE, false);
            }
        } else {
            attResponse.setTemperature(255.0d);
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
