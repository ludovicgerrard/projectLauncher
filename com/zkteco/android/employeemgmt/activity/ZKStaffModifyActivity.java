package com.zkteco.android.employeemgmt.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.fragment.app.FragmentTransaction;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.utils.BitmapUtils;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.employeemgmt.fragment.ZKStaffModifyStep1Fragment;
import com.zkteco.android.employeemgmt.util.HasFaceUtils;
import com.zkteco.android.employeemgmt.util.VerifyTypeUtils;
import com.zkteco.android.employeemgmt.util.ZkPalmUtils;
import com.zkteco.android.zkcore.view.ZKToolbar;

public class ZKStaffModifyActivity extends ZKStaffBaseActivity implements View.OnClickListener {
    private static final String CARD_ICON = "card_icon";
    private static final String FACE_ICON = "face_icon";
    private static final String FINGER_ICON = "finger_icon";
    private static final String PALM_ICON = "palm_icon";
    private static final String PASSWORD_ICON = "password_icon";
    private boolean hasFace = false;
    private ImageView iv_card;
    private ImageView iv_face;
    private ImageView iv_fingerprint;
    private ImageView iv_palm;
    private ImageView iv_password;
    private LinearLayout linearTop;
    private ImageView mStaffIcon;
    private TemplateManager templateManager;
    private UserInfo userInfo;
    private String user_pin;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.templateManager = new TemplateManager(this);
        setContentView((int) R.layout.activity_staff_modify);
        VerifyTypeUtils.init(this);
        initToolBar();
        if (init()) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.fre_home, new ZKStaffModifyStep1Fragment());
            beginTransaction.commit();
        }
    }

    /* access modifiers changed from: protected */
    public void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.staffModifyToolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffModifyActivity.this.lambda$initToolBar$0$ZKStaffModifyActivity(view);
            }
        }, getString(R.string.zk_staff_info));
        zKToolbar.setRightView();
    }

    public /* synthetic */ void lambda$initToolBar$0$ZKStaffModifyActivity(View view) {
        finish();
    }

    private boolean init() {
        try {
            this.userInfo = (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", getIntent().getStringExtra("userpin")).queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 == null) {
            showToast(this.mContext, (int) R.string.error_return);
            finish();
            return false;
        }
        this.user_pin = userInfo2.getUser_PIN();
        String password = this.userInfo.getPassword();
        String main_Card = this.userInfo.getMain_Card();
        ImageView imageView = (ImageView) findViewById(R.id.civ_staff_icon);
        this.mStaffIcon = imageView;
        imageView.setOnClickListener(this);
        this.hasFace = HasFaceUtils.isHasFace(this.user_pin);
        this.iv_face = (ImageView) findViewById(R.id.iv_face);
        setIcon(FACE_ICON, this.hasFace);
        this.iv_palm = (ImageView) findViewById(R.id.iv_palm);
        if (VerifyTypeUtils.enablePv()) {
            this.iv_palm.setVisibility(0);
            setIcon(PALM_ICON, ZkPalmUtils.isHasPv(this.user_pin));
        } else {
            this.iv_palm.setVisibility(8);
        }
        this.iv_fingerprint = (ImageView) findViewById(R.id.iv_fingerprint);
        setIcon(FINGER_ICON, !this.templateManager.getFingerTemplateForUserPin(String.valueOf(this.userInfo.getID())).isEmpty());
        this.iv_card = (ImageView) findViewById(R.id.iv_card);
        setIcon(CARD_ICON, main_Card != null && !main_Card.equals("0") && !main_Card.equals(""));
        this.iv_password = (ImageView) findViewById(R.id.iv_password);
        setIcon(PASSWORD_ICON, !TextUtils.isEmpty(password));
        if (getResources().getConfiguration().orientation == 1) {
            this.linearTop = (LinearLayout) findViewById(R.id.linear_top);
        }
        if (VerifyTypeUtils.enableHaveCard()) {
            this.iv_card.setVisibility(0);
        }
        if (!VerifyTypeUtils.enableFace()) {
            this.iv_face.setVisibility(8);
        }
        if (!VerifyTypeUtils.enableFinger()) {
            this.iv_fingerprint.setVisibility(8);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        init();
        try {
            String str = this.user_pin;
            if (!(str == null || BitmapUtils.getUserPhoto(str) == null || BitmapUtils.getUserPhoto(this.user_pin).length == 0)) {
                if (!"0".equals(ZKLauncher.sPhotoFunOn)) {
                    byte[] userPhoto = BitmapUtils.getUserPhoto(this.user_pin);
                    this.mStaffIcon.setImageBitmap(BitmapFactory.decodeByteArray(userPhoto, 0, userPhoto.length));
                    return;
                }
            }
            this.mStaffIcon.setImageResource(R.mipmap.ic_pic_null);
        } catch (Exception unused) {
            this.mStaffIcon.setImageResource(R.mipmap.ic_pic_null);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.civ_staff_icon && "1".equals(ZKLauncher.sPhotoFunOn)) {
            Intent intent = new Intent(this, ZKStaffIconCollectionActivity.class);
            if (this.user_pin != null) {
                intent.putExtra("operate", "modify");
                intent.putExtra("userpin", this.user_pin);
                startActivity(intent);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        this.hasFace = HasFaceUtils.isHasFace(this.user_pin);
        try {
            this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(this.userInfo.getID()));
            setIcon(FACE_ICON, this.hasFace);
            boolean z = true;
            setIcon(FINGER_ICON, !this.templateManager.getFingerTemplateForUserPin(String.valueOf(this.userInfo.getID())).isEmpty());
            setIcon(CARD_ICON, this.userInfo.getMain_Card() != null && !this.userInfo.getMain_Card().equals("") && !this.userInfo.getMain_Card().equals("0"));
            if (TextUtils.isEmpty(this.userInfo.getPassword())) {
                z = false;
            }
            setIcon(PASSWORD_ICON, z);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setIcon(String str, boolean z) {
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1771794149:
                if (str.equals(FACE_ICON)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1391907953:
                if (str.equals(FINGER_ICON)) {
                    c2 = 1;
                    break;
                }
                break;
            case -245373880:
                if (str.equals(CARD_ICON)) {
                    c2 = 2;
                    break;
                }
                break;
            case 409314205:
                if (str.equals(PASSWORD_ICON)) {
                    c2 = 3;
                    break;
                }
                break;
            case 1255129446:
                if (str.equals(PALM_ICON)) {
                    c2 = 4;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                if (z) {
                    this.iv_face.setImageResource(R.mipmap.ic_face_show);
                    return;
                } else {
                    this.iv_face.setImageResource(R.mipmap.ic_face);
                    return;
                }
            case 1:
                if (z) {
                    this.iv_fingerprint.setImageResource(R.mipmap.ic_fingerprint_show);
                    return;
                } else {
                    this.iv_fingerprint.setImageResource(R.mipmap.ic_fingerprint);
                    return;
                }
            case 2:
                if (z) {
                    this.iv_card.setImageResource(R.mipmap.ic_card_show);
                    return;
                } else {
                    this.iv_card.setImageResource(R.mipmap.ic_card);
                    return;
                }
            case 3:
                if (z) {
                    this.iv_password.setImageResource(R.mipmap.ic_password_show);
                    return;
                } else {
                    this.iv_password.setImageResource(R.mipmap.ic_password);
                    return;
                }
            case 4:
                if (z) {
                    this.iv_palm.setImageResource(R.mipmap.palml);
                    return;
                } else {
                    this.iv_palm.setImageResource(R.mipmap.palmg);
                    return;
                }
            default:
                return;
        }
    }

    public void setUserInfo(UserInfo userInfo2) {
        this.userInfo = userInfo2;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setCardSmallIcon(boolean z) {
        setIcon(CARD_ICON, z);
    }

    public void setPDSmallIcon(boolean z) {
        setIcon(PASSWORD_ICON, z);
    }

    public void setLinearTopVIS(boolean z) {
        if (z) {
            this.linearTop.setVisibility(0);
        } else {
            this.linearTop.setVisibility(8);
        }
    }
}
