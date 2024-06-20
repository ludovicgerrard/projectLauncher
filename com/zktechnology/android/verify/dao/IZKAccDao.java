package com.zktechnology.android.verify.dao;

import com.zkteco.android.db.orm.tna.UserInfo;

public interface IZKAccDao {
    void addAccAttLog(int i, int i2, int i3, int i4);

    void addAccAttLog(UserInfo userInfo, int i, int i2, int i3, int i4);

    void addAccAttLog(UserInfo userInfo, int i, int i2, int i3, String str, int i4, double d, int i5);

    void deleteAccAttLog(int i);

    void deleteCapture(int i);

    void deletePhoto(int i);

    int getAccAttLogCount();

    int getMaxCaptureCount();

    int getMultiCardOpenDoorState();
}
