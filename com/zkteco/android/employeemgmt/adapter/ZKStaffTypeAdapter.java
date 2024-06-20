package com.zkteco.android.employeemgmt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.employeemgmt.model.ZkStaffTypeModel;
import java.util.List;

public class ZKStaffTypeAdapter extends BaseAdapter {
    private Context context;
    private List<ZkStaffTypeModel> mData;

    public ZKStaffTypeAdapter(Context context2) {
        this.context = context2;
    }

    public void setmData(List<ZkStaffTypeModel> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        List<ZkStaffTypeModel> list = this.mData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public Object getItem(int i) {
        return this.mData.get(i);
    }

    public long getItemId(int i) {
        return (long) this.mData.get(i).getId();
    }

    public int getItemViewType(int i) {
        return this.mData.get(i).getType();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r7, android.view.View r8, android.view.ViewGroup r9) {
        /*
            r6 = this;
            java.util.List<com.zkteco.android.employeemgmt.model.ZkStaffTypeModel> r9 = r6.mData
            java.lang.Object r7 = r9.get(r7)
            com.zkteco.android.employeemgmt.model.ZkStaffTypeModel r7 = (com.zkteco.android.employeemgmt.model.ZkStaffTypeModel) r7
            int r9 = r7.getType()
            if (r8 != 0) goto L_0x0012
            android.view.View r8 = r6.initConvertView(r9)
        L_0x0012:
            r0 = 3
            r1 = 2
            r2 = 0
            r3 = 1
            if (r9 == r3) goto L_0x0043
            if (r9 == r1) goto L_0x0030
            if (r9 == r0) goto L_0x001d
            goto L_0x0058
        L_0x001d:
            java.lang.Object r4 = r8.getTag()
            boolean r4 = r4 instanceof com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter.RadioButtonViewHolder
            if (r4 == 0) goto L_0x002c
            java.lang.Object r4 = r8.getTag()
            com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter$RadioButtonViewHolder r4 = (com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter.RadioButtonViewHolder) r4
            goto L_0x0059
        L_0x002c:
            r6.initConvertView(r9)
            goto L_0x0058
        L_0x0030:
            java.lang.Object r4 = r8.getTag()
            boolean r4 = r4 instanceof com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter.ArrowViewHolder
            if (r4 == 0) goto L_0x003f
            java.lang.Object r4 = r8.getTag()
            com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter$ArrowViewHolder r4 = (com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter.ArrowViewHolder) r4
            goto L_0x0051
        L_0x003f:
            r6.initConvertView(r9)
            goto L_0x0058
        L_0x0043:
            java.lang.Object r4 = r8.getTag()
            boolean r4 = r4 instanceof com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter.ArrowViewHolder
            if (r4 == 0) goto L_0x0055
            java.lang.Object r4 = r8.getTag()
            com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter$ArrowViewHolder r4 = (com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter.ArrowViewHolder) r4
        L_0x0051:
            r5 = r4
            r4 = r2
            r2 = r5
            goto L_0x0059
        L_0x0055:
            r6.initConvertView(r9)
        L_0x0058:
            r4 = r2
        L_0x0059:
            if (r9 == r3) goto L_0x00a0
            if (r9 == r1) goto L_0x008d
            if (r9 == r0) goto L_0x0060
            goto L_0x00b2
        L_0x0060:
            android.widget.TextView r9 = r4.leftText
            java.lang.String r0 = r7.getLeftContent()
            r9.setText(r0)
            boolean r7 = r7.isRadioBtnStatus()
            r9 = 0
            if (r7 == 0) goto L_0x0076
            android.widget.Switch r7 = r4.switchButton
            r7.setChecked(r3)
            goto L_0x007b
        L_0x0076:
            android.widget.Switch r7 = r4.switchButton
            r7.setChecked(r9)
        L_0x007b:
            android.widget.Switch r7 = r4.switchButton
            java.lang.String r0 = ""
            r7.setTextOff(r0)
            android.widget.Switch r7 = r4.switchButton
            r7.setTextOn(r0)
            android.widget.Switch r7 = r4.switchButton
            r7.setShowText(r9)
            goto L_0x00b2
        L_0x008d:
            android.widget.TextView r9 = r2.leftText
            java.lang.String r0 = r7.getLeftContent()
            r9.setText(r0)
            android.widget.TextView r9 = r2.rightText
            java.lang.String r7 = r7.getValue()
            r9.setText(r7)
            goto L_0x00b2
        L_0x00a0:
            android.widget.TextView r9 = r2.leftText
            java.lang.String r0 = r7.getLeftContent()
            r9.setText(r0)
            android.widget.TextView r9 = r2.rightText
            java.lang.String r7 = r7.getValue()
            r9.setText(r7)
        L_0x00b2:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.adapter.ZKStaffTypeAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public View initConvertView(int i) {
        View view;
        if (i == 1) {
            view = View.inflate(this.context, R.layout.item_arrow_type, (ViewGroup) null);
            ArrowViewHolder arrowViewHolder = new ArrowViewHolder();
            arrowViewHolder.leftText = (TextView) view.findViewById(R.id.leftText);
            arrowViewHolder.rightText = (TextView) view.findViewById(R.id.rightText);
            view.setTag(arrowViewHolder);
        } else if (i == 2) {
            view = View.inflate(this.context, R.layout.item_input_type, (ViewGroup) null);
            ArrowViewHolder arrowViewHolder2 = new ArrowViewHolder();
            arrowViewHolder2.leftText = (TextView) view.findViewById(R.id.leftText);
            arrowViewHolder2.rightText = (TextView) view.findViewById(R.id.rightText);
            view.setTag(arrowViewHolder2);
        } else if (i != 3) {
            return null;
        } else {
            view = View.inflate(this.context, R.layout.item_radio_btn_type, (ViewGroup) null);
            RadioButtonViewHolder radioButtonViewHolder = new RadioButtonViewHolder();
            radioButtonViewHolder.leftText = (TextView) view.findViewById(R.id.leftText);
            radioButtonViewHolder.switchButton = (Switch) view.findViewById(R.id.switchButton);
            view.setTag(radioButtonViewHolder);
        }
        return view;
    }

    private class ArrowViewHolder {
        TextView leftText;
        TextView rightText;

        private ArrowViewHolder() {
        }
    }

    private class RadioButtonViewHolder {
        TextView leftText;
        Switch switchButton;

        private RadioButtonViewHolder() {
        }
    }
}
