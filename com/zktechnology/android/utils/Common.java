package com.zktechnology.android.utils;

public class Common {
    public static final String CAT_PATH = "/system/bin/cat";
    public static final String CPU_FREQ_AVAILABLE_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
    public static final String CPU_FREQ_CUR_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
    public static final String CPU_FREQ_SETSPEED_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_setspeed";
    public static final String CPU_MODE_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    public static final String CPU_USERSPACE_MODE = "userspace";
    public static final String ECHO_PATH = "echo";
    public static final String GPIO_DIRCTION_IN = "in";
    public static final String GPIO_DIRCTION_NAME = "direction";
    public static final String GPIO_DIRCTION_OUT = "out";
    public static final String GPIO_EXPORT_PATH = "/sys/class/gpio/export";
    public static final String GPIO_HIGH = "1";
    public static final String GPIO_LOW = "0";
    public static final String GPIO_OTG1 = "97";
    public static final String GPIO_OTG1_NAME = "gpio97";
    public static final String GPIO_OTG1_PATH = "/sys/class/gpio/gpio97";
    public static final String GPIO_OTG2 = "107";
    public static final String GPIO_OTG2_NAME = "gpio107";
    public static final String GPIO_OTG2_PATH = "/sys/class/gpio/gpio107";
    public static final String GPIO_ROOT_PATH = "/sys/class/gpio/";
    public static final String GPIO_UNEXPORT_PATH = "/sys/class/gpio/unexport";
    public static final String GPIO_VALUE_NAME = "value";
    public static final String HWCLOCK_PATH = "/system/bin/hwclock";
    public static final String INFO = "INFO";
    public static final String OVER = "OVER";
    public static final int RESULT_ERR = -1;
    public static final int RESULT_OK = 0;
    public static String RESULT_PATH = "/sdcard/memtester_result.txt";
    public static final String RTC_PATH = "/dev/rtc1";
    public static final int TYPE_INFO = 0;
    public static final int TYPE_OVER = 1;

    public String setGreenText(String str) {
        return "<font color='green'>" + str + "</font>";
    }

    public String setRedText(String str) {
        return "<font color='red'>" + str + "</font>";
    }
}
