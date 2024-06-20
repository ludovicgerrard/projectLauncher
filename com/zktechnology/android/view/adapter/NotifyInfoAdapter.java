package com.zktechnology.android.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.verify.bean.ZKNotifyBeen;
import java.util.List;
import java.util.Locale;

public class NotifyInfoAdapter extends RecyclerView.Adapter<NotifyItemHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ZKNotifyBeen> notifyList;

    public NotifyInfoAdapter(Context context2, List<ZKNotifyBeen> list) {
        this.context = context2;
        this.notifyList = list;
        this.layoutInflater = LayoutInflater.from(context2);
    }

    public void setNotifyList(List<ZKNotifyBeen> list) {
        this.notifyList = list;
        notifyDataSetChanged();
    }

    public NotifyItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NotifyItemHolder(this.layoutInflater.inflate(R.layout.item_notifyinfo, viewGroup, false));
    }

    public void onBindViewHolder(NotifyItemHolder notifyItemHolder, int i) {
        notifyItemHolder.mText.setText(this.notifyList.get(i).getDetails());
        boolean z = true;
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 1) {
            z = false;
        }
        if (z) {
            notifyItemHolder.mText.setGravity(21);
            notifyItemHolder.mText.setTextDirection(4);
            notifyItemHolder.mText.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.notifyList.get(i).getIcon(), (Drawable) null);
            return;
        }
        notifyItemHolder.mText.setGravity(19);
        notifyItemHolder.mText.setTextDirection(3);
        notifyItemHolder.mText.setCompoundDrawablesWithIntrinsicBounds(this.notifyList.get(i).getIcon(), (Drawable) null, (Drawable) null, (Drawable) null);
    }

    public int getItemCount() {
        return this.notifyList.size();
    }

    static class NotifyItemHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public final TextView mText;

        public NotifyItemHolder(View view) {
            super(view);
            this.mText = (TextView) view.findViewById(R.id.item_tv);
        }
    }
}
