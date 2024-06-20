package com.zkteco.android.employeemgmt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.ZKStaffAddStepActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffCardRegistrationActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffFaceActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffFingerprintActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffPasswordSettingActivity;
import com.zkteco.android.employeemgmt.fragment.base.ZKStaffBaseFragment;
import com.zkteco.android.employeemgmt.util.HasFaceUtils;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.liveface562.ZkFaceManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZKStaffAddStep4Fragment extends ZKStaffBaseFragment implements View.OnClickListener {
    private int currFingerCount;
    private int horOrVer = 1;
    private boolean isNewFace = true;
    private GridLayout linear_three_btn;
    private int mMaxFingerCount;
    private TemplateManager templateManager;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_add_step4, viewGroup, false);
        ((ZKStaffAddStepActivity) getActivity()).changeTitle(getString(R.string.zk_staff_sadd_inputver));
        VerifyTypeUtils.init(getActivity());
        initView(inflate);
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    private void initView(View view) {
        this.linear_three_btn = (GridLayout) view.findViewById(R.id.linear_three_btn);
        ((Button) view.findViewById(R.id.btn_complete)).setOnClickListener(this);
        this.templateManager = new TemplateManager(getActivity());
        if (getActivity().getResources().getConfiguration().orientation == 2) {
            this.horOrVer = 1;
        } else {
            this.horOrVer = 2;
        }
        this.mMaxFingerCount = DBManager.getInstance().getIntOption(DBConfig.ABOUT_MAXFINGERCOUNT, 100) * 100;
        this.currFingerCount = (int) this.templateManager.getFingerCount();
        showView();
    }

    private void showView() {
        this.linear_three_btn.removeAllViews();
        for (ImageView addView : getShowViewList()) {
            this.linear_three_btn.addView(addView);
        }
    }

    private List<ImageView> getShowViewList() {
        ArrayList arrayList = new ArrayList();
        try {
            UserInfo userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(((ZKStaffAddStepActivity) getActivity()).getUserInfo().getID()));
            int[] iArr = {R.id.ic_takefinger, R.id.ic_takeface, R.id.ic_takecard, R.id.ic_takepass};
            for (int i = 0; i < 4; i++) {
                int i2 = iArr[i];
                ImageView imageView = new ImageView(getActivity());
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.width = (int) ((getResources().getDimension(R.dimen.zk_staff_width_100) / 2.0f) * 3.0f);
                layoutParams.height = (int) ((getResources().getDimension(R.dimen.zk_staff_width_100) / 2.0f) * 3.0f);
                layoutParams.topMargin = (int) ((getResources().getDimension(R.dimen.zk_staff_margin_20) / 2.0f) * 3.0f);
                layoutParams.leftMargin = (int) ((getResources().getDimension(R.dimen.zk_staff_margin_20) / 2.0f) * 3.0f);
                layoutParams.bottomMargin = (int) ((getResources().getDimension(R.dimen.zk_staff_margin_20) / 2.0f) * 3.0f);
                layoutParams.rightMargin = (int) ((getResources().getDimension(R.dimen.zk_staff_margin_20) / 2.0f) * 3.0f);
                imageView.setLayoutParams(layoutParams);
                imageView.setId(i2);
                imageView.setOnClickListener(this);
                switch (i2) {
                    case R.id.ic_takecard:
                        if (VerifyTypeUtils.enableHaveCard()) {
                            if (userInfo.getMain_Card() != null) {
                                imageView.setImageResource(R.mipmap.ic_take_card_success);
                            } else {
                                imageView.setImageResource(R.mipmap.ic_take_card);
                            }
                            arrayList.add(imageView);
                            break;
                        } else {
                            break;
                        }
                    case R.id.ic_takeface:
                        if (VerifyTypeUtils.enableFace()) {
                            imageView.setImageResource(R.mipmap.ic_take_face);
                            if (isAlgorithmInit()) {
                                if (isFaceAdded()) {
                                    imageView.setImageResource(R.mipmap.ic_take_face_success);
                                } else {
                                    imageView.setImageResource(R.mipmap.ic_take_face);
                                }
                            }
                            arrayList.add(imageView);
                            break;
                        } else {
                            break;
                        }
                    case R.id.ic_takefinger:
                        if (VerifyTypeUtils.enableFinger()) {
                            if (this.templateManager.getFingerTemplateForUserPin(String.valueOf(((ZKStaffAddStepActivity) getActivity()).getUserInfo().getID())).size() > 0) {
                                imageView.setImageResource(R.mipmap.ic_take_finger_success);
                            } else {
                                imageView.setImageResource(R.mipmap.ic_take_finger);
                            }
                            arrayList.add(imageView);
                            break;
                        } else {
                            break;
                        }
                    case R.id.ic_takepass:
                        if (userInfo.getPassword() != null) {
                            imageView.setImageResource(R.mipmap.ic_take_pass_success);
                        } else {
                            imageView.setImageResource(R.mipmap.ic_take_pass);
                        }
                        arrayList.add(imageView);
                        break;
                }
            }
            return arrayList;
        } catch (Exception unused) {
            return new ArrayList();
        }
    }

    public void onResume() {
        super.onResume();
        showView();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.btn_complete) {
            switch (id) {
                case R.id.ic_takecard:
                    Intent intent = new Intent(getActivity(), ZKStaffCardRegistrationActivity.class);
                    intent.putExtra("userInfo_id", ((ZKStaffAddStepActivity) getActivity()).getUserInfo().getID());
                    startActivity(intent);
                    return;
                case R.id.ic_takeface:
                    Intent intent2 = new Intent(getActivity(), ZKStaffFaceActivity.class);
                    intent2.putExtra("userInfo_id", ((ZKStaffAddStepActivity) getActivity()).getUserInfo().getID());
                    if (isHaveFace()) {
                        this.isNewFace = false;
                        intent2.putExtra("isModify", true);
                    }
                    intent2.putExtra("isNewFace", this.isNewFace);
                    startActivity(intent2);
                    return;
                case R.id.ic_takefinger:
                    if (this.currFingerCount >= this.mMaxFingerCount) {
                        showToast(LauncherApplication.getLauncherApplicationContext(), getResources().getString(R.string.zk_staff_fill_finger));
                        return;
                    }
                    Intent intent3 = new Intent(getActivity(), ZKStaffFingerprintActivity.class);
                    intent3.putExtra("userInfo_id", ((ZKStaffAddStepActivity) getActivity()).getUserInfo().getID());
                    intent3.putExtra("action", 1);
                    startActivity(intent3);
                    return;
                case R.id.ic_takepass:
                    Intent intent4 = new Intent(getActivity(), ZKStaffPasswordSettingActivity.class);
                    intent4.putExtra("userInfo_id", ((ZKStaffAddStepActivity) getActivity()).getUserInfo().getID());
                    startActivity(intent4);
                    return;
                default:
                    return;
            }
        } else {
            pushFragment(R.id.sfl_content, new ZKStaffAddStep5Fragment());
            this.isNewFace = true;
        }
    }

    private boolean isHaveFace() {
        try {
            List query = new PersBiotemplate().getQueryBuilder().where().eq("user_pin", ((ZKStaffAddStepActivity) getActivity()).getUserInfo().getUser_PIN()).and().eq("bio_type", 9).query();
            if (query == null || query.size() <= 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isFaceAdded() {
        return HasFaceUtils.isHasFace(((ZKStaffAddStepActivity) getActivity()).getUserInfo());
    }

    private boolean isAlgorithmInit() {
        return ZkFaceManager.getInstance().isInit() == 0;
    }
}
