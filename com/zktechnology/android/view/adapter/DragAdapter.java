package com.zktechnology.android.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class DragAdapter extends BaseAdapter {
    public abstract void addNewData(Object obj);

    public View copyView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    public abstract Object getSwapData(int i);

    public boolean isUseCopyView() {
        return false;
    }

    public abstract void onDataModelMove(int i, int i2);

    public abstract void removeData(int i);
}
