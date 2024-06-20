package com.zktechnology.android.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class WallPaperHelper {
    public static String getRealPath(Uri uri, Context context) {
        if (uri == null || uri.getScheme() == null) {
            return null;
        }
        if (uri.getScheme().compareTo("content") == 0) {
            Cursor query = context.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
            if (query == null || !query.moveToFirst()) {
                return null;
            }
            try {
                String string = query.getString(query.getColumnIndexOrThrow("_data"));
                if (!query.isClosed()) {
                    query.close();
                }
                return string;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                if (query.isClosed()) {
                    return null;
                }
                query.close();
                return null;
            } catch (Throwable th) {
                if (!query.isClosed()) {
                    query.close();
                }
                throw th;
            }
        } else if (uri.getScheme().compareTo("file") == 0) {
            return uri.getPath();
        } else {
            return null;
        }
    }

    public static boolean copyFile(String str, String str2) {
        try {
            File file = new File(str);
            if (!file.exists() || !file.isFile() || !file.canRead()) {
                return false;
            }
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (-1 != read) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
