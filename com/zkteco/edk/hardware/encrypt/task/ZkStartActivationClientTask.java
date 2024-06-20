package com.zkteco.edk.hardware.encrypt.task;

import android.text.TextUtils;
import android.util.Log;
import com.zkteco.adk.core.task.IZkTask;
import com.zkteco.adk.core.task.ZkTaskLog;
import com.zkteco.adk.core.task.ZkTaskParamUtils;
import com.zkteco.edk.hardware.encrypt.ZkAES256Utils;
import com.zkteco.edk.hardware.encrypt.ZkLogTag;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZkStartActivationClientTask implements IZkTask {
    private String mFailedExecuteTask;
    private final AtomicBoolean mIsStop = new AtomicBoolean(true);
    private int mQueryStateFailedCount = 0;

    public String taskName() {
        return "ZkStartActivationClientTask";
    }

    public int execute(Map<String, Object> map) {
        ZkTaskLog.v("start execute " + taskName());
        if (map == null) {
            return -2;
        }
        if (ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, false) || this.mQueryStateFailedCount > 9 || ZkTaskParamUtils.getBoolParam(map, ZkActivationParamName.FLAG_IS_ACTIVATION_SERVER_BOOL, false)) {
            return 0;
        }
        if (!startClient(createClient())) {
            this.mQueryStateFailedCount++;
            return -1;
        }
        map.put(ZkActivationParamName.FLAG_IS_ACTIVATION_BOOL, true);
        ZkTaskLog.v("finish execute " + taskName());
        return 0;
    }

    public int stop() {
        this.mIsStop.set(true);
        return 0;
    }

    public int setFailedExecuteTask(String str) {
        this.mFailedExecuteTask = str;
        return 0;
    }

    public String getFailedExecuteTask() {
        return this.mFailedExecuteTask;
    }

    private Socket createClient() {
        try {
            return new Socket(InetAddress.getByName(ZkActivationConstant.ACTIVATION_SERVER_ADDRESS), ZkActivationConstant.ACTIVATION_SERVER_PORT);
        } catch (Exception e) {
            Log.e(ZkLogTag.EDK_ACTIVATION, "start as client failed!");
            e.printStackTrace();
            return null;
        }
    }

    public boolean startClient(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(ZkAES256Utils.encrypt(ZkActivationConstant.ACTIVATION_CMD_ASK_STATUS) + "\n");
            bufferedWriter.flush();
            String readLine = new BufferedReader(new InputStreamReader(inputStream)).readLine();
            if (TextUtils.isEmpty(readLine)) {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
            String[] split = ZkAES256Utils.decrypt(readLine).split(",");
            if (split != null) {
                if (split.length == 3) {
                    if (!"ASW".equals(split[0]) || !"STATUS".equals(split[1]) || !TextUtils.isDigitsOnly(split[2])) {
                        this.mQueryStateFailedCount++;
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return false;
                    }
                    boolean equals = "1".equals(split[2]);
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return equals;
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return false;
        } catch (Exception e5) {
            e5.printStackTrace();
            if (socket != null) {
                socket.close();
            }
        } catch (Throwable th) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }
}
