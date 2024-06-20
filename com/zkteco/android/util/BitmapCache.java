package com.zkteco.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

public class BitmapCache {
    private static final int CACHE_SIZE = 4194304;
    private static final int DEFAULT_BUFFER_SIZE = 2;
    private final LruCache<Integer, Bitmap> bitmaps;
    private final int bufferSize;
    private int index;

    public BitmapCache() {
        this(2);
    }

    public BitmapCache(int i) {
        this.index = -1;
        this.bitmaps = new LruCache<Integer, Bitmap>(4194304) {
            /* access modifiers changed from: protected */
            public int sizeOf(Integer num, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
        this.bufferSize = i;
    }

    public Bitmap getBitmap(Image image) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inSampleSize = 1;
        int i = this.index + 1;
        this.index = i;
        int i2 = i % this.bufferSize;
        this.index = i2;
        if (this.bitmaps.get(Integer.valueOf(i2)) != null) {
            options.inBitmap = this.bitmaps.get(Integer.valueOf(this.index));
        }
        this.bitmaps.put(Integer.valueOf(this.index), BitmapFactory.decodeByteArray(image.getData(), 0, image.getData().length, options));
        return this.bitmaps.get(Integer.valueOf(this.index));
    }
}
