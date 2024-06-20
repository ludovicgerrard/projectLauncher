package com.zktechnology.android.verify.dialog.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.zktechnology.android.event.EventBusHelper;
import com.zktechnology.android.event.EventOpenMenu;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKEventLauncher;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.AES256Util;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.BitmapUtils;
import com.zktechnology.android.utils.Common;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.ZKHandler;
import com.zktechnology.android.utils.ZkImageUtils;
import com.zktechnology.android.verify.bean.process.ZKMarkTypeBean;
import com.zktechnology.android.verify.bean.process.ZKVerViewBean;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.dialog.managment.ZKVerDlgMgt;
import com.zktechnology.android.verify.dialog.view.util.TimeOutRunnable;
import com.zktechnology.android.verify.dialog.view.util.ZKListenableActionLayout;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerOption;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.core.sdk.AccPassManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.AccGroup;
import com.zkteco.android.db.orm.tna.ShortState;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.db.orm.util.SpeakerHelper;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.util.CompressionHelper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZKVerProcessDialog extends ZKTimeOutDialog implements View.OnClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int COLOR_LINE_DEFAULT = 2131099810;
    private static final int COLOR_LINE_VERIFY = 2131099806;
    private static final int ICON_CARD_DEFAULT = 2131558430;
    private static final int ICON_CARD_VERIFY = 2131558426;
    private static final int ICON_FACE_DEFAULT = 2131558438;
    private static final int ICON_FACE_VERIFY = 2131558435;
    private static final int ICON_FINGER_DEFAULT = 2131558443;
    private static final int ICON_FINGER_VERIFY = 2131558441;
    private static final int ICON_PIN_DEFAULT = 2131558459;
    private static final int ICON_PIN_VERIFY = 2131558457;
    private static final int ICON_PW_DEFAULT = 2131558453;
    private static final int ICON_PW_VERIFY = 2131558451;
    private static final int SUPERMANAGERVERIFYICON = 0;
    public static final String TAG = "Verify-Dialog";
    private static final int VERIFYTYPEICON = 1;
    private static ZKVerProcessDialog instance;
    UserInfo bean;
    private boolean bolCleanData;
    private ConstraintLayout cl_verify_info;
    private String deviceSN;
    private int faceFunOn;
    private int fingerFunOn;
    /* access modifiers changed from: private */
    public Handler handler;
    private int hasFingerModule;
    private boolean isShow = false;
    private ImageView iv_pin_change;
    private ImageView iv_super_password;
    private ImageView iv_verify_waiting;
    private LinearLayout layout_ID;
    private LinearLayout layout_name;
    private ImageView left_icon_1;
    private ImageView left_icon_2;
    private ImageView left_icon_3;
    private ImageView left_icon_4;
    private View left_view_1;
    private View left_view_2;
    private View left_view_3;
    private LinearLayout ll_remote_delay;
    private int mIcon1;
    private int mIcon2;
    private int mIcon3;
    private int mIcon4;
    private ZKListenableActionLayout.OnUserInteractionListener mInteractionListener;
    private int mLine1;
    private int mLine2;
    private int mLine3;
    private int mLine4;
    private DialogInterface.OnKeyListener mOnKeyListener;
    private EventOpenMenu mOpenMenuEvent;
    private TextWatcher mPasswordTextWatcher;
    private TextWatcher mPinTextWatcher;
    private View mRootViw;
    private int mifare;
    private int pvFunOn;
    private int rdmFunOn;
    private int rfCardOn;
    Runnable setSuperManegerVerifyIconTask = new Runnable() {
        public void run() {
            try {
                ZKVerProcessDialog.this.getNeedData();
                Message.obtain(ZKVerProcessDialog.this.handler, 0).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    Runnable setVerifyTypeIconVisibleTask = new Runnable() {
        public void run() {
            try {
                ZKVerProcessDialog.this.getNeedData();
                Message.obtain(ZKVerProcessDialog.this.handler, 1).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    final ExecutorService singleThreadExecutor = Executors.newFixedThreadPool(3);
    private TextView tv_fail_msg;
    private TextView tv_superTips;
    List<ZKMarkTypeBean> typeBeans;
    private String urgentPassword;
    private String userPin;
    ZKVerViewBean viewBean;
    private View viewcopy;
    private TextView vpd_card_att_full;
    private LinearLayout vpd_card_fa;
    private TextView vpd_card_fa_message;
    private LinearLayout vpd_card_st;
    private TextView vpd_fa_att_record_full;
    private TextView vpd_face_att_full;
    private LinearLayout vpd_face_fa;
    private TextView vpd_face_fa_message;
    private LinearLayout vpd_face_st;
    private LinearLayout vpd_fai_user;
    private ImageView vpd_fai_user_icon;
    private TextView vpd_fai_user_name;
    private TextView vpd_fai_user_pin;
    private TextView vpd_finger_att_full;
    private LinearLayout vpd_finger_fa;
    private ImageView vpd_finger_fa_icon;
    private TextView vpd_finger_fa_message;
    private LinearLayout vpd_finger_st;
    private LinearLayout vpd_icon_1;
    private LinearLayout vpd_icon_2;
    private LinearLayout vpd_icon_3;
    private LinearLayout vpd_icon_4;
    /* access modifiers changed from: private */
    public ConstraintLayout vpd_key_board_1v1;
    private ImageView vpd_key_board_1v1_card;
    private ImageView vpd_key_board_1v1_close;
    private ImageView vpd_key_board_1v1_face;
    private ImageView vpd_key_board_1v1_fingerprint;
    private ImageView vpd_key_board_1v1_palm;
    private ImageView vpd_key_board_1v1_password;
    private LinearLayout vpd_locate_ll;
    private RelativeLayout vpd_multi_verify_wait;
    private TextView vpd_palm_att_full;
    private LinearLayout vpd_palm_fa;
    private TextView vpd_palm_fa_message;
    private LinearLayout vpd_palm_st;
    private ConstraintLayout vpd_password;
    private EditText vpd_password_edit;
    private TextView vpd_password_hint;
    private Button vpd_password_next;
    private ConstraintLayout vpd_pin;
    private TextView vpd_pin_att_full;
    private EditText vpd_pin_edit;
    private LinearLayout vpd_pin_fa;
    private TextView vpd_pin_fa_message;
    private TextView vpd_pin_hint;
    private Button vpd_pin_next;
    private LinearLayout vpd_su;
    private TextView vpd_su_att_record_full;
    private TextView vpd_su_date;
    private TextView vpd_su_ergent_password_event;
    private ImageView vpd_su_icon;
    private LinearLayout vpd_su_ll_status;
    private TextView vpd_su_locate_info;
    private Button vpd_su_message;
    private TextView vpd_su_name;
    private TextView vpd_su_pin;
    private TextView vpd_su_repeat;
    private TextView vpd_su_state;
    private LinearLayout vpd_su_user_name;
    private LinearLayout vpd_su_user_pin;
    private EditText vpd_super_password_edit;
    private Button vpd_super_password_next;
    /* access modifiers changed from: private */
    public LinearLayout vpd_verify;
    private ImageView vpd_verify_close;
    /* access modifiers changed from: private */
    public ConstraintLayout vpd_way;
    private ImageView vpd_way_card;
    private ImageView vpd_way_close;
    private ImageView vpd_way_face;
    private ImageView vpd_way_fingerprint;
    private ImageView vpd_way_palm;
    private ImageView vpd_way_pin;
    private ImageView vpd_way_super_crack;
    private TextView wg_failed_att_full;
    private ImageView wg_failed_icon;
    private LinearLayout wg_failed_layout;
    private TextView wg_success_att_record_full;
    private TextView wg_success_date;
    private ImageView wg_success_icon;
    private LinearLayout wg_success_layout;
    private TextView wg_success_name;
    private TextView wg_success_pin;
    private TextView wg_success_repeat;
    private ZKVerOption zkVerOption;

    private static boolean isFollowGlobalTimeout(int i) {
        return (i == 20 || i == 30 || i == 73 || i == 22 || i == 32 || i == 72 || i == 0 || i == 1) ? false : true;
    }

    private ZKVerProcessDialog() {
    }

    public static ZKVerProcessDialog getInstance() {
        if (instance == null) {
            instance = new ZKVerProcessDialog();
            setInstance();
        }
        return instance;
    }

    private static void setInstance() {
        instance.setGravity(1);
        instance.setCancelable(true);
        instance.setDialogSize(480, 250);
        instance.setShowLocation(0, 480);
    }

    private static class ZKVerProcessDialogHandler extends ZKHandler<ZKVerProcessDialog> {
        public ZKVerProcessDialogHandler(ZKVerProcessDialog zKVerProcessDialog) {
            super(zKVerProcessDialog);
        }

        public void handleMessage(ZKVerProcessDialog zKVerProcessDialog, Message message) {
            int i = message.what;
            if (i == 0) {
                zKVerProcessDialog.setSuperManegerVerifyIcon();
                zKVerProcessDialog.vpd_key_board_1v1.setVisibility(8);
                zKVerProcessDialog.vpd_way.setVisibility(0);
            } else if (i == 1) {
                zKVerProcessDialog.setVerifyTypeIconVisible(zKVerProcessDialog.bean, zKVerProcessDialog.viewBean);
                zKVerProcessDialog.vpd_key_board_1v1.setVisibility(0);
                zKVerProcessDialog.vpd_way.setVisibility(8);
                zKVerProcessDialog.vpd_verify.setVisibility(8);
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.MyDialogStyle);
        Log.e(TAG, "onCreate: ");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.verify_process_dialog, viewGroup);
        this.mRootViw = inflate;
        inflate.setAlpha(1.0f);
        this.handler = new ZKVerProcessDialogHandler(this);
        setVerifiedInfoTimeOutMs();
        resetInactiveTimeout();
        this.mOpenMenuEvent = new EventOpenMenu(true);
        this.zkVerOption = new ZKVerOption();
        this.timeoutRunnable = new TimeOutRunnable() {
            public void run() {
                ZKVerController.getInstance().resetMultiVerify();
                ZKEventLauncher.setProcessDialogVisibility(false);
            }
        };
        Log.e(TAG, "onCreateView: ");
        return this.mRootViw;
    }

    public void onDestroyView() {
        super.onDestroyView();
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages((Object) null);
            this.handler = null;
        }
        try {
            unbindListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mOpenMenuEvent = null;
        this.zkVerOption = null;
        this.timeoutRunnable = null;
        this.mPinTextWatcher = null;
        this.mOnKeyListener = null;
        this.mInteractionListener = null;
        this.mPasswordTextWatcher = null;
        Log.e(TAG, "onDestroyView: ");
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getDialog().getWindow().setFlags(32, 32);
        getDialog().getWindow().setFlags(262144, 262144);
        this.viewcopy = view;
        this.mInteractionListener = new ZKListenableActionLayout.OnUserInteractionListener() {
            public final void onUserInteraction() {
                ZKVerProcessDialog.this.resetInactiveTimeout();
            }
        };
        this.mOnKeyListener = $$Lambda$ZKVerProcessDialog$szjskXnqeuhQl64l6HWvNWse2E.INSTANCE;
        this.mPinTextWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                ZKVerProcessDialog.this.resetInactiveTimeout();
                if (ZKLauncher.PIN2Width == 1) {
                    try {
                        String obj = editable.toString();
                        char c2 = obj.substring(obj.length() - 1, obj.length()).toCharArray()[0];
                        if (c2 >= '0' && c2 <= '9') {
                            return;
                        }
                        if (c2 >= 'A' && c2 <= 'Z') {
                            return;
                        }
                        if (c2 <= 'a' || c2 > 'z') {
                            editable.delete(obj.length() - 1, obj.length());
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        };
        this.mPasswordTextWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                ZKVerProcessDialog.this.resetInactiveTimeout();
            }
        };
        initView(view);
        bindListener();
        Log.e(TAG, "onViewCreated: ");
    }

    static /* synthetic */ boolean lambda$onViewCreated$0(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 1) {
            return false;
        }
        ZKVerDlgMgt.pop();
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    private void bindListener() {
        View view = this.mRootViw;
        if (view instanceof ZKListenableActionLayout) {
            ((ZKListenableActionLayout) view).setOnUserInteractiveListener(this.mInteractionListener);
        }
        if (getDialog() != null) {
            getDialog().setOnKeyListener(this.mOnKeyListener);
        }
        this.vpd_password_edit.addTextChangedListener(this.mPasswordTextWatcher);
        this.vpd_super_password_edit.addTextChangedListener(this.mPasswordTextWatcher);
        this.vpd_pin_edit.addTextChangedListener(this.mPinTextWatcher);
        this.vpd_way_card.setOnClickListener(this);
        this.vpd_way_fingerprint.setOnClickListener(this);
        this.vpd_way_pin.setOnClickListener(this);
        this.vpd_way_close.setOnClickListener(this);
        this.vpd_password_next.setOnClickListener(this);
        this.vpd_pin_next.setOnClickListener(this);
        this.vpd_verify_close.setOnClickListener(this);
        this.vpd_su_message.setOnClickListener(this);
        this.vpd_way_face.setOnClickListener(this);
        this.vpd_way_palm.setOnClickListener(this);
        this.vpd_way_super_crack.setOnClickListener(this);
        this.vpd_key_board_1v1_password.setOnClickListener(this);
        this.vpd_key_board_1v1_fingerprint.setOnClickListener(this);
        this.vpd_key_board_1v1_card.setOnClickListener(this);
        this.vpd_key_board_1v1_face.setOnClickListener(this);
        this.vpd_key_board_1v1_palm.setOnClickListener(this);
        this.vpd_key_board_1v1_close.setOnClickListener(this);
        this.iv_super_password.setOnClickListener(this);
        this.iv_pin_change.setOnClickListener(this);
        this.vpd_super_password_next.setOnClickListener(this);
    }

    private void unbindListener() {
        View view = this.mRootViw;
        if (view instanceof ZKListenableActionLayout) {
            ((ZKListenableActionLayout) view).setOnUserInteractiveListener((ZKListenableActionLayout.OnUserInteractionListener) null);
        }
        this.vpd_way_card.setOnClickListener((View.OnClickListener) null);
        this.vpd_way_fingerprint.setOnClickListener((View.OnClickListener) null);
        this.vpd_way_pin.setOnClickListener((View.OnClickListener) null);
        this.vpd_way_close.setOnClickListener((View.OnClickListener) null);
        this.vpd_password_next.setOnClickListener((View.OnClickListener) null);
        this.vpd_pin_next.setOnClickListener((View.OnClickListener) null);
        this.vpd_verify_close.setOnClickListener((View.OnClickListener) null);
        this.vpd_su_message.setOnClickListener((View.OnClickListener) null);
        this.vpd_way_face.setOnClickListener((View.OnClickListener) null);
        this.vpd_way_palm.setOnClickListener((View.OnClickListener) null);
        this.vpd_way_super_crack.setOnClickListener((View.OnClickListener) null);
        this.vpd_password_edit.removeTextChangedListener(this.mPasswordTextWatcher);
        this.vpd_super_password_edit.removeTextChangedListener(this.mPasswordTextWatcher);
        this.vpd_pin_edit.removeTextChangedListener(this.mPinTextWatcher);
        this.vpd_key_board_1v1_password.setOnClickListener((View.OnClickListener) null);
        this.vpd_key_board_1v1_fingerprint.setOnClickListener((View.OnClickListener) null);
        this.vpd_key_board_1v1_card.setOnClickListener((View.OnClickListener) null);
        this.vpd_key_board_1v1_face.setOnClickListener((View.OnClickListener) null);
        this.vpd_key_board_1v1_palm.setOnClickListener((View.OnClickListener) null);
        this.vpd_key_board_1v1_close.setOnClickListener((View.OnClickListener) null);
        this.iv_super_password.setOnClickListener((View.OnClickListener) null);
        this.iv_pin_change.setOnClickListener((View.OnClickListener) null);
        this.vpd_super_password_next.setOnClickListener((View.OnClickListener) null);
    }

    public boolean isShow() {
        return this.isShow;
    }

    private void initView(View view) {
        this.ll_remote_delay = (LinearLayout) view.findViewById(R.id.ll_delay);
        this.iv_verify_waiting = (ImageView) view.findViewById(R.id.iv_verify_waiting);
        this.cl_verify_info = (ConstraintLayout) view.findViewById(R.id.cl_verify_info);
        this.iv_super_password = (ImageView) view.findViewById(R.id.iv_super_password);
        this.iv_pin_change = (ImageView) view.findViewById(R.id.iv_pin_change);
        this.vpd_super_password_edit = (EditText) view.findViewById(R.id.vpd_super_password_edit);
        this.vpd_super_password_next = (Button) view.findViewById(R.id.vpd_super_password_next);
        this.vpd_su_user_name = (LinearLayout) view.findViewById(R.id.vpd_su_user_name);
        this.vpd_su_user_pin = (LinearLayout) view.findViewById(R.id.vpd_su_user_pin);
        this.wg_failed_layout = (LinearLayout) view.findViewById(R.id.wg_failed_layout);
        this.wg_failed_att_full = (TextView) view.findViewById(R.id.wg_failed_att_full);
        this.wg_success_att_record_full = (TextView) view.findViewById(R.id.wg_success_att_record_full);
        this.wg_failed_icon = (ImageView) view.findViewById(R.id.wg_failed_icon);
        this.wg_success_layout = (LinearLayout) view.findViewById(R.id.wg_success_layout);
        this.wg_success_repeat = (TextView) view.findViewById(R.id.wg_success_repeat);
        this.wg_success_icon = (ImageView) view.findViewById(R.id.wg_success_icon);
        this.wg_success_date = (TextView) view.findViewById(R.id.wg_success_date);
        this.wg_success_name = (TextView) view.findViewById(R.id.wg_success_name);
        this.wg_success_pin = (TextView) view.findViewById(R.id.wg_success_pin);
        this.vpd_way = (ConstraintLayout) view.findViewById(R.id.vpd_way);
        this.vpd_way_card = (ImageView) view.findViewById(R.id.vpd_way_card);
        this.vpd_way_fingerprint = (ImageView) view.findViewById(R.id.vpd_way_fingerprint);
        this.vpd_way_pin = (ImageView) view.findViewById(R.id.vpd_way_pin);
        this.vpd_way_face = (ImageView) view.findViewById(R.id.vpd_way_face);
        this.vpd_way_palm = (ImageView) view.findViewById(R.id.vpd_way_palm);
        this.vpd_way_close = (ImageView) view.findViewById(R.id.vpd_way_close);
        this.vpd_way_super_crack = (ImageView) view.findViewById(R.id.vpd_way_super_crack);
        this.vpd_verify = (LinearLayout) view.findViewById(R.id.vpd_verify);
        this.vpd_icon_1 = (LinearLayout) view.findViewById(R.id.vpd_icon_1);
        this.left_icon_1 = (ImageView) view.findViewById(R.id.left_icon_1);
        this.left_view_1 = view.findViewById(R.id.left_view_1);
        this.vpd_icon_2 = (LinearLayout) view.findViewById(R.id.vpd_icon_2);
        this.left_icon_2 = (ImageView) view.findViewById(R.id.left_icon_2);
        this.left_view_2 = view.findViewById(R.id.left_view_2);
        this.vpd_icon_3 = (LinearLayout) view.findViewById(R.id.vpd_icon_3);
        this.left_icon_3 = (ImageView) view.findViewById(R.id.left_icon_3);
        this.left_view_3 = view.findViewById(R.id.left_view_3);
        this.vpd_icon_4 = (LinearLayout) view.findViewById(R.id.vpd_icon_4);
        this.left_icon_4 = (ImageView) view.findViewById(R.id.left_icon_4);
        this.vpd_locate_ll = (LinearLayout) view.findViewById(R.id.vpd_su_ll_locate_info);
        this.vpd_su_locate_info = (TextView) view.findViewById(R.id.vpd_su_locate_info);
        this.vpd_face_st = (LinearLayout) view.findViewById(R.id.vpd_face_st);
        this.vpd_face_fa = (LinearLayout) view.findViewById(R.id.vpd_face_fa);
        this.vpd_face_fa_message = (TextView) view.findViewById(R.id.vpd_face_fa_message);
        this.vpd_palm_st = (LinearLayout) view.findViewById(R.id.vpd_palm_st);
        this.vpd_palm_fa = (LinearLayout) view.findViewById(R.id.vpd_palm_fa);
        this.vpd_palm_fa_message = (TextView) view.findViewById(R.id.vpd_palm_fa_message);
        this.vpd_card_st = (LinearLayout) view.findViewById(R.id.vpd_card_st);
        this.vpd_card_fa = (LinearLayout) view.findViewById(R.id.vpd_card_fa);
        this.vpd_card_fa_message = (TextView) view.findViewById(R.id.vpd_card_fa_message);
        this.vpd_finger_st = (LinearLayout) view.findViewById(R.id.vpd_finger_st);
        this.vpd_finger_fa = (LinearLayout) view.findViewById(R.id.vpd_finger_fa);
        this.vpd_finger_fa_icon = (ImageView) view.findViewById(R.id.vpd_finger_fa_icon);
        this.vpd_finger_fa_message = (TextView) view.findViewById(R.id.vpd_finger_fa_message);
        this.vpd_multi_verify_wait = (RelativeLayout) view.findViewById(R.id.vpd_multi_verify_wait);
        this.vpd_password = (ConstraintLayout) view.findViewById(R.id.vpd_password);
        this.vpd_password_edit = (EditText) view.findViewById(R.id.vpd_password_edit);
        this.vpd_password_hint = (TextView) view.findViewById(R.id.vpd_password_hint);
        this.vpd_password_next = (Button) view.findViewById(R.id.vpd_password_next);
        this.vpd_pin = (ConstraintLayout) view.findViewById(R.id.vpd_pin);
        this.vpd_pin_edit = (EditText) view.findViewById(R.id.vpd_pin_edit);
        this.vpd_pin_hint = (TextView) view.findViewById(R.id.vpd_pin_hint);
        this.vpd_pin_next = (Button) view.findViewById(R.id.vpd_pin_next);
        this.vpd_pin_fa = (LinearLayout) view.findViewById(R.id.vpd_pin_fa);
        this.vpd_pin_fa_message = (TextView) view.findViewById(R.id.vpd_pin_fa_message);
        this.vpd_su = (LinearLayout) view.findViewById(R.id.vpd_su);
        this.vpd_su_icon = (ImageView) view.findViewById(R.id.vpd_su_icon);
        this.vpd_su_date = (TextView) view.findViewById(R.id.vpd_su_date);
        this.vpd_su_name = (TextView) view.findViewById(R.id.vpd_su_name);
        this.vpd_su_pin = (TextView) view.findViewById(R.id.vpd_su_pin);
        this.vpd_su_ll_status = (LinearLayout) view.findViewById(R.id.vpd_su_ll_status);
        this.vpd_su_state = (TextView) view.findViewById(R.id.vpd_su_state);
        this.vpd_su_repeat = (TextView) view.findViewById(R.id.vpd_su_repeat);
        this.vpd_su_ergent_password_event = (TextView) view.findViewById(R.id.vpd_su_ergent_password_event);
        this.vpd_su_att_record_full = (TextView) view.findViewById(R.id.vpd_su_att_record_full);
        this.vpd_su_message = (Button) view.findViewById(R.id.vpd_su_message);
        this.vpd_verify_close = (ImageView) view.findViewById(R.id.vpd_verify_close);
        this.vpd_fai_user = (LinearLayout) view.findViewById(R.id.vpd_fai_user);
        this.vpd_fai_user_icon = (ImageView) view.findViewById(R.id.vpd_fai_user_icon);
        this.vpd_fai_user_name = (TextView) view.findViewById(R.id.vpd_fai_user_name);
        this.vpd_fai_user_pin = (TextView) view.findViewById(R.id.vpd_fai_user_pin);
        this.vpd_fa_att_record_full = (TextView) view.findViewById(R.id.vpd_fa_att_record_full);
        this.tv_fail_msg = (TextView) view.findViewById(R.id.tv_fail_msg);
        this.layout_name = (LinearLayout) view.findViewById(R.id.layout_name);
        this.layout_ID = (LinearLayout) view.findViewById(R.id.layout_ID);
        this.vpd_key_board_1v1 = (ConstraintLayout) view.findViewById(R.id.vpd_key_board_1v1);
        this.vpd_key_board_1v1_password = (ImageView) view.findViewById(R.id.vpd_key_board_1v1_password);
        this.vpd_key_board_1v1_fingerprint = (ImageView) view.findViewById(R.id.vpd_key_board_1v1_fingerprint);
        this.vpd_key_board_1v1_card = (ImageView) view.findViewById(R.id.vpd_key_board_1v1_card);
        this.vpd_key_board_1v1_face = (ImageView) view.findViewById(R.id.vpd_key_board_1v1_face);
        this.vpd_key_board_1v1_palm = (ImageView) view.findViewById(R.id.vpd_key_board_1v1_palm);
        this.vpd_key_board_1v1_close = (ImageView) view.findViewById(R.id.vpd_key_board_1v1_close);
        this.vpd_finger_att_full = (TextView) view.findViewById(R.id.vpd_finger_att_full);
        this.vpd_card_att_full = (TextView) view.findViewById(R.id.vpd_card_att_full);
        this.vpd_face_att_full = (TextView) view.findViewById(R.id.vpd_face_att_full);
        this.vpd_palm_att_full = (TextView) view.findViewById(R.id.vpd_palm_att_full);
        this.vpd_pin_att_full = (TextView) view.findViewById(R.id.vpd_pin_att_full);
        this.tv_superTips = (TextView) view.findViewById(R.id.tv_superTips);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pin_change:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PIN.getValue()));
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(1);
                ZKVerProcessPar.CON_MARK_BEAN.setSuperCrack(false);
                ZKVerDlgMgt.push(new ZKVerViewBean(20));
                return;
            case R.id.iv_super_password:
                ZKVerViewBean zKVerViewBean = new ZKVerViewBean(73);
                zKVerViewBean.setFailMsg(getString(R.string.please_enter_urgent_password));
                ZKVerDlgMgt.push(zKVerViewBean);
                return;
            case R.id.vpd_key_board_1v1_card:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.CARD.getValue()));
                ZKVerProcessPar.ACTION_BEAN.setBolRFidRead(true);
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
                ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(40));
                return;
            case R.id.vpd_key_board_1v1_close:
            case R.id.vpd_verify_close:
            case R.id.vpd_way_close:
                clearDatas();
                FileLogUtils.writeTouchLog("clearDatas: ZKVerProcessDialog onClick2");
                ZKEventLauncher.setProcessDialogVisibility(false);
                return;
            case R.id.vpd_key_board_1v1_face:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FACE.getValue()));
                ZKVerProcessPar.ACTION_BEAN.setBolFace(true);
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
                ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(50));
                return;
            case R.id.vpd_key_board_1v1_fingerprint:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FINGER.getValue()));
                ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
                ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(10));
                return;
            case R.id.vpd_key_board_1v1_palm:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PALM.getValue()));
                ZKVerProcessPar.ACTION_BEAN.setBolPalm(true);
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
                ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(80));
                return;
            case R.id.vpd_key_board_1v1_password:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PASSWORD.getValue()));
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
                ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(30));
                return;
            case R.id.vpd_password_next:
                this.vpd_password_next.setClickable(false);
                UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
                String trim = this.vpd_password_edit.getText().toString().trim();
                if (userInfo != null) {
                    try {
                        if (userInfo.getPassword() == null) {
                            hideSoftKeyboardAndUpdateRTL(true, this.vpd_password_edit);
                            ZKVerViewBean zKVerViewBean2 = new ZKVerViewBean(-1);
                            zKVerViewBean2.setUiName(userInfo.getName());
                            zKVerViewBean2.setUiPin(userInfo.getUser_PIN());
                            zKVerViewBean2.setFailMsg(AppUtils.getString(R.string.not_register_password));
                            ZKVerDlgMgt.upDateTopUi(zKVerViewBean2);
                            playSoundTryAgain();
                            this.vpd_password_next.setClickable(true);
                            return;
                        }
                    } catch (Exception e) {
                        LogUtils.e(TAG, "Exc: " + e.getMessage());
                        if (TextUtils.isEmpty(trim)) {
                            ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(32));
                        } else {
                            hideSoftKeyboardAndUpdateRTL(true, this.vpd_password_edit);
                            ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_PASSWORD, trim);
                            ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                        }
                    }
                }
                if (TextUtils.isEmpty(trim)) {
                    ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(32));
                    playSoundTryAgain();
                } else if (TextUtils.isEmpty(trim) || !ZKVerProcessPar.CON_MARK_BEAN.isSuperCrack()) {
                    hideSoftKeyboardAndUpdateRTL(true, this.vpd_password_edit);
                    ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_PASSWORD, trim);
                    ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                } else if (new AccPassManager(getActivity()).validCode(Integer.valueOf(trim).intValue()) > 0) {
                    ZKVerProcessPar.cleanData(11);
                    ZKVerProcessPar.cleanBtnWidget();
                    clearDatas();
                    FileLogUtils.writeTouchLog("clearDatas: ZKVerProcessDialog onClick1");
                    ZKEventLauncher.setProcessDialogVisibility(false);
                    EventBusHelper.post(this.mOpenMenuEvent);
                } else {
                    ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(32));
                    playSoundTryAgain();
                }
                this.vpd_password_next.setClickable(true);
                return;
            case R.id.vpd_pin_next:
                this.vpd_pin_next.setClickable(false);
                String trim2 = this.vpd_pin_edit.getText().toString().trim();
                if (TextUtils.isEmpty(trim2)) {
                    ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(22));
                    playSoundTryAgain();
                } else {
                    hideSoftKeyboardAndUpdateRTL(true, this.vpd_pin_edit);
                    String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_OTHER_VERIFY_PIN);
                    if (TextUtils.isEmpty(string) || string.equals(trim2)) {
                        ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_PIN, this.vpd_pin_edit.getText().toString());
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                    } else {
                        ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
                        this.vpd_pin_next.setClickable(true);
                        return;
                    }
                }
                this.vpd_pin_next.setClickable(true);
                return;
            case R.id.vpd_super_password_next:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.URGENTPASSWORD.getValue()));
                ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_PASSWORD, this.vpd_super_password_edit.getText().toString());
                ZKVerController.getInstance().changeState(ZKVerConState.STATE_RECORD);
                hideSoftKeyboardAndUpdateRTL(true, this.vpd_super_password_edit);
                return;
            case R.id.vpd_way_card:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.CARD.getValue()));
                ZKVerProcessPar.ACTION_BEAN.setBolRFidRead(true);
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(1);
                ZKVerDlgMgt.push(new ZKVerViewBean(40));
                return;
            case R.id.vpd_way_face:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FACE.getValue()));
                ZKVerProcessPar.ACTION_BEAN.setBolFace(true);
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(1);
                ZKVerDlgMgt.push(new ZKVerViewBean(50));
                return;
            case R.id.vpd_way_fingerprint:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FINGER.getValue()));
                ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(1);
                ZKVerDlgMgt.push(new ZKVerViewBean(10));
                return;
            case R.id.vpd_way_palm:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PALM.getValue()));
                ZKVerProcessPar.ACTION_BEAN.setBolPalm(true);
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(1);
                ZKVerDlgMgt.push(new ZKVerViewBean(80));
                return;
            case R.id.vpd_way_pin:
                ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PIN.getValue()));
                ZKVerProcessPar.CON_MARK_BEAN.setVerState(1);
                ZKVerProcessPar.CON_MARK_BEAN.setSuperCrack(false);
                ZKVerDlgMgt.push(new ZKVerViewBean(20));
                return;
            case R.id.vpd_way_super_crack:
                ZKVerProcessPar.CON_MARK_BEAN.setSuperCrack(true);
                ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(30));
                return;
            default:
                return;
        }
    }

    public void playSoundTryAgain() {
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        language.hashCode();
        char c2 = 65535;
        switch (language.hashCode()) {
            case 3241:
                if (language.equals("en")) {
                    c2 = 0;
                    break;
                }
                break;
            case 3246:
                if (language.equals("es")) {
                    c2 = 1;
                    break;
                }
                break;
            case 3276:
                if (language.equals("fr")) {
                    c2 = 2;
                    break;
                }
                break;
            case 3365:
                if (language.equals(Common.GPIO_DIRCTION_IN)) {
                    c2 = 3;
                    break;
                }
                break;
            case 3383:
                if (language.equals("ja")) {
                    c2 = 4;
                    break;
                }
                break;
            case 3428:
                if (language.equals("ko")) {
                    c2 = 5;
                    break;
                }
                break;
            case 3588:
                if (language.equals("pt")) {
                    c2 = 6;
                    break;
                }
                break;
            case 3651:
                if (language.equals("ru")) {
                    c2 = 7;
                    break;
                }
                break;
            case 3700:
                if (language.equals("th")) {
                    c2 = 8;
                    break;
                }
                break;
            case 3710:
                if (language.equals("tr")) {
                    c2 = 9;
                    break;
                }
                break;
            case 3763:
                if (language.equals("vi")) {
                    c2 = 10;
                    break;
                }
                break;
            case 3886:
                if (language.equals("zh")) {
                    c2 = 11;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "EN");
                return;
            case 1:
                if (locale.getCountry().equals("MX")) {
                    SpeakerHelper.playSound(this.mContext, "4.ogg", true, "ES-MX");
                    return;
                } else {
                    SpeakerHelper.playSound(this.mContext, "4.ogg", true, "ES");
                    return;
                }
            case 2:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "FR");
                return;
            case 3:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "IN");
                return;
            case 4:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "JA");
                return;
            case 5:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "KO");
                return;
            case 6:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "PT");
                return;
            case 7:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "RU");
                return;
            case 8:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "TH");
                return;
            case 9:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "TR");
                return;
            case 10:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "VI");
                return;
            case 11:
                if (locale.getCountry().equals("HK")) {
                    SpeakerHelper.playSound(this.mContext, "4.ogg", true, "CH-HK");
                    return;
                } else {
                    SpeakerHelper.playSound(this.mContext, "4.ogg", true, "CH");
                    return;
                }
            default:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "EN");
                return;
        }
    }

    private void setPinType(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(ZKLauncher.PIN2Width)});
        if (ZKLauncher.IsSupportABCPin == 0) {
            editText.setInputType(2);
        }
    }

    private void setFingerImage(ImageView imageView, String str, int i, int i2) {
        FileLogUtils.writeVerifyLog("setFingerImage parFingerprint:" + (str == null ? "== null" : "!= null"));
        if (!TextUtils.isEmpty(str)) {
            byte[] bArr = new byte[(i * i2)];
            try {
                bArr = CompressionHelper.decompress(Base64.decode(str, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bitmap renderCroppedGreyScaleBitmap = ZkImageUtils.renderCroppedGreyScaleBitmap(bArr, i, i2);
            imageView.setImageBitmap(renderCroppedGreyScaleBitmap);
            FileLogUtils.writeVerifyLog("bitmap:" + renderCroppedGreyScaleBitmap);
            return;
        }
        Glide.with(this.vpd_finger_fa_icon.getContext()).load(Integer.valueOf(R.mipmap.ic_2_fingerprint_0002)).into(imageView);
    }

    private void hideSoftKeyboardAndUpdateRTL(boolean z, EditText editText) {
        hideSoftKeyboard(z, editText);
        updateEditTextRTL(editText);
    }

    private void hideSoftKeyboard(boolean z, EditText editText) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
            if (!z) {
                editText.requestFocus();
                inputMethodManager.showSoftInput(editText, 0);
            } else if (getDialog().getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getDialog().getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception unused) {
        }
    }

    private void updateEditTextRTL(EditText editText) {
        boolean z = true;
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 1) {
            z = false;
        }
        editText.setGravity(z ? 5 : 3);
    }

    private void serVerifyIconClean() {
        this.vpd_icon_1.setVisibility(8);
        this.vpd_icon_2.setVisibility(8);
        this.vpd_icon_3.setVisibility(8);
        this.vpd_icon_4.setVisibility(8);
        this.left_icon_1.setImageResource(0);
        this.left_icon_2.setImageResource(0);
        this.left_icon_3.setImageResource(0);
        this.left_icon_4.setImageResource(0);
        this.mIcon1 = 0;
        this.mIcon2 = 0;
        this.mIcon3 = 0;
        this.mIcon4 = 0;
        this.mLine1 = R.color.launcher_clr_dbdbdb;
        this.mLine2 = R.color.launcher_clr_dbdbdb;
        this.mLine3 = R.color.launcher_clr_dbdbdb;
        this.mLine4 = R.color.launcher_clr_dbdbdb;
    }

    private void setFailInfo(ZKVerViewBean zKVerViewBean, int i) {
        UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
        if (i == -1) {
            this.vpd_fai_user_icon.setImageResource(R.mipmap.ic_null_big);
            if (userInfo != null) {
                this.vpd_fai_user_name.setText(userInfo.getName());
                this.vpd_fai_user_pin.setText(userInfo.getUser_PIN());
            }
            this.tv_fail_msg.setText(zKVerViewBean.getFailMsg());
            this.vpd_fa_att_record_full.setText(zKVerViewBean.getUiSpecialState());
        } else if (i == 13) {
            if (!ZKLauncher.sFingerphoto.equals("1")) {
                Glide.with(this.vpd_fai_user_icon.getContext()).load(Integer.valueOf(R.mipmap.ic_2_fingerprint_0002)).into(this.vpd_fai_user_icon);
            } else {
                setFingerImage(this.vpd_fai_user_icon, this.viewBean.getUiFingerprint(), this.viewBean.getUiFPWidth(), this.viewBean.getUiFPHeight());
            }
            this.vpd_fai_user_name.setText(zKVerViewBean.getPrivacyName());
            this.vpd_fai_user_pin.setText(zKVerViewBean.getPrivacyPin());
            this.tv_fail_msg.setText(zKVerViewBean.getFailMsg());
            this.vpd_fa_att_record_full.setText(zKVerViewBean.getUiSpecialState());
        } else if (i == 23) {
            this.vpd_fai_user_icon.setImageResource(R.mipmap.ic_2_pin_0006);
            this.tv_fail_msg.setText(zKVerViewBean.getFailMsg());
            this.vpd_fai_user_name.setText(zKVerViewBean.getPrivacyName());
            this.vpd_fai_user_pin.setText(zKVerViewBean.getPrivacyPin());
            this.vpd_fa_att_record_full.setText(zKVerViewBean.getUiSpecialState());
        } else if (i == 33) {
            this.vpd_fai_user_icon.setImageResource(R.mipmap.ic_2_password_0005);
            this.vpd_fai_user_name.setText(zKVerViewBean.getPrivacyName());
            this.vpd_fai_user_pin.setText(zKVerViewBean.getPrivacyPin());
            this.tv_fail_msg.setText(zKVerViewBean.getFailMsg());
            this.vpd_fa_att_record_full.setText(zKVerViewBean.getUiSpecialState());
        } else if (i == 43) {
            this.vpd_fai_user_icon.setImageResource(R.mipmap.ic_2_card_0007);
            this.vpd_fai_user_name.setText(zKVerViewBean.getPrivacyName());
            this.vpd_fai_user_pin.setText(zKVerViewBean.getPrivacyPin());
            this.tv_fail_msg.setText(zKVerViewBean.getFailMsg());
            this.vpd_fa_att_record_full.setText(zKVerViewBean.getUiSpecialState());
        } else if (i == 53) {
            this.vpd_fai_user_icon.setImageResource(R.mipmap.ic_2_face_0004);
            this.vpd_fai_user_name.setText(zKVerViewBean.getPrivacyName());
            this.vpd_fai_user_pin.setText(zKVerViewBean.getPrivacyPin());
            this.tv_fail_msg.setText(zKVerViewBean.getFailMsg());
            this.vpd_fa_att_record_full.setText(zKVerViewBean.getUiSpecialState());
        } else if (i == 83) {
            this.vpd_fai_user_icon.setImageResource(R.mipmap.ic_2_palm_0003);
            this.vpd_fai_user_name.setText(zKVerViewBean.getPrivacyName());
            this.vpd_fai_user_pin.setText(zKVerViewBean.getPrivacyPin());
            this.tv_fail_msg.setText(zKVerViewBean.getFailMsg());
            this.vpd_fa_att_record_full.setText(zKVerViewBean.getUiSpecialState());
        }
    }

    private void setSuccessInfo(ZKVerViewBean zKVerViewBean, int i) {
        ShortState shortState = null;
        this.vpd_su_icon.setImageBitmap((Bitmap) null);
        this.vpd_su_icon.setBackgroundResource(0);
        if (zKVerViewBean.isUiTFPhoto()) {
            File file = new File(ZKFilePath.PICTURE_PATH + zKVerViewBean.getUiPin() + ZKFilePath.SUFFIX_IMAGE);
            File file2 = new File(ZKFilePath.FACE_PATH + zKVerViewBean.getUiPin() + ZKFilePath.SUFFIX_IMAGE);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setInterpolator(new AccelerateInterpolator());
            alphaAnimation.setDuration(300);
            this.vpd_su_icon.setAnimation(alphaAnimation);
            try {
                if (file.exists()) {
                    byte[] userPhoto = BitmapUtils.getUserPhoto(zKVerViewBean.getUiPin());
                    this.vpd_su_icon.setImageBitmap(BitmapFactory.decodeByteArray(userPhoto, 0, userPhoto.length));
                } else if (file.exists() || !file2.exists()) {
                    this.vpd_su_icon.setBackgroundResource(R.mipmap.ic_null_big);
                } else {
                    int length = (int) file2.length();
                    byte[] bArr = new byte[length];
                    try {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file2));
                        bufferedInputStream.read(bArr, 0, length);
                        bufferedInputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    if (ZKLauncher.zkDataEncdec == 1) {
                        bArr = AES256Util.decrypt(bArr, ZKLauncher.PUBLIC_KEY, ZKLauncher.iv);
                    }
                    this.vpd_su_icon.setImageBitmap(BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                this.vpd_su_icon.setBackgroundResource(R.mipmap.ic_null_big);
            }
        } else if (i != 11) {
            if (i == 21) {
                this.vpd_su_icon.setImageResource(R.mipmap.ic_2_pin_0003);
            } else if (i == 31) {
                this.vpd_su_icon.setImageResource(R.mipmap.ic_2_password_0002);
            } else if (i == 41) {
                this.vpd_su_icon.setImageResource(R.mipmap.ic_2_card_0005);
            } else if (i == 51) {
                this.vpd_su_icon.setImageResource(R.mipmap.ic_2_face_0002);
            } else if (i == 81) {
                this.vpd_su_icon.setImageResource(R.mipmap.ic_2_palm_0002);
            }
        } else if (!ZKLauncher.sFingerphoto.equals("1")) {
            Glide.with(this.vpd_su_icon.getContext()).load(Integer.valueOf(R.mipmap.ic_2_fingerprint_0002)).into(this.vpd_su_icon);
        } else {
            setFingerImage(this.vpd_su_icon, this.viewBean.getUiFingerprint(), this.viewBean.getUiFPWidth(), this.viewBean.getUiFPHeight());
        }
        if (TextUtils.isEmpty(zKVerViewBean.getUiPin())) {
            this.vpd_su_user_name.setVisibility(8);
            this.vpd_su_user_pin.setVisibility(8);
        } else {
            this.vpd_su_user_name.setVisibility(0);
            this.vpd_su_user_pin.setVisibility(0);
            if (zKVerViewBean.getUiName() != null) {
                this.vpd_su_name.setText(zKVerViewBean.getPrivacyName().replace("#", " "));
            } else {
                this.vpd_su_name.setText(zKVerViewBean.getPrivacyName());
            }
            this.vpd_su_pin.setText(zKVerViewBean.getPrivacyPin());
        }
        this.vpd_su_date.setText(zKVerViewBean.getUiSignInTime());
        if (zKVerViewBean.getUiRepeatTime() != null) {
            this.vpd_su_repeat.setVisibility(0);
            this.vpd_su_repeat.setText(zKVerViewBean.getUiRepeatTime());
            this.vpd_su_repeat.setTextColor(getResources().getColor(R.color.launcher_clr_fd2828));
        } else {
            this.vpd_su_repeat.setVisibility(8);
        }
        if (zKVerViewBean.getUiSpecialState() != null) {
            this.vpd_su_att_record_full.setVisibility(0);
            this.vpd_su_att_record_full.setText(zKVerViewBean.getUiSpecialState());
        } else {
            this.vpd_su_att_record_full.setVisibility(8);
        }
        if (zKVerViewBean.getUiStatus() == 255 || TextUtils.isEmpty(zKVerViewBean.getUiPin())) {
            this.vpd_su_ll_status.setVisibility(8);
        } else {
            this.vpd_su_ll_status.setVisibility(0);
            try {
                List query = new ShortState().getQueryBuilder().where().eq("State_No", Integer.valueOf(zKVerViewBean.getUiStatus())).query();
                if (query != null && !query.isEmpty()) {
                    shortState = (ShortState) query.get(0);
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            if (shortState != null) {
                if (shortState.getDescription() == null) {
                    if (shortState.getState_Name() != null) {
                        String state_Name = shortState.getState_Name();
                        state_Name.hashCode();
                        char c2 = 65535;
                        switch (state_Name.hashCode()) {
                            case -892482113:
                                if (state_Name.equals("state0")) {
                                    c2 = 0;
                                    break;
                                }
                                break;
                            case -892482112:
                                if (state_Name.equals("state1")) {
                                    c2 = 1;
                                    break;
                                }
                                break;
                            case -892482111:
                                if (state_Name.equals("state2")) {
                                    c2 = 2;
                                    break;
                                }
                                break;
                            case -892482110:
                                if (state_Name.equals("state3")) {
                                    c2 = 3;
                                    break;
                                }
                                break;
                            case -892482109:
                                if (state_Name.equals("state4")) {
                                    c2 = 4;
                                    break;
                                }
                                break;
                            case -892482108:
                                if (state_Name.equals("state5")) {
                                    c2 = 5;
                                    break;
                                }
                                break;
                        }
                        switch (c2) {
                            case 0:
                                this.vpd_su_state.setText(getString(R.string.zk_rec_check_in));
                                break;
                            case 1:
                                this.vpd_su_state.setText(getString(R.string.zk_rec_check_out));
                                break;
                            case 2:
                                this.vpd_su_state.setText(getString(R.string.zk_rec_break_in));
                                break;
                            case 3:
                                this.vpd_su_state.setText(getString(R.string.zk_rec_break_out));
                                break;
                            case 4:
                                this.vpd_su_state.setText(getString(R.string.zk_rec_overtime_in));
                                break;
                            case 5:
                                this.vpd_su_state.setText(getString(R.string.zk_rec_overtime_out));
                                break;
                            default:
                                this.vpd_su_state.setText(shortState.getDescription());
                                break;
                        }
                    }
                } else {
                    this.vpd_su_state.setText(shortState.getDescription());
                }
                if (ZKLauncher.sAccessRuleType == 1 && ZKLauncher.sLockFunOn == 15) {
                    this.vpd_su_ll_status.setVisibility(8);
                }
            }
        }
        if (zKVerViewBean.getUiShortMessage() != null) {
            this.vpd_su_message.setVisibility(0);
        } else {
            this.vpd_su_message.setVisibility(8);
        }
        if (ZKLauncher.locationFunOn) {
            this.vpd_locate_ll.setVisibility(0);
            this.vpd_su_locate_info.setText(String.format(Locale.US, "%f\n%f", new Object[]{Double.valueOf(ZKLauncher.longitude), Double.valueOf(ZKLauncher.latitude)}));
            return;
        }
        this.vpd_locate_ll.setVisibility(8);
    }

    public static Bitmap getLoacalBitmap(String str) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setVerifySucIcon(List<ZKMarkTypeBean> list, UserInfo userInfo) {
        int i;
        if (userInfo != null) {
            if (ZKLauncher.sAccessRuleType == 1) {
                i = this.zkVerOption.getVerifyType(this.mContext);
            } else {
                i = userInfo.getVerify_Type();
            }
            switch (i) {
                case 8:
                    setVerifyTypeIndex8(list);
                    return;
                case 9:
                    setVerifyTypeIndex9(list);
                    return;
                case 10:
                    setVerifyTypeIndex10(list);
                    return;
                case 11:
                    setVerifyTypeIndex11(list);
                    return;
                case 12:
                    setVerifyTypeIndex12(list);
                    return;
                case 13:
                    setVerifyTypeIndex13(list);
                    return;
                case 14:
                    setVerifyTypeIndex14(list);
                    return;
                case 16:
                    setVerifyTypeIndex16(list);
                    return;
                case 17:
                    setVerifyTypeIndex17(list);
                    return;
                case 18:
                    setVerifyTypeIndex18(list);
                    return;
                case 19:
                    setVerifyTypeIndex19(list);
                    return;
                case 20:
                    setVerifyTypeIndex20(list);
                    return;
                default:
                    return;
            }
        }
    }

    private void setVerifyTypeIndex8(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        boolean z = false;
        if (!ZKVerProcessPar.OTHER_FLAG || size <= 1) {
            i = 0;
        } else {
            size--;
            i = 1;
        }
        if (size >= 2) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(list.get(i).getType()).ordinal()];
        int i3 = R.mipmap.ic_2_pin_0004;
        int i4 = R.mipmap.ic_2_fingerprint_0004;
        if (i2 == 1) {
            if (z) {
                i4 = R.mipmap.ic_2_fingerprint_0001;
            }
            this.mIcon2 = i4;
            this.mIcon3 = R.mipmap.ic_2_pin_0004;
        } else if (i2 == 2) {
            if (z) {
                i3 = R.mipmap.ic_2_pin_0001;
            }
            this.mIcon2 = i3;
            this.mIcon3 = R.mipmap.ic_2_fingerprint_0004;
        }
        int i5 = z ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i5;
        this.mLine3 = R.color.launcher_clr_dbdbdb;
        setIcon2Resource(this.mIcon2, i5);
        setIcon3Resource(this.mIcon3, this.mLine3);
    }

    /* renamed from: com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog$6  reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.zktechnology.android.verify.bean.process.ZKVerifyType[] r0 = com.zktechnology.android.verify.bean.process.ZKVerifyType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType = r0
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog.AnonymousClass6.<clinit>():void");
        }
    }

    private void setVerifyTypeIndex9(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        if (ZKVerProcessPar.KEY_BOARD_1V1) {
            set1V1FirstIcon();
        }
        boolean z = false;
        if ((ZKVerProcessPar.OTHER_FLAG || ZKVerProcessPar.KEY_BOARD_1V1) && size > 1) {
            size--;
            i = 1;
        } else {
            i = 0;
        }
        if (size >= 2) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(list.get(i).getType()).ordinal()];
        int i3 = R.mipmap.ic_2_password_0003;
        int i4 = R.mipmap.ic_2_fingerprint_0004;
        if (i2 == 1) {
            if (z) {
                i4 = R.mipmap.ic_2_fingerprint_0001;
            }
            this.mIcon2 = i4;
            this.mIcon3 = R.mipmap.ic_2_password_0003;
        } else if (i2 == 3) {
            if (z) {
                i3 = R.mipmap.ic_2_password_0001;
            }
            this.mIcon2 = i3;
            this.mIcon3 = R.mipmap.ic_2_fingerprint_0004;
        }
        int i5 = z ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i5;
        this.mLine3 = R.color.launcher_clr_dbdbdb;
        setIcon2Resource(this.mIcon2, i5);
        setIcon3Resource(this.mIcon3, this.mLine3);
    }

    private void setVerifyTypeIndex10(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        if (ZKVerProcessPar.KEY_BOARD_1V1) {
            set1V1FirstIcon();
        }
        boolean z = false;
        if ((ZKVerProcessPar.OTHER_FLAG || ZKVerProcessPar.KEY_BOARD_1V1) && size > 1) {
            size--;
            i = 1;
        } else {
            i = 0;
        }
        if (size >= 2) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(list.get(i).getType()).ordinal()];
        int i3 = R.mipmap.ic_2_card_0006;
        int i4 = R.mipmap.ic_2_fingerprint_0004;
        if (i2 == 1) {
            if (z) {
                i4 = R.mipmap.ic_2_fingerprint_0001;
            }
            this.mIcon2 = i4;
            this.mIcon3 = R.mipmap.ic_2_card_0006;
        } else if (i2 == 4) {
            if (z) {
                i3 = R.mipmap.ic_2_card_0001;
            }
            this.mIcon2 = i3;
            this.mIcon3 = R.mipmap.ic_2_fingerprint_0004;
        }
        int i5 = z ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i5;
        this.mLine3 = R.color.launcher_clr_dbdbdb;
        setIcon2Resource(this.mIcon2, i5);
        setIcon3Resource(this.mIcon3, this.mLine3);
    }

    private void setVerifyTypeIndex11(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        if (ZKVerProcessPar.KEY_BOARD_1V1) {
            set1V1FirstIcon();
        }
        boolean z = false;
        if ((ZKVerProcessPar.OTHER_FLAG || ZKVerProcessPar.KEY_BOARD_1V1) && size > 1) {
            size--;
            i = 1;
        } else {
            i = 0;
        }
        if (size >= 2) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(list.get(i).getType()).ordinal()];
        int i3 = R.mipmap.ic_2_card_0006;
        int i4 = R.mipmap.ic_2_password_0003;
        if (i2 == 3) {
            if (z) {
                i4 = R.mipmap.ic_2_password_0001;
            }
            this.mIcon2 = i4;
            this.mIcon3 = R.mipmap.ic_2_card_0006;
        } else if (i2 == 4) {
            if (z) {
                i3 = R.mipmap.ic_2_card_0001;
            }
            this.mIcon2 = i3;
            this.mIcon3 = R.mipmap.ic_2_password_0003;
        }
        int i5 = z ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i5;
        this.mLine3 = R.color.launcher_clr_dbdbdb;
        setIcon2Resource(this.mIcon2, i5);
        setIcon3Resource(this.mIcon3, this.mLine3);
    }

    private void setVerifyTypeIndex12(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        boolean z = false;
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && size > 1) {
            size--;
            i = 1;
        } else {
            i = 0;
        }
        if (ZKVerProcessPar.KEY_BOARD_1V1) {
            set1V1FirstIcon();
        }
        int type = list.get(i).getType();
        boolean z2 = size >= 2;
        if (size >= 3) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(type).ordinal()];
        int i3 = R.mipmap.ic_2_password_0001;
        int i4 = R.mipmap.ic_2_fingerprint_0001;
        int i5 = R.mipmap.ic_2_card_0006;
        if (i2 == 1) {
            if (!z2) {
                i4 = R.mipmap.ic_2_fingerprint_0004;
            }
            this.mIcon2 = i4;
            if (!z) {
                i3 = R.mipmap.ic_2_password_0003;
            }
            this.mIcon3 = i3;
            this.mIcon4 = R.mipmap.ic_2_card_0006;
        } else if (i2 == 3) {
            if (!z2) {
                i3 = R.mipmap.ic_2_password_0003;
            }
            this.mIcon2 = i3;
            if (z) {
                i5 = R.mipmap.ic_2_card_0001;
            }
            this.mIcon3 = i5;
            this.mIcon4 = R.mipmap.ic_2_fingerprint_0004;
        } else if (i2 == 4) {
            if (z2) {
                i5 = R.mipmap.ic_2_card_0001;
            }
            this.mIcon2 = i5;
            if (!z) {
                i4 = R.mipmap.ic_2_fingerprint_0004;
            }
            this.mIcon3 = i4;
            this.mIcon4 = R.mipmap.ic_2_password_0003;
        }
        int i6 = R.color.launcher_clr_7ac143;
        int i7 = z2 ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i7;
        if (!z) {
            i6 = R.color.launcher_clr_dbdbdb;
        }
        this.mLine3 = i6;
        setIcon2Resource(this.mIcon2, i7);
        setIcon3Resource(this.mIcon3, this.mLine3);
        setIcon4Resource(this.mIcon4);
    }

    private void setVerifyTypeIndex13(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        boolean z = false;
        if (!ZKVerProcessPar.OTHER_FLAG || size <= 1) {
            i = 0;
        } else {
            size--;
            i = 1;
        }
        if (ZKVerProcessPar.KEY_BOARD_1V1) {
            set1V1FirstIcon();
        }
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(list.get(i).getType());
        boolean z2 = size >= 2;
        if (size >= 3) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        int i3 = R.mipmap.ic_2_pin_0004;
        int i4 = R.mipmap.ic_2_password_0001;
        int i5 = R.mipmap.ic_2_fingerprint_0001;
        if (i2 == 1) {
            if (!z2) {
                i5 = R.mipmap.ic_2_fingerprint_0004;
            }
            this.mIcon2 = i5;
            if (ZKVerProcessPar.KEY_BOARD_1V1) {
                if (!z) {
                    i4 = R.mipmap.ic_2_password_0003;
                }
                this.mIcon3 = i4;
            } else {
                if (z) {
                    i3 = R.mipmap.ic_2_pin_0001;
                }
                this.mIcon3 = i3;
            }
            this.mIcon4 = R.mipmap.ic_2_password_0003;
        } else if (i2 != 2) {
            if (i2 == 3) {
                if (!z2) {
                    i4 = R.mipmap.ic_2_password_0003;
                }
                this.mIcon2 = i4;
                if (!z) {
                    i5 = R.mipmap.ic_2_fingerprint_0004;
                }
                this.mIcon3 = i5;
                this.mIcon4 = R.mipmap.ic_2_pin_0004;
            }
        } else if (list.size() > 1) {
            int i6 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(list.get(i + 1).getType()).ordinal()];
            if (i6 == 1) {
                if (!z) {
                    i5 = R.mipmap.ic_2_fingerprint_0004;
                }
                this.mIcon2 = i5;
                this.mIcon3 = R.mipmap.ic_2_password_0003;
            } else if (i6 != 3) {
                LogUtils.e(LogUtils.TAG_VERIFY, "Error: 逻辑错误！");
            } else {
                if (!z) {
                    i4 = R.mipmap.ic_2_password_0003;
                }
                this.mIcon2 = i4;
                this.mIcon3 = R.mipmap.ic_2_fingerprint_0004;
            }
        }
        int i7 = R.color.launcher_clr_7ac143;
        int i8 = z2 ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i8;
        if (!z) {
            i7 = R.color.launcher_clr_dbdbdb;
        }
        this.mLine3 = i7;
        setIcon2Resource(this.mIcon2, i8);
        setIcon3Resource(this.mIcon3, this.mLine3);
        int i9 = this.mIcon4;
        if (i9 != 0) {
            setIcon4Resource(i9);
        }
    }

    private void setVerifyTypeIndex14(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        LogUtils.d(LogUtils.TAG_VERIFY, "AAA+ size1 = %s", Integer.valueOf(size));
        if (!ZKVerProcessPar.OTHER_FLAG || size <= 1) {
            i = 0;
        } else {
            size--;
            i = 1;
        }
        boolean z = size >= 2;
        int type = list.get(i).getType();
        ZKVerifyType fromInteger = ZKVerifyType.fromInteger(type);
        LogUtils.d(LogUtils.TAG_VERIFY, "AAA+ size2 = %s, firstType=%s, step1=%s", Integer.valueOf(size), Integer.valueOf(type), Boolean.valueOf(z));
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[fromInteger.ordinal()];
        int i3 = R.mipmap.ic_2_card_0006;
        int i4 = R.mipmap.ic_2_fingerprint_0004;
        if (i2 == 1) {
            if (z) {
                i4 = R.mipmap.ic_2_fingerprint_0001;
            }
            this.mIcon2 = i4;
            this.mIcon3 = R.mipmap.ic_2_card_0006;
        } else if (i2 == 2) {
            this.mIcon2 = z ? R.mipmap.ic_2_pin_0001 : R.mipmap.ic_2_pin_0004;
            this.mIcon3 = R.mipmap.ic_2_fingerprint_0004;
        } else if (i2 == 4) {
            if (z) {
                i3 = R.mipmap.ic_2_card_0001;
            }
            this.mIcon2 = i3;
            this.mIcon3 = R.mipmap.ic_2_fingerprint_0004;
        }
        int i5 = z ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i5;
        this.mLine3 = R.color.launcher_clr_dbdbdb;
        setIcon2Resource(this.mIcon2, i5);
        setIcon3Resource(this.mIcon3, this.mLine3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0072  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setVerifyTypeIndex16(java.util.List<com.zktechnology.android.verify.bean.process.ZKMarkTypeBean> r6) {
        /*
            r5 = this;
            int r0 = r6.size()
            boolean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1
            if (r1 == 0) goto L_0x000b
            r5.set1V1FirstIcon()
        L_0x000b:
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L_0x002f
            java.lang.Object r3 = r6.get(r1)
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r3 = (com.zktechnology.android.verify.bean.process.ZKMarkTypeBean) r3
            int r3 = r3.getType()
            boolean r4 = com.zktechnology.android.verify.utils.ZKVerProcessPar.OTHER_FLAG
            if (r4 != 0) goto L_0x0029
            boolean r4 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1
            if (r4 != 0) goto L_0x0029
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.fromInteger(r3)
            com.zktechnology.android.verify.bean.process.ZKVerifyType r4 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN
            if (r3 != r4) goto L_0x002f
        L_0x0029:
            if (r0 <= r2) goto L_0x002f
            int r0 = r0 + -1
            r3 = r2
            goto L_0x0030
        L_0x002f:
            r3 = r1
        L_0x0030:
            r4 = 2
            if (r0 < r4) goto L_0x0034
            r1 = r2
        L_0x0034:
            java.lang.Object r6 = r6.get(r3)
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r6 = (com.zktechnology.android.verify.bean.process.ZKMarkTypeBean) r6
            int r6 = r6.getType()
            com.zktechnology.android.verify.bean.process.ZKVerifyType r6 = com.zktechnology.android.verify.bean.process.ZKVerifyType.fromInteger(r6)
            int[] r0 = com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog.AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType
            int r6 = r6.ordinal()
            r6 = r0[r6]
            r0 = 2131558438(0x7f0d0026, float:1.8742192E38)
            r3 = 2131558443(0x7f0d002b, float:1.8742202E38)
            if (r6 == r2) goto L_0x0060
            r2 = 5
            if (r6 == r2) goto L_0x0056
            goto L_0x0069
        L_0x0056:
            if (r1 == 0) goto L_0x005b
            r0 = 2131558435(0x7f0d0023, float:1.8742186E38)
        L_0x005b:
            r5.mIcon2 = r0
            r5.mIcon3 = r3
            goto L_0x0069
        L_0x0060:
            if (r1 == 0) goto L_0x0065
            r3 = 2131558441(0x7f0d0029, float:1.8742198E38)
        L_0x0065:
            r5.mIcon2 = r3
            r5.mIcon3 = r0
        L_0x0069:
            r6 = 2131099810(0x7f0600a2, float:1.7811984E38)
            if (r1 == 0) goto L_0x0072
            r0 = 2131099806(0x7f06009e, float:1.7811976E38)
            goto L_0x0073
        L_0x0072:
            r0 = r6
        L_0x0073:
            r5.mLine2 = r0
            r5.mLine3 = r6
            int r6 = r5.mIcon2
            r5.setIcon2Resource(r6, r0)
            int r6 = r5.mIcon3
            int r0 = r5.mLine3
            r5.setIcon3Resource(r6, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog.setVerifyTypeIndex16(java.util.List):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0074  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setVerifyTypeIndex17(java.util.List<com.zktechnology.android.verify.bean.process.ZKMarkTypeBean> r6) {
        /*
            r5 = this;
            int r0 = r6.size()
            boolean r1 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1
            if (r1 == 0) goto L_0x000b
            r5.set1V1FirstIcon()
        L_0x000b:
            r1 = 1
            r2 = 0
            if (r0 <= 0) goto L_0x002f
            java.lang.Object r3 = r6.get(r2)
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r3 = (com.zktechnology.android.verify.bean.process.ZKMarkTypeBean) r3
            int r3 = r3.getType()
            boolean r4 = com.zktechnology.android.verify.utils.ZKVerProcessPar.OTHER_FLAG
            if (r4 != 0) goto L_0x0029
            boolean r4 = com.zktechnology.android.verify.utils.ZKVerProcessPar.KEY_BOARD_1V1
            if (r4 != 0) goto L_0x0029
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.fromInteger(r3)
            com.zktechnology.android.verify.bean.process.ZKVerifyType r4 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN
            if (r3 != r4) goto L_0x002f
        L_0x0029:
            if (r0 <= r1) goto L_0x002f
            int r0 = r0 + -1
            r3 = r1
            goto L_0x0030
        L_0x002f:
            r3 = r2
        L_0x0030:
            r4 = 2
            if (r0 < r4) goto L_0x0034
            goto L_0x0035
        L_0x0034:
            r1 = r2
        L_0x0035:
            java.lang.Object r6 = r6.get(r3)
            com.zktechnology.android.verify.bean.process.ZKMarkTypeBean r6 = (com.zktechnology.android.verify.bean.process.ZKMarkTypeBean) r6
            int r6 = r6.getType()
            com.zktechnology.android.verify.bean.process.ZKVerifyType r6 = com.zktechnology.android.verify.bean.process.ZKVerifyType.fromInteger(r6)
            int[] r0 = com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog.AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType
            int r6 = r6.ordinal()
            r6 = r0[r6]
            r0 = 3
            r2 = 2131558438(0x7f0d0026, float:1.8742192E38)
            r3 = 2131558453(0x7f0d0035, float:1.8742222E38)
            if (r6 == r0) goto L_0x0062
            r0 = 5
            if (r6 == r0) goto L_0x0058
            goto L_0x006b
        L_0x0058:
            if (r1 == 0) goto L_0x005d
            r2 = 2131558435(0x7f0d0023, float:1.8742186E38)
        L_0x005d:
            r5.mIcon2 = r2
            r5.mIcon3 = r3
            goto L_0x006b
        L_0x0062:
            if (r1 == 0) goto L_0x0067
            r3 = 2131558451(0x7f0d0033, float:1.8742218E38)
        L_0x0067:
            r5.mIcon2 = r3
            r5.mIcon3 = r2
        L_0x006b:
            r6 = 2131099810(0x7f0600a2, float:1.7811984E38)
            if (r1 == 0) goto L_0x0074
            r0 = 2131099806(0x7f06009e, float:1.7811976E38)
            goto L_0x0075
        L_0x0074:
            r0 = r6
        L_0x0075:
            r5.mLine2 = r0
            r5.mLine3 = r6
            int r6 = r5.mIcon2
            r5.setIcon2Resource(r6, r0)
            int r6 = r5.mIcon3
            int r0 = r5.mLine3
            r5.setIcon3Resource(r6, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog.setVerifyTypeIndex17(java.util.List):void");
    }

    private void setVerifyTypeIndex18(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        if (ZKVerProcessPar.KEY_BOARD_1V1) {
            set1V1FirstIcon();
        }
        boolean z = false;
        if ((ZKVerProcessPar.OTHER_FLAG || ZKVerProcessPar.KEY_BOARD_1V1) && size > 1) {
            size--;
            i = 1;
        } else {
            i = 0;
        }
        if (size >= 2) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(list.get(i).getType()).ordinal()];
        int i3 = R.mipmap.ic_2_face_0004;
        int i4 = R.mipmap.ic_2_card_0006;
        if (i2 == 4) {
            if (z) {
                i4 = R.mipmap.ic_2_card_0001;
            }
            this.mIcon2 = i4;
            this.mIcon3 = R.mipmap.ic_2_face_0004;
        } else if (i2 == 5) {
            if (z) {
                i3 = R.mipmap.ic_2_face_0001;
            }
            this.mIcon2 = i3;
            this.mIcon3 = R.mipmap.ic_2_card_0006;
        }
        int i5 = z ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i5;
        this.mLine3 = R.color.launcher_clr_dbdbdb;
        setIcon2Resource(this.mIcon2, i5);
        setIcon3Resource(this.mIcon3, this.mLine3);
    }

    private void setVerifyTypeIndex19(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        boolean z = false;
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && size > 1) {
            size--;
            i = 1;
        } else {
            i = 0;
        }
        if (ZKVerProcessPar.KEY_BOARD_1V1) {
            set1V1FirstIcon();
        }
        int type = list.get(i).getType();
        boolean z2 = size >= 2;
        if (size >= 3) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(type).ordinal()];
        int i3 = R.mipmap.ic_2_card_0001;
        int i4 = R.mipmap.ic_2_fingerprint_0001;
        int i5 = R.mipmap.ic_2_face_0004;
        if (i2 == 1) {
            if (!z2) {
                i4 = R.mipmap.ic_2_fingerprint_0004;
            }
            this.mIcon2 = i4;
            if (!z) {
                i3 = R.mipmap.ic_2_card_0006;
            }
            this.mIcon3 = i3;
            this.mIcon4 = R.mipmap.ic_2_face_0004;
        } else if (i2 == 4) {
            if (!z2) {
                i3 = R.mipmap.ic_2_card_0006;
            }
            this.mIcon2 = i3;
            if (z) {
                i5 = R.mipmap.ic_2_face_0001;
            }
            this.mIcon3 = i5;
            this.mIcon4 = R.mipmap.ic_2_fingerprint_0004;
        } else if (i2 == 5) {
            if (z2) {
                i5 = R.mipmap.ic_2_face_0001;
            }
            this.mIcon2 = i5;
            if (!z) {
                i4 = R.mipmap.ic_2_fingerprint_0004;
            }
            this.mIcon3 = i4;
            this.mIcon4 = R.mipmap.ic_2_card_0006;
        }
        int i6 = R.color.launcher_clr_7ac143;
        int i7 = z2 ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i7;
        if (!z) {
            i6 = R.color.launcher_clr_dbdbdb;
        }
        this.mLine3 = i6;
        setIcon2Resource(this.mIcon2, i7);
        setIcon3Resource(this.mIcon3, this.mLine3);
        setIcon4Resource(this.mIcon4);
    }

    private void setVerifyTypeIndex20(List<ZKMarkTypeBean> list) {
        int i;
        int size = list.size();
        boolean z = false;
        if ((ZKVerProcessPar.KEY_BOARD_1V1 || ZKVerProcessPar.OTHER_FLAG) && size > 1) {
            size--;
            i = 1;
        } else {
            i = 0;
        }
        if (ZKVerProcessPar.KEY_BOARD_1V1) {
            set1V1FirstIcon();
        }
        int type = list.get(i).getType();
        boolean z2 = size >= 2;
        if (size >= 3) {
            z = true;
        }
        int i2 = AnonymousClass6.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(type).ordinal()];
        int i3 = R.mipmap.ic_2_password_0001;
        int i4 = R.mipmap.ic_2_fingerprint_0001;
        int i5 = R.mipmap.ic_2_face_0004;
        if (i2 == 1) {
            if (!z2) {
                i4 = R.mipmap.ic_2_fingerprint_0004;
            }
            this.mIcon2 = i4;
            if (!z) {
                i3 = R.mipmap.ic_2_password_0003;
            }
            this.mIcon3 = i3;
            this.mIcon4 = R.mipmap.ic_2_face_0004;
        } else if (i2 == 3) {
            if (!z2) {
                i3 = R.mipmap.ic_2_password_0003;
            }
            this.mIcon2 = i3;
            if (z) {
                i5 = R.mipmap.ic_2_face_0001;
            }
            this.mIcon3 = i5;
            this.mIcon4 = R.mipmap.ic_2_fingerprint_0004;
        } else if (i2 == 5) {
            if (z2) {
                i5 = R.mipmap.ic_2_face_0001;
            }
            this.mIcon2 = i5;
            if (!z) {
                i4 = R.mipmap.ic_2_fingerprint_0004;
            }
            this.mIcon3 = i4;
            this.mIcon4 = R.mipmap.ic_2_password_0003;
        }
        int i6 = R.color.launcher_clr_7ac143;
        int i7 = z2 ? R.color.launcher_clr_7ac143 : R.color.launcher_clr_dbdbdb;
        this.mLine2 = i7;
        if (!z) {
            i6 = R.color.launcher_clr_dbdbdb;
        }
        this.mLine3 = i6;
        setIcon2Resource(this.mIcon2, i7);
        setIcon3Resource(this.mIcon3, this.mLine3);
        setIcon4Resource(this.mIcon4);
    }

    private void set1V1FirstIcon() {
        setIcon1Resource(R.mipmap.ic_2_pin_0001, R.color.launcher_clr_7ac143);
    }

    private void setIcon1Resource(int i, int i2) {
        this.vpd_icon_1.setVisibility(0);
        this.left_icon_1.setImageResource(i);
        this.left_view_1.setBackgroundResource(i2);
    }

    private void setIcon2Resource(int i, int i2) {
        this.vpd_icon_2.setVisibility(0);
        this.left_icon_2.setImageResource(i);
        this.left_view_2.setBackgroundResource(i2);
    }

    private void setIcon3Resource(int i, int i2) {
        this.vpd_icon_3.setVisibility(0);
        this.left_icon_3.setImageResource(i);
        this.left_view_3.setBackgroundResource(i2);
    }

    private void setIcon4Resource(int i) {
        this.vpd_icon_4.setVisibility(0);
        this.left_icon_4.setImageResource(i);
    }

    public void clearDatas() {
        if (getActivity() != null) {
            hideSoftKeyboardAndUpdateRTL(true, this.vpd_pin_edit);
        }
        if (ZKVerController.getInstance().getState() == ZKVerConState.STATE_WAIT) {
            if (!this.bolCleanData) {
                ZKVerProcessPar.cleanData(13);
            }
            ZKVerProcessPar.cleanBtnWidget();
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            FileLogUtils.writeTouchLog("setTTouchAction: clearDatas");
            this.isShow = false;
        }
    }

    public void onlyDisView() {
        this.bolCleanData = true;
    }

    public void disViewAndClearData() {
        this.bolCleanData = false;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        ZKEventLauncher.sendVerProcessDismissed();
    }

    public void show(FragmentManager fragmentManager, String str) {
        super.show(fragmentManager, str);
        this.isShow = true;
        updateView();
    }

    public void setVerifiedInfoTimeOutMs() {
        int intOption;
        ZKVerViewBean lastViewBean = ZKVerProcessPar.getLastViewBean();
        ZKLauncher.sVerifyDialogTimeOut = DBManager.getInstance().getIntOption("VerifiedInfoDelay", 5);
        if (lastViewBean != null) {
            if (lastViewBean.getUiType() == 100) {
                if (ZKLauncher.sAccessRuleType == 0) {
                    ZKLauncher.sVerifyDialogTimeOut = 8;
                } else {
                    ZKLauncher.sVerifyDialogTimeOut = DBManager.getInstance().getIntOption(ZKDBConfig.ACC_MULTI_USER_VERIFY_TIME, 8);
                }
            } else if (!isFollowGlobalTimeout(lastViewBean.getUiType()) && ZKLauncher.sVerifyDialogTimeOut < (intOption = DBManager.getInstance().getIntOption(ZKDBConfig.OPT_GENERAL_DIALOG_TIMEOUT, 3))) {
                ZKLauncher.sVerifyDialogTimeOut = intOption;
            }
        }
        LogUtils.verifyLog("setVerifiedInfoTimeOutMs time:" + ZKLauncher.sVerifyDialogTimeOut);
        setMyTimeout(ZKLauncher.sVerifyDialogTimeOut);
    }

    public void setTimeout(int i) {
        setMyTimeout(i);
        resetInactiveTimeout();
    }

    /* JADX WARNING: type inference failed for: r9v0, types: [com.zktechnology.android.verify.dialog.view.ZKVerProcessDialog] */
    public void updateView() {
        Log.d(TAG, "upDateUi: ");
        setVerifiedInfoTimeOutMs();
        resetInactiveTimeout();
        this.viewBean = ZKVerProcessPar.getLastViewBean();
        this.typeBeans = ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList();
        UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
        this.bean = userInfo;
        if (userInfo != null) {
            this.userPin = userInfo.getUser_PIN();
        }
        serVerifyIconClean();
        ZKVerViewBean zKVerViewBean = this.viewBean;
        if (zKVerViewBean != null) {
            int uiType = zKVerViewBean.getUiType();
            Log.d(TAG, "upDateUi uiType : " + uiType);
            this.vpd_multi_verify_wait.setVisibility(8);
            if (uiType == 999) {
                this.ll_remote_delay.setVisibility(0);
                this.cl_verify_info.setVisibility(8);
                Animation loadAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_round_rotate);
                loadAnimation.setInterpolator(new LinearInterpolator());
                this.iv_verify_waiting.startAnimation(loadAnimation);
            } else {
                this.ll_remote_delay.setVisibility(8);
                this.cl_verify_info.setVisibility(0);
                this.iv_verify_waiting.clearAnimation();
            }
            if (uiType == -1) {
                this.vpd_fai_user.setVisibility(0);
                this.vpd_key_board_1v1.setVisibility(8);
                this.vpd_way.setVisibility(8);
                this.vpd_verify.setVisibility(0);
                this.vpd_card_st.setVisibility(4);
                this.vpd_card_fa.setVisibility(4);
                this.vpd_finger_st.setVisibility(4);
                this.vpd_finger_fa.setVisibility(4);
                this.vpd_password.setVisibility(4);
                this.vpd_face_st.setVisibility(4);
                this.vpd_face_fa.setVisibility(4);
                this.vpd_palm_st.setVisibility(4);
                this.vpd_palm_fa.setVisibility(4);
                this.vpd_pin.setVisibility(4);
                this.vpd_pin_fa.setVisibility(4);
                this.vpd_su.setVisibility(4);
                setFailInfo(this.viewBean, 53);
            } else if (uiType == 0) {
                this.vpd_verify.setVisibility(8);
                this.singleThreadExecutor.submit(this.setSuperManegerVerifyIconTask);
            } else if (uiType != 1) {
                if (uiType != 100) {
                    switch (uiType) {
                        case 10:
                            this.vpd_fai_user.setVisibility(4);
                            this.vpd_key_board_1v1.setVisibility(8);
                            this.vpd_way.setVisibility(8);
                            this.vpd_verify.setVisibility(0);
                            this.vpd_face_st.setVisibility(4);
                            this.vpd_face_fa.setVisibility(4);
                            this.vpd_palm_st.setVisibility(4);
                            this.vpd_palm_fa.setVisibility(4);
                            this.vpd_card_st.setVisibility(4);
                            this.vpd_card_fa.setVisibility(4);
                            this.vpd_finger_st.setVisibility(0);
                            this.vpd_finger_fa.setVisibility(4);
                            this.vpd_password.setVisibility(4);
                            this.vpd_pin.setVisibility(4);
                            this.vpd_pin_fa.setVisibility(4);
                            this.vpd_su.setVisibility(4);
                            setVerifySucIcon(this.typeBeans, this.bean);
                            break;
                        case 11:
                            this.vpd_fai_user.setVisibility(4);
                            this.vpd_key_board_1v1.setVisibility(8);
                            this.vpd_way.setVisibility(8);
                            this.vpd_verify.setVisibility(0);
                            this.vpd_face_st.setVisibility(4);
                            this.vpd_face_fa.setVisibility(4);
                            this.vpd_palm_st.setVisibility(4);
                            this.vpd_palm_fa.setVisibility(4);
                            this.vpd_card_st.setVisibility(4);
                            this.vpd_card_fa.setVisibility(4);
                            this.vpd_finger_st.setVisibility(4);
                            this.vpd_finger_fa.setVisibility(4);
                            this.vpd_password.setVisibility(4);
                            this.vpd_pin.setVisibility(4);
                            this.vpd_pin_fa.setVisibility(4);
                            this.vpd_su.setVisibility(0);
                            setSuccessInfo(this.viewBean, uiType);
                            break;
                        case 12:
                            this.vpd_fai_user.setVisibility(4);
                            this.vpd_key_board_1v1.setVisibility(8);
                            this.vpd_way.setVisibility(8);
                            this.vpd_verify.setVisibility(0);
                            this.vpd_face_st.setVisibility(4);
                            this.vpd_face_fa.setVisibility(4);
                            this.vpd_palm_st.setVisibility(4);
                            this.vpd_palm_fa.setVisibility(4);
                            this.vpd_card_st.setVisibility(4);
                            this.vpd_card_fa.setVisibility(4);
                            this.vpd_finger_st.setVisibility(4);
                            this.vpd_finger_fa.setVisibility(0);
                            this.vpd_password.setVisibility(4);
                            this.vpd_pin.setVisibility(4);
                            this.vpd_pin_fa.setVisibility(4);
                            this.vpd_su.setVisibility(4);
                            setVerifySucIcon(this.typeBeans, this.bean);
                            if (!ZKLauncher.sFingerphoto.equals("1")) {
                                Glide.with(this.vpd_finger_fa_icon.getContext()).load(Integer.valueOf(R.mipmap.ic_2_fingerprint_0002)).into(this.vpd_finger_fa_icon);
                            } else {
                                setFingerImage(this.vpd_finger_fa_icon, this.viewBean.getUiFingerprint(), this.viewBean.getUiFPWidth(), this.viewBean.getUiFPHeight());
                            }
                            if (this.viewBean.getUiWayLogin() != null && !this.viewBean.getUiWayLogin().equals("")) {
                                this.vpd_finger_fa_message.setText(this.viewBean.getUiWayLogin());
                            } else if (!TextUtils.isEmpty(this.viewBean.getFailMsg())) {
                                this.vpd_finger_fa_message.setText(this.viewBean.getFailMsg());
                            }
                            if (this.viewBean.getUiAttFull() == null || this.viewBean.getUiAttFull().equals("")) {
                                if (this.viewBean.getUiSpecialState() != null && !this.viewBean.getUiSpecialState().equals("")) {
                                    this.vpd_finger_att_full.setVisibility(0);
                                    this.vpd_finger_att_full.setText(this.viewBean.getUiSpecialState());
                                    break;
                                } else {
                                    this.vpd_finger_att_full.setVisibility(8);
                                    break;
                                }
                            } else {
                                this.vpd_finger_att_full.setVisibility(0);
                                this.vpd_finger_att_full.setText(this.viewBean.getUiAttFull());
                                break;
                            }
                            break;
                        case 13:
                            this.vpd_fai_user.setVisibility(0);
                            this.vpd_key_board_1v1.setVisibility(8);
                            this.vpd_way.setVisibility(8);
                            this.vpd_verify.setVisibility(0);
                            this.vpd_face_st.setVisibility(4);
                            this.vpd_face_fa.setVisibility(4);
                            this.vpd_palm_st.setVisibility(4);
                            this.vpd_palm_fa.setVisibility(4);
                            this.vpd_card_st.setVisibility(4);
                            this.vpd_card_fa.setVisibility(4);
                            this.vpd_finger_st.setVisibility(4);
                            this.vpd_finger_fa.setVisibility(4);
                            this.vpd_password.setVisibility(4);
                            this.vpd_pin.setVisibility(4);
                            this.vpd_pin_fa.setVisibility(4);
                            this.vpd_su.setVisibility(4);
                            setFailInfo(this.viewBean, uiType);
                            break;
                        default:
                            switch (uiType) {
                                case 20:
                                    this.vpd_verify.setVisibility(0);
                                    this.vpd_pin.setVisibility(0);
                                    this.vpd_pin_fa.setVisibility(4);
                                    this.vpd_pin_edit.setText("");
                                    this.vpd_pin_hint.setVisibility(0);
                                    this.vpd_pin_hint.setText("");
                                    this.vpd_pin_edit.setSingleLine(true);
                                    hideSoftKeyboardAndUpdateRTL(false, this.vpd_pin_edit);
                                    setPinType(this.vpd_pin_edit);
                                    setVerifySucIcon(this.typeBeans, this.bean);
                                    this.vpd_fai_user.setVisibility(4);
                                    this.vpd_key_board_1v1.setVisibility(8);
                                    this.vpd_way.setVisibility(8);
                                    this.vpd_face_st.setVisibility(4);
                                    this.vpd_face_fa.setVisibility(4);
                                    this.vpd_palm_st.setVisibility(4);
                                    this.vpd_palm_fa.setVisibility(4);
                                    this.vpd_card_st.setVisibility(4);
                                    this.vpd_card_fa.setVisibility(4);
                                    this.vpd_finger_st.setVisibility(4);
                                    this.vpd_finger_fa.setVisibility(4);
                                    this.vpd_password.setVisibility(4);
                                    this.vpd_su.setVisibility(4);
                                    if (ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 1) {
                                        this.iv_super_password.setVisibility(8);
                                    } else {
                                        this.iv_super_password.setVisibility(0);
                                    }
                                    this.iv_pin_change.setVisibility(8);
                                    this.vpd_pin_edit.setVisibility(0);
                                    this.vpd_pin_next.setVisibility(0);
                                    this.vpd_super_password_edit.setVisibility(8);
                                    this.vpd_super_password_next.setVisibility(8);
                                    break;
                                case 21:
                                    this.vpd_fai_user.setVisibility(4);
                                    this.vpd_key_board_1v1.setVisibility(8);
                                    this.vpd_way.setVisibility(8);
                                    this.vpd_verify.setVisibility(0);
                                    this.vpd_face_st.setVisibility(4);
                                    this.vpd_face_fa.setVisibility(4);
                                    this.vpd_palm_st.setVisibility(4);
                                    this.vpd_palm_fa.setVisibility(4);
                                    this.vpd_card_st.setVisibility(4);
                                    this.vpd_card_fa.setVisibility(4);
                                    this.vpd_finger_st.setVisibility(4);
                                    this.vpd_finger_fa.setVisibility(4);
                                    this.vpd_password.setVisibility(4);
                                    this.vpd_pin.setVisibility(4);
                                    this.vpd_pin_fa.setVisibility(4);
                                    this.vpd_su.setVisibility(0);
                                    setSuccessInfo(this.viewBean, uiType);
                                    break;
                                case 22:
                                    this.vpd_fai_user.setVisibility(4);
                                    this.vpd_key_board_1v1.setVisibility(8);
                                    this.vpd_way.setVisibility(8);
                                    this.vpd_verify.setVisibility(0);
                                    this.vpd_face_st.setVisibility(4);
                                    this.vpd_face_fa.setVisibility(4);
                                    this.vpd_palm_st.setVisibility(4);
                                    this.vpd_palm_fa.setVisibility(4);
                                    this.vpd_card_st.setVisibility(4);
                                    this.vpd_card_fa.setVisibility(4);
                                    this.vpd_finger_st.setVisibility(4);
                                    this.vpd_finger_fa.setVisibility(4);
                                    this.vpd_password.setVisibility(4);
                                    this.vpd_pin.setVisibility(0);
                                    this.vpd_pin_fa.setVisibility(4);
                                    this.vpd_su.setVisibility(4);
                                    this.vpd_pin_hint.setVisibility(0);
                                    this.vpd_pin_hint.setText(R.string.dlg_ver_process_pin_msg);
                                    setVerifySucIcon(this.typeBeans, this.bean);
                                    this.vpd_pin_edit.setText("");
                                    hideSoftKeyboardAndUpdateRTL(false, this.vpd_pin_edit);
                                    setPinType(this.vpd_pin_edit);
                                    break;
                                case 23:
                                    this.vpd_fai_user.setVisibility(0);
                                    this.vpd_key_board_1v1.setVisibility(8);
                                    this.vpd_way.setVisibility(8);
                                    this.vpd_verify.setVisibility(0);
                                    this.vpd_face_st.setVisibility(4);
                                    this.vpd_face_fa.setVisibility(4);
                                    this.vpd_palm_st.setVisibility(4);
                                    this.vpd_palm_fa.setVisibility(4);
                                    this.vpd_card_st.setVisibility(4);
                                    this.vpd_card_fa.setVisibility(4);
                                    this.vpd_finger_st.setVisibility(4);
                                    this.vpd_finger_fa.setVisibility(4);
                                    this.vpd_password.setVisibility(4);
                                    this.vpd_pin.setVisibility(4);
                                    this.vpd_pin_fa.setVisibility(4);
                                    this.vpd_su.setVisibility(4);
                                    setFailInfo(this.viewBean, uiType);
                                    break;
                                default:
                                    switch (uiType) {
                                        case 30:
                                            this.vpd_fai_user.setVisibility(4);
                                            this.vpd_key_board_1v1.setVisibility(8);
                                            this.vpd_way.setVisibility(8);
                                            this.vpd_verify.setVisibility(0);
                                            this.vpd_face_st.setVisibility(4);
                                            this.vpd_face_fa.setVisibility(4);
                                            this.vpd_palm_st.setVisibility(4);
                                            this.vpd_palm_fa.setVisibility(4);
                                            this.vpd_card_st.setVisibility(4);
                                            this.vpd_card_fa.setVisibility(4);
                                            this.vpd_finger_st.setVisibility(4);
                                            this.vpd_finger_fa.setVisibility(4);
                                            this.vpd_password.setVisibility(0);
                                            this.vpd_pin.setVisibility(4);
                                            this.vpd_pin_fa.setVisibility(4);
                                            this.vpd_su.setVisibility(4);
                                            setVerifySucIcon(this.typeBeans, this.bean);
                                            this.vpd_password_edit.setText("");
                                            this.vpd_password_hint.setVisibility(4);
                                            hideSoftKeyboardAndUpdateRTL(false, this.vpd_password_edit);
                                            if (!ZKVerProcessPar.CON_MARK_BEAN.isSuperCrack()) {
                                                this.tv_superTips.setVisibility(8);
                                                break;
                                            } else {
                                                this.tv_superTips.setText(String.format("%s%s", new Object[]{getResources().getString(R.string.serial_number), this.deviceSN}));
                                                this.tv_superTips.setVisibility(0);
                                                break;
                                            }
                                        case 31:
                                            this.vpd_fai_user.setVisibility(4);
                                            this.vpd_key_board_1v1.setVisibility(8);
                                            this.vpd_way.setVisibility(8);
                                            this.vpd_verify.setVisibility(0);
                                            this.vpd_face_st.setVisibility(4);
                                            this.vpd_face_fa.setVisibility(4);
                                            this.vpd_palm_st.setVisibility(4);
                                            this.vpd_palm_fa.setVisibility(4);
                                            this.vpd_card_st.setVisibility(4);
                                            this.vpd_card_fa.setVisibility(4);
                                            this.vpd_finger_st.setVisibility(4);
                                            this.vpd_finger_fa.setVisibility(4);
                                            this.vpd_password.setVisibility(4);
                                            this.vpd_pin.setVisibility(4);
                                            this.vpd_pin_fa.setVisibility(4);
                                            this.vpd_su.setVisibility(0);
                                            setSuccessInfo(this.viewBean, uiType);
                                            break;
                                        case 32:
                                            this.vpd_fai_user.setVisibility(4);
                                            this.vpd_key_board_1v1.setVisibility(8);
                                            this.vpd_way.setVisibility(8);
                                            this.vpd_verify.setVisibility(0);
                                            this.vpd_face_st.setVisibility(4);
                                            this.vpd_face_fa.setVisibility(4);
                                            this.vpd_palm_st.setVisibility(4);
                                            this.vpd_palm_fa.setVisibility(4);
                                            this.vpd_card_st.setVisibility(4);
                                            this.vpd_card_fa.setVisibility(4);
                                            this.vpd_finger_st.setVisibility(4);
                                            this.vpd_finger_fa.setVisibility(4);
                                            this.vpd_password.setVisibility(0);
                                            this.vpd_pin.setVisibility(4);
                                            this.vpd_pin_fa.setVisibility(4);
                                            this.vpd_su.setVisibility(4);
                                            this.vpd_password_hint.setVisibility(0);
                                            setVerifySucIcon(this.typeBeans, this.bean);
                                            this.vpd_password_edit.setText("");
                                            this.vpd_password_hint.setText(this.mContext.getResources().getString(R.string.dlg_ver_process_password_msg));
                                            hideSoftKeyboardAndUpdateRTL(false, this.vpd_password_edit);
                                            if (this.viewBean.getUiWayLogin() != null && !this.viewBean.getUiWayLogin().equals("")) {
                                                this.vpd_password_hint.setText(this.viewBean.getUiWayLogin());
                                            } else if (!TextUtils.isEmpty(this.viewBean.getFailMsg())) {
                                                this.vpd_password_hint.setText(this.viewBean.getFailMsg());
                                            }
                                            if (this.viewBean.getUiAttFull() != null && !this.viewBean.getUiAttFull().equals("")) {
                                                this.vpd_password_hint.setText(this.viewBean.getUiAttFull());
                                                break;
                                            }
                                            break;
                                        case 33:
                                            this.vpd_fai_user.setVisibility(0);
                                            this.vpd_key_board_1v1.setVisibility(8);
                                            this.vpd_way.setVisibility(8);
                                            this.vpd_verify.setVisibility(0);
                                            this.vpd_face_st.setVisibility(4);
                                            this.vpd_face_fa.setVisibility(4);
                                            this.vpd_palm_st.setVisibility(4);
                                            this.vpd_palm_fa.setVisibility(4);
                                            this.vpd_card_st.setVisibility(4);
                                            this.vpd_card_fa.setVisibility(4);
                                            this.vpd_finger_st.setVisibility(4);
                                            this.vpd_finger_fa.setVisibility(4);
                                            this.vpd_password.setVisibility(4);
                                            this.vpd_pin.setVisibility(4);
                                            this.vpd_pin_fa.setVisibility(4);
                                            this.vpd_su.setVisibility(4);
                                            setFailInfo(this.viewBean, uiType);
                                            break;
                                        default:
                                            switch (uiType) {
                                                case 40:
                                                    this.vpd_fai_user.setVisibility(4);
                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                    this.vpd_way.setVisibility(8);
                                                    this.vpd_verify.setVisibility(0);
                                                    this.vpd_face_st.setVisibility(4);
                                                    this.vpd_face_fa.setVisibility(4);
                                                    this.vpd_palm_st.setVisibility(4);
                                                    this.vpd_palm_fa.setVisibility(4);
                                                    this.vpd_card_st.setVisibility(0);
                                                    this.vpd_card_fa.setVisibility(4);
                                                    this.vpd_finger_st.setVisibility(4);
                                                    this.vpd_finger_fa.setVisibility(4);
                                                    this.vpd_password.setVisibility(4);
                                                    this.vpd_pin.setVisibility(4);
                                                    this.vpd_pin_fa.setVisibility(4);
                                                    this.vpd_su.setVisibility(4);
                                                    setVerifySucIcon(this.typeBeans, this.bean);
                                                    break;
                                                case 41:
                                                    this.vpd_fai_user.setVisibility(4);
                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                    this.vpd_way.setVisibility(8);
                                                    this.vpd_verify.setVisibility(0);
                                                    this.vpd_face_st.setVisibility(4);
                                                    this.vpd_face_fa.setVisibility(4);
                                                    this.vpd_palm_st.setVisibility(4);
                                                    this.vpd_palm_fa.setVisibility(4);
                                                    this.vpd_card_st.setVisibility(4);
                                                    this.vpd_card_fa.setVisibility(4);
                                                    this.vpd_finger_st.setVisibility(4);
                                                    this.vpd_finger_fa.setVisibility(4);
                                                    this.vpd_password.setVisibility(4);
                                                    this.vpd_pin.setVisibility(4);
                                                    this.vpd_pin_fa.setVisibility(4);
                                                    this.vpd_su.setVisibility(0);
                                                    setSuccessInfo(this.viewBean, uiType);
                                                    break;
                                                case 42:
                                                    this.vpd_fai_user.setVisibility(4);
                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                    this.vpd_way.setVisibility(8);
                                                    this.vpd_verify.setVisibility(0);
                                                    this.vpd_face_st.setVisibility(4);
                                                    this.vpd_face_fa.setVisibility(4);
                                                    this.vpd_palm_st.setVisibility(4);
                                                    this.vpd_palm_fa.setVisibility(4);
                                                    this.vpd_card_st.setVisibility(4);
                                                    this.vpd_card_fa.setVisibility(0);
                                                    this.vpd_finger_st.setVisibility(4);
                                                    this.vpd_finger_fa.setVisibility(4);
                                                    this.vpd_password.setVisibility(4);
                                                    this.vpd_pin.setVisibility(4);
                                                    this.vpd_pin_fa.setVisibility(4);
                                                    this.vpd_su.setVisibility(4);
                                                    setVerifySucIcon(this.typeBeans, this.bean);
                                                    if (this.viewBean.getUiWayLogin() != null && !this.viewBean.getUiWayLogin().equals("")) {
                                                        this.vpd_card_fa_message.setText(this.viewBean.getUiWayLogin());
                                                    } else if (!TextUtils.isEmpty(this.viewBean.getFailMsg())) {
                                                        this.vpd_card_fa_message.setText(this.viewBean.getFailMsg());
                                                    }
                                                    if (this.viewBean.getUiAttFull() == null || this.viewBean.getUiAttFull().equals("")) {
                                                        if (this.viewBean.getUiSpecialState() != null && !this.viewBean.getUiSpecialState().equals("")) {
                                                            this.vpd_card_att_full.setVisibility(0);
                                                            this.vpd_card_att_full.setText(this.viewBean.getUiSpecialState());
                                                            break;
                                                        } else {
                                                            this.vpd_card_att_full.setVisibility(8);
                                                            break;
                                                        }
                                                    } else {
                                                        this.vpd_card_att_full.setVisibility(0);
                                                        this.vpd_card_att_full.setText(this.viewBean.getUiAttFull());
                                                        break;
                                                    }
                                                    break;
                                                case 43:
                                                    this.vpd_fai_user.setVisibility(0);
                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                    this.vpd_way.setVisibility(8);
                                                    this.vpd_verify.setVisibility(0);
                                                    this.vpd_face_st.setVisibility(4);
                                                    this.vpd_face_fa.setVisibility(4);
                                                    this.vpd_palm_st.setVisibility(4);
                                                    this.vpd_palm_fa.setVisibility(4);
                                                    this.vpd_card_st.setVisibility(4);
                                                    this.vpd_card_fa.setVisibility(4);
                                                    this.vpd_finger_st.setVisibility(4);
                                                    this.vpd_finger_fa.setVisibility(4);
                                                    this.vpd_password.setVisibility(4);
                                                    this.vpd_pin.setVisibility(4);
                                                    this.vpd_pin_fa.setVisibility(4);
                                                    this.vpd_su.setVisibility(4);
                                                    setFailInfo(this.viewBean, uiType);
                                                    break;
                                                default:
                                                    switch (uiType) {
                                                        case 50:
                                                            this.vpd_fai_user.setVisibility(4);
                                                            this.vpd_key_board_1v1.setVisibility(8);
                                                            this.vpd_way.setVisibility(8);
                                                            this.vpd_verify.setVisibility(0);
                                                            this.vpd_face_st.setVisibility(0);
                                                            this.vpd_face_fa.setVisibility(4);
                                                            this.vpd_palm_st.setVisibility(4);
                                                            this.vpd_palm_fa.setVisibility(4);
                                                            this.vpd_card_st.setVisibility(4);
                                                            this.vpd_card_fa.setVisibility(4);
                                                            this.vpd_finger_st.setVisibility(4);
                                                            this.vpd_finger_fa.setVisibility(4);
                                                            this.vpd_password.setVisibility(4);
                                                            this.vpd_pin.setVisibility(4);
                                                            this.vpd_pin_fa.setVisibility(4);
                                                            this.vpd_su.setVisibility(4);
                                                            setVerifySucIcon(this.typeBeans, this.bean);
                                                            this.mContext.sendBroadcast(new Intent(ZkFaceLauncher.ACTION_ENTER_WORK_MODE));
                                                            LogUtils.d(LogUtils.TAG_VERIFY, "upDateUi: FaceStart<%s>", 50);
                                                            break;
                                                        case 51:
                                                            LogUtils.d(LogUtils.TAG_VERIFY, "upDateUi: FaceSuccess<%s>", 51);
                                                            this.vpd_verify.setVisibility(0);
                                                            this.vpd_su.setVisibility(0);
                                                            this.vpd_fai_user.setVisibility(4);
                                                            this.vpd_key_board_1v1.setVisibility(8);
                                                            this.vpd_way.setVisibility(8);
                                                            this.vpd_face_st.setVisibility(4);
                                                            this.vpd_face_fa.setVisibility(4);
                                                            this.vpd_palm_st.setVisibility(4);
                                                            this.vpd_palm_fa.setVisibility(4);
                                                            this.vpd_card_st.setVisibility(4);
                                                            this.vpd_card_fa.setVisibility(4);
                                                            this.vpd_finger_st.setVisibility(4);
                                                            this.vpd_finger_fa.setVisibility(4);
                                                            this.vpd_password.setVisibility(4);
                                                            this.vpd_pin.setVisibility(4);
                                                            this.vpd_pin_fa.setVisibility(4);
                                                            setSuccessInfo(this.viewBean, uiType);
                                                            break;
                                                        case 52:
                                                            LogUtils.d(LogUtils.TAG_VERIFY, "upDateUi: FaceFailed<%s>", 52);
                                                            this.vpd_verify.setVisibility(0);
                                                            this.vpd_face_fa.setVisibility(0);
                                                            this.vpd_palm_st.setVisibility(4);
                                                            this.vpd_palm_fa.setVisibility(4);
                                                            this.vpd_finger_fa.setVisibility(4);
                                                            this.vpd_fai_user.setVisibility(4);
                                                            this.vpd_key_board_1v1.setVisibility(8);
                                                            this.vpd_way.setVisibility(8);
                                                            this.vpd_face_st.setVisibility(4);
                                                            this.vpd_card_st.setVisibility(4);
                                                            this.vpd_card_fa.setVisibility(4);
                                                            this.vpd_finger_st.setVisibility(4);
                                                            this.vpd_password.setVisibility(4);
                                                            this.vpd_pin.setVisibility(4);
                                                            this.vpd_pin_fa.setVisibility(4);
                                                            this.vpd_su.setVisibility(4);
                                                            setVerifySucIcon(this.typeBeans, this.bean);
                                                            if (this.viewBean.getUiWayLogin() != null && !this.viewBean.getUiWayLogin().equals("")) {
                                                                this.vpd_face_fa_message.setText(this.viewBean.getUiWayLogin());
                                                            } else if (!TextUtils.isEmpty(this.viewBean.getFailMsg())) {
                                                                this.vpd_face_fa_message.setText(this.viewBean.getFailMsg());
                                                            }
                                                            if (this.viewBean.getUiAttFull() == null || this.viewBean.getUiAttFull().equals("")) {
                                                                if (this.viewBean.getUiSpecialState() != null && !this.viewBean.getUiSpecialState().equals("")) {
                                                                    this.vpd_face_att_full.setVisibility(0);
                                                                    this.vpd_face_att_full.setText(this.viewBean.getUiSpecialState());
                                                                    break;
                                                                } else {
                                                                    this.vpd_face_att_full.setVisibility(8);
                                                                    break;
                                                                }
                                                            } else {
                                                                this.vpd_face_att_full.setVisibility(0);
                                                                this.vpd_face_att_full.setText(this.viewBean.getUiAttFull());
                                                                break;
                                                            }
                                                            break;
                                                        case 53:
                                                            LogUtils.d(LogUtils.TAG_VERIFY, "upDateUi: FaceInvalid<%s>", 52);
                                                            this.vpd_fai_user.setVisibility(0);
                                                            this.vpd_verify.setVisibility(0);
                                                            this.tv_fail_msg.setVisibility(0);
                                                            this.vpd_key_board_1v1.setVisibility(8);
                                                            this.vpd_way.setVisibility(8);
                                                            this.vpd_face_st.setVisibility(4);
                                                            this.vpd_face_fa.setVisibility(4);
                                                            this.vpd_palm_st.setVisibility(4);
                                                            this.vpd_palm_fa.setVisibility(4);
                                                            this.vpd_card_st.setVisibility(4);
                                                            this.vpd_card_fa.setVisibility(4);
                                                            this.vpd_finger_st.setVisibility(4);
                                                            this.vpd_finger_fa.setVisibility(4);
                                                            this.vpd_password.setVisibility(4);
                                                            this.vpd_pin.setVisibility(4);
                                                            this.vpd_pin_fa.setVisibility(4);
                                                            this.vpd_su.setVisibility(4);
                                                            setFailInfo(this.viewBean, uiType);
                                                            break;
                                                        default:
                                                            switch (uiType) {
                                                                case 60:
                                                                case 62:
                                                                    this.vpd_fai_user.setVisibility(4);
                                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                                    this.vpd_way.setVisibility(8);
                                                                    this.vpd_verify.setVisibility(0);
                                                                    this.vpd_face_st.setVisibility(4);
                                                                    this.vpd_face_fa.setVisibility(4);
                                                                    this.vpd_palm_st.setVisibility(4);
                                                                    this.vpd_palm_fa.setVisibility(4);
                                                                    this.vpd_card_st.setVisibility(4);
                                                                    this.vpd_card_fa.setVisibility(4);
                                                                    this.vpd_finger_st.setVisibility(4);
                                                                    this.vpd_finger_fa.setVisibility(4);
                                                                    this.vpd_password.setVisibility(4);
                                                                    this.vpd_pin.setVisibility(4);
                                                                    this.vpd_pin_fa.setVisibility(4);
                                                                    this.vpd_su.setVisibility(0);
                                                                    setSuccessInfo(this.viewBean, uiType);
                                                                    break;
                                                                case 61:
                                                                    this.vpd_fai_user.setVisibility(4);
                                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                                    this.vpd_way.setVisibility(8);
                                                                    this.vpd_verify.setVisibility(0);
                                                                    this.vpd_face_st.setVisibility(4);
                                                                    this.vpd_face_fa.setVisibility(4);
                                                                    this.vpd_palm_st.setVisibility(4);
                                                                    this.vpd_palm_fa.setVisibility(4);
                                                                    this.vpd_card_st.setVisibility(4);
                                                                    this.vpd_card_fa.setVisibility(0);
                                                                    this.vpd_finger_st.setVisibility(4);
                                                                    this.vpd_finger_fa.setVisibility(4);
                                                                    this.vpd_password.setVisibility(4);
                                                                    this.vpd_pin.setVisibility(4);
                                                                    this.vpd_pin_fa.setVisibility(4);
                                                                    this.vpd_su.setVisibility(4);
                                                                    setVerifySucIcon(this.typeBeans, this.bean);
                                                                    if (this.viewBean.getUiWayLogin() != null && !this.viewBean.getUiWayLogin().equals("")) {
                                                                        this.vpd_card_fa_message.setText(this.viewBean.getUiWayLogin());
                                                                    } else if (!TextUtils.isEmpty(this.viewBean.getFailMsg())) {
                                                                        this.vpd_card_fa_message.setText(this.viewBean.getFailMsg());
                                                                    }
                                                                    if (this.viewBean.getUiAttFull() == null || this.viewBean.getUiAttFull().equals("")) {
                                                                        if (this.viewBean.getUiSpecialState() != null && !this.viewBean.getUiSpecialState().equals("")) {
                                                                            this.vpd_card_att_full.setVisibility(0);
                                                                            this.vpd_card_att_full.setText(this.viewBean.getUiSpecialState());
                                                                            break;
                                                                        } else {
                                                                            this.vpd_card_att_full.setVisibility(8);
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        this.vpd_card_att_full.setVisibility(0);
                                                                        this.vpd_card_att_full.setText(this.viewBean.getUiAttFull());
                                                                        break;
                                                                    }
                                                                    break;
                                                                case 63:
                                                                    this.vpd_fai_user.setVisibility(4);
                                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                                    this.vpd_way.setVisibility(8);
                                                                    this.vpd_verify.setVisibility(0);
                                                                    this.vpd_face_st.setVisibility(4);
                                                                    this.vpd_face_fa.setVisibility(4);
                                                                    this.vpd_palm_st.setVisibility(4);
                                                                    this.vpd_palm_fa.setVisibility(4);
                                                                    this.vpd_card_st.setVisibility(4);
                                                                    this.vpd_card_fa.setVisibility(4);
                                                                    this.vpd_finger_st.setVisibility(4);
                                                                    this.vpd_finger_fa.setVisibility(4);
                                                                    this.vpd_password.setVisibility(4);
                                                                    this.vpd_pin.setVisibility(4);
                                                                    this.vpd_pin_fa.setVisibility(0);
                                                                    this.vpd_su.setVisibility(4);
                                                                    setVerifySucIcon(this.typeBeans, this.bean);
                                                                    if (this.viewBean.getUiWayLogin() != null && !this.viewBean.getUiWayLogin().equals("")) {
                                                                        this.vpd_pin_fa_message.setText(this.viewBean.getUiWayLogin());
                                                                    } else if (!TextUtils.isEmpty(this.viewBean.getFailMsg())) {
                                                                        this.vpd_pin_fa_message.setText(this.viewBean.getFailMsg());
                                                                    }
                                                                    if (this.viewBean.getUiAttFull() == null || this.viewBean.getUiAttFull().equals("")) {
                                                                        if (this.viewBean.getUiSpecialState() != null && !this.viewBean.getUiSpecialState().equals("")) {
                                                                            this.vpd_pin_att_full.setVisibility(0);
                                                                            this.vpd_pin_att_full.setText(this.viewBean.getUiSpecialState());
                                                                            break;
                                                                        } else {
                                                                            this.vpd_pin_att_full.setVisibility(4);
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        this.vpd_pin_att_full.setVisibility(0);
                                                                        this.vpd_pin_att_full.setText(this.viewBean.getUiAttFull());
                                                                        break;
                                                                    }
                                                                    break;
                                                                default:
                                                                    switch (uiType) {
                                                                        case 71:
                                                                            this.vpd_verify.setVisibility(0);
                                                                            this.vpd_password.setVisibility(4);
                                                                            this.vpd_pin.setVisibility(4);
                                                                            this.vpd_pin_fa.setVisibility(4);
                                                                            this.vpd_su.setVisibility(0);
                                                                            this.vpd_su_user_name.setVisibility(8);
                                                                            this.vpd_su_ll_status.setVisibility(8);
                                                                            this.vpd_su_user_pin.setVisibility(8);
                                                                            this.vpd_su_att_record_full.setVisibility(8);
                                                                            this.vpd_su_message.setVisibility(8);
                                                                            this.vpd_su_repeat.setVisibility(0);
                                                                            this.vpd_su_repeat.setText(this.viewBean.getFailMsg());
                                                                            this.vpd_su_icon.setImageResource(R.mipmap.ic_2_password_0002);
                                                                            this.vpd_su_ergent_password_event.setText(this.viewBean.getUiSignInTime());
                                                                            break;
                                                                        case 72:
                                                                            this.vpd_super_password_edit.setText("");
                                                                            this.vpd_pin_hint.setText(this.viewBean.getFailMsg());
                                                                            this.vpd_super_password_edit.requestFocus();
                                                                            hideSoftKeyboardAndUpdateRTL(false, this.vpd_super_password_edit);
                                                                            break;
                                                                        case 73:
                                                                            this.vpd_super_password_edit.setText("");
                                                                            this.iv_super_password.setVisibility(8);
                                                                            this.iv_pin_change.setVisibility(0);
                                                                            this.vpd_pin_edit.setVisibility(8);
                                                                            this.vpd_pin_next.setVisibility(8);
                                                                            this.vpd_super_password_edit.setVisibility(0);
                                                                            this.vpd_super_password_next.setVisibility(0);
                                                                            this.vpd_pin_hint.setText(this.viewBean.getFailMsg());
                                                                            this.vpd_super_password_edit.requestFocus();
                                                                            hideSoftKeyboardAndUpdateRTL(false, this.vpd_super_password_edit);
                                                                            break;
                                                                        default:
                                                                            switch (uiType) {
                                                                                case 80:
                                                                                    this.vpd_fai_user.setVisibility(4);
                                                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                                                    this.vpd_way.setVisibility(8);
                                                                                    this.vpd_verify.setVisibility(0);
                                                                                    this.vpd_face_st.setVisibility(4);
                                                                                    this.vpd_face_fa.setVisibility(4);
                                                                                    this.vpd_palm_st.setVisibility(0);
                                                                                    this.vpd_palm_fa.setVisibility(4);
                                                                                    this.vpd_card_st.setVisibility(4);
                                                                                    this.vpd_card_fa.setVisibility(4);
                                                                                    this.vpd_finger_st.setVisibility(4);
                                                                                    this.vpd_finger_fa.setVisibility(4);
                                                                                    this.vpd_password.setVisibility(4);
                                                                                    this.vpd_pin.setVisibility(4);
                                                                                    this.vpd_pin_fa.setVisibility(4);
                                                                                    this.vpd_su.setVisibility(4);
                                                                                    setVerifySucIcon(this.typeBeans, this.bean);
                                                                                    LogUtils.d(LogUtils.TAG_VERIFY, "upDateUi: PalmStart<%s>", 80);
                                                                                    break;
                                                                                case 81:
                                                                                    LogUtils.d(LogUtils.TAG_VERIFY, "upDateUi: PalmSuccess<%s>", 81);
                                                                                    this.vpd_verify.setVisibility(0);
                                                                                    this.vpd_su.setVisibility(0);
                                                                                    this.vpd_fai_user.setVisibility(4);
                                                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                                                    this.vpd_way.setVisibility(8);
                                                                                    this.vpd_face_st.setVisibility(4);
                                                                                    this.vpd_face_fa.setVisibility(4);
                                                                                    this.vpd_palm_st.setVisibility(4);
                                                                                    this.vpd_palm_fa.setVisibility(4);
                                                                                    this.vpd_card_st.setVisibility(4);
                                                                                    this.vpd_card_fa.setVisibility(4);
                                                                                    this.vpd_finger_st.setVisibility(4);
                                                                                    this.vpd_finger_fa.setVisibility(4);
                                                                                    this.vpd_password.setVisibility(4);
                                                                                    this.vpd_pin.setVisibility(4);
                                                                                    this.vpd_pin_fa.setVisibility(4);
                                                                                    setSuccessInfo(this.viewBean, uiType);
                                                                                    break;
                                                                                case 82:
                                                                                    LogUtils.d(LogUtils.TAG_VERIFY, "upDateUi: PalmFailed<%s>", 82);
                                                                                    this.vpd_verify.setVisibility(0);
                                                                                    this.vpd_face_fa.setVisibility(4);
                                                                                    this.vpd_palm_st.setVisibility(4);
                                                                                    this.vpd_palm_fa.setVisibility(0);
                                                                                    this.vpd_finger_fa.setVisibility(4);
                                                                                    this.vpd_fai_user.setVisibility(4);
                                                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                                                    this.vpd_way.setVisibility(8);
                                                                                    this.vpd_face_st.setVisibility(4);
                                                                                    this.vpd_card_st.setVisibility(4);
                                                                                    this.vpd_card_fa.setVisibility(4);
                                                                                    this.vpd_finger_st.setVisibility(4);
                                                                                    this.vpd_password.setVisibility(4);
                                                                                    this.vpd_pin.setVisibility(4);
                                                                                    this.vpd_pin_fa.setVisibility(4);
                                                                                    this.vpd_su.setVisibility(4);
                                                                                    setVerifySucIcon(this.typeBeans, this.bean);
                                                                                    if (this.viewBean.getUiWayLogin() != null && !this.viewBean.getUiWayLogin().equals("")) {
                                                                                        this.vpd_palm_fa_message.setText(this.viewBean.getUiWayLogin());
                                                                                    } else if (!TextUtils.isEmpty(this.viewBean.getFailMsg())) {
                                                                                        this.vpd_palm_fa_message.setText(this.viewBean.getFailMsg());
                                                                                    }
                                                                                    if (this.viewBean.getUiAttFull() == null || this.viewBean.getUiAttFull().equals("")) {
                                                                                        if (this.viewBean.getUiSpecialState() != null && !this.viewBean.getUiSpecialState().equals("")) {
                                                                                            this.vpd_palm_att_full.setVisibility(0);
                                                                                            this.vpd_palm_att_full.setText(this.viewBean.getUiSpecialState());
                                                                                            break;
                                                                                        } else {
                                                                                            this.vpd_palm_att_full.setVisibility(8);
                                                                                            break;
                                                                                        }
                                                                                    } else {
                                                                                        this.vpd_palm_att_full.setVisibility(0);
                                                                                        this.vpd_palm_att_full.setText(this.viewBean.getUiAttFull());
                                                                                        break;
                                                                                    }
                                                                                    break;
                                                                                case 83:
                                                                                    LogUtils.d(LogUtils.TAG_VERIFY, "upDateUi: FaceInvalid<%s>", 82);
                                                                                    this.vpd_fai_user.setVisibility(0);
                                                                                    this.vpd_verify.setVisibility(0);
                                                                                    this.tv_fail_msg.setVisibility(0);
                                                                                    this.vpd_key_board_1v1.setVisibility(8);
                                                                                    this.vpd_way.setVisibility(8);
                                                                                    this.vpd_face_st.setVisibility(4);
                                                                                    this.vpd_face_fa.setVisibility(4);
                                                                                    this.vpd_palm_st.setVisibility(4);
                                                                                    this.vpd_palm_fa.setVisibility(4);
                                                                                    this.vpd_card_st.setVisibility(4);
                                                                                    this.vpd_card_fa.setVisibility(4);
                                                                                    this.vpd_finger_st.setVisibility(4);
                                                                                    this.vpd_finger_fa.setVisibility(4);
                                                                                    this.vpd_password.setVisibility(4);
                                                                                    this.vpd_pin.setVisibility(4);
                                                                                    this.vpd_pin_fa.setVisibility(4);
                                                                                    this.vpd_su.setVisibility(4);
                                                                                    setFailInfo(this.viewBean, uiType);
                                                                                    break;
                                                                            }
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
                } else {
                    this.vpd_verify.setVisibility(4);
                    this.vpd_way.setVisibility(4);
                    this.vpd_key_board_1v1.setVisibility(4);
                    this.vpd_multi_verify_wait.setVisibility(0);
                }
            } else if (this.bean != null) {
                this.singleThreadExecutor.submit(this.setVerifyTypeIconVisibleTask);
            }
            ZKVerProcessPar.VIEW_BEAN_LIST.remove(this.viewBean);
        }
    }

    /* access modifiers changed from: private */
    public void setSuperManegerVerifyIcon() {
        if (1 == this.fingerFunOn && 1 == this.hasFingerModule) {
            this.vpd_way_fingerprint.setVisibility(0);
        } else {
            this.vpd_way_fingerprint.setVisibility(8);
        }
        if (1 == this.rfCardOn) {
            this.vpd_way_card.setVisibility(0);
        } else {
            this.vpd_way_card.setVisibility(8);
        }
        if (1 == this.faceFunOn) {
            this.vpd_way_face.setVisibility(0);
        } else {
            this.vpd_way_face.setVisibility(8);
        }
        if (1 == this.pvFunOn) {
            this.vpd_way_palm.setVisibility(0);
        } else {
            this.vpd_way_palm.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void getNeedData() {
        DataManager instance2 = DBManager.getInstance();
        this.rfCardOn = instance2.getIntOption("~RFCardOn", 0);
        this.fingerFunOn = instance2.getIntOption("FingerFunOn", 1);
        this.hasFingerModule = instance2.getIntOption("hasFingerModule", 1);
        this.faceFunOn = instance2.getIntOption("FaceFunOn", 1);
        this.pvFunOn = instance2.getIntOption("PvFunOn", 1);
        this.urgentPassword = instance2.getStrOption("Door1SupperPassWord", "");
        this.deviceSN = instance2.getStrOption("~SerialNumber", "");
    }

    /* access modifiers changed from: private */
    public void setVerifyTypeIconVisible(UserInfo userInfo, ZKVerViewBean zKVerViewBean) {
        int i;
        if (ZKLauncher.sAccessRuleType == 1) {
            i = this.zkVerOption.getVerifyType(this.mContext);
        } else {
            int verify_Type = userInfo.getVerify_Type();
            AccGroup accGroup = null;
            try {
                if (userInfo.getAcc_Group_ID() > 0) {
                    accGroup = (AccGroup) new AccGroup().queryForId(Long.valueOf((long) userInfo.getAcc_Group_ID()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (verify_Type != 100 || accGroup == null) {
                i = verify_Type;
            } else {
                i = accGroup.getVerification();
            }
        }
        if (i == 0) {
            this.vpd_key_board_1v1_password.setVisibility(0);
            this.vpd_key_board_1v1_fingerprint.setVisibility(0);
            this.vpd_key_board_1v1_card.setVisibility(0);
            this.vpd_key_board_1v1_face.setVisibility(0);
            this.vpd_key_board_1v1_palm.setVisibility(0);
        } else if (i == 1) {
            this.vpd_key_board_1v1_password.setVisibility(8);
            this.vpd_key_board_1v1_fingerprint.setVisibility(0);
            this.vpd_key_board_1v1_card.setVisibility(8);
            this.vpd_key_board_1v1_face.setVisibility(8);
            this.vpd_key_board_1v1_palm.setVisibility(8);
        } else if (i != 25) {
            switch (i) {
                case 3:
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(8);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 4:
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(8);
                    this.vpd_key_board_1v1_card.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 5:
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 6:
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 7:
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(8);
                    this.vpd_key_board_1v1_card.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 8:
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 9:
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 10:
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 11:
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(8);
                    this.vpd_key_board_1v1_card.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 12:
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 13:
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 14:
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_face.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 15:
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(8);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_face.setVisibility(0);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 16:
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(0);
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 17:
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 18:
                    this.vpd_key_board_1v1_face.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(0);
                    this.vpd_key_board_1v1_fingerprint.setVisibility(8);
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 19:
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(0);
                    this.vpd_key_board_1v1_password.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
                case 20:
                    this.vpd_key_board_1v1_fingerprint.setVisibility(0);
                    this.vpd_key_board_1v1_face.setVisibility(0);
                    this.vpd_key_board_1v1_password.setVisibility(0);
                    this.vpd_key_board_1v1_card.setVisibility(8);
                    this.vpd_key_board_1v1_palm.setVisibility(8);
                    break;
            }
        } else {
            this.vpd_key_board_1v1_fingerprint.setVisibility(8);
            this.vpd_key_board_1v1_face.setVisibility(8);
            this.vpd_key_board_1v1_password.setVisibility(8);
            this.vpd_key_board_1v1_card.setVisibility(8);
            this.vpd_key_board_1v1_palm.setVisibility(0);
        }
        if (!zKVerViewBean.getRegister().get(4).booleanValue()) {
            this.vpd_key_board_1v1_palm.setVisibility(8);
        }
        if (!zKVerViewBean.getRegister().get(0).booleanValue()) {
            this.vpd_key_board_1v1_password.setVisibility(8);
        }
        if (!zKVerViewBean.getRegister().get(1).booleanValue() || this.fingerFunOn == 0 || this.hasFingerModule == 0) {
            this.vpd_key_board_1v1_fingerprint.setVisibility(8);
        }
        if (!zKVerViewBean.getRegister().get(2).booleanValue() || this.rfCardOn == 0) {
            this.vpd_key_board_1v1_card.setVisibility(8);
        }
        if (!zKVerViewBean.getRegister().get(3).booleanValue() || this.faceFunOn == 0) {
            this.vpd_key_board_1v1_face.setVisibility(8);
        }
        if (this.vpd_key_board_1v1_face.getVisibility() == 8 && this.vpd_key_board_1v1_card.getVisibility() == 8 && this.vpd_key_board_1v1_fingerprint.getVisibility() == 8 && this.vpd_key_board_1v1_password.getVisibility() == 8 && this.vpd_key_board_1v1_palm.getVisibility() == 8) {
            Toast.makeText(this.mContext, R.string.finger_module_error, 0).show();
            clearDatas();
            FileLogUtils.writeTouchLog("clearDatas: ZKVerProcessDialog setVerifyTypeIconVisible");
            dismiss();
        }
    }
}
