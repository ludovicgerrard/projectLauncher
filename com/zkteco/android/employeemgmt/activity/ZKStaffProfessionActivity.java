package com.zkteco.android.employeemgmt.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.BaseActivity;
import com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter;
import com.zkteco.android.employeemgmt.model.ZkStaffTypeModel;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.zkcore.read.ReadOptionsManager;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.view.alert.ZKEditDialog;
import java.sql.SQLException;
import java.util.ArrayList;

public class ZKStaffProfessionActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    /* access modifiers changed from: private */
    public ZKStaffTypeAdapter adapter;
    DataManager db;
    int fingerFunOn;
    /* access modifiers changed from: private */
    public ZkStaffTypeModel group;
    int hasFingerModule;
    private long id = 0;
    private ListView mListView;
    ReadOptionsManager readOptionsManager;
    private TemplateManager templateManager;
    /* access modifiers changed from: private */
    public UserInfo userInfo;

    public int getLayoutResId() {
        return R.layout.activity_staff_profession;
    }

    public void initPresenter() {
    }

    public void onPointerCaptureChanged(boolean z) {
    }

    public void initUI() {
        initToolBar();
        this.db = DBManager.getInstance();
        this.templateManager = new TemplateManager(this);
        this.id = getIntent().getLongExtra("userInfo_id", 0);
        try {
            this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(this.id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.mListView = (ListView) findViewById(R.id.listview);
    }

    private void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.acessToolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                ZKStaffProfessionActivity.this.finish();
            }
        }, getResources().getString(R.string.zk_staff_user_door_access));
        zKToolbar.setRightView();
    }

    public void initListener() {
        this.mListView.setOnItemClickListener(this);
    }

    public void initData() {
        this.readOptionsManager = new ReadOptionsManager(this);
        ZKStaffTypeAdapter zKStaffTypeAdapter = new ZKStaffTypeAdapter(this);
        this.adapter = zKStaffTypeAdapter;
        this.mListView.setAdapter(zKStaffTypeAdapter);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        setDatas();
    }

    public void setDatas() {
        String str;
        ArrayList arrayList = new ArrayList();
        this.group = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_group), 2, 1);
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 != null) {
            str = userInfo2.getAcc_Group_ID() + "";
        } else {
            str = "";
        }
        this.group.setValue(str);
        arrayList.add(this.group);
        arrayList.add(new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_time_rule), 1, 2));
        if (VerifyTypeUtils.enableFinger()) {
            this.fingerFunOn = this.db.getIntOption("FingerFunOn", 0);
            int intOption = this.db.getIntOption("hasFingerModule", 0);
            this.hasFingerModule = intOption;
            if (this.fingerFunOn == 1 && intOption == 1) {
                ZkStaffTypeModel zkStaffTypeModel = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_force_finger), 1, 3);
                int forceFingerNum = getForceFingerNum();
                if (forceFingerNum == 0) {
                    zkStaffTypeModel.setValue(getResources().getString(R.string.zk_staff_no_defined));
                } else {
                    zkStaffTypeModel.setValue(forceFingerNum + "");
                }
                arrayList.add(zkStaffTypeModel);
            }
        }
        this.adapter.setmData(arrayList);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int id2 = ((ZkStaffTypeModel) this.adapter.getItem(i)).getId();
        if (id2 == 1) {
            showGroupDialog();
        } else if (id2 == 2) {
            Intent intent = new Intent(this, ZKStaffTimeRuleActivity.class);
            UserInfo userInfo2 = this.userInfo;
            if (userInfo2 != null) {
                intent.putExtra("userInfo_id", userInfo2.getID());
            }
            startActivity(intent);
        } else if (id2 == 3) {
            Intent intent2 = new Intent(this, ZKStaffForceFingerActivity.class);
            UserInfo userInfo3 = this.userInfo;
            if (userInfo3 != null) {
                intent2.putExtra("userInfo_id", userInfo3.getID());
            }
            startActivity(intent2);
        }
    }

    public void showGroupDialog() {
        final ZKEditDialog zKEditDialog = new ZKEditDialog(this.mContext);
        zKEditDialog.show();
        zKEditDialog.setBtnType(2, this.mContext.getResources().getString(R.string.zk_core_cancel), this.mContext.getResources().getString(R.string.zk_core_ok));
        int i = 1;
        zKEditDialog.setMessage(this.mContext.getResources().getString(R.string.zk_staff_group_dialog_title));
        zKEditDialog.setContentType(1, "");
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 != null) {
            i = userInfo2.getAcc_Group_ID();
        }
        zKEditDialog.setEditText(2, 3, i + "");
        zKEditDialog.setListener(new ZKEditDialog.ResultListener() {
            public void failure() {
            }

            public void success() {
                String inputText = zKEditDialog.getInputText();
                if (ZKStaffProfessionActivity.this.checkGroupData(zKEditDialog)) {
                    if (inputText.length() > 1) {
                        inputText = zKEditDialog.getInputText().replaceAll("^0*", "");
                    }
                    if (TextUtils.isEmpty(inputText)) {
                        inputText = "0";
                    }
                    if (ZKStaffProfessionActivity.this.userInfo != null) {
                        ZKStaffProfessionActivity.this.userInfo.setAcc_Group_ID(Integer.valueOf(inputText).intValue());
                        try {
                            ZKStaffProfessionActivity.this.userInfo.update();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    zKEditDialog.cancel();
                    ZKStaffProfessionActivity.this.group.setValue(inputText);
                    ZKStaffProfessionActivity.this.adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean checkGroupData(ZKEditDialog zKEditDialog) {
        if (TextUtils.isEmpty(zKEditDialog.getInputText())) {
            zKEditDialog.setHint(getString(R.string.zk_staff_group_dialog_null));
            return false;
        }
        int parseInt = Integer.parseInt(zKEditDialog.getInputText());
        if (parseInt <= 99 && parseInt >= 0) {
            return true;
        }
        zKEditDialog.setHint(getString(R.string.zk_staff_group_dialog_error));
        return false;
    }

    public int getForceFingerNum() {
        int i = 0;
        try {
            for (FpTemplate10 valid : this.templateManager.getFingerTemplateForUserPin(String.valueOf(this.userInfo.getID()))) {
                if (valid.getValid() == 3) {
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
