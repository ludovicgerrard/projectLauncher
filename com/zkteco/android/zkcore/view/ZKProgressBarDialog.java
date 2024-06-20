package com.zkteco.android.zkcore.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zkteco.android.zkcore.R;

public class ZKProgressBarDialog extends Dialog {
    public ZKProgressBarDialog(Context context) {
        super(context);
    }

    public ZKProgressBarDialog(Context context, int i) {
        super(context, i);
    }

    public static class Builder {
        private Context context;
        private boolean isCancelOutside = false;
        private boolean isCancelable = false;
        private String message;

        public Builder(Context context2) {
            this.context = context2;
        }

        public Builder setCancelable(boolean z) {
            this.isCancelable = z;
            return this;
        }

        public Builder setCancelOutside(boolean z) {
            this.isCancelOutside = z;
            return this;
        }

        public Builder setMessage(String str) {
            this.message = str;
            return this;
        }

        public ZKProgressBarDialog create() {
            View inflate = LayoutInflater.from(this.context).inflate(R.layout.dialog_progress_bar, (ViewGroup) null);
            ZKProgressBarDialog zKProgressBarDialog = new ZKProgressBarDialog(this.context, R.style.dia_loading);
            zKProgressBarDialog.setContentView(inflate);
            ((TextView) inflate.findViewById(R.id.tipTextView)).setText(this.message);
            zKProgressBarDialog.setCancelable(this.isCancelable);
            zKProgressBarDialog.setCanceledOnTouchOutside(this.isCancelOutside);
            return zKProgressBarDialog;
        }
    }
}
