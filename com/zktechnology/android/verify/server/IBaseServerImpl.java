package com.zktechnology.android.verify.server;

public interface IBaseServerImpl {
    void changeStateRemoteAuthToDelay();

    void changeStateToAction();

    void changeStateToRecord();

    void changeStateToRemoteAuth();

    void changeStateToUser();

    void changeStateToWait();

    void changeStateToWay();

    void disMissProcessDialog();

    void resetMultiVerify();

    void stateAction();

    void stateDelay();

    void stateIntent();

    void stateRecord();

    void stateRemoteAuth();

    void stateUser();

    void stateWay();
}
