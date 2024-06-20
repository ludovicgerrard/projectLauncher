package com.zkteco.android.employeemgmt.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.guide.guidecore.GuideUsbManager;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.AES256Util;
import com.zktechnology.android.utils.BitmapUtils;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.AccGroup;
import com.zkteco.android.db.orm.tna.AccUserAuthorize;
import com.zkteco.android.db.orm.tna.ExtUser;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.employeemgmt.fragment.ZKStaffModifyStep2Activity;
import com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean;
import com.zkteco.android.employeemgmt.util.HasFaceUtils;
import com.zkteco.android.employeemgmt.util.SQLiteFaceUtils;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.employeemgmt.util.ZkPalmUtils;
import com.zkteco.android.employeemgmt.widget.ZKEditNameDialog;
import com.zkteco.android.employeemgmt.widget.ZKEditPinDialog;
import com.zkteco.android.employeemgmt.widget.ZKProgressBarDialog;
import com.zkteco.android.employeemgmt.widget.ZKSpinerPopupWindow;
import com.zkteco.android.zkcore.utils.FileUtil;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.view.alert.ZKConfirmDialog;
import com.zkteco.android.zkcore.view.alert.ZKEditDialog;
import com.zkteco.edk.finger.lib.ZkFingerprintManager;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bouncycastle.crypto.tls.CipherSuite;

public class ZkAddUserActivity extends ZKStaffBaseActivity implements View.OnClickListener {
    private static final String CARD_ICON = "card_icon";
    private static final String DEFAULT_PHOTO_PATH = ZKFilePath.PICTURE_PATH;
    private static final String FACE_ICON = "face_icon";
    private static final String FINGER_ICON = "finger_icon";
    public static final String OPT_ACC_RULE_TYPE = "AccessRuleType";
    public static final String OPT_LOCK_FUN_ON = "~LockFunOn";
    private static final String PALM_ICON = "palm_icon";
    private static final String PASSWORD_ICON = "password_icon";
    private static final int STAFF_PRI_COM = 0;
    private static final int STAFF_PRI_SUPER = 14;
    /* access modifiers changed from: private */
    public static final String TAG = "ZkAddUserActivity";
    private static final String USER_FINGER_MODULE = "hasFingerModule";
    private static final String USER_FINGE_FUNON = "FingerFunOn";
    private static final String USER_VALID_TIME_FUN = "UserValidTimeFun";
    public static final int VERIFY_TYPE = -1;
    public static boolean isFirstPush = false;
    private static final String keySupportUserValidateDate = "SupportUserValidateDate";
    private static final int withoutCard = 1;
    private static final int withoutPassword = 2;
    AccUserAuthorize accUserAuthorize;
    /* access modifiers changed from: private */
    public String allname = "";
    private View cardLine;
    /* access modifiers changed from: private */
    public ZKProgressBarDialog dialog;
    /* access modifiers changed from: private */
    public ExtUser extUser;
    private View fingerLine;
    /* access modifiers changed from: private */
    public String firstName = "";
    /* access modifiers changed from: private */
    public boolean isFaceNull = true;
    /* access modifiers changed from: private */
    public boolean isFingerNull = true;
    /* access modifiers changed from: private */
    public boolean isPalmNull = true;
    private int isSupportABCPin;
    /* access modifiers changed from: private */
    public boolean ischange = false;
    private ImageView iv_card;
    private ImageView iv_face;
    private ImageView iv_fingerprint;
    private ImageView iv_palm;
    private ImageView iv_password;
    /* access modifiers changed from: private */
    public ImageView iv_pin_edit;
    /* access modifiers changed from: private */
    public String lastName = "";
    private View lineH;
    private View lineVertify;
    private List<ZKStaffVerifyBean> listVerifyList;
    LinearLayout ll_bio_info;
    private int mHasSupportABCPin;
    /* access modifiers changed from: private */
    public List<String> mSpinnerList = new ArrayList();
    private ImageView mStaffIcon;
    /* access modifiers changed from: private */
    public TextView mStaffName;
    /* access modifiers changed from: private */
    public TextView mStaffPri;
    ZKToolbar mToolBar;
    /* access modifiers changed from: private */
    public ZKSpinerPopupWindow<String> mZKSpinerPopupWindow;
    /* access modifiers changed from: private */
    public int nowPos = 0;
    private View palmLine;
    private String pinWidthStr;
    private RelativeLayout relStaffAcc;
    private LinearLayout relStaffCard;
    private LinearLayout relStaffFace;
    private RelativeLayout relStaffFinger;
    private LinearLayout relStaffName;
    private LinearLayout relStaffPassword;
    private RelativeLayout relStaffPri;
    private RelativeLayout relStaffValidate;
    private RelativeLayout relStaffVertify;
    private RelativeLayout relStaffpalm;
    /* access modifiers changed from: private */
    public LinearLayout rel_staff_pin;
    private int sAccessPersonalVerification;
    private TemplateManager templateManager;
    /* access modifiers changed from: private */
    public TextView tvStaffCard;
    private TextView tvStaffFace;
    private TextView tvStaffFinger;
    /* access modifiers changed from: private */
    public TextView tvStaffPassword;
    private TextView tvStaffVertify;
    private TextView tvStaffpalm;
    /* access modifiers changed from: private */
    public TextView tv_staff_pin;
    /* access modifiers changed from: private */
    public UserInfo userInfo;
    UserQueryTask userQueryTask;
    private Map<Integer, String> verifyMap;

