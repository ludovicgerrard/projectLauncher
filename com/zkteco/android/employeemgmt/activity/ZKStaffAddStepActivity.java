package com.zkteco.android.employeemgmt.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.employeemgmt.fragment.ZKStaffAddStep1Fragment;
import com.zkteco.android.zkcore.utils.FileUtil;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.edk.camera.lib.ZkThreadPoolManager;
import java.io.File;

public class ZKStaffAddStepActivity extends ZKStaffBaseActivity {
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private static final String TAG = "ZKStaffAddStepActivity";
    private IntentFilter homeFilter;
    private boolean isCreateNewUser = false;
    private boolean isFirst = true;
    private boolean keyboardState;
    private final BroadcastReceiver mHomeReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(ZKStaffAddStepActivity.TAG, "onReceive: action: " + action);
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action)) {
                String stringExtra = intent.getStringExtra("reason");
                Log.d(ZKStaffAddStepActivity.TAG, "reason: " + stringExtra);
                if ("homekey".equals(stringExtra)) {
                    Log.d(ZKStaffAddStepActivity.TAG, "homekey");
                    ZKStaffAddStepActivity.this.deletePhoto();
                }
            }
        }
    };
    private ZKToolbar mToolbar;
    private UserInfo mUserInfo;
    public RefreshPhotoListener refreshPhotoListener;
    private String userPinBefore = null;
    private String userPinReplace = "";

    public interface RefreshPhotoListener {
        void refreshPhoto();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_add);
        initView();
    }

    private void initView() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.zk_toolbar);
        this.mToolbar = zKToolbar;
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffAddStepActivity.this.lambda$initView$0$ZKStaffAddStepActivity(view);
            }
        }, getString(R.string.zk_staff_sadd_title));
        this.mToolbar.setRightView();
        getSupportFragmentManager().beginTransaction().replace(R.id.sfl_content, new ZKStaffAddStep1Fragment()).commit();
        this.homeFilter = new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS");
    }

    public /* synthetic */ void lambda$initView$0$ZKStaffAddStepActivity(View view) {
        finish();
    }

    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 3 || backStackEntryCount == 4) {
            finish();
        } else if (backStackEntryCount > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
        deletePhoto();
    }

    /* access modifiers changed from: private */
    public void deletePhoto() {
        if (!this.isFirst && !"".equals(this.userPinReplace) && !this.isCreateNewUser) {
            ZkThreadPoolManager.getInstance().execute(new Runnable() {
                public final void run() {
                    ZKStaffAddStepActivity.this.lambda$deletePhoto$1$ZKStaffAddStepActivity();
                }
            });
        }
    }

    public /* synthetic */ void lambda$deletePhoto$1$ZKStaffAddStepActivity() {
        Log.e("llm", "replace = " + this.userPinReplace);
        FileUtil.deleteFile(ZKFilePath.PICTURE_PATH + this.userPinReplace + ZKFilePath.SUFFIX_IMAGE, this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mContext.registerReceiver(this.mHomeReceiver, this.homeFilter);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.mHomeReceiver != null) {
            try {
                this.mContext.unregisterReceiver(this.mHomeReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        deleteDirectory(new File(ZKFilePath.IMAGE_PATH + "temp"));
        super.onDestroy();
    }

    public void deleteDirectory(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File deleteDirectory : listFiles) {
                deleteDirectory(deleteDirectory);
            }
            file.delete();
        }
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, ZKStaffAddStepActivity.class));
    }

    public boolean isKeyboardState() {
        return this.keyboardState;
    }

    public void setKeyboardState(boolean z) {
        this.keyboardState = z;
    }

    public void changeTitle(String str) {
        this.mToolbar.setLeftView(str);
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.mUserInfo = userInfo;
    }

    public String getUserPinBefore() {
        return this.userPinBefore;
    }

    public void setUserPinBefore(String str) {
        this.userPinBefore = str;
    }

    public void setUserPinReplace(String str) {
        this.userPinReplace = str;
    }

    public String getUserPinReplace() {
        return this.userPinReplace;
    }

    public void setIsFirst(boolean z) {
        this.isFirst = z;
    }

    public boolean getIsFirst() {
        return this.isFirst;
    }

    public boolean getIsCreateNewUser() {
        return this.isCreateNewUser;
    }

    public void setIsCreateNewUser(boolean z) {
        this.isCreateNewUser = z;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == 1999 && i == 999) {
            this.refreshPhotoListener.refreshPhoto();
        }
    }

    public void setRefreshPhotoListener(RefreshPhotoListener refreshPhotoListener2) {
        this.refreshPhotoListener = refreshPhotoListener2;
    }
}
