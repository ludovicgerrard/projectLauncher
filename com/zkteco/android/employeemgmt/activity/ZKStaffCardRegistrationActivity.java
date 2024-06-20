package com.zkteco.android.employeemgmt.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.zktechnology.android.helper.CardServiceHelper;
import com.zktechnology.android.helper.McuServiceHelper;
import com.zktechnology.android.helper.OnCardReadListener;
import com.zktechnology.android.helper.OnMcuReadListener;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.receiver.ZkNfcReceiver;
import com.zktechnology.android.rs485.RS485Helper;
import com.zktechnology.android.rs485.RS485Manager;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.HexUtils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.wiegand.WiegandUtil;
import com.zktechnology.android.wiegand.ZKWiegandManager;
import com.zktechnology.android.wiegand.bean.WiegandInData;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.ZKStaffCardRegistrationActivity;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import com.zkteco.edk.card.lib.ICardReaderListener;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZKStaffCardRegistrationActivity extends ZKStaffBaseActivity implements ICardReaderListener {
    private static final long INTERVAL_CARD = 1000;
    private static final int RS485TYPE = 1;
    private static final int SILTYPE = 0;
    /* access modifiers changed from: private */
    public static final String TAG = "ZKStaffCardRegistrationActivity";
    private float Volume;
    private AssetFileDescriptor afd;
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            String str = (String) message.obj;
            ZKStaffCardRegistrationActivity.this.tv_null.setVisibility(8);
            try {
                List query = new UserInfo().getQueryBuilder().where().eq("Main_Card", str).query();
                if (query == null || query.size() <= 0) {
                    boolean unused = ZKStaffCardRegistrationActivity.this.isCardChange = true;
                    ZKStaffCardRegistrationActivity.this.tv_show.setText(String.format("%s%s", new Object[]{ZKStaffCardRegistrationActivity.this.getString(R.string.zk_staff_card_number), str}));
                    String unused2 = ZKStaffCardRegistrationActivity.this.strCardNum = str;
                    ZKStaffCardRegistrationActivity.this.tv_show.setVisibility(0);
                    if (i == 1) {
                        RS485Manager.getInstance(ZKStaffCardRegistrationActivity.this.mContext).successCmd();
                        return;
                    }
                    return;
                }
                ZKStaffCardRegistrationActivity.this.tv_show.setVisibility(0);
                ZKStaffCardRegistrationActivity.this.tv_show.setText(R.string.zk_card_registered);
                if (i == 1) {
                    RS485Manager.getInstance(ZKStaffCardRegistrationActivity.this.mContext).failedCmd();
                }
                ZKStaffCardRegistrationActivity zKStaffCardRegistrationActivity = ZKStaffCardRegistrationActivity.this;
                int unused3 = zKStaffCardRegistrationActivity.retry = zKStaffCardRegistrationActivity.retry - 1;
                if (ZKStaffCardRegistrationActivity.this.retry < 0) {
                    String unused4 = ZKStaffCardRegistrationActivity.this.remoteEnrollResult = "4";
                    ZKStaffCardRegistrationActivity.this.finish();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    private Intent intent;
    /* access modifiers changed from: private */
    public boolean isCardChange = false;
    private boolean isHome = false;
    private boolean isRemoteEnroll = false;
    private long mCardCurrentTime = 0;
    /* access modifiers changed from: private */
    public Disposable mDisposable;
    private NfcAdapter mNfcAdapter;
    private final BroadcastReceiver mNfcReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra(ZkNfcReceiver.EXTRA_KEY);
            if (!TextUtils.isEmpty(stringExtra)) {
                ZKStaffCardRegistrationActivity.this.onCardRead(stringExtra);
            }
        }
    };
    private final OnCardReadListener mOnCardReadListener = new OnCardReadListener() {
        public final void onCardRead(String str) {
            ZKStaffCardRegistrationActivity.this.onCardRead(str);
        }
    };
    private final OnMcuReadListener mOnMcuReadListener = new OnMcuReadListener() {
        public void onRs232Read(byte[] bArr) {
        }

        public void onRs485Read(byte[] bArr) {
            Disposable unused = ZKStaffCardRegistrationActivity.this.mDisposable = Flowable.just(bArr).filter($$Lambda$ZKStaffCardRegistrationActivity$2$jPku6gznYW8rVP_IugehfT2iZjw.INSTANCE).map($$Lambda$ZKStaffCardRegistrationActivity$2$Q40vUvsYnlkGmFUTsqD0DPn4oMI.INSTANCE).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation()).subscribe(new Consumer() {
                public final void accept(Object obj) {
                    ZKStaffCardRegistrationActivity.AnonymousClass2.this.lambda$onRs485Read$2$ZKStaffCardRegistrationActivity$2((String) obj);
                }
            });
        }

        static /* synthetic */ boolean lambda$onRs485Read$0(byte[] bArr) throws Exception {
            return bArr != null && bArr.length > 0;
        }

        static /* synthetic */ String lambda$onRs485Read$1(byte[] bArr) throws Exception {
            if (bArr[6] != 2) {
                return null;
            }
            int length = bArr.length - 11;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 9, bArr2, 0, bArr.length - 11);
            if (length > 4) {
                return RS485Helper.cardNumber(bArr2, 0, length);
            }
            String bytes2HexString = HexUtils.bytes2HexString(bArr2);
            if (DBManager.getInstance().getIntOption("~CardByteRevert", 0) == 1) {
                HexUtils.reserveByte(bArr2);
                bytes2HexString = HexUtils.bytes2HexString(bArr2);
            }
            return String.valueOf(Long.parseLong(bytes2HexString, 16));
        }

        public /* synthetic */ void lambda$onRs485Read$2$ZKStaffCardRegistrationActivity$2(String str) throws Exception {
            if (str != null) {
                ZKStaffCardRegistrationActivity.this.registrationCard(1, str);
            } else {
                RS485Manager.getInstance(ZKStaffCardRegistrationActivity.this.mContext).failedCmd();
            }
        }

        public void onWiegandRead(String str) {
            ZKStaffCardRegistrationActivity.this.onWiegandIn(str);
        }
    };
    private PendingIntent mPendingIntent;
    private final ExecutorService mSingleService = Executors.newSingleThreadExecutor();
    SoundPool pool;
    /* access modifiers changed from: private */
    public String remoteEnrollResult = "6";
    /* access modifiers changed from: private */
    public int retry;
    /* access modifiers changed from: private */
    public String strCardNum;
    /* access modifiers changed from: private */
    public TextView tv_null;
    /* access modifiers changed from: private */
    public TextView tv_show;
    private UserInfo userInfo;

    static /* synthetic */ void lambda$initToolBar$1(View view) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_edit_card);
        initToolBar();
        this.intent = getIntent();
        initView();
        initData();
        AudioManager audioManager = (AudioManager) getSystemService("audio");
        this.Volume = ((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3));
        try {
            this.afd = getAssets().openFd("beep.ogg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.pool = new SoundPool(1, 3, 0);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled()) {
                enable(this.mNfcAdapter);
            }
            this.mNfcAdapter.enableForegroundDispatch(this, this.mPendingIntent, (IntentFilter[]) null, (String[][]) null);
        }
    }

    private void initData() {
        CardServiceHelper.getInstance().addOnCardReadListener(this.mOnCardReadListener);
        McuServiceHelper.getInstance().addOnMcuReadListener(this.mOnMcuReadListener);
        initNfc();
    }

    private void initNfc() {
        NfcAdapter defaultAdapter = ((NfcManager) getSystemService("nfc")).getDefaultAdapter();
        this.mNfcAdapter = defaultAdapter;
        if (defaultAdapter != null) {
            this.mPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, ZkNfcReceiver.class).addFlags(268435456), 134217728);
        }
        registerReceiver(this.mNfcReceiver, new IntentFilter(ZkNfcReceiver.ACTION_NFC_CARD));
    }

    private void initView() {
        this.tv_null = (TextView) findViewById(R.id.tv_card_null);
        this.tv_show = (TextView) findViewById(R.id.tv_card_show);
        String stringExtra = this.intent.getStringExtra("userPin");
        this.retry = this.intent.getIntExtra("retry", Integer.MAX_VALUE);
        try {
            if (TextUtils.isEmpty(stringExtra)) {
                this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(this.intent.getLongExtra("userInfo_id", 0)));
            } else {
                this.isRemoteEnroll = true;
                this.userInfo = (UserInfo) new UserInfo().getQueryBuilder().where().eq("User_PIN", stringExtra).queryForFirst();
            }
            UserInfo userInfo2 = this.userInfo;
            if (userInfo2 == null) {
                finish();
            } else if (userInfo2.getMain_Card() != null && !this.userInfo.getMain_Card().equals("") && !this.userInfo.getMain_Card().equals("0")) {
                this.tv_null.setVisibility(8);
                this.tv_show.setVisibility(0);
                this.tv_show.setText(String.format("%s%s", new Object[]{getString(R.string.zk_staff_card_number), this.userInfo.getMain_Card()}));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.cardToolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffCardRegistrationActivity.this.lambda$initToolBar$0$ZKStaffCardRegistrationActivity(view);
            }
        }, getString(R.string.zk_staff_card_title));
        if (getResources().getConfiguration().orientation == 2) {
            zKToolbar.setRightView(getString(R.string.zk_staff_save), (View.OnClickListener) $$Lambda$ZKStaffCardRegistrationActivity$eFTDxhQ2vQp_LSZyaHd1fhsxTQg.INSTANCE);
            return;
        }
        zKToolbar.setRightView();
        ((Button) findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ZKStaffCardRegistrationActivity.this.lambda$initToolBar$2$ZKStaffCardRegistrationActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initToolBar$0$ZKStaffCardRegistrationActivity(View view) {
        finish();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x009b, code lost:
        if (r1 != 0) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r8.convertPushFree(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ad, code lost:
        if (r1 != 0) goto L_0x009d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b5 A[SYNTHETIC, Splitter:B:31:0x00b5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void lambda$initToolBar$2$ZKStaffCardRegistrationActivity(android.view.View r8) {
        /*
            r7 = this;
            java.lang.String r8 = r7.strCardNum
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x000c
            java.lang.String r8 = "0"
            r7.remoteEnrollResult = r8
        L_0x000c:
            boolean r8 = r7.isCardChange
            if (r8 == 0) goto L_0x00bd
            boolean r8 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.isFirstPush     // Catch:{ SQLException -> 0x00b9 }
            r0 = 0
            if (r8 == 0) goto L_0x0020
            com.zkteco.android.employeemgmt.activity.ZkAddUserActivity$PushUserEvent r8 = com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.PushUserEvent.getInstance()     // Catch:{ SQLException -> 0x00b9 }
            com.zkteco.android.db.orm.tna.UserInfo r1 = r7.userInfo     // Catch:{ SQLException -> 0x00b9 }
            r8.pushUser(r7, r1)     // Catch:{ SQLException -> 0x00b9 }
            com.zkteco.android.employeemgmt.activity.ZkAddUserActivity.isFirstPush = r0     // Catch:{ SQLException -> 0x00b9 }
        L_0x0020:
            com.zkteco.android.db.orm.tna.UserInfo r8 = new com.zkteco.android.db.orm.tna.UserInfo     // Catch:{ SQLException -> 0x00b9 }
            r8.<init>()     // Catch:{ SQLException -> 0x00b9 }
            android.content.Intent r1 = r7.intent     // Catch:{ SQLException -> 0x00b9 }
            java.lang.String r2 = "userInfo_id"
            r3 = 0
            long r1 = r1.getLongExtra(r2, r3)     // Catch:{ SQLException -> 0x00b9 }
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ SQLException -> 0x00b9 }
            java.lang.Object r8 = r8.queryForId(r1)     // Catch:{ SQLException -> 0x00b9 }
            com.zkteco.android.db.orm.tna.UserInfo r8 = (com.zkteco.android.db.orm.tna.UserInfo) r8     // Catch:{ SQLException -> 0x00b9 }
            r7.userInfo = r8     // Catch:{ SQLException -> 0x00b9 }
            java.lang.String r1 = r7.strCardNum     // Catch:{ SQLException -> 0x00b9 }
            r8.setMain_Card(r1)     // Catch:{ SQLException -> 0x00b9 }
            com.zkteco.android.db.orm.tna.UserInfo r8 = r7.userInfo     // Catch:{ SQLException -> 0x00b9 }
            r8.setSEND_FLAG(r0)     // Catch:{ SQLException -> 0x00b9 }
            com.zkteco.android.core.sdk.HubProtocolManager r8 = new com.zkteco.android.core.sdk.HubProtocolManager     // Catch:{ SQLException -> 0x00b9 }
            android.content.Context r1 = r7.getApplicationContext()     // Catch:{ SQLException -> 0x00b9 }
            r8.<init>(r1)     // Catch:{ SQLException -> 0x00b9 }
            com.zkteco.android.db.orm.tna.UserInfo r1 = r7.userInfo     // Catch:{ SQLException -> 0x00a6, all -> 0x00a3 }
            r1.update()     // Catch:{ SQLException -> 0x00a6, all -> 0x00a3 }
            long r1 = r8.convertPushInit()     // Catch:{ SQLException -> 0x00a6, all -> 0x00a3 }
            java.lang.String r5 = "USER_INFO"
            r8.setPushTableName(r1, r5)     // Catch:{ SQLException -> 0x00a1 }
            java.lang.String r5 = "User_PIN"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x00a1 }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ SQLException -> 0x00a1 }
            r8.setPushStrField(r1, r5, r6)     // Catch:{ SQLException -> 0x00a1 }
            java.lang.String r5 = "SEND_FLAG"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x00a1 }
            int r6 = r6.getSEND_FLAG()     // Catch:{ SQLException -> 0x00a1 }
            r8.setPushIntField(r1, r5, r6)     // Catch:{ SQLException -> 0x00a1 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x00a1 }
            r5.<init>()     // Catch:{ SQLException -> 0x00a1 }
            java.lang.String r6 = "(User_PIN='"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x00a1 }
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x00a1 }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ SQLException -> 0x00a1 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x00a1 }
            java.lang.String r6 = "')"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x00a1 }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLException -> 0x00a1 }
            r8.setPushCon(r1, r5)     // Catch:{ SQLException -> 0x00a1 }
            java.lang.String r5 = ""
            r8.sendHubAction(r0, r1, r5)     // Catch:{ SQLException -> 0x00a1 }
            int r0 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r0 == 0) goto L_0x00bd
        L_0x009d:
            r8.convertPushFree(r1)     // Catch:{ SQLException -> 0x00b9 }
            goto L_0x00bd
        L_0x00a1:
            r0 = move-exception
            goto L_0x00a8
        L_0x00a3:
            r0 = move-exception
            r1 = r3
            goto L_0x00b1
        L_0x00a6:
            r0 = move-exception
            r1 = r3
        L_0x00a8:
            r0.printStackTrace()     // Catch:{ all -> 0x00b0 }
            int r0 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r0 == 0) goto L_0x00bd
            goto L_0x009d
        L_0x00b0:
            r0 = move-exception
        L_0x00b1:
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x00b8
            r8.convertPushFree(r1)     // Catch:{ SQLException -> 0x00b9 }
        L_0x00b8:
            throw r0     // Catch:{ SQLException -> 0x00b9 }
        L_0x00b9:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00bd:
            r7.finish()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffCardRegistrationActivity.lambda$initToolBar$2$ZKStaffCardRegistrationActivity(android.view.View):void");
    }

    /* access modifiers changed from: private */
    public void registrationCard(int i, String str) {
        beeg();
        Message obtainMessage = this.handler.obtainMessage();
        obtainMessage.what = i;
        obtainMessage.obj = str;
        this.handler.sendMessage(obtainMessage);
    }

    private void beeg() {
        this.pool.load(this.afd, 0);
        this.pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                ZKStaffCardRegistrationActivity.this.lambda$beeg$3$ZKStaffCardRegistrationActivity(soundPool, i, i2);
            }
        });
    }

    public /* synthetic */ void lambda$beeg$3$ZKStaffCardRegistrationActivity(SoundPool soundPool, int i, int i2) {
        float f = this.Volume;
        Log.d("gjx", "播放结果: " + soundPool.play(i, f, f, 1, 0, 1.0f));
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.isHome = false;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.isHome = true;
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
            disable(this.mNfcAdapter);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0038, code lost:
        if (r3 != 0) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0049, code lost:
        if (r3 != 0) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004b, code lost:
        r0.convertPushFree(r3);
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0053  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDestroy() {
        /*
            r7 = this;
            super.onDestroy()
            io.reactivex.disposables.Disposable r0 = r7.mDisposable
            if (r0 == 0) goto L_0x000a
            r0.dispose()
        L_0x000a:
            com.zktechnology.android.helper.CardServiceHelper r0 = com.zktechnology.android.helper.CardServiceHelper.getInstance()
            com.zktechnology.android.helper.OnCardReadListener r1 = r7.mOnCardReadListener
            r0.removeOnCardReadListener(r1)
            com.zktechnology.android.helper.McuServiceHelper r0 = com.zktechnology.android.helper.McuServiceHelper.getInstance()
            com.zktechnology.android.helper.OnMcuReadListener r1 = r7.mOnMcuReadListener
            r0.removeOnMcuReadListener(r1)
            boolean r0 = r7.isRemoteEnroll
            if (r0 == 0) goto L_0x0057
            com.zkteco.android.core.sdk.HubProtocolManager r0 = new com.zkteco.android.core.sdk.HubProtocolManager
            android.content.Context r1 = r7.getApplicationContext()
            r0.<init>(r1)
            r1 = 0
            long r3 = r0.convertPushInit()     // Catch:{ Exception -> 0x0042, all -> 0x003f }
            r5 = 22
            java.lang.String r6 = r7.remoteEnrollResult     // Catch:{ Exception -> 0x003d }
            r0.sendHubAction(r5, r3, r6)     // Catch:{ Exception -> 0x003d }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0057
            goto L_0x004b
        L_0x003b:
            r5 = move-exception
            goto L_0x004f
        L_0x003d:
            r5 = move-exception
            goto L_0x0044
        L_0x003f:
            r5 = move-exception
            r3 = r1
            goto L_0x004f
        L_0x0042:
            r5 = move-exception
            r3 = r1
        L_0x0044:
            r5.printStackTrace()     // Catch:{ all -> 0x003b }
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0057
        L_0x004b:
            r0.convertPushFree(r3)
            goto L_0x0057
        L_0x004f:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0056
            r0.convertPushFree(r3)
        L_0x0056:
            throw r5
        L_0x0057:
            android.content.BroadcastReceiver r0 = r7.mNfcReceiver     // Catch:{ Exception -> 0x005d }
            r7.unregisterReceiver(r0)     // Catch:{ Exception -> 0x005d }
            goto L_0x0061
        L_0x005d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0061:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffCardRegistrationActivity.onDestroy():void");
    }

    public void onWiegandIn(String str) {
        boolean z = false;
        if (DBManager.getInstance().getIntOption(WiegandConfig.IN, 0) == 1) {
            z = true;
        }
        if (z) {
            this.mSingleService.submit(new WiegandInTask(str));
        }
    }

    public void onCardRead(String str) {
        if (!this.isHome) {
            if (Math.abs(SystemClock.elapsedRealtime() - this.mCardCurrentTime) < 1000) {
                LogUtils.e(LogUtils.TAG_VERIFY, "规避1秒钟打两次卡");
                return;
            }
            this.mCardCurrentTime = SystemClock.elapsedRealtime();
            registrationCard(0, str);
        }
    }

    class WiegandInTask implements Runnable {
        private final String binaryStr;

        public WiegandInTask(String str) {
            this.binaryStr = str;
        }

        public void run() {
            WiegandInData access$1100 = ZKStaffCardRegistrationActivity.getWiegandInData(this.binaryStr);
            if (access$1100 == null) {
                Log.d(ZKStaffCardRegistrationActivity.TAG, "onWiegandIn: data == null");
            } else if (access$1100.getNumCode() < 0) {
                Log.d(ZKStaffCardRegistrationActivity.TAG, "onWiegandIn: data.getNumCode() = " + access$1100.getNumCode());
            } else if (access$1100.getType().getValue() == 1) {
                ZKStaffCardRegistrationActivity.this.handler.obtainMessage((int) access$1100.getNumCode()).sendToTarget();
            } else {
                Log.d(ZKStaffCardRegistrationActivity.TAG, "onWiegandIn: type is PIN data.getType() = " + access$1100.getType());
            }
        }
    }

    /* access modifiers changed from: private */
    public static WiegandInData getWiegandInData(String str) {
        if (str.length() <= 8) {
            return null;
        }
        byte[] binStrToByteArr4Long = WiegandUtil.binStrToByteArr4Long(str);
        WiegandInData wiegandInData = new WiegandInData();
        wiegandInData.setType(ZKWiegandManager.getInstance().getWiegandTypeIn());
        wiegandInData.setCardBit(ZKWiegandManager.getInstance().getWiegandCardBitIn().getValue());
        wiegandInData.setData(binStrToByteArr4Long);
        wiegandInData.getWiegandData();
        if (wiegandInData.getNumCode() < 0 || !wiegandInData.isCan()) {
            return null;
        }
        return wiegandInData;
    }

    private static void enable(NfcAdapter nfcAdapter) {
        if (nfcAdapter != null) {
            try {
                nfcAdapter.getClass().getDeclaredMethod("enable", new Class[0]).invoke(nfcAdapter, new Object[0]);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void disable(NfcAdapter nfcAdapter) {
        if (nfcAdapter != null) {
            try {
                nfcAdapter.getClass().getDeclaredMethod("disable", new Class[0]).invoke(nfcAdapter, new Object[0]);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
