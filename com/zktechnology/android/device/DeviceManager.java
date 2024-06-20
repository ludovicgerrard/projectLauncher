package com.zktechnology.android.device;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.zktechnology.android.device.camera.CameraConfig;
import com.zktechnology.android.device.camera.CameraParam;
import com.zktechnology.android.launcher.BuildConfig;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.FileUtils;
import com.zktechnology.android.utils.GlobalConfig;
import com.zktechnology.android.utils.ZkLogTag;
import com.zktechnology.android.utils.ZkShellUtils;
import com.zkteco.android.core.device.DeviceType;
import com.zkteco.android.core.device.PlatformType;
import com.zkteco.android.core.device.ZkStoreManager;
import com.zkteco.android.io.YAMLConfigurationReader;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import com.zkteco.edk.hardware.encrypt.ZkHardwareUtils;
import java.io.File;
import java.io.IOException;
import java.security.SignatureException;
import java.util.ArrayList;

public class DeviceManager {
    private static final String[] DEVICE_NAME_LIST = {DeviceType.HORUS_H1, DeviceType.G6, DeviceType.G5, DeviceType.G4_PRO, DeviceType.IRIS};
    private static final String KEY_CAMERA_ANGLE = "camera-angle-";
    private static final String KEY_CAMERA_DEFAULT_INDEX = "camera-default-index";
    private static final String KEY_CAMERA_MIRROR = "camera-mirror-";
    private static final String KEY_CAMERA_PREVIEW_HEIGHT = "camera-preview-height";
    private static final String KEY_CAMERA_PREVIEW_WIDTH = "camera-preview-width";
    private static final String KEY_CAMERA_RESOLUTION = "camera-resolution-";
    private static final String KEY_CAMERA_SECONDARY_INDEX = "camera-secondary-index";
    private static final String KEY_CAMERA_TYPE = "camera-type";
    private static final String KEY_DEVICE_TYPE = "device-type";
    private static final String KEY_EXECUTE_CLEAR_PHOTO_WORK_TIME = "lastExecuteWorkTime";
    private static final String KEY_FIRMWARE_ACTIVATION_STATUS = "isFirmwareActivated";
    private static final String KEY_SUPPORT_FACE_ANTI_FAKE = "support-face-anti-fake";
    private static final String KEY_SUPPORT_PALM = "support-palm";
    private static final String SYSTEM_SERIAL_NUMBER_PATH = "/system/etc/ZKTeco/serial_number";
    private static final String TAG = "DeviceManager";
    private CameraConfig mCameraConfig;
    private String mDeviceType;
    private String mPlatformType;
    private ZkStoreManager mStoreManager;
    private final ZKSharedUtil sharedUtil;

    private static String queryDeviceType() {
        String strOption = DBManager.getInstance().getStrOption("~DeviceName", "Unknown");
        Log.i(ZkLogTag.OPTION_QUERY, "~DeviceName : " + strOption);
        String deviceTypeFromSystemParam = getDeviceTypeFromSystemParam();
        if (!TextUtils.isEmpty(deviceTypeFromSystemParam) && !deviceTypeFromSystemParam.equals(strOption)) {
            changeDeviceNameFromSystemParam(deviceTypeFromSystemParam);
            if (DeviceType.HORUS_H1.equals(deviceTypeFromSystemParam)) {
                DBManager.getInstance().setIntOption("AccessRuleType", 0);
            }
            strOption = deviceTypeFromSystemParam;
        }
        for (String str : DEVICE_NAME_LIST) {
            if (strOption.startsWith(str)) {
                return str;
            }
        }
        return strOption;
    }

