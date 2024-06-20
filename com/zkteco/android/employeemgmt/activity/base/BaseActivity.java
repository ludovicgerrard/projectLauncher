package com.zkteco.android.employeemgmt.activity.base;

import android.content.Context;
import android.os.Bundle;
import com.zkteco.android.zkcore.base.ZKBaseActivity;

public abstract class BaseActivity extends ZKBaseActivity {
    public Context mContext;

    public abstract int getLayoutResId();

    public abstract void initData();

    public abstract void initListener();

    public abstract void initPresenter();

    public abstract void initUI();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResId());
        this.mContext = this;
        initUI();
        initPresenter();
        initListener();
        initData();
    }
}
