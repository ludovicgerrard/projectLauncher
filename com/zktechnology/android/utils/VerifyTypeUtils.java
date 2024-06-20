package com.zktechnology.android.utils;

import android.content.Context;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VerifyTypeUtils {
    public static String FaceFunOn;
    public static String FvFunOn;
    public static String IsSupportSFZ;
    public static String PvFunOn;
    public static String RFCardOn;
    public static String fingerFunOn;
    public static String hasFingerModule;
    public static final List<String> mData = new ArrayList();
    private static String[] mVerifyTypes = new String[0];
    public static Map<Integer, String> verifyMap;

    public static void init(Context context) {
        DataManager instance = DBManager.getInstance();
        mVerifyTypes = new String[]{context.getString(R.string.dlg_ver_process_way_fingerprint), context.getString(R.string.zk_staff_vertify_list3), context.getString(R.string.zk_staff_vertify_list4), context.getString(R.string.zk_staff_vertify_list15), context.getString(R.string.zk_staff_vertify_list2), context.getString(R.string.zk_staff_vertify_fingervein), context.getString(R.string.zk_staff_vertify_palmvein), context.getString(R.string.zk_staff_vertify_iris)};
        fingerFunOn = instance.getStrOption("FingerFunOn", "0");
        hasFingerModule = instance.getStrOption("hasFingerModule", "0");
        RFCardOn = instance.getStrOption("~RFCardOn", "0");
        IsSupportSFZ = instance.getStrOption(WiegandConfig.IsSupportSFZ, "0");
        FaceFunOn = instance.getStrOption("FaceFunOn", "0");
        FvFunOn = instance.getStrOption(ZKDBConfig.OPT_FV_FUN, "0");
        PvFunOn = instance.getStrOption("PvFunOn", "0");
    }

    public static List<String> getVerifyTypeList() {
        verifyMap = getmVerifyTypeMap();
        mData.clear();
        for (Integer num : verifyMap.keySet()) {
            mData.add(verifyMap.get(num));
        }
        return mData;
    }

    public static Map getmVerifyTypeMap() {
        verifyMap = new LinkedHashMap();
        StringBuffer stringBuffer = new StringBuffer();
        if (!enableFv() && enableFinger()) {
            stringBuffer.append(mVerifyTypes[0]).append("/");
        }
        stringBuffer.append(mVerifyTypes[1]);
        if (enableCard()) {
            stringBuffer.append("/").append(mVerifyTypes[2]);
        }
        if (enableFace()) {
            stringBuffer.append("/").append(mVerifyTypes[3]);
        }
        if (enablePv()) {
            stringBuffer.append("/").append(mVerifyTypes[6]);
        }
        verifyMap.put(0, stringBuffer.toString());
        if (!enableFv() && enableFinger()) {
            verifyMap.put(1, mVerifyTypes[0]);
        }
        verifyMap.put(2, mVerifyTypes[4]);
        if (enableFinger() || enableFace() || enableFv() || enableCard() || enableSFZ()) {
            verifyMap.put(3, mVerifyTypes[1]);
        }
        if (enableCard() || enableSFZ()) {
            verifyMap.put(4, mVerifyTypes[2]);
        }
        if (!enableFv() && enableFinger() && (enableCard() || enableSFZ() || enableFace())) {
            verifyMap.put(5, mVerifyTypes[0] + "/" + mVerifyTypes[1]);
        }
        if (!enableFv() && enableFinger() && (enableCard() || enableSFZ())) {
            verifyMap.put(6, mVerifyTypes[0] + "/" + mVerifyTypes[2]);
        }
        if ((enableCard() || enableSFZ()) && (enableFinger() || enableFace())) {
            verifyMap.put(7, mVerifyTypes[1] + "/" + mVerifyTypes[2]);
        }
        if (!enableFv() && enableFinger()) {
            verifyMap.put(8, mVerifyTypes[4] + "+" + mVerifyTypes[0]);
        }
        if (!enableFv() && enableFinger()) {
            verifyMap.put(9, mVerifyTypes[0] + "+" + mVerifyTypes[1]);
        }
        if (!enableFv() && enableFinger() && (enableCard() || enableSFZ())) {
            verifyMap.put(10, mVerifyTypes[0] + "+" + mVerifyTypes[2]);
            verifyMap.put(12, mVerifyTypes[0] + "+" + mVerifyTypes[1] + "+" + mVerifyTypes[2]);
        }
        if (enableCard() || enableSFZ()) {
            verifyMap.put(11, mVerifyTypes[1] + "+" + mVerifyTypes[2]);
        }
        if (!enableFv() && enableFinger()) {
            verifyMap.put(13, mVerifyTypes[0] + "+" + mVerifyTypes[4] + "+" + mVerifyTypes[1]);
        }
        if (!enableFv() && enableFinger() && (enableCard() || enableSFZ())) {
            verifyMap.put(14, mVerifyTypes[0] + "+(" + mVerifyTypes[2] + "+" + mVerifyTypes[4] + ")");
        }
        if (enableFace()) {
            verifyMap.put(15, mVerifyTypes[3]);
        }
        if (enableFace() && !enableFv() && enableFinger()) {
            verifyMap.put(16, mVerifyTypes[3] + "+" + mVerifyTypes[0]);
        }
        if (enableFace()) {
            verifyMap.put(17, mVerifyTypes[3] + "+" + mVerifyTypes[1]);
        }
        if (enableFace() && (enableCard() || enableSFZ())) {
            verifyMap.put(18, mVerifyTypes[3] + "+" + mVerifyTypes[2]);
        }
        if (enableFace() && !enableFv() && enableFinger() && (enableCard() || enableSFZ())) {
            verifyMap.put(19, mVerifyTypes[3] + "+" + mVerifyTypes[0] + "+" + mVerifyTypes[2]);
        }
        if (enableFace() && !enableFv() && enableFinger()) {
            verifyMap.put(20, mVerifyTypes[3] + "+" + mVerifyTypes[0] + "+" + mVerifyTypes[1]);
        }
        if (enableFv()) {
            verifyMap.put(21, mVerifyTypes[5]);
        }
        if (enableFv()) {
            verifyMap.put(22, mVerifyTypes[5] + "+" + mVerifyTypes[1]);
        }
        if (enableFv() && (enableCard() || enableSFZ())) {
            verifyMap.put(23, mVerifyTypes[5] + "+" + mVerifyTypes[2]);
        }
        if (enableFv() && (enableCard() || enableSFZ())) {
            verifyMap.put(24, mVerifyTypes[5] + "+" + mVerifyTypes[2] + "+" + mVerifyTypes[1]);
        }
        if (enablePv()) {
            verifyMap.put(25, mVerifyTypes[6]);
        }
        return verifyMap;
    }

    public static boolean enableFinger() {
        return fingerFunOn.equals("1") && hasFingerModule.equals("1");
    }

    public static boolean enableFace() {
        return FaceFunOn.equals("1");
    }

    public static boolean enableCard() {
        return RFCardOn.equals("1");
    }

    public static boolean enableSFZ() {
        return IsSupportSFZ.equals("1");
    }

    public static boolean enablePv() {
        return PvFunOn.equals("1");
    }

    public static boolean enableFv() {
        return FvFunOn.equals("1");
    }
}