    private static String getDeviceTypeFromSystemParam() {
        ZkShellUtils.CommandResult execCommand = ZkShellUtils.execCommand("getprop persist.zkteco.device.name", false);
        if (execCommand.result != -1 && !TextUtils.isEmpty(execCommand.successMsg)) {
            return execCommand.successMsg;
        }
        ZkShellUtils.CommandResult execCommand2 = ZkShellUtils.execCommand("getprop zkteco.device.name", false);
        if (execCommand2.result != -1 && !TextUtils.isEmpty(execCommand2.successMsg)) {
            return execCommand2.successMsg;
        }
        ZkShellUtils.CommandResult execCommand3 = ZkShellUtils.execCommand("devtool -g zkteco.device.name", false);
        if (execCommand3.result == -1 || TextUtils.isEmpty(execCommand3.successMsg)) {
            ZkShellUtils.CommandResult execCommand4 = ZkShellUtils.execCommand("device_config get ZKTECO ~DeviceName", false);
            return (execCommand4.result == -1 || TextUtils.isEmpty(execCommand4.successMsg) || "null".equals(execCommand4.successMsg)) ? "Unknown" : execCommand4.successMsg;
        }
        String replace = execCommand3.successMsg.replace("[", "").replace("]", "");
        if (replace.contains(SimpleComparison.EQUAL_TO_OPERATION)) {
            String[] split = replace.split(SimpleComparison.EQUAL_TO_OPERATION);
            if (split.length > 1) {
                return split[1];
            }
        }
        return execCommand3.successMsg;
    }

    private static void changeDeviceNameFromSystemParam(String str) {
        if (!TextUtils.isEmpty(str)) {
            Log.i(ZkLogTag.OPTION_CHANGE, String.format("set ~DeviceName:%s ret = %s", new Object[]{str, Integer.valueOf(DBManager.getInstance().setStrOption("~DeviceName", str))}));
        }
    }

