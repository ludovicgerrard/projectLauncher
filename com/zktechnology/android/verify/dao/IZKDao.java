package com.zktechnology.android.verify.dao;

import com.zkteco.android.db.orm.tna.UserInfo;

public interface IZKDao {
    boolean checkDoor1CancelKeepOpenDay();

    boolean checkStressPassword(String str);

    boolean checkSuperPassword(String str);

    void deleteBlackPhoto(int i);

    void deleteUser(UserInfo userInfo);

    void deleteUserFaceTemp(String str);

    void deleteUserFingerTemp(String str);

    void deleteUserInfo(UserInfo userInfo);

    int getAllPhotoCount();

    int getBlackPhotoCount();

    String getDoor1CancelKeepOpenDay();

    int getDoor1FirstCardOpenDoor();

    UserInfo getUserInfoByFace(byte[] bArr, int i, int i2);

    UserInfo getUserInfoByFinger(byte[] bArr, int i);

    UserInfo getUserInfoByMainCard(String str);

    UserInfo getUserInfoByPalm(String str);

    UserInfo getUserInfoByPin(String str);

    boolean hasSuperUsers();

    boolean isInExpires(UserInfo userInfo);

    boolean isSuperUser(UserInfo userInfo);

    boolean isSupportExpires();

    int query14User();

    boolean registerFace(UserInfo userInfo);

    boolean registerFinger(UserInfo userInfo);

    boolean registerMainCard(UserInfo userInfo);

    boolean registerPalm(UserInfo userInfo);

    boolean registerPassword(UserInfo userInfo);

    boolean registerPin(UserInfo userInfo);
}
