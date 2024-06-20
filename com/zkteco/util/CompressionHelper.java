package com.zkteco.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class CompressionHelper {
    private static final int BUFFER_SIZE = 4096;

    private CompressionHelper() {
    }

    public static byte[] compress(byte[] bArr) {
        Deflater deflater = new Deflater(9, true);
        deflater.setInput(bArr);
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[4096];
        while (!deflater.finished()) {
            byteArrayOutputStream.write(bArr2, 0, deflater.deflate(bArr2));
        }
        deflater.end();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decompress(byte[] bArr) throws DataFormatException {
        Inflater inflater = new Inflater(true);
        inflater.setInput(bArr, 0, bArr.length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[4096];
        while (!inflater.finished()) {
            byteArrayOutputStream.write(bArr2, 0, inflater.inflate(bArr2));
        }
        inflater.end();
        return byteArrayOutputStream.toByteArray();
    }
}
