package com.zkteco.android.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ExternalResources extends Resources {
    private static final String EXTERNAL_DRAWABLE_FORMAT = "/system/config/res/%s/drawable/%s.png";
    public static final String EXTERNAL_RESOURCES_ROOT = "/system/config/res/";
    private static final String TAG = "com.zkteco.android.util.ExternalResources";
    private final String externalDrawablePath;

    public ExternalResources(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.externalDrawablePath = String.format(EXTERNAL_DRAWABLE_FORMAT, new Object[]{context.getPackageName(), "%s"});
    }

    public Drawable getDrawable(int i) throws Resources.NotFoundException {
        String resourceEntryName = getResourceEntryName(i);
        String str = TAG;
        Log.d(str, "getDrawable >> " + resourceEntryName);
        if (resourceEntryName == null) {
            return super.getDrawable(i);
        }
        String format = String.format(this.externalDrawablePath, new Object[]{resourceEntryName});
        Log.d(str, "getDrawable >> " + format);
        Bitmap decodeFile = BitmapFactory.decodeFile(format);
        if (decodeFile == null) {
            return super.getDrawable(i);
        }
        return new BitmapDrawable(decodeFile);
    }
}
