package com.zkteco.android.zkcore.view.barpopwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.zkteco.android.zkcore.R;
import java.util.List;

public class ZKBarPopWindow extends PopupWindow {
    private ZKBarPopAdapter adapter;
    private List<ZKBarPopBean> data;
    private ListView mPopList;

    public ZKBarPopWindow(Context context, List<ZKBarPopBean> list) {
        super(context);
        this.data = list;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_bar_popwindow, (ViewGroup) null);
        this.mPopList = (ListView) inflate.findViewById(R.id.core_pop_list);
        ZKBarPopAdapter zKBarPopAdapter = new ZKBarPopAdapter(context, this.data, R.layout.item_bar_list);
        this.adapter = zKBarPopAdapter;
        this.mPopList.setAdapter(zKBarPopAdapter);
        setContentView(inflate);
        setWidth(300);
        setHeight(-2);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
    }

    public void setItemsOnClick(AdapterView.OnItemClickListener onItemClickListener) {
        this.mPopList.setOnItemClickListener(onItemClickListener);
    }

    public void setData(List<ZKBarPopBean> list) {
        this.data = list;
        this.adapter.notifyDataSetChanged();
    }

    public List<ZKBarPopBean> getData() {
        return this.data;
    }

    public void upData() {
        this.adapter.notifyDataSetChanged();
    }
}
