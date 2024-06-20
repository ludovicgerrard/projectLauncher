package com.zktechnology.android.verify.dialog.init;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZKEventLauncher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.AppInitializeTask;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.IAppInitializeCallback;
import com.zktechnology.android.utils.ToastUtils;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.io.ShellHelper;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZKInitDialog extends Dialog implements IAppInitializeCallback {
    private static final String KEY_THEME_POSITION = "theme_selected_position";
    private LinearLayout dialogImg;
    /* access modifiers changed from: private */
    public long lastTime;
    Activity mContext;
    protected int mDialogHeight = 320;
    protected int mDialogWidth = 440;
    /* access modifiers changed from: private */
    public TextView mInitializeStateTv;
    private ExecutorService mService = Executors.newSingleThreadExecutor();

    private void setLauncherTheme() {
    }

    public ZKInitDialog(Activity activity) {
        super(activity, R.style.initdialogstyle);
        this.mContext = activity;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.init_dialog);
        ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
        setCancelable(false);
        setPortSize();
        this.dialogImg = (LinearLayout) findViewById(R.id.dialog_lin);
        this.lastTime = SystemClock.elapsedRealtime();
        this.dialogImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - ZKInitDialog.this.lastTime > 600000) {
                    Intent intent = new Intent();
                    intent.setAction("SHOW_NAVIGATION");
                    ZKInitDialog.this.mContext.sendBroadcast(intent);
                    try {
                        ShellHelper.execCommands("pm enable com.android.launcher3");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Intent intent2 = new Intent("android.intent.action.MAIN");
                        intent2.setPackage("com.android.launcher3");
                        ZKInitDialog.this.mContext.startActivity(intent2);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });
        TextView textView = (TextView) findViewById(R.id.initialize_state_tv);
        this.mInitializeStateTv = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ZKInitDialog.this.lambda$onCreate$0$ZKInitDialog(view);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$0$ZKInitDialog(View view) {
        CharSequence hint = this.mInitializeStateTv.getHint();
        if (hint != null) {
            ToastUtils.show(this.mContext, hint.toString());
        }
    }

    private void setPortSize() {
        Window window = getWindow();
        int dp2px = AppUtils.dp2px(this.mContext.getApplicationContext(), (float) this.mDialogWidth);
        int dp2px2 = AppUtils.dp2px(this.mContext.getApplicationContext(), (float) this.mDialogHeight);
        window.setLayout(Math.min(dp2px, ScreenUtil.getScreenWidth(this.mContext.getApplicationContext())), Math.min(dp2px2, ScreenUtil.getScreenHeight(this.mContext.getApplicationContext())));
        window.setAttributes(window.getAttributes());
        window.setGravity(17);
    }

    public void show() {
        super.show();
        this.mService.submit(new AppInitializeTask(LauncherApplication.getLauncherApplicationContext(), this));
    }

    public void dismiss() {
        super.dismiss();
        if (!this.mContext.isFinishing() && !this.mContext.isDestroyed()) {
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            ZKEventLauncher.sendInitTask(2);
        }
    }

    public void initDbOver(boolean z) {
        ZKEventLauncher.sendInitTask(0);
    }

    public void initDeviceOver() {
        ZKEventLauncher.sendInitTask(3);
    }

    public void initStateChange(final int i) {
        Activity activity = this.mContext;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (ZKInitDialog.this.mInitializeStateTv != null) {
                        ZKInitDialog.this.mInitializeStateTv.setText(i);
                    }
                }
            });
        }
    }

    public void initStateChange(final String str) {
        Activity activity = this.mContext;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (ZKInitDialog.this.mInitializeStateTv != null) {
                        ZKInitDialog.this.mInitializeStateTv.setText(str);
                    }
                }
            });
        }
    }

    public void replaceBiotemplateRemainingNum(int i) {
        final String str = AppUtils.getString(R.string.init_replace_biotemplate_in_db) + "\n" + AppUtils.getString(R.string.replace_biotemplate_remaining_num) + i;
        Activity activity = this.mContext;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (ZKInitDialog.this.mInitializeStateTv != null) {
                        ZKInitDialog.this.mInitializeStateTv.setText(str);
                    }
                }
            });
        }
    }

    public void initExceptionStateChange(final String str) {
        TextView textView;
        if (Looper.getMainLooper() != Looper.myLooper()) {
            Activity activity = this.mContext;
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (ZKInitDialog.this.mInitializeStateTv != null) {
                            ZKInitDialog.this.mInitializeStateTv.setHint(str);
                            ZKInitDialog.this.mInitializeStateTv.setText(R.string.init_error);
                        }
                    }
                });
            }
        } else if (this.mContext != null && (textView = this.mInitializeStateTv) != null) {
            textView.setHint(str);
            this.mInitializeStateTv.setText(R.string.init_error);
        }
    }

    public void allInitOver() {
        FileLogUtils.writeLauncherInitRecord("allInitOver  ---------------------------------------------");
        setLauncherTheme();
        ZKEventLauncher.sendInitTask(1);
    }

    public void release() {
        this.mService.shutdown();
        this.mService.shutdownNow();
        this.mService = null;
        this.mContext = null;
    }
}
