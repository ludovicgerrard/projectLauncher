package com.zkteco.android.core.device;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.zktechnology.android.utils.GlobalConfig;
import com.zkteco.android.core.interfaces.IDeviceStore;
import com.zkteco.android.db.DBConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ZkStoreManager {
    private final IDeviceStore propertyStore;

    public ZkStoreManager(String str) {
        this.propertyStore = initStore(str);
    }

    private IDeviceStore initStore(String str) {
        if (str.startsWith(DeviceType.G4_PRO) || str.startsWith(DeviceType.G5)) {
            return new G4ProStore();
        }
        if (str.startsWith(DeviceType.G6)) {
            return new G6Store();
        }
        if (str.startsWith(DeviceType.HORUS_H1)) {
            return new H1Store();
        }
        return new G4ProStore();
    }

    public boolean setProp(String str, String str2) {
        return this.propertyStore.setProp(str, str2);
    }

    public String getProp(String str) {
        return this.propertyStore.getProp(str);
    }

    public String getDeviceSn() {
        String deviceSN = this.propertyStore instanceof G4ProStore ? getDeviceSN() : null;
        if (TextUtils.isEmpty(deviceSN)) {
            deviceSN = getProp(DBConfig.SYSTEM_SERIAL_NUMBER);
        }
        if (TextUtils.isEmpty(deviceSN)) {
            deviceSN = getProp("~SerialNumber");
        }
        if ((this.propertyStore instanceof G6Store) && TextUtils.isEmpty(deviceSN)) {
            deviceSN = getProp("serial.no");
        }
        return TextUtils.isEmpty(deviceSN) ? getProp(GlobalConfig.SERIAL_NUMBER_COMMAND) : deviceSN;
    }

    private String getDeviceSN() {
        String prop = getProp(DBConfig.SYSTEM_SERIAL_NUMBER);
        if (prop == null) {
            return getSnFormFile();
        }
        if ("1234567890123".equals(prop)) {
            return getSnFormFile();
        }
        return prop;
    }

    private static String getSnFormFile() {
        File file = new File("system/etc/ZKTeco/serial_number");
        if (!file.exists() || file.isDirectory()) {
            Log.e("USB_DevicesInfo", "system/etc/ZKTeco/serial_number file not exists");
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[256];
            StringBuilder sb = new StringBuilder();
            while (fileInputStream.read(bArr) != -1) {
                sb.append(new String(bArr));
            }
            fileInputStream.close();
            return new String(Base64.decode(sb.toString(), 0));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
