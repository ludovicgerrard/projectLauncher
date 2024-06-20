package com.zktechnology.android.acc.advance.Interceptors;

import android.os.SystemClock;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.acc.DoorAccessDao;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.bean.process.ZKVerifyHistory;
import com.zktechnology.android.verify.bean.process.ZKVerifyInfo;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zktechnology.android.verify.utils.VerifyTypeUtil;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ContinuousVerifyInterceptor extends BaseInterceptor implements Interceptor {
    private static final int CONTINUOUS_SINGLE_VERIFY_COUNT = 5;
    private static final int CONTINUOUS_START_VERIFY_COUNT = 1;
    private static final int CONTINUOUS_TOTAL_VERIFY_COUNT = 10;
    private static final String TAG = "ContinuousVerifyInterceptor";

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        Date date2 = date;
        UserInfo userInfo = doorAccessRequest.getUserInfo();
        if (userInfo != null) {
            ZKAccDao accDao = doorAccessRequest.getAccDao();
            if (accDao.isSuperUser(userInfo)) {
                ZKVerifyInfo verifyInfo = doorAccessRequest.getVerifyInfo();
                ArrayList<ZKVerifyHistory> verifyHistories = verifyInfo.getVerifyHistories();
                ZKVerifyHistory zKVerifyHistory = null;
                Iterator<ZKVerifyHistory> it = verifyHistories.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ZKVerifyHistory next = it.next();
                    if (next.getUserPin().equals(userInfo.getUser_PIN())) {
                        zKVerifyHistory = next;
                        break;
                    }
                }
                int i = 1;
                if (zKVerifyHistory == null) {
                    zKVerifyHistory = new ZKVerifyHistory();
                    zKVerifyHistory.setUserPin(userInfo.getUser_PIN());
                    zKVerifyHistory.setVerifyCount(1);
                    zKVerifyHistory.setTotalVerifyCount(1);
                    zKVerifyHistory.setLastVerifyTime(SystemClock.elapsedRealtime());
                    verifyHistories.add(zKVerifyHistory);
                    verifyInfo.setVerifyHistories(verifyHistories);
                    doorAccessRequest.setVerifyInfo(verifyInfo);
                } else {
                    DoorAccessRequest doorAccessRequest2 = doorAccessRequest;
                    boolean isVerifyExpired = isVerifyExpired(zKVerifyHistory.getLastVerifyTime(), ((long) DBManager.getInstance().getIntOption(ZKDBConfig.ACC_MULTI_USER_VERIFY_TIME, 8)) * 1000);
                    zKVerifyHistory.setLastVerifyTime(SystemClock.elapsedRealtime());
                    int verifyCount = zKVerifyHistory.getVerifyCount();
                    int totalVerifyCount = zKVerifyHistory.getTotalVerifyCount();
                    if (isVerifyExpired) {
                        if (verifyCount < 5) {
                            zKVerifyHistory.setVerifyCount(1);
                        }
                        if (totalVerifyCount >= 5) {
                            i = totalVerifyCount < 10 ? 6 : totalVerifyCount;
                        }
                        zKVerifyHistory.setTotalVerifyCount(i);
                        boolean checkDoor1CancelKeepOpenDay = accDao.checkDoor1CancelKeepOpenDay();
                        if (!doorAccessResponse.isStressFingerAlarmOn() && !doorAccessResponse.isStressPasswordAlarmOn()) {
                            processResponse(checkDoor1CancelKeepOpenDay, DoorOpenType.LOCAL_OPEN, doorAccessRequest, doorAccessResponse, date);
                            return;
                        }
                        return;
                    }
                    zKVerifyHistory.setVerifyCount(verifyCount + 1);
                    zKVerifyHistory.setTotalVerifyCount(totalVerifyCount + 1);
                }
                ZKVerifyType verifyType = verifyInfo.getVerifyType();
                int verify_Type = DBManager.getInstance().getIntOption("AccessPersonalVerification", 0) == 1 ? userInfo.getVerify_Type() : -1;
                if (verify_Type == -1) {
                    verify_Type = ZKLauncher.sDoor1VerifyType;
                }
                int doorVerifyType = VerifyTypeUtil.getDoorVerifyType(verifyType, verify_Type);
                int doorAlwaysOpenTimeZone = DoorAccessDao.getDoorAlwaysOpenTimeZone();
                boolean isInAccTimeZoneExtension = doorAlwaysOpenTimeZone > 0 ? DoorAccessDao.isInAccTimeZoneExtension(doorAlwaysOpenTimeZone, true) : false;
                int verifyCount2 = zKVerifyHistory.getVerifyCount();
                int totalVerifyCount2 = zKVerifyHistory.getTotalVerifyCount();
                if (verifyCount2 == 5) {
                    zKVerifyHistory.setVerifyCount(0);
                    doorAccessResponse.setAdminContinuousVerify(true);
                    if (totalVerifyCount2 == 10) {
                        zKVerifyHistory.setTotalVerifyCount(0);
                        if (isInAccTimeZoneExtension) {
                            DoorAccessDao.setDoor1CancelKeepOpenDay(false);
                            processResponse(10, DoorOpenType.ADMIN_CANCEL_OPEN_ALWAYS, doorAccessRequest, doorAccessResponse, date);
                            return;
                        }
                        accDao.addAccAttLog(userInfo, ZKLauncher.sInOutState, ZKLauncher.sDoorId, 10, this.dbSDF.format(date2).replace(" ", ExifInterface.GPS_DIRECTION_TRUE), doorVerifyType, -1.0d, 0);
                    } else if (isInAccTimeZoneExtension) {
                        DoorAccessDao.setDoor1CancelKeepOpenDay(true);
                        processResponse(11, DoorOpenType.ADMIN_OPEN_ALWAYS, doorAccessRequest, doorAccessResponse, date);
                    } else {
                        accDao.addAccAttLog(userInfo, ZKLauncher.sInOutState, ZKLauncher.sDoorId, 11, this.dbSDF.format(date2).replace(" ", ExifInterface.GPS_DIRECTION_TRUE), doorVerifyType, -1.0d, 0);
                    }
                } else {
                    DoorAccessResponse doorAccessResponse2 = doorAccessResponse;
                    boolean checkDoor1CancelKeepOpenDay2 = accDao.checkDoor1CancelKeepOpenDay();
                    if (!doorAccessResponse.isStressFingerAlarmOn() && !doorAccessResponse.isStressPasswordAlarmOn()) {
                        processResponse(checkDoor1CancelKeepOpenDay2, DoorOpenType.LOCAL_OPEN, doorAccessRequest, doorAccessResponse, date);
                    }
                }
            }
        }
    }
}
