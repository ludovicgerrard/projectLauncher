package com.zkteco.android.employeemgmt.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AccTimeZoneRule;
import com.zkteco.android.db.orm.tna.AccUserAuthorize;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.BaseActivity;
import com.zkteco.android.employeemgmt.adapter.ZKListSelectAdapter;
import com.zkteco.android.employeemgmt.model.ZKListSelectModel;
import com.zkteco.android.zkcore.view.ZKToolbar;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ZKStaffTimeRuleActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    ZKListSelectAdapter adapter;
    ArrayList<ZKListSelectModel> datas = new ArrayList<>();
    private long id = 0;
    private DataManager mDataManager;
    private ListView mListView;
    private UserInfo userInfo;

    public int getLayoutResId() {
        return R.layout.activity_time_rule;
    }

    public void initPresenter() {
    }

    public void onPointerCaptureChanged(boolean z) {
    }

    public void initUI() {
        initToolBar();
        this.mDataManager = DBManager.getInstance();
        this.id = getIntent().getLongExtra("userInfo_id", 0);
        this.mListView = (ListView) findViewById(R.id.listview);
    }

    private void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.toolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                ZKStaffTimeRuleActivity.this.finish();
            }
        }, getResources().getString(R.string.zk_staff_time_rule));
        zKToolbar.setRightView();
    }

    public void initListener() {
        this.mListView.setOnItemClickListener(this);
    }

    public void initData() {
        try {
            this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(this.id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ZKListSelectAdapter zKListSelectAdapter = new ZKListSelectAdapter(this);
        this.adapter = zKListSelectAdapter;
        this.mListView.setAdapter(zKListSelectAdapter);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        setDatas();
    }

    public void setDatas() {
        boolean z;
        this.datas.clear();
        try {
            List query = new AccUserAuthorize().getQueryBuilder().where().eq("UserPIN", this.userInfo.getUser_PIN()).query();
            List queryForAll = new AccTimeZoneRule().queryForAll();
            for (int i = 0; i < queryForAll.size(); i++) {
                if (query != null) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= query.size()) {
                            break;
                        } else if (((AccTimeZoneRule) queryForAll.get(i)).getTime_Zone_ID() == ((AccUserAuthorize) query.get(i2)).getAuthorizeTimezone()) {
                            z = true;
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                z = false;
                if (z) {
                    this.datas.add(new ZKListSelectModel(getResources().getString(R.string.zk_staff_time_rule_item) + ((AccTimeZoneRule) queryForAll.get(i)).getTime_Zone_ID(), ((AccTimeZoneRule) queryForAll.get(i)).getTime_Zone_ID()).setSelectStatus(true));
                } else {
                    this.datas.add(new ZKListSelectModel(getResources().getString(R.string.zk_staff_time_rule_item) + ((AccTimeZoneRule) queryForAll.get(i)).getTime_Zone_ID(), ((AccTimeZoneRule) queryForAll.get(i)).getTime_Zone_ID()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(this.datas, new Comparator<ZKListSelectModel>() {
            public int compare(ZKListSelectModel zKListSelectModel, ZKListSelectModel zKListSelectModel2) {
                return zKListSelectModel.getValue() - zKListSelectModel2.getValue();
            }
        });
        this.adapter.setDatas(this.datas);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        ZKListSelectModel zKListSelectModel = (ZKListSelectModel) this.adapter.getItem(i);
        if (zKListSelectModel.isSelectStatus()) {
            deleteItem(zKListSelectModel.getValue());
            zKListSelectModel.setSelectStatus(false);
        } else {
            createItem(zKListSelectModel.getValue());
            zKListSelectModel.setSelectStatus(true);
        }
        this.adapter.notifyDataSetChanged();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void deleteItem(int r3) {
        /*
            r2 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = " DELETE       FROM      ACC_USER_AUTHORIZE  WHERE  AuthorizeTimezone = "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r3 = r0.append(r3)
            java.lang.String r0 = " AND UserPIN = '"
            java.lang.StringBuilder r3 = r3.append(r0)
            com.zkteco.android.db.orm.tna.UserInfo r0 = r2.userInfo
            java.lang.String r0 = r0.getUser_PIN()
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r0 = "'"
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            com.zkteco.android.db.orm.manager.DataManager r0 = r2.mDataManager     // Catch:{ SQLException -> 0x0037, all -> 0x0035 }
            android.database.Cursor r3 = r0.queryBySql(r3)     // Catch:{ SQLException -> 0x0037, all -> 0x0035 }
            if (r3 == 0) goto L_0x0037
            r3.close()
            goto L_0x0037
        L_0x0035:
            r3 = move-exception
            throw r3
        L_0x0037:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffTimeRuleActivity.deleteItem(int):void");
    }

    private void createItem(int i) {
        AccUserAuthorize accUserAuthorize = new AccUserAuthorize();
        accUserAuthorize.setUserPIN(this.userInfo.getUser_PIN());
        accUserAuthorize.setAuthorizeTimezone(i);
        try {
            accUserAuthorize.createIfNotExists();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
