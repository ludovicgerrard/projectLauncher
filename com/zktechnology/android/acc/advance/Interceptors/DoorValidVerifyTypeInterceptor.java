package com.zktechnology.android.acc.advance.Interceptors;

import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.DoorVerifyCombination;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.dao.ZKAccDao;
import java.util.Date;

public class DoorValidVerifyTypeInterceptor extends BaseInterceptor implements Interceptor {
    private static final String TAG = "DoorValidVerifyTypeInterceptor";

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        ZKAccDao accDao = doorAccessRequest.getAccDao();
        ZKVerifyType verifyType = doorAccessRequest.getVerifyInfo().getVerifyType();
        boolean z = false;
        int verify_Type = DBManager.getInstance().getIntOption("AccessPersonalVerification", 0) == 1 ? doorAccessRequest.getUserInfo().getVerify_Type() : -1;
        if (verify_Type == -1) {
            verify_Type = accDao.getDoor1VerifyType();
        }
        DoorVerifyCombination fromInteger = DoorVerifyCombination.fromInteger(verify_Type);
        if (fromInteger != null && fromInteger.getVerifyTypes().contains(verifyType)) {
            z = true;
        }
        if (!z) {
            processResponse(doorAccessRequest.getContext().getResources().getString(R.string.illegal_verify_type), 41, doorAccessRequest, doorAccessResponse, date);
        }
    }
}
