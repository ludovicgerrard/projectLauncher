package com.zktechnology.android.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.zktechnology.android.bean.AppInfo;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.receiver.OpLogReceiver;
import com.zktechnology.android.strategy.BackgroundThreadExecutor;
import com.zktechnology.android.strategy.MainThreadExecutor;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.employeemgmt.activity.ZKStaffHomeActivity;
import com.zkteco.android.zkcore.view.ZKToolbar;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends BaseActivity implements View.OnClickListener {
    int AccessRuleType;
    private float FLIP_DISTANCE = 50.0f;
    int LockFunOn;
    ArrayList<AppInfo> appList = new ArrayList<>();
    GridView gridView;
    private boolean isCanBack;
    private LauncherScreenTimeoutRunnable launcherScreenTimeoutRunnable = null;
    ArrayList<String> list = new ArrayList<>();
    private DataManager mDataManager;
    private int screenTimeoutMs = -1;

    interface ClickListener {
        void onClick(int i, AppInfo appInfo);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Log.e("android lifecycle", "menu onCreate: ");
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_menu);
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.acessToolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                MenuActivity.this.lambda$onCreate$0$MenuActivity(view);
            }
        }, getResources().getString(R.string.all_apps_button_label));
        zKToolbar.setRightView();
        DataManager instance = DBManager.getInstance();
        this.mDataManager = instance;
        int intOption = instance.getIntOption("~USBDisk", 0);
        this.list.add("com.zkteco.android.attendanceevent");
        this.list.add("com.zkteco.android.accesscontrol");
        this.list.add("com.zkteco.android.historyrecord");
        this.list.add("com.zkteco.android.datamgmt");
        if (intOption == 1) {
            this.list.add("com.zkteco.android.udiskmgmt");
        }
        this.list.add("com.zkteco.android.alarmMgmt");
        this.list.add("com.zkteco.android.setup");
        this.list.add("com.zkteco.android.worknumber");
        this.list.add("com.zkteco.android.splugin");
        this.list.add("com.zkteco.android.msgmgmt");
        this.list.add("com.zkteco.android.virtual.data");
        if (intOption == 1) {
            this.list.add("com.zkteco.android.update");
        }
        initView();
        getInstalledApps();
        MyAdapter myAdapter = new MyAdapter();
        myAdapter.setClickListener(new ClickListener() {
            public final void onClick(int i, AppInfo appInfo) {
                MenuActivity.this.lambda$onCreate$1$MenuActivity(i, appInfo);
            }
        });
        this.gridView.setAdapter(myAdapter);
        OpLogReceiver.sendBroadcast(this, new Intent(OpLogReceiver.ACTION_ENTER_MENU));
    }

    public /* synthetic */ void lambda$onCreate$0$MenuActivity(View view) {
        finishActivity();
    }

    public /* synthetic */ void lambda$onCreate$1$MenuActivity(int i, AppInfo appInfo) {
        if (i == 0) {
            startActivity(new Intent(this, ZKStaffHomeActivity.class));
            overridePendingTransition(0, 0);
        } else if ("com.zkteco.android.accesscontrol".equals(appInfo.packageName)) {
            if (ZKLauncher.sAccessRuleType == 1) {
                startActivityForPackageName(appInfo.packageName, appInfo.packageName + ".business.home.profession.ZkAccHomeActivity");
            } else if (ZKLauncher.sLockFunOn == 15) {
                startActivityForPackageName(appInfo.packageName, appInfo.packageName + ".business.home.senior.ZkAccSeniorHomeActivity");
            } else {
                startActivityForPackageName(appInfo.packageName, appInfo.packageName + ".business.home.offline.ZKAccOfflineHomeActivity");
            }
        } else if (!"com.zkteco.android.historyrecord".equals(appInfo.packageName)) {
            startActivityForPackage(this, appInfo.packageName);
        } else if (ZKLauncher.sAccessRuleType == 1 && ZKLauncher.sLockFunOn == 15) {
            startActivityForPackageName(appInfo.packageName, appInfo.packageName + ".activity.ZKAttAccRecordActivity");
        } else {
            startActivityForPackageName(appInfo.packageName, appInfo.packageName + ".activity.ZKAttRecordActivity");
        }
    }

    private void initView() {
        this.gridView = (GridView) findViewById(R.id.gridview);
    }

    public void getInstalledApps() {
        AppInfo appInfo = new AppInfo();
        appInfo.appIcon = ContextCompat.getDrawable(this, R.mipmap.emplyee_icon);
        appInfo.appName = getResources().getString(R.string.app_name);
        this.appList.add(appInfo);
        List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < this.list.size(); i++) {
            String str = this.list.get(i);
            if (str.equals("com.zkteco.android.employeemgmt")) {
                AppInfo appInfo2 = new AppInfo();
                appInfo2.appName = getResources().getString(R.string.app_name);
                appInfo2.packageName = "com.zkteco.android.employeemgmt";
                appInfo2.appIcon = ContextCompat.getDrawable(this, R.mipmap.emplyee_icon);
                this.appList.add(appInfo2);
            } else {
                for (int i2 = 0; i2 < installedPackages.size(); i2++) {
                    PackageInfo packageInfo = installedPackages.get(i2);
                    if (packageInfo.packageName.equals(str)) {
                        AppInfo appInfo3 = new AppInfo();
                        appInfo3.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                        appInfo3.packageName = packageInfo.packageName;
                        appInfo3.versionName = packageInfo.versionName;
                        appInfo3.versionCode = packageInfo.versionCode;
                        appInfo3.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                        this.appList.add(appInfo3);
                    }
                }
            }
        }
    }

    public void onClick(View view) {
        if (this.isCanBack) {
            finishActivity();
        }
    }

    class MyAdapter extends BaseAdapter {
        ClickListener clickListener;

        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return 0;
        }

        MyAdapter() {
        }

        public void setClickListener(ClickListener clickListener2) {
            this.clickListener = clickListener2;
        }

        public int getCount() {
            return MenuActivity.this.appList.size();
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            View inflate = View.inflate(MenuActivity.this, R.layout.item_menu, (ViewGroup) null);
            final AppInfo appInfo = MenuActivity.this.appList.get(i);
            inflate.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    MyAdapter.this.clickListener.onClick(i, appInfo);
                }
            });
            ((ImageView) inflate.findViewById(R.id.img)).setBackground(appInfo.appIcon);
            ((TextView) inflate.findViewById(R.id.text)).setText(appInfo.appName);
            return inflate;
        }
    }

    public void startActivityForPackageName(String str, String str2) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(str, str2));
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void startActivityForPackage(Context context, String str) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        launchIntentForPackage.setPackage((String) null);
        context.startActivity(launchIntentForPackage);
        overridePendingTransition(0, 0);
    }

    private void enableLauncherScreenTimeout() {
        this.screenTimeoutMs = DBManager.getInstance().getIntOption("TOMenu", 60) * 1000;
        disableLauncherScreenTimeout();
        if (this.screenTimeoutMs > 0) {
            this.launcherScreenTimeoutRunnable = new LauncherScreenTimeoutRunnable();
            BackgroundThreadExecutor.getInstance().executeDelayed(this.launcherScreenTimeoutRunnable, (long) this.screenTimeoutMs);
        }
    }

    private void disableLauncherScreenTimeout() {
        LauncherScreenTimeoutRunnable launcherScreenTimeoutRunnable2 = this.launcherScreenTimeoutRunnable;
        if (launcherScreenTimeoutRunnable2 != null) {
            launcherScreenTimeoutRunnable2.setKillRunnable();
            BackgroundThreadExecutor.getInstance().remove(this.launcherScreenTimeoutRunnable);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.e("android lifecycle", "menu onResume: ");
        super.onResume();
        enableLauncherScreenTimeout();
        this.isCanBack = false;
        MainThreadExecutor.getInstance().executeDelayed(new Runnable() {
            public final void run() {
                MenuActivity.this.lambda$onResume$2$MenuActivity();
            }
        }, 500);
    }

    public /* synthetic */ void lambda$onResume$2$MenuActivity() {
        this.isCanBack = true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        disableLauncherScreenTimeout();
    }

    private class LauncherScreenTimeoutRunnable implements Runnable {
        private boolean killRunnable = false;

        public LauncherScreenTimeoutRunnable() {
        }

        public void setKillRunnable() {
            this.killRunnable = true;
        }

        public void run() {
            if (!this.killRunnable) {
                MenuActivity.this.finishActivity();
            }
        }
    }

    public void onBackPressed() {
        finishActivity();
    }

    /* access modifiers changed from: private */
    public void finishActivity() {
        finish();
        overridePendingTransition(17432578, 17432579);
    }
}
