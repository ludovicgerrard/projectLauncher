package com.zkteco.android.io;

import android.content.Context;
import android.database.Cursor;
import android.util.Base64;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class CursorToImageHelper {
    private static final String EXTENSION_JPG = ".jpg";
    private static final String TAG = "com.zkteco.android.io.CursorToImageHelper";

    private CursorToImageHelper() {
    }

    public static boolean cursorToImage(Context context, Cursor cursor, String str, String str2, String str3) {
        if (cursor == null) {
            return false;
        }
        int columnIndex = cursor.getColumnIndex(str);
        int columnIndex2 = cursor.getColumnIndex(str2);
        boolean z = false;
        while (cursor.moveToNext()) {
            String string = cursor.getString(columnIndex);
            String string2 = cursor.getString(columnIndex2);
            if (!z) {
                new File(str3).mkdir();
                z = true;
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str3 + File.separator + string + EXTENSION_JPG);
                fileOutputStream.write(Base64.decode(string2, 0));
                fileOutputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Error at cursorToImage function", e);
            }
        }
        return true;
    }
}
