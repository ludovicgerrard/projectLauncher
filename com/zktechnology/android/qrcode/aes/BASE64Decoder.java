package com.zktechnology.android.qrcode.aes;

import com.zkteco.android.zkcore.wiegand.WiegandConfig;

public class BASE64Decoder extends CharacterDecoder {
    private static final char[] pem_array = {'A', 'B', WiegandConfig.CARD_FORMAT_C, 'D', WiegandConfig.CARD_FORMAT_E, WiegandConfig.CARD_FORMAT_F, 'G', 'H', 'I', 'J', 'K', 'L', WiegandConfig.CARD_FORMAT_M, 'N', WiegandConfig.CARD_FORMAT_O, 'P', 'Q', 'R', WiegandConfig.CARD_FORMAT_S, 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', WiegandConfig.CARD_FORMAT_c, 'd', WiegandConfig.CARD_FORMAT_e, WiegandConfig.CARD_FORMAT_f, 'g', 'h', 'i', 'j', 'k', 'l', WiegandConfig.CARD_FORMAT_m, 'n', WiegandConfig.CARD_FORMAT_o, 'p', 'q', 'r', WiegandConfig.CARD_FORMAT_s, 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] pem_convert_array = new byte[256];
    byte[] decode_buffer = new byte[4];

    /* access modifiers changed from: protected */
    public int bytesPerAtom() {
        return 4;
    }

    /* access modifiers changed from: protected */
    public int bytesPerLine() {
        return 72;
    }

    static {
        int i = 0;
        for (int i2 = 0; i2 < 255; i2++) {
            pem_convert_array[i2] = -1;
        }
        while (true) {
            char[] cArr = pem_array;
            if (i < cArr.length) {
                pem_convert_array[cArr[i]] = (byte) i;
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decodeAtom(java.io.PushbackInputStream r10, java.io.OutputStream r11, int r12) throws java.io.IOException {
        /*
            r9 = this;
            r0 = 2
            if (r12 < r0) goto L_0x00cd
        L_0x0003:
            int r1 = r10.read()
            r2 = -1
            if (r1 == r2) goto L_0x00c7
            r3 = 10
            if (r1 == r3) goto L_0x0003
            r3 = 13
            if (r1 == r3) goto L_0x0003
            byte[] r3 = r9.decode_buffer
            byte r1 = (byte) r1
            r4 = 0
            r3[r4] = r1
            int r1 = r12 + -1
            r5 = 1
            int r10 = r9.readFully(r10, r3, r5, r1)
            if (r10 == r2) goto L_0x00c1
            r10 = 61
            r1 = 3
            if (r12 <= r1) goto L_0x002d
            byte[] r3 = r9.decode_buffer
            byte r3 = r3[r1]
            if (r3 != r10) goto L_0x002d
            r12 = r1
        L_0x002d:
            if (r12 <= r0) goto L_0x0036
            byte[] r3 = r9.decode_buffer
            byte r3 = r3[r0]
            if (r3 != r10) goto L_0x0036
            r12 = r0
        L_0x0036:
            r10 = 4
            if (r12 == r0) goto L_0x0059
            if (r12 == r1) goto L_0x004b
            if (r12 == r10) goto L_0x0041
            r3 = r2
            r4 = r3
            r5 = r4
            goto L_0x006e
        L_0x0041:
            byte[] r2 = pem_convert_array
            byte[] r3 = r9.decode_buffer
            byte r3 = r3[r1]
            r3 = r3 & 255(0xff, float:3.57E-43)
            byte r2 = r2[r3]
        L_0x004b:
            byte[] r3 = pem_convert_array
            byte[] r6 = r9.decode_buffer
            byte r6 = r6[r0]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte r3 = r3[r6]
            r8 = r3
            r3 = r2
            r2 = r8
            goto L_0x005a
        L_0x0059:
            r3 = r2
        L_0x005a:
            byte[] r6 = pem_convert_array
            byte[] r7 = r9.decode_buffer
            byte r5 = r7[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte r5 = r6[r5]
            byte r4 = r7[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            byte r4 = r6[r4]
            r8 = r3
            r3 = r2
            r2 = r4
            r4 = r8
        L_0x006e:
            if (r12 == r0) goto L_0x00b4
            if (r12 == r1) goto L_0x009a
            if (r12 == r10) goto L_0x0075
            goto L_0x00c0
        L_0x0075:
            int r12 = r2 << 2
            r12 = r12 & 252(0xfc, float:3.53E-43)
            int r0 = r5 >>> 4
            r0 = r0 & r1
            r12 = r12 | r0
            byte r12 = (byte) r12
            r11.write(r12)
            int r10 = r5 << 4
            r10 = r10 & 240(0xf0, float:3.36E-43)
            int r12 = r3 >>> 2
            r12 = r12 & 15
            r10 = r10 | r12
            byte r10 = (byte) r10
            r11.write(r10)
            int r10 = r3 << 6
            r10 = r10 & 192(0xc0, float:2.69E-43)
            r12 = r4 & 63
            r10 = r10 | r12
            byte r10 = (byte) r10
            r11.write(r10)
            goto L_0x00c0
        L_0x009a:
            int r12 = r2 << 2
            r12 = r12 & 252(0xfc, float:3.53E-43)
            int r2 = r5 >>> 4
            r1 = r1 & r2
            r12 = r12 | r1
            byte r12 = (byte) r12
            r11.write(r12)
            int r10 = r5 << 4
            r10 = r10 & 240(0xf0, float:3.36E-43)
            int r12 = r3 >>> 2
            r12 = r12 & 15
            r10 = r10 | r12
            byte r10 = (byte) r10
            r11.write(r10)
            goto L_0x00c0
        L_0x00b4:
            int r12 = r2 << 2
            r12 = r12 & 252(0xfc, float:3.53E-43)
            int r10 = r5 >>> 4
            r10 = r10 & r1
            r10 = r10 | r12
            byte r10 = (byte) r10
            r11.write(r10)
        L_0x00c0:
            return
        L_0x00c1:
            com.zktechnology.android.qrcode.aes.CEStreamExhausted r10 = new com.zktechnology.android.qrcode.aes.CEStreamExhausted
            r10.<init>()
            throw r10
        L_0x00c7:
            com.zktechnology.android.qrcode.aes.CEStreamExhausted r10 = new com.zktechnology.android.qrcode.aes.CEStreamExhausted
            r10.<init>()
            throw r10
        L_0x00cd:
            com.zktechnology.android.qrcode.aes.CEFormatException r10 = new com.zktechnology.android.qrcode.aes.CEFormatException
            java.lang.String r11 = "BASE64Decoder: Not enough bytes for an atom."
            r10.<init>(r11)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.qrcode.aes.BASE64Decoder.decodeAtom(java.io.PushbackInputStream, java.io.OutputStream, int):void");
    }
}
