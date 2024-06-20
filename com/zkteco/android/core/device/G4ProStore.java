package com.zkteco.android.core.device;

import com.zkteco.android.core.interfaces.IDeviceStore;

public class G4ProStore implements IDeviceStore {
    public boolean setProp(String str, String str2) {
        return SysPropertyUtil.setKeyValue(str, str2.getBytes());
    }

    public String getProp(String str) {
        byte[] keyValue = SysPropertyUtil.getKeyValue(str);
        if (keyValue != null) {
            return new String(keyValue);
        }
        return null;
    }
}
