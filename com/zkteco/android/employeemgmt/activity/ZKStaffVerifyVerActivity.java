package com.zkteco.android.employeemgmt.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.employeemgmt.adapter.ZKStaffVertificationVerAdapter;
import com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.view.alert.ZKConfirmDialog;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ZKStaffVerifyVerActivity extends ZKStaffBaseActivity {
    private String TYPE_CARD = "卡";
    private String TYPE_FACE = "人脸";
    private String TYPE_FINGER = "指纹";
    private String TYPE_PSW = "密码";
    private ZKStaffVertificationVerAdapter adapter;
    private final DataManager dataManager = DBManager.getInstance();
    private ListView listView;
    /* access modifiers changed from: private */
    public List<ZKStaffVerifyBean> mData = new ArrayList();
    private int sAccessRuleType;
    private TemplateManager templateManager;
    /* access modifiers changed from: private */
    public UserInfo userInfo;
    private Map<Integer, String> verifyMap;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.templateManager = new TemplateManager(this);
        setContentView((int) R.layout.activity_zkstaff_verify_ver);
        VerifyTypeUtils.init(this);
        Intent intent = getIntent();
        long longExtra = intent.getLongExtra("userInfo_id", 0);
        this.sAccessRuleType = intent.getIntExtra("sAccessRuleType", 0);
        if (longExtra != 0) {
            try {
                this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(longExtra));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.userInfo == null) {
            showToast(this.mContext, (int) R.string.error_return);
            finish();
            return;
        }
        this.mData = createList();
        initView();
    }

    private void initView() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.toolbar_verify);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                ZKStaffVerifyVerActivity.this.finish();
            }
        }, getString(R.string.zk_staff_vertify_tit));
        zKToolbar.setRightView();
        this.adapter = new ZKStaffVertificationVerAdapter(this, this.mData);
        ListView listView2 = (ListView) findViewById(R.id.listview_verify);
        this.listView = listView2;
        listView2.setAdapter(this.adapter);
        int verify_Type = this.userInfo.getVerify_Type();
        if (verify_Type == -1) {
            verify_Type = 0;
        }
        this.adapter.setSelected(verify_Type);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* JADX WARNING: Code restructure failed: missing block: B:126:0x02e0, code lost:
                if (r0 != 0) goto L_0x02f1;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:135:0x02ef, code lost:
                if (r0 != 0) goto L_0x02f1;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:136:0x02f1, code lost:
                r4.convertPushFree(r0);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:137:0x02f4, code lost:
                r3.this$0.finish();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:138:0x02f9, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:17:0x007a, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$500(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x007c;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:23:0x0098, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$200(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x009a;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b6, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$400(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x00b8;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d4, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$200(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x00d6;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:45:0x0104, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$200(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x0106;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:51:0x0122, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$200(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x0124;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:57:0x0140, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$500(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x0142;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:65:0x016a, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$500(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x016c;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:71:0x0188, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$200(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x018a;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:77:0x01a6, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$200(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x01a8;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x0050, code lost:
                if (com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$200(r6, com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.access$100(r6)) == false) goto L_0x0052;
             */
            /* JADX WARNING: Removed duplicated region for block: B:142:0x02ff  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onItemClick(android.widget.AdapterView<?> r4, android.view.View r5, int r6, long r7) {
                /*
                    r3 = this;
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    java.util.List r4 = r4.mData
                    java.lang.Object r4 = r4.get(r6)
                    com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean r4 = (com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean) r4
                    int r4 = r4.getValue()
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r5 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r5 = r5.userInfo
                    int r5 = r5.getPrivilege()
                    r6 = 14
                    if (r5 != r6) goto L_0x0250
                    com.zkteco.android.zkcore.view.alert.ZKConfirmDialog r5 = new com.zkteco.android.zkcore.view.alert.ZKConfirmDialog
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    android.content.Context r6 = r6.getApplicationContext()
                    r5.<init>(r6)
                    switch(r4) {
                        case 1: goto L_0x023e;
                        case 2: goto L_0x002c;
                        case 3: goto L_0x022c;
                        case 4: goto L_0x021a;
                        case 5: goto L_0x01fc;
                        case 6: goto L_0x01de;
                        case 7: goto L_0x01c0;
                        case 8: goto L_0x01ae;
                        case 9: goto L_0x0190;
                        case 10: goto L_0x0172;
                        case 11: goto L_0x0148;
                        case 12: goto L_0x012a;
                        case 13: goto L_0x010c;
                        case 14: goto L_0x00ee;
                        case 15: goto L_0x00dc;
                        case 16: goto L_0x00be;
                        case 17: goto L_0x00a0;
                        case 18: goto L_0x0082;
                        case 19: goto L_0x0058;
                        case 20: goto L_0x002e;
                        default: goto L_0x002c;
                    }
                L_0x002c:
                    goto L_0x0250
                L_0x002e:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r6 = r6.userInfo
                    boolean r6 = com.zkteco.android.employeemgmt.util.HasFaceUtils.isHasFace((com.zkteco.android.db.orm.tna.UserInfo) r6)
                    if (r6 == 0) goto L_0x0052
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 == 0) goto L_0x0052
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x0052:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x0058:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r6 = r6.userInfo
                    boolean r6 = com.zkteco.android.employeemgmt.util.HasFaceUtils.isHasFace((com.zkteco.android.db.orm.tna.UserInfo) r6)
                    if (r6 == 0) goto L_0x007c
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 == 0) goto L_0x007c
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.cardExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x007c:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x0082:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r6 = r6.userInfo
                    boolean r6 = com.zkteco.android.employeemgmt.util.HasFaceUtils.isHasFace((com.zkteco.android.db.orm.tna.UserInfo) r6)
                    if (r6 == 0) goto L_0x009a
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x009a:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x00a0:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r6 = r6.userInfo
                    boolean r6 = com.zkteco.android.employeemgmt.util.HasFaceUtils.isHasFace((com.zkteco.android.db.orm.tna.UserInfo) r6)
                    if (r6 == 0) goto L_0x00b8
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x00b8:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x00be:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r6 = r6.userInfo
                    boolean r6 = com.zkteco.android.employeemgmt.util.HasFaceUtils.isHasFace((com.zkteco.android.db.orm.tna.UserInfo) r6)
                    if (r6 == 0) goto L_0x00d6
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x00d6:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x00dc:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r6 = r6.userInfo
                    boolean r6 = com.zkteco.android.employeemgmt.util.HasFaceUtils.isHasFace((com.zkteco.android.db.orm.tna.UserInfo) r6)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x00ee:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.cardExist(r7)
                    if (r6 == 0) goto L_0x0106
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x0106:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x010c:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 == 0) goto L_0x0124
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x0124:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x012a:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 == 0) goto L_0x0142
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.cardExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x0142:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x0148:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 == 0) goto L_0x016c
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 == 0) goto L_0x016c
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.cardExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x016c:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x0172:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.cardExist(r7)
                    if (r6 == 0) goto L_0x018a
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x018a:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x0190:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 == 0) goto L_0x01a8
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                L_0x01a8:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x01ae:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x01c0:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.cardExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x01de:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.cardExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x01fc:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x021a:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.cardExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x022c:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.pswExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x023e:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r7 = r6.userInfo
                    boolean r6 = r6.fingerExist(r7)
                    if (r6 != 0) goto L_0x0250
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.showErrorDialog(r5)
                    return
                L_0x0250:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r5 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r5 = r5.userInfo
                    r5.setVerify_Type(r4)
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r4 = r4.userInfo
                    r5 = 0
                    r4.setSEND_FLAG(r5)
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this     // Catch:{ SQLException -> 0x026d }
                    com.zkteco.android.db.orm.tna.UserInfo r4 = r4.userInfo     // Catch:{ SQLException -> 0x026d }
                    r4.update()     // Catch:{ SQLException -> 0x026d }
                    goto L_0x0271
                L_0x026d:
                    r4 = move-exception
                    r4.printStackTrace()
                L_0x0271:
                    com.zkteco.android.core.sdk.HubProtocolManager r4 = new com.zkteco.android.core.sdk.HubProtocolManager
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r6 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.<init>(r6)
                    r6 = 0
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r8 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this     // Catch:{ SQLException -> 0x02e8, all -> 0x02e5 }
                    com.zkteco.android.db.orm.tna.UserInfo r8 = r8.userInfo     // Catch:{ SQLException -> 0x02e8, all -> 0x02e5 }
                    r8.setSEND_FLAG(r5)     // Catch:{ SQLException -> 0x02e8, all -> 0x02e5 }
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r8 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this     // Catch:{ SQLException -> 0x02e8, all -> 0x02e5 }
                    com.zkteco.android.db.orm.tna.UserInfo r8 = r8.userInfo     // Catch:{ SQLException -> 0x02e8, all -> 0x02e5 }
                    r8.update()     // Catch:{ SQLException -> 0x02e8, all -> 0x02e5 }
                    long r0 = r4.convertPushInit()     // Catch:{ SQLException -> 0x02e8, all -> 0x02e5 }
                    java.lang.String r8 = "USER_INFO"
                    r4.setPushTableName(r0, r8)     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.String r8 = "User_PIN"
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this     // Catch:{ SQLException -> 0x02e3 }
                    com.zkteco.android.db.orm.tna.UserInfo r2 = r2.userInfo     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.String r2 = r2.getUser_PIN()     // Catch:{ SQLException -> 0x02e3 }
                    r4.setPushStrField(r0, r8, r2)     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.String r8 = "SEND_FLAG"
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this     // Catch:{ SQLException -> 0x02e3 }
                    com.zkteco.android.db.orm.tna.UserInfo r2 = r2.userInfo     // Catch:{ SQLException -> 0x02e3 }
                    int r2 = r2.getSEND_FLAG()     // Catch:{ SQLException -> 0x02e3 }
                    r4.setPushIntField(r0, r8, r2)     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x02e3 }
                    r8.<init>()     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.String r2 = "(User_PIN='"
                    java.lang.StringBuilder r8 = r8.append(r2)     // Catch:{ SQLException -> 0x02e3 }
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this     // Catch:{ SQLException -> 0x02e3 }
                    com.zkteco.android.db.orm.tna.UserInfo r2 = r2.userInfo     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.String r2 = r2.getUser_PIN()     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.StringBuilder r8 = r8.append(r2)     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.String r2 = "')"
                    java.lang.StringBuilder r8 = r8.append(r2)     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.String r8 = r8.toString()     // Catch:{ SQLException -> 0x02e3 }
                    r4.setPushCon(r0, r8)     // Catch:{ SQLException -> 0x02e3 }
                    java.lang.String r8 = ""
                    r4.sendHubAction(r5, r0, r8)     // Catch:{ SQLException -> 0x02e3 }
                    int r5 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                    if (r5 == 0) goto L_0x02f4
                    goto L_0x02f1
                L_0x02e3:
                    r5 = move-exception
                    goto L_0x02ea
                L_0x02e5:
                    r5 = move-exception
                    r0 = r6
                    goto L_0x02fb
                L_0x02e8:
                    r5 = move-exception
                    r0 = r6
                L_0x02ea:
                    r5.printStackTrace()     // Catch:{ all -> 0x02fa }
                    int r5 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                    if (r5 == 0) goto L_0x02f4
                L_0x02f1:
                    r4.convertPushFree(r0)
                L_0x02f4:
                    com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity r4 = com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.this
                    r4.finish()
                    return
                L_0x02fa:
                    r5 = move-exception
                L_0x02fb:
                    int r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                    if (r6 == 0) goto L_0x0302
                    r4.convertPushFree(r0)
                L_0x0302:
                    throw r5
                */
                throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.AnonymousClass2.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
            }
        });
    }

    private ArrayList<ZKStaffVerifyBean> createList() {
        this.verifyMap = VerifyTypeUtils.getmVerifyTypeMap();
        List<String> verifyTypeList = VerifyTypeUtils.getVerifyTypeList();
        ArrayList<ZKStaffVerifyBean> arrayList = new ArrayList<>();
        if (this.sAccessRuleType == 1) {
            arrayList.add(new ZKStaffVerifyBean(getResources().getString(R.string.zk_staff_info_null), -1));
        }
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

    /* access modifiers changed from: private */
    public boolean fingerExist(UserInfo userInfo2) {
        List<FpTemplate10> fingerTemplateForUserPin = this.templateManager.getFingerTemplateForUserPin(String.valueOf(userInfo2.getID()));
        return (fingerTemplateForUserPin == null || fingerTemplateForUserPin.size() == 0) ? false : true;
    }

    /* access modifiers changed from: private */
    public boolean pswExist(UserInfo userInfo2) {
        return !TextUtils.isEmpty(userInfo2.getPassword());
    }

    /* access modifiers changed from: private */
    public boolean cardExist(UserInfo userInfo2) {
        return !TextUtils.isEmpty(userInfo2.getMain_Card());
    }

    /* access modifiers changed from: private */
    public void showErrorDialog(final ZKConfirmDialog zKConfirmDialog) {
        final ZKConfirmDialog zKConfirmDialog2 = new ZKConfirmDialog(this);
        zKConfirmDialog2.show();
        zKConfirmDialog2.setType(1, "", "", getString(R.string.zk_staff_ok));
        zKConfirmDialog2.setMessage(getString(R.string.zk_staff_modify_promt_only_change));
        zKConfirmDialog2.setListener(new ZKConfirmDialog.ResultListener() {
            public void cover() {
            }

            public void failure() {
            }

            public void success() {
                zKConfirmDialog2.cancel();
                zKConfirmDialog.cancel();
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isContainsVertifyType(int r9) {
        /*
            r8 = this;
            java.lang.String r0 = ""
            r1 = 0
            com.zkteco.android.db.orm.tna.UserInfo r2 = r8.userInfo     // Catch:{ SQLException -> 0x0057 }
            r2.update()     // Catch:{ SQLException -> 0x0057 }
            com.zkteco.android.db.orm.tna.PersBiotemplate r2 = new com.zkteco.android.db.orm.tna.PersBiotemplate     // Catch:{ SQLException -> 0x0057 }
            r2.<init>()     // Catch:{ SQLException -> 0x0057 }
            com.j256.ormlite.stmt.QueryBuilder r2 = r2.getQueryBuilder()     // Catch:{ SQLException -> 0x0057 }
            com.j256.ormlite.stmt.Where r2 = r2.where()     // Catch:{ SQLException -> 0x0057 }
            java.lang.String r3 = "user_pin"
            com.zkteco.android.db.orm.tna.UserInfo r4 = r8.userInfo     // Catch:{ SQLException -> 0x0057 }
            java.lang.String r4 = r4.getUser_PIN()     // Catch:{ SQLException -> 0x0057 }
            com.j256.ormlite.stmt.Where r2 = r2.eq(r3, r4)     // Catch:{ SQLException -> 0x0057 }
            com.j256.ormlite.stmt.Where r2 = r2.and()     // Catch:{ SQLException -> 0x0057 }
            java.lang.String r3 = "bio_type"
            r4 = 9
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ SQLException -> 0x0057 }
            com.j256.ormlite.stmt.Where r2 = r2.eq(r3, r4)     // Catch:{ SQLException -> 0x0057 }
            java.util.List r2 = r2.query()     // Catch:{ SQLException -> 0x0057 }
            com.zkteco.android.db.orm.manager.template.TemplateManager r3 = r8.templateManager     // Catch:{ SQLException -> 0x0054 }
            com.zkteco.android.db.orm.tna.UserInfo r4 = r8.userInfo     // Catch:{ SQLException -> 0x0054 }
            long r4 = r4.getID()     // Catch:{ SQLException -> 0x0054 }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ SQLException -> 0x0054 }
            java.util.List r1 = r3.getFingerTemplateForUserPin(r4)     // Catch:{ SQLException -> 0x0054 }
            com.zkteco.android.db.orm.tna.UserInfo r3 = r8.userInfo     // Catch:{ SQLException -> 0x0054 }
            java.lang.String r3 = r3.getPassword()     // Catch:{ SQLException -> 0x0054 }
            com.zkteco.android.db.orm.tna.UserInfo r4 = r8.userInfo     // Catch:{ SQLException -> 0x0052 }
            java.lang.String r0 = r4.getMain_Card()     // Catch:{ SQLException -> 0x0052 }
            goto L_0x005d
        L_0x0052:
            r4 = move-exception
            goto L_0x005a
        L_0x0054:
            r4 = move-exception
            r3 = r0
            goto L_0x005a
        L_0x0057:
            r4 = move-exception
            r3 = r0
            r2 = r1
        L_0x005a:
            r4.printStackTrace()
        L_0x005d:
            java.util.List<com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean> r4 = r8.mData
            java.lang.Object r9 = r4.get(r9)
            com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean r9 = (com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean) r9
            java.lang.String r9 = r9.string
            int r4 = r2.size()
            java.lang.String r5 = "/"
            r6 = 1
            r7 = 0
            if (r4 != 0) goto L_0x00b5
            java.lang.String r4 = r8.TYPE_FACE
            boolean r4 = r9.contains(r4)
            if (r4 == 0) goto L_0x00ac
            boolean r4 = r9.contains(r5)
            if (r4 == 0) goto L_0x00ac
            java.lang.String r1 = r8.TYPE_CARD
            boolean r1 = r9.contains(r1)
            if (r1 == 0) goto L_0x008d
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x00ab
        L_0x008d:
            java.lang.String r0 = r8.TYPE_FINGER
            boolean r0 = r9.contains(r0)
            if (r0 == 0) goto L_0x009b
            int r0 = r2.size()
            if (r0 != 0) goto L_0x00ab
        L_0x009b:
            java.lang.String r0 = r8.TYPE_PSW
            boolean r9 = r9.contains(r0)
            if (r9 == 0) goto L_0x00aa
            boolean r9 = android.text.TextUtils.isEmpty(r3)
            if (r9 != 0) goto L_0x00aa
            goto L_0x00ab
        L_0x00aa:
            r6 = r7
        L_0x00ab:
            return r6
        L_0x00ac:
            java.lang.String r4 = r8.TYPE_FACE
            boolean r4 = r9.contains(r4)
            if (r4 == 0) goto L_0x00b5
            return r7
        L_0x00b5:
            if (r1 == 0) goto L_0x00bd
            int r4 = r1.size()
            if (r4 != 0) goto L_0x0101
        L_0x00bd:
            java.lang.String r4 = r8.TYPE_FINGER
            boolean r4 = r9.contains(r4)
            if (r4 == 0) goto L_0x00f8
            boolean r4 = r9.contains(r5)
            if (r4 == 0) goto L_0x00f8
            java.lang.String r1 = r8.TYPE_CARD
            boolean r1 = r9.contains(r1)
            if (r1 == 0) goto L_0x00d9
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x00f7
        L_0x00d9:
            java.lang.String r0 = r8.TYPE_FACE
            boolean r0 = r9.contains(r0)
            if (r0 == 0) goto L_0x00e7
            int r0 = r2.size()
            if (r0 != 0) goto L_0x00f7
        L_0x00e7:
            java.lang.String r0 = r8.TYPE_PSW
            boolean r9 = r9.contains(r0)
            if (r9 == 0) goto L_0x00f6
            boolean r9 = android.text.TextUtils.isEmpty(r3)
            if (r9 != 0) goto L_0x00f6
            goto L_0x00f7
        L_0x00f6:
            r6 = r7
        L_0x00f7:
            return r6
        L_0x00f8:
            java.lang.String r4 = r8.TYPE_FINGER
            boolean r4 = r9.contains(r4)
            if (r4 == 0) goto L_0x0101
            return r7
        L_0x0101:
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 == 0) goto L_0x014d
            java.lang.String r4 = r8.TYPE_PSW
            boolean r4 = r9.contains(r4)
            if (r4 == 0) goto L_0x0144
            boolean r4 = r9.contains(r5)
            if (r4 == 0) goto L_0x0144
            java.lang.String r3 = r8.TYPE_FINGER
            boolean r3 = r9.contains(r3)
            if (r3 == 0) goto L_0x0125
            if (r1 == 0) goto L_0x0125
            int r1 = r1.size()
            if (r1 > 0) goto L_0x0143
        L_0x0125:
            java.lang.String r1 = r8.TYPE_FACE
            boolean r1 = r9.contains(r1)
            if (r1 == 0) goto L_0x0133
            int r1 = r2.size()
            if (r1 != 0) goto L_0x0143
        L_0x0133:
            java.lang.String r1 = r8.TYPE_CARD
            boolean r9 = r9.contains(r1)
            if (r9 == 0) goto L_0x0142
            boolean r9 = android.text.TextUtils.isEmpty(r0)
            if (r9 != 0) goto L_0x0142
            goto L_0x0143
        L_0x0142:
            r6 = r7
        L_0x0143:
            return r6
        L_0x0144:
            java.lang.String r4 = r8.TYPE_PSW
            boolean r4 = r9.contains(r4)
            if (r4 == 0) goto L_0x014d
            return r7
        L_0x014d:
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0199
            java.lang.String r0 = r8.TYPE_CARD
            boolean r0 = r9.contains(r0)
            if (r0 == 0) goto L_0x0190
            boolean r0 = r9.contains(r5)
            if (r0 == 0) goto L_0x0190
            java.lang.String r0 = r8.TYPE_FINGER
            boolean r0 = r9.contains(r0)
            if (r0 == 0) goto L_0x0171
            if (r1 == 0) goto L_0x0171
            int r0 = r1.size()
            if (r0 > 0) goto L_0x018f
        L_0x0171:
            java.lang.String r0 = r8.TYPE_FACE
            boolean r0 = r9.contains(r0)
            if (r0 == 0) goto L_0x017f
            int r0 = r2.size()
            if (r0 != 0) goto L_0x018f
        L_0x017f:
            java.lang.String r0 = r8.TYPE_PSW
            boolean r9 = r9.contains(r0)
            if (r9 == 0) goto L_0x018e
            boolean r9 = android.text.TextUtils.isEmpty(r3)
            if (r9 != 0) goto L_0x018e
            goto L_0x018f
        L_0x018e:
            r6 = r7
        L_0x018f:
            return r6
        L_0x0190:
            java.lang.String r0 = r8.TYPE_CARD
            boolean r9 = r9.contains(r0)
            if (r9 == 0) goto L_0x0199
            return r7
        L_0x0199:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity.isContainsVertifyType(int):boolean");
    }
}
