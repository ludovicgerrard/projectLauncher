package com.zkteco.android.employeemgmt.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.strategy.MainThreadExecutor;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.ZKStaffHomeActivity;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.employeemgmt.activity.controller.StaffHomeController;
import com.zkteco.android.employeemgmt.adapter.MyLinearLayoutManager;
import com.zkteco.android.employeemgmt.adapter.ZKStaffHomeAdapter;
import com.zkteco.android.employeemgmt.model.ZKStaffBean;
import com.zkteco.android.employeemgmt.widget.ZKProgressBarDialog;
import com.zkteco.android.zkcore.view.ZKRecycleView;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.view.alert.ZKConfirmDialog;
import com.zkteco.android.zkcore.view.alert.ZKEditDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZKStaffHomeActivity extends ZKStaffBaseActivity implements View.OnClickListener, ZKStaffHomeAdapter.OnGarbageStatus, ZKStaffHomeAdapter.OncheckListener {
    private static final int MAX_SIZE = 15;
    public static final int RESULT_CODE_DELETE_USER = 1001;
    private final String TAG = getClass().getSimpleName();
    /* access modifiers changed from: private */
    public ZKEditDialog alertDialogOc;
    /* access modifiers changed from: private */
    public EditText editSearch;
    /* access modifiers changed from: private */
    public boolean garbageStatus = false;
    private boolean isAddUser = false;
    /* access modifiers changed from: private */
    public boolean isLoadMore;
    /* access modifiers changed from: private */
    public boolean isSelectAll = false;
    /* access modifiers changed from: private */
    public ImageView ivAddAndDel;
    /* access modifiers changed from: private */
    public ImageView ivSelectAll;
    /* access modifiers changed from: private */
    public LinearLayout llSelectAll;
    /* access modifiers changed from: private */
    public ZKStaffHomeAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable;
    /* access modifiers changed from: private */
    public StaffHomeController mController;
    /* access modifiers changed from: private */
    public ZKProgressBarDialog mDeleteDialog;
    private final StaffHomeController.OnListChangeListener mListener = new StaffHomeController.OnListChangeListener() {
        public void startDelete() {
            ZKStaffHomeActivity.this.mDeleteDialog.show();
            ZKStaffHomeActivity.this.enableInactivityTimeout(false);
        }

        public void deleteFinish() {
            if (!ZKStaffHomeActivity.this.isFinishing() && ZKStaffHomeActivity.this.mDeleteDialog != null) {
                ZKStaffHomeActivity.this.mDeleteDialog.dismiss();
                ZKStaffHomeActivity.this.enableInactivityTimeout(true);
                ZKStaffHomeActivity.this.onResume();
                ZKStaffHomeActivity.this.mAdapter.setIsCxShow(false);
                ZKStaffHomeActivity.this.ivAddAndDel.setImageResource(R.mipmap.bt_staff_add);
                ZKStaffHomeActivity.this.ivAddAndDel.setClickable(true);
                ZKStaffHomeActivity.this.ivSelectAll.setImageResource(R.mipmap.cx_unselect);
                boolean unused = ZKStaffHomeActivity.this.isSelectAll = false;
                ZKStaffHomeActivity.this.llSelectAll.setVisibility(8);
                ZKStaffHomeActivity.this.mToolBar.setImgState(false);
                if (ZKStaffHomeActivity.this.getResources().getConfiguration().orientation == 2) {
                    ZKStaffHomeActivity.this.mToolBar.setmEtInputNull();
                } else if (ZKStaffHomeActivity.this.getResources().getConfiguration().orientation == 1) {
                    ZKStaffHomeActivity.this.editSearch.setText((CharSequence) null);
                }
                if (ZKStaffHomeActivity.this.mAdapter.getData().size() == 0) {
                    ZKStaffHomeActivity.this.relNullStaff.setVisibility(0);
                }
                boolean unused2 = ZKStaffHomeActivity.this.garbageStatus = false;
            }
        }

        public void queryFinish(int i, List<ZKStaffBean> list) {
            if (!ZKStaffHomeActivity.this.isFinishing() && ZKStaffHomeActivity.this.mLoadingDialog != null) {
                ZKStaffHomeActivity.this.mLoadingDialog.dismiss();
                ZKStaffHomeActivity.this.mAdapter.setQueryPar(ZKStaffHomeActivity.this.editSearch.getText().toString());
                if (i == 0) {
                    int itemCount = ZKStaffHomeActivity.this.mAdapter.getItemCount();
                    ZKStaffHomeActivity.this.mAdapter.getData().clear();
                    ZKStaffHomeActivity.this.mAdapter.notifyItemRangeRemoved(0, itemCount);
                }
                ZKStaffHomeActivity.this.mAdapter.getData().addAll(list);
                ZKStaffHomeActivity.this.mAdapter.notifyItemRangeInserted(i, ZKStaffHomeActivity.this.mAdapter.getItemCount());
                ZKStaffHomeActivity.this.checkDelSelection();
                ZKStaffHomeActivity zKStaffHomeActivity = ZKStaffHomeActivity.this;
                boolean unused = zKStaffHomeActivity.isLoadMore = zKStaffHomeActivity.mAdapter.getItemCount() > 0;
                if (ZKStaffHomeActivity.this.mAdapter.getData().size() == 0) {
                    ZKStaffHomeActivity.this.mZKRecycleView.setVisibility(8);
                    ZKStaffHomeActivity.this.relNullStaff.setVisibility(0);
                    return;
                }
                ZKStaffHomeActivity.this.mZKRecycleView.setVisibility(0);
                ZKStaffHomeActivity.this.relNullStaff.setVisibility(8);
            }
        }

        public void userUpdate(int i) {
            ZKStaffHomeActivity.this.mAdapter.notifyItemChanged(i);
        }
    };
    /* access modifiers changed from: private */
    public ZKProgressBarDialog mLoadingDialog;
    /* access modifiers changed from: private */
    public ZKToolbar mToolBar;
    /* access modifiers changed from: private */
    public ZKRecycleView mZKRecycleView;
    RefreshReceiver refreshReceiver;
    /* access modifiers changed from: private */
    public RelativeLayout relNullStaff;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_home);
        this.refreshReceiver = new RefreshReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refresh.bio.icon");
        registerReceiver(this.refreshReceiver, intentFilter);
        initToolBar();
        initView();
        this.mController = new StaffHomeController(this, this.mListener);
        loadData(0);
    }

    private void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.homeToolBar);
        this.mToolBar = zKToolbar;
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffHomeActivity.this.lambda$initToolBar$0$ZKStaffHomeActivity(view);
            }
        }, getString(R.string.zk_staff_management));
        int i = isEnglish() ? 128 : 1;
        this.mToolBar.setRightView(R.mipmap.ic_bar_del, R.mipmap.ic_bar_del_t, new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffHomeActivity.this.lambda$initToolBar$1$ZKStaffHomeActivity(view);
            }
        });
        EditText editText = (EditText) findViewById(R.id.edit_search);
        this.editSearch = editText;
        editText.setInputType(i);
        this.editSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(24)});
        addDisposable(RxTextView.textChanges(this.editSearch).debounce(800, TimeUnit.MILLISECONDS).skip(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() {
            public final void accept(Object obj) {
                ZKStaffHomeActivity.this.lambda$initToolBar$2$ZKStaffHomeActivity((CharSequence) obj);
            }
        }));
    }

    public /* synthetic */ void lambda$initToolBar$0$ZKStaffHomeActivity(View view) {
        finish();
    }

    public /* synthetic */ void lambda$initToolBar$1$ZKStaffHomeActivity(View view) {
        if (this.mAdapter.getIsCxShow()) {
            this.mAdapter.setIsCxShow(false);
            this.ivAddAndDel.setImageResource(R.mipmap.bt_staff_add);
            this.ivAddAndDel.setClickable(true);
            this.ivSelectAll.setImageResource(R.mipmap.cx_unselect);
            this.isSelectAll = false;
            this.llSelectAll.setVisibility(8);
            this.mToolBar.setImgState(false);
            return;
        }
        for (ZKStaffBean ckSelected : this.mAdapter.getData()) {
            ckSelected.setCkSelected(false);
        }
        this.mAdapter.setIsCxShow(true);
        ZKStaffHomeAdapter zKStaffHomeAdapter = this.mAdapter;
        zKStaffHomeAdapter.notifyItemRangeChanged(0, zKStaffHomeAdapter.getItemCount(), "payload");
        this.ivAddAndDel.setImageResource(R.mipmap.bt_staff_del_grey);
        this.ivAddAndDel.setClickable(false);
        this.llSelectAll.setVisibility(0);
        this.mToolBar.setImgState(true);
        if (this.mAdapter.getData().size() == 0) {
            this.garbageStatus = false;
        }
    }

    public /* synthetic */ void lambda$initToolBar$2$ZKStaffHomeActivity(CharSequence charSequence) throws Exception {
        loadData(0);
    }

    public void addDisposable(Disposable disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }

    public void clearDisposable() {
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    private void initView() {
        MyLinearLayoutManager myLinearLayoutManager = new MyLinearLayoutManager(this);
        ZKRecycleView zKRecycleView = (ZKRecycleView) findViewById(R.id.recycle_staff_list);
        this.mZKRecycleView = zKRecycleView;
        zKRecycleView.setLayoutManager(myLinearLayoutManager);
        ZKStaffHomeAdapter zKStaffHomeAdapter = new ZKStaffHomeAdapter(this);
        this.mAdapter = zKStaffHomeAdapter;
        zKStaffHomeAdapter.setOnGarbageStatus(this);
        this.mAdapter.setOncheckListener(this);
        this.mZKRecycleView.setAdapter(this.mAdapter);
        this.mZKRecycleView.setRefreshLoadListener(new ZKRecycleView.RefreshLoadListener() {
            public void onRefresh() {
            }

            public /* synthetic */ void lambda$onLoadMore$0$ZKStaffHomeActivity$1() {
                ZKStaffHomeActivity.this.loadMore();
            }

            public void onLoadMore() {
                MainThreadExecutor.getInstance().executeDelayed(new Runnable() {
                    public final void run() {
                        ZKStaffHomeActivity.AnonymousClass1.this.lambda$onLoadMore$0$ZKStaffHomeActivity$1();
                    }
                }, 300);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.iv_addAndDel);
        this.ivAddAndDel = imageView;
        imageView.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_select_all);
        this.llSelectAll = linearLayout;
        linearLayout.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.select_all);
        this.ivSelectAll = imageView2;
        if (this.isSelectAll) {
            imageView2.setImageResource(R.mipmap.cx_select);
        } else {
            imageView2.setImageResource(R.mipmap.cx_unselect);
        }
        this.relNullStaff = (RelativeLayout) findViewById(R.id.rl_null_staff);
        this.mZKRecycleView.setPullRefreshEnable(false);
        this.mDeleteDialog = new ZKProgressBarDialog.Builder(this).setMessage(getResources().getString(R.string.zk_staff_del)).setCancelOutside(false).setCancelable(false).create();
        this.mLoadingDialog = new ZKProgressBarDialog.Builder(this).setMessage(getResources().getString(R.string.loading)).setCancelOutside(false).setCancelable(false).create();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.isAddUser) {
            this.editSearch.setText("");
            loadData(0);
            this.isAddUser = false;
        }
        if (this.mAdapter.isModifyUser()) {
            this.mController.updateUser(this.mAdapter.getData(), this.mAdapter.getOnClickPosition());
            this.mAdapter.setIsModifyUser(false);
        }
    }

    /* access modifiers changed from: private */
    public void loadData(int i) {
        if (!this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.show();
        }
        this.mController.query(this.editSearch.getText().toString(), 15, i);
    }

    /* access modifiers changed from: private */
    public void checkDelSelection() {
        if (this.mAdapter.getIsCxShow()) {
            if (this.mAdapter.getData().size() > 0) {
                Iterator<ZKStaffBean> it = this.mAdapter.getData().iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!it.next().isCkSelected()) {
                            this.isSelectAll = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            } else {
                this.isSelectAll = false;
            }
            if (this.isSelectAll) {
                this.ivSelectAll.setImageResource(R.mipmap.cx_select);
            } else {
                this.ivSelectAll.setImageResource(R.mipmap.cx_unselect);
            }
            onSwitchStatus();
        }
    }

    private boolean isAccess() {
        return this.mController.isAccess();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.iv_addAndDel) {
            addOrDel();
        }
        if (view.getId() == R.id.ll_select_all) {
            selectAll();
        }
    }

    private void selectAll() {
        if (this.isSelectAll) {
            this.ivSelectAll.setImageResource(R.mipmap.cx_unselect);
            if (this.mAdapter.getData() != null && this.mAdapter.getData().size() > 0) {
                for (ZKStaffBean ckSelected : this.mAdapter.getData()) {
                    ckSelected.setCkSelected(false);
                }
                ZKStaffHomeAdapter zKStaffHomeAdapter = this.mAdapter;
                zKStaffHomeAdapter.notifyItemRangeChanged(0, zKStaffHomeAdapter.getItemCount(), "payload");
            }
            this.isSelectAll = false;
        } else {
            this.ivSelectAll.setImageResource(R.mipmap.cx_select);
            if (this.mAdapter.getData() != null && this.mAdapter.getData().size() > 0) {
                for (ZKStaffBean ckSelected2 : this.mAdapter.getData()) {
                    ckSelected2.setCkSelected(true);
                }
                ZKStaffHomeAdapter zKStaffHomeAdapter2 = this.mAdapter;
                zKStaffHomeAdapter2.notifyItemRangeChanged(0, zKStaffHomeAdapter2.getItemCount(), "payload");
            }
            this.isSelectAll = true;
        }
        onSwitchStatus();
    }

    private void addOrDel() {
        if (!this.mAdapter.getIsCxShow()) {
            try {
                if (new UserInfo().countOf() < this.mController.getMaxUserCount()) {
                    ZkAddUserActivity.actionStart(this);
                    this.isAddUser = true;
                    return;
                }
                showToast((Context) this, getResources().getString(R.string.zk_staff_max_user));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (this.garbageStatus) {
            this.alertDialogOc = new ZKEditDialog(this);
            if (!isAccess()) {
                this.alertDialogOc.show();
                this.alertDialogOc.setBtnType(2, getResources().getString(R.string.zk_core_cancel), getResources().getString(R.string.zk_staff_vertify_sure));
                this.alertDialogOc.setContentType(2, getResources().getString(R.string.zk_sycn_clear_att));
                this.alertDialogOc.setMessage(getResources().getString(R.string.zk_clear_user));
                this.alertDialogOc.setListener(new ZKEditDialog.ResultListener() {
                    public void failure() {
                    }

                    public void success() {
                        ZKStaffHomeActivity.this.alertDialogOc.dismiss();
                        ZKStaffHomeActivity.this.mController.deleteItems(ZKStaffHomeActivity.this.mAdapter.getData(), ZKStaffHomeActivity.this.alertDialogOc.getCheckBoxState(), ZKStaffHomeActivity.this);
                    }
                });
                return;
            }
            final ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(this);
            zKConfirmDialog.show();
            zKConfirmDialog.setType(2, getString(R.string.zk_core_cancel), "", getString(R.string.zk_staff_vertify_sure));
            zKConfirmDialog.setMessage(getString(R.string.zk_clear_user));
            zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
                public void cover() {
                }

                public void failure() {
                }

                public void success() {
                    zKConfirmDialog.cancel();
                    ZKStaffHomeActivity.this.mController.deleteItems(ZKStaffHomeActivity.this.mAdapter.getData(), false, ZKStaffHomeActivity.this);
                }
            });
        }
    }

    public void onSwitchStatus() {
        boolean z;
        Iterator<ZKStaffBean> it = this.mAdapter.getData().iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().isCkSelected()) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (z) {
            this.garbageStatus = true;
            this.ivAddAndDel.setClickable(true);
            if (getResources().getConfiguration().orientation == 2) {
                this.ivAddAndDel.setImageResource(R.mipmap.bt_staff_del);
            } else {
                this.ivAddAndDel.setImageResource(R.mipmap.delete_port);
            }
        } else {
            this.garbageStatus = false;
            this.ivAddAndDel.setClickable(false);
            if (getResources().getConfiguration().orientation == 2) {
                this.ivAddAndDel.setImageResource(R.mipmap.bt_staff_del_grey);
            } else {
                this.ivAddAndDel.setImageResource(R.mipmap.delete_gray_port);
            }
        }
    }

    /* access modifiers changed from: private */
    public void loadMore() {
        loadData(this.mAdapter.getItemCount());
        this.mZKRecycleView.setPushRefreshEnable(this.isLoadMore);
        this.mZKRecycleView.setPullLoadMoreCompleted();
    }

    private boolean isEnglish() {
        return getResources().getConfiguration().locale.getLanguage().endsWith("en");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        ZKEditDialog zKEditDialog = this.alertDialogOc;
        if (zKEditDialog != null && zKEditDialog.isShowing()) {
            this.alertDialogOc.dismiss();
            this.alertDialogOc = null;
        }
        StaffHomeController staffHomeController = this.mController;
        if (staffHomeController != null) {
            staffHomeController.release();
        }
        ZKProgressBarDialog zKProgressBarDialog = this.mLoadingDialog;
        if (zKProgressBarDialog != null && zKProgressBarDialog.isShowing()) {
            this.mLoadingDialog.dismiss();
        }
        this.mLoadingDialog = null;
        ZKProgressBarDialog zKProgressBarDialog2 = this.mDeleteDialog;
        if (zKProgressBarDialog2 != null && zKProgressBarDialog2.isShowing()) {
            this.mDeleteDialog.dismiss();
        }
        this.mDeleteDialog = null;
        clearDisposable();
        unregisterReceiver(this.refreshReceiver);
        super.onDestroy();
    }

    public void oncheck() {
        for (ZKStaffBean isCkSelected : this.mAdapter.getData()) {
            if (!isCkSelected.isCkSelected()) {
                this.ivSelectAll.setImageResource(R.mipmap.cx_unselect);
                this.isSelectAll = false;
                return;
            }
        }
        this.ivSelectAll.setImageResource(R.mipmap.cx_select);
        this.isSelectAll = true;
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    class RefreshReceiver extends BroadcastReceiver {
        RefreshReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            ZKStaffHomeActivity.this.loadData(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (intent != null && i2 == 1001) {
            String stringExtra = intent.getStringExtra("User_PIN");
            int i3 = 0;
            while (true) {
                if (i3 >= this.mAdapter.getItemCount()) {
                    i3 = -1;
                    break;
                } else if (this.mAdapter.getItem(i3).getUserInfo().getUser_PIN().equals(stringExtra)) {
                    break;
                } else {
                    i3++;
                }
            }
            if (i3 != -1) {
                this.mAdapter.getData().remove(i3);
                this.mAdapter.notifyItemRemoved(i3);
            }
        }
    }
}
