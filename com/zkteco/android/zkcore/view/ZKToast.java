package com.zkteco.android.zkcore.view;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.zkteco.android.zkcore.R;

public class ZKToast {
    private static long lastTime;
    private static TextView mTvToast;
    private static String oldMsg;
    private static long oneTime;
    private static Toast toast;
    private static long twoTime;

    public static void showToast(Context context, String str) {
        if (Build.VERSION.SDK_INT != 28) {
            if (toast == null) {
                toast = new Toast(context);
                View inflate = LayoutInflater.from(context).inflate(R.layout.layout_zk_toast, (ViewGroup) null);
                TextView textView = (TextView) inflate.findViewById(R.id.core_tv_toast);
                mTvToast = textView;
                textView.setText(str);
                toast.setDuration(0);
                toast.setView(inflate);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (!str.equals(oldMsg)) {
                    oldMsg = str;
                    mTvToast.setText(str);
                    toast.show();
                } else if (twoTime - oneTime > 0) {
                    toast.show();
                }
            }
            oneTime = twoTime;
        } else if (Math.abs(SystemClock.elapsedRealtime() - lastTime) > 2500) {
            Toast.makeText(context, str, 0).show();
            lastTime = SystemClock.elapsedRealtime();
        }
    }

    public static void showToast(Context context, int i) {
        showToast(context, context.getString(i));
    }
}
