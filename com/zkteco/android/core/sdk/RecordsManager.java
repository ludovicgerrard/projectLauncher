package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.RecordsInterface;
import com.zkteco.android.core.interfaces.RecordsProvider;
import com.zkteco.android.core.library.CoreProvider;
import java.sql.SQLException;

public class RecordsManager implements RecordsInterface {
    private RecordsProvider provider;

    public RecordsManager(Context context) {
        this.provider = RecordsProvider.getInstance(new CoreProvider(context));
    }

    public void reRootDevice() {
        this.provider.reRootDevice();
    }

    public void factoryReset() throws SQLException {
        this.provider.factoryReset();
    }

    public void clearData() throws SQLException {
        this.provider.clearData();
    }

    public void clearAttLog() throws SQLException {
        this.provider.clearAttLog();
    }

    public void clearPermission() throws SQLException {
        this.provider.clearPermission();
    }

    public void clearAccData() throws SQLException {
        this.provider.clearAccData();
    }

    public void clearUserPhoto() throws SQLException {
        this.provider.clearUserPhoto();
    }

    public void clearBlackListPhoto() throws SQLException {
        this.provider.clearBlackListPhoto();
    }

    public void clearAttPhoto() throws SQLException {
        this.provider.clearAttPhoto();
    }

    public Boolean exportDatabase(String str) throws Exception {
        return this.provider.exportDatabase(str);
    }

    public void importDatabase(String str) throws Exception {
        this.provider.importDatabase(str);
    }
}
