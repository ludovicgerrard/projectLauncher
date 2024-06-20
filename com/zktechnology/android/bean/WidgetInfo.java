package com.zktechnology.android.bean;

import com.zktechnology.android.launcher.R;

public enum WidgetInfo {
    ZK_CHECK_IN(R.string.zk_check_in, R.mipmap.unselected_check_in, R.mipmap.selected_check_in),
    ZK_CHECK_OUT(R.string.zk_check_out, R.mipmap.unselected_check_out, R.mipmap.selected_check_out),
    ZK_GO_OUT(R.string.zk_rec_break_in, R.mipmap.unselected_go_out, R.mipmap.selected_go_out),
    ZK_GoBACK(R.string.zk_rec_break_out, R.mipmap.unselected_goback, R.mipmap.selected_goback),
    ZK_OVERTIME_CHECK_IN(R.string.zk_overtime_check_in, R.mipmap.unselected_overtime_check_in, R.mipmap.selected_overtime_check_in),
    ZK_OVERTIME_CHECK_OUT(R.string.zk_overtime_check_out, R.mipmap.unselected_overtime_check_out, R.mipmap.selected_overtime_check_out);
    
    public final int nameId;
    public final int selectedImageId;
    public final int unselectedImageId;

    private WidgetInfo(int i, int i2, int i3) {
        this.nameId = i;
        this.unselectedImageId = i2;
        this.selectedImageId = i3;
    }
}
