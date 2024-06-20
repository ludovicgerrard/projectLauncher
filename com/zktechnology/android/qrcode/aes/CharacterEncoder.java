package com.zktechnology.android.qrcode.aes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

public abstract class CharacterEncoder {
    protected PrintStream pStream;

    /* access modifiers changed from: protected */
    public abstract int bytesPerAtom();

    /* access modifiers changed from: protected */
    public abstract int bytesPerLine();

    /* access modifiers changed from: protected */
    public abstract void encodeAtom(OutputStream outputStream, byte[] bArr, int i, int i2) throws IOException;

    /* access modifiers changed from: protected */
    public void encodeBufferSuffix(OutputStream outputStream) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void encodeLinePrefix(OutputStream outputStream, int i) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void encodeBufferPrefix(OutputStream outputStream) throws IOException {
        this.pStream = new PrintStream(outputStream);
    }

    /* access modifiers changed from: protected */
    public void encodeLineSuffix(OutputStream outputStream) throws IOException {
        this.pStream.println();
    }

    /* access modifiers changed from: protected */
    public int readFully(InputStream inputStream, byte[] bArr) throws IOException {
        for (int i = 0; i < bArr.length; i++) {
            int read = inputStream.read();
            if (read == -1) {
                return i;
            }
            bArr[i] = (byte) read;
        }
        return bArr.length;
    }

    public void encode(InputStream inputStream, OutputStream outputStream) throws IOException {
        int bytesPerLine = bytesPerLine();
        if (bytesPerLine >= 0) {
            byte[] bArr = new byte[bytesPerLine];
            encodeBufferPrefix(outputStream);
            while (true) {
                int readFully = readFully(inputStream, bArr);
                if (readFully == 0) {
                    break;
                }
                encodeLinePrefix(outputStream, readFully);
                int i = 0;
                while (i < readFully) {
                    if (bytesPerAtom() + i <= readFully) {
                        encodeAtom(outputStream, bArr, i, bytesPerAtom());
                    } else {
                        encodeAtom(outputStream, bArr, i, readFully - i);
                    }
                    i += bytesPerAtom();
                }
                if (readFully < bytesPerLine()) {
                    break;
                }
                encodeLineSuffix(outputStream);
            }
            encodeBufferSuffix(outputStream);
        }
    }

    public void encode(byte[] bArr, OutputStream outputStream) throws IOException {
        encode((InputStream) new ByteArrayInputStream(bArr), outputStream);
    }

    public String encode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encode((InputStream) new ByteArrayInputStream(bArr), (OutputStream) byteArrayOutputStream);
            return byteArrayOutputStream.toString("8859_1");
        } catch (Exception unused) {
            throw new Error("CharacterEncoder.encode internal error");
        }
    }

    private byte[] getBytes(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return new byte[0];
        }
        byte[] bArr = null;
        if (byteBuffer.hasArray()) {
            byte[] array = byteBuffer.array();
            if (array.length == byteBuffer.capacity() && array.length == byteBuffer.remaining()) {
                byteBuffer.position(byteBuffer.limit());
                bArr = array;
            }
        }
        if (bArr != null) {
            return bArr;
        }
        byte[] bArr2 = new byte[byteBuffer.remaining()];
        byteBuffer.get(bArr2);
        return bArr2;
    }

    public void encode(ByteBuffer byteBuffer, OutputStream outputStream) throws IOException {
        encode(getBytes(byteBuffer), outputStream);
    }

    public String encode(ByteBuffer byteBuffer) {
        return encode(getBytes(byteBuffer));
    }

    public void encodeBuffer(InputStream inputStream, OutputStream outputStream) throws IOException {
        int readFully;
        int bytesPerLine = bytesPerLine();
        if (bytesPerLine >= 0) {
            byte[] bArr = new byte[bytesPerLine];
            encodeBufferPrefix(outputStream);
            do {
                readFully = readFully(inputStream, bArr);
                if (readFully == 0) {
                    break;
                }
                encodeLinePrefix(outputStream, readFully);
                int i = 0;
                while (i < readFully) {
                    if (bytesPerAtom() + i <= readFully) {
                        encodeAtom(outputStream, bArr, i, bytesPerAtom());
                    } else {
                        encodeAtom(outputStream, bArr, i, readFully - i);
                    }
                    i += bytesPerAtom();
                }
                encodeLineSuffix(outputStream);
            } while (readFully >= bytesPerLine());
            encodeBufferSuffix(outputStream);
        }
    }

    public void encodeBuffer(byte[] bArr, OutputStream outputStream) throws IOException {
        encodeBuffer((InputStream) new ByteArrayInputStream(bArr), outputStream);
    }

    public String encodeBuffer(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encodeBuffer((InputStream) new ByteArrayInputStream(bArr), (OutputStream) byteArrayOutputStream);
            return byteArrayOutputStream.toString();
        } catch (Exception unused) {
            throw new Error("CharacterEncoder.encodeBuffer internal error");
        }
    }

    public void encodeBuffer(ByteBuffer byteBuffer, OutputStream outputStream) throws IOException {
        encodeBuffer(getBytes(byteBuffer), outputStream);
    }

    public String encodeBuffer(ByteBuffer byteBuffer) {
        return encodeBuffer(getBytes(byteBuffer));
    }
}
