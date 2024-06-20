package com.zkteco.android.core.interfaces;

import java.sql.SQLException;

public interface RecordsInterface {
    public static final String FACTORY_RESET = "factory-reset";
    public static final String RECORD_CLEAR_ACCDATA = "record-clear-accdata";
    public static final String RECORD_CLEAR_ATTLOG = "record-clear-attlog";
    public static final String RECORD_CLEAR_ATTPHOTO = "record-clear-attphoto";
    public static final String RECORD_CLEAR_BLACKLISTPHOTO = "record-clear-blacklistphoto";
    public static final String RECORD_CLEAR_DATA = "record-clear-data";
    public static final String RECORD_CLEAR_PERMISSION = "record-clear-permission";
    public static final String RECORD_CLEAR_USERPHOTO = "record-clear-userphoto";
    public static final String RECORD_EXPORT_DATABASE = "record-export-database";
    public static final String RECORD_IMPORT_DATABASE = "record-import-database";
    public static final String RE_BOOT_DEVICE = "re_boot_device";

    void clearAccData() throws SQLException;

    void clearAttLog() throws SQLException;

    void clearAttPhoto() throws SQLException;

    void clearBlackListPhoto() throws SQLException;

    void clearData() throws SQLException;

    void clearPermission() throws SQLException;

    void clearUserPhoto() throws SQLException;

    Boolean exportDatabase(String str) throws Exception;

    void factoryReset() throws SQLException;

    void importDatabase(String str) throws Exception;

    void reRootDevice();
}
