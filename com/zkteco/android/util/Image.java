package com.zkteco.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

public class Image {
    private final byte[] data;
    private final int height;
    private final int width;

    public Image(byte[] bArr, int i, int i2) {
        this.data = bArr;
        this.width = i;
        this.height = i2;
    }

    public byte[] getData() {
        return this.data;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Image crop(Area area) {
        int i = area.getUpperLeft().x;
        int i2 = area.getUpperLeft().y;
        int i3 = area.getLowerRight().x;
        int i4 = area.getLowerRight().y;
        if (i < 0 || i2 >= this.width || i3 < 0 || i4 >= this.height) {
            throw new IllegalArgumentException("Area outside of image");
        }
        int i5 = i3 - i;
        int i6 = i4 - i2;
        byte[] bArr = this.data;
        Bitmap createBitmap = Bitmap.createBitmap(BitmapFactory.decodeByteArray(bArr, 0, bArr.length), i, i2, i6, i5);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        createBitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
        return new Image(byteArrayOutputStream.toByteArray(), i6, i5);
    }

    public String getBase64() {
        return Base64.encodeToString(this.data, 0);
    }

    public String toString() {
        return "Image{data=" + getBase64().substring(0, 20) + ", width=" + this.width + ", height=" + this.height + '}';
    }
}
