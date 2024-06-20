package com.zkteco.android.core.device;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ZktecoPropertyMessage {
    public static final int CMD_GET_PROP = 1;
    public static final int CMD_SET_PROP = 0;
    private int cmd = 0;
    private byte[] prop_key = new byte[100];
    private byte[] prop_value = new byte[10240];

    public byte[] getZktecoMessage() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = this.cmd;
        byteArrayOutputStream.write(new byte[]{(byte) (i & 255), (byte) ((65280 & i) >> 8), (byte) ((16711680 & i) >> 16), (byte) ((i & -16777216) >> 24)});
        byteArrayOutputStream.write(this.prop_key);
        byteArrayOutputStream.write(this.prop_value);
        return byteArrayOutputStream.toByteArray();
    }

    public void setCmd(int i) {
        this.cmd = i;
    }

    public int getCmd() {
        return this.cmd;
    }

    public int setPropertyKey(byte[] bArr) {
        if (bArr == null || bArr.length > 20) {
            return -1;
        }
        System.arraycopy(bArr, 0, this.prop_key, 0, bArr.length);
        return bArr.length;
    }

    public byte[] getPropertyKey() {
        return this.prop_key;
    }

    public int setPropertyValue(byte[] bArr) {
        if (bArr == null || bArr.length > 20) {
            return -1;
        }
        System.arraycopy(bArr, 0, this.prop_value, 0, bArr.length);
        return bArr.length;
    }

    public byte[] getPropertyValue() {
        return this.prop_value;
    }
}
