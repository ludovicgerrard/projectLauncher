package com.zkteco.android.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public final class ImageUtils {
    private static final String TAG = "com.zkteco.android.io.ImageUtils";

    private ImageUtils() {
    }

    public static Bitmap byteArrayToBitmap(byte[] bArr) {
        return byteArrayToBitmap(bArr, (BitmapFactory.Options) null);
    }

    public static Bitmap byteArrayToBitmap(byte[] bArr, BitmapFactory.Options options) {
        if (options == null) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        }
        return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] fileToByteArray(File file) throws FileNotFoundException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        byte[] bArr = null;
        try {
            long length = randomAccessFile.length();
            int i = (int) length;
            if (((long) i) == length) {
                bArr = new byte[i];
                randomAccessFile.readFully(bArr);
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error while converting file to byte array", e);
                }
                return bArr;
            }
            throw new IOException("File size >= 2 GB");
        } catch (IOException e2) {
            Log.e(TAG, "Error while converting file to byte array", e2);
            randomAccessFile.close();
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (IOException e3) {
                Log.e(TAG, "Error while converting file to byte array", e3);
            }
            throw th;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public static Bitmap decodeSampledBitmapFromFile(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public static void resizeBitmapAndSetToImageView(String str, ImageView imageView) {
        Bitmap decodeSampledBitmapFromFile = decodeSampledBitmapFromFile(str, imageView.getWidth(), imageView.getHeight());
        if (decodeSampledBitmapFromFile != null) {
            decodeSampledBitmapFromFile.compress(Bitmap.CompressFormat.JPEG, 40, new ByteArrayOutputStream());
        }
        imageView.setImageBitmap(decodeSampledBitmapFromFile);
    }

    public static String encodeImageFileToBase64(Uri uri, boolean z) {
        return Base64.encodeToString(FileHelper.imageFileToByteArray(uri), 2);
    }

    public static Bitmap decodeImageFromBase64(String str) {
        return decodeImageFromBase64(str, (BitmapFactory.Options) null);
    }

    public static Bitmap decodeImageFromBase64(String str, BitmapFactory.Options options) {
        Log.d(TAG, "Base-64 string is " + str);
        return byteArrayToBitmap(Base64.decode(str, 2), options);
    }
}