    public void initDeviceType() throws IOException, SignatureException {
        String queryDeviceType = queryDeviceType();
        Log.i(TAG, "getDeviceTypeByDatabase: " + this.mDeviceType);
        if (queryDeviceType.contains(DeviceType.G4_PRO)) {
            this.mDeviceType = DeviceType.G4_PRO;
        } else if (queryDeviceType.contains(DeviceType.G5)) {
            this.mDeviceType = DeviceType.G5;
        } else if (queryDeviceType.contains(DeviceType.G6)) {
            this.mDeviceType = DeviceType.G6;
        } else if (queryDeviceType.contains(DeviceType.HORUS_H1)) {
            this.mDeviceType = DeviceType.HORUS_H1;
        } else if (queryDeviceType.contains(DeviceType.IRIS)) {
            this.mDeviceType = DeviceType.IRIS;
        } else {
            this.mDeviceType = "Unknown";
        }
        this.mStoreManager = new ZkStoreManager(this.mDeviceType);
        String str = this.mDeviceType + ".yml";
        String format = String.format("%s/device", new Object[]{Environment.getExternalStorageDirectory()});
        File file = new File(format + File.separator + str);
        if (!file.exists()) {
            copyAssetFile(LauncherApplication.getLauncherApplicationContext(), String.format("device/%s", new Object[]{str}), format, File.separator + str);
        }
        YAMLConfigurationReader yAMLConfigurationReader = new YAMLConfigurationReader(file.getPath(), false);
        String str2 = (String) yAMLConfigurationReader.get("device-type", "");
        Log.i(TAG, "getDeviceFromYML: " + str2);
        if (!str2.equals(this.mDeviceType)) {
            Log.e(TAG, "device type not sample!");
            return;
        }
        this.mCameraConfig = new CameraConfig();
        this.mCameraConfig.setCameraType(((Integer) yAMLConfigurationReader.get(KEY_CAMERA_TYPE, 1)).intValue());
        int intValue = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_PREVIEW_WIDTH, 720)).intValue();
        int intValue2 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_PREVIEW_HEIGHT, 1280)).intValue();
        this.mCameraConfig.setPreviewWidth(intValue);
        this.mCameraConfig.setPreviewHeight(intValue2);
        int i = -1;
        int intValue3 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_DEFAULT_INDEX, -1)).intValue();
        Log.i(TAG, "default camera index: " + intValue3);
        if (intValue3 >= 0) {
            int intValue4 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_ANGLE + intValue3, 1)).intValue();
            int intValue5 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_MIRROR + intValue3, 1)).intValue();
            int intValue6 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_RESOLUTION + intValue3, 3)).intValue();
            Log.i(TAG, String.format("default camera index:%s angle:%s mirror:%s resolution:%s ", new Object[]{Integer.valueOf(intValue3), Integer.valueOf(intValue4), Integer.valueOf(intValue5), Integer.valueOf(intValue6)}));
            ArrayList arrayList = new ArrayList();
            arrayList.add(new CameraParam(intValue4, intValue3, intValue5, intValue6));
            this.mCameraConfig.setParams(arrayList);
            i = -1;
        }
        int intValue7 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_SECONDARY_INDEX, Integer.valueOf(i))).intValue();
        Log.i(TAG, "second camera index: " + intValue7);
        if (intValue7 >= 0) {
            int intValue8 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_ANGLE + intValue7, 1)).intValue();
            int intValue9 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_MIRROR + intValue7, 1)).intValue();
            int intValue10 = ((Integer) yAMLConfigurationReader.get(KEY_CAMERA_RESOLUTION + intValue7, 3)).intValue();
            Log.i(TAG, String.format("second camera index:%s angle:%s mirror:%s resolution:%s ", new Object[]{Integer.valueOf(intValue7), Integer.valueOf(intValue8), Integer.valueOf(intValue9), Integer.valueOf(intValue10)}));
            this.mCameraConfig.getParams().add(new CameraParam(intValue8, intValue7, intValue9, intValue10));
        }
        int intValue11 = ((Integer) yAMLConfigurationReader.get(KEY_SUPPORT_FACE_ANTI_FAKE, 0)).intValue();
        int intValue12 = ((Integer) yAMLConfigurationReader.get(KEY_SUPPORT_PALM, 0)).intValue();
        Log.i(TAG, String.format("anti fake: %s palm: %s", new Object[]{Integer.valueOf(intValue11), Integer.valueOf(intValue12)}));
        this.sharedUtil.putString("device-type", this.mDeviceType);
        this.sharedUtil.putString(GlobalConfig.SHARED_KEY_CAMERA_CONFIG, JSON.toJSONString(this.mCameraConfig));
        this.sharedUtil.putInt(GlobalConfig.SHARED_KEY_SUPPORT_FACE_ANTI_FAKE, intValue11);
        this.sharedUtil.putInt(GlobalConfig.SHARED_KEY_SUPPORT_PALM, intValue12);
    }

    public void initPlatformType() throws IOException, SignatureException {
        String str = this.mDeviceType;
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -262767315:
                if (str.equals(DeviceType.HORUS_H1)) {
                    c2 = 0;
                    break;
                }
                break;
            case 2254:
                if (str.equals(DeviceType.G5)) {
                    c2 = 1;
                    break;
                }
                break;
            case 2255:
                if (str.equals(DeviceType.G6)) {
                    c2 = 2;
                    break;
                }
                break;
            case 2081726650:
                if (str.equals(DeviceType.G4_PRO)) {
                    c2 = 3;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                this.mPlatformType = PlatformType.HORUS_H1;
                break;
            case 1:
            case 3:
                this.mPlatformType = "ZIM200";
                break;
            case 2:
                this.mPlatformType = PlatformType.ZIM205;
                break;
            default:
                this.mPlatformType = "Unknown";
                break;
        }
        if (this.mPlatformType.equals("Unknown")) {
            DBManager.getInstance().setStrOption("~Platform", "empty");
        } else {
            DBManager.getInstance().setStrOption("~Platform", this.mPlatformType);
        }
        FileUtils.deleteFile(new File("sdcard/config/config.yml"));
        FileUtils.copyAssets(LauncherApplication.getLauncherApplicationContext(), "config/config.yml", "sdcard/config/config.yml");
        try {
            File file = new File(FileUtils.CONFIG_YML_PATH);
            if (file.exists()) {
                String replaceAll = FileUtils.readText(FileUtils.CONFIG_YML_PATH, true).replaceAll("#BIOCVVERSION#", BuildConfig.VERSION_NAME).replaceAll("#BIOCVDEVICENAME#", String.format("FaceDepot_%s_AttAcc", new Object[]{this.mPlatformType}));
                FileUtils.deleteFile(file);
                FileUtils.writeStringData(replaceAll, FileUtils.CONFIG_YML_PATH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class SingletonHolder {
        static final DeviceManager INSTANCE = new DeviceManager();

        SingletonHolder() {
        }
    }

    private DeviceManager() {
        this.sharedUtil = new ZKSharedUtil(LauncherApplication.getLauncherApplicationContext());
    }

    public String getDeviceType() {
        return this.mDeviceType;
    }

    public String getPlatformType() {
        return this.mPlatformType;
    }

    public static DeviceManager getDefault() {
        return SingletonHolder.INSTANCE;
    }

    public CameraConfig getCameraConfig() {
        if (this.mCameraConfig == null) {
            String string = this.sharedUtil.getString(GlobalConfig.SHARED_KEY_CAMERA_CONFIG, "");
            if (TextUtils.isEmpty(string)) {
                this.mCameraConfig = new CameraConfig();
            } else {
                this.mCameraConfig = (CameraConfig) JSON.parseObject(string, CameraConfig.class);
            }
        }
        return this.mCameraConfig;
    }

    public boolean isSupportFaceAntiFake() {
        return this.sharedUtil.getInt(GlobalConfig.SHARED_KEY_SUPPORT_FACE_ANTI_FAKE, 0) == 1;
    }

    public boolean isSupportPalm() {
        return this.sharedUtil.getInt(GlobalConfig.SHARED_KEY_SUPPORT_PALM, 0) == 1;
    }

    public void checkDeviceSerialNumber() {
        String strOption = DBManager.getInstance().getStrOption("~SerialNumber", "");
        Log.i(ZkLogTag.OPTION_QUERY, "~SerialNumber : " + strOption);
        if ("0000000001".equals(strOption)) {
            String deviceSn = this.mStoreManager.getDeviceSn();
            if (TextUtils.isEmpty(deviceSn)) {
                deviceSn = getSystemSerialNumber();
            }
            if (!TextUtils.isEmpty(deviceSn) && !deviceSn.equals(strOption)) {
                changeSerialNumberFromSystemParam(deviceSn);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004b, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0052, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0053, code lost:
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005b, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return "";
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004e A[SYNTHETIC, Splitter:B:19:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005a A[ExcHandler: FileNotFoundException (r0v1 'e' java.io.FileNotFoundException A[CUSTOM_DECLARE]), Splitter:B:5:0x0017] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getSystemSerialNumber() {
        /*
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/system/etc/ZKTeco/serial_number"
            r0.<init>(r1)
            boolean r1 = r0.exists()
            java.lang.String r2 = ""
            if (r1 == 0) goto L_0x005e
            boolean r1 = r0.isDirectory()
            if (r1 == 0) goto L_0x0016
            goto L_0x005e
        L_0x0016:
            r1 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x004b }
            r3.<init>(r0)     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x004b }
            r0 = 256(0x100, float:3.59E-43)
            byte[] r0 = new byte[r0]     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            r1.<init>()     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
        L_0x0025:
            int r4 = r3.read(r0)     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            r5 = -1
            if (r4 == r5) goto L_0x0035
            java.lang.String r4 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            r4.<init>(r0)     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            r1.append(r4)     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            goto L_0x0025
        L_0x0035:
            r3.close()     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            java.lang.String r0 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            java.lang.String r1 = r1.toString()     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            r4 = 0
            byte[] r1 = android.util.Base64.decode(r1, r4)     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            r0.<init>(r1)     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x0048 }
            r2 = r0
            goto L_0x005e
        L_0x0048:
            r0 = move-exception
            r1 = r3
            goto L_0x004c
        L_0x004b:
            r0 = move-exception
        L_0x004c:
            if (r1 == 0) goto L_0x0056
            r1.close()     // Catch:{ IOException -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0056:
            r0.printStackTrace()
            goto L_0x005e
        L_0x005a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x005e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.device.DeviceManager.getSystemSerialNumber():java.lang.String");
    }

    private static void changeSerialNumberFromSystemParam(String str) {
        if (!TextUtils.isEmpty(str)) {
            Log.i(ZkLogTag.OPTION_CHANGE, String.format("set ~SerialNumber:%s ret = %s", new Object[]{str, Integer.valueOf(DBManager.getInstance().setStrOption("~SerialNumber", str))}));
        }
    }

    public boolean checkFirmwareActivation() {
        boolean isHardwareActive = ZkHardwareUtils.isHardwareActive();
        this.sharedUtil.putBoolean(KEY_FIRMWARE_ACTIVATION_STATUS, isHardwareActive);
        return isHardwareActive;
    }

    public String getWorkTime() {
        return this.sharedUtil.getString(KEY_EXECUTE_CLEAR_PHOTO_WORK_TIME, "");
    }

    public void setWorkTime(String str) {
        this.sharedUtil.putString(KEY_EXECUTE_CLEAR_PHOTO_WORK_TIME, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0065 A[SYNTHETIC, Splitter:B:34:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x006d A[Catch:{ IOException -> 0x0069 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0078 A[SYNTHETIC, Splitter:B:43:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0080 A[Catch:{ IOException -> 0x007c }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void copyAssetFile(android.content.Context r2, java.lang.String r3, java.lang.String r4, java.lang.String r5) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x000e
            r0.mkdirs()
        L_0x000e:
            r0 = 0
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            java.io.InputStream r2 = r2.open(r3)     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            r1.<init>()     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            java.lang.StringBuilder r4 = r1.append(r4)     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            java.lang.String r1 = java.io.File.separator     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0057, all -> 0x0053 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ IOException -> 0x0051, all -> 0x004f }
        L_0x0037:
            int r5 = r2.read(r4)     // Catch:{ IOException -> 0x0051, all -> 0x004f }
            r0 = -1
            if (r5 == r0) goto L_0x0043
            r0 = 0
            r3.write(r4, r0, r5)     // Catch:{ IOException -> 0x0051, all -> 0x004f }
            goto L_0x0037
        L_0x0043:
            r3.flush()     // Catch:{ IOException -> 0x0051, all -> 0x004f }
            if (r2 == 0) goto L_0x004b
            r2.close()     // Catch:{ IOException -> 0x0069 }
        L_0x004b:
            r3.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x0074
        L_0x004f:
            r4 = move-exception
            goto L_0x0055
        L_0x0051:
            r4 = move-exception
            goto L_0x0059
        L_0x0053:
            r4 = move-exception
            r3 = r0
        L_0x0055:
            r0 = r2
            goto L_0x0076
        L_0x0057:
            r4 = move-exception
            r3 = r0
        L_0x0059:
            r0 = r2
            goto L_0x0060
        L_0x005b:
            r4 = move-exception
            r3 = r0
            goto L_0x0076
        L_0x005e:
            r4 = move-exception
            r3 = r0
        L_0x0060:
            r4.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x006b
            r0.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x006b
        L_0x0069:
            r2 = move-exception
            goto L_0x0071
        L_0x006b:
            if (r3 == 0) goto L_0x0074
            r3.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x0074
        L_0x0071:
            r2.printStackTrace()
        L_0x0074:
            return
        L_0x0075:
            r4 = move-exception
        L_0x0076:
            if (r0 == 0) goto L_0x007e
            r0.close()     // Catch:{ IOException -> 0x007c }
            goto L_0x007e
        L_0x007c:
            r2 = move-exception
            goto L_0x0084
        L_0x007e:
            if (r3 == 0) goto L_0x0087
            r3.close()     // Catch:{ IOException -> 0x007c }
            goto L_0x0087
        L_0x0084:
            r2.printStackTrace()
        L_0x0087:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.device.DeviceManager.copyAssetFile(android.content.Context, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public boolean isH1() {
        return DeviceType.HORUS_H1.equals(getDeviceType());
    }

    public boolean isG6() {
        return DeviceType.G6.equals(getDeviceType());
    }

    public boolean isG4Pro() {
        return DeviceType.G4_PRO.equals(getDeviceType());
    }

    public boolean isG5() {
        return DeviceType.G5.equals(getDeviceType());
    }

    public boolean setProp(String str, String str2) {
        Log.d(TAG, "setProp: key---" + str + ", value---" + str2);
        return this.mStoreManager.setProp(str, str2);
    }

    public String getProp(String str) {
        String prop = this.mStoreManager.getProp(str);
        Log.d(TAG, "getProp: key----" + str + ", value---" + prop);
        return prop;
    }
}
