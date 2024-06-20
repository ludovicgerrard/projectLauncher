package com.zkteco.android.employeemgmt.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.ZKStaffAddStepActivity;
import com.zkteco.android.employeemgmt.fragment.base.ZKStaffBaseFragment;
import com.zkteco.android.zkcore.utils.FileUtil;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class ZKStaffAddStep2Fragment extends ZKStaffBaseFragment implements View.OnClickListener {
    private int isSupportABCPin;
    /* access modifiers changed from: private */
    public EditText mEtLastName;
    /* access modifiers changed from: private */
    public EditText mEtName;
    private EditText mEtPin;
    private int mHasSupportABCPin;
    private TextView mTvLastNameHint;
    private TextView mTvNameHint;
    /* access modifiers changed from: private */
    public TextView mTvPinHint;
    /* access modifiers changed from: private */
    public int maxNameLength = 24;
    private String pinWidthStr;
    private int sAccessRuleType;
    private TextView tvLastName;
    private UserInfo userInfo;
    private View viewLastName;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_add_step2, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View view) {
        if (getResources().getConfiguration().orientation == 2) {
            ((ImageButton) view.findViewById(R.id.ib_left2)).setOnClickListener(this);
            ((ImageButton) view.findViewById(R.id.ib_right2)).setOnClickListener(this);
        } else {
            ((Button) view.findViewById(R.id.ib_left2)).setOnClickListener(this);
            ((Button) view.findViewById(R.id.ib_right2)).setOnClickListener(this);
        }
        this.mEtName = (EditText) view.findViewById(R.id.et_name);
        this.mEtLastName = (EditText) view.findViewById(R.id.et_last_name);
        this.mEtPin = (EditText) view.findViewById(R.id.et_pin);
        this.mTvPinHint = (TextView) view.findViewById(R.id.tv_pin_hint);
        this.mTvNameHint = (TextView) view.findViewById(R.id.tv_name_hint);
        this.mTvLastNameHint = (TextView) view.findViewById(R.id.tv_last_name_hint);
        this.viewLastName = view.findViewById(R.id.view_last_name);
        this.tvLastName = (TextView) view.findViewById(R.id.tv_last_name);
        if (isEnglishOrSpanish()) {
            setEditText(128);
        } else {
            setEditText(1);
        }
        this.mEtName.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                int selectionStart = ZKStaffAddStep2Fragment.this.mEtName.getSelectionStart();
                int selectionEnd = ZKStaffAddStep2Fragment.this.mEtName.getSelectionEnd();
                ZKStaffAddStep2Fragment.this.mEtName.removeTextChangedListener(this);
                boolean z = false;
                if (!TextUtils.isEmpty(editable.toString())) {
                    while (ZKStaffAddStep2Fragment.this.calculateLength(editable.toString()) > ZKStaffAddStep2Fragment.this.maxNameLength) {
                        selectionStart--;
                        editable.delete(selectionStart, selectionEnd);
                        z = true;
                        selectionEnd--;
                    }
                }
                if (z) {
                    ZKStaffAddStep2Fragment.this.mEtName.setText(editable);
                    ZKStaffAddStep2Fragment.this.mEtName.setSelection(selectionStart);
                }
                ZKStaffAddStep2Fragment.this.mEtName.addTextChangedListener(this);
            }
        });
        this.mEtLastName.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                int selectionStart = ZKStaffAddStep2Fragment.this.mEtLastName.getSelectionStart();
                int selectionEnd = ZKStaffAddStep2Fragment.this.mEtLastName.getSelectionEnd();
                ZKStaffAddStep2Fragment.this.mEtLastName.removeTextChangedListener(this);
                boolean z = false;
                if (!TextUtils.isEmpty(editable.toString())) {
                    while (ZKStaffAddStep2Fragment.this.calculateLength(editable.toString()) > ZKStaffAddStep2Fragment.this.maxNameLength) {
                        selectionStart--;
                        editable.delete(selectionStart, selectionEnd);
                        z = true;
                        selectionEnd--;
                    }
                }
                if (z) {
                    ZKStaffAddStep2Fragment.this.mEtLastName.setText(editable);
                    ZKStaffAddStep2Fragment.this.mEtLastName.setSelection(selectionStart);
                }
                ZKStaffAddStep2Fragment.this.mEtLastName.addTextChangedListener(this);
            }
        });
        this.mEtPin.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                String obj = editable.toString();
                if (obj.length() >= 2) {
                    obj = obj.substring(0, 1);
                }
                if (obj.equals("0")) {
                    editable.clear();
                    boolean unused = ZKStaffAddStep2Fragment.this.checkPinData(true);
                    ZKStaffAddStep2Fragment.this.mTvPinHint.setVisibility(0);
                    ZKStaffAddStep2Fragment.this.mTvPinHint.startAnimation(AnimationUtils.loadAnimation(ZKStaffAddStep2Fragment.this.getActivity(), R.anim.anim_shake));
                }
            }
        });
    }

    private void setEditText(int i) {
        this.mEtName.setInputType(i);
        this.mEtLastName.setInputType(i);
    }

    public void onActivityCreated(Bundle bundle) {
        if (((ZKStaffAddStepActivity) getActivity()).isKeyboardState()) {
            hideSoftKeyboard(false);
        } else {
            hideSoftKeyboard(true);
        }
        this.userInfo = new UserInfo();
        super.onActivityCreated(bundle);
    }

    /* access modifiers changed from: private */
    public int calculateLength(String str) {
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char c2 = charArray[i2];
            i = ((c2 < 11904 || c2 > 65103) && (c2 < 41279 || c2 > 43584) && c2 < 128) ? i + 1 : i + 2;
        }
        return i;
    }

    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        try {
            DataManager instance = DBManager.getInstance();
            this.isSupportABCPin = instance.getIntOption("IsSupportABCPin", 0);
            this.mHasSupportABCPin = instance.getIntOption("HasSupportABCPin", 0);
            this.pinWidthStr = instance.getStrOption(ZKDBConfig.OPT_PIN2WIDTH, "9");
            this.sAccessRuleType = instance.getIntOption("AccessRuleType", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int intValue = Integer.valueOf(this.pinWidthStr).intValue();
        if (this.mHasSupportABCPin != 1) {
            this.mEtPin.setInputType(2);
        } else if (this.isSupportABCPin == 0) {
            this.mEtPin.setInputType(2);
        }
        this.mEtPin.setFilters(new InputFilter[]{new InputFilter.LengthFilter(intValue)});
        if (ZKLauncher.longName == 1) {
            this.maxNameLength = 12;
            this.mEtName.setHint(R.string.first_name);
            this.mEtLastName.setVisibility(0);
            this.tvLastName.setVisibility(0);
            this.viewLastName.setVisibility(0);
            this.mTvLastNameHint.setVisibility(4);
            return;
        }
        this.maxNameLength = 24;
        this.mEtName.setHint(R.string.zk_staff_sadd_name);
        this.mEtLastName.setVisibility(8);
        this.tvLastName.setVisibility(8);
        this.viewLastName.setVisibility(8);
        this.mTvLastNameHint.setVisibility(8);
    }

    public void onClick(View view) {
        String str;
        if (canTouch()) {
            int id = view.getId();
            if (id == R.id.ib_left2) {
                hideSoftKeyboard(true);
                finishFragment();
            } else if (id != R.id.ib_right2 || !checkNameData()) {
            } else {
                if (checkPinData(false)) {
                    this.mTvPinHint.setVisibility(4);
                    String trim = this.mEtLastName.getText().toString().trim();
                    if (TextUtils.isEmpty(trim)) {
                        str = this.mEtName.getText().toString().trim();
                    } else {
                        str = this.mEtName.getText().toString().trim() + " " + trim;
                    }
                    this.userInfo.setName(str);
                    this.userInfo.setUser_PIN(this.mEtPin.getText().toString());
                    this.userInfo.setPrivilege(0);
                    if (this.sAccessRuleType == 1) {
                        this.userInfo.setVerify_Type(-1);
                        this.userInfo.setAcc_Group_ID(0);
                    }
                    hideSoftKeyboard(true);
                    ((ZKStaffAddStepActivity) getActivity()).setUserInfo(this.userInfo);
                    if (((ZKStaffAddStepActivity) getActivity()).getUserPinBefore() != null && !((ZKStaffAddStepActivity) getActivity()).getUserPinBefore().equals(this.userInfo.getUser_PIN())) {
                        FileUtil.deleteFile(ZKFilePath.PICTURE_PATH + ((ZKStaffAddStepActivity) getActivity()).getUserPinBefore() + ".jpg", getActivity());
                    }
                    ((ZKStaffAddStepActivity) getActivity()).setUserPinBefore(this.userInfo.getUser_PIN());
                    pushFragment(R.id.sfl_content, new ZKStaffAddStep3Fragment());
                    return;
                }
                this.mTvPinHint.setVisibility(0);
                this.mTvPinHint.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_shake));
            }
        }
    }

    private boolean checkNameData() {
        Animation loadAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_shake);
        if (ZKLauncher.longName == 1) {
            if (TextUtils.isEmpty(this.mEtName.getText().toString().replace(" ", ""))) {
                this.mTvNameHint.setText(getString(R.string.no_first_name));
                this.mTvNameHint.setVisibility(0);
                this.mTvNameHint.startAnimation(loadAnimation);
                return false;
            }
            this.mTvNameHint.setVisibility(4);
            if (TextUtils.isEmpty(this.mEtLastName.getText().toString().replace(" ", ""))) {
                this.mTvLastNameHint.setText(getString(R.string.no_last_name));
                this.mTvLastNameHint.setVisibility(0);
                this.mTvLastNameHint.startAnimation(loadAnimation);
                return false;
            }
            this.mTvLastNameHint.setVisibility(4);
        } else if (TextUtils.isEmpty(this.mEtName.getText().toString().replace(" ", ""))) {
            this.mTvNameHint.setText(getString(R.string.zk_staff_sadd_plename));
            this.mTvNameHint.setVisibility(0);
            this.mTvNameHint.startAnimation(loadAnimation);
            return false;
        } else {
            this.mTvNameHint.setVisibility(4);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean checkPinData(boolean z) {
        if (z) {
            this.mTvPinHint.setText(getString(R.string.zk_staff_sadd_pinfirst_notzero));
            return false;
        } else if (TextUtils.isEmpty(this.mEtPin.getText().toString())) {
            this.mTvPinHint.setText(getString(R.string.zk_staff_sadd_plepin));
            return false;
        } else {
            this.mTvPinHint.setVisibility(4);
            if (this.mHasSupportABCPin == 1) {
                int i = this.isSupportABCPin;
                if (i == 0) {
                    if (!Pattern.compile("^[0-9]*$").matcher(this.mEtPin.getText().toString()).matches()) {
                        this.mTvPinHint.setText(getString(R.string.zk_staff_sadd_pinonly_number));
                        return false;
                    }
                } else if (i == 1 && !Pattern.compile("^[A-Za-z0-9]+$").matcher(this.mEtPin.getText().toString()).matches()) {
                    this.mTvPinHint.setText(getString(R.string.zk_staff_sadd_pinboth_numAndlet));
                    return false;
                }
            } else if (!Pattern.compile("^[0-9]*$").matcher(this.mEtPin.getText().toString()).matches()) {
                this.mTvPinHint.setText(getString(R.string.zk_staff_sadd_pinonly_number));
                return false;
            }
            List list = null;
            try {
                list = new UserInfo().getQueryBuilder().where().eq("User_PIN", this.mEtPin.getText().toString()).query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (list == null || list.size() <= 0) {
                return true;
            }
            this.mTvPinHint.setText(getString(R.string.zk_staff_sadd_pinexist));
            return false;
        }
    }

    private void hideSoftKeyboard(boolean z) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
        if (z && getActivity().getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private boolean isEnglishOrSpanish() {
        String language = getResources().getConfiguration().locale.getLanguage();
        return language.endsWith("en") || language.endsWith("es") || language.equals("ru");
    }
}
