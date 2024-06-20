package com.zkteco.android.db.orm.util;

import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64Encoder extends FilterOutputStream {
    private static final char[] chars = {'A', 'B', WiegandConfig.CARD_FORMAT_C, 'D', WiegandConfig.CARD_FORMAT_E, WiegandConfig.CARD_FORMAT_F, 'G', 'H', 'I', 'J', 'K', 'L', WiegandConfig.CARD_FORMAT_M, 'N', WiegandConfig.CARD_FORMAT_O, 'P', 'Q', 'R', WiegandConfig.CARD_FORMAT_S, 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', WiegandConfig.CARD_FORMAT_c, 'd', WiegandConfig.CARD_FORMAT_e, WiegandConfig.CARD_FORMAT_f, 'g', 'h', 'i', 'j', 'k', 'l', WiegandConfig.CARD_FORMAT_m, 'n', WiegandConfig.CARD_FORMAT_o, 'p', 'q', 'r', WiegandConfig.CARD_FORMAT_s, 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private int carryOver;
    private int charCount;
    private boolean isWrapBreak;

    private Base64Encoder(OutputStream outputStream) {
        super(outputStream);
        this.isWrapBreak = true;
    }

    private Base64Encoder(OutputStream outputStream, boolean z) {
        this(outputStream);
        this.isWrapBreak = z;
    }

    public void write(int i) throws IOException {
        if (i < 0) {
            i += 256;
        }
        int i2 = this.charCount;
        if (i2 % 3 == 0) {
            this.carryOver = i & 3;
            this.out.write(chars[i >> 2]);
        } else if (i2 % 3 == 1) {
            this.carryOver = i & 15;
            this.out.write(chars[((this.carryOver << 4) + (i >> 4)) & 63]);
        } else if (i2 % 3 == 2) {
            OutputStream outputStream = this.out;
            char[] cArr = chars;
            outputStream.write(cArr[((this.carryOver << 2) + (i >> 6)) & 63]);
            this.out.write(cArr[i & 63]);
            this.carryOver = 0;
        }
        int i3 = this.charCount + 1;
        this.charCount = i3;
        if (this.isWrapBreak && i3 % 57 == 0) {
            this.out.write(10);
        }
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        for (int i3 = 0; i3 < i2; i3++) {
            write(bArr[i + i3]);
        }
    }

    public void close() throws IOException {
        int i = this.charCount;
        if (i % 3 == 1) {
            this.out.write(chars[(this.carryOver << 4) & 63]);
            this.out.write(61);
            this.out.write(61);
        } else if (i % 3 == 2) {
            this.out.write(chars[(this.carryOver << 2) & 63]);
            this.out.write(61);
        }
        super.close();
    }

    public static String encode(byte[] bArr) {
        return encode(bArr, true);
    }

    public static String encode(byte[] bArr, boolean z) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) (((double) bArr.length) * 1.4d));
        Base64Encoder base64Encoder = new Base64Encoder(byteArrayOutputStream, z);
        try {
            base64Encoder.write(bArr);
            try {
                base64Encoder.close();
                return byteArrayOutputStream.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        } catch (Throwable th) {
            try {
                base64Encoder.close();
                throw th;
            } catch (IOException e3) {
                throw new RuntimeException(e3);
            }
        }
    }
}
