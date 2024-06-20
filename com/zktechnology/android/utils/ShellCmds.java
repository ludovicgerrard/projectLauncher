package com.zktechnology.android.utils;

import android.util.Log;
import com.zktechnology.android.utils.ShellUtils;
import java.util.ArrayList;
import java.util.List;

public class ShellCmds {
    private static String TAG = "ShellCmds";

    public static String generateCmd(String str, List<String> list) {
        String str2 = str + " ";
        for (int i = 0; i < list.size(); i++) {
            str2 = str2 + list.get(i) + " ";
        }
        return str2;
    }

    public static ShellUtils.CommandResult readRTC(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(" -f ");
        arrayList.add(str);
        return ShellUtils.execCommand(generateCmd(Common.HWCLOCK_PATH, arrayList), true);
    }

    public static ShellUtils.CommandResult writeRTCFromSystem(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(" -f ");
        arrayList.add(str);
        arrayList.add(" -w");
        return ShellUtils.execCommand(generateCmd(Common.HWCLOCK_PATH, arrayList), true);
    }

    public static ShellUtils.CommandResult writeSystemFromRTC(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(" -f ");
        arrayList.add(str);
        arrayList.add(" -s");
        return ShellUtils.execCommand(generateCmd(Common.HWCLOCK_PATH, arrayList), true);
    }

    public static String[] getAvailCpuFreq() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Common.CPU_FREQ_AVAILABLE_PATH);
        ShellUtils.CommandResult execCommand = ShellUtils.execCommand(generateCmd(Common.CAT_PATH, arrayList), true);
        Log.d(TAG, "licq getAvailCpuFreq():" + execCommand.successMsg);
        return execCommand.successMsg.split(" ");
    }

    public static String getCpuCurFreq() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Common.CPU_FREQ_CUR_PATH);
        ShellUtils.CommandResult execCommand = ShellUtils.execCommand(generateCmd(Common.CAT_PATH, arrayList), true);
        Log.d(TAG, "licq getCpuCurFreq():" + execCommand.successMsg + " " + execCommand.result);
        return execCommand.successMsg;
    }

    public static int indexCurFreq(String[] strArr, String str) {
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].indexOf(str) != -1) {
                return i;
            }
        }
        return strArr.length;
    }

    public static void setCpuMode(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(" > ");
        arrayList.add(Common.CPU_MODE_PATH);
        Log.d(TAG, "licq setCpuMode() result:" + ShellUtils.execCommand(generateCmd(Common.ECHO_PATH, arrayList), true).result);
    }

    public static void setCpuFreq(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(" > ");
        arrayList.add(Common.CPU_FREQ_SETSPEED_PATH);
        Log.d(TAG, "licq setCpuFreq() result:" + ShellUtils.execCommand(generateCmd(Common.ECHO_PATH, arrayList), true).result);
    }

    public static String upCpuFreq() {
        String[] availCpuFreq = getAvailCpuFreq();
        int indexCurFreq = indexCurFreq(availCpuFreq, getCpuCurFreq());
        setCpuMode(Common.CPU_USERSPACE_MODE);
        setCpuFreq(availCpuFreq[(indexCurFreq + 1) % availCpuFreq.length]);
        return getCpuCurFreq();
    }

    public static String downCpuFreq() {
        String[] availCpuFreq = getAvailCpuFreq();
        int indexCurFreq = indexCurFreq(availCpuFreq, getCpuCurFreq());
        setCpuMode(Common.CPU_USERSPACE_MODE);
        if (indexCurFreq == 0) {
            setCpuFreq(availCpuFreq[availCpuFreq.length - 1]);
        } else {
            setCpuFreq(availCpuFreq[(indexCurFreq - 1) % availCpuFreq.length]);
        }
        return getCpuCurFreq();
    }

    public static int exportGpio(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(" > ");
        arrayList.add(Common.GPIO_EXPORT_PATH);
        ShellUtils.CommandResult execCommand = ShellUtils.execCommand(generateCmd(Common.ECHO_PATH, arrayList), true);
        Log.d(TAG, "licq exportGpio() result:" + execCommand.result);
        return execCommand.result;
    }

    public static int unexportGpio(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(" > ");
        arrayList.add(Common.GPIO_UNEXPORT_PATH);
        ShellUtils.CommandResult execCommand = ShellUtils.execCommand(generateCmd(Common.ECHO_PATH, arrayList), true);
        Log.d(TAG, "licq unexportGpio() result:" + execCommand.result);
        return execCommand.result;
    }

    public static int setGpioDirection(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        arrayList.add(" > ");
        arrayList.add(str + "/" + "direction");
        ShellUtils.CommandResult execCommand = ShellUtils.execCommand(generateCmd(Common.ECHO_PATH, arrayList), true);
        Log.d(TAG, "licq setGpioDirection() result:" + execCommand.result);
        return execCommand.result;
    }

    public static int setGpioValue(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        arrayList.add(" > ");
        arrayList.add(str + "/" + "value");
        ShellUtils.CommandResult execCommand = ShellUtils.execCommand(generateCmd(Common.ECHO_PATH, arrayList), true);
        Log.d(TAG, "licq setGpioValue() result:" + execCommand.result);
        return execCommand.result;
    }

    public static String readGpioValue(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str + "/" + "value");
        ShellUtils.CommandResult execCommand = ShellUtils.execCommand(generateCmd(Common.CAT_PATH, arrayList), true);
        Log.d(TAG, "licq readGpioValue() result:" + execCommand.result);
        return execCommand.successMsg;
    }

    public static int setMaster() {
        int i;
        if (!"1".equals(readGpioValue(Common.GPIO_OTG1_PATH))) {
            exportGpio(Common.GPIO_OTG1);
            setGpioDirection(Common.GPIO_OTG1_PATH, Common.GPIO_DIRCTION_OUT);
            i = setGpioValue(Common.GPIO_OTG1_PATH, "1");
        } else {
            i = -1;
        }
        if ("1".equals(readGpioValue(Common.GPIO_OTG2_PATH))) {
            return i;
        }
        exportGpio(Common.GPIO_OTG2);
        setGpioDirection(Common.GPIO_OTG2_PATH, Common.GPIO_DIRCTION_OUT);
        return setGpioValue(Common.GPIO_OTG2_PATH, "1");
    }

    public static int setSlaver() {
        int i;
        if (!"0".equals(readGpioValue(Common.GPIO_OTG1_PATH))) {
            exportGpio(Common.GPIO_OTG1);
            setGpioDirection(Common.GPIO_OTG1_PATH, Common.GPIO_DIRCTION_OUT);
            i = setGpioValue(Common.GPIO_OTG1_PATH, "0");
        } else {
            i = -1;
        }
        if ("0".equals(readGpioValue(Common.GPIO_OTG2_PATH))) {
            return i;
        }
        exportGpio(Common.GPIO_OTG2);
        setGpioDirection(Common.GPIO_OTG2_PATH, Common.GPIO_DIRCTION_OUT);
        return setGpioValue(Common.GPIO_OTG2_PATH, "0");
    }
}
