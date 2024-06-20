package com.zkteco.liveface562.common;

public interface ZkFaceStateCallback {
    void onInitState(int i);

    void onServiceConnected();

    void onServiceDisconnected();
}
