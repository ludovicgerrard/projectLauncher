package com.zkteco.android.employeemgmt.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.contants.BiometricCommuCMD;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.AccGroup;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.BaseActivity;
import com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter;
import com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean;
import com.zkteco.android.employeemgmt.model.ZkStaffTypeModel;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.zkcore.read.ReadOptionsManager;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.view.alert.ZKEditDialog;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ZKStaffOfflineActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    /* access modifiers changed from: private */
    public ZKStaffTypeAdapter adapter;
    DataManager db;
    int fingerFunOn;
    /* access modifiers changed from: private */
    public ZkStaffTypeModel group;
    int hasFingerModule;
    private long id = 0;
    private List<ZKStaffVerifyBean> listVerifyList;
    private ListView mListView;
    ReadOptionsManager readOptionsManager;
    private TemplateManager templateManager;
    /* access modifiers changed from: private */
    public ZkStaffTypeModel timeZone1;
    /* access modifiers changed from: private */
    public ZkStaffTypeModel timeZone2;
    /* access modifiers changed from: private */
    public ZkStaffTypeModel timeZone3;
    /* access modifiers changed from: private */
    public UserInfo userInfo;
    private Map<Integer, String> verifyMap;

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
                ZKStaffOfflineActivity.this.finish();
            }
        }, getResources().getString(R.string.zk_staff_user_door_access));
        zKToolbar.setRightView();
    }

    public void initListener() {
        this.mListView.setOnItemClickListener(this);
    }

    public void initData() {
        this.listVerifyList = createList();
        this.readOptionsManager = new ReadOptionsManager(this);
        this.templateManager = new TemplateManager(this);
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
        String str2;
        String str3;
        String str4;
        ArrayList arrayList = new ArrayList();
        this.group = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_group), 2, 1);
        UserInfo userInfo2 = this.userInfo;
        String str5 = "";
        if (userInfo2 != null) {
            str = userInfo2.getAcc_Group_ID() + str5;
        } else {
            str = str5;
        }
        this.group.setValue(str);
        arrayList.add(this.group);
        ZkStaffTypeModel zkStaffTypeModel = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_verifyline), 1, 2);
        if (-1 == this.userInfo.getVerify_Type()) {
            str2 = this.listVerifyList.get(0).getString();
        } else {
            str2 = str5;
            for (int i = 0; i < this.listVerifyList.size(); i++) {
                if (this.listVerifyList.get(i).getValue() == this.userInfo.getVerify_Type()) {
                    str2 = this.listVerifyList.get(i).getString();
                }
            }
        }
        zkStaffTypeModel.setValue(str2);
        if (VerifyTypeUtils.enableFinger()) {
            this.fingerFunOn = this.db.getIntOption("FingerFunOn", 0);
            int intOption = this.db.getIntOption("hasFingerModule", 0);
            this.hasFingerModule = intOption;
            if (this.fingerFunOn == 1 && intOption == 1) {
                ZkStaffTypeModel zkStaffTypeModel2 = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_force_finger), 1, 3);
                int forceFingerNum = getForceFingerNum();
                if (forceFingerNum == 0) {
                    zkStaffTypeModel2.setValue(getResources().getString(R.string.zk_staff_no_defined));
                } else {
                    zkStaffTypeModel2.setValue(forceFingerNum + str5);
                }
                arrayList.add(zkStaffTypeModel2);
            }
        }
        ZkStaffTypeModel zkStaffTypeModel3 = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_group_time_zone), 3, 4);
        int is_Group_TZ = this.userInfo.getIs_Group_TZ();
        if (is_Group_TZ == 1) {
            zkStaffTypeModel3.setRadioBtnStatus(true);
        } else {
            zkStaffTypeModel3.setRadioBtnStatus(false);
        }
        arrayList.add(zkStaffTypeModel3);
        if (is_Group_TZ != 1) {
            this.timeZone1 = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_time_zone_1), 2, 5);
            UserInfo userInfo3 = this.userInfo;
            if (userInfo3 != null) {
                str3 = userInfo3.getTimezone1() + str5;
            } else {
                str3 = str5;
            }
            this.timeZone1.setValue(str3);
            arrayList.add(this.timeZone1);
            this.timeZone2 = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_time_zone_2), 2, 6);
            UserInfo userInfo4 = this.userInfo;
            if (userInfo4 != null) {
                str4 = userInfo4.getTimezone2() + str5;
            } else {
                str4 = str5;
            }
            this.timeZone2.setValue(str4);
            arrayList.add(this.timeZone2);
            this.timeZone3 = new ZkStaffTypeModel(getResources().getString(R.string.zk_staff_time_zone_3), 2, 7);
            UserInfo userInfo5 = this.userInfo;
            if (userInfo5 != null) {
                str5 = userInfo5.getTimezone3() + str5;
            }
            this.timeZone3.setValue(str5);
            arrayList.add(this.timeZone3);
        }
        this.adapter.setmData(arrayList);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        ZkStaffTypeModel zkStaffTypeModel = (ZkStaffTypeModel) this.adapter.getItem(i);
        switch (zkStaffTypeModel.getId()) {
            case 1:
                showGroupDialog();
                return;
            case 2:
                Intent intent = new Intent(this, ZKStaffVerifyVerActivity.class);
                intent.putExtra("userInfo_id", this.userInfo.getID());
                startActivity(intent);
                return;
            case 3:
                Intent intent2 = new Intent(this, ZKStaffForceFingerActivity.class);
                intent2.putExtra("userInfo_id", this.userInfo.getID());
                startActivity(intent2);
                return;
            case 4:
                if (zkStaffTypeModel.isRadioBtnStatus()) {
                    this.userInfo.setIs_Group_TZ(0);
                } else {
                    this.userInfo.setIs_Group_TZ(1);
                }
                try {
                    this.userInfo.update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setDatas();
                return;
            case 5:
                showTimeZoneDialog(R.string.zk_staff_time_zone_1, Integer.valueOf(this.timeZone1.getValue()).intValue(), 1);
                return;
            case 6:
                showTimeZoneDialog(R.string.zk_staff_time_zone_2, Integer.valueOf(this.timeZone2.getValue()).intValue(), 2);
                return;
            case 7:
                showTimeZoneDialog(R.string.zk_staff_time_zone_3, Integer.valueOf(this.timeZone3.getValue()).intValue(), 3);
                return;
            default:
                return;
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
                if (ZKStaffOfflineActivity.this.checkGroupData(zKEditDialog)) {
                    if (inputText.length() > 1) {
                        inputText = zKEditDialog.getInputText().replaceAll("^0*", "");
                    }
                    if (TextUtils.isEmpty(inputText)) {
                        inputText = "0";
                    }
                    if (ZKStaffOfflineActivity.this.userInfo != null) {
                        ZKStaffOfflineActivity.this.userInfo.setAcc_Group_ID(Integer.valueOf(inputText).intValue());
                        try {
                            ZKStaffOfflineActivity.this.userInfo.update();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    zKEditDialog.cancel();
                    ZKStaffOfflineActivity.this.group.setValue(inputText);
                    ZKStaffOfflineActivity.this.adapter.notifyDataSetChanged();
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
        if (parseInt > 99 || parseInt < 0) {
            zKEditDialog.setHint(getString(R.string.zk_staff_group_dialog_error));
            return false;
        }
        try {
            if (((AccGroup) new AccGroup().getQueryBuilder().where().eq(BiometricCommuCMD.FIELD_DESC_TMP_ID, Integer.valueOf(parseInt)).queryForFirst()) != null) {
                return true;
            }
            zKEditDialog.setHint(getString(R.string.zk_staff_offline_dialog_error_hint_not_exist));
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void showTimeZoneDialog(int i, int i2, final int i3) {
        final ZKEditDialog zKEditDialog = new ZKEditDialog(this.mContext);
        zKEditDialog.show();
        zKEditDialog.setBtnType(2, this.mContext.getResources().getString(R.string.zk_core_cancel), this.mContext.getResources().getString(R.string.zk_core_ok));
        zKEditDialog.setMessage(this.mContext.getResources().getString(R.string.zk_staff_time_zone_input) + this.mContext.getResources().getString(i) + " (" + 0 + "~" + 50 + ")");
        zKEditDialog.setContentType(1, "");
        zKEditDialog.setEditText(2, 3, i2 + "");
        zKEditDialog.setListener(new ZKEditDialog.ResultListener() {
            public void failure() {
            }

            public void success() {
                String inputText = zKEditDialog.getInputText();
                if (ZKStaffOfflineActivity.this.checkTimeZoneData(zKEditDialog)) {
                    if (inputText.length() > 1) {
                        inputText = zKEditDialog.getInputText().replaceAll("^0*", "");
                    }
                    if (TextUtils.isEmpty(inputText)) {
                        inputText = "0";
                    }
                    if (ZKStaffOfflineActivity.this.userInfo != null) {
                        int i = i3;
                        if (i == 1) {
                            ZKStaffOfflineActivity.this.userInfo.setTimezone1(Integer.valueOf(inputText).intValue());
                            ZKStaffOfflineActivity.this.timeZone1.setValue(inputText);
                        } else if (i == 2) {
                            ZKStaffOfflineActivity.this.userInfo.setTimezone2(Integer.valueOf(inputText).intValue());
                            ZKStaffOfflineActivity.this.timeZone2.setValue(inputText);
                        } else if (i == 3) {
                            ZKStaffOfflineActivity.this.userInfo.setTimezone3(Integer.valueOf(inputText).intValue());
                            ZKStaffOfflineActivity.this.timeZone3.setValue(inputText);
                        }
                        try {
                            ZKStaffOfflineActivity.this.userInfo.update();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    zKEditDialog.cancel();
                    ZKStaffOfflineActivity.this.adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean checkTimeZoneData(ZKEditDialog zKEditDialog) {
        if (TextUtils.isEmpty(zKEditDialog.getInputText())) {
            zKEditDialog.setHint(getString(R.string.zk_staff_time_zone_null));
            return false;
        }
        int parseInt = Integer.parseInt(zKEditDialog.getInputText());
        if (parseInt <= 50 && parseInt >= 0) {
            return true;
        }
        zKEditDialog.setHint(getString(R.string.zk_staff_time_zone_error));
        return false;
    }

    public int getForceFingerNum() {
        int i = 0;
        for (FpTemplate10 valid : this.templateManager.getFingerTemplateForUserPin(String.valueOf(this.userInfo.getID()))) {
            if (valid.getValid() == 3) {
                i++;
            }
        }
        return i;
    }

    private ArrayList<ZKStaffVerifyBean> createList() {
        this.verifyMap = VerifyTypeUtils.getmVerifyTypeMap();
        List<String> verifyTypeList = VerifyTypeUtils.getVerifyTypeList();
        ArrayList<ZKStaffVerifyBean> arrayList = new ArrayList<>();
        int i = 0;
        for (int i2 = 0; i2 < verifyTypeList.size(); i2++) {
            Iterator<Map.Entry<Integer, String>> it = this.verifyMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry next = it.next();
                if (((String) next.getValue()).equals(verifyTypeList.get(i2))) {
                    i = ((Integer) next.getKey()).intValue();
                    break;
                }
            }
            arrayList.add(new ZKStaffVerifyBean(verifyTypeList.get(i2), i));
        }
        return arrayList;
    }
}
