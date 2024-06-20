package com.zktechnology.android.verify.bean;

import com.zkteco.android.db.orm.tna.AccMultiUser;
import java.util.ArrayList;
import java.util.List;

public class ZKVerifyOpenDoorResultBean {
    public static final int ILLEGAL_GROUP_STATUS = 8;
    public static final int SUCCESS_GROUP_STATUS = 111;
    public static final int WAIT_NEXT_GROUP_STATUS = 9;
    private static boolean enableOpen = false;
    private static int groupVerifyPeriodStatus = 8;
    private static boolean multiUserVerifyStatus = false;
    private static List<AccMultiUser> multiUsers;
    private static ZKVerifyOpenDoorResultBean resultBean;
    private static List<Integer> userGroupList = new ArrayList();

    private ZKVerifyOpenDoorResultBean() {
    }

    public static synchronized ZKVerifyOpenDoorResultBean getInstance() {
        ZKVerifyOpenDoorResultBean zKVerifyOpenDoorResultBean;
        synchronized (ZKVerifyOpenDoorResultBean.class) {
            if (resultBean == null) {
                resultBean = new ZKVerifyOpenDoorResultBean();
            }
            zKVerifyOpenDoorResultBean = resultBean;
        }
        return zKVerifyOpenDoorResultBean;
    }

    public static void resetAllStatus() {
        groupVerifyPeriodStatus = 8;
        multiUserVerifyStatus = false;
        enableOpen = false;
        multiUsers = null;
        userGroupList.clear();
    }

    public static void check() {
        enableOpen = false;
        groupVerifyPeriodStatus = 8;
        List<AccMultiUser> list = multiUsers;
        if (list == null || list.size() == 0) {
            enableOpen = false;
            return;
        }
        int i = 0;
        while (i < multiUsers.size()) {
            AccMultiUser accMultiUser = multiUsers.get(i);
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
                    int i5 = 0;
                    while (true) {
                        if (i5 >= userGroupList.size()) {
                            break;
                        } else if (userGroupList.get(i5).intValue() == i4) {
                            break;
                        } else {
                            i5++;
                        }
                    }
                    i2++;
                }
                i3++;
                i2++;
            }
            if (i3 == 5) {
                groupVerifyPeriodStatus = 111;
                enableOpen = true;
                return;
            } else if (i3 >= userGroupList.size()) {
                groupVerifyPeriodStatus = 9;
                enableOpen = false;
                return;
            } else {
                i++;
            }
        }
    }
}
