package com.zkteco.android.core.library;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.zkteco.android.util.serialization.Serializer;
import com.zkteco.android.util.serialization.SerializerFactory;
import java.io.IOException;
import java.util.Arrays;

public final class CoreProvider implements Provider {
    private static final String CONTENT_PROVIDER_URI = "content://com.zkteco.android.core/";
    private static final boolean DEBUG = Log.isLoggable(CoreProvider.class.getSimpleName(), 3);
    private static final String TAG = "CoreProvider";
    private final Context context;
    private final Serializer serializer = SerializerFactory.INSTANCE.getDefaultSerializer();

    public void close() throws IOException {
    }

    public CoreProvider(Context context2) {
        this.context = context2;
    }

    public Object invoke(String str, Object... objArr) {
        String[] strArr;
        if (DEBUG) {
            Log.d(TAG, "Invoking function " + str + "; parameters: " + Arrays.toString(objArr));
        }
        Uri parse = Uri.parse(CONTENT_PROVIDER_URI + str);
        if (objArr != null) {
            int length = objArr.length;
            strArr = new String[length];
            for (int i = 0; i < length; i++) {
                if (objArr[i] == null) {
                    strArr[i] = null;
                } else {
                    strArr[i] = this.serializer.getStringFromInstance(objArr[i]);
                }
            }
        } else {
            strArr = null;
        }
        if (DEBUG) {
            Log.d(TAG, "Invoking function " + str + "; projection: " + Arrays.toString(strArr));
        }
        return CursorWrapper.unwrapFromCursor(this.context.getContentResolver().query(parse, strArr, (String) null, (String[]) null, (String) null));
    }
}
