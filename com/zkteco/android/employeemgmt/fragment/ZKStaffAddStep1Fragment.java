package com.zkteco.android.employeemgmt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.employeemgmt.activity.ZKStaffAddStepActivity;
import com.zkteco.android.employeemgmt.fragment.base.ZKStaffBaseFragment;

public class ZKStaffAddStep1Fragment extends ZKStaffBaseFragment implements View.OnClickListener {
    private Button btn;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_add_step1, viewGroup, false);
        Button button = (Button) inflate.findViewById(R.id.btn_start);
        this.btn = button;
        button.setOnClickListener(this);
        ((ZKStaffAddStepActivity) getActivity()).setUserPinBefore((String) null);
        return inflate;
    }

    public void onClick(View view) {
        if (canTouch()) {
            ((ZKStaffAddStepActivity) getActivity()).setKeyboardState(true);
            pushFragment(R.id.sfl_content, new ZKStaffAddStep2Fragment());
        }
    }
}
