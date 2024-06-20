package com.zktechnology.android.launcher2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.MemoryFile;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.work.WorkRequest;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.CanVerifyUtil;
import com.zkteco.android.videointercom.IVideoBellCallStatusListener;
import com.zkteco.android.videointercom.IVideoIntercomAidlInterface;
import com.zkteco.android.videointercom.IVideoP2PEventListener;
import com.zkteco.android.videointercom.IWatchDog;
import java.io.FileDescriptor;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ZKVideoIntercomLauncher extends ZKExtractLauncher {
    Runnable checkFeedDogTask = new Runnable() {
        public void run() {
            if (ZKVideoIntercomLauncher.this.howLongHasNotFeedDog >= 10) {
                if (ZKVideoIntercomLauncher.this.iVideoIntercomAidlInterface != null) {
                    ZKVideoIntercomLauncher zKVideoIntercomLauncher = ZKVideoIntercomLauncher.this;
                    zKVideoIntercomLauncher.bindService(zKVideoIntercomLauncher.intent, ZKVideoIntercomLauncher.this.serviceConnection, 1);
                }
                int unused = ZKVideoIntercomLauncher.this.howLongHasNotFeedDog = 0;
                return;
            }
            ZKVideoIntercomLauncher.access$512(ZKVideoIntercomLauncher.this, 1);
        }
    };
    /* access modifiers changed from: private */
    public boolean checkFeedDogTaskIsRunning;
    private Parcel datas;
    Runnable delayTask = new Runnable() {
        public void run() {
            ZKVideoIntercomLauncher.this.tv_time.setText("对方手机可能不在身边，建议稍后尝试...");
        }
    };
    private FrameLayout fl_call;
    /* access modifiers changed from: private */
    public Handler handler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public int howLongHasNotFeedDog = 0;
    public IVideoIntercomAidlInterface iVideoIntercomAidlInterface = null;
    /* access modifiers changed from: private */
    public Intent intent;
    /* access modifiers changed from: private */
    public boolean isAnswering = false;
    private MemoryFile memoryFile;
    final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
    public boolean sendVideoFlag = false;
    public SendVideoTask sendVideoTask;
    /* access modifiers changed from: private */
    public ServiceConnection serviceConnection;
    /* access modifiers changed from: private */
    public int timeUsedInsec;
    /* access modifiers changed from: private */
    public TextView tv_time;
    /* access modifiers changed from: private */
    public Handler uiHandle = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 1 && ZKVideoIntercomLauncher.this.isAnswering) {
                ZKVideoIntercomLauncher.this.tv_time.setText(ZKVideoIntercomLauncher.this.getTime());
                ZKVideoIntercomLauncher.this.uiHandle.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };
    public IBinder videoService;

    static /* synthetic */ int access$512(ZKVideoIntercomLauncher zKVideoIntercomLauncher, int i) {
        int i2 = zKVideoIntercomLauncher.howLongHasNotFeedDog + i;
        zKVideoIntercomLauncher.howLongHasNotFeedDog = i2;
        return i2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent2 = new Intent();
        this.intent = intent2;
        intent2.setComponent(new ComponentName("com.zkteco.android.videointercom", "com.zkteco.android.videointercom.VideoIntercomService"));
        AnonymousClass2 r4 = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName componentName) {
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ZKVideoIntercomLauncher.this.videoService = iBinder;
                ZKVideoIntercomLauncher.this.iVideoIntercomAidlInterface = IVideoIntercomAidlInterface.Stub.asInterface(iBinder);
                try {
                    ZKVideoIntercomLauncher.this.iVideoIntercomAidlInterface.setBellStatus(new IVideoBellCallStatusListener.Stub() {
                        public void bellCallStatus(int i) throws RemoteException {
                            if (i == 1 || i == -1) {
                                ZKVideoIntercomLauncher.this.setCallStatus(0);
                            }
                            if (i == 0) {
                                boolean unused = ZKVideoIntercomLauncher.this.isAnswering = true;
                                ZKVideoIntercomLauncher.this.handler.removeCallbacks(ZKVideoIntercomLauncher.this.delayTask);
                                int unused2 = ZKVideoIntercomLauncher.this.timeUsedInsec = 0;
                                ZKVideoIntercomLauncher.this.uiHandle.sendEmptyMessageDelayed(1, 1000);
                            }
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    ZKVideoIntercomLauncher.this.iVideoIntercomAidlInterface.P2PEventListener(new IVideoP2PEventListener.Stub() {
                        public void P2PEvent(String str) throws RemoteException {
                            if ("TRANS_LIVE_VIDEO_START".equals(str)) {
                                ZKVideoIntercomLauncher.this.sendVideoFlag = true;
                            } else if ("TRANS_LIVE_VIDEO_STOP".equals(str)) {
                                ZKVideoIntercomLauncher.this.sendVideoFlag = false;
                            }
                        }
                    });
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
                try {
                    ZKVideoIntercomLauncher.this.iVideoIntercomAidlInterface.serviceLiveListener(new IWatchDog.Stub() {
                        public void feedDog(int i) throws RemoteException {
                            int unused = ZKVideoIntercomLauncher.this.howLongHasNotFeedDog = 0;
                        }
                    });
                    if (!ZKVideoIntercomLauncher.this.checkFeedDogTaskIsRunning) {
                        ZKVideoIntercomLauncher.this.scheduledThreadPool.scheduleAtFixedRate(ZKVideoIntercomLauncher.this.checkFeedDogTask, 1, 1, TimeUnit.SECONDS);
                        boolean unused = ZKVideoIntercomLauncher.this.checkFeedDogTaskIsRunning = true;
                    }
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                }
            }
        };
        this.serviceConnection = r4;
        bindService(this.intent, r4, 1);
        this.sendVideoTask = new SendVideoTask();
        initView();
    }

    private void initView() {
        this.fl_call = (FrameLayout) findViewById(R.id.frame_calling);
        this.tv_time = (TextView) findViewById(R.id.tv_time);
        findViewById(R.id.call_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ZKVideoIntercomLauncher.this.setCallStatus(0);
            }
        });
    }

    public void setCallStatus(int i) {
        try {
            this.isAnswering = false;
            if (i == 0) {
                CanVerifyUtil.getInstance().setCanVerify(true);
                this.sendVideoFlag = false;
                this.fl_call.setVisibility(8);
                this.tv_time.setText("");
                this.timeUsedInsec = 0;
                this.handler.removeCallbacks(this.delayTask);
                this.uiHandle.removeMessages(1);
            } else {
                CanVerifyUtil.getInstance().setCanVerify(false);
                this.sendVideoFlag = true;
                this.fl_call.setVisibility(0);
                this.handler.postDelayed(this.delayTask, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS);
                this.tv_time.setText("等待对方接听...");
            }
            IVideoIntercomAidlInterface iVideoIntercomAidlInterface2 = this.iVideoIntercomAidlInterface;
            if (iVideoIntercomAidlInterface2 != null) {
                iVideoIntercomAidlInterface2.setCallStatus(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTime() {
        this.timeUsedInsec++;
        return getHour() + ":" + getMin() + ":" + getSec();
    }

    public CharSequence getHour() {
        int i = this.timeUsedInsec / 3600;
        return i < 10 ? "0" + i : String.valueOf(i);
    }

    public CharSequence getMin() {
        int i = this.timeUsedInsec / 60;
        return i < 10 ? "0" + i : String.valueOf(i);
    }

    public CharSequence getSec() {
        int i = this.timeUsedInsec % 60;
        return i < 10 ? "0" + i : String.valueOf(i);
    }

    /* access modifiers changed from: private */
    public void sendVideo(byte[] bArr) {
        MemoryFile memoryFile2;
        if (bArr != null) {
            try {
                IVideoIntercomAidlInterface iVideoIntercomAidlInterface2 = this.iVideoIntercomAidlInterface;
                if (iVideoIntercomAidlInterface2 != null && iVideoIntercomAidlInterface2.asBinder().isBinderAlive()) {
                    int length = bArr.length;
                    MemoryFile memoryFile3 = new MemoryFile((String) null, 2097152);
                    this.memoryFile = memoryFile3;
                    memoryFile3.allowPurging(false);
                    this.datas = Parcel.obtain();
                    this.memoryFile.writeBytes(new byte[length], 0, 0, length);
                    Method declaredMethod = this.memoryFile.getClass().getDeclaredMethod("getFileDescriptor", new Class[0]);
                    this.iVideoIntercomAidlInterface.writeBytesFinish(length);
                    if (declaredMethod != null) {
                        ParcelFileDescriptor dup = ParcelFileDescriptor.dup((FileDescriptor) declaredMethod.invoke(this.memoryFile, new Object[0]));
                        if (this.videoService != null) {
                            this.datas.writeParcelable(dup, 0);
                            this.videoService.transact(100, this.datas, (Parcel) null, 0);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Parcel parcel = this.datas;
                if (parcel != null) {
                    parcel.recycle();
                    this.datas = null;
                }
                memoryFile2 = this.memoryFile;
                if (memoryFile2 == null) {
                    return;
                }
            } catch (Throwable th) {
                Parcel parcel2 = this.datas;
                if (parcel2 != null) {
                    parcel2.recycle();
                    this.datas = null;
                }
                MemoryFile memoryFile4 = this.memoryFile;
                if (memoryFile4 != null) {
                    memoryFile4.close();
                    this.memoryFile = null;
                }
                throw th;
            }
        }
        Parcel parcel3 = this.datas;
        if (parcel3 != null) {
            parcel3.recycle();
            this.datas = null;
        }
        memoryFile2 = this.memoryFile;
        if (memoryFile2 == null) {
            return;
        }
        memoryFile2.close();
        this.memoryFile = null;
    }

    class SendVideoTask implements Runnable {
        byte[] data = null;

        SendVideoTask() {
        }

        public void setData(byte[] bArr) {
            this.data = bArr;
        }

        public void run() {
            ZKVideoIntercomLauncher.this.sendVideo(this.data);
        }
    }

    public void onDestroy() {
        unbindService(this.serviceConnection);
        this.serviceConnection = null;
        super.onDestroy();
    }
}
