package com.zktechnology.android.acc.advance.Interceptors;

import android.os.SystemClock;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zktechnology.android.verify.utils.VerifyTypeUtil;
import com.zktechnology.android.verify.utils.ZKCameraController;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseInterceptor {
    private static final String TAG = "BaseInterceptor";
    SimpleDateFormat dbSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    /* access modifiers changed from: protected */
    public boolean isVerifyExpired(long j, long j2) {
        return j != 0 && SystemClock.elapsedRealtime() - j >= j2;
    }

    /* access modifiers changed from: protected */
    public void processResponse(boolean z, DoorOpenType doorOpenType, DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        processResponse(z, false, false, false, 0, -1, (String) null, (String) null, doorOpenType, doorAccessRequest, doorAccessResponse, date);
    }

    /* access modifiers changed from: protected */
    public void processResponse(String str, int i, DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        processResponse(false, false, false, false, 0, i, str, (String) null, DoorOpenType.NONE, doorAccessRequest, doorAccessResponse, date);
    }

    /* access modifiers changed from: protected */
    public void processResponse(int i, DoorOpenType doorOpenType, DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        processResponse(false, false, false, false, 0, i, (String) null, (String) null, doorOpenType, doorAccessRequest, doorAccessResponse, date);
    }

    /* access modifiers changed from: protected */
    public void processResponse(boolean z, boolean z2, String str, int i, DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        processResponse(z, z2, false, false, 0, i, str, (String) null, DoorOpenType.NONE, doorAccessRequest, doorAccessResponse, date);
    }

    /* access modifiers changed from: protected */
    public void processResponse(String str, int i, DoorOpenType doorOpenType, DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        processResponse(false, false, false, false, 0, i, (String) null, str, doorOpenType, doorAccessRequest, doorAccessResponse, date);
    }

    /* access modifiers changed from: protected */
    public void processResponse(boolean z, boolean z2, boolean z3, boolean z4, int i, int i2, String str, String str2, DoorOpenType doorOpenType, DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        int i3 = i2;
        DoorAccessResponse doorAccessResponse2 = doorAccessResponse;
        Date date2 = date;
        doorAccessResponse2.setCanProceed(z);
        doorAccessResponse2.setRemoteAlarmOn(z2);
        doorAccessResponse2.setStressFingerAlarmOn(z3);
        doorAccessResponse2.setStressPasswordAlarmOn(z4);
        doorAccessResponse2.setRemoteAlarmDelay(i);
        doorAccessResponse2.setErrorMessage(str);
        doorAccessResponse2.setErrorCode(0);
        doorAccessResponse2.setFirstOpenUserPin(str2);
        doorAccessResponse2.setDoorOpenType(doorOpenType);
        ZKVerifyType verifyType = doorAccessRequest.getVerifyInfo().getVerifyType();
        if (i3 >= 0) {
            doorAccessResponse2.setErrorCode(i3);
            int i4 = ZKLauncher.sInOutState;
            if (i3 == 24 && ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 5) {
                i4 = i4 == 1 ? 0 : 1;
            }
            ZKAccDao accDao = doorAccessRequest.getAccDao();
            UserInfo userInfo = doorAccessRequest.getUserInfo();
            int verify_Type = (userInfo == null || DBManager.getInstance().getIntOption("AccessPersonalVerification", 0) != 1) ? -1 : userInfo.getVerify_Type();
            if (verify_Type == -1) {
                verify_Type = ZKLauncher.sDoor1VerifyType;
            }
            if (i3 != 1001 && (i3 != 27 || ZKLauncher.enableUnregisterPass != 0)) {
                accDao.addAccAttLog(userInfo, i4, ZKLauncher.sDoorId, i2, this.dbSDF.format(date2).replace(" ", ExifInterface.GPS_DIRECTION_TRUE), VerifyTypeUtil.getDoorVerifyType(verifyType, verify_Type), doorAccessResponse.getTemperature(), doorAccessResponse.getWearMask());
            }
            LogUtils.d(ZKCameraController.TAG, TAG + new SimpleDateFormat(ZKConstantConfig.DATE_FORMAT_2, Locale.US).format(date2).replace(" ", ExifInterface.GPS_DIRECTION_TRUE));
            return;
        }
    }
}
