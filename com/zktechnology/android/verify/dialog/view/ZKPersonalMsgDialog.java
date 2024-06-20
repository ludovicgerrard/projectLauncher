package com.zktechnology.android.verify.dialog.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.bean.ZKPersonMsg;
import com.zktechnology.android.verify.dao.ZKPersonMsgDao;
import com.zktechnology.android.verify.dialog.view.util.ZKListenableActionLayout;
import java.util.ArrayList;
import java.util.List;

public class ZKPersonalMsgDialog extends ZKTimeOutDialog {
    public static final String TAG = "com.zktechnology.android.verify.dialog.view.ZKPersonalMsgDialog";
    private ZKPersonMsgDao dao;
    private List<ZKPersonMsg> data = new ArrayList();
    private ImageView iv_close;
    private int mIndex = 1;
    private RelativeLayout rl_null_data;
    private ScrollView sv_data;
    private TextView tv_content;
    private TextView tv_count;
    private TextView tv_date;
    private TextView tv_last;
    private TextView tv_next;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View inflate = layoutInflater.inflate(R.layout.personal_msg_dialog, viewGroup);
        setVerifiedInfoTimeOutPersonalMsg();
        resetInactiveTimeout();
        if (inflate instanceof ZKListenableActionLayout) {
            ((ZKListenableActionLayout) inflate).setOnUserInteractiveListener(new ZKListenableActionLayout.OnUserInteractionListener() {
                public final void onUserInteraction() {
                    ZKPersonalMsgDialog.this.resetInactiveTimeout();
                }
            });
        }
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.dao = new ZKPersonMsgDao(DBManager.getInstance());
        initData();
        initView(view);
        initListener();
    }

    private void initView(View view) {
        this.tv_count = (TextView) view.findViewById(R.id.tv_count);
        this.tv_date = (TextView) view.findViewById(R.id.tv_date);
        this.iv_close = (ImageView) view.findViewById(R.id.iv_close);
        this.tv_content = (TextView) view.findViewById(R.id.tv_content);
        this.tv_last = (TextView) view.findViewById(R.id.tv_last);
        this.tv_next = (TextView) view.findViewById(R.id.tv_next);
        this.rl_null_data = (RelativeLayout) view.findViewById(R.id.rl_null_data);
        this.sv_data = (ScrollView) view.findViewById(R.id.sv_data);
        List<ZKPersonMsg> list = this.data;
        if (list == null || list.size() <= 0) {
            this.rl_null_data.setVisibility(0);
            this.sv_data.setVisibility(8);
            this.tv_date.setVisibility(8);
            this.tv_count.setVisibility(8);
            return;
        }
        this.rl_null_data.setVisibility(8);
        this.sv_data.setVisibility(0);
        this.tv_date.setVisibility(0);
        this.tv_count.setVisibility(0);
        this.tv_count.setText("（" + this.mIndex + "/" + this.data.size() + "）");
        this.tv_date.setText(this.data.get(0).getStart_Time());
        this.tv_content.setText(this.data.get(0).getContent());
    }

    private void initData() {
        this.data = this.dao.queryPersonMsg(getArguments().getString("userPin"));
    }

    private void initListener() {
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return ZKPersonalMsgDialog.this.lambda$initListener$0$ZKPersonalMsgDialog(dialogInterface, i, keyEvent);
            }
        });
        this.iv_close.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ZKPersonalMsgDialog.this.lambda$initListener$1$ZKPersonalMsgDialog(view);
            }
        });
        this.tv_last.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ZKPersonalMsgDialog.this.lambda$initListener$2$ZKPersonalMsgDialog(view);
            }
        });
        this.tv_next.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ZKPersonalMsgDialog.this.lambda$initListener$3$ZKPersonalMsgDialog(view);
            }
        });
    }

    public /* synthetic */ boolean lambda$initListener$0$ZKPersonalMsgDialog(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 0) {
            return false;
        }
        dismiss();
        return true;
    }

    public /* synthetic */ void lambda$initListener$1$ZKPersonalMsgDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initListener$2$ZKPersonalMsgDialog(View view) {
        List<ZKPersonMsg> list;
        if (this.mIndex != 1 && (list = this.data) != null && list.size() > 0) {
            this.mIndex--;
            updateUi();
        }
    }

    public /* synthetic */ void lambda$initListener$3$ZKPersonalMsgDialog(View view) {
        List<ZKPersonMsg> list;
        if (this.mIndex != this.data.size() && (list = this.data) != null && list.size() > 0) {
            this.mIndex++;
            updateUi();
        }
    }

    private void updateUi() {
        this.tv_count.setText("（" + this.mIndex + "/" + this.data.size() + "）");
        this.tv_date.setText(this.data.get(this.mIndex - 1).getStart_Time());
        this.tv_content.setText(this.data.get(this.mIndex - 1).getContent());
    }
}
