package com.zktechnology.android.utils;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toastMessage;

    public static void show(Context context, String str) {
        Toast toast = toastMessage;
        if (toast != null) {
            toast.cancel();
            toastMessage = null;
        }
        Toast makeText = Toast.makeText(context, str, 0);
        toastMessage = makeText;
        ((TextView) ((LinearLayout) makeText.getView()).getChildAt(0)).setTextSize(20.0f);
        toastMessage.show();
    }
}
