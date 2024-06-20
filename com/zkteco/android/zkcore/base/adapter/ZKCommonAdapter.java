package com.zkteco.android.zkcore.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public abstract class ZKCommonAdapter<T> extends BaseAdapter {
    private int LayoutId;
    protected Context context;
    protected List<T> datas;
    protected LayoutInflater inflater;

    public abstract void convert(ZKViewHolder zKViewHolder, T t, int i);

    public long getItemId(int i) {
        return (long) i;
    }

    public ZKCommonAdapter(Context context2, List<T> list, int i) {
        this.context = context2;
        this.inflater = LayoutInflater.from(context2);
        this.datas = list;
        this.LayoutId = i;
    }

    public List<T> getDatas() {
        return this.datas;
    }

    public void setDatas(List<T> list) {
        this.datas = list;
    }

    public int getCount() {
        return this.datas.size();
    }

    public T getItem(int i) {
        return this.datas.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ZKViewHolder zKViewHolder = ZKViewHolder.get(this.context, view, viewGroup, this.LayoutId, i);
        convert(zKViewHolder, getItem(i), i);
        return zKViewHolder.getConvertView();
    }
}
