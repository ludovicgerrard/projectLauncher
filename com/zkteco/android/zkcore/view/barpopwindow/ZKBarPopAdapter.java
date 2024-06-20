package com.zkteco.android.zkcore.view.barpopwindow;

import android.content.Context;
import android.widget.ImageView;
import com.zkteco.android.zkcore.R;
import com.zkteco.android.zkcore.base.adapter.ZKCommonAdapter;
import com.zkteco.android.zkcore.base.adapter.ZKViewHolder;
import java.util.List;

class ZKBarPopAdapter extends ZKCommonAdapter<ZKBarPopBean> {
    ZKBarPopAdapter(Context context, List<ZKBarPopBean> list, int i) {
        super(context, list, i);
    }

    public void convert(ZKViewHolder zKViewHolder, ZKBarPopBean zKBarPopBean, int i) {
        ((ImageView) zKViewHolder.getView(R.id.core_rb_state)).setSelected(zKBarPopBean.isState());
        zKViewHolder.setText(R.id.core_tv_content, zKBarPopBean.getName());
    }
}
