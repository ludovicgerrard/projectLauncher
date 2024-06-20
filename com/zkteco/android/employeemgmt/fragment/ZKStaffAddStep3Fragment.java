package com.zkteco.android.employeemgmt.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.receiver.OpLogReceiver;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.tna.AccUserAuthorize;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.ZKStaffAddStepActivity;
import com.zkteco.android.employeemgmt.activity.ZKStaffIconCollectionActivity;
import com.zkteco.android.employeemgmt.fragment.base.ZKStaffBaseFragment;
import com.zkteco.android.employeemgmt.widget.CircleTransform;
import com.zkteco.android.zkcore.utils.FileUtil;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import java.io.File;

public class ZKStaffAddStep3Fragment extends ZKStaffBaseFragment implements View.OnClickListener, ZKStaffAddStepActivity.RefreshPhotoListener {
    private static final String DEFAULT_PHOTO_PATH = ZKFilePath.PICTURE_PATH;
    public static final String MAX_USERPTHOTO = "~MaxUserPhotoCount";
    public static final String OPT_ACC_RULE_TYPE = "AccessRuleType";
    public static final String OPT_LOCK_FUN_ON = "~LockFunOn";
    public static final int REQUEST_TAKEPHOTO = 999;
    private static final int RESULT_TAKEPHOTO_SUCCESS = 1999;
    /* access modifiers changed from: private */
    public Activity activity;
    private ImageView mIvTakePhoto;
    private int mUserPhotoCount;
    private int sAccessRuleType;
    private int sLockFunOn;
    /* access modifiers changed from: private */
    public String user_pin;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentActivity activity2 = getActivity();
        this.activity = activity2;
        ((ZKStaffAddStepActivity) activity2).setRefreshPhotoListener(this);
        View inflate = layoutInflater.inflate(R.layout.fragment_add_step3, viewGroup, false);
        this.mIvTakePhoto = (ImageView) inflate.findViewById(R.id.bt_take_photo);
        if (getResources().getConfiguration().orientation == 2) {
            ((ImageButton) inflate.findViewById(R.id.ib_left4)).setOnClickListener(this);
            ((ImageButton) inflate.findViewById(R.id.ib_right4)).setOnClickListener(this);
        } else {
            ((Button) inflate.findViewById(R.id.ib_left4)).setOnClickListener(this);
            ((Button) inflate.findViewById(R.id.ib_right4)).setOnClickListener(this);
        }
        this.mIvTakePhoto.setOnClickListener(this);
        this.user_pin = ((ZKStaffAddStepActivity) getActivity()).getUserInfo().getUser_PIN();
        if (!((ZKStaffAddStepActivity) getActivity()).getIsFirst() && !TextUtils.isEmpty(this.user_pin)) {
            if (!((ZKStaffAddStepActivity) getActivity()).getUserPinReplace().equals(this.user_pin)) {
                new Thread(new Runnable() {
                    public void run() {
                        FileUtil.deleteFile(ZKFilePath.PICTURE_PATH + ZKStaffAddStep3Fragment.this.user_pin + ".jpg", ZKStaffAddStep3Fragment.this.getActivity());
                    }
                }).run();
            } else if (Build.VERSION.SDK_INT >= 23) {
                ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) Glide.with((Fragment) this).load(ZKFilePath.PICTURE_PATH + this.user_pin + ".jpg").skipMemoryCache(true)).diskCacheStrategy(DiskCacheStrategy.NONE)).error((int) R.mipmap.ic_take_photo)).transform((Transformation<Bitmap>) new CircleTransform())).into(this.mIvTakePhoto);
            } else if (getResources().getConfiguration().orientation == 2) {
                ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) Glide.with((Fragment) this).load(ZKFilePath.PICTURE_PATH + this.user_pin + ".jpg").skipMemoryCache(true)).diskCacheStrategy(DiskCacheStrategy.NONE)).error((int) R.mipmap.ic_take_photo)).transform((Transformation<Bitmap>) new CircleTransform())).into(this.mIvTakePhoto);
            }
        }
        ((ZKStaffAddStepActivity) getActivity()).setIsFirst(false);
        return inflate;
    }

    public void onStart() {
        super.onStart();
        this.sLockFunOn = DBManager.getInstance().getIntOption("~LockFunOn", 0);
        this.sAccessRuleType = DBManager.getInstance().getIntOption("AccessRuleType", 0);
        this.mUserPhotoCount = DBManager.getInstance().getIntOption("~MaxUserPhotoCount", 10000);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.bt_take_photo) {
            if (id == R.id.ib_left4) {
                finishFragment();
                FileUtil.deleteFile(ZKFilePath.PICTURE_PATH + this.user_pin + ZKFilePath.SUFFIX_IMAGE, this.activity);
                FileUtil.deleteFile(ZKFilePath.IMAGE_PATH + "temp/" + this.user_pin + ZKFilePath.SUFFIX_IMAGE, this.activity);
            } else if (id == R.id.ib_right4) {
                pushFragment(R.id.sfl_content, new ZKStaffAddStep4Fragment());
                final UserInfo userInfo = ((ZKStaffAddStepActivity) this.activity).getUserInfo();
                if (userInfo != null) {
                    try {
                        if (this.activity != null) {
                            userInfo.setSEND_FLAG(1);
                            userInfo.create();
                            File file = new File(ZKFilePath.IMAGE_PATH + "temp/" + this.user_pin + ZKFilePath.SUFFIX_IMAGE);
                            if (file.exists()) {
                                file.renameTo(new File(ZKFilePath.PICTURE_PATH + this.user_pin + ZKFilePath.SUFFIX_IMAGE));
                            }
                            if (this.sLockFunOn == 15 && this.sAccessRuleType == 1) {
                                AccUserAuthorize accUserAuthorize = new AccUserAuthorize();
                                accUserAuthorize.setUserPIN(userInfo.getUser_PIN());
                                accUserAuthorize.setAuthorizeDoor(1);
                                accUserAuthorize.setAuthorizeTimezone(1);
                                accUserAuthorize.create();
                            }
                            new Thread(new Runnable() {
                                /* JADX WARNING: Code restructure failed: missing block: B:17:0x006a, code lost:
                                    if (r6 != 0) goto L_0x006c;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:18:0x006c, code lost:
                                    r2.convertPushFree(r6);
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:20:0x0081, code lost:
                                    if (com.zkteco.android.zkcore.utils.FileUtil.isUserPhotoExist(com.zkteco.android.zkcore.utils.ZKFilePath.PICTURE_PATH, r6.getUser_PIN(), com.zkteco.android.employeemgmt.fragment.ZKStaffAddStep3Fragment.access$100(r11.this$0)) == false) goto L_0x00d5;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
                                    r6 = r2.convertPushInit();
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
                                    r2.setPushTableName(r6, "USER_PHOTO_INDEX");
                                    r2.setPushStrField(r6, "User_PIN", r6.getUser_PIN());
                                    r2.setPushCon(r6, "User_PIN=" + r6.getUser_PIN());
                                    r2.sendHubAction(0, r6, "");
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b6, code lost:
                                    if (r6 == 0) goto L_0x00d5;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b9, code lost:
                                    r0 = th;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:29:0x00bb, code lost:
                                    r0 = e;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:30:0x00bd, code lost:
                                    r0 = th;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:31:0x00be, code lost:
                                    r6 = 0;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c0, code lost:
                                    r0 = e;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c1, code lost:
                                    r6 = 0;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
                                    r0.printStackTrace();
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c7, code lost:
                                    if (r6 != 0) goto L_0x00c9;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c9, code lost:
                                    r2.convertPushFree(r6);
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:40:0x00cf, code lost:
                                    if (r6 != 0) goto L_0x00d1;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d1, code lost:
                                    r2.convertPushFree(r6);
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d4, code lost:
                                    throw r0;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
                                    r6 = r2.convertStandaloneInit();
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
                                    r2.convertStandaloneSetUserPIN(r6, r6.getUser_PIN());
                                    r2.convertStandaloneSetEventType(r6, 231);
                                    r2.sendHubAction(5, r6, "");
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:48:0x00ed, code lost:
                                    if (r6 == 0) goto L_?;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f0, code lost:
                                    r0 = th;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f2, code lost:
                                    r0 = e;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:51:0x00f4, code lost:
                                    r0 = th;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f5, code lost:
                                    r6 = 0;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f7, code lost:
                                    r0 = e;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:54:0x00f8, code lost:
                                    r6 = 0;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
                                    r0.printStackTrace();
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fe, code lost:
                                    if (r6 == 0) goto L_?;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:59:0x0100, code lost:
                                    r2.convertStandaloneFree(r6);
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:61:0x0106, code lost:
                                    if (r6 != 0) goto L_0x0108;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:62:0x0108, code lost:
                                    r2.convertStandaloneFree(r6);
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:63:0x010b, code lost:
                                    throw r0;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
                                    return;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
                                    return;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
                                    return;
                                 */
                                /* JADX WARNING: Code restructure failed: missing block: B:7:0x0057, code lost:
                                    if (r6 != 0) goto L_0x006c;
                                 */
                                /* JADX WARNING: Removed duplicated region for block: B:41:0x00d1  */
                                /* JADX WARNING: Removed duplicated region for block: B:62:0x0108  */
                                /* JADX WARNING: Removed duplicated region for block: B:66:0x0110  */
                                /* JADX WARNING: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
                                /* Code decompiled incorrectly, please refer to instructions dump. */
                                public void run() {
                                    /*
                                        r11 = this;
                                        java.lang.String r0 = "User_PIN"
                                        java.lang.String r1 = ""
                                        com.zkteco.android.core.sdk.HubProtocolManager r2 = new com.zkteco.android.core.sdk.HubProtocolManager
                                        com.zkteco.android.employeemgmt.fragment.ZKStaffAddStep3Fragment r3 = com.zkteco.android.employeemgmt.fragment.ZKStaffAddStep3Fragment.this
                                        android.app.Activity r3 = r3.activity
                                        r2.<init>(r3)
                                        r3 = 0
                                        r4 = 0
                                        long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x0063, all -> 0x005f }
                                        java.lang.String r8 = "USER_INFO"
                                        r2.setPushTableName(r6, r8)     // Catch:{ Exception -> 0x005d }
                                        java.lang.String r8 = "ID"
                                        com.zkteco.android.db.orm.tna.UserInfo r9 = r6     // Catch:{ Exception -> 0x005d }
                                        long r9 = r9.getID()     // Catch:{ Exception -> 0x005d }
                                        int r9 = (int) r9     // Catch:{ Exception -> 0x005d }
                                        r2.setPushIntField(r6, r8, r9)     // Catch:{ Exception -> 0x005d }
                                        com.zkteco.android.db.orm.tna.UserInfo r8 = r6     // Catch:{ Exception -> 0x005d }
                                        java.lang.String r8 = r8.getUser_PIN()     // Catch:{ Exception -> 0x005d }
                                        r2.setPushStrField(r6, r0, r8)     // Catch:{ Exception -> 0x005d }
                                        java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005d }
                                        r8.<init>()     // Catch:{ Exception -> 0x005d }
                                        java.lang.String r9 = "User_PIN = '"
                                        java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x005d }
                                        com.zkteco.android.db.orm.tna.UserInfo r9 = r6     // Catch:{ Exception -> 0x005d }
                                        java.lang.String r9 = r9.getUser_PIN()     // Catch:{ Exception -> 0x005d }
                                        java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x005d }
                                        java.lang.String r9 = "'"
                                        java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x005d }
                                        java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x005d }
                                        r2.setPushCon(r6, r8)     // Catch:{ Exception -> 0x005d }
                                        r2.sendHubAction(r3, r6, r1)     // Catch:{ Exception -> 0x005d }
                                        int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r8 == 0) goto L_0x006f
                                        goto L_0x006c
                                    L_0x005a:
                                        r0 = move-exception
                                        goto L_0x010c
                                    L_0x005d:
                                        r8 = move-exception
                                        goto L_0x0065
                                    L_0x005f:
                                        r0 = move-exception
                                        r6 = r4
                                        goto L_0x010c
                                    L_0x0063:
                                        r8 = move-exception
                                        r6 = r4
                                    L_0x0065:
                                        r8.printStackTrace()     // Catch:{ all -> 0x005a }
                                        int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r8 == 0) goto L_0x006f
                                    L_0x006c:
                                        r2.convertPushFree(r6)
                                    L_0x006f:
                                        java.lang.String r6 = com.zkteco.android.zkcore.utils.ZKFilePath.PICTURE_PATH
                                        com.zkteco.android.db.orm.tna.UserInfo r7 = r6
                                        java.lang.String r7 = r7.getUser_PIN()
                                        com.zkteco.android.employeemgmt.fragment.ZKStaffAddStep3Fragment r8 = com.zkteco.android.employeemgmt.fragment.ZKStaffAddStep3Fragment.this
                                        android.app.Activity r8 = r8.activity
                                        boolean r6 = com.zkteco.android.zkcore.utils.FileUtil.isUserPhotoExist(r6, r7, r8)
                                        if (r6 == 0) goto L_0x00d5
                                        long r6 = r2.convertPushInit()     // Catch:{ Exception -> 0x00c0, all -> 0x00bd }
                                        java.lang.String r8 = "USER_PHOTO_INDEX"
                                        r2.setPushTableName(r6, r8)     // Catch:{ Exception -> 0x00bb }
                                        com.zkteco.android.db.orm.tna.UserInfo r8 = r6     // Catch:{ Exception -> 0x00bb }
                                        java.lang.String r8 = r8.getUser_PIN()     // Catch:{ Exception -> 0x00bb }
                                        r2.setPushStrField(r6, r0, r8)     // Catch:{ Exception -> 0x00bb }
                                        java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bb }
                                        r0.<init>()     // Catch:{ Exception -> 0x00bb }
                                        java.lang.String r8 = "User_PIN="
                                        java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ Exception -> 0x00bb }
                                        com.zkteco.android.db.orm.tna.UserInfo r8 = r6     // Catch:{ Exception -> 0x00bb }
                                        java.lang.String r8 = r8.getUser_PIN()     // Catch:{ Exception -> 0x00bb }
                                        java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ Exception -> 0x00bb }
                                        java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00bb }
                                        r2.setPushCon(r6, r0)     // Catch:{ Exception -> 0x00bb }
                                        r2.sendHubAction(r3, r6, r1)     // Catch:{ Exception -> 0x00bb }
                                        int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r0 == 0) goto L_0x00d5
                                        goto L_0x00c9
                                    L_0x00b9:
                                        r0 = move-exception
                                        goto L_0x00cd
                                    L_0x00bb:
                                        r0 = move-exception
                                        goto L_0x00c2
                                    L_0x00bd:
                                        r0 = move-exception
                                        r6 = r4
                                        goto L_0x00cd
                                    L_0x00c0:
                                        r0 = move-exception
                                        r6 = r4
                                    L_0x00c2:
                                        r0.printStackTrace()     // Catch:{ all -> 0x00b9 }
                                        int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r0 == 0) goto L_0x00d5
                                    L_0x00c9:
                                        r2.convertPushFree(r6)
                                        goto L_0x00d5
                                    L_0x00cd:
                                        int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r1 == 0) goto L_0x00d4
                                        r2.convertPushFree(r6)
                                    L_0x00d4:
                                        throw r0
                                    L_0x00d5:
                                        long r6 = r2.convertStandaloneInit()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
                                        com.zkteco.android.db.orm.tna.UserInfo r0 = r6     // Catch:{ Exception -> 0x00f2 }
                                        java.lang.String r0 = r0.getUser_PIN()     // Catch:{ Exception -> 0x00f2 }
                                        r2.convertStandaloneSetUserPIN(r6, r0)     // Catch:{ Exception -> 0x00f2 }
                                        r0 = 231(0xe7, float:3.24E-43)
                                        r2.convertStandaloneSetEventType(r6, r0)     // Catch:{ Exception -> 0x00f2 }
                                        r0 = 5
                                        r2.sendHubAction(r0, r6, r1)     // Catch:{ Exception -> 0x00f2 }
                                        int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r0 == 0) goto L_0x0103
                                        goto L_0x0100
                                    L_0x00f0:
                                        r0 = move-exception
                                        goto L_0x0104
                                    L_0x00f2:
                                        r0 = move-exception
                                        goto L_0x00f9
                                    L_0x00f4:
                                        r0 = move-exception
                                        r6 = r4
                                        goto L_0x0104
                                    L_0x00f7:
                                        r0 = move-exception
                                        r6 = r4
                                    L_0x00f9:
                                        r0.printStackTrace()     // Catch:{ all -> 0x00f0 }
                                        int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r0 == 0) goto L_0x0103
                                    L_0x0100:
                                        r2.convertStandaloneFree(r6)
                                    L_0x0103:
                                        return
                                    L_0x0104:
                                        int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r1 == 0) goto L_0x010b
                                        r2.convertStandaloneFree(r6)
                                    L_0x010b:
                                        throw r0
                                    L_0x010c:
                                        int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                                        if (r1 == 0) goto L_0x0113
                                        r2.convertPushFree(r6)
                                    L_0x0113:
                                        throw r0
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.fragment.ZKStaffAddStep3Fragment.AnonymousClass2.run():void");
                                }
                            }).start();
                            getActivity().sendBroadcast(new Intent(OpLogReceiver.ACTION_REGISTER_USER));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ((ZKStaffAddStepActivity) this.activity).setIsCreateNewUser(true);
            }
        } else if (new File(DEFAULT_PHOTO_PATH).listFiles().length >= this.mUserPhotoCount) {
            showToast((Context) getActivity(), getActivity().getString(R.string.zk_staff_max_user_photo));
        } else {
            Intent intent = new Intent(getActivity(), ZKStaffIconCollectionActivity.class);
            intent.putExtra("operate", "add");
            intent.putExtra("userpin", this.user_pin);
            if (getResources().getConfiguration().orientation == 2) {
                getActivity().startActivityForResult(intent, 999);
            } else {
                startActivityForResult(intent, 999);
            }
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (getActivity().getResources().getConfiguration().orientation != 2) {
            ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) Glide.with(getActivity()).load(ZKFilePath.IMAGE_PATH + "temp/" + this.user_pin + ".jpg").skipMemoryCache(true)).diskCacheStrategy(DiskCacheStrategy.NONE)).error((int) R.mipmap.ic_take_photo)).transform((Transformation<Bitmap>) new CircleTransform())).into(this.mIvTakePhoto);
            ((ZKStaffAddStepActivity) getActivity()).setUserPinReplace(this.user_pin);
        } else if (i == 999 && i2 == 1999) {
            if (Build.VERSION.SDK_INT >= 23) {
                ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) Glide.with(getContext()).load(ZKFilePath.PICTURE_PATH + this.user_pin + ".jpg").skipMemoryCache(true)).diskCacheStrategy(DiskCacheStrategy.NONE)).error((int) R.mipmap.ic_take_photo)).transform((Transformation<Bitmap>) new CircleTransform())).into(this.mIvTakePhoto);
                ((ZKStaffAddStepActivity) getActivity()).setUserPinReplace(this.user_pin);
            }
        } else if (Build.VERSION.SDK_INT >= 23) {
            showToast(getContext(), getString(R.string.zk_staff_icon_failed));
        }
    }

    public void refreshPhoto() {
        ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) Glide.with(getActivity()).load(ZKFilePath.PICTURE_PATH + this.user_pin + ".jpg").skipMemoryCache(true)).diskCacheStrategy(DiskCacheStrategy.NONE)).error((int) R.mipmap.ic_take_photo)).transform((Transformation<Bitmap>) new CircleTransform())).into(this.mIvTakePhoto);
        ((ZKStaffAddStepActivity) getActivity()).setUserPinReplace(this.user_pin);
    }
}
