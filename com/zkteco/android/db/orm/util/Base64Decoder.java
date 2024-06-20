package com.zkteco.android.db.orm.util;

import android.text.TextUtils;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64Decoder extends FilterInputStream {
    private static final char[] chars = {'A', 'B', WiegandConfig.CARD_FORMAT_C, 'D', WiegandConfig.CARD_FORMAT_E, WiegandConfig.CARD_FORMAT_F, 'G', 'H', 'I', 'J', 'K', 'L', WiegandConfig.CARD_FORMAT_M, 'N', WiegandConfig.CARD_FORMAT_O, 'P', 'Q', 'R', WiegandConfig.CARD_FORMAT_S, 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', WiegandConfig.CARD_FORMAT_c, 'd', WiegandConfig.CARD_FORMAT_e, WiegandConfig.CARD_FORMAT_f, 'g', 'h', 'i', 'j', 'k', 'l', WiegandConfig.CARD_FORMAT_m, 'n', WiegandConfig.CARD_FORMAT_o, 'p', 'q', 'r', WiegandConfig.CARD_FORMAT_s, 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final int[] ints = new int[128];
    private int carryOver;
    private int charCount;

    static {
        for (int i = 0; i < 64; i++) {
            ints[chars[i]] = i;
        }
    }

    private Base64Decoder(InputStream inputStream) {
        super(inputStream);
    }

    public int read() throws IOException {
        int read;
        do {
            read = this.in.read();
            if (read == -1) {
                return -1;
            }
        } while (Character.isWhitespace((char) read));
        int i = this.charCount + 1;
        this.charCount = i;
        if (read == 61) {
            return -1;
        }
        int i2 = ints[read];
        int i3 = (i - 1) % 4;
        if (i3 == 0) {
            this.carryOver = i2 & 63;
            return read();
        } else if (i3 == 1) {
            int i4 = ((this.carryOver << 2) + (i2 >> 4)) & 255;
            this.carryOver = i2 & 15;
            return i4;
        } else if (i3 == 2) {
            int i5 = ((this.carryOver << 4) + (i2 >> 2)) & 255;
            this.carryOver = i2 & 3;
            return i5;
        } else if (i3 == 3) {
            return ((this.carryOver << 6) + i2) & 255;
        } else {
            return -1;
        }
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (bArr.length >= (i2 + i) - 1) {
            int i3 = 0;
            while (i3 < i2) {
                int read = read();
                if (read == -1 && i3 == 0) {
                    return -1;
                }
                if (read == -1) {
                    break;
                }
                bArr[i + i3] = (byte) read;
                i3++;
            }
            return i3;
        }
        throw new IOException("The input buffer is too small: " + i2 + " bytes requested starting at offset " + i + " while the buffer  is only " + bArr.length + " bytes long.");
    }

    public static String decode(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return new String(decodeToBytes(str));
    }

    public static byte[] decodeToBytes(String str) {
        byte[] bytes = str.getBytes();
        Base64Decoder base64Decoder = new Base64Decoder(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) (((double) bytes.length) * 0.75d));
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int read = base64Decoder.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        base64Decoder.close();
                        try {
                            byteArrayOutputStream.close();
                            return byteArray;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (IOException e2) {
                        throw new RuntimeException(e2);
                    }
                }
            }
        } catch (IOException e3) {
            throw new RuntimeException(e3);
        } catch (Throwable th) {
            try {
                base64Decoder.close();
                try {
                    byteArrayOutputStream.close();
                    throw th;
                } catch (IOException e4) {
                    throw new RuntimeException(e4);
                }
            } catch (IOException e5) {
                throw new RuntimeException(e5);
            }
        }
    }
}
