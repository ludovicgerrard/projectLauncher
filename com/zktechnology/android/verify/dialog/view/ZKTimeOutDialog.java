package com.zktechnology.android.verify.dialog.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.strategy.MainThreadExecutor;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.dialog.view.util.TimeOutRunnable;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.manager.DataManager;

public class ZKTimeOutDialog extends DialogFragment {
    private static final int DIALOG_HEIGHT_DP = 200;
    private static final int DIALOG_WIDTH_DP = 440;
    private static final String TAG = "com.zktechnology.android.verify.dialog.view.ZKTimeOutDialog";
    protected Context mContext;
    private Dialog mDialog;
    private int mDialogHeight = 200;
    private int mDialogWidth = DIALOG_WIDTH_DP;
    private int mGravity = 17;
    private Window mWindow;
    private long shownTimeoutMs = 3;
    protected TimeOutRunnable timeoutRunnable;
    private int x = 0;
    private int y = 0;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setStyle(2, R.style.MyDialogStyle);
        setCancelable(false);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mContext = view.getContext().getApplicationContext();
    }

    private void setPortSize() {
        this.mDialog = getDialog();
        Window window = getDialog().getWindow();
        this.mWindow = window;
        if (window != null) {
            int dp2px = AppUtils.dp2px(getActivity().getApplicationContext(), (float) this.mDialogWidth);
            int dp2px2 = AppUtils.dp2px(getActivity().getApplicationContext(), (float) this.mDialogHeight);
            int screenWidth = ScreenUtil.getScreenWidth(getActivity().getApplicationContext());
            int screenHeight = ScreenUtil.getScreenHeight(getActivity().getApplicationContext());
            this.mWindow.setLayout(Math.min(dp2px, screenWidth), Math.min(dp2px2, screenHeight));
            WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
            attributes.x = this.x;
            attributes.y = this.y;
            this.mWindow.setAttributes(attributes);
            this.mWindow.setGravity(this.mGravity);
        }
    }

    private void setLandSize() {
        this.mDialog = getDialog();
        Window window = getDialog().getWindow();
        this.mWindow = window;
        if (window != null) {
            this.mWindow.setLayout(AppUtils.dp2px(getActivity().getApplicationContext(), 518.0f), AppUtils.dp2px(getActivity().getApplicationContext(), 320.0f));
            WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
            attributes.x = this.x;
            attributes.y = this.y;
            this.mWindow.setAttributes(attributes);
            this.mWindow.setGravity(this.mGravity);
        }
    }

    public void setShowLocation(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public void setGravity(int i) {
        this.mGravity = i;
    }

    public void setDialogSize(int i, int i2) {
        this.mDialogWidth = i;
        this.mDialogHeight = i2;
    }

    public void setCancelable(boolean z) {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setCancelable(z);
        }
    }

    public void onResume() {
        super.onResume();
        if (this.mContext.getResources().getConfiguration().orientation == 1) {
            setPortSize();
        } else {
            setLandSize();
        }
    }

    public void onStop() {
        super.onStop();
        if (this.timeoutRunnable != null) {
            MainThreadExecutor.getInstance().remove(this.timeoutRunnable);
        }
    }

    public void show(FragmentManager fragmentManager, String str) {
        super.show(fragmentManager, str);
        Log.d(TAG, "Show dialog.");
    }

    public void setVerifiedInfoTimeOutMs() {
        try {
            ZKLauncher.sVerifyDialogTimeOut = DBManager.getInstance().getIntOption("VerifiedInfoDelay", 5);
            this.shownTimeoutMs = (long) (ZKLauncher.sVerifyDialogTimeOut * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVerifiedInfoTimeOutMsWorkCode() {
        try {
            DataManager instance = DBManager.getInstance();
            if ("1".equals(instance.getStrOption(ZKDBConfig.OPT_MUSTCHOICEWORKCODE, "0"))) {
                this.shownTimeoutMs = (long) (instance.getIntOption(ZKDBConfig.OPT_WORKCODEINPUTTIMEOUT, 5) * 1000);
            } else {
                setVerifiedInfoTimeOutMs();
            }
            Log.d(TAG, "Verified Info TimeOut is " + this.shownTimeoutMs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVerifiedInfoTimeOutPersonalMsg() {
        try {
            this.shownTimeoutMs = (long) (DBManager.getInstance().getIntOption("PersonalSMSDispTimeOut", 60) * 1000);
            Log.d(TAG, "Verified Info TimeOut PersonalMsg is " + this.shownTimeoutMs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMyTimeout(int i) {
        this.shownTimeoutMs = (long) (i * 1000);
    }

    /* access modifiers changed from: protected */
    public void resetInactiveTimeout() {
        Log.d(TAG, "Dialog timeOut");
        if (this.shownTimeoutMs > 0 && this.timeoutRunnable != null) {
            MainThreadExecutor.getInstance().remove(this.timeoutRunnable);
            MainThreadExecutor.getInstance().executeDelayed(this.timeoutRunnable, this.shownTimeoutMs);
        }
    }
}
