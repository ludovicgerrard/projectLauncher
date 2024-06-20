package com.zkteco.android.zkcore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.zkteco.android.zkcore.R;

public class ZKToolbar2 extends Toolbar {
    private ImageView mBack;
    private ImageView mIvIconBtn;
    private LinearLayout mLeftTitleBtn;
    private ImageView mPop;
    private TextView mRighext;
    private LinearLayout mRightView;
    private TextView mTitle;

    public ZKToolbar2(Context context) {
        this(context, (AttributeSet) null);
    }

    public ZKToolbar2(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZKToolbar2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_toolba_port2, this);
        this.mLeftTitleBtn = (LinearLayout) inflate.findViewById(R.id.core_tb_ll_pop);
        this.mTitle = (TextView) inflate.findViewById(R.id.core_tb_tv_title);
        this.mPop = (ImageView) inflate.findViewById(R.id.core_tb_iv_pop);
        this.mBack = (ImageView) inflate.findViewById(R.id.core_tb_iv_back);
        this.mRightView = (LinearLayout) inflate.findViewById(R.id.core_tb_rl_icon_input);
        this.mIvIconBtn = (ImageView) inflate.findViewById(R.id.core_tb_iv_ii_icon);
        this.mRighext = (TextView) inflate.findViewById(R.id.core_tb_text);
    }

    private void setTitle(String str) {
        this.mTitle.setText(str);
    }

    public void setLeftView(View.OnClickListener onClickListener, String str) {
        this.mPop.setVisibility(8);
        this.mBack.setVisibility(0);
        setTitle(str);
        this.mBack.setOnClickListener(onClickListener);
        this.mLeftTitleBtn.setOnClickListener((View.OnClickListener) null);
    }

    public void setLeftView(String str, String str2, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        this.mPop.setVisibility(0);
        this.mBack.setVisibility(0);
        setTitle(str2);
        this.mLeftTitleBtn.setOnClickListener(onClickListener);
        this.mBack.setOnClickListener(onClickListener2);
    }

    public void setRightView(int i, View.OnClickListener onClickListener) {
        this.mRightView.setVisibility(0);
        this.mIvIconBtn.setVisibility(0);
        this.mIvIconBtn.setImageResource(i);
        this.mRightView.setOnClickListener(onClickListener);
    }

    public void setRightView(String str, View.OnClickListener onClickListener) {
        this.mRighext.setText(str);
        this.mRighext.setOnClickListener(onClickListener);
    }

    public void setRightView(int i, String str, View.OnClickListener onClickListener) {
        this.mRighext.setText(str);
        this.mIvIconBtn.setImageResource(i);
        this.mRightView.setOnClickListener(onClickListener);
    }

    public void setRightViewText(String str) {
        this.mRighext.setText(str);
    }

    public void setPopupArrowVisibility(int i) {
        ImageView imageView = this.mPop;
        if (imageView != null) {
            imageView.setVisibility(i);
        }
    }
}
