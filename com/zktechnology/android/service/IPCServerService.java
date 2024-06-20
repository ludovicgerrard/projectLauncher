package com.zktechnology.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import java.util.Vector;

public class IPCServerService extends Service {
    public static final String BIO_TYPE = "bio_type";
    public static final String CMD_ID = "cmdId";
    public static final int MSG_DELETE_ALL = 4;
    public static final int MSG_DELETE_SINGLE = 3;
    public static final int MSG_OFFLINE = 5;
    public static final int MSG_REGISTER = 1;
    public static final int MSG_UPDATE = 2;
    public static final int MSG_UPDATE_TEMPLATE = 6;
    public static final String PIC_NAME = "picName";
    public static final String PIC_PATH = "picPath";
    private static final String TAG = "IPCServerService";
    public static final String TYPE = "type";
    public static final String USER_PIN = "user_pin";
    /* access modifiers changed from: private */
    public static IPCCallBack mIPCCallBack;
    /* access modifiers changed from: private */
    public Vector<Messenger> mClientMessages = new Vector<>();
    final Messenger mMessenger = new Messenger(new IpcHandler());

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    private class IpcHandler extends Handler {
        private IpcHandler() {
        }

        public void handleMessage(Message message) {
            Message obtain = Message.obtain();
            switch (message.what) {
                case 1:
                    String str = (String) message.getData().get("who");
                    IPCServerService.this.mClientMessages.add(message.replyTo);
                    try {
                        obtain.what = 1;
                        message.replyTo.send(obtain);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.i("IPC", "【Launcher】: " + str + " connect Launcher's IPC Service");
                    return;
                case 2:
                    Bundle data = message.getData();
                    String string = data.getString("type");
                    String string2 = data.getString("picName");
                    String string3 = data.getString("picPath");
                    String string4 = data.getString("cmdId");
                    Log.i("IPC", "【Launcher】: receive update user 1");
                    if (IPCServerService.mIPCCallBack != null) {
                        Log.i("IPC", "【Launcher】: receive update user 2");
                        IPCServerService.mIPCCallBack.onUserUpdate(string, string4, string2, string3);
                        return;
                    }
                    return;
                case 3:
                    String string5 = message.getData().getString("user_pin");
                    Log.i("IPC", "【Launcher】: receive delete single user 1");
                    if (IPCServerService.mIPCCallBack != null) {
                        Log.i("IPC", "【Launcher】:  receive delete single user 2");
                        IPCServerService.mIPCCallBack.deleteSingleUser(string5);
                        return;
                    }
                    return;
                case 4:
                    Log.i("IPC", "【Launcher】: receive delete all user1 ");
                    if (IPCServerService.mIPCCallBack != null) {
                        Log.i("IPC", "【Launcher】: receive delete all user 2");
                        IPCServerService.mIPCCallBack.deleteAll();
                        break;
                    }
                    break;
                case 5:
                    break;
                case 6:
                    Bundle data2 = message.getData();
                    String string6 = data2.getString("user_pin");
                    int i = data2.getInt("bio_type");
                    Log.i("IPC", "【Launcher】: receive update template 1");
                    if (IPCServerService.mIPCCallBack != null) {
                        Log.i("IPC", "【Launcher】: receive update template 2 pin=" + string6 + ", bioType=" + i);
                        IPCServerService.mIPCCallBack.onTemplateUpdate(string6, i);
                        return;
                    }
                    return;
                default:
                    return;
            }
            IPCServerService.this.mClientMessages.remove(message.replyTo);
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public static void setIPCCallBack(IPCCallBack iPCCallBack) {
        if (iPCCallBack != null) {
            mIPCCallBack = iPCCallBack;
        }
    }

    public class IServiceBinder extends Binder {
        public IServiceBinder() {
        }

        public IPCServerService getService() {
            return IPCServerService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }
}
