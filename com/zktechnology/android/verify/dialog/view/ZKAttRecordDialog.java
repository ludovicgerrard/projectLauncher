package com.zktechnology.android.verify.dialog.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.dao.ZKAttRecordDao;
import com.zktechnology.android.verify.dialog.view.adapter.ZKAttRecordAdapter;
import com.zktechnology.android.verify.dialog.view.util.ZKListenableActionLayout;

public class ZKAttRecordDialog extends ZKTimeOutDialog {
    public static final String TAG = "com.zktechnology.android.verify.dialog.view.ZKAttRecordDialog";
    private TextView attDialogConfirm;
    private ListView attDialogList;
    private ZKAttRecordDao dao;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.MyDialogStyle);
        setCancelable(false);
        this.dao = new ZKAttRecordDao(DBManager.getInstance());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View inflate = layoutInflater.inflate(R.layout.att_msg_dialog, viewGroup);
        setVerifiedInfoTimeOutMs();
        resetInactiveTimeout();
        if (inflate instanceof ZKListenableActionLayout) {
            ((ZKListenableActionLayout) inflate).setOnUserInteractiveListener(new ZKListenableActionLayout.OnUserInteractionListener() {
                public void onUserInteraction() {
                    ZKAttRecordDialog.this.resetInactiveTimeout();
                }
            });
        }
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.dao = new ZKAttRecordDao(DBManager.getInstance());
        initView(view);
        initData(this.mContext);
        initListener();
    }

    private void initData(Context context) {
        ZKAttRecordAdapter zKAttRecordAdapter = new ZKAttRecordAdapter(context);
        this.attDialogList.setAdapter(zKAttRecordAdapter);
        Bundle arguments = getArguments();
        zKAttRecordAdapter.updateData(this.dao.queryAttRecord(arguments.getString("userPin"), arguments.getString("userName")));
        zKAttRecordAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        this.attDialogList = (ListView) view.findViewById(R.id.att_dialog_list);
        this.attDialogConfirm = (TextView) view.findViewById(R.id.att_dialog_confirm);
    }

    private void initListener() {
        this.attDialogConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZKAttRecordDialog.this.dismiss();
            }
        });
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4 || keyEvent.getAction() != 0) {
                    return false;
                }
                ZKAttRecordDialog.this.dismiss();
                return true;
            }
        });
    }
}
