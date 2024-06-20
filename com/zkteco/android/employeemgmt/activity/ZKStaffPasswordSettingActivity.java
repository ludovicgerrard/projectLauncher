package com.zkteco.android.employeemgmt.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.zkcore.view.ZKToolbar;
import java.sql.SQLException;

public class ZKStaffPasswordSettingActivity extends ZKStaffBaseActivity {
    private final DataManager dataManager = DBManager.getInstance();
    /* access modifiers changed from: private */
    public EditText edConfirmpd;
    /* access modifiers changed from: private */
    public EditText edEnterpd;
    /* access modifiers changed from: private */
    public boolean isShowPwd = false;
    /* access modifiers changed from: private */
    public TextView tvPromt;
    /* access modifiers changed from: private */
    public long userInfo_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_pdsetting);
        initToolBar();
        initView();
        this.userInfo_id = getIntent().getLongExtra("userInfo_id", 0);
        try {
            UserInfo userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(this.userInfo_id));
            if (userInfo != null && userInfo.getPassword() != null) {
                this.edEnterpd.setText(userInfo.getPassword());
                this.edEnterpd.setSelection(userInfo.getPassword().length());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.passwordSettingToolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                ZKStaffPasswordSettingActivity.this.finish();
            }
        }, getString(R.string.zk_staff_pd_title));
        zKToolbar.setRightView(getString(R.string.zk_staff_save), (View.OnClickListener) new View.OnClickListener() {
            /* JADX WARNING: Removed duplicated region for block: B:27:0x00a6 A[SYNTHETIC, Splitter:B:27:0x00a6] */
            /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onClick(android.view.View r9) {
                /*
                    r8 = this;
                    com.zkteco.android.db.orm.tna.UserInfo r9 = new com.zkteco.android.db.orm.tna.UserInfo     // Catch:{ SQLException -> 0x00c6 }
                    r9.<init>()     // Catch:{ SQLException -> 0x00c6 }
                    com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity r0 = com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.this     // Catch:{ SQLException -> 0x00c6 }
                    long r0 = r0.userInfo_id     // Catch:{ SQLException -> 0x00c6 }
                    java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ SQLException -> 0x00c6 }
                    java.lang.Object r9 = r9.queryForId(r0)     // Catch:{ SQLException -> 0x00c6 }
                    com.zkteco.android.db.orm.tna.UserInfo r9 = (com.zkteco.android.db.orm.tna.UserInfo) r9     // Catch:{ SQLException -> 0x00c6 }
                    com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity r0 = com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.this     // Catch:{ SQLException -> 0x00c6 }
                    boolean r0 = r0.checkPassword()     // Catch:{ SQLException -> 0x00c6 }
                    r1 = 0
                    if (r0 == 0) goto L_0x00aa
                    if (r9 == 0) goto L_0x00ca
                    com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity r0 = com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.this     // Catch:{ SQLException -> 0x00c6 }
                    android.widget.EditText r0 = r0.edEnterpd     // Catch:{ SQLException -> 0x00c6 }
                    android.text.Editable r0 = r0.getText()     // Catch:{ SQLException -> 0x00c6 }
                    java.lang.String r0 = r0.toString()     // Catch:{ SQLException -> 0x00c6 }
                    r9.setPassword(r0)     // Catch:{ SQLException -> 0x00c6 }
                    r9.setSEND_FLAG(r1)     // Catch:{ SQLException -> 0x00c6 }
                    r9.update()     // Catch:{ SQLException -> 0x00c6 }
                    com.zkteco.android.core.sdk.HubProtocolManager r0 = new com.zkteco.android.core.sdk.HubProtocolManager     // Catch:{ SQLException -> 0x00c6 }
                    com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity r2 = com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.this     // Catch:{ SQLException -> 0x00c6 }
                    android.content.Context r2 = r2.getApplicationContext()     // Catch:{ SQLException -> 0x00c6 }
                    r0.<init>(r2)     // Catch:{ SQLException -> 0x00c6 }
                    r2 = 0
                    long r4 = r0.convertPushInit()     // Catch:{ Exception -> 0x0098, all -> 0x0095 }
                    java.lang.String r6 = "USER_INFO"
                    r0.setPushTableName(r4, r6)     // Catch:{ Exception -> 0x0093 }
                    java.lang.String r6 = "User_PIN"
                    java.lang.String r7 = r9.getUser_PIN()     // Catch:{ Exception -> 0x0093 }
                    r0.setPushStrField(r4, r6, r7)     // Catch:{ Exception -> 0x0093 }
                    java.lang.String r6 = "SEND_FLAG"
                    int r7 = r9.getSEND_FLAG()     // Catch:{ Exception -> 0x0093 }
                    r0.setPushIntField(r4, r6, r7)     // Catch:{ Exception -> 0x0093 }
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0093 }
                    r6.<init>()     // Catch:{ Exception -> 0x0093 }
                    java.lang.String r7 = "(User_PIN='"
                    java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0093 }
                    java.lang.String r9 = r9.getUser_PIN()     // Catch:{ Exception -> 0x0093 }
                    java.lang.StringBuilder r9 = r6.append(r9)     // Catch:{ Exception -> 0x0093 }
                    java.lang.String r6 = "')"
                    java.lang.StringBuilder r9 = r9.append(r6)     // Catch:{ Exception -> 0x0093 }
                    java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0093 }
                    r0.setPushCon(r4, r9)     // Catch:{ Exception -> 0x0093 }
                    java.lang.String r9 = ""
                    r0.sendHubAction(r1, r4, r9)     // Catch:{ Exception -> 0x0093 }
                    com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity r9 = com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.this     // Catch:{ Exception -> 0x0093 }
                    r9.finish()     // Catch:{ Exception -> 0x0093 }
                    int r9 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
                    if (r9 == 0) goto L_0x00ca
                L_0x008d:
                    r0.convertPushFree(r4)     // Catch:{ SQLException -> 0x00c6 }
                    goto L_0x00ca
                L_0x0091:
                    r9 = move-exception
                    goto L_0x00a2
                L_0x0093:
                    r9 = move-exception
                    goto L_0x009a
                L_0x0095:
                    r9 = move-exception
                    r4 = r2
                    goto L_0x00a2
                L_0x0098:
                    r9 = move-exception
                    r4 = r2
                L_0x009a:
                    r9.printStackTrace()     // Catch:{ all -> 0x0091 }
                    int r9 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
                    if (r9 == 0) goto L_0x00ca
                    goto L_0x008d
                L_0x00a2:
                    int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
                    if (r1 == 0) goto L_0x00a9
                    r0.convertPushFree(r4)     // Catch:{ SQLException -> 0x00c6 }
                L_0x00a9:
                    throw r9     // Catch:{ SQLException -> 0x00c6 }
                L_0x00aa:
                    com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity r9 = com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.this     // Catch:{ SQLException -> 0x00c6 }
                    android.widget.TextView r9 = r9.tvPromt     // Catch:{ SQLException -> 0x00c6 }
                    r9.setVisibility(r1)     // Catch:{ SQLException -> 0x00c6 }
                    com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity r9 = com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.this     // Catch:{ SQLException -> 0x00c6 }
                    android.widget.TextView r9 = r9.tvPromt     // Catch:{ SQLException -> 0x00c6 }
                    com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity r0 = com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.this     // Catch:{ SQLException -> 0x00c6 }
                    r1 = 2130771982(0x7f01000e, float:1.714707E38)
                    android.view.animation.Animation r0 = android.view.animation.AnimationUtils.loadAnimation(r0, r1)     // Catch:{ SQLException -> 0x00c6 }
                    r9.startAnimation(r0)     // Catch:{ SQLException -> 0x00c6 }
                    goto L_0x00ca
                L_0x00c6:
                    r9 = move-exception
                    r9.printStackTrace()
                L_0x00ca:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity.AnonymousClass2.onClick(android.view.View):void");
            }
        });
    }

    private void initView() {
        this.edEnterpd = (EditText) findViewById(R.id.ed_enterpd);
        this.edConfirmpd = (EditText) findViewById(R.id.ed_confirmpd);
        this.edEnterpd.setFocusable(true);
        this.edEnterpd.setFocusableInTouchMode(true);
        this.edEnterpd.setInputType(2);
        this.edConfirmpd.setInputType(2);
        this.edEnterpd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.edConfirmpd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        EditText editText = this.edEnterpd;
        editText.setSelection(editText.getText().toString().trim().length());
        EditText editText2 = this.edConfirmpd;
        editText2.setSelection(editText2.getText().toString().trim().length());
        this.tvPromt = (TextView) findViewById(R.id.tv_prompt);
        hideSoftKeyBoard((RelativeLayout) findViewById(R.id.rel_password));
        this.edEnterpd.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (ZKStaffPasswordSettingActivity.this.tvPromt.getVisibility() == 0) {
                    ZKStaffPasswordSettingActivity.this.tvPromt.setVisibility(4);
                }
            }
        });
        this.edConfirmpd.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (ZKStaffPasswordSettingActivity.this.tvPromt.getVisibility() == 0) {
                    ZKStaffPasswordSettingActivity.this.tvPromt.setVisibility(4);
                }
            }
        });
        final ImageView imageView = (ImageView) findViewById(R.id.iv_pwd);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ZKStaffPasswordSettingActivity.this.isShowPwd) {
                    boolean unused = ZKStaffPasswordSettingActivity.this.isShowPwd = false;
                    imageView.setImageResource(R.mipmap.pwd_hide);
                    ZKStaffPasswordSettingActivity.this.edEnterpd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ZKStaffPasswordSettingActivity.this.edConfirmpd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ZKStaffPasswordSettingActivity.this.edEnterpd.setSelection(ZKStaffPasswordSettingActivity.this.edEnterpd.getText().toString().trim().length());
                    ZKStaffPasswordSettingActivity.this.edConfirmpd.setSelection(ZKStaffPasswordSettingActivity.this.edConfirmpd.getText().toString().trim().length());
                    return;
                }
                boolean unused2 = ZKStaffPasswordSettingActivity.this.isShowPwd = true;
                imageView.setImageResource(R.mipmap.pwd_show);
                ZKStaffPasswordSettingActivity.this.edEnterpd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ZKStaffPasswordSettingActivity.this.edConfirmpd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ZKStaffPasswordSettingActivity.this.edEnterpd.setSelection(ZKStaffPasswordSettingActivity.this.edEnterpd.getText().toString().trim().length());
                ZKStaffPasswordSettingActivity.this.edConfirmpd.setSelection(ZKStaffPasswordSettingActivity.this.edConfirmpd.getText().toString().trim().length());
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean checkPassword() {
        if (TextUtils.isEmpty(this.edEnterpd.getText().toString())) {
            this.tvPromt.setText(getString(R.string.zk_staff_pdenter_notnull));
            return false;
        } else if (TextUtils.isEmpty(this.edConfirmpd.getText().toString())) {
            this.tvPromt.setText(getString(R.string.zk_staff_pdconfirm_notnull));
            return false;
        } else if (!this.edEnterpd.getText().toString().equals(this.edConfirmpd.getText().toString())) {
            this.tvPromt.setText(getString(R.string.zk_staff_pd_unsame));
            return false;
        } else if (this.edEnterpd.getText().toString().length() >= 6) {
            return true;
        } else {
            this.tvPromt.setText(getString(R.string.zk_staff_pd_length));
            return false;
        }
    }
}
