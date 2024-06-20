package com.zktechnology.android.acc.advance.Interceptors;

import android.content.Context;
import android.os.SystemClock;
import com.zktechnology.android.acc.DoorOpenType;
import com.zktechnology.android.acc.advance.DoorAccessRequest;
import com.zktechnology.android.acc.advance.DoorAccessResponse;
import com.zktechnology.android.acc.advance.Interceptor;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.bean.process.ZKVerifyInfo;
import com.zktechnology.android.verify.dao.ZKAccDao;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.tna.AccMultiUser;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class MultipleVerifyInterceptor extends BaseInterceptor implements Interceptor {
    private static final int MAXIMUM_GROUP_SIZE = 5;
    private static final String TAG = "MultipleVerifyInterceptor";

    private boolean checkVerify(List<AccMultiUser> list, Hashtable<Integer, ArrayList<String>> hashtable) {
        Hashtable<Integer, ArrayList<String>> hashtable2 = hashtable;
        Hashtable hashtable3 = new Hashtable();
        for (int i = 0; i < list.size(); i++) {
            hashtable3.clear();
            AccMultiUser accMultiUser = list.get(i);
            int group1 = accMultiUser.getGroup1();
            int group2 = accMultiUser.getGroup2();
            int group3 = accMultiUser.getGroup3();
            int group4 = accMultiUser.getGroup4();
            int group5 = accMultiUser.getGroup5();
            int i2 = 0;
            int i3 = 0;
            while (i2 < 5) {
                int i4 = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? 0 : group5 : group4 : group3 : group2 : group1;
                if (i4 != 0) {
                    ArrayList arrayList = null;
                    if (hashtable2.containsKey(Integer.valueOf(i4))) {
                        arrayList = hashtable2.get(Integer.valueOf(i4));
                    }
                    if (arrayList != null) {
                        int intValue = hashtable3.containsKey(Integer.valueOf(i4)) ? ((Integer) hashtable3.get(Integer.valueOf(i4))).intValue() : 0;
                        if (intValue < arrayList.size()) {
                            hashtable3.put(Integer.valueOf(i4), Integer.valueOf(intValue + 1));
                        }
                    }
                    i2++;
                }
                i3++;
                i2++;
            }
            if (i3 == 5) {
                return true;
            }
        }
        return false;
    }

    private List<AccMultiUser> getAccGroupList(ZKAccDao zKAccDao, int i) {
        List<AccMultiUser> accGroupList = zKAccDao.getAccGroupList(i);
        for (int i2 = 0; i2 < accGroupList.size(); i2++) {
            AccMultiUser accMultiUser = accGroupList.get(i2);
            int i3 = accMultiUser.getGroup1() > 0 ? 1 : 0;
            if (accMultiUser.getGroup2() > 0) {
                i3++;
            }
            if (accMultiUser.getGroup3() > 0) {
                i3++;
            }
            if (accMultiUser.getGroup4() > 0) {
                i3++;
            }
            if (accMultiUser.getGroup5() > 0) {
                i3++;
            }
            if (i3 < 2) {
                accGroupList.remove(i2);
            }
        }
        return accGroupList;
    }

    public void interceptor(DoorAccessRequest doorAccessRequest, DoorAccessResponse doorAccessResponse, Date date) {
        UserInfo userInfo = doorAccessRequest.getUserInfo();
        if (userInfo != null) {
            ZKAccDao accDao = doorAccessRequest.getAccDao();
            if (accDao.getMultiCardOpenDoorFuncState() != 0 && accDao.getMultiCardOpenDoorState() != 0) {
                Context context = doorAccessRequest.getContext();
                String string = context.getResources().getString(R.string.illegal_access);
                String string2 = context.getString(R.string.illegal_group);
                String string3 = context.getString(R.string.multi_verify_wait);
                int acc_Group_ID = userInfo.getAcc_Group_ID();
                StringBuilder sb = new StringBuilder();
                String str = TAG;
                LogUtils.verifyLog(sb.append(str).append(" groupId: ").append(acc_Group_ID).toString());
                if (acc_Group_ID < 0) {
                    processResponse(string, 23, doorAccessRequest, doorAccessResponse, date);
                } else if (acc_Group_ID == 0) {
                    processResponse(string2, 48, doorAccessRequest, doorAccessResponse, date);
                } else {
                    List<AccMultiUser> accGroupList = getAccGroupList(accDao, acc_Group_ID);
                    if (accGroupList == null || accGroupList.size() == 0) {
                        processResponse(string2, 48, doorAccessRequest, doorAccessResponse, date);
                        return;
                    }
                    ZKVerifyInfo verifyInfo = doorAccessRequest.getVerifyInfo();
                    long lastMultipleVerifyTime = verifyInfo.getLastMultipleVerifyTime();
                    boolean isVerifyExpired = isVerifyExpired(lastMultipleVerifyTime, ((long) DBManager.getInstance().getIntOption(ZKDBConfig.ACC_MULTI_USER_VERIFY_TIME, 8)) * 1000);
                    LogUtils.verifyFormatLog("%s lastVerifyTime:%s isVerifyExpired:%s.", str, Long.valueOf(lastMultipleVerifyTime), Boolean.valueOf(isVerifyExpired));
                    verifyInfo.setLastMultipleVerifyTime(SystemClock.elapsedRealtime());
                    if (isVerifyExpired) {
                        verifyInfo.setGroupUserList((Hashtable<Integer, ArrayList<String>>) null);
                        verifyInfo.setLastMultipleVerifyTime(0);
                        processResponse(context.getString(R.string.multi_verify_timeout), 48, doorAccessRequest, doorAccessResponse, date);
                        return;
                    }
                    int acc_Group_ID2 = userInfo.getAcc_Group_ID();
                    Hashtable<Integer, ArrayList<String>> groupUserList = verifyInfo.getGroupUserList();
                    if (!groupUserList.containsKey(Integer.valueOf(acc_Group_ID2))) {
                        groupUserList.put(Integer.valueOf(acc_Group_ID2), new ArrayList());
                    }
                    String user_PIN = userInfo.getUser_PIN();
                    ArrayList computeIfAbsent = groupUserList.computeIfAbsent(Integer.valueOf(acc_Group_ID2), $$Lambda$MultipleVerifyInterceptor$c_uasGmMBp6EoJSYHjGwUqbHY4.INSTANCE);
                    if (computeIfAbsent.contains(user_PIN)) {
                        LogUtils.verifyFormatLog("%s user:%s groupId:%s, has been verified.", str, user_PIN, Integer.valueOf(acc_Group_ID2));
                        processResponse(string3, 26, doorAccessRequest, doorAccessResponse, date);
                        return;
                    }
                    computeIfAbsent.add(user_PIN);
                    groupUserList.put(Integer.valueOf(acc_Group_ID2), computeIfAbsent);
                    verifyInfo.setGroupUserList(groupUserList);
                    boolean checkVerify = checkVerify(accGroupList, groupUserList);
                    LogUtils.verifyFormatLog("%s user:%s groupId:%s checkResult:%s, new user has been verify.", str, user_PIN, Integer.valueOf(acc_Group_ID2), Boolean.valueOf(checkVerify));
                    if (checkVerify) {
                        verifyInfo.setGroupUserList((Hashtable<Integer, ArrayList<String>>) null);
                        verifyInfo.setLastMultipleVerifyTime(0);
                        processResponse(3, DoorOpenType.LOCAL_OPEN, doorAccessRequest, doorAccessResponse, date);
                        return;
                    }
                    processResponse(string3, 26, doorAccessRequest, doorAccessResponse, date);
                }
            }
        }
    }

    static /* synthetic */ ArrayList lambda$interceptor$0(Integer num) {
        return new ArrayList();
    }
}
