package com.zkteco.android.employeemgmt.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.BitmapUtils;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.employeemgmt.activity.ZKStaffModifyActivity;
import com.zkteco.android.employeemgmt.model.ZKStaffBean;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.employeemgmt.widget.CircleTransform;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZKStaffHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isCxShow;
    private int isFaceFunOn;
    private boolean isModifyUser = false;
    private int isSupportSFZ;
    private Context mContext;
    private List<ZKStaffBean> mCurrentData = new ArrayList();
    private final LayoutInflater mInflater;
    private String modifyUserPin = "";
    private int onClickPosition;
    public OnGarbageStatus onGarbageStatus;
    public OncheckListener oncheckListener;
    private String queryPar;
    private int rfCardOn;

    public interface OnGarbageStatus {
        void onSwitchStatus();
    }

    public interface OncheckListener {
        void oncheck();
    }

    public ZKStaffHomeAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        try {
            this.rfCardOn = DBManager.getInstance().getIntOption("~RFCardOn", 0);
            this.isSupportSFZ = DBManager.getInstance().getIntOption(WiegandConfig.IsSupportSFZ, 0);
            this.isFaceFunOn = DBManager.getInstance().getIntOption("FaceFunOn", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ZKStaffBean> getData() {
        return this.mCurrentData;
    }

    public void setData(List<ZKStaffBean> list) {
        this.mCurrentData = list;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        List<ZKStaffBean> list = this.mCurrentData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public ZKStaffBean getItem(int i) {
        return this.mCurrentData.get(i);
    }

    public void remove(int i) {
        this.mCurrentData.remove(i);
        notifyItemRemoved(i);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R.layout.item_staff_content, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List<Object> list) {
        if (list.isEmpty()) {
            super.onBindViewHolder(viewHolder, i, list);
            return;
        }
        ZKStaffBean item = getItem(i);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        ImageView imageView = viewHolder2.cx;
        RelativeLayout relativeLayout = viewHolder2.rel_checkbox;
        if (item.isCkSelected()) {
            imageView.setImageResource(R.mipmap.cx_select);
        } else {
            imageView.setImageResource(R.mipmap.cx_unselect);
        }
        if (this.isCxShow) {
            relativeLayout.setVisibility(0);
        } else {
            relativeLayout.setVisibility(8);
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ZKStaffBean item = getItem(i);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        RelativeLayout relativeLayout = viewHolder2.rel_all;
        RelativeLayout relativeLayout2 = viewHolder2.rel_checkbox;
        ImageView imageView = viewHolder2.cx;
        relativeLayout.setOnClickListener(new View.OnClickListener(item, imageView, viewHolder) {
            public final /* synthetic */ ZKStaffBean f$1;
            public final /* synthetic */ ImageView f$2;
            public final /* synthetic */ RecyclerView.ViewHolder f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void onClick(View view) {
                ZKStaffHomeAdapter.this.lambda$onBindViewHolder$0$ZKStaffHomeAdapter(this.f$1, this.f$2, this.f$3, view);
            }
        });
        if (item.isCkSelected()) {
            imageView.setImageResource(R.mipmap.cx_select);
        } else {
            imageView.setImageResource(R.mipmap.cx_unselect);
        }
        if (this.isCxShow) {
            relativeLayout2.setVisibility(0);
        } else {
            relativeLayout2.setVisibility(8);
        }
        setQueryPar(this.queryPar, item, viewHolder2);
        if (!new File(ZKFilePath.PICTURE_PATH + item.getUserInfo().getUser_PIN() + ZKFilePath.SUFFIX_IMAGE).exists() || BitmapUtils.getUserPhoto(item.getUserInfo().getUser_PIN()) == null || !"1".equals(ZKLauncher.sPhotoFunOn)) {
            viewHolder2.iv_icon.setImageResource(R.mipmap.ic_pic_null);
        } else {
            viewHolder2.iv_icon.setImageResource(R.mipmap.ic_pic_null);
            ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) Glide.with(this.mContext).load(BitmapUtils.getUserPhoto(item.getUserInfo().getUser_PIN())).skipMemoryCache(true)).diskCacheStrategy(DiskCacheStrategy.NONE)).error((int) R.mipmap.ic_pic_null)).transform((Transformation<Bitmap>) new CircleTransform())).dontAnimate()).into(viewHolder2.iv_icon);
        }
        if (!item.isFaceLight() || this.isFaceFunOn != 1) {
            viewHolder2.iv_face.setVisibility(8);
        } else {
            viewHolder2.iv_face.setVisibility(0);
        }
        if (item.isFingerLight()) {
            viewHolder2.iv_fingerprint.setVisibility(0);
        } else {
            viewHolder2.iv_fingerprint.setVisibility(8);
        }
        if (!item.isCardLight() || !(this.rfCardOn == 1 || this.isSupportSFZ == 1)) {
            viewHolder2.iv_card.setVisibility(8);
        } else {
            viewHolder2.iv_card.setVisibility(0);
        }
        if (item.isPdLight()) {
            viewHolder2.iv_password.setVisibility(0);
        } else {
            viewHolder2.iv_password.setVisibility(8);
        }
        if (!item.isPalmPrintLight() || !VerifyTypeUtils.enablePv()) {
            viewHolder2.iv_palm.setVisibility(8);
        } else {
            viewHolder2.iv_palm.setVisibility(0);
        }
        if (item.getUserInfo().getPrivilege() == 14) {
            viewHolder2.iv_staff_admin.setVisibility(0);
        } else {
            viewHolder2.iv_staff_admin.setVisibility(8);
        }
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$ZKStaffHomeAdapter(ZKStaffBean zKStaffBean, ImageView imageView, RecyclerView.ViewHolder viewHolder, View view) {
        if (this.isCxShow) {
            if (zKStaffBean.isCkSelected()) {
                zKStaffBean.setCkSelected(false);
                imageView.setImageResource(R.mipmap.cx_unselect);
            } else {
                zKStaffBean.setCkSelected(true);
                imageView.setImageResource(R.mipmap.cx_select);
            }
            this.onGarbageStatus.onSwitchStatus();
            this.oncheckListener.oncheck();
        } else if (viewHolder.getAdapterPosition() != -1) {
            Intent intent = new Intent(this.mContext, ZKStaffModifyActivity.class);
            intent.putExtra("userpin", zKStaffBean.getUserInfo().getUser_PIN());
            this.mContext.startActivity(intent);
            setIsModifyUser(true);
            this.modifyUserPin = zKStaffBean.getUserInfo().getUser_PIN();
            this.onClickPosition = viewHolder.getAdapterPosition();
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cx;
        ImageView iv_card;
        ImageView iv_face;
        ImageView iv_fingerprint;
        ImageView iv_icon;
        ImageView iv_palm;
        ImageView iv_password;
        ImageView iv_staff_admin;
        LinearLayout lin_Info;
        RelativeLayout rel_all;
        RelativeLayout rel_checkbox;
        AppCompatTextView tv_name;
        TextView tv_pin;

        public ViewHolder(View view) {
            super(view);
            this.rel_checkbox = (RelativeLayout) view.findViewById(R.id.rel_checkbox);
            this.cx = (ImageView) view.findViewById(R.id.cx_select);
            this.iv_icon = (ImageView) view.findViewById(R.id.iv_staff_icon);
            this.tv_name = (AppCompatTextView) view.findViewById(R.id.tv_name);
            this.tv_pin = (TextView) view.findViewById(R.id.tv_pin);
            this.lin_Info = (LinearLayout) view.findViewById(R.id.linear_staff_info);
            this.iv_face = (ImageView) view.findViewById(R.id.iv_face);
            this.iv_fingerprint = (ImageView) view.findViewById(R.id.iv_fingerprint);
            this.iv_card = (ImageView) view.findViewById(R.id.iv_card);
            this.iv_password = (ImageView) view.findViewById(R.id.iv_password);
            this.iv_palm = (ImageView) view.findViewById(R.id.iv_palm);
            this.rel_all = (RelativeLayout) view.findViewById(R.id.rel_all);
            this.iv_staff_admin = (ImageView) view.findViewById(R.id.iv_staff_admin);
        }
    }

    public void setIsCxShow(boolean z) {
        this.isCxShow = z;
        notifyDataSetChanged();
    }

    public boolean getIsCxShow() {
        return this.isCxShow;
    }

    public void setQueryPar(String str, ZKStaffBean zKStaffBean, ViewHolder viewHolder) {
        String name = zKStaffBean.getUserInfo().getName() == null ? "" : zKStaffBean.getUserInfo().getName();
        if (str == null || str.equals("")) {
            viewHolder.tv_name.setText(name);
            viewHolder.tv_pin.setText("ID: " + zKStaffBean.getUserInfo().getUser_PIN());
            return;
        }
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.clr_F91E1E));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(name);
        int indexOf = name.toLowerCase().indexOf(str.toLowerCase());
        if (indexOf != -1) {
            spannableStringBuilder.setSpan(foregroundColorSpan, indexOf, str.length() + indexOf, 33);
            viewHolder.tv_name.setText(spannableStringBuilder);
        } else {
            viewHolder.tv_name.setText(name);
        }
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder("ID: " + zKStaffBean.getUserInfo().getUser_PIN());
        int indexOf2 = zKStaffBean.getUserInfo().getUser_PIN().toLowerCase().indexOf(str.toLowerCase());
        if (indexOf2 != -1) {
            int i = indexOf2 + 4;
            spannableStringBuilder2.setSpan(foregroundColorSpan, i, str.length() + i, 33);
        }
        viewHolder.tv_pin.setText(spannableStringBuilder2);
    }

    public void setOnGarbageStatus(OnGarbageStatus onGarbageStatus2) {
        this.onGarbageStatus = onGarbageStatus2;
    }

    public void setOncheckListener(OncheckListener oncheckListener2) {
        this.oncheckListener = oncheckListener2;
    }

    private String getUserPhoto(String str) {
        return ZKFilePath.PICTURE_PATH + str + ".jpg";
    }

    public void setQueryPar(String str) {
        this.queryPar = str;
    }

    public boolean isModifyUser() {
        return this.isModifyUser;
    }

    public void setIsModifyUser(boolean z) {
        this.isModifyUser = z;
    }

    public int getOnClickPosition() {
        return this.onClickPosition;
    }
}
