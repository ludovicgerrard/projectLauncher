package com.zktechnology.android.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveImage {
    public static final String path;
    public static final String thermalimagePath;
    public static final String visibleimagePath;

    static {
        String str = Environment.getExternalStorageDirectory().toString() + "/ZKTeco/data/vtimage";
        path = str;
        visibleimagePath = str + "/visibleimage.bmp";
        thermalimagePath = str + "/thermalimage.bmp";
    }

    public SaveImage() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void saveBmp(Bitmap bitmap, String str) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int i = width * 3;
            int i2 = ((width % 4) + i) * height;
            try {
                File file = new File(str);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                writeWord(fileOutputStream, 19778);
                writeDword(fileOutputStream, (long) (i2 + 54));
                writeWord(fileOutputStream, 0);
                writeWord(fileOutputStream, 0);
                writeDword(fileOutputStream, 54);
                writeDword(fileOutputStream, 40);
                writeLong(fileOutputStream, (long) width);
                writeLong(fileOutputStream, (long) height);
                writeWord(fileOutputStream, 1);
                writeWord(fileOutputStream, 24);
                writeDword(fileOutputStream, 0);
                writeDword(fileOutputStream, 0);
                writeLong(fileOutputStream, 0);
                writeLong(fileOutputStream, 0);
                writeDword(fileOutputStream, 0);
                writeDword(fileOutputStream, 0);
                byte[] bArr = new byte[i2];
                int i3 = i + (width % 4);
                int i4 = height - 1;
                int i5 = 0;
                while (i5 < height) {
                    int i6 = 0;
                    int i7 = 0;
                    while (i6 < width) {
                        int pixel = bitmap.getPixel(i6, i5);
                        int i8 = (i4 * i3) + i7;
                        bArr[i8] = (byte) Color.blue(pixel);
                        bArr[i8 + 1] = (byte) Color.green(pixel);
                        bArr[i8 + 2] = (byte) Color.red(pixel);
                        i6++;
                        i7 += 3;
                    }
                    i5++;
                    i4--;
                }
                fileOutputStream.write(bArr);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeWord(FileOutputStream fileOutputStream, int i) throws IOException {
        fileOutputStream.write(new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255)});
    }

    /* access modifiers changed from: protected */
    public void writeDword(FileOutputStream fileOutputStream, long j) throws IOException {
        fileOutputStream.write(new byte[]{(byte) ((int) (j & 255)), (byte) ((int) ((j >> 8) & 255)), (byte) ((int) ((j >> 16) & 255)), (byte) ((int) ((j >> 24) & 255))});
    }

    /* access modifiers changed from: protected */
    public void writeLong(FileOutputStream fileOutputStream, long j) throws IOException {
        fileOutputStream.write(new byte[]{(byte) ((int) (j & 255)), (byte) ((int) ((j >> 8) & 255)), (byte) ((int) ((j >> 16) & 255)), (byte) ((int) ((j >> 24) & 255))});
    }
}
