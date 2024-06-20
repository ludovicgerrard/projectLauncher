package com.zktechnology.android.launcher2;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.zktechnology.android.activity.BaseActivity;
import com.zktechnology.android.activity.MenuActivity;
import com.zktechnology.android.activity.PrivacyAgreementActivity;
import com.zktechnology.android.hardware.ScreenLightUtils;
import com.zktechnology.android.helper.WallPaperHelper;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.strategy.BackgroundThreadExecutor;
import com.zktechnology.android.strategy.MainThreadExecutor;
import com.zktechnology.android.utils.CanVerifyUtil;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.FileUtils;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.TimerRestartManager;
import com.zktechnology.android.utils.ZKReceiver;
import com.zktechnology.android.utils.ZKRunnable;
import com.zktechnology.android.utils.ZKThreadPool;
import com.zktechnology.android.verify.dialog.init.ZKInitDialog;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zktechnology.android.view.CustomWidgetView;
import com.zkteco.android.core.sdk.BtnWidgetManager;
import com.zkteco.android.db.orm.util.SpeakerHelper;
import com.zkteco.android.zkcore.utils.ZKSharedUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Launcher extends BaseActivity implements View.OnLongClickListener, View.OnClickListener {
    private static final String ACTION_WALLPAPER_DELETE = "com.zktechnology.android.Change.Wallpaper.delete";
    public static final String CHANGE_WALLPAPER = "com.zktechnology.android.Change.Wallpaper";
    private static final String CUSTOMIZED_LOGO_WALLPAPER_PATH = "sdcard/customizedLogoWallpaper/";
    private static final String CUSTOMIZED_WALLPAPER_FILE = "customized_wallpaper.jpg";
    private static final String CUSTOMIZED_WALLPAPER_PATH = "sdcard/customizedWallpaper/";
    static final boolean DEBUG_STRICT_MODE = false;
    public static final String DEVICE_RESET = "com.zkteco.android.employeemgmt.action.RESET";
    public static final int ICON_ALARM = 5;
    public static final int ICON_AUINPUT = 7;
    public static final int ICON_CONNECT_ETHERNET = 2;
    public static final int ICON_CONNECT_SERVER = 1;
    public static final int ICON_CONNECT_UDISK = 4;
    public static final int ICON_CONNECT_WIFI = 3;
    public static final int ICON_IO = 6;
    public static final int ICON_OPEN_ALWAYS = 8;
    public static final int ICON_SENSE_STATE = 9;
    static final boolean PROFILE_STARTUP = false;
    private static final String TAG = "Launcher";
    private static final String WALLPAPER_DIR_PATH = "sdcard/ZKTeco/data/wallpaper/";
    private static ZKFileObserver customizedLogoWallpaperDirObserver = new ZKFileObserver(Environment.getExternalStorageDirectory().getPath() + "/customizedLogoWallpaper/");
    private CustomWidgetView customWidgetView;
    private final ZKFileObserver.OnFileChangeListener customizedLogoWallpaper = new ZKFileObserver.OnFileChangeListener() {
        public final void onEvent(int i, String str) {
            Launcher.this.lambda$new$0$Launcher(i, str);
        }
    };
    /* access modifiers changed from: private */
    public DeviceDialog deviceDialog;
    /* access modifiers changed from: private */
    public Runnable dismissDialog;
    private ImageView launcherBg;
    public Context mContext;
    private ZKInitDialog mInitDialog;
    protected State mState = State.WORKSPACE;
    /* access modifiers changed from: private */
    public NotifyInfoDialog notifyInfoDialog;
    private BroadcastReceiver resetReceiver;
    public int soundArea;
    public int soundClose;
    public int soundFar;
    public int soundMT;
    public int soundMask;
    public int soundNormal;
    public SoundPool soundPool;
    public int soundid;
    public ZKSharedUtil spUtil;
    private TimerRestartManager timerRestartManager;
    private BroadcastReceiver wallpaperReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (Launcher.ACTION_WALLPAPER_DELETE.equals(intent.getAction())) {
                Launcher.this.refreshWallPaper();
                Launcher.this.setWallPaper();
            }
        }
    };
    private View wallpaperRoot;
    WallpaperRunnable wallpaperRunnable;

    public enum State {
        WORKSPACE,
        APPS_CUSTOMIZE
    }

    public void onBackPressed() {
    }

    /* access modifiers changed from: protected */
    public void onHomePressed(Intent intent) {
    }

    /* access modifiers changed from: package-private */
    public void showWorkspace(boolean z) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(16777216, 16777216);
        ScreenLightUtils.getInstance().setScreenBrightness(255, "Application init");
        this.dismissDialog = new DismissDialogRunnable(this);
        this.resetReceiver = new ResetReceiver(this);
        this.mContext = getApplicationContext();
        setContentView((int) R.layout.launcher);
        setupViews();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DEVICE_RESET);
        registerReceiver(this.resetReceiver, intentFilter);
        registerReceiver(this.wallpaperReceiver, new IntentFilter(ACTION_WALLPAPER_DELETE));
        showInitDialog();
        NotifyInfoDialog notifyInfoDialog2 = new NotifyInfoDialog(this);
        this.notifyInfoDialog = notifyInfoDialog2;
        notifyInfoDialog2.setOnDismissListener(new OnDismissListener<Launcher>(this) {
            public void onDismiss(Launcher launcher, DialogInterface dialogInterface) {
                MainThreadExecutor.getInstance().remove(Launcher.this.dismissDialog);
            }
        });
        initDeviceDialog();
        this.spUtil = new ZKSharedUtil(this.mContext);
        customizedLogoWallpaperDirObserver.start(this.customizedLogoWallpaper);
    }

    public /* synthetic */ void lambda$new$0$Launcher(int i, String str) {
        if (i == 512 || i == 4) {
            refreshWallPaper();
            setWallPaper();
        }
    }

    private static class ZKFileObserver extends FileObserver {
        private WeakReference<OnFileChangeListener> onFileChangeListener;

        public interface OnFileChangeListener {
            void onEvent(int i, String str);
        }

        ZKFileObserver(String str) {
            super(str, 4095);
        }

        public void onEvent(int i, String str) {
            int i2 = i & 4095;
            WeakReference<OnFileChangeListener> weakReference = this.onFileChangeListener;
            if (weakReference != null && weakReference.get() != null) {
                ((OnFileChangeListener) this.onFileChangeListener.get()).onEvent(i2, str);
            }
        }

        public void stopWatching() {
            super.stopWatching();
            this.onFileChangeListener = null;
        }

        public void start(OnFileChangeListener onFileChangeListener2) {
            this.onFileChangeListener = new WeakReference<>(onFileChangeListener2);
            startWatching();
        }
    }

    private static abstract class OnDismissListener<T> implements DialogInterface.OnDismissListener {
        private WeakReference<T> obj;

        public abstract void onDismiss(T t, DialogInterface dialogInterface);

        public OnDismissListener(T t) {
            this.obj = new WeakReference<>(t);
        }

        public void onDismiss(DialogInterface dialogInterface) {
            if (this.obj.get() != null) {
                onDismiss(this.obj.get(), dialogInterface);
            }
        }
    }

    private void initDeviceDialog() {
        DeviceDialog deviceDialog2 = new DeviceDialog(this);
        this.deviceDialog = deviceDialog2;
        deviceDialog2.setOnDismissListener(new OnDismissListener<Launcher>(this) {
            public void onDismiss(Launcher launcher, DialogInterface dialogInterface) {
                MainThreadExecutor.getInstance().remove(Launcher.this.dismissDialog);
            }
        });
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initDeviceDialog();
    }

    private static class ResetReceiver extends ZKReceiver<Launcher> {
        public ResetReceiver(Launcher launcher) {
            super(launcher);
        }

        public void onReceive(Launcher launcher, Context context, Intent intent) {
            if (Launcher.DEVICE_RESET.equals(intent.getAction())) {
                try {
                    BtnWidgetManager btnWidgetManager = new BtnWidgetManager(context);
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(btnWidgetManager.getBtnWidgets(0));
                    arrayList.addAll(btnWidgetManager.getBtnWidgets(1));
                    btnWidgetManager.saveBtnWidgets(0, arrayList);
                    btnWidgetManager.saveBtnWidgets(1, new ArrayList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setupViews() {
        this.customWidgetView = (CustomWidgetView) findViewById(R.id.custom_widget);
        this.wallpaperRoot = findViewById(R.id.wallpaper_root);
        ImageView imageView = (ImageView) findViewById(R.id.launcher_bg);
        this.launcherBg = imageView;
        imageView.setOnLongClickListener(this);
        this.launcherBg.setOnClickListener(this);
        this.wallpaperRoot.setOnClickListener(this);
    }

    /* access modifiers changed from: package-private */
    public void showAllApps() {
        playSoundMenu();
        changWorkSpaceState(State.APPS_CUSTOMIZE);
        startActivity(new Intent(this, MenuActivity.class));
        overridePendingTransition(0, 0);
    }

    public void changWorkSpaceState(State state) {
        this.mState = state;
    }

    public State getWorkSpaceState() {
        return this.mState;
    }

    private void playSoundMenu() {
        SpeakerHelper.playSound(this, "beep3.ogg", false, "");
    }

    public void showInitDialog() {
        ZKInitDialog zKInitDialog = new ZKInitDialog(this);
        this.mInitDialog = zKInitDialog;
        zKInitDialog.show();
    }

    public void dismissInitDialog() {
        FileLogUtils.writeLauncherInitRecord("dismissInitDialog start");
        this.customWidgetView.initData();
        ZKInitDialog zKInitDialog = this.mInitDialog;
        if (zKInitDialog != null) {
            zKInitDialog.dismiss();
            setSoundId();
            if (DBManager.getInstance().getIntOption("PrivacyAgreementShow", 1) == 1 && ZKLauncher.zkDataEncdec == 1) {
                startActivity(new Intent(this, PrivacyAgreementActivity.class));
            }
            ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            FileLogUtils.writeTouchLog("setTTouchAction: dismissInitDialog");
            this.timerRestartManager = new TimerRestartManager(getApplicationContext());
        }
    }

    public void setSoundId() {
        ZKThreadPool.getInstance().executeTask(new Runnable() {
            public final void run() {
                Launcher.this.lambda$setSoundId$1$Launcher();
            }
        });
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void lambda$setSoundId$1$Launcher() {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            java.util.Locale r0 = r0.locale
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "---locale---"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            r1.println(r2)
            java.lang.String r0 = r0.toString()
            android.media.SoundPool r1 = r5.soundPool
            android.content.Context r2 = r5.mContext
            r3 = 2131689478(0x7f0f0006, float:1.9007973E38)
            r4 = 1
            int r1 = r1.load(r2, r3, r4)
            r5.soundid = r1
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "---lang---"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            r1.println(r2)
            int r1 = r0.hashCode()
            switch(r1) {
                case 3121: goto L_0x0106;
                case 96646644: goto L_0x00fb;
                case 96795103: goto L_0x00f1;
                case 96795356: goto L_0x00e6;
                case 97182509: goto L_0x00dc;
                case 97688863: goto L_0x00d2;
                case 100340341: goto L_0x00c7;
                case 100876622: goto L_0x00bc;
                case 102217250: goto L_0x00b1;
                case 106983531: goto L_0x00a7;
                case 108860863: goto L_0x009c;
                case 110320671: goto L_0x0091;
                case 110618591: goto L_0x0085;
                case 112197572: goto L_0x0079;
                case 115861276: goto L_0x006e;
                case 115861428: goto L_0x0062;
                case 115861812: goto L_0x0057;
                default: goto L_0x0055;
            }
        L_0x0055:
            goto L_0x0111
        L_0x0057:
            java.lang.String r1 = "zh_TW"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = r4
            goto L_0x0112
        L_0x0062:
            java.lang.String r1 = "zh_HK"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 13
            goto L_0x0112
        L_0x006e:
            java.lang.String r1 = "zh_CN"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 0
            goto L_0x0112
        L_0x0079:
            java.lang.String r1 = "vi_VN"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 11
            goto L_0x0112
        L_0x0085:
            java.lang.String r1 = "tr_TR"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 12
            goto L_0x0112
        L_0x0091:
            java.lang.String r1 = "th_TH"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 7
            goto L_0x0112
        L_0x009c:
            java.lang.String r1 = "ru_RU"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 5
            goto L_0x0112
        L_0x00a7:
            java.lang.String r1 = "pt_BR"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 4
            goto L_0x0112
        L_0x00b1:
            java.lang.String r1 = "ko_KR"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 10
            goto L_0x0112
        L_0x00bc:
            java.lang.String r1 = "ja_JP"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 9
            goto L_0x0112
        L_0x00c7:
            java.lang.String r1 = "in_ID"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 8
            goto L_0x0112
        L_0x00d2:
            java.lang.String r1 = "fr_FR"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 3
            goto L_0x0112
        L_0x00dc:
            java.lang.String r1 = "fa_IR"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 6
            goto L_0x0112
        L_0x00e6:
            java.lang.String r1 = "es_MX"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 14
            goto L_0x0112
        L_0x00f1:
            java.lang.String r1 = "es_ES"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 2
            goto L_0x0112
        L_0x00fb:
            java.lang.String r1 = "en_US"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 16
            goto L_0x0112
        L_0x0106:
            java.lang.String r1 = "ar"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0111
            r0 = 15
            goto L_0x0112
        L_0x0111:
            r0 = -1
        L_0x0112:
            switch(r0) {
                case 0: goto L_0x05c3;
                case 1: goto L_0x05c3;
                case 2: goto L_0x0574;
                case 3: goto L_0x0524;
                case 4: goto L_0x04d4;
                case 5: goto L_0x0484;
                case 6: goto L_0x0435;
                case 7: goto L_0x03e5;
                case 8: goto L_0x0395;
                case 9: goto L_0x0345;
                case 10: goto L_0x02f5;
                case 11: goto L_0x02a5;
                case 12: goto L_0x0255;
                case 13: goto L_0x0205;
                case 14: goto L_0x01b5;
                case 15: goto L_0x0165;
                default: goto L_0x0115;
            }
        L_0x0115:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689490(0x7f0f0012, float:1.9007997E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689489(0x7f0f0011, float:1.9007995E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689485(0x7f0f000d, float:1.9007987E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689488(0x7f0f0010, float:1.9007993E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689487(0x7f0f000f, float:1.900799E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689486(0x7f0f000e, float:1.9007989E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0165:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689484(0x7f0f000c, float:1.9007985E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689483(0x7f0f000b, float:1.9007983E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689479(0x7f0f0007, float:1.9007975E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689482(0x7f0f000a, float:1.900798E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689481(0x7f0f0009, float:1.9007979E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689480(0x7f0f0008, float:1.9007977E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x01b5:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689502(0x7f0f001e, float:1.9008021E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689501(0x7f0f001d, float:1.900802E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689497(0x7f0f0019, float:1.9008011E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689500(0x7f0f001c, float:1.9008017E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689499(0x7f0f001b, float:1.9008015E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689498(0x7f0f001a, float:1.9008013E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0205:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689514(0x7f0f002a, float:1.9008046E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689513(0x7f0f0029, float:1.9008043E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689509(0x7f0f0025, float:1.9008035E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689512(0x7f0f0028, float:1.9008041E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689511(0x7f0f0027, float:1.900804E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689510(0x7f0f0026, float:1.9008037E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0255:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689577(0x7f0f0069, float:1.9008173E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689576(0x7f0f0068, float:1.9008171E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689572(0x7f0f0064, float:1.9008163E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689575(0x7f0f0067, float:1.900817E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689574(0x7f0f0066, float:1.9008167E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689573(0x7f0f0065, float:1.9008165E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x02a5:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689583(0x7f0f006f, float:1.9008185E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689582(0x7f0f006e, float:1.9008183E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689578(0x7f0f006a, float:1.9008175E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689581(0x7f0f006d, float:1.9008181E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689580(0x7f0f006c, float:1.900818E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689579(0x7f0f006b, float:1.9008177E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x02f5:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689536(0x7f0f0040, float:1.900809E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689535(0x7f0f003f, float:1.9008088E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689531(0x7f0f003b, float:1.900808E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689534(0x7f0f003e, float:1.9008086E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689533(0x7f0f003d, float:1.9008084E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689532(0x7f0f003c, float:1.9008082E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0345:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689526(0x7f0f0036, float:1.900807E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689525(0x7f0f0035, float:1.9008068E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689521(0x7f0f0031, float:1.900806E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689524(0x7f0f0034, float:1.9008066E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689523(0x7f0f0033, float:1.9008064E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689522(0x7f0f0032, float:1.9008062E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0395:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689520(0x7f0f0030, float:1.9008058E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689519(0x7f0f002f, float:1.9008056E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689515(0x7f0f002b, float:1.9008048E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689518(0x7f0f002e, float:1.9008054E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689517(0x7f0f002d, float:1.9008052E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689516(0x7f0f002c, float:1.900805E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x03e5:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689542(0x7f0f0046, float:1.9008102E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689541(0x7f0f0045, float:1.90081E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689537(0x7f0f0041, float:1.9008092E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689540(0x7f0f0044, float:1.9008098E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689539(0x7f0f0043, float:1.9008096E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689538(0x7f0f0042, float:1.9008094E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0435:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689477(0x7f0f0005, float:1.900797E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689476(0x7f0f0004, float:1.9007968E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689472(0x7f0f0000, float:1.900796E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689475(0x7f0f0003, float:1.9007966E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689474(0x7f0f0002, float:1.9007964E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689473(0x7f0f0001, float:1.9007962E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0484:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689565(0x7f0f005d, float:1.9008149E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689564(0x7f0f005c, float:1.9008147E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689560(0x7f0f0058, float:1.9008139E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689563(0x7f0f005b, float:1.9008145E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689562(0x7f0f005a, float:1.9008143E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689561(0x7f0f0059, float:1.900814E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x04d4:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689548(0x7f0f004c, float:1.9008114E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689547(0x7f0f004b, float:1.9008112E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689543(0x7f0f0047, float:1.9008104E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689546(0x7f0f004a, float:1.900811E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689545(0x7f0f0049, float:1.9008108E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689544(0x7f0f0048, float:1.9008106E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0524:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689508(0x7f0f0024, float:1.9008033E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689507(0x7f0f0023, float:1.9008031E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689503(0x7f0f001f, float:1.9008023E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689506(0x7f0f0022, float:1.900803E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689505(0x7f0f0021, float:1.9008027E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689504(0x7f0f0020, float:1.9008025E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x0574:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689496(0x7f0f0018, float:1.900801E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689495(0x7f0f0017, float:1.9008007E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689491(0x7f0f0013, float:1.9007999E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689494(0x7f0f0016, float:1.9008005E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689493(0x7f0f0015, float:1.9008003E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689492(0x7f0f0014, float:1.9008E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
            goto L_0x0611
        L_0x05c3:
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689567(0x7f0f005f, float:1.9008153E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundFar = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689566(0x7f0f005e, float:1.900815E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundClose = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689568(0x7f0f0060, float:1.9008155E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundArea = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689571(0x7f0f0063, float:1.9008161E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundNormal = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689570(0x7f0f0062, float:1.900816E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMT = r0
            android.media.SoundPool r0 = r5.soundPool
            android.content.Context r1 = r5.mContext
            r2 = 2131689569(0x7f0f0061, float:1.9008157E38)
            int r0 = r0.load(r1, r2, r4)
            r5.soundMask = r0
        L_0x0611:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.launcher2.Launcher.lambda$setSoundId$1$Launcher():void");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        hideBottomUIMenu();
        hideStatusBar();
        changWorkSpaceState(State.WORKSPACE);
        refreshWallPaper();
    }

    class WallpaperRunnable implements Runnable {
        String imageFilesPath;

        WallpaperRunnable() {
        }

        public void setData(String str) {
            this.imageFilesPath = str;
        }

        public void run() {
            WallpaperManager instance = WallpaperManager.getInstance(Launcher.this.getApplicationContext());
            try {
                if (this.imageFilesPath.isEmpty()) {
                    instance.setResource(R.drawable.wallpaper_01);
                } else {
                    Bitmap decodeFile = BitmapFactory.decodeFile(this.imageFilesPath);
                    if (!(instance == null || decodeFile == null)) {
                        instance.setBitmap(decodeFile);
                    }
                }
                MainThreadExecutor.getInstance().remove(Launcher.this.wallpaperRunnable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWallPaper() {
        BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
            public final void run() {
                Launcher.this.lambda$setWallPaper$2$Launcher();
            }
        });
    }

    public /* synthetic */ void lambda$setWallPaper$2$Launcher() {
        String str = "";
        if (new File(this.spUtil.getString("wallpaperShowUri", str)).exists()) {
            str = this.spUtil.getString("wallpaperShowUri", str);
        } else if (!new File(this.spUtil.getString("wallpaperShowUri", str)).exists() && new File("sdcard/customizedLogoWallpaper/customized_wallpaper.jpg").exists()) {
            str = "sdcard/customizedLogoWallpaper/customized_wallpaper.jpg";
        }
        if (this.wallpaperRunnable == null) {
            this.wallpaperRunnable = new WallpaperRunnable();
        }
        this.wallpaperRunnable.setData(str);
        MainThreadExecutor.getInstance().execute(this.wallpaperRunnable);
    }

    /* access modifiers changed from: private */
    public void refreshWallPaper() {
        BackgroundThreadExecutor.getInstance().execute((Runnable) new Runnable() {
            public final void run() {
                Launcher.this.lambda$refreshWallPaper$5$Launcher();
            }
        });
    }

    public /* synthetic */ void lambda$refreshWallPaper$5$Launcher() {
        if (new File("sdcard/customizedWallpaper/customized_wallpaper.jpg").exists() && WallPaperHelper.copyFile("sdcard/customizedWallpaper/customized_wallpaper.jpg", "sdcard/ZKTeco/data/wallpaper/customized_wallpaper.jpg")) {
            FileUtils.deleteFile(new File(CUSTOMIZED_WALLPAPER_PATH));
        }
        File file = new File(CUSTOMIZED_LOGO_WALLPAPER_PATH);
        if (!file.exists() && file.mkdirs()) {
            LogUtils.d(TAG, "sdcard/customizedLogoWallpaper/:mkdirs");
        }
        Bitmap bitmap = null;
        if (new File(this.spUtil.getString("wallpaperShowUri", "")).exists() && !this.spUtil.getString("wallpaperShowUri", "").equals("sdcard/customizedLogoWallpaper/customized_wallpaper.jpg")) {
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(this.spUtil.getString("wallpaperShowUri", "")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                MainThreadExecutor.getInstance().execute(new Runnable(bitmap) {
                    public final /* synthetic */ Bitmap f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        Launcher.this.lambda$refreshWallPaper$3$Launcher(this.f$1);
                    }
                });
                return;
            }
        }
        this.spUtil.putString("wallpaperShowUri", "");
        if (new File("sdcard/customizedLogoWallpaper/customized_wallpaper.jpg").exists()) {
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream("sdcard/customizedLogoWallpaper/customized_wallpaper.jpg"));
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        MainThreadExecutor.getInstance().execute(new Runnable(bitmap) {
            public final /* synthetic */ Bitmap f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                Launcher.this.lambda$refreshWallPaper$4$Launcher(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$refreshWallPaper$3$Launcher(Bitmap bitmap) {
        this.launcherBg.setImageBitmap(bitmap);
    }

    public /* synthetic */ void lambda$refreshWallPaper$4$Launcher(Bitmap bitmap) {
        if (bitmap != null) {
            this.launcherBg.setImageDrawable((Drawable) null);
            this.launcherBg.setImageBitmap(bitmap);
            return;
        }
        this.launcherBg.setImageResource(R.drawable.wallpaper_01);
    }

    private void hideStatusBar() {
        Intent intent = new Intent();
        intent.setAction("HIDE_STATUS");
        sendBroadcast(intent);
        intent.setAction("android.rfid.DISABLE_STATUSBAR");
        intent.putExtra("lockStatus", 65536);
        sendBroadcast(intent);
    }

    private void hideBottomUIMenu() {
        Intent intent = new Intent();
        intent.setAction("HIDE_NAVIGATION");
        sendBroadcast(intent);
        intent.setAction("android.rfid.CONTROL_NAVIGATION");
        intent.putExtra("showNavigation", false);
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        this.customWidgetView.changeUI();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        changWorkSpaceState(State.APPS_CUSTOMIZE);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        ZKVerProcessPar.cleanData(1);
        unregisterReceiver(this.resetReceiver);
        unregisterReceiver(this.wallpaperReceiver);
        this.resetReceiver = null;
        TimerRestartManager timerRestartManager2 = this.timerRestartManager;
        if (timerRestartManager2 != null) {
            timerRestartManager2.onDestroy();
        }
        ZKInitDialog zKInitDialog = this.mInitDialog;
        if (zKInitDialog != null) {
            zKInitDialog.dismiss();
            this.mInitDialog.release();
            this.mInitDialog = null;
        }
        DeviceDialog deviceDialog2 = this.deviceDialog;
        if (deviceDialog2 != null) {
            deviceDialog2.setOnDismissListener((DialogInterface.OnDismissListener) null);
            this.deviceDialog.cancel();
            this.deviceDialog.release();
            this.deviceDialog = null;
        }
        NotifyInfoDialog notifyInfoDialog2 = this.notifyInfoDialog;
        if (notifyInfoDialog2 != null) {
            notifyInfoDialog2.cancel();
            this.notifyInfoDialog.release();
            this.notifyInfoDialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
            this.notifyInfoDialog = null;
        }
        MainThreadExecutor.getInstance().remove(this.wallpaperRunnable);
        MainThreadExecutor.getInstance().remove(this.dismissDialog);
        this.dismissDialog = null;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_IO_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(6);
                    break;
                }
                break;
            case R.id.iv_alarm_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(5);
                    break;
                }
                break;
            case R.id.iv_auInput_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(7);
                    break;
                }
                break;
            case R.id.iv_connect_ethernet_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(2);
                    break;
                }
                break;
            case R.id.iv_connect_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(1);
                    break;
                }
                break;
            case R.id.iv_connect_udisk_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(4);
                    break;
                }
                break;
            case R.id.iv_connect_wifi_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(3);
                    break;
                }
                break;
            case R.id.iv_device_info:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.deviceDialog.initData();
                    this.deviceDialog.show();
                    break;
                }
                break;
            case R.id.iv_open_always_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(8);
                    break;
                }
                break;
            case R.id.iv_sense_state:
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    this.notifyInfoDialog.show();
                    this.notifyInfoDialog.updateList(9);
                    break;
                }
                break;
            case R.id.launcher_bg:
                this.customWidgetView.setVisibility(0);
                this.wallpaperRoot.setVisibility(4);
                findViewById(R.id.wallpaperlistRoot).setVisibility(8);
                break;
            case R.id.wallpaper_root:
                this.wallpaperRoot.setVisibility(4);
                break;
        }
        MainThreadExecutor.getInstance().executeDelayed(this.dismissDialog, 5000);
    }

    private static class DismissDialogRunnable extends ZKRunnable<Launcher> {
        public DismissDialogRunnable(Launcher launcher) {
            super(launcher);
        }

        public void run(Launcher launcher) {
            if (launcher.notifyInfoDialog.isShowing()) {
                launcher.notifyInfoDialog.dismiss();
            }
            if (launcher.deviceDialog != null && launcher.deviceDialog.isShowing()) {
                launcher.deviceDialog.dismiss();
            }
        }
    }

    public boolean onLongClick(View view) {
        if (Integer.valueOf(new ZKSharedUtil(getApplicationContext()).getString("WbApp", "0")).intValue() == 0) {
            return false;
        }
        this.customWidgetView.setVisibility(4);
        this.wallpaperRoot.setVisibility(0);
        ((TextView) findViewById(R.id.hotseat_tv)).setText(getString(R.string.hotseat_change_wallpaper_label));
        return true;
    }

    public static void keyguardOperation(Window window, boolean z) {
        if (z) {
            window.clearFlags(6815872);
        } else {
            window.addFlags(6815872);
        }
    }
}
