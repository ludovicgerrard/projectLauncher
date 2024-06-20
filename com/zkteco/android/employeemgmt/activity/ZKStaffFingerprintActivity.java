package com.zkteco.android.employeemgmt.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import com.zktechnology.android.helper.FingerprintServiceHelper;
import com.zktechnology.android.helper.McuServiceHelper;
import com.zktechnology.android.helper.OnFingerprintScanListener;
import com.zktechnology.android.helper.OnMcuReadListener;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.receiver.OpLogReceiver;
import com.zktechnology.android.rs485.RS485Manager;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.FpTemplate10;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.ZKStaffFingerprintActivity;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.employeemgmt.common.Constants;
import com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean;
import com.zkteco.android.employeemgmt.util.HasFaceUtils;
import com.zkteco.android.employeemgmt.widget.CustomFingerZoneView;
import com.zkteco.android.employeemgmt.widget.ZKProgressBar;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.view.alert.ZKConfirmDialog;
import com.zkteco.edk.finger.lib.ZkFingerprintManager;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZKStaffFingerprintActivity extends ZKStaffBaseActivity implements View.OnClickListener {
    /* access modifiers changed from: private */
    public static int horOrVer = 1;
    private static final int leftHand = 1;
    private static final int rightHand = 2;
    private float Volume;
    /* access modifiers changed from: private */
    public int actionValue;
    private AssetFileDescriptor afd;
    /* access modifiers changed from: private */
    public int count = 3;
    /* access modifiers changed from: private */
    public int currentAllFingerCount;
    /* access modifiers changed from: private */
    public DataManager dataManager;
    /* access modifiers changed from: private */
    public ZKConfirmDialog dialog;
    boolean finger0;
    boolean finger1;
    boolean finger2;
    boolean finger3;
    boolean finger4;
    boolean finger5;
    boolean finger6;
    boolean finger7;
    boolean finger8;
    boolean finger9;
    boolean fingerState;
    Runnable fingerTask = new Runnable() {
        public void run() {
            if (ZKStaffFingerprintActivity.this.count == 3) {
                try {
                    ArrayList unused = ZKStaffFingerprintActivity.this.listFingerPrint = new ArrayList();
                    String[] strArr = new String[1];
                    int identify = ZkFingerprintManager.getInstance().identify(ZKStaffFingerprintActivity.this.temp, Integer.parseInt(ZKStaffFingerprintActivity.this.mThreshold), strArr);
                    String str = strArr[0];
                    if (identify == 0) {
                        if (TextUtils.isEmpty(str)) {
                            ZKStaffFingerprintActivity.this.listFingerPrint.add(ZKStaffFingerprintActivity.this.temp);
                            ZKStaffFingerprintActivity.access$610(ZKStaffFingerprintActivity.this);
                            ZKStaffFingerprintActivity.this.handler.sendEmptyMessage(1);
                            ZKStaffFingerprintActivity.this.mFingerZoneView.setChangeStatus(false);
                        }
                    }
                    ZKStaffFingerprintActivity.this.handler.sendEmptyMessage(0);
                    ZKStaffFingerprintActivity.this.mFingerZoneView.setChangeStatus(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (ZKStaffFingerprintActivity.this.count < 3 && ZKStaffFingerprintActivity.this.count > 0) {
                int parseInt = Integer.parseInt(ZKStaffFingerprintActivity.this.vThreshold);
                boolean z = ZkFingerprintManager.getInstance().verify(ZKStaffFingerprintActivity.this.temp, (byte[]) ZKStaffFingerprintActivity.this.listFingerPrint.get(0), parseInt) == 0;
                if (ZKStaffFingerprintActivity.this.count == 1) {
                    z = z && ZkFingerprintManager.getInstance().verify(ZKStaffFingerprintActivity.this.temp, (byte[]) ZKStaffFingerprintActivity.this.listFingerPrint.get(1), parseInt) == 0;
                }
                if (z) {
                    ZKStaffFingerprintActivity.this.listFingerPrint.add(ZKStaffFingerprintActivity.this.temp);
                    ZKStaffFingerprintActivity.access$610(ZKStaffFingerprintActivity.this);
                    ZKStaffFingerprintActivity.this.handler.sendEmptyMessage(2);
                } else {
                    ZKStaffFingerprintActivity.this.handler.sendEmptyMessage(3);
                }
            }
            if (ZKStaffFingerprintActivity.this.count == 0) {
                byte[] bArr = new byte[2048];
                if (ZKStaffFingerprintActivity.this.isCover) {
                    if (ZkFingerprintManager.getInstance().deleteTemplate(ZKStaffFingerprintActivity.this.userInfo.getID() + "_" + ZKStaffFingerprintActivity.this.numFingerNow) != 0) {
                        ZKStaffFingerprintActivity.this.handler.sendEmptyMessage(4);
                        return;
                    } else if (ZkFingerprintManager.getInstance().enroll(ZKStaffFingerprintActivity.this.userInfo.getID() + "_" + ZKStaffFingerprintActivity.this.numFingerNow, ZKStaffFingerprintActivity.this.listFingerPrint, bArr) == 0) {
                        ZKStaffFingerprintActivity.this.tempLateManager.updateFingerTemplate(ZKStaffFingerprintActivity.this.userInfo, ZKStaffFingerprintActivity.this.numFingerNow, bArr);
                        boolean unused2 = ZKStaffFingerprintActivity.this.isCover = false;
                        ZKStaffFingerprintActivity zKStaffFingerprintActivity = ZKStaffFingerprintActivity.this;
                        zKStaffFingerprintActivity.sendFingerHub(zKStaffFingerprintActivity.numFingerNow);
                        ZKStaffFingerprintActivity.this.sendChangeHub();
                    } else {
                        int unused3 = ZKStaffFingerprintActivity.this.count = 3;
                        ZKStaffFingerprintActivity.this.mFingerZoneView.setChangeStatus(true);
                        ZKStaffFingerprintActivity.this.handler.sendEmptyMessage(5);
                        return;
                    }
                } else if (ZkFingerprintManager.getInstance().enroll(ZKStaffFingerprintActivity.this.userInfo.getID() + "_" + ZKStaffFingerprintActivity.this.numFingerNow, ZKStaffFingerprintActivity.this.listFingerPrint, bArr) == 0) {
                    ZKStaffFingerprintActivity.this.tempLateManager.insertFingerTemplate(ZKStaffFingerprintActivity.this.userInfo, ZKStaffFingerprintActivity.this.numFingerNow, bArr, 0);
                    ZKStaffFingerprintActivity.this.sendUserInfoHub();
                    ZKStaffFingerprintActivity zKStaffFingerprintActivity2 = ZKStaffFingerprintActivity.this;
                    zKStaffFingerprintActivity2.sendFingerHub(zKStaffFingerprintActivity2.numFingerNow);
                    ZKStaffFingerprintActivity.this.sendChangeHub();
                    ZKStaffFingerprintActivity.access$1508(ZKStaffFingerprintActivity.this);
                } else {
                    ZKStaffFingerprintActivity.this.handler.sendEmptyMessage(6);
                    return;
                }
                ZKStaffFingerprintActivity.this.handler.sendEmptyMessage(7);
            }
        }
    };
    /* access modifiers changed from: private */
    public final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            int i;
            boolean z = true;
            switch (message.what) {
                case 0:
                    if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setVisibility(0);
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_promthadfinger));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.startAnimation(AnimationUtils.loadAnimation(ZKStaffFingerprintActivity.this, R.anim.anim_shake));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_F78254));
                        ZKStaffFingerprintActivity.this.ivCheckState.setBackgroundResource(R.mipmap.circle_e01);
                    } else {
                        ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_promtdiffinger));
                        ZKStaffFingerprintActivity.this.tvShowStep.startAnimation(AnimationUtils.loadAnimation(ZKStaffFingerprintActivity.this, R.anim.anim_shake));
                        ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_F78254));
                    }
                    ZKStaffFingerprintActivity.this.showStep(ZKStaffFingerprintActivity.horOrVer, 1, 2, ZKStaffFingerprintActivity.this.mProgressBar);
                    return;
                case 1:
                    if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setVisibility(0);
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_seconddown));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                    } else {
                        ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                    }
                    int access$600 = 3 - ZKStaffFingerprintActivity.this.count;
                    if (access$600 == 1) {
                        if (ZKStaffFingerprintActivity.horOrVer == 1) {
                            ZKStaffFingerprintActivity.this.ivCheckState.setBackgroundResource(R.mipmap.circle_s01);
                        }
                        ZKStaffFingerprintActivity.this.showStep(ZKStaffFingerprintActivity.horOrVer, 1, 1, ZKStaffFingerprintActivity.this.mProgressBar);
                        ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_seconddown));
                        return;
                    } else if (access$600 == 2 && ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.ivCheckState.setBackgroundResource(R.mipmap.circle_s02);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setVisibility(0);
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_thirddown));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                    } else {
                        ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_thirddown));
                        ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                    }
                    if (ZKStaffFingerprintActivity.this.count == 0) {
                        if (ZKStaffFingerprintActivity.this.getIntent().getBooleanExtra("remote", false)) {
                            String unused = ZKStaffFingerprintActivity.this.remoteEnrollResult = "0";
                        }
                        ZKStaffFingerprintActivity.this.ivCheckState.setBackgroundResource(0);
                        ZKStaffFingerprintActivity.this.showStep(ZKStaffFingerprintActivity.horOrVer, 4, 0, ZKStaffFingerprintActivity.this.mProgressBar);
                        return;
                    }
                    int access$6002 = 3 - ZKStaffFingerprintActivity.this.count;
                    if (access$6002 != 1) {
                        if (access$6002 == 2) {
                            if (ZKStaffFingerprintActivity.horOrVer == 1) {
                                ZKStaffFingerprintActivity.this.ivCheckState.setBackgroundResource(R.mipmap.circle_s02);
                            }
                            ZKStaffFingerprintActivity.this.showStep(ZKStaffFingerprintActivity.horOrVer, 2, 1, ZKStaffFingerprintActivity.this.mProgressBar);
                            if (ZKStaffFingerprintActivity.horOrVer == 2) {
                                ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_thirddown));
                                return;
                            }
                            return;
                        }
                        return;
                    } else if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.ivCheckState.setBackgroundResource(R.mipmap.circle_s01);
                        return;
                    } else {
                        return;
                    }
                case 3:
                    if (ZKStaffFingerprintActivity.this.count == 2) {
                        if (ZKStaffFingerprintActivity.horOrVer == 1) {
                            ZKStaffFingerprintActivity.this.ivCheckState.setBackgroundResource(R.mipmap.circle_e02);
                        } else {
                            ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_promtsamefinger));
                            ZKStaffFingerprintActivity.this.tvShowStep.startAnimation(AnimationUtils.loadAnimation(ZKStaffFingerprintActivity.this, R.anim.anim_shake));
                            ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_F78254));
                        }
                        ZKStaffFingerprintActivity.this.showStep(ZKStaffFingerprintActivity.horOrVer, 2, 2, ZKStaffFingerprintActivity.this.mProgressBar);
                    }
                    if (ZKStaffFingerprintActivity.this.count == 1) {
                        if (ZKStaffFingerprintActivity.horOrVer == 1) {
                            ZKStaffFingerprintActivity.this.ivCheckState.setBackgroundResource(R.mipmap.circle_e03);
                        } else {
                            ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_promtsamefinger));
                            ZKStaffFingerprintActivity.this.tvShowStep.startAnimation(AnimationUtils.loadAnimation(ZKStaffFingerprintActivity.this, R.anim.anim_shake));
                            ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_F78254));
                        }
                        ZKStaffFingerprintActivity.this.showStep(ZKStaffFingerprintActivity.horOrVer, 3, 2, ZKStaffFingerprintActivity.this.mProgressBar);
                    }
                    if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setVisibility(0);
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_promtsamefinger));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.startAnimation(AnimationUtils.loadAnimation(ZKStaffFingerprintActivity.this, R.anim.anim_shake));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_F78254));
                        return;
                    }
                    return;
                case 4:
                    ZKStaffFingerprintActivity.this.showPromtDialog(2);
                    int unused2 = ZKStaffFingerprintActivity.this.count = 3;
                    ZKStaffFingerprintActivity.this.mFingerZoneView.setChangeStatus(true);
                    if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setVisibility(0);
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                        return;
                    } else if (ZKStaffFingerprintActivity.this.isLRHand) {
                        if (!ZKStaffFingerprintActivity.this.finger5 || !ZKStaffFingerprintActivity.this.finger6 || !ZKStaffFingerprintActivity.this.finger7 || !ZKStaffFingerprintActivity.this.finger8 || !ZKStaffFingerprintActivity.this.finger9) {
                            ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                            ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                            return;
                        }
                        ZKStaffFingerprintActivity.this.tvShowStep.setText("");
                        return;
                    } else if (!ZKStaffFingerprintActivity.this.finger0 || !ZKStaffFingerprintActivity.this.finger1 || !ZKStaffFingerprintActivity.this.finger2 || !ZKStaffFingerprintActivity.this.finger3 || !ZKStaffFingerprintActivity.this.finger4) {
                        ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                        ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                        return;
                    } else {
                        ZKStaffFingerprintActivity.this.tvShowStep.setText("");
                        return;
                    }
                case 5:
                    if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setVisibility(0);
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                        return;
                    } else if (ZKStaffFingerprintActivity.this.isLRHand) {
                        if (!ZKStaffFingerprintActivity.this.finger5 || !ZKStaffFingerprintActivity.this.finger6 || !ZKStaffFingerprintActivity.this.finger7 || !ZKStaffFingerprintActivity.this.finger8 || !ZKStaffFingerprintActivity.this.finger9) {
                            ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                            ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                            return;
                        }
                        ZKStaffFingerprintActivity.this.tvShowStep.setText("");
                        return;
                    } else if (!ZKStaffFingerprintActivity.this.finger0 || !ZKStaffFingerprintActivity.this.finger1 || !ZKStaffFingerprintActivity.this.finger2 || !ZKStaffFingerprintActivity.this.finger3 || !ZKStaffFingerprintActivity.this.finger4) {
                        ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                        ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                        return;
                    } else {
                        return;
                    }
                case 6:
                    ZKStaffFingerprintActivity.this.showPromtDialog(3);
                    int unused3 = ZKStaffFingerprintActivity.this.count = 3;
                    ZKStaffFingerprintActivity.this.mFingerZoneView.setChangeStatus(true);
                    if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setVisibility(0);
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                        return;
                    } else if (ZKStaffFingerprintActivity.this.isLRHand) {
                        if (!ZKStaffFingerprintActivity.this.finger0 || !ZKStaffFingerprintActivity.this.finger1 || !ZKStaffFingerprintActivity.this.finger2 || !ZKStaffFingerprintActivity.this.finger3 || !ZKStaffFingerprintActivity.this.finger4 || !ZKStaffFingerprintActivity.this.finger5 || !ZKStaffFingerprintActivity.this.finger6 || !ZKStaffFingerprintActivity.this.finger7 || !ZKStaffFingerprintActivity.this.finger8 || !ZKStaffFingerprintActivity.this.finger9) {
                            ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                            ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                            return;
                        }
                        ZKStaffFingerprintActivity.this.tvShowStep.setText("");
                        return;
                    } else if (!ZKStaffFingerprintActivity.this.finger0 || !ZKStaffFingerprintActivity.this.finger1 || !ZKStaffFingerprintActivity.this.finger2 || !ZKStaffFingerprintActivity.this.finger3 || !ZKStaffFingerprintActivity.this.finger4 || !ZKStaffFingerprintActivity.this.finger5 || !ZKStaffFingerprintActivity.this.finger6 || !ZKStaffFingerprintActivity.this.finger7 || !ZKStaffFingerprintActivity.this.finger8 || !ZKStaffFingerprintActivity.this.finger9) {
                        ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                        ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                        return;
                    } else {
                        ZKStaffFingerprintActivity.this.tvShowStep.setText("");
                        return;
                    }
                case 7:
                    ZKStaffFingerprintActivity zKStaffFingerprintActivity = ZKStaffFingerprintActivity.this;
                    zKStaffFingerprintActivity.showToast((Context) zKStaffFingerprintActivity, zKStaffFingerprintActivity.getResources().getString(R.string.zk_staff_promtdifsuccess));
                    if (ZKStaffFingerprintActivity.this.mFingerZoneView != null) {
                        ZKStaffFingerprintActivity.this.mFingerZoneView.setClickFingerStatus(4, ZKStaffFingerprintActivity.this.numFingerNow, ZKStaffFingerprintActivity.this.numFingerNow);
                        ZKStaffFingerprintActivity.this.mFingerZoneView.invalidate();
                    }
                    int unused4 = ZKStaffFingerprintActivity.this.count = 3;
                    ZKStaffFingerprintActivity.this.mFingerZoneView.setChangeStatus(true);
                    if (ZKStaffFingerprintActivity.horOrVer == 1) {
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setVisibility(0);
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                        ZKStaffFingerprintActivity.this.tvPromptSameFinger.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                    } else if (ZKStaffFingerprintActivity.this.isLRHand) {
                        if (!ZKStaffFingerprintActivity.this.finger5 || !ZKStaffFingerprintActivity.this.finger6 || !ZKStaffFingerprintActivity.this.finger7 || !ZKStaffFingerprintActivity.this.finger8 || !ZKStaffFingerprintActivity.this.finger9) {
                            ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                            ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                        } else {
                            ZKStaffFingerprintActivity.this.tvShowStep.setText("");
                        }
                    } else if (!ZKStaffFingerprintActivity.this.finger0 || !ZKStaffFingerprintActivity.this.finger1 || !ZKStaffFingerprintActivity.this.finger2 || !ZKStaffFingerprintActivity.this.finger3 || !ZKStaffFingerprintActivity.this.finger4) {
                        ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                        ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                    }
                    if (ZKStaffFingerprintActivity.this.tempLateManager.getFingerCount() < ((long) ZKStaffFingerprintActivity.this.maxFingerCount)) {
                        if (((long) ZKStaffFingerprintActivity.this.currentAllFingerCount) != 10) {
                            z = false;
                        }
                        if (z || !TextUtils.isEmpty(ZKStaffFingerprintActivity.this.userPin)) {
                            String unused5 = ZKStaffFingerprintActivity.this.remoteEnrollResult = "0";
                            ZKStaffFingerprintActivity.this.finish();
                            boolean unused6 = ZKStaffFingerprintActivity.this.isShowDialog = false;
                        } else {
                            ZKStaffFingerprintActivity zKStaffFingerprintActivity2 = ZKStaffFingerprintActivity.this;
                            zKStaffFingerprintActivity2.showCompletedDialog(zKStaffFingerprintActivity2);
                        }
                    } else {
                        ZKStaffFingerprintActivity zKStaffFingerprintActivity3 = ZKStaffFingerprintActivity.this;
                        zKStaffFingerprintActivity3.showToast((Context) zKStaffFingerprintActivity3, zKStaffFingerprintActivity3.getResources().getString(R.string.zk_staff_fill_finger));
                        ZKStaffFingerprintActivity.this.finish();
                    }
                    if (ZKStaffFingerprintActivity.this.isLRHand) {
                        i = ZKStaffFingerprintActivity.this.rightHandFinger;
                    } else {
                        i = ZKStaffFingerprintActivity.this.leftHandFinger;
                    }
                    ZKStaffFingerprintActivity.this.sendBroadcast(new Intent(OpLogReceiver.ACTION_ADD_FINGER_PRINT).putExtra("OpWho", ZKStaffFingerprintActivity.this.userInfo.getUser_PIN()).putExtra("Value1", i).putExtra("Value2", ""));
                    return;
                default:
                    return;
            }
        }
    };
    private HubProtocolManager hubProtocolManager;
    /* access modifiers changed from: private */
    public boolean isBothNull;
    /* access modifiers changed from: private */
    public boolean isCover = false;
    private boolean isFirst = true;
    private boolean isHome = false;
    /* access modifiers changed from: private */
    public boolean isLRHand = true;
    /* access modifiers changed from: private */
    public boolean isShowDialog = false;
    /* access modifiers changed from: private */
    public ImageView ivCheckState;
    private ImageView ivLeftHand;
    private ImageView ivRightHand;
    /* access modifiers changed from: private */
    public int leftHandFinger = 0;
    /* access modifiers changed from: private */
    public ArrayList<byte[]> listFingerPrint;
    int mAccessRuleType;
    /* access modifiers changed from: private */
    public Disposable mDisposable;
    private final ExecutorService mFaceService = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public CustomFingerZoneView mFingerZoneView;
    private final OnFingerprintScanListener mListener = new OnFingerprintScanListener() {
        public void onFingerprintCapture(byte[] bArr, int i, int i2) {
        }

        public void onFingerprintExtract(byte[] bArr) {
            ZKStaffFingerprintActivity.this.registerFingerprint(bArr);
        }
    };
    private final OnMcuReadListener mOnMcuReadListener = new OnMcuReadListener() {
        public void onRs232Read(byte[] bArr) {
        }

        public void onWiegandRead(String str) {
        }

        public void onRs485Read(byte[] bArr) {
            Disposable unused = ZKStaffFingerprintActivity.this.mDisposable = Flowable.just(bArr).filter($$Lambda$ZKStaffFingerprintActivity$2$wVWwUjf64KUnriHa1NkbGGCJZLM.INSTANCE).map($$Lambda$ZKStaffFingerprintActivity$2$FARvzGQpTSeffVuGPgFUzALtDL4.INSTANCE).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() {
                public final void accept(Object obj) {
                    ZKStaffFingerprintActivity.AnonymousClass2.this.lambda$onRs485Read$2$ZKStaffFingerprintActivity$2((byte[]) obj);
                }
            });
        }

        static /* synthetic */ boolean lambda$onRs485Read$0(byte[] bArr) throws Exception {
            return bArr != null && bArr.length > 0;
        }

        static /* synthetic */ byte[] lambda$onRs485Read$1(byte[] bArr) throws Exception {
            if (bArr[6] != 1) {
                return null;
            }
            byte[] bArr2 = new byte[(bArr.length - 11)];
            System.arraycopy(bArr, 9, bArr2, 0, bArr.length - 11);
            return bArr2;
        }

        public /* synthetic */ void lambda$onRs485Read$2$ZKStaffFingerprintActivity$2(byte[] bArr) throws Exception {
            if (bArr != null) {
                ZKStaffFingerprintActivity.this.registerFingerprint(bArr);
                RS485Manager.getInstance(ZKStaffFingerprintActivity.this.mContext).successCmd();
                return;
            }
            RS485Manager.getInstance(ZKStaffFingerprintActivity.this.mContext).failedCmd();
        }
    };
    /* access modifiers changed from: private */
    public ZKProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public String mThreshold;
    /* access modifiers changed from: private */
    public int maxFingerCount;
    /* access modifiers changed from: private */
    public int numFingerNow = 5;
    private boolean once = false;
    /* access modifiers changed from: private */
    public String remoteEnrollResult = "6";
    private int retry;
    /* access modifiers changed from: private */
    public int rightHandFinger = 5;
    private int soundLoad;
    private SoundPool soundPool;
    private final ArrayList<String> strFinList = new ArrayList<>();
    /* access modifiers changed from: private */
    public byte[] temp;
    /* access modifiers changed from: private */
    public TemplateManager tempLateManager;
    /* access modifiers changed from: private */
    public TextView tvPromptSameFinger;
    /* access modifiers changed from: private */
    public TextView tvShowStep;
    /* access modifiers changed from: private */
    public UserInfo userInfo;
    long userInfo_id;
    /* access modifiers changed from: private */
    public String userPin;
    /* access modifiers changed from: private */
    public String vThreshold;

    static /* synthetic */ int access$1508(ZKStaffFingerprintActivity zKStaffFingerprintActivity) {
        int i = zKStaffFingerprintActivity.currentAllFingerCount;
        zKStaffFingerprintActivity.currentAllFingerCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$1510(ZKStaffFingerprintActivity zKStaffFingerprintActivity) {
        int i = zKStaffFingerprintActivity.currentAllFingerCount;
        zKStaffFingerprintActivity.currentAllFingerCount = i - 1;
        return i;
    }

    static /* synthetic */ int access$610(ZKStaffFingerprintActivity zKStaffFingerprintActivity) {
        int i = zKStaffFingerprintActivity.count;
        zKStaffFingerprintActivity.count = i - 1;
        return i;
    }

    private void reset() {
        this.count = 3;
        this.mFingerZoneView.setChangeStatus(true);
        showStep(horOrVer, 4, 0, this.mProgressBar);
        if (horOrVer == 1) {
            this.tvPromptSameFinger.setVisibility(0);
            this.tvPromptSameFinger.setText(getResources().getString(R.string.zk_staff_firstdown));
            this.tvPromptSameFinger.setTextColor(getResources().getColor(R.color.clr_7AC143));
        } else if (this.isLRHand) {
            if (!this.finger5 || !this.finger6 || !this.finger7 || !this.finger8 || !this.finger9) {
                this.tvShowStep.setText(getResources().getString(R.string.zk_staff_firstdown));
                this.tvShowStep.setTextColor(getResources().getColor(R.color.clr_7AC143));
                return;
            }
            this.tvShowStep.setText("");
        } else if (!this.finger0 || !this.finger1 || !this.finger2 || !this.finger3 || !this.finger4) {
            this.tvShowStep.setText(getResources().getString(R.string.zk_staff_firstdown));
            this.tvShowStep.setTextColor(getResources().getColor(R.color.clr_7AC143));
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_edit_fingerprint);
        if (this.tempLateManager == null) {
            this.tempLateManager = new TemplateManager(this);
        }
        initToolBar();
        initFingerData();
        initView();
        initListener();
        initIntent();
        initManager();
        initRS485();
    }

    private void initRS485() {
        McuServiceHelper.getInstance().addOnMcuReadListener(this.mOnMcuReadListener);
    }

    private void initFingerData() {
        if (this.strFinList.size() == 0) {
            this.strFinList.add(getResources().getString(R.string.zk_staff_bigfinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_indexfinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_middlefinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_ringfinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_littlefinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_bigfinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_indexfinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_middlefinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_ringfinger));
            this.strFinList.add(getResources().getString(R.string.zk_staff_littlefinger));
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.isHome = false;
        FingerprintServiceHelper.getInstance().addOnFingerprintScanListener(this.mListener);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        DataManager dataManager2;
        super.onResume();
        this.mAccessRuleType = this.dataManager.getIntOption("AccessRuleType", 0);
        if (this.isFirst && (dataManager2 = this.dataManager) != null) {
            String strOption = dataManager2.getStrOption(DBConfig.ABOUT_MAXFINGERCOUNT, "20");
            if (strOption != null) {
                this.maxFingerCount = Integer.parseInt(strOption) * 100;
            }
            this.isFirst = false;
        }
        this.currentAllFingerCount = this.tempLateManager.getFingerTemplateForUserPin(String.valueOf(this.userInfo.getID())).size();
        if (horOrVer == 2) {
            if (this.isLRHand) {
                if (!this.finger5 || !this.finger6 || !this.finger7 || !this.finger8 || !this.finger9) {
                    this.tvShowStep.setText(getResources().getString(R.string.zk_staff_firstdown));
                    this.tvShowStep.setTextColor(getResources().getColor(R.color.clr_7AC143));
                } else {
                    this.tvShowStep.setText("");
                }
            } else if (!this.finger0 || !this.finger1 || !this.finger2 || !this.finger3 || !this.finger4) {
                this.tvShowStep.setText(getResources().getString(R.string.zk_staff_firstdown));
                this.tvShowStep.setTextColor(getResources().getColor(R.color.clr_7AC143));
            } else {
                this.tvShowStep.setText("");
            }
        }
        Log.i(ZKStaffFingerprintActivity.class.getCanonicalName(), "currentAllFingerCount:" + this.currentAllFingerCount + "  userInfo_id:" + this.userInfo_id);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.isHome = true;
        FingerprintServiceHelper.getInstance().removeOnFingerprintScanListener(this.mListener);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002b, code lost:
        if (r2 != 0) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003b, code lost:
        if (r2 != 0) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003d, code lost:
        r7.hubProtocolManager.convertPushFree(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
        if (r7.remoteEnrollResult.equals("0") == false) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r2 = r7.hubProtocolManager.convertStandaloneInit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r7.hubProtocolManager.convertStandaloneSetUserPIN(r2, r7.userInfo.getUser_PIN());
        r7.hubProtocolManager.convertStandaloneSetEventType(r2, 232);
        r7.hubProtocolManager.convertStandaloneSetLength(r2, 0);
        r7.hubProtocolManager.convertStandaloneSetResult(r2, 0);
        r7.hubProtocolManager.convertStandaloneSetReserve(r2, 7);
        r7.hubProtocolManager.sendHubAction(7, r2, "");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007e, code lost:
        if (r2 == 0) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0081, code lost:
        r4 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0083, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0084, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0086, code lost:
        r4 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0087, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r4.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x008d, code lost:
        if (r2 != 0) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008f, code lost:
        r7.hubProtocolManager.convertStandaloneFree(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0095, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0098, code lost:
        if (r2 != 0) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009a, code lost:
        r7.hubProtocolManager.convertStandaloneFree(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x009f, code lost:
        throw r4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDestroy() {
        /*
            r7 = this;
            io.reactivex.disposables.Disposable r0 = r7.mDisposable
            if (r0 == 0) goto L_0x0007
            r0.dispose()
        L_0x0007:
            com.zktechnology.android.helper.McuServiceHelper r0 = com.zktechnology.android.helper.McuServiceHelper.getInstance()
            com.zktechnology.android.helper.OnMcuReadListener r1 = r7.mOnMcuReadListener
            r0.removeOnMcuReadListener(r1)
            java.lang.String r0 = r7.userPin
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x00ab
            r0 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0034, all -> 0x0030 }
            long r2 = r2.convertPushInit()     // Catch:{ Exception -> 0x0034, all -> 0x0030 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x002e }
            r5 = 21
            java.lang.String r6 = r7.remoteEnrollResult     // Catch:{ Exception -> 0x002e }
            r4.sendHubAction(r5, r2, r6)     // Catch:{ Exception -> 0x002e }
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 == 0) goto L_0x0042
            goto L_0x003d
        L_0x002e:
            r4 = move-exception
            goto L_0x0036
        L_0x0030:
            r4 = move-exception
            r2 = r0
            goto L_0x00a1
        L_0x0034:
            r4 = move-exception
            r2 = r0
        L_0x0036:
            r4.printStackTrace()     // Catch:{ all -> 0x00a0 }
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 == 0) goto L_0x0042
        L_0x003d:
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager
            r4.convertPushFree(r2)
        L_0x0042:
            java.lang.String r2 = r7.remoteEnrollResult
            java.lang.String r3 = "0"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x00ab
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0086, all -> 0x0083 }
            long r2 = r2.convertStandaloneInit()     // Catch:{ Exception -> 0x0086, all -> 0x0083 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0081 }
            com.zkteco.android.db.orm.tna.UserInfo r5 = r7.userInfo     // Catch:{ Exception -> 0x0081 }
            java.lang.String r5 = r5.getUser_PIN()     // Catch:{ Exception -> 0x0081 }
            r4.convertStandaloneSetUserPIN(r2, r5)     // Catch:{ Exception -> 0x0081 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0081 }
            r5 = 232(0xe8, float:3.25E-43)
            r4.convertStandaloneSetEventType(r2, r5)     // Catch:{ Exception -> 0x0081 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0081 }
            r5 = 0
            r4.convertStandaloneSetLength(r2, r5)     // Catch:{ Exception -> 0x0081 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0081 }
            r4.convertStandaloneSetResult(r2, r5)     // Catch:{ Exception -> 0x0081 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0081 }
            r5 = 7
            r4.convertStandaloneSetReserve(r2, r5)     // Catch:{ Exception -> 0x0081 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0081 }
            java.lang.String r6 = ""
            r4.sendHubAction(r5, r2, r6)     // Catch:{ Exception -> 0x0081 }
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x00ab
            goto L_0x008f
        L_0x0081:
            r4 = move-exception
            goto L_0x0088
        L_0x0083:
            r4 = move-exception
            r2 = r0
            goto L_0x0096
        L_0x0086:
            r4 = move-exception
            r2 = r0
        L_0x0088:
            r4.printStackTrace()     // Catch:{ all -> 0x0095 }
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x00ab
        L_0x008f:
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.hubProtocolManager
            r0.convertStandaloneFree(r2)
            goto L_0x00ab
        L_0x0095:
            r4 = move-exception
        L_0x0096:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x009f
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.hubProtocolManager
            r0.convertStandaloneFree(r2)
        L_0x009f:
            throw r4
        L_0x00a0:
            r4 = move-exception
        L_0x00a1:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x00aa
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.hubProtocolManager
            r0.convertPushFree(r2)
        L_0x00aa:
            throw r4
        L_0x00ab:
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "action.refresh.bio.icon"
            r0.<init>(r1)
            r7.sendBroadcast(r0)
            super.onDestroy()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffFingerprintActivity.onDestroy():void");
    }

    private void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.fingerToolbar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffFingerprintActivity.this.lambda$initToolBar$0$ZKStaffFingerprintActivity(view);
            }
        }, getString(R.string.zk_staff_finger_title));
        zKToolbar.setRightView(getResources().getString(R.string.zk_staff_back), (View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffFingerprintActivity.this.lambda$initToolBar$1$ZKStaffFingerprintActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initToolBar$0$ZKStaffFingerprintActivity(View view) {
        finish();
    }

    public /* synthetic */ void lambda$initToolBar$1$ZKStaffFingerprintActivity(View view) {
        reset();
    }

    private void initView() {
        if (getResources().getConfiguration().orientation == 2) {
            horOrVer = 1;
        } else {
            horOrVer = 2;
        }
        this.mFingerZoneView = (CustomFingerZoneView) findViewById(R.id.finger_view);
        this.ivCheckState = (ImageView) findViewById(R.id.iv_check_state);
        this.ivLeftHand = (ImageView) findViewById(R.id.iv_lefthand);
        this.ivRightHand = (ImageView) findViewById(R.id.iv_righthand);
        TextView textView = (TextView) findViewById(R.id.tv_promt_samefinger);
        this.tvPromptSameFinger = textView;
        textView.setVisibility(0);
        this.tvPromptSameFinger.setText(getResources().getString(R.string.zk_staff_firstdown));
        this.tvPromptSameFinger.setTextColor(getResources().getColor(R.color.clr_7AC143));
        DataManager instance = DBManager.getInstance();
        this.dataManager = instance;
        this.mThreshold = instance.getStrOption("MThreshold", "35");
        this.vThreshold = this.dataManager.getStrOption("VThreshold", "15");
        this.hubProtocolManager = new HubProtocolManager(getApplicationContext());
        if (horOrVer == 2) {
            this.mProgressBar = (ZKProgressBar) findViewById(R.id.pb_finger);
            this.tvShowStep = (TextView) findViewById(R.id.tv_show_which_finger);
        }
    }

    private void initListener() {
        if (!getIntent().getBooleanExtra("remote", false)) {
            this.ivLeftHand.setOnClickListener(this);
            this.ivRightHand.setOnClickListener(this);
        }
        CustomFingerZoneView customFingerZoneView = this.mFingerZoneView;
        if (customFingerZoneView != null) {
            customFingerZoneView.setFingerStatusChangedListener(new CustomFingerZoneView.FingerStatusChangedListener() {
                public void onFingerStatusChanged(final int i, final int i2, final int i3, boolean z) {
                    if (z) {
                        ZKStaffFingerprintActivity.this.setTVStatus(i, i3);
                    } else if (TextUtils.isEmpty(ZKStaffFingerprintActivity.this.userPin)) {
                        if (ZKStaffFingerprintActivity.this.mFingerZoneView.getFingerStatus(i3).intValue() == 2) {
                            if (ZKStaffFingerprintActivity.this.numFingerNow != i3) {
                                boolean unused = ZKStaffFingerprintActivity.this.isCover = false;
                            }
                            ZKConfirmDialog unused2 = ZKStaffFingerprintActivity.this.dialog = new ZKConfirmDialog(ZKStaffFingerprintActivity.this);
                            ZKStaffFingerprintActivity.this.dialog.show();
                            ZKStaffFingerprintActivity.this.dialog.setType(3, ZKStaffFingerprintActivity.this.getString(R.string.zk_staff_cancel), ZKStaffFingerprintActivity.this.getString(R.string.zk_staff_delete), ZKStaffFingerprintActivity.this.getString(R.string.zk_staff_cover));
                            ZKStaffFingerprintActivity.this.dialog.setMessage(ZKStaffFingerprintActivity.this.getString(R.string.zk_staff_prompt_finger));
                            ZKStaffFingerprintActivity.this.dialog.setListener(new ZKConfirmDialog.ResultListener() {
                                public void failure() {
                                }

                                public void cover() {
                                    int i;
                                    try {
                                        if (ZKStaffFingerprintActivity.this.userInfo.getPrivilege() == 14) {
                                            if (ZKStaffFingerprintActivity.this.dataManager.getIntOption("AccessRuleType", 0) == 0) {
                                                i = ZKStaffFingerprintActivity.this.userInfo.getVerify_Type();
                                            } else {
                                                i = ZKStaffFingerprintActivity.this.dataManager.getIntOption(ZKDBConfig.OPT_DOOR_1_VERIFY_TYPE, 0);
                                            }
                                            List<ZKStaffVerifyBean> verifyList = Constants.getVerifyList(ZKStaffFingerprintActivity.this);
                                            if (!ZKStaffFingerprintActivity.this.isBothNull && !ZKStaffFingerprintActivity.this.mFingerZoneView.isFinAboveOne() && i != -1) {
                                                String string = verifyList.get(i).getString();
                                                boolean contains = string.contains("指纹");
                                                boolean contains2 = string.contains("+");
                                                boolean contains3 = string.contains("/");
                                                if (contains && !contains2 && !contains3) {
                                                    ZKStaffFingerprintActivity.this.showPromtDialog(1);
                                                    return;
                                                } else if (contains && contains2) {
                                                    ZKStaffFingerprintActivity.this.showPromtDialog(1);
                                                    return;
                                                } else if (i != 0) {
                                                    if (i != 5) {
                                                        if (i == 6) {
                                                            String main_Card = ZKStaffFingerprintActivity.this.userInfo.getMain_Card();
                                                            if (main_Card == null || main_Card.equals("0") || main_Card.equals("")) {
                                                                if (!ZKStaffFingerprintActivity.this.mFingerZoneView.isFinAboveOne()) {
                                                                    ZKStaffFingerprintActivity.this.showPromtDialog(1);
                                                                    return;
                                                                }
                                                            }
                                                        }
                                                    } else if (TextUtils.isEmpty(ZKStaffFingerprintActivity.this.userInfo.getPassword()) && !ZKStaffFingerprintActivity.this.mFingerZoneView.isFinAboveOne()) {
                                                        ZKStaffFingerprintActivity.this.showPromtDialog(1);
                                                        return;
                                                    }
                                                } else if (!HasFaceUtils.isHasFace(ZKStaffFingerprintActivity.this.userInfo) && TextUtils.isEmpty(ZKStaffFingerprintActivity.this.userInfo.getPassword()) && !ZKStaffFingerprintActivity.this.mFingerZoneView.isFinAboveOne()) {
                                                    ZKStaffFingerprintActivity.this.showPromtDialog(1);
                                                    return;
                                                }
                                            }
                                        }
                                        if (!ZKStaffFingerprintActivity.this.isBothNull || ZKStaffFingerprintActivity.this.mFingerZoneView.isFinAboveOne() || ZKStaffFingerprintActivity.this.actionValue == 1 || HasFaceUtils.isHasFace(ZKStaffFingerprintActivity.this.userInfo)) {
                                            for (FpTemplate10 delete : ZKStaffFingerprintActivity.this.tempLateManager.getFingerTemplateForUserPinAndFingerNum(String.valueOf(ZKStaffFingerprintActivity.this.userInfo.getID()), i3)) {
                                                delete.delete();
                                            }
                                            ZKStaffFingerprintActivity.this.changeFingerState(i3, false);
                                            ZKStaffFingerprintActivity.this.changeFingerView(i);
                                            ZKStaffFingerprintActivity.this.mFingerZoneView.setClickFingerStatus(1, i2, i3);
                                            ZKStaffFingerprintActivity.this.mFingerZoneView.invalidate();
                                            ZKStaffFingerprintActivity.this.mFingerZoneView.setCurrentFingerNum(i3);
                                            int unused = ZKStaffFingerprintActivity.this.numFingerNow = i3;
                                            ZKStaffFingerprintActivity.this.setTVStatus(i, i3);
                                            ZkFingerprintManager.getInstance().deleteTemplate(ZKStaffFingerprintActivity.this.userInfo.getID() + "_" + ZKStaffFingerprintActivity.this.numFingerNow);
                                            ZKStaffFingerprintActivity.access$1510(ZKStaffFingerprintActivity.this);
                                            ZKStaffFingerprintActivity.this.sendBroadcast(new Intent(OpLogReceiver.ACTION_DELETE_FINGER_PRINT).putExtra("OpWho", ZKStaffFingerprintActivity.this.userInfo.getUser_PIN()));
                                        } else {
                                            ZKStaffFingerprintActivity.this.showPromtDialog(1);
                                        }
                                        ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                                        ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                public void success() {
                                    ZKStaffFingerprintActivity.this.mFingerZoneView.setClickFingerStatus(3, i2, i3);
                                    ZKStaffFingerprintActivity.this.mFingerZoneView.invalidate();
                                    ZKStaffFingerprintActivity.this.mFingerZoneView.setCurrentFingerNum(i3);
                                    int unused = ZKStaffFingerprintActivity.this.numFingerNow = i3;
                                    ZKStaffFingerprintActivity.this.setTVStatus(i, i3);
                                    ZKStaffFingerprintActivity.this.changeFingerState(i3, false);
                                    boolean unused2 = ZKStaffFingerprintActivity.this.isCover = true;
                                    ZKStaffFingerprintActivity.this.changeFingerView(i);
                                    ZKStaffFingerprintActivity.this.tvShowStep.setText(ZKStaffFingerprintActivity.this.getResources().getString(R.string.zk_staff_firstdown));
                                    ZKStaffFingerprintActivity.this.tvShowStep.setTextColor(ZKStaffFingerprintActivity.this.getResources().getColor(R.color.clr_7AC143));
                                }
                            });
                            return;
                        }
                        if (ZKStaffFingerprintActivity.this.fingerState) {
                            ZKStaffFingerprintActivity.this.changeFingerState(i2, true);
                            if (ZKStaffFingerprintActivity.this.mFingerZoneView.getFingerStatus(i2).intValue() == 1) {
                                ZKStaffFingerprintActivity.this.changeFingerState(i2, false);
                            }
                            ZKStaffFingerprintActivity.this.fingerState = false;
                        }
                        if (ZKStaffFingerprintActivity.this.numFingerNow != i3) {
                            boolean unused3 = ZKStaffFingerprintActivity.this.isCover = false;
                        }
                        ZKStaffFingerprintActivity.this.setTVStatus(i, i3);
                        ZKStaffFingerprintActivity.this.mFingerZoneView.setClickFingerStatus(2, i2, i3);
                        ZKStaffFingerprintActivity.this.mFingerZoneView.invalidate();
                        ZKStaffFingerprintActivity.this.mFingerZoneView.setCurrentFingerNum(i3);
                        int unused4 = ZKStaffFingerprintActivity.this.numFingerNow = i3;
                    }
                }

                public void changeFingerBgAndState(int i) {
                    if (i == 1) {
                        for (int i2 = 0; i2 < 5; i2++) {
                            ZKStaffFingerprintActivity.this.changeFingerState(i2, true);
                        }
                    } else if (i == 2) {
                        for (int i3 = 5; i3 < 10; i3++) {
                            ZKStaffFingerprintActivity.this.changeFingerState(i3, true);
                        }
                    }
                    ZKStaffFingerprintActivity.this.changeFingerView(i);
                }
            });
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        this.userInfo_id = intent.getLongExtra("userInfo_id", 0);
        this.isBothNull = intent.getBooleanExtra("isBothNull", true);
        this.actionValue = intent.getIntExtra("action", 0);
        int intExtra = intent.getIntExtra("fingerID", -1);
        this.userPin = intent.getStringExtra("userPin");
        this.retry = intent.getIntExtra("retry", Integer.MAX_VALUE);
        try {
            if (!TextUtils.isEmpty(this.userPin)) {
                this.userInfo = (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", this.userPin).queryForFirst();
            } else {
                this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(this.userInfo_id));
            }
            if (this.userInfo == null) {
                finish();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<FpTemplate10> fingerTemplateForUserPin = this.tempLateManager.getFingerTemplateForUserPin(String.valueOf(this.userInfo.getID()));
        for (FpTemplate10 fingerid : fingerTemplateForUserPin) {
            int fingerid2 = fingerid.getFingerid();
            this.mFingerZoneView.setFingerFirstShow(fingerid2, 2);
            changeFingerState(fingerid2, true);
        }
        changeFingerView(2);
        if (intExtra != -1) {
            if (intExtra < 5) {
                onClick(this.ivLeftHand);
            }
            setTVStatus(intExtra >= 5 ? 2 : 1, intExtra);
            this.mFingerZoneView.setClickFingerStatus(2, intExtra, intExtra);
            this.mFingerZoneView.invalidate();
            this.mFingerZoneView.setCurrentFingerNum(intExtra);
            this.numFingerNow = intExtra;
            Iterator<FpTemplate10> it = fingerTemplateForUserPin.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (this.numFingerNow == it.next().getFingerid()) {
                        this.isCover = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            this.mFingerZoneView.setFingerFirstSelectedNum();
            this.numFingerNow = this.mFingerZoneView.getCurrentFingerNum();
            this.mFingerZoneView.invalidate();
        }
        if (getIntent().getBooleanExtra("remote", false)) {
            changeFingerState(intExtra, false);
        }
    }

    private void initManager() {
        try {
            this.afd = getAssets().openFd("beep.ogg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SoundPool soundPool2 = new SoundPool(1, 1, 0);
        this.soundPool = soundPool2;
        this.soundLoad = soundPool2.load(this.afd, 0);
        AudioManager audioManager = (AudioManager) getSystemService("audio");
        this.Volume = ((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3));
    }

    /* access modifiers changed from: private */
    public void setTVStatus(int i, int i2) {
        if (1 == i) {
            this.leftHandFinger = i2;
        } else if (2 == i) {
            this.rightHandFinger = i2;
        }
        if (horOrVer == 2) {
            if (i == 1) {
                this.tvPromptSameFinger.setText(this.strFinList.get(4 - i2));
            } else if (i == 2) {
                this.tvPromptSameFinger.setText(this.strFinList.get(i2));
            }
        }
        changeFingerView(i);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.iv_lefthand) {
            if (id == R.id.iv_righthand && this.mFingerZoneView.getChangeStatus()) {
                if (this.once) {
                    this.mFingerZoneView.setWhichHandAndFinger(2, this.rightHandFinger);
                    this.numFingerNow = this.rightHandFinger;
                    this.ivLeftHand.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.lhand_black));
                    this.ivRightHand.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.rhand_white));
                    this.mFingerZoneView.setBackground(AppCompatResources.getDrawable(this.mContext, R.mipmap.right_bg_01));
                    if (!this.finger5 || !this.finger6 || !this.finger7 || !this.finger8 || !this.finger9) {
                        this.tvShowStep.setText(getResources().getString(R.string.zk_staff_firstdown));
                        this.tvShowStep.setTextColor(getResources().getColor(R.color.clr_7AC143));
                    }
                    this.once = false;
                    this.isLRHand = true;
                    changeFingerView(2);
                }
                if (horOrVer == 2) {
                    this.tvPromptSameFinger.setText(this.strFinList.get(this.rightHandFinger));
                }
            }
        } else if (this.mFingerZoneView.getChangeStatus()) {
            if (!this.once) {
                this.mFingerZoneView.setWhichHandAndFinger(1, this.leftHandFinger);
                this.numFingerNow = this.leftHandFinger;
                this.ivLeftHand.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.lhand_white));
                this.ivRightHand.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.rhand_black));
                this.mFingerZoneView.setBackground(AppCompatResources.getDrawable(this.mContext, R.mipmap.left_bg_01));
                if (!this.finger0 || !this.finger1 || !this.finger2 || !this.finger3 || !this.finger4) {
                    this.tvShowStep.setText(getResources().getString(R.string.zk_staff_firstdown));
                    this.tvShowStep.setTextColor(getResources().getColor(R.color.clr_7AC143));
                }
                this.once = true;
                this.isLRHand = false;
                changeFingerView(1);
            }
            if (horOrVer == 2) {
                this.tvPromptSameFinger.setText(this.strFinList.get(4 - this.leftHandFinger));
            }
        }
    }

    /* access modifiers changed from: private */
    public void registerFingerprint(byte[] bArr) {
        this.temp = bArr;
        SoundPool soundPool2 = this.soundPool;
        int i = this.soundLoad;
        float f = this.Volume;
        soundPool2.play(i, f, f, 0, 0, 1.0f);
        postInactivityTimeoutHandler();
        if (!this.isHome && !this.isShowDialog) {
            this.fingerState = true;
            int i2 = this.numFingerNow;
            if (i2 < 0 || i2 > 4) {
                if (i2 >= 5 && i2 <= 9 && this.finger5 && this.finger6 && this.finger7 && this.finger8 && this.finger9) {
                    this.fingerState = false;
                    return;
                }
            } else if (this.finger0 && this.finger1 && this.finger2 && this.finger3 && this.finger4) {
                this.fingerState = false;
                return;
            }
            if (this.currentAllFingerCount < this.maxFingerCount || this.isCover) {
                if (this.tempLateManager.getFingerCount() >= ((long) this.maxFingerCount) && !this.isCover) {
                    showToast((Context) this, getResources().getString(R.string.zk_staff_fill_finger));
                    finish();
                }
                this.mFaceService.submit(this.fingerTask);
                return;
            }
            showToast((Context) this, getResources().getString(R.string.zk_staff_max_finger));
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendUserInfoHub() {
        /*
            r8 = this;
            com.zkteco.android.core.sdk.HubProtocolManager r0 = new com.zkteco.android.core.sdk.HubProtocolManager
            r0.<init>(r8)
            r1 = 0
            long r3 = r0.convertPushInit()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.lang.String r5 = "USER_INFO"
            r0.setPushTableName(r3, r5)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r5 = "ID"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r8.userInfo     // Catch:{ Exception -> 0x0061 }
            long r6 = r6.getID()     // Catch:{ Exception -> 0x0061 }
            int r6 = (int) r6     // Catch:{ Exception -> 0x0061 }
            r0.setPushIntField(r3, r5, r6)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r5 = "User_PIN"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r8.userInfo     // Catch:{ Exception -> 0x0061 }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ Exception -> 0x0061 }
            r0.setPushStrField(r3, r5, r6)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r5 = "SEND_FLAG"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r8.userInfo     // Catch:{ Exception -> 0x0061 }
            int r6 = r6.getSEND_FLAG()     // Catch:{ Exception -> 0x0061 }
            r0.setPushIntField(r3, r5, r6)     // Catch:{ Exception -> 0x0061 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0061 }
            r5.<init>()     // Catch:{ Exception -> 0x0061 }
            java.lang.String r6 = "User_PIN = '"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0061 }
            com.zkteco.android.db.orm.tna.UserInfo r6 = r8.userInfo     // Catch:{ Exception -> 0x0061 }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ Exception -> 0x0061 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r6 = "'"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0061 }
            r0.setPushCon(r3, r5)     // Catch:{ Exception -> 0x0061 }
            r5 = 0
            java.lang.String r6 = ""
            r0.sendHubAction(r5, r3, r6)     // Catch:{ Exception -> 0x0061 }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0072
            goto L_0x006f
        L_0x005f:
            r5 = move-exception
            goto L_0x0073
        L_0x0061:
            r5 = move-exception
            goto L_0x0068
        L_0x0063:
            r5 = move-exception
            r3 = r1
            goto L_0x0073
        L_0x0066:
            r5 = move-exception
            r3 = r1
        L_0x0068:
            r5.printStackTrace()     // Catch:{ all -> 0x005f }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0072
        L_0x006f:
            r0.convertPushFree(r3)
        L_0x0072:
            return
        L_0x0073:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x007a
            r0.convertPushFree(r3)
        L_0x007a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffFingerprintActivity.sendUserInfoHub():void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendFingerHub(int r10) {
        /*
            r9 = this;
            r0 = 0
            com.zkteco.android.db.orm.manager.template.TemplateManager r2 = r9.tempLateManager     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            com.zkteco.android.db.orm.tna.UserInfo r3 = r9.userInfo     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            long r3 = r3.getID()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.util.List r10 = r2.getFingerTemplateForUserPinAndFingerNum(r3, r10)     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r9.hubProtocolManager     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            long r2 = r2.convertPushInit()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r9.hubProtocolManager     // Catch:{ Exception -> 0x0061 }
            java.lang.String r5 = "fptemplate10"
            r4.setPushTableName(r2, r5)     // Catch:{ Exception -> 0x0061 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r9.hubProtocolManager     // Catch:{ Exception -> 0x0061 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0061 }
            r5.<init>()     // Catch:{ Exception -> 0x0061 }
            java.lang.String r6 = "ID="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0061 }
            r6 = 0
            java.lang.Object r7 = r10.get(r6)     // Catch:{ Exception -> 0x0061 }
            com.zkteco.android.db.orm.tna.FpTemplate10 r7 = (com.zkteco.android.db.orm.tna.FpTemplate10) r7     // Catch:{ Exception -> 0x0061 }
            long r7 = r7.getID()     // Catch:{ Exception -> 0x0061 }
            int r7 = (int) r7     // Catch:{ Exception -> 0x0061 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0061 }
            r4.setPushCon(r2, r5)     // Catch:{ Exception -> 0x0061 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r9.hubProtocolManager     // Catch:{ Exception -> 0x0061 }
            java.lang.String r5 = "ID"
            java.lang.Object r10 = r10.get(r6)     // Catch:{ Exception -> 0x0061 }
            com.zkteco.android.db.orm.tna.FpTemplate10 r10 = (com.zkteco.android.db.orm.tna.FpTemplate10) r10     // Catch:{ Exception -> 0x0061 }
            long r7 = r10.getID()     // Catch:{ Exception -> 0x0061 }
            int r10 = (int) r7     // Catch:{ Exception -> 0x0061 }
            r4.setPushIntField(r2, r5, r10)     // Catch:{ Exception -> 0x0061 }
            com.zkteco.android.core.sdk.HubProtocolManager r10 = r9.hubProtocolManager     // Catch:{ Exception -> 0x0061 }
            java.lang.String r4 = ""
            r10.sendHubAction(r6, r2, r4)     // Catch:{ Exception -> 0x0061 }
            int r10 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r10 == 0) goto L_0x0074
            goto L_0x006f
        L_0x0061:
            r10 = move-exception
            goto L_0x0068
        L_0x0063:
            r10 = move-exception
            r2 = r0
            goto L_0x0076
        L_0x0066:
            r10 = move-exception
            r2 = r0
        L_0x0068:
            r10.printStackTrace()     // Catch:{ all -> 0x0075 }
            int r10 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r10 == 0) goto L_0x0074
        L_0x006f:
            com.zkteco.android.core.sdk.HubProtocolManager r10 = r9.hubProtocolManager
            r10.convertPushFree(r2)
        L_0x0074:
            return
        L_0x0075:
            r10 = move-exception
        L_0x0076:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x007f
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r9.hubProtocolManager
            r0.convertPushFree(r2)
        L_0x007f:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffFingerprintActivity.sendFingerHub(int):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendChangeHub() {
        /*
            r7 = this;
            r0 = 0
            com.zkteco.android.core.sdk.HubProtocolManager r2 = r7.hubProtocolManager     // Catch:{ Exception -> 0x001a, all -> 0x0017 }
            long r2 = r2.convertPushInit()     // Catch:{ Exception -> 0x001a, all -> 0x0017 }
            com.zkteco.android.core.sdk.HubProtocolManager r4 = r7.hubProtocolManager     // Catch:{ Exception -> 0x0015 }
            r5 = 25
            r6 = 0
            r4.sendHubAction(r5, r2, r6)     // Catch:{ Exception -> 0x0015 }
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x0028
            goto L_0x0023
        L_0x0015:
            r4 = move-exception
            goto L_0x001c
        L_0x0017:
            r4 = move-exception
            r2 = r0
            goto L_0x002a
        L_0x001a:
            r4 = move-exception
            r2 = r0
        L_0x001c:
            r4.printStackTrace()     // Catch:{ all -> 0x0029 }
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x0028
        L_0x0023:
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.hubProtocolManager
            r0.convertPushFree(r2)
        L_0x0028:
            return
        L_0x0029:
            r4 = move-exception
        L_0x002a:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x0033
            com.zkteco.android.core.sdk.HubProtocolManager r0 = r7.hubProtocolManager
            r0.convertPushFree(r2)
        L_0x0033:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffFingerprintActivity.sendChangeHub():void");
    }

    /* access modifiers changed from: private */
    public void changeFingerState(int i, boolean z) {
        switch (i) {
            case 0:
                this.finger0 = z;
                return;
            case 1:
                this.finger1 = z;
                return;
            case 2:
                this.finger2 = z;
                return;
            case 3:
                this.finger3 = z;
                return;
            case 4:
                this.finger4 = z;
                return;
            case 5:
                this.finger5 = z;
                return;
            case 6:
                this.finger6 = z;
                return;
            case 7:
                this.finger7 = z;
                return;
            case 8:
                this.finger8 = z;
                return;
            case 9:
                this.finger9 = z;
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void changeFingerView(int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (i == 1) {
                if (!this.finger0 || !this.finger1 || !this.finger2 || !this.finger3 || !this.finger4) {
                    this.mFingerZoneView.setBackground(AppCompatResources.getDrawable(this.mContext, R.mipmap.left_bg_01));
                } else {
                    this.mFingerZoneView.setBackground(AppCompatResources.getDrawable(this.mContext, R.mipmap.left_bg_02));
                }
            }
            if (i != 2) {
                return;
            }
            if (!this.finger5 || !this.finger6 || !this.finger7 || !this.finger8 || !this.finger9) {
                this.mFingerZoneView.setBackground(AppCompatResources.getDrawable(this.mContext, R.mipmap.right_bg_01));
            } else {
                this.mFingerZoneView.setBackground(AppCompatResources.getDrawable(this.mContext, R.mipmap.right_bg_02));
            }
        }
    }

    /* access modifiers changed from: private */
    public void showPromtDialog(int i) {
        if (!isFinishing() && !isDestroyed()) {
            try {
                final ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(this);
                zKConfirmDialog.show();
                zKConfirmDialog.setType(1, "", "", getString(R.string.zk_staff_ok));
                if (i == 1) {
                    zKConfirmDialog.setMessage(getString(R.string.zk_staff_modify_promt_only));
                } else if (i == 2) {
                    zKConfirmDialog.setMessage(getString(R.string.zk_staff_modify_promt_delfail));
                } else if (i == 3) {
                    zKConfirmDialog.setMessage(getString(R.string.zk_staff_modify_promt_enrfail));
                    int i2 = this.retry - 1;
                    this.retry = i2;
                    if (i2 < 0) {
                        this.remoteEnrollResult = "4";
                        zKConfirmDialog.dismiss();
                        finish();
                    }
                }
                zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
                    public void failure() {
                        boolean unused = ZKStaffFingerprintActivity.this.isShowDialog = false;
                    }

                    public void cover() {
                        boolean unused = ZKStaffFingerprintActivity.this.isShowDialog = false;
                    }

                    public void success() {
                        zKConfirmDialog.cancel();
                        boolean unused = ZKStaffFingerprintActivity.this.isShowDialog = false;
                    }
                });
                this.isShowDialog = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void showCompletedDialog(Context context) {
        final ZKConfirmDialog zKConfirmDialog = new ZKConfirmDialog(context);
        zKConfirmDialog.show();
        zKConfirmDialog.setType(2, getString(R.string.zk_staff_fin_next_no), "", getString(R.string.zk_staff_fin_next_yes));
        zKConfirmDialog.setMessage(getString(R.string.zk_staff_fin_continue_ornot));
        zKConfirmDialog.setListener(new ZKConfirmDialog.ResultListener() {
            public void cover() {
            }

            public void failure() {
                zKConfirmDialog.cancel();
                ZKStaffFingerprintActivity.this.finish();
                boolean unused = ZKStaffFingerprintActivity.this.isShowDialog = false;
            }

            public void success() {
                zKConfirmDialog.cancel();
                boolean unused = ZKStaffFingerprintActivity.this.isShowDialog = false;
                ZKStaffFingerprintActivity.this.trySwitchHand();
            }
        });
        this.isShowDialog = true;
    }

    /* access modifiers changed from: private */
    public void trySwitchHand() {
        boolean z = this.finger0;
        if (!z || !this.finger1 || !this.finger2 || !this.finger3 || !this.finger4 || !this.finger5 || !this.finger6 || !this.finger7 || !this.finger8 || !this.finger9) {
            boolean z2 = this.isLRHand;
            if (z2 && this.finger5 && this.finger6 && this.finger7 && this.finger8 && this.finger9) {
                onClick(this.ivLeftHand);
            } else if (!z2 && z && this.finger1 && this.finger2 && this.finger3 && this.finger4) {
                onClick(this.ivRightHand);
            }
        }
    }

    /* access modifiers changed from: private */
    public void showStep(int i, int i2, int i3, ZKProgressBar zKProgressBar) {
        if (i == 2) {
            if (i2 == 1) {
                zKProgressBar.setStatusStage1(i3);
            } else if (i2 == 2) {
                zKProgressBar.setStatusStage2(i3);
            } else if (i2 == 3) {
                zKProgressBar.setStatusStage3(i3);
            } else if (i2 == 4) {
                zKProgressBar.setStatusProNew();
            }
            zKProgressBar.invalidate();
        }
    }
}
