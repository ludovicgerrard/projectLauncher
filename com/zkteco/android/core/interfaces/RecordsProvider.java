package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;
import java.sql.SQLException;

public class RecordsProvider extends AbstractProvider implements RecordsInterface {
    private RecordsProvider(Provider provider) {
        super(provider);
    }

    public static RecordsProvider getInstance(Provider provider) {
        return new RecordsProvider(provider);
    }

    public void reRootDevice() {
        getProvider().invoke(RecordsInterface.RE_BOOT_DEVICE, new Object[0]);
    }

    public void factoryReset() {
        getProvider().invoke(RecordsInterface.FACTORY_RESET, new Object[0]);
    }

    public void clearData() {
        getProvider().invoke(RecordsInterface.RECORD_CLEAR_DATA, new Object[0]);
    }

    public void clearAttLog() {
        getProvider().invoke(RecordsInterface.RECORD_CLEAR_ATTLOG, new Object[0]);
    }

    public void clearPermission() {
        getProvider().invoke(RecordsInterface.RECORD_CLEAR_PERMISSION, new Object[0]);
    }

    public void clearAccData() {
        getProvider().invoke(RecordsInterface.RECORD_CLEAR_ACCDATA, new Object[0]);
    }

    public void clearUserPhoto() throws SQLException {
        getProvider().invoke(RecordsInterface.RECORD_CLEAR_USERPHOTO, new Object[0]);
    }

    public void clearBlackListPhoto() throws SQLException {
        getProvider().invoke(RecordsInterface.RECORD_CLEAR_BLACKLISTPHOTO, new Object[0]);
    }

    public void clearAttPhoto() throws SQLException {
        getProvider().invoke(RecordsInterface.RECORD_CLEAR_ATTPHOTO, new Object[0]);
    }

    public Boolean exportDatabase(String str) throws Exception {
        return (Boolean) getProvider().invoke(RecordsInterface.RECORD_EXPORT_DATABASE, str);
    }

    public void importDatabase(String str) throws Exception {
        getProvider().invoke(RecordsInterface.RECORD_IMPORT_DATABASE, str);
    }
}
