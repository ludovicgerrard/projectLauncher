package com.zkteco.android.employeemgmt.common;

import android.content.Context;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final int MAX_ACC_GROUP = 99;
    public static final int MAX_TIME_ZONE = 50;
    public static final int MIN_ACC_GROUP = 0;
    public static final int MIN_TIME_ZONE = 0;

    private static boolean isCard(DataManager dataManager) {
        int intOption = dataManager.getIntOption("~RFCardOn", 0);
        int intOption2 = dataManager.getIntOption(WiegandConfig.IsSupportSFZ, 0);
        if (intOption == 1 || intOption2 == 1) {
            return true;
        }
        return false;
    }

    public static List<ZKStaffVerifyBean> getVerifyList(Context context) {
        ArrayList arrayList = new ArrayList();
        DataManager instance = DBManager.getInstance();
        int intOption = instance.getIntOption("FingerFunOn", 1);
        int intOption2 = instance.getIntOption("hasFingerModule", 1);
        int intOption3 = instance.getIntOption("FaceFunOn", 0);
        boolean isCard = isCard(instance);
        ArrayList arrayList2 = new ArrayList();
        if (isCard) {
            arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list0), 0));
        } else {
            arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list0_1), 0));
        }
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list1), 1));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list2), 2));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list3), 3));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list4), 4));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list5), 5));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list6), 6));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list7), 7));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list8), 8));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list9), 9));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list10), 10));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list11), 11));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list12), 12));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list13), 13));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list14), 14));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list15), 15));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list16), 16));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list17), 17));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list18), 18));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list19), 19));
        arrayList2.add(new ZKStaffVerifyBean(context.getString(R.string.zk_staff_vertify_list20), 20));
        for (int i = 0; i < arrayList2.size(); i++) {
            if (((intOption == 1 && intOption2 == 1) || !((ZKStaffVerifyBean) arrayList2.get(i)).getString().contains(context.getString(R.string.zk_staff_vertify_list1))) && ((isCard || !((ZKStaffVerifyBean) arrayList2.get(i)).getString().contains(context.getString(R.string.zk_staff_vertify_list4))) && (intOption3 == 1 || !((ZKStaffVerifyBean) arrayList2.get(i)).getString().contains(context.getString(R.string.zk_staff_vertify_list16))))) {
                arrayList.add((ZKStaffVerifyBean) arrayList2.get(i));
            }
        }
        return arrayList;
    }
}
