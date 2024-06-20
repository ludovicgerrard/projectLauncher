package com.zkteco.android.employeemgmt.activity;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.BaseActivity;
import com.zkteco.android.employeemgmt.widget.CustomFingerZoneView;
import com.zkteco.android.zkcore.view.ZKToolbar;
import java.sql.SQLException;

public class ZKStaffForceFingerActivity extends BaseActivity implements View.OnClickListener {
    private static final int lefthand = 1;
    private static final int righthand = 2;
    DataManager dataManager;
    boolean finger0;
    boolean finger1;
    boolean finger2;
    boolean finger3;
    boolean finger4;
    boolean finger5;
    boolean finger6;
    boolean finger7;
    boolean finger8;
    boolean finger9;
    private long id = 0;
    private ImageView ivlefthand;
    private ImageView ivrighthand;
    private int lefthandFinger = 0;
    /* access modifiers changed from: private */
    public CustomFingerZoneView mFingerZoneView;
    private boolean once = false;
    private int righthandFinger = 5;
    private TemplateManager templateManager;
    /* access modifiers changed from: private */
    public UserInfo userInfo;

    public int getLayoutResId() {
        return R.layout.activity_force_finger;
    }

    public void initPresenter() {
    }

    public void initUI() {
        initToolBar();
        this.dataManager = DBManager.getInstance();
        this.templateManager = new TemplateManager(this);
        this.id = getIntent().getLongExtra("userInfo_id", 0);
        try {
            this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(this.id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (this.userInfo == null) {
            finish();
            return;
        }
        this.mFingerZoneView = (CustomFingerZoneView) findViewById(R.id.finger_view);
        this.ivlefthand = (ImageView) findViewById(R.id.iv_lefthand);
        this.ivrighthand = (ImageView) findViewById(R.id.iv_righthand);
    }

    private void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.fingerToolbar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                ZKStaffForceFingerActivity.this.finish();
            }
        }, getResources().getString(R.string.zk_staff_force_finger));
        zKToolbar.setRightView();
    }

    public void initListener() {
        this.ivlefthand.setOnClickListener(this);
        this.ivrighthand.setOnClickListener(this);
        CustomFingerZoneView customFingerZoneView = this.mFingerZoneView;
        if (customFingerZoneView != null) {
            customFingerZoneView.setFingerStatusChangedListener(new CustomFingerZoneView.FingerStatusChangedListener() {
                public void onFingerStatusChanged(int i, int i2, int i3, boolean z) {
                    int intValue = ZKStaffForceFingerActivity.this.mFingerZoneView.getFingerStatus(i3).intValue();
                    String str = "update fptemplate10 set valid = ? where pin = " + ZKStaffForceFingerActivity.this.userInfo.getID() + " and fingerid = " + i3;
                    if (intValue == 2) {
                        ZKStaffForceFingerActivity.this.dataManager.executeSql("ZKDB.db", str, new Object[]{3});
                    }
                    if (intValue == 4) {
                        ZKStaffForceFingerActivity.this.dataManager.executeSql("ZKDB.db", str, new Object[]{1});
                    }
                    ZKStaffForceFingerActivity.this.mFingerZoneView.setClickForceFingerStatus(i2, i3);
                    ZKStaffForceFingerActivity.this.mFingerZoneView.postInvalidate();
                }

                public void changeFingerBgAndState(int i) {
                    if (i == 1) {
                        for (int i2 = 0; i2 < 5; i2++) {
                            ZKStaffForceFingerActivity.this.changeFingerState(i2, true);
                        }
                    } else if (i == 2) {
                        for (int i3 = 5; i3 < 10; i3++) {
                            ZKStaffForceFingerActivity.this.changeFingerState(i3, true);
                        }
                    }
                    ZKStaffForceFingerActivity.this.changeFingerView(i);
                }
            });
        }
    }

    public void initData() {
        for (FpTemplate10 next : this.templateManager.getFingerTemplateForUserPin(String.valueOf(this.userInfo.getID()))) {
            int valid = next.getValid();
            if (valid == 1) {
                this.mFingerZoneView.setFingerFirstShow(next.getFingerid(), 2);
            } else if (valid == 3) {
                this.mFingerZoneView.setFingerFirstShow(next.getFingerid(), 4);
            }
        }
        changeFingerView(2);
        this.mFingerZoneView.invalidate();
    }

    /* access modifiers changed from: private */
    public void changeFingerState(int i, boolean z) {
        switch (i) {
            case 0:
                this.finger0 = z;
                return;
            case 1:
                this.finger1 = z;
                return;
            case 2:
                this.finger2 = z;
                return;
            case 3:
                this.finger3 = z;
                return;
            case 4:
                this.finger4 = z;
                return;
            case 5:
                this.finger5 = z;
                return;
            case 6:
                this.finger6 = z;
                return;
            case 7:
                this.finger7 = z;
                return;
            case 8:
                this.finger8 = z;
                return;
            case 9:
                this.finger9 = z;
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void changeFingerView(int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (i == 1) {
                if (!this.finger0 || !this.finger1 || !this.finger2 || !this.finger3 || !this.finger4) {
                    this.mFingerZoneView.setBackground(getDrawable(R.mipmap.left_bg_01));
                } else {
                    this.mFingerZoneView.setBackground(getDrawable(R.mipmap.left_bg_02));
                }
            }
            if (i != 2) {
                return;
            }
            if (!this.finger5 || !this.finger6 || !this.finger7 || !this.finger8 || !this.finger9) {
                this.mFingerZoneView.setBackground(getDrawable(R.mipmap.right_bg_01));
            } else {
                this.mFingerZoneView.setBackground(getDrawable(R.mipmap.right_bg_02));
            }
        }
    }

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 != R.id.iv_lefthand) {
            if (id2 == R.id.iv_righthand && this.mFingerZoneView.getChangeStatus() && this.once) {
                this.mFingerZoneView.setWhichHandAndFinger(2, this.righthandFinger);
                this.ivlefthand.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.lhand_black));
                this.ivrighthand.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.rhand_white));
                if (Build.VERSION.SDK_INT >= 21) {
                    this.mFingerZoneView.setBackground(getDrawable(R.mipmap.right_bg_01));
                }
                this.once = false;
                changeFingerView(2);
            }
        } else if (this.mFingerZoneView.getChangeStatus() && !this.once) {
            this.mFingerZoneView.setWhichHandAndFinger(1, this.lefthandFinger);
            this.ivlefthand.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.lhand_white));
            this.ivrighthand.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.rhand_black));
            if (Build.VERSION.SDK_INT >= 21) {
                this.mFingerZoneView.setBackground(getDrawable(R.mipmap.left_bg_01));
            }
            this.once = true;
            changeFingerView(1);
        }
    }
}
