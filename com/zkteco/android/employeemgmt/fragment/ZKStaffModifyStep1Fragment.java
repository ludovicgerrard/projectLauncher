package com.zkteco.android.employeemgmt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.guide.guidecore.GuideUsbManager;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.receiver.OpLogReceiver;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.AccGroup;
import com.zkteco.android.db.orm.tna.ExtUser;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.ZKStaffCardRegistrationActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffFingerprintActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffModifyActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffOfflineActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffPalmActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffProfessionActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffVerifyActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffVerifyVerActivity;
import com.zkteco.android.employeemgmt.common.Constants;
import com.zkteco.android.employeemgmt.fragment.base.ZKStaffBaseFragment;
import com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean;
import com.zkteco.android.employeemgmt.util.HasFaceUtils;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.employeemgmt.util.ZkPalmUtils;
import com.zkteco.android.employeemgmt.util.ZkSuperAdminUtils;
import com.zkteco.android.employeemgmt.widget.ZKEditNameDialog;
import com.zkteco.android.employeemgmt.widget.ZKSpinerPopupWindow;
import com.zkteco.android.zkcore.view.alert.ZKConfirmDialog;
import com.zkteco.android.zkcore.view.alert.ZKEditDialog;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ZKStaffModifyStep1Fragment extends ZKStaffBaseFragment implements View.OnClickListener {
    public static final String OPT_ACC_RULE_TYPE = "AccessRuleType";
    public static final String OPT_LOCK_FUN_ON = "~LockFunOn";
    private static final int SCROLL_LINE_CARD = 7;
    private static final int SCROLL_LINE_FACE = 10;
    private static final int SCROLL_LINE_FINGER = 6;
    private static final int SCROLL_LINE_NAME = 1;
    private static final int SCROLL_LINE_PALM = 5;
    private static final int SCROLL_LINE_PASSWORD = 8;
    private static final int SCROLL_LINE_PIN = 2;
    private static final int SCROLL_LINE_PRIVILIGE = 3;
    private static final int SCROLL_LINE_VALIDATE = 4;
    private static final int SCROLL_LINE_VERIFY = 9;
    private static final int STAFF_PRI_COM = 0;
    private static final int STAFF_PRI_SUPER = 14;
    public static final String USER_FACE_FUNON = "FaceFunOn";
    private static final String USER_FINGER_MODULE = "hasFingerModule";
    private static final String USER_FINGE_FUNON = "FingerFunOn";
    private static final String USER_VALID_TIME_FUN = "UserValidTimeFun";
    public static final int VERIFY_TYPE = -1;
    private static final String keySupportUserValidateDate = "SupportUserValidateDate";
    private DataManager dataManager;
    /* access modifiers changed from: private */
    public ExtUser extUser;
    private View fingerLine;
    private boolean isFaceNull = true;
    /* access modifiers changed from: private */
    public boolean isFingerNull = true;
    private boolean ischange = false;
    private View lineH;
    private View lineVertify;
    private List<ZKStaffVerifyBean> listVerifyList;
    private List<String> mSpinnerList = new ArrayList();
    /* access modifiers changed from: private */
    public TextView mStaffName;
    private TextView mStaffPri;
    private TextView mUserPin;
    /* access modifiers changed from: private */
    public ZKSpinerPopupWindow<String> mZKSpinerPopupWindow;
    private int nowPos = 0;
    private View palmLine;
    private RelativeLayout relHome;
    private RelativeLayout relStaffAcc;
    private RelativeLayout relStaffCard;
    private RelativeLayout relStaffFace;
    private RelativeLayout relStaffFinger;
    private LinearLayout relStaffName;
    private RelativeLayout relStaffPalm;
    private RelativeLayout relStaffPassword;
    private RelativeLayout relStaffPri;
    private RelativeLayout relStaffValidate;
    private RelativeLayout relStaffVertify;
    private int sAccessPersonalVerification;
    /* access modifiers changed from: private */
    public int sAccessRuleType;
    private int sLockFunOn;
    private TemplateManager templateManager;
    /* access modifiers changed from: private */
    public TextView tvStaffCard;
    private TextView tvStaffFace;
    private TextView tvStaffFinger;
    private TextView tvStaffPalm;
    /* access modifiers changed from: private */
    public TextView tvStaffPassword;
    private TextView tvStaffVertify;
    /* access modifiers changed from: private */
    public UserInfo userInfo;
    private Map<Integer, String> verifyMap;

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (getActivity().getResources().getConfiguration().orientation != 2) {
            ((ZKStaffModifyActivity) getActivity()).setLinearTopVIS(true);
        }
        try {
            this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(((ZKStaffModifyActivity) getActivity()).getUserInfo().getID()));
            this.extUser = (ExtUser) new ExtUser().getQueryBuilder().where().eq("Pin", this.userInfo.getUser_PIN()).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initData();
        View inflate = layoutInflater.inflate(R.layout.fragment_modify_step1, viewGroup, false);
        this.listVerifyList = createList();
        initView(inflate);
        initSpinner();
        return inflate;
    }

    private void initData() {
        VerifyTypeUtils.init(getActivity());
        try {
            this.dataManager = DBManager.getInstance();
            this.templateManager = new TemplateManager(getActivity());
            this.sLockFunOn = this.dataManager.getIntOption("~LockFunOn", 0);
            this.sAccessRuleType = this.dataManager.getIntOption("AccessRuleType", 0);
            this.sAccessPersonalVerification = this.dataManager.getIntOption("AccessPersonalVerification", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initListener();
    }

    public void onResume() {
        super.onResume();
        try {
            UserInfo userInfo2 = (UserInfo) new UserInfo().queryForId(Long.valueOf(((ZKStaffModifyActivity) getActivity()).getUserInfo().getID()));
            this.userInfo = userInfo2;
            if (userInfo2 == null) {
                showToast((Context) getActivity(), (int) R.string.error_return);
                getActivity().finish();
                return;
            }
            setLineContent(1, userInfo2);
            setLineContent(2, this.userInfo);
            setLineContent(3, this.userInfo);
            setLineContent(4, this.userInfo);
            setLineContent(5, this.userInfo);
            setLineContent(6, this.userInfo);
            setLineContent(7, this.userInfo);
            setLineContent(8, this.userInfo);
            setLineContent(9, this.userInfo);
            setLineContent(10, this.userInfo);
            judegeVerifyType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        if (this.mSpinnerList.size() == 0) {
            this.mSpinnerList.add(getString(R.string.zk_staff_commenuser));
            this.mSpinnerList.add(getString(R.string.zk_staff_superuser));
        }
        if (this.userInfo == null) {
            try {
                this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(((ZKStaffModifyActivity) getActivity()).getUserInfo().getID()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.fingerLine = view.findViewById(R.id.fingerLine);
        this.palmLine = view.findViewById(R.id.palmLine);
        this.relHome = (RelativeLayout) view.findViewById(R.id.rel_frag1_home);
        this.relStaffName = (LinearLayout) view.findViewById(R.id.rel_staff_name);
        this.mStaffName = (TextView) view.findViewById(R.id.tv_staff_name);
        setLineContent(1, this.userInfo);
        this.mUserPin = (TextView) view.findViewById(R.id.tv_staff_pin);
        setLineContent(2, this.userInfo);
        this.relStaffPri = (RelativeLayout) view.findViewById(R.id.rel_staff_authority);
        this.mStaffPri = (TextView) view.findViewById(R.id.tv_staff_authority);
        setLineContent(3, this.userInfo);
        this.relStaffValidate = (RelativeLayout) view.findViewById(R.id.rel_staff_validate);
        setLineContent(4, this.userInfo);
        this.relStaffFinger = (RelativeLayout) view.findViewById(R.id.rel_staff_finger);
        this.tvStaffFinger = (TextView) view.findViewById(R.id.tv_staff_finger);
        setLineContent(6, this.userInfo);
        this.relStaffPalm = (RelativeLayout) view.findViewById(R.id.rel_staff_palm);
        this.tvStaffPalm = (TextView) view.findViewById(R.id.tv_staff_palm);
        setLineContent(5, this.userInfo);
        this.relStaffCard = (RelativeLayout) view.findViewById(R.id.rel_staff_card);
        this.tvStaffCard = (TextView) view.findViewById(R.id.tv_staff_card);
        setLineContent(7, this.userInfo);
        this.relStaffPassword = (RelativeLayout) view.findViewById(R.id.rel_staff_password);
        this.tvStaffPassword = (TextView) view.findViewById(R.id.tv_staff_password);
        setLineContent(8, this.userInfo);
        this.relStaffVertify = (RelativeLayout) view.findViewById(R.id.rel_staff_vertify);
        this.lineVertify = view.findViewById(R.id.verifyview);
        this.tvStaffVertify = (TextView) view.findViewById(R.id.tv_staff_verify);
        this.relStaffAcc = (RelativeLayout) view.findViewById(R.id.rel_staff_acc);
        View findViewById = view.findViewById(R.id.cardLine);
        setLineContent(9, this.userInfo);
        this.relStaffFace = (RelativeLayout) view.findViewById(R.id.rel_staff_face);
        this.lineH = view.findViewById(R.id.faceview);
        this.tvStaffFace = (TextView) view.findViewById(R.id.tv_staff_face);
        setLineContent(10, this.userInfo);
        if (this.sAccessRuleType == 1) {
            this.relStaffAcc.setVisibility(0);
            if (this.sAccessPersonalVerification == 0) {
                this.relStaffVertify.setVisibility(8);
                this.lineVertify.setVisibility(8);
            }
        } else if (this.sLockFunOn == 15) {
            this.relStaffAcc.setVisibility(0);
        } else {
            this.relStaffAcc.setVisibility(8);
        }
        if (VerifyTypeUtils.enableHaveCard()) {
            this.relStaffCard.setVisibility(0);
            findViewById.setVisibility(0);
        }
    }

    private void initListener() {
        this.relStaffName.setOnClickListener(this);
        this.relStaffPri.setOnClickListener(this);
        this.relStaffValidate.setOnClickListener(this);
        this.relStaffFinger.setOnClickListener(this);
        this.relStaffPalm.setOnClickListener(this);
        this.relStaffCard.setOnClickListener(this);
        this.relStaffPassword.setOnClickListener(this);
        this.relStaffFace.setOnClickListener(this);
        this.relStaffVertify.setOnClickListener(this);
        this.relStaffAcc.setOnClickListener(this);
    }

    private void initSpinner() {
        ZKSpinerPopupWindow zKSpinerPopupWindow = new ZKSpinerPopupWindow(getActivity(), getActivity(), this.mSpinnerList, this.nowPos, new AdapterView.OnItemClickListener() {
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                ZKStaffModifyStep1Fragment.this.lambda$initSpinner$0$ZKStaffModifyStep1Fragment(adapterView, view, i, j);
            }
        });
        this.mZKSpinerPopupWindow = zKSpinerPopupWindow;
        zKSpinerPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public final void onDismiss() {
                ZKStaffModifyStep1Fragment.this.lambda$initSpinner$1$ZKStaffModifyStep1Fragment();
            }
        });
    }

    public /* synthetic */ void lambda$initSpinner$0$ZKStaffModifyStep1Fragment(AdapterView adapterView, View view, int i, long j) {
        if (i == 0) {
            this.mZKSpinerPopupWindow.setNowPosition(i);
            this.nowPos = i;
            this.userInfo.setPrivilege(0);
            this.mZKSpinerPopupWindow.setBackgroundAlpha(1.0f);
            this.mZKSpinerPopupWindow.dismiss();
            this.ischange = true;
        } else if (i == 1) {
            String main_Card = this.userInfo.getMain_Card();
            if (!this.isFaceNull || !this.isFingerNull || !TextUtils.isEmpty(this.userInfo.getPassword()) || ((main_Card != null && !main_Card.equals("0") && !main_Card.equals("")) || ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN()))) {
                this.mZKSpinerPopupWindow.setNowPosition(i);
                this.nowPos = i;
                this.mZKSpinerPopupWindow.dismiss();
                this.mZKSpinerPopupWindow.setBackgroundAlpha(1.0f);
                this.userInfo.setPrivilege(14);
                this.ischange = true;
            } else {
                ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(getActivity());
                zKConfirmDialog.show();
                zKConfirmDialog.setType(1, "", "", getString(R.string.zk_staff_ok));
                zKConfirmDialog.setMessage(getString(R.string.zk_staff_modify_promt_admin));
                zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
                    public void cover() {
                    }

                    public void failure() {
                    }

                    public void success() {
                        ZKStaffModifyStep1Fragment.this.mZKSpinerPopupWindow.dismiss();
                        ZKStaffModifyStep1Fragment.this.mZKSpinerPopupWindow.setBackgroundAlpha(1.0f);
                    }
                });
                this.ischange = false;
            }
        }
        if (this.ischange) {
            try {
                this.userInfo.setSEND_FLAG(0);
                this.userInfo.update();
                setLineContent(3, this.userInfo);
                sendPush();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public /* synthetic */ void lambda$initSpinner$1$ZKStaffModifyStep1Fragment() {
        this.mZKSpinerPopupWindow.setBackgroundAlpha(1.0f);
    }

    private void setLineContent(int i, UserInfo userInfo2) {
        boolean z = true;
        int i2 = 0;
        switch (i) {
            case 1:
                if (this.sAccessRuleType == 0 || (ZKLauncher.longName == 0 && userInfo2.getName() != null)) {
                    String name = userInfo2.getName();
                    if (name != null && name.contains("&")) {
                        name = name.replace("&", " ");
                    }
                    this.mStaffName.setText(name);
                    return;
                }
                String str = null;
                if (this.extUser != null) {
                    str = this.extUser.getFirstName() + " " + this.extUser.getLastName();
                }
                this.mStaffName.setText(str);
                return;
            case 2:
                this.mUserPin.setText(userInfo2.getUser_PIN());
                return;
            case 3:
                int privilege = userInfo2.getPrivilege();
                if (privilege == 0) {
                    this.mStaffPri.setText(this.mSpinnerList.get(0));
                    this.nowPos = 0;
                    return;
                } else if (privilege == 14) {
                    this.mStaffPri.setText(this.mSpinnerList.get(1));
                    this.nowPos = 1;
                    return;
                } else {
                    return;
                }
            case 4:
                DataManager dataManager2 = this.dataManager;
                if (dataManager2 == null) {
                    this.relStaffValidate.setVisibility(8);
                    return;
                } else if (dataManager2.getStrOption("UserValidTimeFun", "0").equals("0")) {
                    this.relStaffValidate.setVisibility(8);
                    return;
                } else if (this.dataManager.getStrOption("SupportUserValidateDate", "0").equals("1")) {
                    this.relStaffValidate.setVisibility(0);
                    return;
                } else {
                    return;
                }
            case 5:
                DataManager dataManager3 = this.dataManager;
                if (dataManager3 == null) {
                    return;
                }
                if (dataManager3.getIntOption("PvFunOn", 0) == 1) {
                    this.relStaffPalm.setVisibility(0);
                    this.palmLine.setVisibility(0);
                    if (ZkPalmUtils.isHasPv(userInfo2.getUser_PIN())) {
                        this.tvStaffPalm.setText(getString(R.string.zk_staff_have));
                        return;
                    } else {
                        this.tvStaffPalm.setText(getString(R.string.zk_staff_info_null));
                        return;
                    }
                } else {
                    this.relStaffPalm.setVisibility(8);
                    this.palmLine.setVisibility(8);
                    return;
                }
            case 6:
                DataManager dataManager4 = this.dataManager;
                if (dataManager4 == null) {
                    this.relStaffFinger.setVisibility(8);
                    return;
                } else if (dataManager4.getStrOption("FingerFunOn", "0").equals("0") || this.dataManager.getStrOption("hasFingerModule", "0").equals("0")) {
                    this.relStaffFinger.setVisibility(8);
                    this.fingerLine.setVisibility(8);
                    return;
                } else if (this.dataManager.getStrOption("FingerFunOn", "0").equals("1") && this.dataManager.getStrOption("hasFingerModule", "0").equals("1")) {
                    this.relStaffFinger.setVisibility(0);
                    this.fingerLine.setVisibility(0);
                    List<FpTemplate10> fingerTemplateForUserPin = this.templateManager.getFingerTemplateForUserPin(String.valueOf(userInfo2.getID()));
                    if (fingerTemplateForUserPin.size() > 0) {
                        z = false;
                    }
                    this.isFingerNull = z;
                    if (z) {
                        this.tvStaffFinger.setText(getString(R.string.zk_staff_info_null));
                        return;
                    } else {
                        this.tvStaffFinger.setText(fingerTemplateForUserPin.size() + "/10");
                        return;
                    }
                } else {
                    return;
                }
            case 7:
                String main_Card = userInfo2.getMain_Card();
                if (main_Card == null || main_Card.equals("0") || main_Card.equals("")) {
                    this.tvStaffCard.setText(getString(R.string.zk_staff_info_null));
                    return;
                } else {
                    this.tvStaffCard.setText(main_Card);
                    return;
                }
            case 8:
                if (TextUtils.isEmpty(userInfo2.getPassword())) {
                    this.tvStaffPassword.setText(getString(R.string.zk_staff_info_null));
                    return;
                } else {
                    this.tvStaffPassword.setText(getString(R.string.zk_staff_password));
                    return;
                }
            case 9:
                if (-1 != userInfo2.getVerify_Type()) {
                    while (i2 < this.listVerifyList.size()) {
                        if (this.listVerifyList.get(i2).getValue() == userInfo2.getVerify_Type()) {
                            this.tvStaffVertify.setText(this.listVerifyList.get(i2).getString());
                        }
                        i2++;
                    }
                    return;
                } else if (this.sAccessRuleType == 1) {
                    this.tvStaffVertify.setText(R.string.zk_staff_info_null);
                    return;
                } else {
                    userInfo2.setVerify_Type(0);
                    while (i2 < this.listVerifyList.size()) {
                        if (this.listVerifyList.get(i2).getValue() == userInfo2.getVerify_Type()) {
                            this.tvStaffVertify.setText(this.listVerifyList.get(i2).getString());
                        }
                        i2++;
                    }
                    return;
                }
            case 10:
                DataManager dataManager5 = this.dataManager;
                if (dataManager5 == null) {
                    this.relStaffFinger.setVisibility(8);
                    return;
                } else if (dataManager5.getStrOption("FaceFunOn", "0").equals("0")) {
                    this.relStaffFace.setVisibility(8);
                    this.lineH.setVisibility(8);
                    return;
                } else if (this.dataManager.getStrOption("FaceFunOn", "0").equals("1")) {
                    this.relStaffFace.setVisibility(0);
                    this.lineH.setVisibility(0);
                    try {
                        if (new PersBiotemplate().getQueryBuilder().where().eq("user_pin", userInfo2.getUser_PIN()).and().eq("bio_type", 9).query().size() > 0) {
                            z = false;
                        }
                        this.isFaceNull = z;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (HasFaceUtils.isHasFace(userInfo2)) {
                        this.tvStaffFace.setText(R.string.zk_staff_have);
                        return;
                    } else {
                        this.tvStaffFace.setText(R.string.zk_staff_info_null);
                        return;
                    }
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public void onClick(View view) {
        Intent intent;
        boolean z = false;
        switch (view.getId()) {
            case R.id.rel_staff_acc:
                if (this.sAccessRuleType == 1) {
                    Intent intent2 = new Intent(getActivity(), ZKStaffProfessionActivity.class);
                    intent2.putExtra("userInfo_id", this.userInfo.getID());
                    startActivity(intent2);
                    return;
                } else if (this.sLockFunOn == 15) {
                    Intent intent3 = new Intent(getActivity(), ZKStaffOfflineActivity.class);
                    intent3.putExtra("userInfo_id", this.userInfo.getID());
                    startActivity(intent3);
                    return;
                } else {
                    return;
                }
            case R.id.rel_staff_authority:
                if (getActivity().getResources().getConfiguration().orientation == 2) {
                    this.mZKSpinerPopupWindow.setWidth(300);
                } else {
                    this.mZKSpinerPopupWindow.setWidth(GuideUsbManager.CONTRAST_MAX);
                }
                this.mZKSpinerPopupWindow.setBackgroundAlpha(0.4f);
                this.mZKSpinerPopupWindow.showAtLocation(this.relHome, 17, 0, 0);
                return;
            case R.id.rel_staff_card:
                clickCardItem();
                return;
            case R.id.rel_staff_face:
                Intent intent4 = new Intent(getActivity(), ZKStaffFaceActivity.class);
                intent4.putExtra("userInfo_id", this.userInfo.getID());
                if (isHanveFace()) {
                    intent4.putExtra("isReEnrollFace", true);
                    intent4.putExtra("isModify", true);
                    intent4.putExtra("isNewFace", false);
                } else {
                    intent4.putExtra("isReEnrollFace", false);
                    intent4.putExtra("isModify", false);
                    intent4.putExtra("isNewFace", true);
                }
                startActivity(intent4);
                return;
            case R.id.rel_staff_finger:
                Intent intent5 = new Intent(getActivity(), ZKStaffFingerprintActivity.class);
                intent5.putExtra("id", 1);
                intent5.putExtra("userInfo_id", this.userInfo.getID());
                intent5.putExtra("action", 2);
                if (this.userInfo.getPrivilege() == 14 && TextUtils.isEmpty(this.userInfo.getMain_Card()) && TextUtils.isEmpty(this.userInfo.getPassword()) && isHanveFace()) {
                    z = true;
                }
                intent5.putExtra("isBothNull", z);
                startActivity(intent5);
                return;
            case R.id.rel_staff_name:
                final ZKEditNameDialog zKEditNameDialog = new ZKEditNameDialog(getActivity());
                if (this.sAccessRuleType == 0) {
                    String name = this.userInfo.getName();
                    if (ZKLauncher.longName == 0) {
                        if (name.contains("&")) {
                            name = name.replace("&", " ");
                        }
                        zKEditNameDialog.setUserName(name);
                    } else if (name == null || !name.contains("&")) {
                        zKEditNameDialog.setFirsetName(name);
                    } else {
                        String[] split = name.split("&");
                        zKEditNameDialog.setFirsetName(split[0]);
                        zKEditNameDialog.setLastName(split[1]);
                    }
                } else if (ZKLauncher.longName == 0) {
                    zKEditNameDialog.setUserName(this.userInfo.getName());
                } else {
                    ExtUser extUser2 = this.extUser;
                    if (extUser2 != null) {
                        zKEditNameDialog.setFirsetName(extUser2.getFirstName());
                        zKEditNameDialog.setLastName(this.extUser.getLastName());
                    }
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
                        if (ZKLauncher.longName == 0 || ZKStaffModifyStep1Fragment.this.sAccessRuleType == 0) {
                            if (ZKStaffModifyStep1Fragment.this.sAccessRuleType != 0 || ZKLauncher.longName != 1) {
                                String inputText = zKEditNameDialog.getInputText();
                                if (inputText.contains(" ")) {
                                    inputText = inputText.replace(" ", "&");
                                }
                                ZKStaffModifyStep1Fragment.this.userInfo.setName(inputText);
                            } else if (!zKEditNameDialog.getFirstName().isEmpty() && !zKEditNameDialog.getLastName().isEmpty()) {
                                ZKStaffModifyStep1Fragment.this.userInfo.setName(zKEditNameDialog.getFirstName() + "&" + zKEditNameDialog.getLastName());
                            } else if (!zKEditNameDialog.getFirstName().isEmpty() || zKEditNameDialog.getLastName().isEmpty()) {
                                ZKStaffModifyStep1Fragment.this.userInfo.setName(zKEditNameDialog.getFirstName());
                            } else {
                                ZKStaffModifyStep1Fragment.this.userInfo.setName("&" + zKEditNameDialog.getLastName());
                            }
                            ZKStaffModifyStep1Fragment.this.userInfo.setSEND_FLAG(0);
                            try {
                                ZKStaffModifyStep1Fragment.this.userInfo.update();
                                ZKStaffModifyStep1Fragment.this.sendPush();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else if (ZKStaffModifyStep1Fragment.this.extUser != null) {
                            ZKStaffModifyStep1Fragment.this.extUser.setFirstName(zKEditNameDialog.getFirstName());
                            ZKStaffModifyStep1Fragment.this.extUser.setLastName(zKEditNameDialog.getLastName());
                            ZKStaffModifyStep1Fragment.this.extUser.setSEND_FLAG(0);
                            try {
                                ZKStaffModifyStep1Fragment.this.extUser.update();
                                ZKStaffModifyStep1Fragment.this.sendExtPush();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                        } else {
                            ExtUser unused = ZKStaffModifyStep1Fragment.this.extUser = new ExtUser();
                            ZKStaffModifyStep1Fragment.this.extUser.setFirstName(zKEditNameDialog.getFirstName());
                            ZKStaffModifyStep1Fragment.this.extUser.setLastName(zKEditNameDialog.getLastName());
                            ZKStaffModifyStep1Fragment.this.extUser.setPin(ZKStaffModifyStep1Fragment.this.userInfo.getUser_PIN());
                            try {
                                ZKStaffModifyStep1Fragment.this.extUser.create();
                                ZKStaffModifyStep1Fragment.this.sendExtPush();
                            } catch (SQLException e3) {
                                e3.printStackTrace();
                            }
                        }
                        zKEditNameDialog.cancel();
                        ZKStaffModifyStep1Fragment.this.mStaffName.setText(zKEditNameDialog.getInputText());
                    }
                });
                zKEditNameDialog.show();
                return;
            case R.id.rel_staff_palm:
                Intent intent6 = new Intent();
                intent6.setClass(getActivity(), ZKStaffPalmActivity.class);
                intent6.putExtra("userPin", this.userInfo.getUser_PIN());
                intent6.putExtra("isModify", ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN()));
                intent6.putExtra("isNewPalm", true);
                startActivity(intent6);
                return;
            case R.id.rel_staff_password:
                clickPasswordItem();
                return;
            case R.id.rel_staff_validate:
                startActivity(new Intent(getActivity(), ZKStaffModifyStep2Activity.class).putExtra("id", this.userInfo.getID()));
                return;
            case R.id.rel_staff_vertify:
                if (getActivity().getResources().getConfiguration().orientation == 2) {
                    intent = new Intent(getActivity(), ZKStaffVerifyActivity.class);
                } else {
                    intent = new Intent(getActivity(), ZKStaffVerifyVerActivity.class);
                }
                intent.putExtra("userInfo_id", this.userInfo.getID());
                intent.putExtra("sAccessRuleType", this.sAccessRuleType);
                intent.putExtra("sLockFunOn", this.sLockFunOn);
                startActivity(intent);
                return;
            default:
                return;
        }
    }

    private void clickPasswordItem() {
        if (!TextUtils.isEmpty(this.userInfo.getPassword())) {
            final ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(getActivity());
            zKConfirmDialog.show();
            zKConfirmDialog.setType(3, getString(R.string.zk_staff_cancel), getString(R.string.zk_staff_delete), getString(R.string.zk_staff_cover));
            zKConfirmDialog.setMessage(getString(R.string.zk_staff_prompt_password));
            zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
                public void failure() {
                    ZKStaffModifyStep1Fragment.this.judegeVerifyType();
                }

                public void cover() {
                    try {
                        if (ZKStaffModifyStep1Fragment.this.userInfo.getPrivilege() == 14) {
                            int verify_Type = ZKStaffModifyStep1Fragment.this.userInfo.getVerify_Type();
                            List<ZKStaffVerifyBean> verifyList = Constants.getVerifyList(ZKStaffModifyStep1Fragment.this.getActivity());
                            ZKStaffModifyStep1Fragment zKStaffModifyStep1Fragment = ZKStaffModifyStep1Fragment.this;
                            if (zKStaffModifyStep1Fragment.isCanDeleteVerify(zKStaffModifyStep1Fragment.isFingerNull, ZKStaffModifyStep1Fragment.this.userInfo, 3) && verify_Type != -1) {
                                String string = verifyList.get(ZKStaffModifyStep1Fragment.this.userInfo.getVerify_Type()).getString();
                                boolean contains = string.contains("密码");
                                boolean contains2 = string.contains("+");
                                boolean contains3 = string.contains("/");
                                if (contains && !contains2 && !contains3) {
                                    ZKStaffModifyStep1Fragment.this.showErrorDialog(zKConfirmDialog);
                                    return;
                                } else if (!contains || !contains2) {
                                    int verify_Type2 = ZKStaffModifyStep1Fragment.this.userInfo.getVerify_Type();
                                    if (verify_Type2 != 5) {
                                        if (verify_Type2 == 7) {
                                            String main_Card = ZKStaffModifyStep1Fragment.this.userInfo.getMain_Card();
                                            if (main_Card == null || main_Card.equals("0") || main_Card.equals("")) {
                                                ZKStaffModifyStep1Fragment.this.showErrorDialog(zKConfirmDialog);
                                                return;
                                            }
                                        }
                                    } else if (ZKStaffModifyStep1Fragment.this.isFingerNull) {
                                        ZKStaffModifyStep1Fragment.this.showErrorDialog(zKConfirmDialog);
                                        return;
                                    }
                                } else {
                                    ZKStaffModifyStep1Fragment.this.showErrorDialog(zKConfirmDialog);
                                    return;
                                }
                            }
                        }
                    } catch (Exception unused) {
                    }
                    if (ZKStaffModifyStep1Fragment.this.userInfo.getPrivilege() == 14) {
                        ZKStaffModifyStep1Fragment zKStaffModifyStep1Fragment2 = ZKStaffModifyStep1Fragment.this;
                        if (!zKStaffModifyStep1Fragment2.isCanDeleteVerify(zKStaffModifyStep1Fragment2.isFingerNull, ZKStaffModifyStep1Fragment.this.userInfo, 3) && !HasFaceUtils.isHasFace(ZKStaffModifyStep1Fragment.this.userInfo)) {
                            ZKStaffModifyStep1Fragment.this.showErrorDialog(zKConfirmDialog);
                            return;
                        }
                    }
                    ZKStaffModifyStep1Fragment.this.userInfo.setPassword((String) null);
                    try {
                        ZKStaffModifyStep1Fragment.this.getActivity().sendBroadcast(new Intent(OpLogReceiver.ACTION_DELETE_PASSWORD).putExtra("OpWho", ZKStaffModifyStep1Fragment.this.userInfo.getUser_PIN()));
                        ZKStaffModifyStep1Fragment.this.userInfo.update();
                        zKConfirmDialog.cancel();
                        ZKStaffModifyStep1Fragment.this.tvStaffPassword.setText(ZKStaffModifyStep1Fragment.this.getString(R.string.zk_staff_info_null));
                        ((ZKStaffModifyActivity) ZKStaffModifyStep1Fragment.this.getActivity()).setPDSmallIcon(false);
                        ZKStaffModifyStep1Fragment.this.sendPush();
                        ZKStaffModifyStep1Fragment.this.judegeVerifyType();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                public void success() {
                    if (ZKStaffModifyStep1Fragment.this.getActivity().getResources().getConfiguration().orientation == 2) {
                        Intent intent = new Intent(ZKStaffModifyStep1Fragment.this.getActivity(), ZKStaffPasswordSettingActivity.class);
                        intent.putExtra("userInfo_id", ZKStaffModifyStep1Fragment.this.userInfo.getID());
                        ZKStaffModifyStep1Fragment.this.startActivity(intent);
                        return;
                    }
                    ZKStaffModifyStep1Fragment.this.showPdDialog();
                }
            });
        } else if (getActivity().getResources().getConfiguration().orientation == 2) {
            Intent intent = new Intent(getActivity(), ZKStaffPasswordSettingActivity.class);
            intent.putExtra("userInfo_id", this.userInfo.getID());
            startActivity(intent);
        } else {
            showPdDialog();
        }
    }

    private void clickCardItem() {
        String main_Card = this.userInfo.getMain_Card();
        if (TextUtils.isEmpty(main_Card) || "0".equals(main_Card)) {
            Intent intent = new Intent(getActivity(), ZKStaffCardRegistrationActivity.class);
            intent.putExtra("userInfo_id", this.userInfo.getID());
            startActivity(intent);
            return;
        }
        final ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(getActivity());
        zKConfirmDialog.show();
        zKConfirmDialog.setType(3, getString(R.string.zk_staff_cancel), getString(R.string.zk_staff_delete), getString(R.string.zk_staff_cover));
        zKConfirmDialog.setMessage(getString(R.string.zk_staff_prompt_card));
        zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
            public void failure() {
                zKConfirmDialog.cancel();
            }

            public void cover() {
                try {
                    if (ZKStaffModifyStep1Fragment.this.userInfo.getPrivilege() == 14 && !ZkSuperAdminUtils.isSupportDelCard(ZKStaffModifyStep1Fragment.this.userInfo)) {
                        ZKStaffModifyStep1Fragment.this.showErrorDialog(zKConfirmDialog);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    ZKStaffModifyStep1Fragment.this.userInfo.setMain_Card((String) null);
                    ZKStaffModifyStep1Fragment.this.userInfo.update();
                    zKConfirmDialog.cancel();
                    ZKStaffModifyStep1Fragment.this.tvStaffCard.setText(ZKStaffModifyStep1Fragment.this.getString(R.string.zk_staff_info_null));
                    ((ZKStaffModifyActivity) ZKStaffModifyStep1Fragment.this.getActivity()).setCardSmallIcon(false);
                    ZKStaffModifyStep1Fragment.this.sendPush();
                    ZKStaffModifyStep1Fragment.this.judegeVerifyType();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }

            public void success() {
                Intent intent = new Intent(ZKStaffModifyStep1Fragment.this.getActivity(), ZKStaffCardRegistrationActivity.class);
                intent.putExtra("userInfo_id", ZKStaffModifyStep1Fragment.this.userInfo.getID());
                ZKStaffModifyStep1Fragment.this.startActivity(intent);
            }
        });
    }

    private boolean fingerExist(UserInfo userInfo2) {
        List<FpTemplate10> fingerTemplateForUserPin = this.templateManager.getFingerTemplateForUserPin(String.valueOf(userInfo2.getID()));
        return (fingerTemplateForUserPin == null || fingerTemplateForUserPin.size() == 0) ? false : true;
    }

    private boolean palmExist(UserInfo userInfo2) {
        return ZkPalmUtils.isHasPv(userInfo2.getUser_PIN());
    }

    private boolean pswExist(UserInfo userInfo2) {
        return !TextUtils.isEmpty(userInfo2.getPassword());
    }

    private boolean cardExist(UserInfo userInfo2) {
        return !TextUtils.isEmpty(userInfo2.getMain_Card());
    }

    /* access modifiers changed from: private */
    public void judegeVerifyType() {
        int verify_Type = this.userInfo.getVerify_Type();
        if (this.sAccessRuleType == 1) {
            if (this.dataManager.getIntOption("AccessPersonalVerification", 0) != 1) {
                verify_Type = this.dataManager.getIntOption(ZKDBConfig.OPT_DOOR_1_VERIFY_TYPE, 0);
            } else if (verify_Type == -1) {
                verify_Type = this.dataManager.getIntOption(ZKDBConfig.OPT_DOOR_1_VERIFY_TYPE, 0);
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
        int intOption = this.dataManager.getIntOption("FingerFunOn", 0);
        int intOption2 = this.dataManager.getIntOption("hasFingerModule", 0);
        int intOption3 = this.dataManager.getIntOption("PvFunOn", 0);
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
                    if (intOption3 == 0) {
                        showTipDialog(isEnroll(true, false, false, true, false));
                        return;
                    } else {
                        showTipDialog(isEnroll(true, false, false, true, true));
                        return;
                    }
                }
            } else if (!fingerExist(this.userInfo) && !pswExist(this.userInfo) && !HasFaceUtils.isHasFace(this.userInfo) && !ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
                if (intOption3 == 0) {
                    showTipDialog(isEnroll(true, true, false, true, false));
                    return;
                } else {
                    showTipDialog(isEnroll(true, true, false, true, true));
                    return;
                }
            }
        } else if (intOption == 0 || intOption2 == 0) {
            if (!pswExist(this.userInfo) && !HasFaceUtils.isHasFace(this.userInfo) && !cardExist(this.userInfo) && !ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
                if (intOption3 == 0) {
                    showTipDialog(isEnroll(true, false, true, true, false));
                    return;
                } else {
                    showTipDialog(isEnroll(true, false, true, true, true));
                    return;
                }
            }
        } else if (!fingerExist(this.userInfo) && !pswExist(this.userInfo) && !HasFaceUtils.isHasFace(this.userInfo) && !cardExist(this.userInfo) && !ZkPalmUtils.isHasPv(this.userInfo.getUser_PIN())) {
            if (intOption3 == 0) {
                showTipDialog(isEnroll(true, true, true, true, false));
                return;
            } else {
                showTipDialog(isEnroll(true, true, true, true, true));
                return;
            }
        }
        this.tvStaffFace.setTextColor(getResources().getColor(R.color.clr_989898));
        this.tvStaffFinger.setTextColor(getResources().getColor(R.color.clr_989898));
        this.tvStaffPassword.setTextColor(getResources().getColor(R.color.clr_989898));
        this.tvStaffCard.setTextColor(getResources().getColor(R.color.clr_989898));
        this.tvStaffPalm.setTextColor(getResources().getColor(R.color.clr_989898));
    }

    private String isEnroll(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        StringBuilder sb = new StringBuilder();
        if (!z || HasFaceUtils.isHasFace(this.userInfo)) {
            this.tvStaffFace.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            sb.append(getString(R.string.zk_staff_face));
            this.tvStaffFace.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (!z2 || fingerExist(this.userInfo)) {
            this.tvStaffFinger.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            sb.append("、").append(getString(R.string.zk_staff_finger));
            this.tvStaffFinger.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (!z3 || cardExist(this.userInfo)) {
            this.tvStaffCard.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            sb.append("、").append(getString(R.string.zk_staff_card));
            this.tvStaffCard.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (!z4 || pswExist(this.userInfo)) {
            this.tvStaffPassword.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            sb.append("、").append(getString(R.string.zk_staff_pd));
            this.tvStaffPassword.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (!z5 || palmExist(this.userInfo)) {
            this.tvStaffPalm.setTextColor(getResources().getColor(R.color.clr_989898));
        } else {
            sb.append("、").append(getString(R.string.zk_staff_vertify_palmvein));
            this.tvStaffPalm.setTextColor(getResources().getColor(R.color.zk_staff_clr_FF1717));
        }
        if (sb.indexOf("、") == 0) {
            sb.replace(0, 1, "");
        }
        return sb.toString();
    }

    private void showTipDialog(String str) {
        final ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(getActivity());
        zKConfirmDialog.show();
        zKConfirmDialog.setType(1, "", "", getString(R.string.zk_staff_ok));
        zKConfirmDialog.setMessage(getString(R.string.verify_type_check) + str);
        zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
            public void cover() {
            }

            public void failure() {
            }

            public void success() {
                zKConfirmDialog.cancel();
            }
        });
    }

    private boolean isHanveFace() {
        try {
            List query = new PersBiotemplate().getQueryBuilder().where().eq("user_pin", this.userInfo.getUser_PIN()).and().eq("bio_type", 9).query();
            if (query == null || query.size() <= 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    /* access modifiers changed from: private */
    public boolean isCanDeleteVerify(boolean z, UserInfo userInfo2, int i) {
        if (i != 2) {
            if (i != 3) {
                return true;
            }
            return !z || !TextUtils.isEmpty(userInfo2.getMain_Card());
        } else if (!z || !TextUtils.isEmpty(userInfo2.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void showErrorDialog(final ZKConfirmDialog zKConfirmDialog) {
        final ZKConfirmDialog zKConfirmDialog2 = new ZKConfirmDialog(getActivity());
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

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendPush() {
        /*
            r7 = this;
            com.zkteco.android.core.sdk.HubProtocolManager r0 = new com.zkteco.android.core.sdk.HubProtocolManager
            androidx.fragment.app.FragmentActivity r1 = r7.getActivity()
            r0.<init>(r1)
            r1 = 0
            long r3 = r0.convertPushInit()     // Catch:{ Exception -> 0x005e, all -> 0x005b }
            java.lang.String r5 = "USER_INFO"
            r0.setPushTableName(r3, r5)     // Catch:{ Exception -> 0x0059 }
            java.lang.String r5 = "User_PIN"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ Exception -> 0x0059 }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ Exception -> 0x0059 }
            r0.setPushStrField(r3, r5, r6)     // Catch:{ Exception -> 0x0059 }
            java.lang.String r5 = "SEND_FLAG"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ Exception -> 0x0059 }
            int r6 = r6.getSEND_FLAG()     // Catch:{ Exception -> 0x0059 }
            r0.setPushIntField(r3, r5, r6)     // Catch:{ Exception -> 0x0059 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0059 }
            r5.<init>()     // Catch:{ Exception -> 0x0059 }
            java.lang.String r6 = "(User_PIN='"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0059 }
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ Exception -> 0x0059 }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ Exception -> 0x0059 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0059 }
            java.lang.String r6 = "')"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0059 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0059 }
            r0.setPushCon(r3, r5)     // Catch:{ Exception -> 0x0059 }
            r5 = 0
            java.lang.String r6 = ""
            r0.sendHubAction(r5, r3, r6)     // Catch:{ Exception -> 0x0059 }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x006a
            goto L_0x0067
        L_0x0057:
            r5 = move-exception
            goto L_0x006b
        L_0x0059:
            r5 = move-exception
            goto L_0x0060
        L_0x005b:
            r5 = move-exception
            r3 = r1
            goto L_0x006b
        L_0x005e:
            r5 = move-exception
            r3 = r1
        L_0x0060:
            r5.printStackTrace()     // Catch:{ all -> 0x0057 }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x006a
        L_0x0067:
            r0.convertPushFree(r3)
        L_0x006a:
            return
        L_0x006b:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0072
            r0.convertPushFree(r3)
        L_0x0072:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.fragment.ZKStaffModifyStep1Fragment.sendPush():void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendExtPush() {
        /*
            r8 = this;
            com.zkteco.android.core.sdk.HubProtocolManager r0 = new com.zkteco.android.core.sdk.HubProtocolManager
            androidx.fragment.app.FragmentActivity r1 = r8.getActivity()
            r0.<init>(r1)
            r1 = 0
            long r3 = r0.convertPushInit()     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            java.lang.String r5 = "ExtUser"
            r0.setPushTableName(r3, r5)     // Catch:{ Exception -> 0x004a }
            java.lang.String r5 = "ID"
            com.zkteco.android.db.orm.tna.ExtUser r6 = r8.extUser     // Catch:{ Exception -> 0x004a }
            long r6 = r6.getID()     // Catch:{ Exception -> 0x004a }
            int r6 = (int) r6     // Catch:{ Exception -> 0x004a }
            r0.setPushIntField(r3, r5, r6)     // Catch:{ Exception -> 0x004a }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x004a }
            r5.<init>()     // Catch:{ Exception -> 0x004a }
            java.lang.String r6 = "ID="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x004a }
            com.zkteco.android.db.orm.tna.ExtUser r6 = r8.extUser     // Catch:{ Exception -> 0x004a }
            long r6 = r6.getID()     // Catch:{ Exception -> 0x004a }
            int r6 = (int) r6     // Catch:{ Exception -> 0x004a }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x004a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x004a }
            r0.setPushCon(r3, r5)     // Catch:{ Exception -> 0x004a }
            r5 = 0
            java.lang.String r6 = ""
            r0.sendHubAction(r5, r3, r6)     // Catch:{ Exception -> 0x004a }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x005b
            goto L_0x0058
        L_0x0048:
            r5 = move-exception
            goto L_0x005c
        L_0x004a:
            r5 = move-exception
            goto L_0x0051
        L_0x004c:
            r5 = move-exception
            r3 = r1
            goto L_0x005c
        L_0x004f:
            r5 = move-exception
            r3 = r1
        L_0x0051:
            r5.printStackTrace()     // Catch:{ all -> 0x0048 }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x005b
        L_0x0058:
            r0.convertPushFree(r3)
        L_0x005b:
            return
        L_0x005c:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0063
            r0.convertPushFree(r3)
        L_0x0063:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.fragment.ZKStaffModifyStep1Fragment.sendExtPush():void");
    }

    private boolean isEnglish() {
        String language = getResources().getConfiguration().locale.getLanguage();
        return language.endsWith("en") || language.endsWith("es") || language.equals("ru");
    }

    /* access modifiers changed from: private */
    public void showPdDialog() {
        final ZKEditDialog zKEditDialog = new ZKEditDialog(getActivity());
        zKEditDialog.show();
        zKEditDialog.showEye();
        zKEditDialog.setBtnType(2, getString(R.string.zk_staff_cancel), getString(R.string.zk_staff_ok));
        zKEditDialog.setMessage(getString(R.string.zk_staff_pdInfo));
        zKEditDialog.setContentType(3, "");
        zKEditDialog.setEtTop(2, 8, getString(R.string.zk_staff_vertify_password_port));
        zKEditDialog.setEtConf(2, 8, getString(R.string.zk_staff_vertify_correct_password_port));
        zKEditDialog.setListener(new ZKEditDialog.ResultListener() {
            public void failure() {
                ZKStaffModifyStep1Fragment.this.judegeVerifyType();
            }

            public void success() {
                if (ZKStaffModifyStep1Fragment.this.checkPdData(zKEditDialog)) {
                    ZKStaffModifyStep1Fragment.this.userInfo.setPassword(zKEditDialog.getInputText());
                    try {
                        ZKStaffModifyStep1Fragment.this.userInfo.setSEND_FLAG(0);
                        ZKStaffModifyStep1Fragment.this.userInfo.update();
                        zKEditDialog.cancel();
                        ZKStaffModifyStep1Fragment.this.tvStaffPassword.setText(ZKStaffModifyStep1Fragment.this.getString(R.string.zk_staff_password));
                        ((ZKStaffModifyActivity) ZKStaffModifyStep1Fragment.this.getActivity()).setPDSmallIcon(true);
                        ZKStaffModifyStep1Fragment.this.sendPush();
                        ZKStaffModifyStep1Fragment.this.judegeVerifyType();
                        ZKStaffModifyStep1Fragment.this.getActivity().sendBroadcast(new Intent(OpLogReceiver.ACTION_SET_PASSWORD).putExtra("OpWho", ZKStaffModifyStep1Fragment.this.userInfo.getUser_PIN()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
}
