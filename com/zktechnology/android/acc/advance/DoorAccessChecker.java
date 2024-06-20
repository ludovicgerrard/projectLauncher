package com.zktechnology.android.acc.advance;

import android.content.Context;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.advance.Interceptors.AntiSubmarineInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.ContinuousVerifyInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.DoorEffectiveTimePeriodInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.DoorValidVerifyTypeInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.MaskInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.MasterCardInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.MultipleVerifyInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.StressFingerInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.StressPasswordInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.TemperatureInterceptor;
import com.zktechnology.android.acc.advance.Interceptors.UserPermissionInterceptor;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.bean.process.ZKVerifyInfo;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.Date;

public class DoorAccessChecker {
    private static final String TAG = "DoorAccessChecker";

    public static DoorAccessResponse check(Context context, ZKAccDao zKAccDao, UserInfo userInfo, ZKVerifyInfo zKVerifyInfo, Date date) {
        DoorAccessRequest doorAccessRequest = new DoorAccessRequest();
        doorAccessRequest.setContext(context);
        doorAccessRequest.setAccDao(zKAccDao);
        doorAccessRequest.setUserInfo(userInfo);
        doorAccessRequest.setVerifyInfo(zKVerifyInfo);
        DoorAccessResponse doorAccessResponse = new DoorAccessResponse();
        doorAccessResponse.setCanProceed(true);
        doorAccessResponse.setErrorMessage((String) null);
        doorAccessResponse.setRemoteAlarmOn(false);
        doorAccessResponse.setDoorOpenType(DoorOpenType.NONE);
        doorAccessResponse.setTemperature(-1.0d);
        doorAccessResponse.setTempHigh(false);
        doorAccessResponse.setWearMask(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_WEAR_MASK));
        InterceptorChain interceptorChain = new InterceptorChain();
        if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_REMOTE_AUTH) {
            if (userInfo != null) {
                interceptorChain.addInterceptors(new StressFingerInterceptor()).addInterceptors(new StressPasswordInterceptor()).addInterceptors(new DoorValidVerifyTypeInterceptor()).addInterceptors(new DoorEffectiveTimePeriodInterceptor()).addInterceptors(new UserPermissionInterceptor()).addInterceptors(new MultipleVerifyInterceptor()).addInterceptors(new AntiSubmarineInterceptor()).addInterceptors(new ContinuousVerifyInterceptor()).addInterceptors(new MasterCardInterceptor()).addInterceptors(new TemperatureInterceptor()).addInterceptors(new MaskInterceptor());
            } else {
                interceptorChain.addInterceptors(new TemperatureInterceptor()).addInterceptors(new MaskInterceptor());
            }
            if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 5 && DBManager.getInstance().getIntOption("WiegandMethod", 0) == 0) {
                interceptorChain = userInfo != null ? new InterceptorChain().addInterceptors(new AntiSubmarineInterceptor()).addInterceptors(new TemperatureInterceptor()).addInterceptors(new MaskInterceptor()) : new InterceptorChain().addInterceptors(new TemperatureInterceptor()).addInterceptors(new MaskInterceptor());
            }
        }
        interceptorChain.interceptor(doorAccessRequest, doorAccessResponse, date);
        return doorAccessResponse;
    }
}
