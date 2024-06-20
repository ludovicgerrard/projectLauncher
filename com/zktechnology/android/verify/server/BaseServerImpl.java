package com.zktechnology.android.verify.server;

import android.content.Context;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.event.EventBusHelper;
import com.zktechnology.android.event.EventOpenMenu;
import com.zktechnology.android.event.EventShowNoSuperDialog;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZKEventLauncher;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.rs232.ZKRS232EncryptManager;
import com.zktechnology.android.rs485.RS485Manager;
import com.zktechnology.android.utils.AppUtils;
import com.zktechnology.android.utils.Common;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.StartAppByName;
import com.zktechnology.android.verify.bean.process.ZKMarkTypeBean;
import com.zktechnology.android.verify.bean.process.ZKVerConMarkBean;
import com.zktechnology.android.verify.bean.process.ZKVerViewBean;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.dao.IZKDao;
import com.zktechnology.android.verify.dialog.managment.ZKVerDlgMgt;
import com.zktechnology.android.verify.utils.ZKConstantConfig;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerOption;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zktechnology.android.wiegand.ZKWiegandManager;
import com.zkteco.android.core.model.ActionModel;
import com.zkteco.android.core.sdk.BtnWidgetManager;
import com.zkteco.android.db.DBConfig;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.db.orm.util.SpeakerHelper;
import com.zkteco.android.zkcore.utils.ZKPersiaCalendar;
import com.zkteco.zkinfraredservice.irpalm.ZKPalmService12;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public abstract class BaseServerImpl implements IBaseServerImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int REGISTER_CARD = 2;
    public static final int REGISTER_FACE = 3;
    public static final int REGISTER_FINGER = 1;
    public static final int REGISTER_PALM = 4;
    public static final int REGISTER_PW = 0;
    public static final String TAG = "BaseServerImpl";
    private static final String TTS_GOOGLE_ENGINE = "com.google.android.tts";
    /* access modifiers changed from: private */
    public static TextToSpeech mTextToSpeech;
    protected Context mContext = LauncherApplication.getLauncherApplicationContext();
    private IZKDao mDao;
    DataManager mDataManager;
    private EventOpenMenu mOpenMenuEvent = new EventOpenMenu(false);
    protected EventShowNoSuperDialog mShowNoSuperDialog = new EventShowNoSuperDialog();
    protected ZKVerOption zkVerOption = new ZKVerOption();

    public abstract void pictureBlack(String str, Date date);

    public BaseServerImpl() {
        initTTS();
    }

    /* access modifiers changed from: protected */
    public void setDB(IZKDao iZKDao) {
        this.mDao = iZKDao;
    }

    public ArrayList<Boolean> getRegisteredVerifyType(UserInfo userInfo) {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        arrayList.add(Boolean.valueOf(this.mDao.registerPassword(userInfo)));
        arrayList.add(Boolean.valueOf(this.mDao.registerFinger(userInfo)));
        arrayList.add(Boolean.valueOf(this.mDao.registerMainCard(userInfo)));
        arrayList.add(Boolean.valueOf(this.mDao.registerFace(userInfo)));
        arrayList.add(Boolean.valueOf(this.mDao.registerPalm(userInfo)));
        LogUtils.d(LogUtils.TAG_VERIFY, "验证方式登记状况：Finger<%s> PW<%s> Card<%s> Face<%s> Palm<%s>", arrayList.get(1), arrayList.get(0), arrayList.get(2), arrayList.get(3), arrayList.get(4));
        return arrayList;
    }

    public boolean checkVerifyTempRegisterState(int i) {
        UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
        int i2 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()];
        if (i2 == 1) {
            return this.mDao.registerPassword(userInfo);
        }
        if (i2 == 2) {
            return this.mDao.registerFinger(userInfo);
        }
        if (i2 == 3) {
            return this.mDao.registerMainCard(userInfo);
        }
        if (i2 == 4) {
            return this.mDao.registerFace(userInfo);
        }
        if (i2 != 5) {
            return false;
        }
        return this.mDao.registerPin(userInfo);
    }

    /* renamed from: com.zktechnology.android.verify.server.BaseServerImpl$2  reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
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
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PIN     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.zktechnology.android.verify.bean.process.ZKVerifyType r1 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PALM     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.BaseServerImpl.AnonymousClass2.<clinit>():void");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v19, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v27, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v41, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v45, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v53, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v55, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v56, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v58, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v60, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v66, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v100, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v101, resolved type: boolean} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x03a5, code lost:
        r14 = com.zktechnology.android.launcher.R.string.not_register_face_card;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x03e6, code lost:
        r2 = r3;
        r3 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0408, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x042b, code lost:
        r14 = com.zktechnology.android.launcher.R.string.not_register_password_face;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x046d, code lost:
        r2 = r3;
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x046f, code lost:
        r14 = com.zktechnology.android.launcher.R.string.not_register_finger;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x0491, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x04b1, code lost:
        r2 = r3;
        r3 = r8;
        r14 = com.zktechnology.android.launcher.R.string.not_register_face_finger;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x04b6, code lost:
        r2 = 0;
        r14 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x04b8, code lost:
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x04c5, code lost:
        if (r2 != false) goto L_0x04d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x04dc, code lost:
        r14 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:276:0x0688, code lost:
        r2 = r3;
        r3 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:299:0x0709, code lost:
        r2 = r3;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:300:0x070b, code lost:
        r14 = com.zktechnology.android.launcher.R.string.not_register_card;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:311:0x074c, code lost:
        r2 = r3;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:319:0x076d, code lost:
        r2 = 0;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:325:0x078f, code lost:
        r2 = r3;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:326:0x0791, code lost:
        r14 = com.zktechnology.android.launcher.R.string.not_register_password;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:332:0x07b3, code lost:
        r2 = r3;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:338:0x07d5, code lost:
        r2 = r3;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:342:0x07eb, code lost:
        if (r2 != false) goto L_0x07ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:343:0x07ed, code lost:
        r17 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:351:0x080e, code lost:
        r2 = r3;
        r3 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:352:0x0810, code lost:
        r14 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:361:0x0832, code lost:
        r14 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:370:0x0854, code lost:
        r14 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:379:0x0879, code lost:
        r2 = 0;
        r14 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:380:0x087b, code lost:
        r3 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:382:0x0888, code lost:
        if (r2 != false) goto L_0x07ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0051, code lost:
        r14 = -1;
        r3 = r3;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkNeedVerifyTypeIsRegistered(com.zkteco.android.db.orm.tna.UserInfo r21, java.util.ArrayList<java.lang.Boolean> r22, java.util.Date r23) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r2 = r22
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "checkNeedVerifyTypeIsRegistered: register.size() == "
            java.lang.StringBuilder r3 = r3.append(r4)
            int r4 = r22.size()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.log(r3)
            r3 = 0
            if (r1 != 0) goto L_0x0024
            return r3
        L_0x0024:
            com.zktechnology.android.verify.utils.ZKVerOption r4 = r0.zkVerOption
            android.content.Context r5 = r0.mContext
            int r4 = r4.getVerifyType(r5)
            r5 = 25
            r8 = 4
            r10 = 2131755237(0x7f1000e5, float:1.9141348E38)
            r11 = 2131755244(0x7f1000ec, float:1.9141362E38)
            r12 = 2131755239(0x7f1000e7, float:1.9141352E38)
            r13 = 2131755247(0x7f1000ef, float:1.9141368E38)
            r14 = 2131755235(0x7f1000e3, float:1.9141344E38)
            r15 = 2131755234(0x7f1000e2, float:1.9141342E38)
            r16 = 2131755243(0x7f1000eb, float:1.914136E38)
            r17 = 2131755238(0x7f1000e6, float:1.914135E38)
            r6 = -1
            r7 = 1
            if (r4 == r5) goto L_0x091b
            r5 = 3
            r9 = 2
            switch(r4) {
                case -1: goto L_0x088c;
                case 0: goto L_0x088c;
                case 1: goto L_0x087e;
                case 2: goto L_0x0879;
                case 3: goto L_0x0867;
                case 4: goto L_0x0857;
                case 5: goto L_0x0835;
                case 6: goto L_0x0813;
                case 7: goto L_0x07f1;
                case 8: goto L_0x07d9;
                case 9: goto L_0x0750;
                case 10: goto L_0x06cc;
                case 11: goto L_0x064c;
                case 12: goto L_0x04e5;
                case 13: goto L_0x0750;
                case 14: goto L_0x04c8;
                case 15: goto L_0x04bb;
                case 16: goto L_0x0430;
                case 17: goto L_0x03aa;
                case 18: goto L_0x0327;
                case 19: goto L_0x01be;
                case 20: goto L_0x0054;
                default: goto L_0x0050;
            }
        L_0x0050:
            r2 = r3
        L_0x0051:
            r14 = r6
            goto L_0x0934
        L_0x0054:
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x007a
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x007a
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x007a
            r8 = r7
            goto L_0x007b
        L_0x007a:
            r8 = r3
        L_0x007b:
            if (r8 == 0) goto L_0x007f
            goto L_0x04b6
        L_0x007f:
            java.lang.Object r9 = r2.get(r3)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x00ab
            java.lang.Object r9 = r2.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x00ab
            java.lang.Object r9 = r2.get(r5)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x00ab
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE
            int r3 = r2.getValue()
            goto L_0x0491
        L_0x00ab:
            java.lang.Object r9 = r2.get(r5)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x00d9
            java.lang.Object r9 = r2.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x00d9
            java.lang.Object r9 = r2.get(r3)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x00d9
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            r2 = r3
            r3 = r8
            goto L_0x0791
        L_0x00d9:
            java.lang.Object r9 = r2.get(r5)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x0105
            java.lang.Object r9 = r2.get(r3)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x0105
            java.lang.Object r9 = r2.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x0105
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            goto L_0x046d
        L_0x0105:
            java.lang.Object r9 = r2.get(r5)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x0133
            java.lang.Object r9 = r2.get(r3)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x0133
            java.lang.Object r9 = r2.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x0133
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            r2 = r3
            r3 = r8
            goto L_0x0854
        L_0x0133:
            java.lang.Object r9 = r2.get(r3)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x015f
            java.lang.Object r9 = r2.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x015f
            java.lang.Object r9 = r2.get(r5)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x015f
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            goto L_0x04b1
        L_0x015f:
            java.lang.Object r9 = r2.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x018d
            java.lang.Object r9 = r2.get(r5)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x018d
            java.lang.Object r9 = r2.get(r3)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x018d
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            r2 = r3
            r3 = r8
            goto L_0x042b
        L_0x018d:
            java.lang.Object r7 = r2.get(r7)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 != 0) goto L_0x04b6
            java.lang.Object r5 = r2.get(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x04b6
            java.lang.Object r2 = r2.get(r3)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x04b6
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            r2 = r3
            r3 = r8
            r14 = 2131755241(0x7f1000e9, float:1.9141356E38)
            goto L_0x0934
        L_0x01be:
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x01e4
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x01e4
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x01e4
            r8 = r7
            goto L_0x01e5
        L_0x01e4:
            r8 = r3
        L_0x01e5:
            if (r8 == 0) goto L_0x01e9
            goto L_0x04b6
        L_0x01e9:
            java.lang.Object r11 = r2.get(r9)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x0215
            java.lang.Object r11 = r2.get(r7)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x0215
            java.lang.Object r11 = r2.get(r5)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 != 0) goto L_0x0215
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE
            int r3 = r2.getValue()
            goto L_0x0491
        L_0x0215:
            java.lang.Object r11 = r2.get(r5)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x0243
            java.lang.Object r11 = r2.get(r7)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x0243
            java.lang.Object r11 = r2.get(r9)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 != 0) goto L_0x0243
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD
            int r3 = r2.getValue()
            r2 = r3
            r3 = r8
            goto L_0x070b
        L_0x0243:
            java.lang.Object r11 = r2.get(r5)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x026f
            java.lang.Object r11 = r2.get(r9)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x026f
            java.lang.Object r11 = r2.get(r7)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 != 0) goto L_0x026f
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            goto L_0x046d
        L_0x026f:
            java.lang.Object r11 = r2.get(r5)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x029d
            java.lang.Object r11 = r2.get(r9)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 != 0) goto L_0x029d
            java.lang.Object r11 = r2.get(r7)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 != 0) goto L_0x029d
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            r2 = r3
            r3 = r8
            goto L_0x0832
        L_0x029d:
            java.lang.Object r11 = r2.get(r9)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x02c9
            java.lang.Object r11 = r2.get(r7)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 != 0) goto L_0x02c9
            java.lang.Object r11 = r2.get(r5)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 != 0) goto L_0x02c9
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            goto L_0x04b1
        L_0x02c9:
            java.lang.Object r10 = r2.get(r7)
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x02f7
            java.lang.Object r10 = r2.get(r5)
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 != 0) goto L_0x02f7
            java.lang.Object r10 = r2.get(r9)
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 != 0) goto L_0x02f7
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE
            int r3 = r2.getValue()
            r2 = r3
            r3 = r8
            goto L_0x03a5
        L_0x02f7:
            java.lang.Object r7 = r2.get(r7)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 != 0) goto L_0x04b6
            java.lang.Object r5 = r2.get(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x04b6
            java.lang.Object r2 = r2.get(r9)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x04b6
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            r2 = 2131755240(0x7f1000e8, float:1.9141354E38)
            r14 = r2
            goto L_0x0491
        L_0x0327:
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0340
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0340
            goto L_0x0341
        L_0x0340:
            r7 = r3
        L_0x0341:
            if (r7 == 0) goto L_0x0345
            goto L_0x0879
        L_0x0345:
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0365
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x0365
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD
            int r3 = r2.getValue()
            goto L_0x0688
        L_0x0365:
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0385
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x0385
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE
            int r3 = r2.getValue()
            goto L_0x0408
        L_0x0385:
            java.lang.Object r5 = r2.get(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x0879
            java.lang.Object r2 = r2.get(r9)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x0879
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE
            int r3 = r2.getValue()
            r2 = r3
            r3 = r7
        L_0x03a5:
            r14 = 2131755236(0x7f1000e4, float:1.9141346E38)
            goto L_0x0934
        L_0x03aa:
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x03c3
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x03c3
            goto L_0x03c4
        L_0x03c3:
            r7 = r3
        L_0x03c4:
            if (r7 == 0) goto L_0x03c8
            goto L_0x0879
        L_0x03c8:
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x03ea
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x03ea
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
        L_0x03e6:
            r2 = r3
            r3 = r7
            goto L_0x0791
        L_0x03ea:
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x040b
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x040b
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE
            int r3 = r2.getValue()
        L_0x0408:
            r2 = r3
            goto L_0x087b
        L_0x040b:
            java.lang.Object r5 = r2.get(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x0879
            java.lang.Object r2 = r2.get(r3)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x0879
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            r2 = r3
            r3 = r7
        L_0x042b:
            r14 = 2131755246(0x7f1000ee, float:1.9141366E38)
            goto L_0x0934
        L_0x0430:
            java.lang.Object r8 = r2.get(r5)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x044a
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x044a
            r8 = r7
            goto L_0x044b
        L_0x044a:
            r8 = r3
        L_0x044b:
            if (r8 == 0) goto L_0x044f
            goto L_0x04b6
        L_0x044f:
            java.lang.Object r9 = r2.get(r5)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x0473
            java.lang.Object r9 = r2.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x0473
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
        L_0x046d:
            r2 = r3
            r3 = r8
        L_0x046f:
            r14 = r17
            goto L_0x0934
        L_0x0473:
            java.lang.Object r9 = r2.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x0493
            java.lang.Object r9 = r2.get(r5)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x0493
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FACE
            int r3 = r2.getValue()
        L_0x0491:
            r2 = r3
            goto L_0x04b8
        L_0x0493:
            java.lang.Object r5 = r2.get(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x04b6
            java.lang.Object r2 = r2.get(r7)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x04b6
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
        L_0x04b1:
            r2 = r3
            r3 = r8
            r14 = r10
            goto L_0x0934
        L_0x04b6:
            r2 = r3
            r14 = r6
        L_0x04b8:
            r3 = r8
            goto L_0x0934
        L_0x04bb:
            java.lang.Object r2 = r2.get(r5)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x04de
            goto L_0x04d4
        L_0x04c8:
            java.lang.Object r2 = r2.get(r7)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x04d6
        L_0x04d4:
            r14 = r6
            goto L_0x04de
        L_0x04d6:
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r3.getValue()
        L_0x04dc:
            r14 = r17
        L_0x04de:
            r19 = r3
            r3 = r2
            r2 = r19
            goto L_0x0934
        L_0x04e5:
            java.lang.Object r5 = r2.get(r7)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x050b
            java.lang.Object r5 = r2.get(r3)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x050b
            java.lang.Object r5 = r2.get(r9)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x050b
            r5 = r7
            goto L_0x050c
        L_0x050b:
            r5 = r3
        L_0x050c:
            if (r5 == 0) goto L_0x0510
            goto L_0x076d
        L_0x0510:
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x053c
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x053c
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x053c
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            goto L_0x07b3
        L_0x053c:
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0568
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0568
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x0568
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            goto L_0x078f
        L_0x0568:
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0594
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0594
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x0594
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD
            int r3 = r2.getValue()
            goto L_0x0709
        L_0x0594:
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x05c0
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x05c0
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x05c0
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            goto L_0x074c
        L_0x05c0:
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x05ec
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x05ec
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x05ec
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            goto L_0x07d5
        L_0x05ec:
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x061a
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x061a
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x061a
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            r2 = r3
            r3 = r5
            goto L_0x0810
        L_0x061a:
            java.lang.Object r7 = r2.get(r7)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 != 0) goto L_0x076d
            java.lang.Object r7 = r2.get(r3)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 != 0) goto L_0x076d
            java.lang.Object r2 = r2.get(r9)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x076d
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            r2 = 2131755248(0x7f1000f0, float:1.914137E38)
            r14 = r2
            r2 = r3
            r3 = r5
            goto L_0x0934
        L_0x064c:
            java.lang.Object r5 = r2.get(r3)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x0665
            java.lang.Object r5 = r2.get(r9)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x0665
            goto L_0x0666
        L_0x0665:
            r7 = r3
        L_0x0666:
            if (r7 == 0) goto L_0x066a
            goto L_0x0879
        L_0x066a:
            java.lang.Object r5 = r2.get(r3)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x068c
            java.lang.Object r5 = r2.get(r9)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x068c
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD
            int r3 = r2.getValue()
        L_0x0688:
            r2 = r3
            r3 = r7
            goto L_0x070b
        L_0x068c:
            java.lang.Object r5 = r2.get(r9)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x06ac
            java.lang.Object r5 = r2.get(r3)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x06ac
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            goto L_0x03e6
        L_0x06ac:
            java.lang.Object r5 = r2.get(r3)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x0879
            java.lang.Object r2 = r2.get(r9)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x0879
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
            goto L_0x080e
        L_0x06cc:
            java.lang.Object r5 = r2.get(r7)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x06e6
            java.lang.Object r5 = r2.get(r9)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x06e6
            r5 = r7
            goto L_0x06e7
        L_0x06e6:
            r5 = r3
        L_0x06e7:
            if (r5 == 0) goto L_0x06eb
            goto L_0x076d
        L_0x06eb:
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x070e
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x070e
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.CARD
            int r3 = r2.getValue()
        L_0x0709:
            r2 = r3
            r3 = r5
        L_0x070b:
            r14 = r15
            goto L_0x0934
        L_0x070e:
            java.lang.Object r8 = r2.get(r9)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x072e
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x072e
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
            goto L_0x07b3
        L_0x072e:
            java.lang.Object r7 = r2.get(r7)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 != 0) goto L_0x076d
            java.lang.Object r2 = r2.get(r9)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x076d
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
        L_0x074c:
            r2 = r3
            r3 = r5
            goto L_0x0832
        L_0x0750:
            java.lang.Object r5 = r2.get(r7)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x076a
            java.lang.Object r5 = r2.get(r3)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x076a
            r5 = r7
            goto L_0x076b
        L_0x076a:
            r5 = r3
        L_0x076b:
            if (r5 == 0) goto L_0x0771
        L_0x076d:
            r2 = r3
            r3 = r5
            goto L_0x0051
        L_0x0771:
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x0795
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x0795
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
        L_0x078f:
            r2 = r3
            r3 = r5
        L_0x0791:
            r14 = r16
            goto L_0x0934
        L_0x0795:
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x07b7
            java.lang.Object r8 = r2.get(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x07b7
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r2.getValue()
        L_0x07b3:
            r2 = r3
            r3 = r5
            goto L_0x046f
        L_0x07b7:
            java.lang.Object r8 = r2.get(r3)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x076d
            java.lang.Object r2 = r2.get(r7)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x076d
            com.zktechnology.android.verify.bean.process.ZKVerifyType r2 = com.zktechnology.android.verify.bean.process.ZKVerifyType.PASSWORD
            int r3 = r2.getValue()
        L_0x07d5:
            r2 = r3
            r3 = r5
            goto L_0x0854
        L_0x07d9:
            java.lang.Object r2 = r2.get(r7)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x07eb
            com.zktechnology.android.verify.bean.process.ZKVerifyType r3 = com.zktechnology.android.verify.bean.process.ZKVerifyType.FINGER
            int r3 = r3.getValue()
        L_0x07eb:
            if (r2 == 0) goto L_0x04dc
        L_0x07ed:
            r17 = r6
            goto L_0x04dc
        L_0x07f1:
            java.lang.Object r5 = r2.get(r3)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x080b
            java.lang.Object r2 = r2.get(r9)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x080a
            goto L_0x080b
        L_0x080a:
            r7 = r3
        L_0x080b:
            if (r7 == 0) goto L_0x080e
            r11 = r6
        L_0x080e:
            r2 = r3
            r3 = r7
        L_0x0810:
            r14 = r11
            goto L_0x0934
        L_0x0813:
            java.lang.Object r5 = r2.get(r7)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x082d
            java.lang.Object r2 = r2.get(r9)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x082c
            goto L_0x082d
        L_0x082c:
            r7 = r3
        L_0x082d:
            if (r7 == 0) goto L_0x0830
            r12 = r6
        L_0x0830:
            r2 = r3
            r3 = r7
        L_0x0832:
            r14 = r12
            goto L_0x0934
        L_0x0835:
            java.lang.Object r5 = r2.get(r7)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x084f
            java.lang.Object r2 = r2.get(r3)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x084e
            goto L_0x084f
        L_0x084e:
            r7 = r3
        L_0x084f:
            if (r7 == 0) goto L_0x0852
            r13 = r6
        L_0x0852:
            r2 = r3
            r3 = r7
        L_0x0854:
            r14 = r13
            goto L_0x0934
        L_0x0857:
            java.lang.Object r2 = r2.get(r9)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x0864
            r15 = r6
        L_0x0864:
            r14 = r15
            goto L_0x04de
        L_0x0867:
            java.lang.Object r2 = r2.get(r3)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x0875
            r16 = r6
        L_0x0875:
            r14 = r16
            goto L_0x04de
        L_0x0879:
            r2 = r3
            r14 = r6
        L_0x087b:
            r3 = r7
            goto L_0x0934
        L_0x087e:
            java.lang.Object r2 = r2.get(r7)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x04dc
            goto L_0x07ed
        L_0x088c:
            java.lang.Object r10 = r2.get(r3)
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 != 0) goto L_0x08cb
            java.lang.Object r10 = r2.get(r7)
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 != 0) goto L_0x08cb
            java.lang.Object r9 = r2.get(r9)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x08cb
            java.lang.Object r5 = r2.get(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x08cb
            java.lang.Object r2 = r2.get(r8)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x08c9
            goto L_0x08cb
        L_0x08c9:
            r2 = r3
            goto L_0x08cc
        L_0x08cb:
            r2 = r7
        L_0x08cc:
            com.zkteco.android.db.orm.manager.DataManager r5 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r8 = "FingerFunOn"
            int r5 = r5.getIntOption(r8, r3)
            com.zkteco.android.db.orm.manager.DataManager r8 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r9 = "hasFingerModule"
            int r8 = r8.getIntOption(r9, r3)
            com.zkteco.android.db.orm.manager.DataManager r9 = com.zktechnology.android.utils.DBManager.getInstance()
            java.lang.String r10 = "~RFCardOn"
            int r9 = r9.getIntOption(r10, r3)
            if (r2 != 0) goto L_0x04d4
            if (r5 != r7) goto L_0x08f6
            if (r8 != r7) goto L_0x08f6
            if (r9 != r7) goto L_0x08f6
            r10 = 2131755249(0x7f1000f1, float:1.9141372E38)
            goto L_0x08f7
        L_0x08f6:
            r10 = r6
        L_0x08f7:
            if (r5 == 0) goto L_0x08fb
            if (r8 != 0) goto L_0x0900
        L_0x08fb:
            if (r9 != r7) goto L_0x0900
            r10 = 2131755245(0x7f1000ed, float:1.9141364E38)
        L_0x0900:
            if (r5 != r7) goto L_0x090a
            if (r8 != r7) goto L_0x090a
            if (r9 != 0) goto L_0x090a
            r18 = 2131755241(0x7f1000e9, float:1.9141356E38)
            goto L_0x090c
        L_0x090a:
            r18 = r10
        L_0x090c:
            if (r5 == 0) goto L_0x0910
            if (r8 != 0) goto L_0x0917
        L_0x0910:
            if (r9 != 0) goto L_0x0917
            r14 = 2131755246(0x7f1000ee, float:1.9141366E38)
            goto L_0x04de
        L_0x0917:
            r14 = r18
            goto L_0x04de
        L_0x091b:
            java.lang.String r5 = "25 手掌"
            r0.log(r5)
            java.lang.Object r2 = r2.get(r8)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x092e
            r5 = r6
            goto L_0x0931
        L_0x092e:
            r5 = 2131755242(0x7f1000ea, float:1.9141358E38)
        L_0x0931:
            r14 = r5
            goto L_0x04de
        L_0x0934:
            java.lang.String r5 = "Verify"
            if (r3 != 0) goto L_0x094e
            if (r14 == r6) goto L_0x094e
            r0.showNotRegisteredDialog(r1, r14)
            com.zktechnology.android.push.acc.AccPush r1 = com.zktechnology.android.push.acc.AccPush.getInstance()
            r6 = 27
            r7 = r23
            r1.pushUnregisterEvent(r6, r2, r7)
            java.lang.String r1 = "所需验证方式未注册"
            com.zktechnology.android.utils.LogUtils.e(r5, r1)
            goto L_0x0953
        L_0x094e:
            java.lang.String r1 = "所需验证方式均已注册"
            com.zktechnology.android.utils.LogUtils.d(r5, r1)
        L_0x0953:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "验证方式："
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r2 = "  isRegister: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.log(r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.BaseServerImpl.checkNeedVerifyTypeIsRegistered(com.zkteco.android.db.orm.tna.UserInfo, java.util.ArrayList, java.util.Date):boolean");
    }

    private void showNotRegisteredDialog(UserInfo userInfo, int i) {
        log("showNotRegisteredDialog: " + this.mContext.getString(i));
        ZKRS232EncryptManager.getInstance().failedCmd();
        log("showNotRegisteredDialog: 1");
        RS485Manager.getInstance(this.mContext).failedCmd();
        log("showNotRegisteredDialog: 2");
        LogUtils.e(LogUtils.TAG_VERIFY, "showNotRegisteredDialog");
        playSoundTryAgain();
        log("showNotRegisteredDialog: 3");
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean(-1);
        log("showNotRegisteredDialog: 4");
        if (userInfo != null) {
            zKVerViewBean.setUiName(userInfo.getName());
            zKVerViewBean.setUiPin(userInfo.getUser_PIN());
            log("showNotRegisteredDialog: 5");
        }
        log("showNotRegisteredDialog: 6");
        String string = AppUtils.getString(i);
        if (i == R.string.not_register_palm) {
            zKVerViewBean.setUiType(83);
        }
        zKVerViewBean.setFailMsg(string);
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        log("showNotRegisteredDialog: 结束");
    }

    public void changeStateToUser() {
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_USER);
    }

    public void changeStateToWay() {
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAY);
    }

    public void changeStateToAction() {
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_ACTION);
    }

    public void changeStateToWait() {
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    public void changeStateToRecord() {
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_RECORD);
    }

    public void changeStateToRemoteAuth() {
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_REMOTE_AUTH);
    }

    public void changeStateRemoteAuthToDelay() {
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_DELAY);
    }

    /* access modifiers changed from: protected */
    public UserInfo getUserInfoByFingerprint(int i) {
        byte[] byteArray = ZKVerProcessPar.CON_MARK_BUNDLE.getByteArray(ZKVerConConst.BUNDLE_FP_TEMPLATE_BUFFER);
        if (byteArray == null) {
            return null;
        }
        UserInfo userInfoByFinger = this.mDao.getUserInfoByFinger(byteArray, i);
        if (userInfoByFinger != null) {
            ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_OTHER_VERIFY_PIN, userInfoByFinger.getUser_PIN());
        }
        return userInfoByFinger;
    }

    /* access modifiers changed from: protected */
    public UserInfo getUserInfoByPassword() {
        UserInfo userInfo = (UserInfo) ZKVerProcessPar.CON_MARK_BUNDLE.getSerializable(ZKVerConConst.BUNDLE_USER_INFO);
        if (userInfo == null) {
            return null;
        }
        String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_PASSWORD);
        if (this.mDao.checkStressPassword(string)) {
            return userInfo;
        }
        String password = userInfo.getPassword();
        if (TextUtils.isEmpty(password) || !password.equals(string)) {
            return null;
        }
        return userInfo;
    }

    /* access modifiers changed from: protected */
    public UserInfo getUserInfoByCard() {
        String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_CARD);
        if (TextUtils.isEmpty(string)) {
            LogUtils.e(LogUtils.TAG_VERIFY, "Error: Card Message empty!");
            return null;
        }
        UserInfo userInfoByMainCard = this.mDao.getUserInfoByMainCard(string);
        if (userInfoByMainCard != null) {
            ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_OTHER_VERIFY_PIN, userInfoByMainCard.getUser_PIN());
        }
        return userInfoByMainCard;
    }

    /* access modifiers changed from: protected */
    public UserInfo getUserInfoByPin() {
        return this.mDao.getUserInfoByPin(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_PIN));
    }

    /* access modifiers changed from: protected */
    public UserInfo getUserInfoByFace() {
        UserInfo userInfo;
        String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FACE_RECOGNIZE_PIN);
        if (TextUtils.isEmpty(string)) {
            userInfo = this.mDao.getUserInfoByFace(ZKVerProcessPar.CON_MARK_BUNDLE.getByteArray(ZKVerConConst.BUNDLE_FACE_FEATURE), ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FACE_WIDTH, 0), ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FACE_HEIGHT, 0));
        } else {
            userInfo = this.mDao.getUserInfoByPin(string);
        }
        if (userInfo != null) {
            ZKVerProcessPar.CON_MARK_BUNDLE.putString(ZKVerConConst.BUNDLE_OTHER_VERIFY_PIN, userInfo.getUser_PIN());
        }
        return userInfo;
    }

    /* access modifiers changed from: protected */
    public UserInfo getUserInfoByPalm() {
        String[] strArr = new String[1];
        if (ZKPalmService12.dbIdentify(ZKVerProcessPar.CON_MARK_BUNDLE.getByteArray(ZKVerConConst.BUNDLE_PALM_VERIFY), strArr) > ZKLauncher.pvMThreshold) {
            return this.mDao.getUserInfoByPalm(strArr[0]);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public UserInfo getMultiUserInfoByFinger(UserInfo userInfo) {
        if (ZKVerProcessPar.KEY_BOARD_1V1 && ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_RETRY) == 0 && ZKLauncher.sFPRetry != 0) {
            ZKVerDlgMgt.pop();
            changeStateToWait();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            FileLogUtils.writeTouchLog("setTTouchAction: getMultiUserInfoByFinger");
            if (2 == ZKLauncher.sCameraSystem) {
                pictureBlack((String) null, new Date());
            }
        }
        UserInfo userInfoByFingerprint = getUserInfoByFingerprint(ZKLauncher.mVThreshold);
        return (userInfoByFingerprint == null || userInfo == null || !userInfoByFingerprint.getUser_PIN().equals(userInfo.getUser_PIN())) ? userInfoByFingerprint : userInfo;
    }

    /* access modifiers changed from: protected */
    public UserInfo getMultiUserInfoByPin(UserInfo userInfo) {
        if (userInfo.getUser_PIN().equals(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_PIN))) {
            return userInfo;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public UserInfo getMultiUserInfoByPassword(UserInfo userInfo) {
        String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_PASSWORD);
        boolean checkStressPassword = this.mDao.checkStressPassword(string);
        if ((ZKVerProcessPar.CON_MARK_BEAN.getIntent() == 1 || !checkStressPassword) && !userInfo.getPassword().equals(string)) {
            return null;
        }
        return userInfo;
    }

    /* access modifiers changed from: protected */
    public UserInfo getMultiUserInfoByCard(UserInfo userInfo) {
        String main_Card = userInfo.getMain_Card();
        String string = ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_CARD);
        if (TextUtils.isEmpty(main_Card) || !main_Card.equals(string)) {
            return null;
        }
        return userInfo;
    }

    /* access modifiers changed from: protected */
    public UserInfo getMultiUserInfoByFace(UserInfo userInfo) {
        UserInfo userInfoByFace = getUserInfoByFace();
        if (userInfoByFace == null || !userInfoByFace.getUser_PIN().equals(userInfo.getUser_PIN())) {
            return null;
        }
        return userInfo;
    }

    /* access modifiers changed from: protected */
    public UserInfo getMultiUserInfoByPalm(UserInfo userInfo) {
        if (ZKPalmService12.dbVerify(ZKVerProcessPar.CON_MARK_BUNDLE.getByteArray(ZKVerConConst.BUNDLE_PALM_VERIFY), userInfo.getUser_PIN()) > ZKLauncher.pvVThreshold) {
            return userInfo;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void showFingerDialog() {
        sendRS232Continue();
        ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FINGER.getValue(), false));
        ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
        ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(10));
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    /* access modifiers changed from: protected */
    public void showFingerDialog(int i) {
        sendRS232Continue();
        ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FINGER.getValue(), false));
        ZKVerProcessPar.CON_MARK_BEAN.setVerState(i);
        ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
        ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(10));
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    /* access modifiers changed from: protected */
    public void showPinDialog() {
        ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PIN.getValue(), false));
        ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(20));
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    /* access modifiers changed from: protected */
    public void showPasswordDialog() {
        ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PASSWORD.getValue(), false));
        ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(30));
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    /* access modifiers changed from: protected */
    public void showCardDialog() {
        sendRS232Continue();
        ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.CARD.getValue(), false));
        ZKVerProcessPar.ACTION_BEAN.setBolRFidRead(true);
        ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(40));
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    /* access modifiers changed from: protected */
    public void showPalmDialog() {
        ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.PALM.getValue(), false));
        ZKVerProcessPar.ACTION_BEAN.setBolPalm(true);
        ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(80));
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    private void sendRS232Continue() {
        if (ZKVerProcessPar.VERIFY_SOURCE_TYPE == 3) {
            ZKRS232EncryptManager.getInstance().continueVerify();
        }
    }

    /* access modifiers changed from: protected */
    public void showFaceDialog() {
        ZKVerProcessPar.CON_MARK_BEAN.getVerifyTypeList().add(new ZKMarkTypeBean(ZKVerifyType.FACE.getValue(), false));
        ZKVerProcessPar.ACTION_BEAN.setBolFace(true);
        ZKVerDlgMgt.upDateTopUi(new ZKVerViewBean(50));
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    /* access modifiers changed from: protected */
    public void startVerifyFinger() {
        setMultiVerifyState();
        showFingerDialog();
    }

    /* access modifiers changed from: protected */
    public void startVerifyPin() {
        setMultiVerifyState();
        showPinDialog();
    }

    /* access modifiers changed from: protected */
    public void startVerifyPassword() {
        setMultiVerifyState();
        showPasswordDialog();
    }

    /* access modifiers changed from: protected */
    public void startVerifyCard() {
        setMultiVerifyState();
        showCardDialog();
    }

    /* access modifiers changed from: protected */
    public void startVerifyFace() {
        setMultiVerifyState();
        showFaceDialog();
    }

    /* access modifiers changed from: protected */
    public void startVerifyPalm() {
        setMultiVerifyState();
        showPalmDialog();
    }

    private void setMultiVerifyState() {
        ZKVerProcessPar.CON_MARK_BEAN.setVerState(2);
        ZKVerProcessPar.CON_MARK_BEAN.getLastType().setState(true);
    }

    /* access modifiers changed from: protected */
    public void showSelectVerifyTypeDialog() {
        ZKVerDlgMgt.push(new ZKVerViewBean(0));
        changeStateToWait();
    }

    /* access modifiers changed from: protected */
    public void showInputPinDialog() {
        ZKVerDlgMgt.push(new ZKVerViewBean(20));
        changeStateToWait();
    }

    private void showUserInvalidDialog(UserInfo userInfo) {
        ZKVerConMarkBean zKVerConMarkBean = ZKVerProcessPar.CON_MARK_BEAN;
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
        zKVerViewBean.setUiName(userInfo.getName());
        zKVerViewBean.setUiPin(userInfo.getUser_PIN());
        int i = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(zKVerConMarkBean.getLastType().getType()).ordinal()];
        if (i == 1) {
            zKVerViewBean.setUiType(33);
        } else if (i == 2) {
            zKVerViewBean.setUiType(13);
            zKVerViewBean.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
            zKVerViewBean.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
            zKVerViewBean.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
        } else if (i == 3) {
            zKVerViewBean.setUiType(43);
        } else if (i == 4) {
            zKVerViewBean.setUiType(53);
        } else if (i == 5) {
            zKVerViewBean.setUiType(23);
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
    }

    public void showUserFailedDialog(ZKVerViewBean zKVerViewBean, int i) {
        switch (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()]) {
            case 1:
                zKVerViewBean.setUiType(32);
                break;
            case 2:
                if (!ZKVerProcessPar.KEY_BOARD_1V1 || ZKLauncher.sFPRetry == 0 || ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_1V1_RETRY) != 0) {
                    ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(true);
                } else {
                    ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(false);
                }
                zKVerViewBean.setUiType(12);
                zKVerViewBean.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
                zKVerViewBean.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
                zKVerViewBean.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
                break;
            case 3:
                ZKVerProcessPar.ACTION_BEAN.setBolRFidRead(true);
                zKVerViewBean.setUiType(42);
                break;
            case 4:
                ZKVerProcessPar.ACTION_BEAN.setBolFace(true);
                zKVerViewBean.setUiType(52);
                break;
            case 5:
                zKVerViewBean.setUiType(22);
                break;
            case 6:
                ZKVerProcessPar.ACTION_BEAN.setBolPalm(true);
                zKVerViewBean.setUiType(82);
                break;
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        ZKWiegandManager.getInstance().wiegandOutFailedID();
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    public void showUserSuccessDialog(ZKVerViewBean zKVerViewBean, int i) {
        int i2 = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(i).ordinal()];
        if (i2 == 1) {
            zKVerViewBean.setUiType(31);
        } else if (i2 == 2) {
            zKVerViewBean.setUiType(11);
            FileLogUtils.writeStateLog("fingerprintImage Base SuccessDialog get BUNDLE_FP_BUFFER:" + (ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER) == null ? "== null" : "!= null"));
            zKVerViewBean.setUiFingerprint(ZKVerProcessPar.CON_MARK_BUNDLE.getString(ZKVerConConst.BUNDLE_FP_BUFFER));
            zKVerViewBean.setUiFPHeight(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_HEIGHT, 0));
            zKVerViewBean.setUiFPWidth(ZKVerProcessPar.CON_MARK_BUNDLE.getInt(ZKVerConConst.BUNDLE_FP_WIDTH, 0));
        } else if (i2 == 3) {
            zKVerViewBean.setUiType(41);
        } else if (i2 == 4) {
            zKVerViewBean.setUiType(51);
        } else if (i2 == 5) {
            zKVerViewBean.setUiType(21);
        }
        FileLogUtils.writeVerifySuccessLog("userPin->" + zKVerViewBean.getUiPin() + "; " + "userName->" + zKVerViewBean.getUiName() + ": ");
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        ZKVerProcessPar.cleanData(4);
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    public void disMissProcessDialog() {
        ZKEventLauncher.setProcessDialogVisibility(false);
    }

    /* access modifiers changed from: protected */
    public void resetVerifyProcess() {
        log("-------------------重置 验证流程 resetVerifyProcess: ");
        LogUtils.e(LogUtils.TAG_VERIFY, "Reset Verify Process!");
        ZKVerProcessPar.cleanBtnWidget();
        ZKVerProcessPar.cleanData(5);
        changeStateToWait();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
        FileLogUtils.writeTouchLog("setTTouchAction: resetVerifyProcess");
    }

    public void openApp() {
        if (isWidgetPressed()) {
            StartAppByName.doStartApplicationWithPackageName(this.mContext, getWidgetActionParams());
            changeStateToWait();
        }
    }

    public void openMenu() {
        EventBusHelper.post(this.mOpenMenuEvent);
        changeStateToWait();
    }

    /* access modifiers changed from: protected */
    public void disableVerifyType(ZKVerifyType zKVerifyType) {
        switch (AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[zKVerifyType.ordinal()]) {
            case 1:
                ZKVerProcessPar.ACTION_BEAN.setBolKeyboard(false);
                return;
            case 2:
                ZKVerProcessPar.ACTION_BEAN.setBolFingerprint(false);
                return;
            case 3:
                ZKVerProcessPar.ACTION_BEAN.setBolRFidRead(false);
                return;
            case 4:
                ZKVerProcessPar.ACTION_BEAN.setBolFace(false);
                return;
            case 5:
                ZKVerProcessPar.ACTION_BEAN.setBolKeyboard(false);
                return;
            case 6:
                ZKVerProcessPar.ACTION_BEAN.setBolPalm(false);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void playSoundTryAgain() {
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        language.hashCode();
        char c2 = 65535;
        switch (language.hashCode()) {
            case 3121:
                if (language.equals("ar")) {
                    c2 = 0;
                    break;
                }
                break;
            case 3241:
                if (language.equals("en")) {
                    c2 = 1;
                    break;
                }
                break;
            case 3246:
                if (language.equals("es")) {
                    c2 = 2;
                    break;
                }
                break;
            case 3259:
                if (language.equals("fa")) {
                    c2 = 3;
                    break;
                }
                break;
            case 3276:
                if (language.equals("fr")) {
                    c2 = 4;
                    break;
                }
                break;
            case 3365:
                if (language.equals(Common.GPIO_DIRCTION_IN)) {
                    c2 = 5;
                    break;
                }
                break;
            case 3383:
                if (language.equals("ja")) {
                    c2 = 6;
                    break;
                }
                break;
            case 3428:
                if (language.equals("ko")) {
                    c2 = 7;
                    break;
                }
                break;
            case 3588:
                if (language.equals("pt")) {
                    c2 = 8;
                    break;
                }
                break;
            case 3651:
                if (language.equals("ru")) {
                    c2 = 9;
                    break;
                }
                break;
            case 3700:
                if (language.equals("th")) {
                    c2 = 10;
                    break;
                }
                break;
            case 3710:
                if (language.equals("tr")) {
                    c2 = 11;
                    break;
                }
                break;
            case 3763:
                if (language.equals("vi")) {
                    c2 = 12;
                    break;
                }
                break;
            case 3886:
                if (language.equals("zh")) {
                    c2 = 13;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                SpeakerHelper.playSound(this.mContext, "4.m4a", true, "AR");
                return;
            case 1:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "EN");
                return;
            case 2:
                if ("MX".equals(locale.getCountry())) {
                    SpeakerHelper.playSound(this.mContext, "4.ogg", true, "ES-MX");
                    return;
                } else {
                    SpeakerHelper.playSound(this.mContext, "4.ogg", true, "ES");
                    return;
                }
            case 3:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "FA");
                return;
            case 4:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "FR");
                return;
            case 5:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "IN");
                return;
            case 6:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "JA");
                return;
            case 7:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "KO");
                return;
            case 8:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "PT");
                return;
            case 9:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "RU");
                return;
            case 10:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "TH");
                return;
            case 11:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "TR");
                return;
            case 12:
                SpeakerHelper.playSound(this.mContext, "4.ogg", true, "VI");
                return;
            case 13:
                if ("HK".equals(locale.getCountry())) {
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

    private void initTTS() {
        this.mDataManager = DBManager.getInstance();
        TextToSpeech textToSpeech = mTextToSpeech;
        if (textToSpeech != null) {
            textToSpeech.stop();
            mTextToSpeech.shutdown();
            mTextToSpeech = null;
        }
        if (mTextToSpeech == null) {
            mTextToSpeech = new TextToSpeech(this.mContext, new TTSListener(), TTS_GOOGLE_ENGINE);
        }
    }

    public void playTTS(String str) {
        TextToSpeech textToSpeech = mTextToSpeech;
        if (textToSpeech == null) {
            mTextToSpeech = new TextToSpeech(this.mContext, new TTSListener(str) {
                public void onInit(int i) {
                    super.onInit(i);
                    if (i == 0) {
                        BaseServerImpl.mTextToSpeech.setSpeechRate(1.0f);
                        BaseServerImpl.mTextToSpeech.speak(this.str, 0, (HashMap) null);
                    }
                }
            }, TTS_GOOGLE_ENGINE);
            return;
        }
        textToSpeech.setSpeechRate(1.0f);
        mTextToSpeech.speak(str, 0, (HashMap) null);
    }

    private class TTSListener implements TextToSpeech.OnInitListener {
        String str;

        public TTSListener() {
        }

        public TTSListener(String str2) {
            this.str = str2;
        }

        public void onInit(int i) {
            if (i == 0) {
                List<TextToSpeech.EngineInfo> engines = BaseServerImpl.mTextToSpeech.getEngines();
                for (int i2 = 0; i2 < engines.size(); i2++) {
                    if (BaseServerImpl.TTS_GOOGLE_ENGINE.equals(engines.get(i2).name)) {
                        BaseServerImpl.this.checkLanguage(BaseServerImpl.this.mContext.getResources().getConfiguration().locale);
                        return;
                    }
                    if (i2 == engines.size() - 1) {
                        if (BaseServerImpl.this.mDataManager.getIntOption(DBConfig.TTS_ON, 0) != 0) {
                            BaseServerImpl.this.mDataManager.setIntOption(DBConfig.TTS_ON, 0);
                        }
                        if (BaseServerImpl.mTextToSpeech != null) {
                            BaseServerImpl.mTextToSpeech.stop();
                            BaseServerImpl.mTextToSpeech.shutdown();
                            TextToSpeech unused = BaseServerImpl.mTextToSpeech = null;
                        }
                    }
                }
                return;
            }
            if (BaseServerImpl.this.mDataManager.getIntOption(DBConfig.TTS_ON, 0) != 0) {
                BaseServerImpl.this.mDataManager.setIntOption(DBConfig.TTS_ON, 0);
            }
            if (BaseServerImpl.mTextToSpeech != null) {
                BaseServerImpl.mTextToSpeech.stop();
                BaseServerImpl.mTextToSpeech.shutdown();
                TextToSpeech unused2 = BaseServerImpl.mTextToSpeech = null;
            }
        }
    }

    /* access modifiers changed from: private */
    public void checkLanguage(Locale locale) {
        mTextToSpeech.setLanguage(locale);
        if ((mTextToSpeech.isLanguageAvailable(locale) < 0 || mTextToSpeech.getVoice().getFeatures().contains("notInstalled")) && this.mDataManager.getIntOption(DBConfig.TTS_ON, 0) != 0) {
            this.mDataManager.setIntOption(DBConfig.TTS_ON, 0);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00fb, code lost:
        if (r6.equals("ar") == false) goto L_0x0054;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void playSoundOk(java.lang.String r6) {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            java.util.Locale r0 = r0.locale
            com.zkteco.android.db.orm.manager.DataManager r1 = r5.mDataManager
            java.lang.String r2 = "TTSOn"
            r3 = 0
            int r1 = r1.getIntOption(r2, r3)
            if (r1 == 0) goto L_0x003c
            android.speech.tts.TextToSpeech r1 = mTextToSpeech
            if (r1 == 0) goto L_0x003c
            android.speech.tts.Voice r1 = r1.getVoice()
            if (r1 == 0) goto L_0x0037
            java.lang.String r4 = r0.toString()
            java.util.Locale r1 = r1.getLocale()
            java.lang.String r1 = r1.toString()
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L_0x003c
            r5.checkLanguage(r0)
            goto L_0x003c
        L_0x0037:
            com.zkteco.android.db.orm.manager.DataManager r1 = r5.mDataManager
            r1.setIntOption(r2, r3)
        L_0x003c:
            com.zkteco.android.db.orm.manager.DataManager r1 = r5.mDataManager
            int r1 = r1.getIntOption(r2, r3)
            if (r1 != 0) goto L_0x01aa
            java.lang.String r6 = r0.getLanguage()
            r6.hashCode()
            r1 = -1
            int r2 = r6.hashCode()
            r4 = 1
            switch(r2) {
                case 3121: goto L_0x00f5;
                case 3241: goto L_0x00e9;
                case 3246: goto L_0x00dd;
                case 3259: goto L_0x00d1;
                case 3276: goto L_0x00c6;
                case 3365: goto L_0x00bb;
                case 3383: goto L_0x00b0;
                case 3428: goto L_0x00a5;
                case 3588: goto L_0x0098;
                case 3651: goto L_0x008b;
                case 3700: goto L_0x007e;
                case 3710: goto L_0x0071;
                case 3763: goto L_0x0064;
                case 3886: goto L_0x0057;
                default: goto L_0x0054;
            }
        L_0x0054:
            r3 = r1
            goto L_0x00ff
        L_0x0057:
            java.lang.String r2 = "zh"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x0060
            goto L_0x0054
        L_0x0060:
            r3 = 13
            goto L_0x00ff
        L_0x0064:
            java.lang.String r2 = "vi"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x006d
            goto L_0x0054
        L_0x006d:
            r3 = 12
            goto L_0x00ff
        L_0x0071:
            java.lang.String r2 = "tr"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x007a
            goto L_0x0054
        L_0x007a:
            r3 = 11
            goto L_0x00ff
        L_0x007e:
            java.lang.String r2 = "th"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x0087
            goto L_0x0054
        L_0x0087:
            r3 = 10
            goto L_0x00ff
        L_0x008b:
            java.lang.String r2 = "ru"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x0094
            goto L_0x0054
        L_0x0094:
            r3 = 9
            goto L_0x00ff
        L_0x0098:
            java.lang.String r2 = "pt"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00a1
            goto L_0x0054
        L_0x00a1:
            r3 = 8
            goto L_0x00ff
        L_0x00a5:
            java.lang.String r2 = "ko"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00ae
            goto L_0x0054
        L_0x00ae:
            r3 = 7
            goto L_0x00ff
        L_0x00b0:
            java.lang.String r2 = "ja"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00b9
            goto L_0x0054
        L_0x00b9:
            r3 = 6
            goto L_0x00ff
        L_0x00bb:
            java.lang.String r2 = "in"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00c4
            goto L_0x0054
        L_0x00c4:
            r3 = 5
            goto L_0x00ff
        L_0x00c6:
            java.lang.String r2 = "fr"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00cf
            goto L_0x0054
        L_0x00cf:
            r3 = 4
            goto L_0x00ff
        L_0x00d1:
            java.lang.String r2 = "fa"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00db
            goto L_0x0054
        L_0x00db:
            r3 = 3
            goto L_0x00ff
        L_0x00dd:
            java.lang.String r2 = "es"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00e7
            goto L_0x0054
        L_0x00e7:
            r3 = 2
            goto L_0x00ff
        L_0x00e9:
            java.lang.String r2 = "en"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00f3
            goto L_0x0054
        L_0x00f3:
            r3 = r4
            goto L_0x00ff
        L_0x00f5:
            java.lang.String r2 = "ar"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L_0x00ff
            goto L_0x0054
        L_0x00ff:
            java.lang.String r6 = "EN"
            java.lang.String r1 = "0.ogg"
            switch(r3) {
                case 0: goto L_0x01a0;
                case 1: goto L_0x019a;
                case 2: goto L_0x017e;
                case 3: goto L_0x0176;
                case 4: goto L_0x016e;
                case 5: goto L_0x0166;
                case 6: goto L_0x015e;
                case 7: goto L_0x0156;
                case 8: goto L_0x014e;
                case 9: goto L_0x0146;
                case 10: goto L_0x013d;
                case 11: goto L_0x0134;
                case 12: goto L_0x012b;
                case 13: goto L_0x010d;
                default: goto L_0x0106;
            }
        L_0x0106:
            android.content.Context r0 = r5.mContext
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r0, r1, r4, r6)
            goto L_0x01ad
        L_0x010d:
            java.lang.String r6 = r0.getCountry()
            java.lang.String r0 = "HK"
            boolean r6 = r0.equals(r6)
            if (r6 == 0) goto L_0x0122
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "CH-HK"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x0122:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "CH"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x012b:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "VI"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x0134:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "TR"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x013d:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "TH"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x0146:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "RU"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x014e:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "PT"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x0156:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "KO"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x015e:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "JA"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x0166:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "IN"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x016e:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "FR"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x0176:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "FA"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x017e:
            java.lang.String r6 = r0.getCountry()
            java.lang.String r0 = "MX"
            boolean r6 = r0.equals(r6)
            if (r6 == 0) goto L_0x0192
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "ES-MX"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x0192:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "ES"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r1, r4, r0)
            goto L_0x01ad
        L_0x019a:
            android.content.Context r0 = r5.mContext
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r0, r1, r4, r6)
            goto L_0x01ad
        L_0x01a0:
            android.content.Context r6 = r5.mContext
            java.lang.String r0 = "0.m4a"
            java.lang.String r1 = "AR"
            com.zkteco.android.db.orm.util.SpeakerHelper.playSound(r6, r0, r4, r1)
            goto L_0x01ad
        L_0x01aa:
            r5.playTTS(r6)
        L_0x01ad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.server.BaseServerImpl.playSoundOk(java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    public boolean isWidgetPressed() {
        boolean z = new BtnWidgetManager(this.mContext).getPressedWidgetId() != -1;
        if (z) {
            LogUtils.e(LogUtils.TAG_VERIFY, "BtnWidget is pressed!");
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public int getWidgetActionType() {
        BtnWidgetManager btnWidgetManager = new BtnWidgetManager(this.mContext);
        ActionModel action = btnWidgetManager.getBtnWidgets(btnWidgetManager.getPressedWidgetId()).get(btnWidgetManager.getPressedPosition()).getAction();
        int actionType = action.getActionType();
        LogUtils.d(LogUtils.TAG_VERIFY, "BtnWidget: actionModel = %s,ActionType = %s", action, Integer.valueOf(actionType));
        return actionType;
    }

    /* access modifiers changed from: protected */
    public String getWidgetActionParams() {
        BtnWidgetManager btnWidgetManager = new BtnWidgetManager(this.mContext);
        String actionParams = btnWidgetManager.getBtnWidgets(btnWidgetManager.getPressedWidgetId()).get(btnWidgetManager.getPressedPosition()).getAction().getActionParams();
        LogUtils.d(LogUtils.TAG_VERIFY, "BtnWidget: params = " + actionParams);
        return actionParams;
    }

    /* access modifiers changed from: protected */
    public String getVerifyTime() {
        return getVerifyTime(new Date());
    }

    /* access modifiers changed from: protected */
    public String getVerifyTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date).replace(" ", ExifInterface.GPS_DIRECTION_TRUE);
    }

    public String getRecordTime(SimpleDateFormat simpleDateFormat, Date date) {
        String str;
        String str2;
        String str3 = ZKLauncher.sDateFmt;
        str3.hashCode();
        char c2 = 65535;
        switch (str3.hashCode()) {
            case 48:
                if (str3.equals("0")) {
                    c2 = 0;
                    break;
                }
                break;
            case 49:
                if (str3.equals("1")) {
                    c2 = 1;
                    break;
                }
                break;
            case 50:
                if (str3.equals("2")) {
                    c2 = 2;
                    break;
                }
                break;
            case 51:
                if (str3.equals(ExifInterface.GPS_MEASUREMENT_3D)) {
                    c2 = 3;
                    break;
                }
                break;
            case 52:
                if (str3.equals("4")) {
                    c2 = 4;
                    break;
                }
                break;
            case 53:
                if (str3.equals("5")) {
                    c2 = 5;
                    break;
                }
                break;
            case 54:
                if (str3.equals("6")) {
                    c2 = 6;
                    break;
                }
                break;
            case 55:
                if (str3.equals("7")) {
                    c2 = 7;
                    break;
                }
                break;
            case 56:
                if (str3.equals("8")) {
                    c2 = 8;
                    break;
                }
                break;
            case 57:
                if (str3.equals("9")) {
                    c2 = 9;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                str = ZKConstantConfig.DATE_FM_0;
                break;
            case 1:
                str = ZKConstantConfig.DATE_FM_1;
                break;
            case 2:
                str = ZKConstantConfig.DATE_FM_2;
                break;
            case 3:
                str = ZKConstantConfig.DATE_FM_3;
                break;
            case 4:
                str = ZKConstantConfig.DATE_FM_4;
                break;
            case 5:
                str = ZKConstantConfig.DATE_FM_5;
                break;
            case 6:
                str = ZKConstantConfig.DATE_FM_6;
                break;
            case 7:
                str = ZKConstantConfig.DATE_FM_7;
                break;
            case 8:
                str = ZKConstantConfig.DATE_FM_8;
                break;
            case 9:
                str = "yyyy-MM-dd";
                break;
            default:
                str = "";
                break;
        }
        try {
            if (Settings.System.getInt(this.mContext.getContentResolver(), "time_12_24") == 24) {
                str2 = str + ZKConstantConfig.DATE_TM_0;
            } else {
                str2 = str + ZKConstantConfig.DATE_TM_1;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            str2 = str + ZKConstantConfig.DATE_TM_0;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str2, Locale.US);
        if (!"1".equals(ZKLauncher.sHejiraCalendar)) {
            return simpleDateFormat2.format(date);
        }
        if ("1".equals(ZKLauncher.sCalenderType)) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            return simpleDateFormat2.format(ZKPersiaCalendar.GregorianToPersianCalendar(instance).getTime());
        } else if (!"2".equals(ZKLauncher.sCalenderType)) {
            return simpleDateFormat2.format(date);
        } else {
            Calendar instance2 = Calendar.getInstance();
            instance2.setTime(date);
            return simpleDateFormat2.format(ZKPersiaCalendar.GregorianToIslamicCalendar(instance2).getTime());
        }
    }

    /* access modifiers changed from: protected */
    public void showWiegandFailedDialog() {
        showWiegandFailedDialog(false);
    }

    public void showWiegandSuccessDialog(UserInfo userInfo, boolean z, String str, boolean z2) {
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
        int i = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(ZKVerProcessPar.CON_MARK_BEAN.getLastType().getType()).ordinal()];
        if (i == 3) {
            zKVerViewBean.setUiType(60);
        } else if (i != 5) {
            zKVerViewBean.setUiType(60);
        } else {
            zKVerViewBean.setUiType(62);
        }
        zKVerViewBean.setUiName(userInfo.getName());
        zKVerViewBean.setUiPin(userInfo.getUser_PIN());
        zKVerViewBean.setUiSignInTime(str);
        zKVerViewBean.setUiTFPhoto(z2);
        if (z) {
            zKVerViewBean.setUiSpecialState(AppUtils.getString(R.string.server_special_state));
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        ZKVerProcessPar.cleanData(6);
        ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
        FileLogUtils.writeTouchLog("setFTouchAction: showWiegandSuccessDialog");
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    /* access modifiers changed from: protected */
    public void showWiegandFailedDialog(boolean z) {
        ZKVerViewBean zKVerViewBean = new ZKVerViewBean();
        int i = AnonymousClass2.$SwitchMap$com$zktechnology$android$verify$bean$process$ZKVerifyType[ZKVerifyType.fromInteger(ZKVerProcessPar.CON_MARK_BEAN.getLastType().getType()).ordinal()];
        if (i == 2) {
            zKVerViewBean.setUiType(12);
        } else if (i == 3) {
            zKVerViewBean.setUiType(61);
        } else if (i != 5) {
            zKVerViewBean.setUiType(61);
        } else {
            zKVerViewBean.setUiType(63);
        }
        if (z && ZKLauncher.sLoopDeleteLog == 0) {
            zKVerViewBean.setUiAttFull(AppUtils.getString(R.string.server_att_full));
        }
        ZKVerDlgMgt.upDateTopUi(zKVerViewBean);
        ZKVerProcessPar.cleanData(7);
        ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
        FileLogUtils.writeTouchLog("setFTouchAction: showWiegandFailedDialog");
        ZKVerController.getInstance().changeState(ZKVerConState.STATE_WAIT);
    }

    public void handleException(Exception exc) {
        StackTraceElement[] stackTrace = exc.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTrace) {
            sb.append(stackTraceElement.toString()).append("\n");
        }
        FileLogUtils.writeVerifyExceptionLog(exc.toString());
        FileLogUtils.writeVerifyExceptionLog(sb.toString());
        resetVerifyProcess();
    }

    public void log(String str) {
        LogUtils.verifyLog(str);
    }
}
