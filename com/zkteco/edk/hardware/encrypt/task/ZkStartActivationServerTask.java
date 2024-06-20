package com.zkteco.edk.hardware.encrypt.task;

import android.util.Log;
import com.zkteco.adk.core.task.IZkTask;
import com.zkteco.adk.core.task.ZkTaskLog;
import com.zkteco.adk.core.task.ZkTaskParamUtils;
import com.zkteco.adk.core.task.ZkThreadPoolManager;
import com.zkteco.edk.hardware.encrypt.ZkAES256Utils;
import com.zkteco.edk.hardware.encrypt.ZkLogTag;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZkStartActivationServerTask implements IZkTask {
    private String mFailedExecuteTask;
    private boolean mIsActivation = false;
    private boolean mIsCreatedServerOnce = false;
    private final AtomicBoolean mIsStop = new AtomicBoolean(true);
    private ServerSocket mServerSocket;

    public String taskName() {
        return "ZkStartActivationServerTask";
    }

    public int execute(Map<String, Object> map) {
        ZkTaskLog.v("start execute " + taskName());
        if (map == null) {
            return -2;
        }
        this.mIsActivation = ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, false);
        if (this.mIsCreatedServerOnce || ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_NEED_START_AS_SERVER_BOOL, false)) {
            return 0;
        }
        boolean z = true;
        if (this.mServerSocket == null) {
            this.mIsCreatedServerOnce = true;
            this.mServerSocket = createServer();
        }
        if (this.mServerSocket == null) {
            z = false;
        }
        map.put(ZkActivationParamName.FLAG_IS_ACTIVATION_SERVER_BOOL, Boolean.valueOf(z));
        if (this.mServerSocket != null) {
            ZkThreadPoolManager.getInstance().execute(new Runnable() {
                public final void run() {
                    ZkStartActivationServerTask.this.lambda$execute$0$ZkStartActivationServerTask();
                }
            });
        }
        ZkTaskLog.v("finish execute " + taskName());
        return 0;
    }

    public /* synthetic */ void lambda$execute$0$ZkStartActivationServerTask() {
        startServer(this.mServerSocket);
    }

    public int stop() {
        this.mIsStop.set(true);
        closeServer();
        return 0;
    }

    private void closeServer() {
        ServerSocket serverSocket = this.mServerSocket;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                this.mServerSocket.close();
                this.mServerSocket = null;
                this.mIsCreatedServerOnce = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int setFailedExecuteTask(String str) {
        this.mFailedExecuteTask = str;
        return 0;
    }

    public String getFailedExecuteTask() {
        return this.mFailedExecuteTask;
    }

    private ServerSocket createServer() {
        try {
            return new ServerSocket(ZkActivationConstant.ACTIVATION_SERVER_PORT);
        } catch (Exception e) {
            Log.e(ZkLogTag.EDK_ACTIVATION, "start as server failed!");
            e.printStackTrace();
            return null;
        }
    }

    public void startServer(ServerSocket serverSocket) {
        try {
            if (this.mIsStop.get()) {
                this.mIsStop.set(false);
                while (!this.mIsStop.get()) {
                    Socket accept = this.mServerSocket.accept();
                    if (ZkActivationConstant.ACTIVATION_CMD_ASK_STATUS.equals(ZkAES256Utils.decrypt(new BufferedReader(new InputStreamReader(accept.getInputStream())).readLine()))) {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
                        bufferedWriter.write(ZkAES256Utils.encrypt(String.format("%s,%s", new Object[]{ZkActivationConstant.ACTIVATION_CMD_ASW_STATUS, this.mIsActivation ? "1" : "0"})) + "\n");
                        bufferedWriter.flush();
                    }
                    accept.close();
                }
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Exception e3) {
            ZkTaskLog.e("execute receiver client data exception! " + e3.getMessage());
            e3.printStackTrace();
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Throwable th) {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }
}
