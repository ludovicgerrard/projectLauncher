package com.zktechnology.android.launcher2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.verify.bean.ZKNotifyBeen;
import com.zktechnology.android.view.adapter.NotifyInfoAdapter;
import java.util.ArrayList;
import java.util.List;

public class NotifyInfoDialog extends Dialog {
    private Context context;
    private NotifyInfoAdapter notifyInfoAdapter;
    private List<ZKNotifyBeen> notifyList = new ArrayList();
    private RecyclerView recyclerView;
    private TextView tvTitle;

    public NotifyInfoDialog(Context context2) {
        super(context2, R.style.initdialogstyle);
        this.context = context2.getApplicationContext();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_notifyinfo);
        setCanceledOnTouchOutside(true);
        initView();
        initDate();
    }

    public void release() {
        this.context = null;
    }

    private void initView() {
        this.tvTitle = (TextView) findViewById(R.id.notifyinfo_title);
        this.recyclerView = (RecyclerView) findViewById(R.id.notifyinfo_recyclerview);
    }

    private void initDate() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        NotifyInfoAdapter notifyInfoAdapter2 = new NotifyInfoAdapter(this.context, this.notifyList);
        this.notifyInfoAdapter = notifyInfoAdapter2;
        this.recyclerView.setAdapter(notifyInfoAdapter2);
    }

    public void updateList(int i) {
        this.notifyList.clear();
        switch (i) {
            case 1:
                this.tvTitle.setText(R.string.cloud_server);
                ZKNotifyBeen zKNotifyBeen = new ZKNotifyBeen();
                zKNotifyBeen.setIcon(this.context.getResources().getDrawable(R.drawable.no_network_connect));
                zKNotifyBeen.setDetails(this.context.getResources().getString(R.string.no_connect_cloud_server));
                ZKNotifyBeen zKNotifyBeen2 = new ZKNotifyBeen();
                zKNotifyBeen2.setIcon(this.context.getResources().getDrawable(R.drawable.connet_server));
                zKNotifyBeen2.setDetails(this.context.getResources().getString(R.string.connect_cloud_server));
                ZKNotifyBeen zKNotifyBeen3 = new ZKNotifyBeen();
                zKNotifyBeen3.setIcon(this.context.getResources().getDrawable(R.drawable.server_data));
                zKNotifyBeen3.setDetails(this.context.getResources().getString(R.string.cloud_server_data));
                this.notifyList.add(zKNotifyBeen);
                this.notifyList.add(zKNotifyBeen2);
                this.notifyList.add(zKNotifyBeen3);
                break;
            case 2:
                this.tvTitle.setText(R.string.ethernet);
                ZKNotifyBeen zKNotifyBeen4 = new ZKNotifyBeen();
                zKNotifyBeen4.setIcon(this.context.getResources().getDrawable(R.drawable.no_ehernet));
                zKNotifyBeen4.setDetails(this.context.getResources().getString(R.string.no_connect_ethernet));
                ZKNotifyBeen zKNotifyBeen5 = new ZKNotifyBeen();
                zKNotifyBeen5.setIcon(this.context.getResources().getDrawable(R.drawable.conn_ethernet));
                zKNotifyBeen5.setDetails(this.context.getResources().getString(R.string.connect_ethernet));
                this.notifyList.add(zKNotifyBeen4);
                this.notifyList.add(zKNotifyBeen5);
                break;
            case 3:
                this.tvTitle.setText("Wifi");
                ZKNotifyBeen zKNotifyBeen6 = new ZKNotifyBeen();
                zKNotifyBeen6.setIcon(this.context.getResources().getDrawable(R.drawable.wifi_not_connected));
                zKNotifyBeen6.setDetails(this.context.getResources().getString(R.string.no_connect_wifi));
                ZKNotifyBeen zKNotifyBeen7 = new ZKNotifyBeen();
                zKNotifyBeen7.setIcon(this.context.getResources().getDrawable(R.drawable.wifi4));
                zKNotifyBeen7.setDetails(this.context.getResources().getString(R.string.connect_wifi));
                ZKNotifyBeen zKNotifyBeen8 = new ZKNotifyBeen();
                zKNotifyBeen8.setIcon(this.context.getResources().getDrawable(R.drawable.nowifi));
                zKNotifyBeen8.setDetails(this.context.getResources().getString(R.string.no_open_wifi));
                this.notifyList.add(zKNotifyBeen8);
                this.notifyList.add(zKNotifyBeen6);
                this.notifyList.add(zKNotifyBeen7);
                break;
            case 4:
                this.tvTitle.setText(R.string.udisk);
                ZKNotifyBeen zKNotifyBeen9 = new ZKNotifyBeen();
                zKNotifyBeen9.setIcon(this.context.getResources().getDrawable(R.drawable.udisk));
                zKNotifyBeen9.setDetails(this.context.getResources().getString(R.string.connect_udisk));
                this.notifyList.add(zKNotifyBeen9);
                break;
            case 5:
                this.tvTitle.setText(R.string.alarm);
                ZKNotifyBeen zKNotifyBeen10 = new ZKNotifyBeen();
                zKNotifyBeen10.setIcon(this.context.getResources().getDrawable(R.drawable.alarm));
                zKNotifyBeen10.setDetails(this.context.getResources().getString(R.string.in_the_alarm));
                this.notifyList.add(zKNotifyBeen10);
                break;
            case 6:
                this.tvTitle.setText(R.string.direction_of_the_door);
                ZKNotifyBeen zKNotifyBeen11 = new ZKNotifyBeen();
                zKNotifyBeen11.setIcon(this.context.getResources().getDrawable(R.drawable.in));
                zKNotifyBeen11.setDetails(this.context.getResources().getString(R.string.in_door));
                ZKNotifyBeen zKNotifyBeen12 = new ZKNotifyBeen();
                zKNotifyBeen12.setIcon(this.context.getResources().getDrawable(R.drawable.out));
                zKNotifyBeen12.setDetails(this.context.getResources().getString(R.string.out_door));
                this.notifyList.add(zKNotifyBeen11);
                this.notifyList.add(zKNotifyBeen12);
                break;
            case 7:
                this.tvTitle.setText(R.string.auxiliary_input_function);
                ZKNotifyBeen zKNotifyBeen13 = new ZKNotifyBeen();
                zKNotifyBeen13.setIcon(this.context.getResources().getDrawable(R.drawable.in_out));
                zKNotifyBeen13.setDetails(this.context.getResources().getString(R.string.auxiliary_input_function_open));
                this.notifyList.add(zKNotifyBeen13);
                break;
            case 8:
                this.tvTitle.setText(R.string.door_alaways_open);
                ZKNotifyBeen zKNotifyBeen14 = new ZKNotifyBeen();
                zKNotifyBeen14.setIcon(this.context.getResources().getDrawable(R.drawable.open_always));
                zKNotifyBeen14.setDetails(this.context.getResources().getString(R.string.door_in_alaways_open));
                ZKNotifyBeen zKNotifyBeen15 = new ZKNotifyBeen();
                zKNotifyBeen15.setIcon(this.context.getResources().getDrawable(R.drawable.open_always_invalid));
                zKNotifyBeen15.setDetails(this.context.getResources().getString(R.string.door_not_in_alaways_open));
                this.notifyList.add(zKNotifyBeen14);
                this.notifyList.add(zKNotifyBeen15);
                break;
            case 9:
                this.tvTitle.setText(R.string.sensor_state);
                ZKNotifyBeen zKNotifyBeen16 = new ZKNotifyBeen();
                zKNotifyBeen16.setIcon(this.context.getResources().getDrawable(R.drawable.sense_open));
                zKNotifyBeen16.setDetails(this.context.getResources().getString(R.string.sensor_open));
                ZKNotifyBeen zKNotifyBeen17 = new ZKNotifyBeen();
                zKNotifyBeen17.setIcon(this.context.getResources().getDrawable(R.drawable.sense_close));
                zKNotifyBeen17.setDetails(this.context.getResources().getString(R.string.sensor_close));
                this.notifyList.add(zKNotifyBeen16);
                this.notifyList.add(zKNotifyBeen17);
                break;
        }
        this.notifyInfoAdapter.setNotifyList(this.notifyList);
    }
}
