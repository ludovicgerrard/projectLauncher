package com.zkteco.android.employeemgmt.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.zkcore.base.ZKBaseFragment;
import com.zkteco.android.zkcore.view.ZKToast;

public abstract class ZKStaffBaseFragment extends ZKBaseFragment {
    private long lastTime;
    private FragmentManager mFragmentManager;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFragmentManager = getActivity().getSupportFragmentManager();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void initFragment(int i, Fragment fragment) {
        FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
        beginTransaction.replace(i, fragment);
        beginTransaction.addToBackStack(fragment.getClass().getName());
        beginTransaction.commit();
    }

    public void pushFragment(int i, Fragment fragment) {
        FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
        beginTransaction.setCustomAnimations(R.animator.rotate_left_in, R.animator.rotate_left_out, R.animator.rotate_right_in, R.animator.rotate_right_out);
        beginTransaction.replace(i, fragment);
        beginTransaction.addToBackStack(fragment.getClass().getName());
        beginTransaction.commit();
    }

    public void pushFragmentNoAnim(int i, Fragment fragment) {
        FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
        beginTransaction.replace(i, fragment);
        beginTransaction.addToBackStack(fragment.getClass().getName());
        beginTransaction.commit();
    }

    public void pushFragment(int i, Fragment fragment, Bundle bundle) {
        FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        beginTransaction.setCustomAnimations(R.animator.rotate_left_in, R.animator.rotate_left_out, R.animator.rotate_right_in, R.animator.rotate_right_out);
        beginTransaction.replace(i, fragment);
        beginTransaction.addToBackStack(fragment.getClass().getName());
        beginTransaction.commit();
    }

    public void pushFragmentNoAnim(int i, Fragment fragment, Bundle bundle) {
        FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        beginTransaction.replace(i, fragment);
        beginTransaction.addToBackStack(fragment.getClass().getName());
        beginTransaction.commit();
    }

    public void finishFragment() {
        this.mFragmentManager.popBackStack();
    }

    public Bundle finishFragment(Bundle bundle) {
        this.mFragmentManager.popBackStack();
        return bundle;
    }

    public boolean canTouch() {
        if (Math.abs(SystemClock.elapsedRealtime() - this.lastTime) <= 500) {
            return false;
        }
        this.lastTime = SystemClock.elapsedRealtime();
        return true;
    }

    /* access modifiers changed from: protected */
    public void showToast(Context context, int i) {
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable(context, i) {
                public final /* synthetic */ Context f$0;
                public final /* synthetic */ int f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKToast.showToast(this.f$0, this.f$1);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void showToast(Context context, String str) {
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable(context, str) {
                public final /* synthetic */ Context f$0;
                public final /* synthetic */ String f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void run() {
                    ZKToast.showToast(this.f$0, this.f$1);
                }
            });
        }
    }
}
