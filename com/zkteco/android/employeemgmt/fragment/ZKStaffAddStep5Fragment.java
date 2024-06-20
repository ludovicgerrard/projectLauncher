package com.zkteco.android.employeemgmt.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.ZKStaffAddStepActivity;
import com.zkteco.android.employeemgmt.fragment.base.ZKStaffBaseFragment;
import com.zkteco.android.employeemgmt.widget.ZKCircleImageView;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import java.sql.SQLException;

public class ZKStaffAddStep5Fragment extends ZKStaffBaseFragment implements View.OnClickListener {
    private Activity activity;
    private ZKCircleImageView iv_user;
    private int maxUserCount;
    private TextView tv_ID;
    private TextView tv_num;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_add_step5, viewGroup, false);
        ((ZKStaffAddStepActivity) getActivity()).changeTitle(getString(R.string.zk_staff_sadd_title));
        initView(inflate);
        this.activity = getActivity();
        String strOption = DBManager.getInstance().getStrOption("~MaxUserCount", "100");
        if (strOption != null) {
            this.maxUserCount = Integer.parseInt(strOption) * 100;
        }
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        UserInfo userInfo = ((ZKStaffAddStepActivity) getActivity()).getUserInfo();
        if (userInfo != null && this.activity != null) {
            this.tv_num.setText(this.activity.getResources().getString(R.string.zk_staff_end_pin) + userInfo.getUser_PIN());
            this.tv_ID.setText(getActivity().getResources().getString(R.string.zk_staff_end_id) + userInfo.getName());
            ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) Glide.with((Fragment) this).load(ZKFilePath.PICTURE_PATH + userInfo.getUser_PIN() + ".jpg").skipMemoryCache(true)).diskCacheStrategy(DiskCacheStrategy.NONE)).error((int) R.mipmap.ic_pic_null)).into((ImageView) this.iv_user);
        }
    }

    public void onResume() {
        super.onResume();
    }

    private void initView(View view) {
        this.iv_user = (ZKCircleImageView) view.findViewById(R.id.iv_user);
        this.tv_ID = (TextView) view.findViewById(R.id.tv_ID);
        this.tv_num = (TextView) view.findViewById(R.id.tv_num);
        ((Button) view.findViewById(R.id.btn_continue)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btn_next_vertify)).setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                try {
                    if (new UserInfo().countOf() < ((long) this.maxUserCount)) {
                        getActivity().getFragmentManager().popBackStackImmediate(ZKStaffAddStep2Fragment.class.getName(), 1);
                        return;
                    } else {
                        showToast((Context) getActivity(), getResources().getString(R.string.zk_staff_max_user));
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.btn_next_vertify:
                ((ZKStaffAddStepActivity) this.activity).finish();
                return;
            default:
                return;
        }
    }
}