    private void showTipDialog(String str) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_user_add);
        initToolBar();
        initView();
        initData();
        initSpinner();
        isFirstPush = true;
    }

    private void initSpinner() {
        if (this.mSpinnerList.size() == 0) {
            this.mSpinnerList.add(getString(R.string.zk_staff_commenuser));
            this.mSpinnerList.add(getString(R.string.zk_staff_superuser));
        }
        ZKSpinerPopupWindow zKSpinerPopupWindow = new ZKSpinerPopupWindow(this, this, this.mSpinnerList, this.nowPos, new AdapterView.OnItemClickListener() {
            /* JADX WARNING: Removed duplicated region for block: B:23:0x00d9 A[SYNTHETIC, Splitter:B:23:0x00d9] */
            /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onItemClick(android.widget.AdapterView<?> r4, android.view.View r5, int r6, long r7) {
                /*
                    r3 = this;
                    r4 = 14
                    r5 = 1065353216(0x3f800000, float:1.0)
                    r7 = 0
                    r8 = 1
                    if (r6 != 0) goto L_0x002f
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r0 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    com.zkteco.android.employeemgmt.widget.ZKSpinerPopupWindow r0 = r0.mZKSpinerPopupWindow
                    r0.setNowPosition(r6)
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r0 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    int unused = r0.nowPos = r6
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r6 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    com.zkteco.android.employeemgmt.widget.ZKSpinerPopupWindow r6 = r6.mZKSpinerPopupWindow
                    r6.setBackgroundAlpha(r5)
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r5 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    com.zkteco.android.employeemgmt.widget.ZKSpinerPopupWindow r5 = r5.mZKSpinerPopupWindow
                    r5.dismiss()
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r5 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    boolean unused = r5.ischange = r8
                    goto L_0x00d0
                L_0x002f:
                    if (r6 != r8) goto L_0x00d0
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r0 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r0 = r0.userInfo
                    java.lang.String r0 = r0.getMain_Card()
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r1 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    boolean r1 = r1.isFaceNull
                    if (r1 == 0) goto L_0x00a9
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r1 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    boolean r1 = r1.isFingerNull
                    if (r1 == 0) goto L_0x00a9
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r1 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    com.zkteco.android.db.orm.tna.UserInfo r1 = r1.userInfo
                    java.lang.String r1 = r1.getPassword()
                    boolean r1 = android.text.TextUtils.isEmpty(r1)
                    if (r1 == 0) goto L_0x00a9
                    java.lang.String r1 = ""
                    if (r0 == 0) goto L_0x006d
                    java.lang.String r2 = "0"
                    boolean r2 = r0.equals(r2)
                    if (r2 != 0) goto L_0x006d
                    boolean r0 = r0.equals(r1)
                    if (r0 == 0) goto L_0x00a9
                L_0x006d:
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r0 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    boolean r0 = r0.isPalmNull
                    if (r0 == 0) goto L_0x00a9
                    com.zkteco.android.zkcore.view.alert.ZKConfirmDialog r5 = new com.zkteco.android.zkcore.view.alert.ZKConfirmDialog
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r6 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    r5.<init>(r6)
                    r5.show()
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r6 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    r0 = 2131755455(0x7f1001bf, float:1.914179E38)
                    java.lang.String r6 = r6.getString(r0)
                    r5.setType(r8, r1, r1, r6)
                    java.lang.String[] r6 = new java.lang.String[r8]
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r0 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    r1 = 2131755444(0x7f1001b4, float:1.9141767E38)
                    java.lang.String r0 = r0.getString(r1)
                    r6[r7] = r0
                    r5.setMessage(r6)
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity$1$1 r6 = new com.zkteco.android.employeemgmt.activity.ZkAddUserActivity$1$1
                    r6.<init>()
                    r5.setListener(r6)
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r5 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    boolean unused = r5.ischange = r7
                    goto L_0x00d0
                L_0x00a9:
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r0 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    com.zkteco.android.employeemgmt.widget.ZKSpinerPopupWindow r0 = r0.mZKSpinerPopupWindow
                    r0.setNowPosition(r6)
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r0 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    int unused = r0.nowPos = r6
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r6 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    com.zkteco.android.employeemgmt.widget.ZKSpinerPopupWindow r6 = r6.mZKSpinerPopupWindow
                    r6.dismiss()
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r6 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    com.zkteco.android.employeemgmt.widget.ZKSpinerPopupWindow r6 = r6.mZKSpinerPopupWindow
                    r6.setBackgroundAlpha(r5)
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r5 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    boolean unused = r5.ischange = r8
                    r5 = r4
                    goto L_0x00d1
                L_0x00d0:
                    r5 = r7
                L_0x00d1:
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r6 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this
                    boolean r6 = r6.ischange
                    if (r6 == 0) goto L_0x0134
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r6 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.db.orm.tna.UserInfo r6 = r6.userInfo     // Catch:{ SQLException -> 0x0130 }
                    r6.setPrivilege(r5)     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r5 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.db.orm.tna.UserInfo r5 = r5.userInfo     // Catch:{ SQLException -> 0x0130 }
                    r5.update()     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r5 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.db.orm.tna.UserInfo r5 = r5.userInfo     // Catch:{ SQLException -> 0x0130 }
                    int r5 = r5.getPrivilege()     // Catch:{ SQLException -> 0x0130 }
                    if (r5 == 0) goto L_0x0115
                    if (r5 == r4) goto L_0x00fa
                    goto L_0x0134
                L_0x00fa:
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r4 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    android.widget.TextView r4 = r4.mStaffPri     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r5 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    java.util.List r5 = r5.mSpinnerList     // Catch:{ SQLException -> 0x0130 }
                    java.lang.Object r5 = r5.get(r8)     // Catch:{ SQLException -> 0x0130 }
                    java.lang.CharSequence r5 = (java.lang.CharSequence) r5     // Catch:{ SQLException -> 0x0130 }
                    r4.setText(r5)     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r4 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    int unused = r4.nowPos = r8     // Catch:{ SQLException -> 0x0130 }
                    goto L_0x0134
                L_0x0115:
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r4 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    android.widget.TextView r4 = r4.mStaffPri     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r5 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    java.util.List r5 = r5.mSpinnerList     // Catch:{ SQLException -> 0x0130 }
                    java.lang.Object r5 = r5.get(r7)     // Catch:{ SQLException -> 0x0130 }
                    java.lang.CharSequence r5 = (java.lang.CharSequence) r5     // Catch:{ SQLException -> 0x0130 }
                    r4.setText(r5)     // Catch:{ SQLException -> 0x0130 }
                    com.zkteco.android.employeemgmt.activity.ZkAddUserActivity r4 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.this     // Catch:{ SQLException -> 0x0130 }
                    int unused = r4.nowPos = r7     // Catch:{ SQLException -> 0x0130 }
                    goto L_0x0134
                L_0x0130:
                    r4 = move-exception
                    r4.printStackTrace()
                L_0x0134:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.AnonymousClass1.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
            }
        });
        this.mZKSpinerPopupWindow = zKSpinerPopupWindow;
        zKSpinerPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                ZkAddUserActivity.this.mZKSpinerPopupWindow.setBackgroundAlpha(1.0f);
            }
        });
    }

    private void initData() {
        VerifyTypeUtils.init(this);
        this.listVerifyList = createList();
        try {
            this.userInfo = new UserInfo();
            this.extUser = new ExtUser();
            UserQueryTask userQueryTask2 = new UserQueryTask();
            this.userQueryTask = userQueryTask2;
            userQueryTask2.execute(new Void[0]);
            this.templateManager = new TemplateManager(this);
            this.sAccessPersonalVerification = DBManager.getInstance().getIntOption("AccessPersonalVerification", 0);
            this.isSupportABCPin = DBManager.getInstance().getIntOption("IsSupportABCPin", 0);
            this.mHasSupportABCPin = DBManager.getInstance().getIntOption("HasSupportABCPin", 0);
            this.pinWidthStr = DBManager.getInstance().getStrOption(ZKDBConfig.OPT_PIN2WIDTH, "9");
            if (DBManager.getInstance() == null) {
                this.relStaffValidate.setVisibility(8);
            } else if (DBManager.getInstance().getStrOption("UserValidTimeFun", "0").equals("0")) {
                this.relStaffValidate.setVisibility(8);
            } else if (DBManager.getInstance().getStrOption("SupportUserValidateDate", "0").equals("1")) {
                this.relStaffValidate.setVisibility(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void initView() {
        this.iv_face = (ImageView) findViewById(R.id.iv_face);
        this.iv_fingerprint = (ImageView) findViewById(R.id.iv_fingerprint);
        this.iv_card = (ImageView) findViewById(R.id.iv_card);
        this.iv_password = (ImageView) findViewById(R.id.iv_password);
        this.iv_palm = (ImageView) findViewById(R.id.iv_palm);
        this.relStaffName = (LinearLayout) findViewById(R.id.rel_staff_name);
        this.mStaffName = (TextView) findViewById(R.id.tv_staff_name);
        this.rel_staff_pin = (LinearLayout) findViewById(R.id.rel_staff_pin);
        this.tv_staff_pin = (TextView) findViewById(R.id.tv_staff_pin);
        this.relStaffPri = (RelativeLayout) findViewById(R.id.rel_staff_authority);
        this.mStaffPri = (TextView) findViewById(R.id.tv_staff_authority);
        this.ll_bio_info = (LinearLayout) findViewById(R.id.ll_bio_info);
        this.iv_pin_edit = (ImageView) findViewById(R.id.iv_pin_edit);
        this.relStaffValidate = (RelativeLayout) findViewById(R.id.rel_staff_validate);
        this.relStaffpalm = (RelativeLayout) findViewById(R.id.rel_staff_palm);
        this.tvStaffpalm = (TextView) findViewById(R.id.tv_staff_palm);
        this.palmLine = findViewById(R.id.palmLine);
        this.relStaffFinger = (RelativeLayout) findViewById(R.id.rel_staff_finger);
        this.tvStaffFinger = (TextView) findViewById(R.id.tv_staff_finger);
        this.fingerLine = findViewById(R.id.fingerLine);
        this.relStaffCard = (LinearLayout) findViewById(R.id.rel_staff_card);
        this.tvStaffCard = (TextView) findViewById(R.id.tv_staff_card);
        this.cardLine = findViewById(R.id.cardLine);
        this.relStaffPassword = (LinearLayout) findViewById(R.id.rel_staff_password);
        this.tvStaffPassword = (TextView) findViewById(R.id.tv_staff_password);
        this.relStaffFace = (LinearLayout) findViewById(R.id.rel_staff_face);
        this.lineH = findViewById(R.id.faceview);
        this.tvStaffFace = (TextView) findViewById(R.id.tv_staff_face);
        this.mStaffIcon = (ImageView) findViewById(R.id.civ_staff_icon);
        this.relStaffAcc = (RelativeLayout) findViewById(R.id.rel_staff_acc);
        this.relStaffVertify = (RelativeLayout) findViewById(R.id.rel_staff_vertify);
        this.lineVertify = findViewById(R.id.verifyview);
        this.tvStaffVertify = (TextView) findViewById(R.id.tv_staff_verify);
        this.relStaffAcc.setOnClickListener(this);
        this.mStaffIcon.setOnClickListener(this);
        this.relStaffValidate.setOnClickListener(this);
        this.relStaffFace.setOnClickListener(this);
        this.relStaffName.setOnClickListener(this);
        this.rel_staff_pin.setOnClickListener(this);
        this.relStaffFinger.setOnClickListener(this);
        this.relStaffpalm.setOnClickListener(this);
        this.relStaffCard.setOnClickListener(this);
        this.relStaffPassword.setOnClickListener(this);
        this.relStaffPri.setOnClickListener(this);
        this.relStaffVertify.setOnClickListener(this);
        this.dialog = new ZKProgressBarDialog.Builder(this).setMessage(getResources().getString(R.string.loading)).setCancelOutside(false).setCancelable(false).create();
    }

    /* access modifiers changed from: protected */
    public void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.staffModifyToolBar);
        this.mToolBar = zKToolbar;
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                if (ZkAddUserActivity.this.tv_staff_pin.getText().toString().isEmpty()) {
                    ZkAddUserActivity.this.finish();
                    return;
                }
                UserInfo access$1100 = ZkAddUserActivity.this.getUserInfo();
                if (access$1100 != null) {
                    try {
                        if (access$1100.getMain_Card() != null) {
                            access$1100.getMain_Card();
                        }
                    } catch (Exception e) {
                        ZkAddUserActivity.this.finish();
                        e.printStackTrace();
                        return;
                    }
                }
                if (!(access$1100 == null || access$1100.getPassword() == null)) {
                    access$1100.getPassword();
                }
                if (ZKLauncher.longName == 1 && ZKLauncher.sAccessRuleType == 1) {
                    ZkAddUserActivity.this.pushExtUser();
                }
                if (ZkAddUserActivity.isFirstPush) {
                    PushUserEvent instance = PushUserEvent.getInstance();
                    ZkAddUserActivity zkAddUserActivity = ZkAddUserActivity.this;
                    instance.pushUser(zkAddUserActivity, zkAddUserActivity.userInfo);
                    ZkAddUserActivity.isFirstPush = false;
                }
                ZkAddUserActivity.this.finish();
            }
        }, getString(R.string.zk_staff_sadd_title));
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pushExtUser() {
        /*
            r8 = this;
            com.zkteco.android.core.sdk.HubProtocolManager r0 = new com.zkteco.android.core.sdk.HubProtocolManager
            r0.<init>(r8)
            r1 = 0
            long r3 = r0.convertPushInit()     // Catch:{ Exception -> 0x004b, all -> 0x0048 }
            java.lang.String r5 = "ExtUser"
            r0.setPushTableName(r3, r5)     // Catch:{ Exception -> 0x0046 }
            java.lang.String r5 = "ID"
            com.zkteco.android.db.orm.tna.ExtUser r6 = r8.extUser     // Catch:{ Exception -> 0x0046 }
            long r6 = r6.getID()     // Catch:{ Exception -> 0x0046 }
            int r6 = (int) r6     // Catch:{ Exception -> 0x0046 }
            r0.setPushIntField(r3, r5, r6)     // Catch:{ Exception -> 0x0046 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0046 }
            r5.<init>()     // Catch:{ Exception -> 0x0046 }
            java.lang.String r6 = "ID= "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0046 }
            com.zkteco.android.db.orm.tna.ExtUser r6 = r8.extUser     // Catch:{ Exception -> 0x0046 }
            long r6 = r6.getID()     // Catch:{ Exception -> 0x0046 }
            int r6 = (int) r6     // Catch:{ Exception -> 0x0046 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0046 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0046 }
            r0.setPushCon(r3, r5)     // Catch:{ Exception -> 0x0046 }
            r5 = 0
            java.lang.String r6 = ""
            r0.sendHubAction(r5, r3, r6)     // Catch:{ Exception -> 0x0046 }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0057
            goto L_0x0054
        L_0x0044:
            r5 = move-exception
            goto L_0x0058
        L_0x0046:
            r5 = move-exception
            goto L_0x004d
        L_0x0048:
            r5 = move-exception
            r3 = r1
            goto L_0x0058
        L_0x004b:
            r5 = move-exception
            r3 = r1
        L_0x004d:
            r5.printStackTrace()     // Catch:{ all -> 0x0044 }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0057
        L_0x0054:
            r0.convertPushFree(r3)
        L_0x0057:
            return
        L_0x0058:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x005f
            r0.convertPushFree(r3)
        L_0x005f:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.pushExtUser():void");
    }

    private void deleteUser() {
        if (this.userInfo == null) {
            finish();
            return;
        }
        File file = new File(ZKFilePath.PICTURE_PATH + this.userInfo.getUser_PIN() + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            ArrayList<FpTemplate10> arrayList = new ArrayList<>();
            if (this.userInfo.getID() == 0) {
                try {
                    List queryForAll = new UserInfo().queryForAll();
                    for (int i = 0; i < queryForAll.size(); i++) {
                        if (((UserInfo) queryForAll.get(i)).getUser_PIN().equals(this.userInfo.getUser_PIN())) {
                            Log.d("del", "deleteItem: getUser_PIN  " + ((UserInfo) queryForAll.get(i)).getUser_PIN());
                            Log.d("del", "deleteItem: getID  " + ((UserInfo) queryForAll.get(i)).getID());
                            arrayList.addAll(this.templateManager.getFingerTemplateForUserPin(String.valueOf(((UserInfo) queryForAll.get(i)).getID())));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                arrayList.addAll(this.templateManager.getFingerTemplateForUserPin(String.valueOf(this.userInfo.getID())));
            }
            for (FpTemplate10 fpTemplate10 : arrayList) {
                fpTemplate10.delete();
                ZkFingerprintManager.getInstance().deleteTemplate(this.userInfo.getID() + "_" + fpTemplate10.getFingerid());
            }
            FileUtil.deleteFile(ZKFilePath.FACE_PATH + this.userInfo.getUser_PIN() + ".jpg", this);
            SQLiteFaceUtils.deleteFaceOrPalmTemplate(this.userInfo.getUser_PIN(), 9);
            SQLiteFaceUtils.deleteFaceOrPalmTemplate(this.userInfo.getUser_PIN(), 8);
            ZKPalmService12.dbDel(this.userInfo.getUser_PIN());
            deleteUserByPIN(this.userInfo.getUser_PIN());
            deleteExtUserByPIN(this.userInfo.getUser_PIN());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        finish();
    }

    private void deleteUserByPIN(String str) {
        try {
            DBManager.getInstance().executeSql("ZKDB.db", "DELETE FROM USER_INFO WHERE User_PIN = ?", new Object[]{str});
            Log.d("deleteUser", "deleteUser: " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteExtUserByPIN(String str) {
        try {
            DBManager.getInstance().executeSql("ZKDB.db", "DELETE FROM ExtUser WHERE User_PIN = ?", new Object[]{str});
            Log.d("deleteUser", "deleteExtUser: " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, ZkAddUserActivity.class));
    }

    public void onClick(View view) {
        Intent intent;
        int id = view.getId();
        if (id != R.id.civ_staff_icon) {
            boolean z = false;
            switch (id) {
                case R.id.rel_staff_acc:
                    if (!checkPinAndName()) {
                        return;
                    }
                    if (ZKLauncher.sAccessRuleType == 1) {
                        Intent intent2 = new Intent(this, ZKStaffProfessionActivity.class);
                        intent2.putExtra("userInfo_id", this.userInfo.getID());
                        startActivity(intent2);
                        return;
                    } else if (ZKLauncher.sLockFunOn == 15) {
                        Intent intent3 = new Intent(this, ZKStaffOfflineActivity.class);
                        intent3.putExtra("userInfo_id", this.userInfo.getID());
                        startActivity(intent3);
                        return;
                    } else {
                        return;
                    }
                case R.id.rel_staff_authority:
                    if (checkPinAndName()) {
                        this.mZKSpinerPopupWindow.setWidth(GuideUsbManager.CONTRAST_MAX);
                        this.mZKSpinerPopupWindow.setBackgroundAlpha(0.4f);
                        this.mZKSpinerPopupWindow.showAtLocation(view, 17, 0, 0);
                        return;
                    }
                    return;
                case R.id.rel_staff_card:
                    if (checkPinAndName()) {
                        String main_Card = this.userInfo.getMain_Card();
                        if (main_Card == null || main_Card.equals("") || main_Card.equals("0")) {
                            Intent intent4 = new Intent(this, ZKStaffCardRegistrationActivity.class);
                            intent4.putExtra("userInfo_id", this.userInfo.getID());
                            startActivity(intent4);
                            return;
                        }
                        final ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(this);
                        zKConfirmDialog.show();
                        zKConfirmDialog.setType(3, getString(R.string.zk_staff_cancel), getString(R.string.zk_staff_delete), getString(R.string.zk_staff_cover));
                        zKConfirmDialog.setMessage(getString(R.string.zk_staff_prompt_card));
                        zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
                            public void failure() {
                                zKConfirmDialog.cancel();
                            }

                            public void cover() {
                                if (ZkAddUserActivity.this.userInfo.getPrivilege() != 14 || ZkAddUserActivity.this.checkVerifyExistWithOut(1)) {
                                    ZkAddUserActivity.this.userInfo.setMain_Card((String) null);
                                    try {
                                        ZkAddUserActivity.this.userInfo.update();
                                        zKConfirmDialog.cancel();
                                        ZkAddUserActivity.this.tvStaffCard.setText(ZkAddUserActivity.this.getString(R.string.zk_staff_info_null));
                                        ZkAddUserActivity.this.setIcon(ZkAddUserActivity.CARD_ICON, false);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ZkAddUserActivity.this.showErrorDialog(zKConfirmDialog);
                                }
                            }

                            public void success() {
                                Intent intent = new Intent(ZkAddUserActivity.this, ZKStaffCardRegistrationActivity.class);
                                intent.putExtra("userInfo_id", ZkAddUserActivity.this.userInfo.getID());
                                ZkAddUserActivity.this.startActivity(intent);
                            }
                        });
                        return;
                    }
                    return;
                case R.id.rel_staff_face:
                    if (checkPinAndName()) {
                        Intent intent5 = new Intent(this, ZKStaffFaceActivity.class);
                        intent5.putExtra("userInfo_id", this.userInfo.getID());
                        if (isHanveFace()) {
                            intent5.putExtra("isReEnrollFace", true);
                            intent5.putExtra("isModify", true);
                            intent5.putExtra("isNewFace", false);
                        } else {
                            intent5.putExtra("isReEnrollFace", false);
                            intent5.putExtra("isModify", false);
                            intent5.putExtra("isNewFace", true);
                        }
                        startActivity(intent5);
                        return;
                    }
                    return;
                case R.id.rel_staff_finger:
                    if (checkPinAndName()) {
                        Intent intent6 = new Intent(this, ZKStaffFingerprintActivity.class);
                        intent6.putExtra("userInfo_id", this.userInfo.getID());
                        intent6.putExtra("action", 2);
                        if (this.userInfo.getPrivilege() == 14 && TextUtils.isEmpty(this.userInfo.getMain_Card()) && TextUtils.isEmpty(this.userInfo.getPassword()) && isHanveFace()) {
                            z = true;
                        }
                        intent6.putExtra("isBothNull", z);
                        startActivity(intent6);
                        return;
                    }
                    return;
                case R.id.rel_staff_name:
                    if (checkPinAndName()) {
                        final ZKEditNameDialog zKEditNameDialog = new ZKEditNameDialog(this);
                        if (ZKLauncher.sAccessRuleType == 0) {
                            if (ZKLauncher.longName == 0) {
                                zKEditNameDialog.setUserName(this.mStaffName.getText().toString());
                            } else {
                                String str = this.allname;
                                if (str == null || !str.contains("&")) {
                                    zKEditNameDialog.setFirsetName(this.allname);
                                } else {
                                    String[] split = this.allname.split("&");
                                    zKEditNameDialog.setFirsetName(split[0]);
                                    zKEditNameDialog.setLastName(split[1]);
                                }
                            }
                        } else if (ZKLauncher.longName == 0) {
                            zKEditNameDialog.setUserName(this.mStaffName.getText().toString());
                        } else {
                            zKEditNameDialog.setFirsetName(this.firstName);
                            zKEditNameDialog.setLastName(this.lastName);
                        }
                        if (isEnglish()) {
                            zKEditNameDialog.setInputType(128);
                        } else {
                            zKEditNameDialog.setInputType(1);
                        }
                        zKEditNameDialog.setOnClickListener(new ZKEditNameDialog.OnClickListener() {
                            public void onClickCancel() {
                                zKEditNameDialog.cancel();
                            }

                            public void onClickOk() {
                                String unused = ZkAddUserActivity.this.firstName = zKEditNameDialog.getFirstName();
                                String unused2 = ZkAddUserActivity.this.lastName = zKEditNameDialog.getLastName();
                                if (ZKLauncher.sAccessRuleType != 0) {
                                    ZkAddUserActivity.this.mStaffName.setText(zKEditNameDialog.getInputText());
                                    String unused3 = ZkAddUserActivity.this.allname = zKEditNameDialog.getInputText();
                                    if (ZkAddUserActivity.this.allname.contains(" ")) {
                                        ZkAddUserActivity zkAddUserActivity = ZkAddUserActivity.this;
                                        String unused4 = zkAddUserActivity.allname = zkAddUserActivity.allname.replace(" ", "&");
                                    }
                                } else if (ZKLauncher.longName != 1) {
                                    String unused5 = ZkAddUserActivity.this.allname = zKEditNameDialog.getInputText();
                                    ZkAddUserActivity.this.mStaffName.setText(ZkAddUserActivity.this.allname);
                                } else if (!ZkAddUserActivity.this.firstName.isEmpty() && !ZkAddUserActivity.this.lastName.isEmpty()) {
                                    String unused6 = ZkAddUserActivity.this.allname = ZkAddUserActivity.this.firstName + "&" + ZkAddUserActivity.this.lastName;
                                    ZkAddUserActivity.this.mStaffName.setText(ZkAddUserActivity.this.firstName + " " + ZkAddUserActivity.this.lastName);
                                } else if (!ZkAddUserActivity.this.firstName.isEmpty() || ZkAddUserActivity.this.lastName.isEmpty()) {
                                    ZkAddUserActivity zkAddUserActivity2 = ZkAddUserActivity.this;
                                    String unused7 = zkAddUserActivity2.allname = zkAddUserActivity2.firstName;
                                    ZkAddUserActivity.this.mStaffName.setText(ZkAddUserActivity.this.firstName);
                                } else {
                                    String unused8 = ZkAddUserActivity.this.allname = "&" + ZkAddUserActivity.this.lastName;
                                    ZkAddUserActivity.this.mStaffName.setText(ZkAddUserActivity.this.lastName);
                                }
                                try {
                                    if (ZKLauncher.longName == 1 && ZKLauncher.sAccessRuleType == 1) {
                                        String charSequence = ZkAddUserActivity.this.tv_staff_pin.getText().toString();
                                        ZkAddUserActivity.this.extUser.setFirstName(ZkAddUserActivity.this.firstName);
                                        ZkAddUserActivity.this.extUser.setLastName(ZkAddUserActivity.this.lastName);
                                        if (charSequence.equals(ZkAddUserActivity.this.extUser.getPin())) {
                                            ZkAddUserActivity.this.extUser.update();
                                        } else {
                                            ZkAddUserActivity.this.extUser.setPin(ZkAddUserActivity.this.tv_staff_pin.getText().toString());
                                            ZkAddUserActivity.this.extUser.create();
                                        }
                                        zKEditNameDialog.cancel();
                                    }
                                    ZkAddUserActivity.this.userInfo.setName(ZkAddUserActivity.this.allname);
                                    ZkAddUserActivity.this.userInfo.update();
                                    zKEditNameDialog.cancel();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        zKEditNameDialog.show();
                        return;
                    }
                    return;
                case R.id.rel_staff_palm:
                    if (checkPinAndName()) {
                        Intent intent7 = new Intent();
                        intent7.setClass(this, ZKStaffPalmActivity.class);
                        intent7.putExtra("userPin", this.userInfo.getUser_PIN());
                        intent7.putExtra("isModify", ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN()));
                        intent7.putExtra("isNewPalm", true);
                        startActivity(intent7);
                        return;
                    }
                    return;
                case R.id.rel_staff_password:
                    if (!checkPinAndName()) {
                        return;
                    }
                    if (!TextUtils.isEmpty(this.userInfo.getPassword())) {
                        final ZKConfirmDialog zKConfirmDialog2 = new ZKConfirmDialog(this);
                        zKConfirmDialog2.show();
                        zKConfirmDialog2.setType(3, getString(R.string.zk_staff_cancel), getString(R.string.zk_staff_delete), getString(R.string.zk_staff_cover));
                        zKConfirmDialog2.setMessage(getString(R.string.zk_staff_prompt_password));
                        zKConfirmDialog2.setListener(new ZKConfirmDialog.ResultListener() {
                            public void failure() {
                            }

                            public void cover() {
                                if (ZkAddUserActivity.this.userInfo.getPrivilege() != 14 || ZkAddUserActivity.this.checkVerifyExistWithOut(2)) {
                                    ZkAddUserActivity.this.userInfo.setPassword("");
                                    try {
                                        ZkAddUserActivity.this.userInfo.setSEND_FLAG(0);
                                        ZkAddUserActivity.this.userInfo.update();
                                        ZkAddUserActivity.this.sendUpdateHub();
                                        zKConfirmDialog2.cancel();
                                        ZkAddUserActivity.this.tvStaffPassword.setText(ZkAddUserActivity.this.getString(R.string.zk_staff_info_null));
                                        ZkAddUserActivity.this.setIcon(ZkAddUserActivity.PASSWORD_ICON, false);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ZkAddUserActivity.this.showErrorDialog(zKConfirmDialog2);
                                }
                            }

                            public void success() {
                                ZkAddUserActivity.this.showPdDialog();
                            }
                        });
                        return;
                    }
                    showPdDialog();
                    return;
                case R.id.rel_staff_pin:
                    showUserPinInputDialog();
                    return;
                case R.id.rel_staff_validate:
                    if (checkPinAndName()) {
                        startActivity(new Intent(this, ZKStaffModifyStep2Activity.class).putExtra("id", this.userInfo.getID()));
                        return;
                    }
                    return;
                case R.id.rel_staff_vertify:
                    if (checkPinAndName()) {
                        if (getResources().getConfiguration().orientation == 2) {
                            intent = new Intent(this, ZKStaffVerifyActivity.class);
                        } else {
                            intent = new Intent(this, ZKStaffVerifyVerActivity.class);
                        }
                        intent.putExtra("userInfo_id", this.userInfo.getID());
                        intent.putExtra("sAccessRuleType", ZKLauncher.sAccessRuleType);
                        intent.putExtra("sLockFunOn", ZKLauncher.sLockFunOn);
                        startActivity(intent);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (checkPinAndName() && "1".equals(ZKLauncher.sPhotoFunOn)) {
            if (new File(DEFAULT_PHOTO_PATH).listFiles().length >= DBManager.getInstance().getIntOption("~MaxUserPhotoCount", 10000)) {
                showToast((Context) this, getString(R.string.zk_staff_max_user_photo));
                return;
            }
            Intent intent8 = new Intent(this, ZKStaffIconCollectionActivity.class);
            if (this.userInfo.getUser_PIN() != null) {
                intent8.putExtra("operate", "modify");
                intent8.putExtra("userpin", this.userInfo.getUser_PIN());
                startActivity(intent8);
            }
        }
    }

    private void showUserPinInputDialog() {
        final ZKEditPinDialog zKEditPinDialog = new ZKEditPinDialog(this);
        zKEditPinDialog.setUsePin(this.mStaffName.getText().toString());
        int parseInt = Integer.parseInt(this.pinWidthStr);
        if (this.mHasSupportABCPin == 1) {
            if (this.isSupportABCPin == 0) {
                zKEditPinDialog.setInputType(2);
            } else {
                zKEditPinDialog.setInputType(CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA);
            }
            zKEditPinDialog.setSupportABC(true);
        } else {
            zKEditPinDialog.setInputType(2);
            zKEditPinDialog.setSupportABC(false);
        }
        zKEditPinDialog.setFilters(new InputFilter[]{new InputFilter.LengthFilter(parseInt)});
        zKEditPinDialog.setOnClickListener(new ZKEditPinDialog.OnClickListener() {
            public void onClickCancel() {
                zKEditPinDialog.cancel();
            }

            public void onClickOk() {
                if (ZkAddUserActivity.this.checkUserPinData(zKEditPinDialog)) {
                    ZkAddUserActivity.this.tv_staff_pin.setText(zKEditPinDialog.getUserPinText());
                    ZkAddUserActivity.this.iv_pin_edit.setImageResource(R.mipmap.ic_unedit);
                    ZkAddUserActivity.this.rel_staff_pin.setOnClickListener((View.OnClickListener) null);
                    try {
                        ZkAddUserActivity.this.userInfo.setUser_PIN(zKEditPinDialog.getUserPinText());
                        if (ZKLauncher.sAccessRuleType == 1) {
                            ZkAddUserActivity.this.userInfo.setVerify_Type(-1);
                            ZkAddUserActivity.this.userInfo.setAcc_Group_ID(0);
                        }
                        ZkAddUserActivity.this.userInfo.createIfNotExists();
                        ZkAddUserActivity.this.tryInitAccessUserAuthorize();
                        ZkAddUserActivity.this.sendUpdateHub();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    zKEditPinDialog.cancel();
                }
            }
        });
        zKEditPinDialog.show();
    }

    private boolean checkPinAndName() {
        if (this.tv_staff_pin.getText().toString().isEmpty()) {
            showToast((Context) this, getString(R.string.zk_staff_pinInfo));
            return false;
        }
        this.userInfo.setUser_PIN(this.tv_staff_pin.getText().toString());
        try {
            this.userInfo.createOrUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            tryInitAccessUserAuthorize();
            return true;
        } catch (SQLException e2) {
            e2.printStackTrace();
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void tryInitAccessUserAuthorize() throws SQLException {
        if (this.accUserAuthorize == null && ZKLauncher.sLockFunOn == 15 && ZKLauncher.sAccessRuleType == 1) {
            AccUserAuthorize accUserAuthorize2 = new AccUserAuthorize();
            this.accUserAuthorize = accUserAuthorize2;
            accUserAuthorize2.setUserPIN(this.userInfo.getUser_PIN());
            this.accUserAuthorize.setAuthorizeDoor(1);
            this.accUserAuthorize.setAuthorizeTimezone(1);
            this.accUserAuthorize.createOrUpdate();
        }
    }

    /* access modifiers changed from: private */
    public void showPdDialog() {
        final ZKEditDialog zKEditDialog = new ZKEditDialog(this);
        zKEditDialog.show();
        zKEditDialog.showEye();
        zKEditDialog.setBtnType(2, getString(R.string.zk_staff_cancel), getString(R.string.zk_staff_ok));
        zKEditDialog.setMessage(getString(R.string.zk_staff_pdInfo));
        zKEditDialog.setContentType(3, "");
        zKEditDialog.setEtTop(2, 8, getString(R.string.zk_staff_vertify_password_port));
        zKEditDialog.setEtConf(2, 8, getString(R.string.zk_staff_vertify_correct_password_port));
        zKEditDialog.setListener(new ZKEditDialog.ResultListener() {
            public void failure() {
            }

            public void success() {
                if (ZkAddUserActivity.this.checkPdData(zKEditDialog)) {
                    ZkAddUserActivity.this.tvStaffPassword.setText(ZkAddUserActivity.this.getString(R.string.zk_staff_password));
                    ZkAddUserActivity.this.userInfo.setPassword(zKEditDialog.getInputText());
                    try {
                        ZkAddUserActivity.this.userInfo.update();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    zKEditDialog.cancel();
                    ZkAddUserActivity.this.displayUserInfo();
                    ZkAddUserActivity.this.sendUpdateHub();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean checkPdData(ZKEditDialog zKEditDialog) {
        if (TextUtils.isEmpty(zKEditDialog.getInputText())) {
            zKEditDialog.setHint(getString(R.string.zk_staff_pdenter_notnull));
            return false;
        } else if (TextUtils.isEmpty(zKEditDialog.getInputTextConf())) {
            zKEditDialog.setHintConf(getString(R.string.zk_staff_pdconfirm_notnull));
            return false;
        } else if (!zKEditDialog.getInputText().equals(zKEditDialog.getInputTextConf())) {
            zKEditDialog.setHintConf(getString(R.string.zk_staff_pd_unsame));
            return false;
        } else if (zKEditDialog.getInputText().length() >= 6) {
            return true;
        } else {
            zKEditDialog.setHintConf(getString(R.string.zk_staff_pd_length));
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        displayUserInfo();
    }

    /* access modifiers changed from: private */
    public UserInfo getUserInfo() {
        UserInfo userInfo2 = this.userInfo;
        if (!(userInfo2 == null || userInfo2.getUser_PIN() == null)) {
            try {
                return (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", this.userInfo.getUser_PIN()).queryForFirst();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:105|106) */
    /* JADX WARNING: Code restructure failed: missing block: B:106:?, code lost:
        r13.mStaffIcon.setImageResource(com.zktechnology.android.launcher.R.mipmap.ic_pic_null);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:105:0x02a8 */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x028c A[Catch:{ Exception -> 0x02a8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x028d A[Catch:{ Exception -> 0x02a8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02b6 A[Catch:{ Exception -> 0x0309 }] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x02d2 A[Catch:{ Exception -> 0x0309 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0169 A[SYNTHETIC, Splitter:B:52:0x0169] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0174 A[Catch:{ Exception -> 0x0309 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x018c A[SYNTHETIC, Splitter:B:58:0x018c] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01bf A[SYNTHETIC, Splitter:B:68:0x01bf] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01ca A[Catch:{ Exception -> 0x0309 }] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01e4 A[Catch:{ Exception -> 0x0309 }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01f0 A[Catch:{ Exception -> 0x0309 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void displayUserInfo() {
        /*
            r13 = this;
            java.lang.String r0 = "FaceFunOn"
            java.lang.String r1 = "hasFingerModule"
            java.lang.String r2 = "FingerFunOn"
            java.lang.String r3 = "0"
            com.zkteco.android.db.orm.tna.UserInfo r4 = r13.userInfo
            if (r4 != 0) goto L_0x000d
            return
        L_0x000d:
            com.zkteco.android.db.orm.tna.UserInfo r4 = r13.getUserInfo()
            boolean r5 = com.zkteco.android.employeemgmt.util.VerifyTypeUtils.enableHaveCard()
            r6 = 8
            r7 = 0
            if (r5 != 0) goto L_0x002a
            android.widget.LinearLayout r5 = r13.relStaffCard
            r5.setVisibility(r6)
            android.view.View r5 = r13.cardLine
            r5.setVisibility(r6)
            android.widget.ImageView r5 = r13.iv_card
            r5.setVisibility(r6)
            goto L_0x0039
        L_0x002a:
            android.widget.LinearLayout r5 = r13.relStaffCard
            r5.setVisibility(r7)
            android.view.View r5 = r13.cardLine
            r5.setVisibility(r7)
            android.widget.ImageView r5 = r13.iv_card
            r5.setVisibility(r7)
        L_0x0039:
            boolean r5 = com.zkteco.android.employeemgmt.util.VerifyTypeUtils.enableFace()
            if (r5 != 0) goto L_0x004f
            android.widget.ImageView r5 = r13.iv_face
            r5.setVisibility(r6)
            android.widget.LinearLayout r5 = r13.relStaffFace
            r5.setVisibility(r6)
            android.view.View r5 = r13.lineH
            r5.setVisibility(r6)
            goto L_0x005e
        L_0x004f:
            android.widget.ImageView r5 = r13.iv_face
            r5.setVisibility(r7)
            android.widget.LinearLayout r5 = r13.relStaffFace
            r5.setVisibility(r7)
            android.view.View r5 = r13.lineH
            r5.setVisibility(r7)
        L_0x005e:
            boolean r5 = com.zkteco.android.employeemgmt.util.VerifyTypeUtils.enableFinger()
            if (r5 != 0) goto L_0x0074
            android.widget.ImageView r5 = r13.iv_fingerprint
            r5.setVisibility(r6)
            android.widget.RelativeLayout r5 = r13.relStaffFinger
            r5.setVisibility(r6)
            android.view.View r5 = r13.fingerLine
            r5.setVisibility(r6)
            goto L_0x0083
        L_0x0074:
            android.widget.ImageView r5 = r13.iv_fingerprint
            r5.setVisibility(r7)
            android.widget.RelativeLayout r5 = r13.relStaffFinger
            r5.setVisibility(r7)
            android.view.View r5 = r13.fingerLine
            r5.setVisibility(r7)
        L_0x0083:
            boolean r5 = com.zkteco.android.employeemgmt.util.VerifyTypeUtils.enablePv()
            if (r5 != 0) goto L_0x0099
            android.widget.ImageView r5 = r13.iv_palm
            r5.setVisibility(r6)
            android.widget.RelativeLayout r5 = r13.relStaffpalm
            r5.setVisibility(r6)
            android.view.View r5 = r13.palmLine
            r5.setVisibility(r6)
            goto L_0x00a8
        L_0x0099:
            android.widget.ImageView r5 = r13.iv_palm
            r5.setVisibility(r7)
            android.widget.RelativeLayout r5 = r13.relStaffpalm
            r5.setVisibility(r7)
            android.view.View r5 = r13.palmLine
            r5.setVisibility(r7)
        L_0x00a8:
            com.zkteco.android.db.orm.manager.DataManager r5 = com.zktechnology.android.utils.DBManager.getInstance()
            r8 = 1
            if (r5 == 0) goto L_0x030d
            com.zkteco.android.db.orm.tna.UserInfo r5 = r13.userInfo
            java.lang.String r5 = r5.getUser_PIN()
            if (r5 == 0) goto L_0x030d
            com.zkteco.android.db.orm.manager.DataManager r5 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0309 }
            java.lang.String r5 = r5.getStrOption(r2, r3)     // Catch:{ Exception -> 0x0309 }
            boolean r5 = r5.equals(r3)     // Catch:{ Exception -> 0x0309 }
            java.lang.String r9 = "1"
            r10 = 2131755433(0x7f1001a9, float:1.9141745E38)
            if (r5 != 0) goto L_0x014e
            com.zkteco.android.db.orm.manager.DataManager r5 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0309 }
            java.lang.String r5 = r5.getStrOption(r1, r3)     // Catch:{ Exception -> 0x0309 }
            boolean r5 = r5.equals(r3)     // Catch:{ Exception -> 0x0309 }
            if (r5 == 0) goto L_0x00da
            goto L_0x014e
        L_0x00da:
            com.zkteco.android.db.orm.manager.DataManager r5 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0309 }
            java.lang.String r2 = r5.getStrOption(r2, r3)     // Catch:{ Exception -> 0x0309 }
            boolean r2 = r2.equals(r9)     // Catch:{ Exception -> 0x0309 }
            if (r2 == 0) goto L_0x0158
            com.zkteco.android.db.orm.manager.DataManager r2 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0309 }
            java.lang.String r1 = r2.getStrOption(r1, r3)     // Catch:{ Exception -> 0x0309 }
            boolean r1 = r1.equals(r9)     // Catch:{ Exception -> 0x0309 }
            if (r1 == 0) goto L_0x0158
            android.widget.RelativeLayout r1 = r13.relStaffFinger     // Catch:{ Exception -> 0x0309 }
            r1.setVisibility(r7)     // Catch:{ Exception -> 0x0309 }
            android.view.View r1 = r13.fingerLine     // Catch:{ Exception -> 0x0309 }
            r1.setVisibility(r7)     // Catch:{ Exception -> 0x0309 }
            com.zkteco.android.db.orm.manager.template.TemplateManager r1 = r13.templateManager     // Catch:{ Exception -> 0x0309 }
            long r11 = r4.getID()     // Catch:{ Exception -> 0x0309 }
            java.lang.String r2 = java.lang.String.valueOf(r11)     // Catch:{ Exception -> 0x0309 }
            java.util.List r1 = r1.getFingerTemplateForUserPin(r2)     // Catch:{ Exception -> 0x0309 }
            int r2 = r1.size()     // Catch:{ Exception -> 0x0309 }
            if (r2 > 0) goto L_0x0116
            r2 = r8
            goto L_0x0117
        L_0x0116:
            r2 = r7
        L_0x0117:
            r13.isFingerNull = r2     // Catch:{ Exception -> 0x0309 }
            if (r2 != 0) goto L_0x0138
            android.widget.TextView r2 = r13.tvStaffFinger     // Catch:{ Exception -> 0x0309 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0309 }
            r5.<init>()     // Catch:{ Exception -> 0x0309 }
            int r1 = r1.size()     // Catch:{ Exception -> 0x0309 }
            java.lang.StringBuilder r1 = r5.append(r1)     // Catch:{ Exception -> 0x0309 }
            java.lang.String r5 = "/10"
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ Exception -> 0x0309 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0309 }
            r2.setText(r1)     // Catch:{ Exception -> 0x0309 }
            goto L_0x0141
        L_0x0138:
            android.widget.TextView r1 = r13.tvStaffFinger     // Catch:{ Exception -> 0x0309 }
            java.lang.String r2 = r13.getString(r10)     // Catch:{ Exception -> 0x0309 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0309 }
        L_0x0141:
            java.lang.String r1 = "finger_icon"
            boolean r2 = r13.isFingerNull     // Catch:{ Exception -> 0x0309 }
            if (r2 != 0) goto L_0x0149
            r2 = r8
            goto L_0x014a
        L_0x0149:
            r2 = r7
        L_0x014a:
            r13.setIcon(r1, r2)     // Catch:{ Exception -> 0x0309 }
            goto L_0x0158
        L_0x014e:
            android.widget.RelativeLayout r1 = r13.relStaffFinger     // Catch:{ Exception -> 0x0309 }
            r1.setVisibility(r6)     // Catch:{ Exception -> 0x0309 }
            android.view.View r1 = r13.fingerLine     // Catch:{ Exception -> 0x0309 }
            r1.setVisibility(r6)     // Catch:{ Exception -> 0x0309 }
        L_0x0158:
            com.zkteco.android.db.orm.tna.UserInfo r1 = r13.userInfo     // Catch:{ Exception -> 0x0309 }
            java.lang.String r1 = r1.getUser_PIN()     // Catch:{ Exception -> 0x0309 }
            boolean r1 = com.zkteco.android.employeemgmt.util.ZkPalmUtils.isHasPv(r1)     // Catch:{ Exception -> 0x0309 }
            java.lang.String r2 = "palm_icon"
            r5 = 2131755429(0x7f1001a5, float:1.9141737E38)
            if (r1 == 0) goto L_0x0174
            android.widget.TextView r1 = r13.tvStaffpalm     // Catch:{ Exception -> 0x0309 }
            r1.setText(r5)     // Catch:{ Exception -> 0x0309 }
            r13.isPalmNull = r7     // Catch:{ Exception -> 0x0309 }
            r13.setIcon(r2, r8)     // Catch:{ Exception -> 0x0309 }
            goto L_0x0182
        L_0x0174:
            android.widget.TextView r1 = r13.tvStaffpalm     // Catch:{ Exception -> 0x0309 }
            java.lang.String r11 = r13.getString(r10)     // Catch:{ Exception -> 0x0309 }
            r1.setText(r11)     // Catch:{ Exception -> 0x0309 }
            r13.isPalmNull = r8     // Catch:{ Exception -> 0x0309 }
            r13.setIcon(r2, r7)     // Catch:{ Exception -> 0x0309 }
        L_0x0182:
            java.lang.String r1 = r4.getMain_Card()     // Catch:{ Exception -> 0x0309 }
            java.lang.String r2 = "card_icon"
            java.lang.String r11 = ""
            if (r1 == 0) goto L_0x01a6
            boolean r12 = r1.equals(r3)     // Catch:{ Exception -> 0x0309 }
            if (r12 != 0) goto L_0x01a6
            boolean r12 = r1.equals(r11)     // Catch:{ Exception -> 0x0309 }
            if (r12 != 0) goto L_0x01a6
            android.widget.TextView r11 = r13.tvStaffCard     // Catch:{ Exception -> 0x0309 }
            r11.setText(r1)     // Catch:{ Exception -> 0x0309 }
            com.zkteco.android.db.orm.tna.UserInfo r11 = r13.userInfo     // Catch:{ Exception -> 0x0309 }
            r11.setMain_Card(r1)     // Catch:{ Exception -> 0x0309 }
            r13.setIcon(r2, r8)     // Catch:{ Exception -> 0x0309 }
            goto L_0x01b7
        L_0x01a6:
            android.widget.TextView r1 = r13.tvStaffCard     // Catch:{ Exception -> 0x0309 }
            java.lang.String r12 = r13.getString(r10)     // Catch:{ Exception -> 0x0309 }
            r1.setText(r12)     // Catch:{ Exception -> 0x0309 }
            com.zkteco.android.db.orm.tna.UserInfo r1 = r13.userInfo     // Catch:{ Exception -> 0x0309 }
            r1.setMain_Card(r11)     // Catch:{ Exception -> 0x0309 }
            r13.setIcon(r2, r7)     // Catch:{ Exception -> 0x0309 }
        L_0x01b7:
            boolean r1 = r13.pswExist(r4)     // Catch:{ Exception -> 0x0309 }
            java.lang.String r2 = "password_icon"
            if (r1 == 0) goto L_0x01ca
            android.widget.TextView r1 = r13.tvStaffPassword     // Catch:{ Exception -> 0x0309 }
            java.lang.String r11 = "******"
            r1.setText(r11)     // Catch:{ Exception -> 0x0309 }
            r13.setIcon(r2, r8)     // Catch:{ Exception -> 0x0309 }
            goto L_0x01d6
        L_0x01ca:
            android.widget.TextView r1 = r13.tvStaffPassword     // Catch:{ Exception -> 0x0309 }
            java.lang.String r11 = r13.getString(r10)     // Catch:{ Exception -> 0x0309 }
            r1.setText(r11)     // Catch:{ Exception -> 0x0309 }
            r13.setIcon(r2, r7)     // Catch:{ Exception -> 0x0309 }
        L_0x01d6:
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0309 }
            java.lang.String r1 = r1.getStrOption(r0, r3)     // Catch:{ Exception -> 0x0309 }
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0309 }
            if (r1 == 0) goto L_0x01f0
            android.widget.LinearLayout r0 = r13.relStaffFace     // Catch:{ Exception -> 0x0309 }
            r0.setVisibility(r6)     // Catch:{ Exception -> 0x0309 }
            android.view.View r0 = r13.lineH     // Catch:{ Exception -> 0x0309 }
            r0.setVisibility(r6)     // Catch:{ Exception -> 0x0309 }
            goto L_0x0260
        L_0x01f0:
            com.zkteco.android.db.orm.manager.DataManager r1 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ Exception -> 0x0309 }
            java.lang.String r0 = r1.getStrOption(r0, r3)     // Catch:{ Exception -> 0x0309 }
            boolean r0 = r0.equals(r9)     // Catch:{ Exception -> 0x0309 }
            if (r0 == 0) goto L_0x0260
            android.widget.LinearLayout r0 = r13.relStaffFace     // Catch:{ Exception -> 0x0309 }
            r0.setVisibility(r7)     // Catch:{ Exception -> 0x0309 }
            android.view.View r0 = r13.lineH     // Catch:{ Exception -> 0x0309 }
            r0.setVisibility(r7)     // Catch:{ Exception -> 0x0309 }
            com.zkteco.android.db.orm.tna.PersBiotemplate r0 = new com.zkteco.android.db.orm.tna.PersBiotemplate     // Catch:{ Exception -> 0x0309 }
            r0.<init>()     // Catch:{ Exception -> 0x0309 }
            com.j256.ormlite.stmt.QueryBuilder r0 = r0.getQueryBuilder()     // Catch:{ SQLException -> 0x0241 }
            com.j256.ormlite.stmt.Where r0 = r0.where()     // Catch:{ SQLException -> 0x0241 }
            java.lang.String r1 = "user_pin"
            com.zkteco.android.db.orm.tna.UserInfo r2 = r13.userInfo     // Catch:{ SQLException -> 0x0241 }
            java.lang.String r2 = r2.getUser_PIN()     // Catch:{ SQLException -> 0x0241 }
            com.j256.ormlite.stmt.Where r0 = r0.eq(r1, r2)     // Catch:{ SQLException -> 0x0241 }
            com.j256.ormlite.stmt.Where r0 = r0.and()     // Catch:{ SQLException -> 0x0241 }
            java.lang.String r1 = "bio_type"
            r2 = 9
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ SQLException -> 0x0241 }
            com.j256.ormlite.stmt.Where r0 = r0.eq(r1, r2)     // Catch:{ SQLException -> 0x0241 }
            java.util.List r0 = r0.query()     // Catch:{ SQLException -> 0x0241 }
            int r0 = r0.size()     // Catch:{ SQLException -> 0x0241 }
            if (r0 > 0) goto L_0x023d
            r0 = r8
            goto L_0x023e
        L_0x023d:
            r0 = r7
        L_0x023e:
            r13.isFaceNull = r0     // Catch:{ SQLException -> 0x0241 }
            goto L_0x0245
        L_0x0241:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0309 }
        L_0x0245:
            com.zkteco.android.db.orm.tna.UserInfo r0 = r13.userInfo     // Catch:{ Exception -> 0x0309 }
            boolean r0 = com.zkteco.android.employeemgmt.util.HasFaceUtils.isHasFace((com.zkteco.android.db.orm.tna.UserInfo) r0)     // Catch:{ Exception -> 0x0309 }
            java.lang.String r1 = "face_icon"
            if (r0 == 0) goto L_0x0258
            android.widget.TextView r0 = r13.tvStaffFace     // Catch:{ Exception -> 0x0309 }
            r0.setText(r5)     // Catch:{ Exception -> 0x0309 }
            r13.setIcon(r1, r8)     // Catch:{ Exception -> 0x0309 }
            goto L_0x0260
        L_0x0258:
            android.widget.TextView r0 = r13.tvStaffFace     // Catch:{ Exception -> 0x0309 }
            r0.setText(r10)     // Catch:{ Exception -> 0x0309 }
            r13.setIcon(r1, r7)     // Catch:{ Exception -> 0x0309 }
        L_0x0260:
            r0 = 2131558507(0x7f0d006b, float:1.8742332E38)
            com.zkteco.android.db.orm.tna.UserInfo r1 = r13.userInfo     // Catch:{ Exception -> 0x02a8 }
            java.lang.String r1 = r1.getUser_PIN()     // Catch:{ Exception -> 0x02a8 }
            if (r1 == 0) goto L_0x02a2
            com.zkteco.android.db.orm.tna.UserInfo r1 = r13.userInfo     // Catch:{ Exception -> 0x02a8 }
            java.lang.String r1 = r1.getUser_PIN()     // Catch:{ Exception -> 0x02a8 }
            byte[] r1 = com.zktechnology.android.utils.BitmapUtils.getUserPhoto(r1)     // Catch:{ Exception -> 0x02a8 }
            if (r1 == 0) goto L_0x02a2
            com.zkteco.android.db.orm.tna.UserInfo r1 = r13.userInfo     // Catch:{ Exception -> 0x02a8 }
            java.lang.String r1 = r1.getUser_PIN()     // Catch:{ Exception -> 0x02a8 }
            byte[] r1 = com.zktechnology.android.utils.BitmapUtils.getUserPhoto(r1)     // Catch:{ Exception -> 0x02a8 }
            int r1 = r1.length     // Catch:{ Exception -> 0x02a8 }
            if (r1 == 0) goto L_0x02a2
            java.lang.String r1 = com.zktechnology.android.launcher2.ZKLauncher.sPhotoFunOn     // Catch:{ Exception -> 0x02a8 }
            boolean r1 = r3.equals(r1)     // Catch:{ Exception -> 0x02a8 }
            if (r1 == 0) goto L_0x028d
            goto L_0x02a2
        L_0x028d:
            com.zkteco.android.db.orm.tna.UserInfo r1 = r13.userInfo     // Catch:{ Exception -> 0x02a8 }
            java.lang.String r1 = r1.getUser_PIN()     // Catch:{ Exception -> 0x02a8 }
            byte[] r1 = com.zktechnology.android.utils.BitmapUtils.getUserPhoto(r1)     // Catch:{ Exception -> 0x02a8 }
            int r2 = r1.length     // Catch:{ Exception -> 0x02a8 }
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeByteArray(r1, r7, r2)     // Catch:{ Exception -> 0x02a8 }
            android.widget.ImageView r2 = r13.mStaffIcon     // Catch:{ Exception -> 0x02a8 }
            r2.setImageBitmap(r1)     // Catch:{ Exception -> 0x02a8 }
            goto L_0x02ad
        L_0x02a2:
            android.widget.ImageView r1 = r13.mStaffIcon     // Catch:{ Exception -> 0x02a8 }
            r1.setImageResource(r0)     // Catch:{ Exception -> 0x02a8 }
            goto L_0x02ad
        L_0x02a8:
            android.widget.ImageView r1 = r13.mStaffIcon     // Catch:{ Exception -> 0x0309 }
            r1.setImageResource(r0)     // Catch:{ Exception -> 0x0309 }
        L_0x02ad:
            r13.userInfo = r4     // Catch:{ Exception -> 0x0309 }
            r0 = -1
            int r1 = r4.getVerify_Type()     // Catch:{ Exception -> 0x0309 }
            if (r0 != r1) goto L_0x02d2
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sAccessRuleType     // Catch:{ Exception -> 0x0309 }
            if (r0 != r8) goto L_0x02c0
            android.widget.TextView r0 = r13.tvStaffVertify     // Catch:{ Exception -> 0x0309 }
            r0.setText(r10)     // Catch:{ Exception -> 0x0309 }
            goto L_0x0303
        L_0x02c0:
            android.widget.TextView r0 = r13.tvStaffVertify     // Catch:{ Exception -> 0x0309 }
            java.util.List<com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean> r1 = r13.listVerifyList     // Catch:{ Exception -> 0x0309 }
            java.lang.Object r1 = r1.get(r7)     // Catch:{ Exception -> 0x0309 }
            com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean r1 = (com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean) r1     // Catch:{ Exception -> 0x0309 }
            java.lang.String r1 = r1.getString()     // Catch:{ Exception -> 0x0309 }
            r0.setText(r1)     // Catch:{ Exception -> 0x0309 }
            goto L_0x0303
        L_0x02d2:
            r0 = r7
        L_0x02d3:
            java.util.List<com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean> r1 = r13.listVerifyList     // Catch:{ Exception -> 0x0309 }
            int r1 = r1.size()     // Catch:{ Exception -> 0x0309 }
            if (r0 >= r1) goto L_0x0303
            java.util.List<com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean> r1 = r13.listVerifyList     // Catch:{ Exception -> 0x0309 }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ Exception -> 0x0309 }
            com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean r1 = (com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean) r1     // Catch:{ Exception -> 0x0309 }
            int r1 = r1.getValue()     // Catch:{ Exception -> 0x0309 }
            com.zkteco.android.db.orm.tna.UserInfo r2 = r13.userInfo     // Catch:{ Exception -> 0x0309 }
            int r2 = r2.getVerify_Type()     // Catch:{ Exception -> 0x0309 }
            if (r1 != r2) goto L_0x0300
            android.widget.TextView r1 = r13.tvStaffVertify     // Catch:{ Exception -> 0x0309 }
            java.util.List<com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean> r2 = r13.listVerifyList     // Catch:{ Exception -> 0x0309 }
            java.lang.Object r2 = r2.get(r0)     // Catch:{ Exception -> 0x0309 }
            com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean r2 = (com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean) r2     // Catch:{ Exception -> 0x0309 }
            java.lang.String r2 = r2.getString()     // Catch:{ Exception -> 0x0309 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0309 }
        L_0x0300:
            int r0 = r0 + 1
            goto L_0x02d3
        L_0x0303:
            com.zkteco.android.db.orm.tna.UserInfo r0 = r13.userInfo     // Catch:{ Exception -> 0x0309 }
            r0.update()     // Catch:{ Exception -> 0x0309 }
            goto L_0x030d
        L_0x0309:
            r0 = move-exception
            r0.printStackTrace()
        L_0x030d:
            boolean r0 = r13.checkVerifyExistWithOut(r7)
            if (r0 == 0) goto L_0x0358
            android.widget.TextView r0 = r13.tvStaffFace
            android.content.res.Resources r1 = r13.getResources()
            r2 = 2131099717(0x7f060045, float:1.7811795E38)
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            android.widget.TextView r0 = r13.tvStaffFinger
            android.content.res.Resources r1 = r13.getResources()
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            android.widget.TextView r0 = r13.tvStaffPassword
            android.content.res.Resources r1 = r13.getResources()
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            android.widget.TextView r0 = r13.tvStaffCard
            android.content.res.Resources r1 = r13.getResources()
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            android.widget.TextView r0 = r13.tvStaffpalm
            android.content.res.Resources r1 = r13.getResources()
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            goto L_0x039c
        L_0x0358:
            android.widget.TextView r0 = r13.tvStaffFace
            android.content.res.Resources r1 = r13.getResources()
            r2 = 2131099924(0x7f060114, float:1.7812215E38)
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            android.widget.TextView r0 = r13.tvStaffFinger
            android.content.res.Resources r1 = r13.getResources()
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            android.widget.TextView r0 = r13.tvStaffPassword
            android.content.res.Resources r1 = r13.getResources()
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            android.widget.TextView r0 = r13.tvStaffCard
            android.content.res.Resources r1 = r13.getResources()
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
            android.widget.TextView r0 = r13.tvStaffpalm
            android.content.res.Resources r1 = r13.getResources()
            int r1 = r1.getColor(r2)
            r0.setTextColor(r1)
        L_0x039c:
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sAccessRuleType
            if (r0 != r8) goto L_0x03b4
            android.widget.RelativeLayout r0 = r13.relStaffAcc
            r0.setVisibility(r7)
            int r0 = r13.sAccessPersonalVerification
            if (r0 != 0) goto L_0x03c5
            android.widget.RelativeLayout r0 = r13.relStaffVertify
            r0.setVisibility(r6)
            android.view.View r0 = r13.lineVertify
            r0.setVisibility(r6)
            goto L_0x03c5
        L_0x03b4:
            int r0 = com.zktechnology.android.launcher2.ZKLauncher.sLockFunOn
            r1 = 15
            if (r0 != r1) goto L_0x03c0
            android.widget.RelativeLayout r0 = r13.relStaffAcc
            r0.setVisibility(r7)
            goto L_0x03c5
        L_0x03c0:
            android.widget.RelativeLayout r0 = r13.relStaffAcc
            r0.setVisibility(r6)
        L_0x03c5:
            r13.judegeVerifyType()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.displayUserInfo():void");
    }

    private boolean isEnglish() {
        String language = getResources().getConfiguration().locale.getLanguage();
        return language.endsWith("en") || language.endsWith("es") || language.equals("ru");
    }

    private boolean checkNameData(ZKEditNameDialog zKEditNameDialog) {
        if (ZKLauncher.longName == 1) {
            if (TextUtils.isEmpty(zKEditNameDialog.getFirstName().replace(" ", ""))) {
                zKEditNameDialog.setHint(getString(R.string.no_first_name));
                return false;
            } else if (TextUtils.isEmpty(zKEditNameDialog.getLastName().replace(" ", ""))) {
                zKEditNameDialog.setLastHint(getString(R.string.no_last_name));
                return false;
            }
        } else if (TextUtils.isEmpty(zKEditNameDialog.getFirstName().replace(" ", ""))) {
            zKEditNameDialog.setHint(getString(R.string.zk_staff_sadd_plename));
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean checkUserPinData(ZKEditPinDialog zKEditPinDialog) {
        if (TextUtils.isEmpty(zKEditPinDialog.getUserPin().replace(" ", ""))) {
            zKEditPinDialog.setHint(getString(R.string.zk_staff_sadd_plepin));
            return false;
        } else if (zKEditPinDialog.getUserPinText().charAt(0) == '0') {
            zKEditPinDialog.setHint(getString(R.string.zk_staff_sadd_pinfirst_notzero));
            return false;
        } else if (!this.userQueryTask.isRunning()) {
            List<UserInfo> list = this.userQueryTask.getList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUser_PIN().equals(zKEditPinDialog.getUserPinText())) {
                    zKEditPinDialog.setHint(getString(R.string.zk_staff_sadd_pinpromt));
                    return false;
                }
            }
            return true;
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (!ZkAddUserActivity.this.dialog.isShowing()) {
                        ZkAddUserActivity.this.dialog.show();
                    }
                }
            });
            return false;
        }
    }

    private boolean isHanveFace() {
        UserInfo userInfo2 = this.userInfo;
        if (!(userInfo2 == null || userInfo2.getUser_PIN() == null)) {
            try {
                List query = new PersBiotemplate().getQueryBuilder().where().eq("user_pin", this.userInfo.getUser_PIN()).and().eq("bio_type", 9).query();
                if (query == null || query.size() <= 0) {
                    return false;
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static byte[] getUserPhoto(String str) {
        File file = new File(ZKFilePath.PICTURE_PATH + str + ZKFilePath.SUFFIX_IMAGE);
        if (!file.exists()) {
            return null;
        }
        int length = (int) file.length();
        byte[] bArr = new byte[length];
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(bArr, 0, length);
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return ZKLauncher.zkDataEncdec == 1 ? AES256Util.decrypt(bArr, ZKLauncher.PUBLIC_KEY, ZKLauncher.iv) : bArr;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        byte[] userPhoto = BitmapUtils.getUserPhoto(intent.getStringExtra("UserPin"));
        this.mStaffIcon.setImageBitmap(BitmapFactory.decodeByteArray(userPhoto, 0, userPhoto.length));
    }

    /* access modifiers changed from: private */
    public void setIcon(String str, boolean z) {
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1771794149:
                if (str.equals(FACE_ICON)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1391907953:
                if (str.equals(FINGER_ICON)) {
                    c2 = 1;
                    break;
                }
                break;
            case -245373880:
                if (str.equals(CARD_ICON)) {
                    c2 = 2;
                    break;
                }
                break;
            case 409314205:
                if (str.equals(PASSWORD_ICON)) {
                    c2 = 3;
                    break;
                }
                break;
            case 1255129446:
                if (str.equals(PALM_ICON)) {
                    c2 = 4;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                if (z) {
                    this.iv_face.setImageResource(R.mipmap.ic_face_show);
                    return;
                } else {
                    this.iv_face.setImageResource(R.mipmap.ic_face);
                    return;
                }
            case 1:
                if (z) {
                    this.iv_fingerprint.setImageResource(R.mipmap.ic_fingerprint_show);
                    return;
                } else {
                    this.iv_fingerprint.setImageResource(R.mipmap.ic_fingerprint);
                    return;
                }
            case 2:
                if (z) {
                    this.iv_card.setImageResource(R.mipmap.ic_card_show);
                    return;
                } else {
                    this.iv_card.setImageResource(R.mipmap.ic_card);
                    return;
                }
            case 3:
                if (z) {
                    this.iv_password.setImageResource(R.mipmap.ic_password_show);
                    return;
                } else {
                    this.iv_password.setImageResource(R.mipmap.ic_password);
                    return;
                }
            case 4:
                if (z) {
                    this.iv_palm.setImageResource(R.mipmap.palml);
                    return;
                } else {
                    this.iv_palm.setImageResource(R.mipmap.palmg);
                    return;
                }
            default:
                return;
        }
    }

    public static class PushUserEvent {
        private static PushUserEvent instance;

        public static PushUserEvent getInstance() {
            if (instance == null) {
                instance = new PushUserEvent();
            }
            return instance;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x00ba, code lost:
            if (r6 != 0) goto L_0x00cf;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x00cd, code lost:
            if (r6 != 0) goto L_0x00cf;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x00cf, code lost:
            r2.convertPushFree(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00de, code lost:
            if (com.zkteco.android.zkcore.utils.FileUtil.isUserPhotoExist(com.zkteco.android.zkcore.utils.ZKFilePath.PICTURE_PATH, r13.getUser_PIN(), (android.app.Activity) r12) == false) goto L_0x012e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r6 = r2.convertPushInit();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
            r2.setPushTableName(r6, "USER_PHOTO_INDEX");
            r2.setPushStrField(r6, "User_PIN", r13.getUser_PIN());
            r2.setPushCon(r6, "User_PIN=" + r13.getUser_PIN());
            r2.sendHubAction(0, r6, "");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x010f, code lost:
            if (r6 == 0) goto L_0x012e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0112, code lost:
            r12 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0114, code lost:
            r12 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0116, code lost:
            r12 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x0117, code lost:
            r6 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0119, code lost:
            r12 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x011a, code lost:
            r6 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
            r12.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0120, code lost:
            if (r6 != 0) goto L_0x0122;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0122, code lost:
            r2.convertPushFree(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0128, code lost:
            if (r6 != 0) goto L_0x012a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x012a, code lost:
            r2.convertPushFree(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x012d, code lost:
            throw r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
            r6 = r2.convertStandaloneInit();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
            r2.convertStandaloneSetUserPIN(r6, r13.getUser_PIN());
            r2.convertStandaloneSetEventType(r6, 231);
            r2.sendHubAction(5, r6, "");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0144, code lost:
            if (r6 == 0) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0147, code lost:
            r12 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0149, code lost:
            r12 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x014b, code lost:
            r12 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x014c, code lost:
            r6 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x014e, code lost:
            r12 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x014f, code lost:
            r6 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
            r12.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x0155, code lost:
            if (r6 == 0) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x0157, code lost:
            r2.convertStandaloneFree(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:0x015d, code lost:
            if (r6 != 0) goto L_0x015f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x015f, code lost:
            r2.convertStandaloneFree(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x0162, code lost:
            throw r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
            return;
         */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x012a  */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x015f  */
        /* JADX WARNING: Removed duplicated region for block: B:69:0x0167  */
        /* JADX WARNING: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void pushUser(android.content.Context r12, com.zkteco.android.db.orm.tna.UserInfo r13) {
            /*
                r11 = this;
                java.lang.String r0 = "User_PIN"
                java.lang.String r1 = ""
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "pushUser: "
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.String r3 = r13.getName()
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.String r2 = r2.toString()
                java.lang.String r3 = "TestPushUser"
                android.util.Log.d(r3, r2)
                java.io.File r2 = new java.io.File
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = com.zkteco.android.zkcore.utils.ZKFilePath.IMAGE_PATH
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r4 = "temp/"
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r4 = r13.getUser_PIN()
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r4 = com.zkteco.android.zkcore.utils.ZKFilePath.SUFFIX_IMAGE
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r3 = r3.toString()
                r2.<init>(r3)
                boolean r3 = r2.exists()
                if (r3 == 0) goto L_0x0073
                java.io.File r3 = new java.io.File
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = com.zkteco.android.zkcore.utils.ZKFilePath.PICTURE_PATH
                java.lang.StringBuilder r4 = r4.append(r5)
                java.lang.String r5 = r13.getUser_PIN()
                java.lang.StringBuilder r4 = r4.append(r5)
                java.lang.String r5 = com.zkteco.android.zkcore.utils.ZKFilePath.SUFFIX_IMAGE
                java.lang.StringBuilder r4 = r4.append(r5)
                java.lang.String r4 = r4.toString()
                r3.<init>(r4)
                r2.renameTo(r3)
            L_0x0073:
                com.zkteco.android.core.sdk.HubProtocolManager r2 = new com.zkteco.android.core.sdk.HubProtocolManager
                r2.<init>(r12)
                r3 = 0
                r4 = 0
                long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x00c6, all -> 0x00c2 }
                java.lang.String r8 = "USER_INFO"
                r2.setPushTableName(r6, r8)     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r8 = "ID"
                long r9 = r13.getID()     // Catch:{ Exception -> 0x00c0 }
                int r9 = (int) r9     // Catch:{ Exception -> 0x00c0 }
                r2.setPushIntField(r6, r8, r9)     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r8 = r13.getUser_PIN()     // Catch:{ Exception -> 0x00c0 }
                r2.setPushStrField(r6, r0, r8)     // Catch:{ Exception -> 0x00c0 }
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c0 }
                r8.<init>()     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r9 = "User_PIN = '"
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r9 = r13.getUser_PIN()     // Catch:{ Exception -> 0x00c0 }
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r9 = "'"
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x00c0 }
                r2.setPushCon(r6, r8)     // Catch:{ Exception -> 0x00c0 }
                r2.sendHubAction(r3, r6, r1)     // Catch:{ Exception -> 0x00c0 }
                int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r8 == 0) goto L_0x00d2
                goto L_0x00cf
            L_0x00bd:
                r12 = move-exception
                goto L_0x0163
            L_0x00c0:
                r8 = move-exception
                goto L_0x00c8
            L_0x00c2:
                r12 = move-exception
                r6 = r4
                goto L_0x0163
            L_0x00c6:
                r8 = move-exception
                r6 = r4
            L_0x00c8:
                r8.printStackTrace()     // Catch:{ all -> 0x00bd }
                int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r8 == 0) goto L_0x00d2
            L_0x00cf:
                r2.convertPushFree(r6)
            L_0x00d2:
                java.lang.String r6 = com.zkteco.android.zkcore.utils.ZKFilePath.PICTURE_PATH
                java.lang.String r7 = r13.getUser_PIN()
                android.app.Activity r12 = (android.app.Activity) r12
                boolean r12 = com.zkteco.android.zkcore.utils.FileUtil.isUserPhotoExist(r6, r7, r12)
                if (r12 == 0) goto L_0x012e
                long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x0119, all -> 0x0116 }
                java.lang.String r12 = "USER_PHOTO_INDEX"
                r2.setPushTableName(r6, r12)     // Catch:{ Exception -> 0x0114 }
                java.lang.String r12 = r13.getUser_PIN()     // Catch:{ Exception -> 0x0114 }
                r2.setPushStrField(r6, r0, r12)     // Catch:{ Exception -> 0x0114 }
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0114 }
                r12.<init>()     // Catch:{ Exception -> 0x0114 }
                java.lang.String r0 = "User_PIN="
                java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ Exception -> 0x0114 }
                java.lang.String r0 = r13.getUser_PIN()     // Catch:{ Exception -> 0x0114 }
                java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ Exception -> 0x0114 }
                java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0114 }
                r2.setPushCon(r6, r12)     // Catch:{ Exception -> 0x0114 }
                r2.sendHubAction(r3, r6, r1)     // Catch:{ Exception -> 0x0114 }
                int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r12 == 0) goto L_0x012e
                goto L_0x0122
            L_0x0112:
                r12 = move-exception
                goto L_0x0126
            L_0x0114:
                r12 = move-exception
                goto L_0x011b
            L_0x0116:
                r12 = move-exception
                r6 = r4
                goto L_0x0126
            L_0x0119:
                r12 = move-exception
                r6 = r4
            L_0x011b:
                r12.printStackTrace()     // Catch:{ all -> 0x0112 }
                int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r12 == 0) goto L_0x012e
            L_0x0122:
                r2.convertPushFree(r6)
                goto L_0x012e
            L_0x0126:
                int r13 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r13 == 0) goto L_0x012d
                r2.convertPushFree(r6)
            L_0x012d:
                throw r12
            L_0x012e:
                long r6 = r2.convertStandaloneInit()     // Catch:{ Exception -> 0x014e, all -> 0x014b }
                java.lang.String r12 = r13.getUser_PIN()     // Catch:{ Exception -> 0x0149 }
                r2.convertStandaloneSetUserPIN(r6, r12)     // Catch:{ Exception -> 0x0149 }
                r12 = 231(0xe7, float:3.24E-43)
                r2.convertStandaloneSetEventType(r6, r12)     // Catch:{ Exception -> 0x0149 }
                r12 = 5
                r2.sendHubAction(r12, r6, r1)     // Catch:{ Exception -> 0x0149 }
                int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r12 == 0) goto L_0x015a
                goto L_0x0157
            L_0x0147:
                r12 = move-exception
                goto L_0x015b
            L_0x0149:
                r12 = move-exception
                goto L_0x0150
            L_0x014b:
                r12 = move-exception
                r6 = r4
                goto L_0x015b
            L_0x014e:
                r12 = move-exception
                r6 = r4
            L_0x0150:
                r12.printStackTrace()     // Catch:{ all -> 0x0147 }
                int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r12 == 0) goto L_0x015a
            L_0x0157:
                r2.convertStandaloneFree(r6)
            L_0x015a:
                return
            L_0x015b:
                int r13 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r13 == 0) goto L_0x0162
                r2.convertStandaloneFree(r6)
            L_0x0162:
                throw r12
            L_0x0163:
                int r13 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r13 == 0) goto L_0x016a
                r2.convertPushFree(r6)
            L_0x016a:
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.PushUserEvent.pushUser(android.content.Context, com.zkteco.android.db.orm.tna.UserInfo):void");
        }
    }

    /* access modifiers changed from: private */
    public boolean checkVerifyExistWithOut(int i) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (i == 1) {
            z2 = false;
            z = true;
        } else if (i != 2) {
            z = false;
            z2 = false;
        } else {
            z = false;
            z2 = true;
        }
        UserInfo userInfo2 = getUserInfo();
        if (!z && VerifyTypeUtils.enableHaveCard() && userInfo2 != null && userInfo2.getMain_Card() != null && !userInfo2.getMain_Card().isEmpty()) {
            z3 = true;
        }
        if (!z2 && userInfo2 != null && userInfo2.getPassword() != null && !userInfo2.getPassword().isEmpty()) {
            z3 = true;
        }
        if (isHanveFace() && VerifyTypeUtils.enableFace()) {
            z3 = true;
        }
        if (!this.isFingerNull && VerifyTypeUtils.enableFinger()) {
            z3 = true;
        }
        if (this.isPalmNull || !VerifyTypeUtils.enablePv()) {
            return z3;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void showErrorDialog(final ZKConfirmDialog zKConfirmDialog) {
        final ZKConfirmDialog zKConfirmDialog2 = new ZKConfirmDialog(this);
        zKConfirmDialog2.show();
        zKConfirmDialog2.setType(1, "", "", getString(R.string.zk_staff_ok));
        zKConfirmDialog2.setMessage(getString(R.string.zk_staff_modify_promt_only));
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

    private boolean fingerExist(UserInfo userInfo2) {
        List<FpTemplate10> fingerTemplateForUserPin = this.templateManager.getFingerTemplateForUserPin(String.valueOf(userInfo2.getID()));
        return (fingerTemplateForUserPin == null || fingerTemplateForUserPin.size() == 0) ? false : true;
    }

    private boolean pswExist(UserInfo userInfo2) {
        return !TextUtils.isEmpty(userInfo2.getPassword());
    }

    private boolean cardExist(UserInfo userInfo2) {
        return !TextUtils.isEmpty(userInfo2.getMain_Card());
    }

    private void judegeVerifyType() {
        int verify_Type = this.userInfo.getVerify_Type();
        if (ZKLauncher.sAccessRuleType == 1) {
            if (DBManager.getInstance().getIntOption("AccessPersonalVerification", 0) != 1) {
                verify_Type = DBManager.getInstance().getIntOption(ZKDBConfig.OPT_DOOR_1_VERIFY_TYPE, 0);
            } else if (verify_Type == -1) {
                verify_Type = DBManager.getInstance().getIntOption(ZKDBConfig.OPT_DOOR_1_VERIFY_TYPE, 0);
            }
        } else if (verify_Type == -1) {
            verify_Type = 0;
        }
        if (verify_Type == 100) {
            try {
                verify_Type = ((AccGroup) new AccGroup().queryForId(Long.valueOf((long) this.userInfo.getAcc_Group_ID()))).getVerification();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int intOption = DBManager.getInstance().getIntOption("FingerFunOn", 0);
        int intOption2 = DBManager.getInstance().getIntOption("hasFingerModule", 0);
        if (verify_Type != 0) {
            if (verify_Type != 1) {
                if (verify_Type != 25) {
                    switch (verify_Type) {
                        case 3:
                            if (!pswExist(this.userInfo)) {
                                showTipDialog(isEnroll(false, false, false, true, false));
                                return;
                            }
                            break;
                        case 4:
                            if (!cardExist(this.userInfo) && VerifyTypeUtils.enableHaveCard()) {
                                showTipDialog(isEnroll(false, false, true, false, false));
                                return;
                            }
                        case 5:
                            if (!pswExist(this.userInfo) && !fingerExist(this.userInfo)) {
                                if (intOption != 0 && intOption2 != 0) {
                                    showTipDialog(isEnroll(false, true, false, true, false));
                                    return;
                                }
                                return;
                            }
                            break;
                        case 6:
                            if (!fingerExist(this.userInfo) && !cardExist(this.userInfo) && VerifyTypeUtils.enableHaveCard()) {
                                if (intOption != 0 && intOption2 != 0) {
                                    showTipDialog(isEnroll(false, true, true, false, false));
                                    return;
                                }
                                return;
                            }
                            break;
                        case 7:
                            if (!pswExist(this.userInfo) && !cardExist(this.userInfo) && VerifyTypeUtils.enableHaveCard()) {
                                showTipDialog(isEnroll(false, false, true, true, false));
                                return;
                            }
                        case 8:
                            if (!fingerExist(this.userInfo)) {
                                if (intOption != 0 && intOption2 != 0) {
                                    showTipDialog(isEnroll(false, true, false, false, false));
                                    return;
                                }
                                return;
                            }
                            break;
                        case 9:
                            if (!pswExist(this.userInfo) || !fingerExist(this.userInfo)) {
                                if (intOption != 0 && intOption2 != 0) {
                                    showTipDialog(isEnroll(false, true, false, true, false));
                                    return;
                                }
                                return;
                            }
                            break;
                        case 10:
                            if ((!cardExist(this.userInfo) || !fingerExist(this.userInfo)) && VerifyTypeUtils.enableHaveCard()) {
                                if (intOption != 0 && intOption2 != 0) {
                                    showTipDialog(isEnroll(false, true, true, false, false));
                                    return;
                                }
                                return;
                            }
                            break;
                        case 11:
                            if ((!pswExist(this.userInfo) || !cardExist(this.userInfo)) && VerifyTypeUtils.enableHaveCard()) {
                                showTipDialog(isEnroll(false, false, true, true, false));
                                return;
                            }
                        case 12:
                            if ((!pswExist(this.userInfo) || !fingerExist(this.userInfo) || !cardExist(this.userInfo)) && VerifyTypeUtils.enableHaveCard()) {
                                if (intOption != 0 && intOption2 != 0) {
                                    showTipDialog(isEnroll(false, true, true, true, false));
                                    return;
                                }
                                return;
                            }
                            break;
                        case 13:
                            if (!pswExist(this.userInfo) || !fingerExist(this.userInfo)) {
                                if (intOption != 0 && intOption2 != 0) {
                                    showTipDialog(isEnroll(false, true, false, true, false));
                                    return;
                                }
                                return;
                            }
                            break;
                        default:
                            switch (verify_Type) {
                                case 15:
                                    if (!HasFaceUtils.isHasFace(this.userInfo)) {
                                        showTipDialog(isEnroll(true, false, false, false, false));
                                        return;
                                    }
                                    break;
                                case 16:
                                    if (!HasFaceUtils.isHasFace(this.userInfo) || !fingerExist(this.userInfo)) {
                                        if (intOption != 0 && intOption2 != 0) {
                                            showTipDialog(isEnroll(true, true, false, false, false));
                                            return;
                                        }
                                        return;
                                    }
                                    break;
                                case 17:
                                    if (!HasFaceUtils.isHasFace(this.userInfo) || !pswExist(this.userInfo)) {
                                        showTipDialog(isEnroll(true, false, false, true, false));
                                        return;
                                    }
                                case 18:
                                    if ((!HasFaceUtils.isHasFace(this.userInfo) || !cardExist(this.userInfo)) && VerifyTypeUtils.enableHaveCard()) {
                                        showTipDialog(isEnroll(true, false, true, false, false));
                                        return;
                                    }
                                case 19:
                                    if ((!HasFaceUtils.isHasFace(this.userInfo) || !fingerExist(this.userInfo) || !cardExist(this.userInfo)) && VerifyTypeUtils.enableHaveCard()) {
                                        if (intOption != 0 && intOption2 != 0) {
                                            showTipDialog(isEnroll(true, true, true, false, false));
                                            return;
                                        }
                                        return;
                                    }
                                    break;
                                case 20:
                                    if (!HasFaceUtils.isHasFace(this.userInfo) || !pswExist(this.userInfo) || !fingerExist(this.userInfo)) {
                                        if (intOption != 0 && intOption2 != 0) {
                                            showTipDialog(isEnroll(true, true, false, true, false));
                                            return;
                                        }
                                        return;
                                    }
                                    break;
                            }
                            break;
                    }
                } else if (!ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
                    showTipDialog(isEnroll(false, false, false, false, true));
                    return;
                }
            } else if (!fingerExist(this.userInfo)) {
                if (intOption != 0 && intOption2 != 0) {
                    showTipDialog(isEnroll(false, true, false, false, false));
                    return;
                }
                return;
            }
        } else if (!VerifyTypeUtils.enableHaveCard()) {
            if (intOption == 0 || intOption2 == 0) {
                if (!pswExist(this.userInfo) && !HasFaceUtils.isHasFace(this.userInfo) && !ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
                    showTipDialog(isEnroll(true, false, false, true, true));
                    return;
                }
            } else if (!fingerExist(this.userInfo) && !pswExist(this.userInfo) && !HasFaceUtils.isHasFace(this.userInfo) && !ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
                showTipDialog(isEnroll(true, true, false, true, true));
                return;
            }
        } else if (intOption == 0 || intOption2 == 0) {
            if (!pswExist(this.userInfo) && !HasFaceUtils.isHasFace(this.userInfo) && !cardExist(this.userInfo) && !ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
                showTipDialog(isEnroll(true, false, true, true, true));
                return;
            }
        } else if (!fingerExist(this.userInfo) && !pswExist(this.userInfo) && !HasFaceUtils.isHasFace(this.userInfo) && !cardExist(this.userInfo) && !ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
            showTipDialog(isEnroll(true, true, true, true, true));
            return;
        }
        this.tvStaffFace.setTextColor(getResources().getColor(R.color.clr_989898));
        this.tvStaffFinger.setTextColor(getResources().getColor(R.color.clr_989898));
        this.tvStaffPassword.setTextColor(getResources().getColor(R.color.clr_989898));
        this.tvStaffCard.setTextColor(getResources().getColor(R.color.clr_989898));
        this.tvStaffpalm.setTextColor(getResources().getColor(R.color.clr_989898));
    }

    private String isEnroll(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!z || HasFaceUtils.isHasFace(this.userInfo)) {
            this.tvStaffFace.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            stringBuffer.append(getString(R.string.zk_staff_face));
            this.tvStaffFace.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (!z2 || fingerExist(this.userInfo)) {
            this.tvStaffFinger.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            stringBuffer.append("、" + getString(R.string.zk_staff_finger));
            this.tvStaffFinger.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (!z3 || cardExist(this.userInfo)) {
            this.tvStaffCard.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            stringBuffer.append("、" + getString(R.string.zk_staff_card));
            this.tvStaffCard.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (!z4 || pswExist(this.userInfo)) {
            this.tvStaffPassword.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            stringBuffer.append("、" + getString(R.string.zk_staff_pd));
            this.tvStaffPassword.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (!z5 || ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
            this.tvStaffpalm.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            stringBuffer.append("、" + getString(R.string.zk_staff_vertify_palmvein));
            this.tvStaffpalm.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (stringBuffer.indexOf("、") == 0) {
            stringBuffer.replace(0, 1, "");
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendUpdateHub() {
        /*
            r7 = this;
            com.zkteco.android.core.sdk.HubProtocolManager r0 = new com.zkteco.android.core.sdk.HubProtocolManager
            android.content.Context r1 = r7.getApplicationContext()
            r0.<init>(r1)
            r1 = 0
            com.zkteco.android.db.orm.tna.UserInfo r3 = r7.userInfo     // Catch:{ SQLException -> 0x0061, all -> 0x005e }
            r3.update()     // Catch:{ SQLException -> 0x0061, all -> 0x005e }
            long r3 = r0.convertPushInit()     // Catch:{ SQLException -> 0x0061, all -> 0x005e }
            java.lang.String r5 = "USER_INFO"
            r0.setPushTableName(r3, r5)     // Catch:{ SQLException -> 0x005c }
            java.lang.String r5 = "User_PIN"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x005c }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ SQLException -> 0x005c }
            r0.setPushStrField(r3, r5, r6)     // Catch:{ SQLException -> 0x005c }
            java.lang.String r5 = "SEND_FLAG"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x005c }
            int r6 = r6.getSEND_FLAG()     // Catch:{ SQLException -> 0x005c }
            r0.setPushIntField(r3, r5, r6)     // Catch:{ SQLException -> 0x005c }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x005c }
            r5.<init>()     // Catch:{ SQLException -> 0x005c }
            java.lang.String r6 = "(User_PIN='"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x005c }
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x005c }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ SQLException -> 0x005c }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x005c }
            java.lang.String r6 = "')"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x005c }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLException -> 0x005c }
            r0.setPushCon(r3, r5)     // Catch:{ SQLException -> 0x005c }
            r5 = 0
            java.lang.String r6 = ""
            r0.sendHubAction(r5, r3, r6)     // Catch:{ SQLException -> 0x005c }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x006d
            goto L_0x006a
        L_0x005c:
            r5 = move-exception
            goto L_0x0063
        L_0x005e:
            r5 = move-exception
            r3 = r1
            goto L_0x006f
        L_0x0061:
            r5 = move-exception
            r3 = r1
        L_0x0063:
            r5.printStackTrace()     // Catch:{ all -> 0x006e }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x006d
        L_0x006a:
            r0.convertPushFree(r3)
        L_0x006d:
            return
        L_0x006e:
            r5 = move-exception
        L_0x006f:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0076
            r0.convertPushFree(r3)
        L_0x0076:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.sendUpdateHub():void");
    }

    private class UserQueryTask extends AsyncTask<Void, Void, List<UserInfo>> {
        private boolean isRunning;
        List<UserInfo> userInfos;

        private UserQueryTask() {
            this.isRunning = false;
        }

        /* access modifiers changed from: private */
        public boolean isRunning() {
            return this.isRunning;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            Log.d(ZkAddUserActivity.TAG, "onPreExecute: ");
            super.onPreExecute();
            this.isRunning = true;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(List<UserInfo> list) {
            Log.d(ZkAddUserActivity.TAG, "onPostExecute: ");
            super.onPostExecute(list);
            this.isRunning = false;
            this.userInfos = list;
            if (ZkAddUserActivity.this.dialog.isShowing()) {
                ZkAddUserActivity.this.dialog.dismiss();
            }
        }

        /* access modifiers changed from: protected */
        public List<UserInfo> doInBackground(Void... voidArr) {
            Log.d(ZkAddUserActivity.TAG, "doInBackground: ");
            try {
                return new UserInfo().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public List<UserInfo> getList() {
            return this.userInfos;
        }
    }
}
