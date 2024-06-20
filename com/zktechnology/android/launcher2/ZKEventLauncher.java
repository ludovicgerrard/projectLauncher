package com.zktechnology.android.launcher2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.zktechnology.android.event.EventBusHelper;
import com.zktechnology.android.event.EventChangeTheme;
import com.zktechnology.android.event.EventDismissInitDialog;
import com.zktechnology.android.event.EventInitTask;
import com.zktechnology.android.event.EventProcessDialog;
import com.zktechnology.android.event.EventProcessDismissDialog;
import com.zktechnology.android.event.EventSetProcessDialogTimeOut;
import com.zktechnology.android.launcher2.Launcher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog;
import com.zkteco.android.db.orm.util.FragmentHelper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class ZKEventLauncher extends ZKRS232Launcher {
    private static final String TAG = "ZKEventLauncher";
    public static boolean isDBOk = false;
    public static boolean show;
    private FragmentManager fragmentManager;

    /* access modifiers changed from: protected */
    public abstract void initDataSuccess();

    /* access modifiers changed from: protected */
    public abstract void initHub();

    /* access modifiers changed from: protected */
    public abstract void onCoreServiceInitBroadcast();

    /* access modifiers changed from: protected */
    public abstract void onZKInitializeComplete();

    private static void setShowStatic(boolean z) {
        show = z;
    }

    private static void setIsDBOkStatic(boolean z) {
        isDBOk = z;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!EventBusHelper.isRegister(this)) {
            EventBusHelper.register(this);
        }
        this.fragmentManager = getSupportFragmentManager();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        ZKVerProcessDialog zKVerProcessDialog = (ZKVerProcessDialog) FragmentHelper.getAlreadyAddedFragment(this, ZKVerProcessDialog.TAG);
        if (zKVerProcessDialog != null) {
            zKVerProcessDialog.clearDatas();
            FileLogUtils.writeTouchLog("clearDatas: ZKEventLauncher onPause");
            zKVerProcessDialog.dismiss();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventBusHelper.unregister(this);
    }

    public static void setProcessDialogVisibility(boolean z) {
        EventBusHelper.post(new EventProcessDialog(z));
    }

    public static void setProcessDialogTimeOut(int i) {
        EventBusHelper.post(new EventSetProcessDialogTimeOut(i));
    }

    public static void sendInitTask(int i) {
        LogUtils.d(TAG, "sendInitTask：" + i);
        FileLogUtils.writeLauncherInitRecord("发送EventBus    EventInitTask " + i);
        EventBusHelper.post(new EventInitTask(i));
    }

    public static void sendVerProcessDismissed() {
        EventBusHelper.post(new EventProcessDismissDialog());
    }

    public static void startChangeTheme(boolean z) {
        EventBusHelper.post(new EventChangeTheme(z));
    }

    public static void dismissInitDialog(boolean z) {
        EventBusHelper.post(new EventDismissInitDialog(z));
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void showProcessDialog(EventProcessDialog eventProcessDialog) {
        setShowStatic(eventProcessDialog.isShow());
        boolean z = show;
        if (z) {
            super.showProcessDialog(z);
        }
        ZKVerProcessDialog zKVerProcessDialog = (ZKVerProcessDialog) FragmentHelper.getAlreadyAddedFragment(this, ZKVerProcessDialog.TAG);
        if (!show || this.mState == Launcher.State.WORKSPACE || zKVerProcessDialog == null) {
            FragmentManager fragmentManager2 = this.fragmentManager;
            if (fragmentManager2 != null) {
                FragmentTransaction beginTransaction = fragmentManager2.beginTransaction();
                if (zKVerProcessDialog == null) {
                    ZKVerProcessDialog instance = ZKVerProcessDialog.getInstance();
                    if (show) {
                        if (!instance.isAdded()) {
                            beginTransaction.add((Fragment) instance, ZKVerProcessDialog.TAG);
                            beginTransaction.commitAllowingStateLoss();
                            this.fragmentManager.executePendingTransactions();
                        }
                        instance.updateView();
                        return;
                    }
                    try {
                        if (instance.isAdded()) {
                            instance.clearDatas();
                            beginTransaction.detach(instance);
                            beginTransaction.commitAllowingStateLoss();
                            this.fragmentManager.executePendingTransactions();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (show) {
                    if (!zKVerProcessDialog.isAdded()) {
                        beginTransaction.attach(zKVerProcessDialog);
                        beginTransaction.commitAllowingStateLoss();
                        this.fragmentManager.executePendingTransactions();
                    }
                    zKVerProcessDialog.updateView();
                } else {
                    try {
                        zKVerProcessDialog.clearDatas();
                        beginTransaction.detach(zKVerProcessDialog);
                        beginTransaction.commitAllowingStateLoss();
                        this.fragmentManager.executePendingTransactions();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } else {
            zKVerProcessDialog.clearDatas();
            FileLogUtils.writeTouchLog("clearDatas: ZKEventLauncher showProcessDialog1");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setProcessDialogTimeOut(EventSetProcessDialogTimeOut eventSetProcessDialogTimeOut) {
        ZKVerProcessDialog zKVerProcessDialog = (ZKVerProcessDialog) FragmentHelper.getAlreadyAddedFragment(this, ZKVerProcessDialog.TAG);
        if (zKVerProcessDialog != null) {
            zKVerProcessDialog.setTimeout(eventSetProcessDialogTimeOut.getTimeOut());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setProcessDismissDialog(EventProcessDismissDialog eventProcessDismissDialog) {
        super.showProcessDialog(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveInitTask(EventInitTask eventInitTask) {
        int task = eventInitTask.getTask();
        if (task == 0) {
            setIsDBOkStatic(true);
            initDBComplete();
            initDB();
            initDataSuccess();
            runningTask(true);
            initComplete();
            LogUtils.d(TAG, "receiveInitTask：EVENT_INIT_DB_SUCCESS  finish");
            Intent intent = new Intent("com.alarm.reset");
            List<ResolveInfo> queryBroadcastReceivers = getPackageManager().queryBroadcastReceivers(intent, 0);
            if (queryBroadcastReceivers != null && !queryBroadcastReceivers.isEmpty()) {
                for (ResolveInfo next : queryBroadcastReceivers) {
                    String str = TAG;
                    Log.d(str, "receiveInitTask: " + next.activityInfo.packageName);
                    Log.d(str, "receiveInitTask: " + next.activityInfo.parentActivityName);
                    Log.d(str, "receiveInitTask: " + next.activityInfo.name);
                }
            }
            intent.setComponent(new ComponentName("com.zkteco.android.alarmMgmt", "com.zkteco.android.alarm.receiver.RingReceiver"));
            sendBroadcast(intent);
        } else if (task == 1) {
            String str2 = TAG;
            LogUtils.d(str2, "receiveInitTask：EVENT_INIT_HUB");
            FileLogUtils.writeLauncherInitRecord("EVENT_INIT_HUB");
            initHub();
            LogUtils.d(str2, "receiveInitTask：EVENT_INIT_HUB  finish");
        } else if (task == 2) {
            String str3 = TAG;
            LogUtils.d(str3, "receiveInitTask：EVENT_ALL_INIT_OK");
            onZKInitializeComplete();
            onCoreServiceInitBroadcast();
            LogUtils.d(str3, "receiveInitTask：EVENT_ALL_INIT_OK  finish");
            FileLogUtils.writeLauncherInitRecord("EVENT_ALL_INIT_OK ----------------------------------------");
        } else if (task == 3) {
            changeViewVisibility();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveChangeTheme(EventChangeTheme eventChangeTheme) {
        eventChangeTheme.isChange();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveDismissInitDialog(EventDismissInitDialog eventDismissInitDialog) {
        FileLogUtils.writeLauncherInitRecord("isDismiss: " + eventDismissInitDialog.isDismiss());
        hookWebView();
        if (eventDismissInitDialog.isDismiss()) {
            dismissInitDialog();
        }
    }

    private void hookWebView() {
        Method method;
        int i = Build.VERSION.SDK_INT;
        try {
            Class<?> cls = Class.forName("android.webkit.WebViewFactory");
            Field declaredField = cls.getDeclaredField("sProviderInstance");
            declaredField.setAccessible(true);
            Object obj = declaredField.get((Object) null);
            if (obj == null) {
                if (i > 22) {
                    method = cls.getDeclaredMethod("getProviderClass", new Class[0]);
                } else if (i == 22) {
                    method = cls.getDeclaredMethod("getFactoryClass", new Class[0]);
                } else {
                    return;
                }
                method.setAccessible(true);
                Class cls2 = (Class) method.invoke(cls, new Object[0]);
                Class<?> cls3 = Class.forName("android.webkit.WebViewDelegate");
                Constructor<?> declaredConstructor = cls3.getDeclaredConstructor(new Class[0]);
                declaredConstructor.setAccessible(true);
                if (i < 26) {
                    Constructor constructor = cls2.getConstructor(new Class[]{cls3});
                    if (constructor != null) {
                        constructor.setAccessible(true);
                        obj = constructor.newInstance(new Object[]{declaredConstructor.newInstance(new Object[0])});
                    }
                } else {
                    Field declaredField2 = cls.getDeclaredField("CHROMIUM_WEBVIEW_FACTORY_METHOD");
                    declaredField2.setAccessible(true);
                    String str = (String) declaredField2.get((Object) null);
                    if (str == null) {
                        str = "create";
                    }
                    Method method2 = cls2.getMethod(str, new Class[]{cls3});
                    if (method2 != null) {
                        obj = method2.invoke((Object) null, new Object[]{declaredConstructor.newInstance(new Object[0])});
                    }
                }
                if (obj != null) {
                    declaredField.set("sProviderInstance", obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
