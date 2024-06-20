package com.zkteco.android.db.orm.util;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public final class FragmentHelper {
    private static final String TAG = "com.zkteco.android.db.orm.util.FragmentHelper";

    private FragmentHelper() {
    }

    public static void attachDialogFragment(AppCompatActivity appCompatActivity, Fragment fragment) {
        FragmentTransaction beginTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        beginTransaction.attach(fragment);
        beginTransaction.commitAllowingStateLoss();
    }

    public static void detachDialogFragment(AppCompatActivity appCompatActivity, Fragment fragment) {
        FragmentTransaction beginTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        beginTransaction.detach(fragment);
        beginTransaction.commitAllowingStateLoss();
    }

    public static Fragment getAlreadyAddedFragment(AppCompatActivity appCompatActivity, String str) {
        return appCompatActivity.getSupportFragmentManager().findFragmentByTag(str);
    }

    public static void launchDialogFragment(AppCompatActivity appCompatActivity, Fragment fragment) {
        Fragment findFragmentByTag = appCompatActivity.getSupportFragmentManager().findFragmentByTag(fragment.getClass().getCanonicalName());
        FragmentTransaction beginTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        beginTransaction.add(fragment, fragment.getClass().getCanonicalName());
        if (findFragmentByTag != null) {
            Log.d(TAG, "removing old fragment");
            beginTransaction.remove(findFragmentByTag);
        }
        beginTransaction.commitAllowingStateLoss();
    }
}
