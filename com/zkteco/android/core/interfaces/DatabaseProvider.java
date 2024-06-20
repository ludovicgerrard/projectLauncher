package com.zkteco.android.core.interfaces;

import android.database.Cursor;
import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.core.library.Provider;
import java.util.List;

public class DatabaseProvider extends AbstractProvider implements DatabaseInterface {
    public DatabaseProvider(Provider provider) {
        super(provider);
    }

    public static DatabaseInterface getInstance(Provider provider) {
        return new DatabaseProvider(provider);
    }

    public void open(String str, Integer num) {
        getProvider().invoke(DatabaseInterface.OPEN, str, num);
    }

    public Cursor executeQuery(String str, DatabaseInterface.SQLCommand sQLCommand) {
        return (Cursor) getProvider().invoke(DatabaseInterface.QUERY, str, sQLCommand);
    }

    public void executeUpdate(String str, DatabaseInterface.SQLCommand sQLCommand) {
        getProvider().invoke(DatabaseInterface.UPDATE, str, sQLCommand);
    }

    public Number executeInsert(String str, DatabaseInterface.SQLCommand sQLCommand) {
        return (Number) getProvider().invoke(DatabaseInterface.INSERT, str, sQLCommand);
    }

    public boolean executeTransaction(String str, List<DatabaseInterface.SQLCommand> list) {
        return ((Boolean) getProvider().invoke(DatabaseInterface.TRANSACTION, str, list)).booleanValue();
    }

    public void executeSql(String str, String str2, Object[] objArr) {
        getProvider().invoke(DatabaseInterface.EXECSQL, str, str2, objArr);
    }

    public void systemInitOptionTable() {
        getProvider().invoke(DatabaseInterface.SYSTEM_INIT_OPTIONTABLE, new Object[0]);
    }

    public void systemInit() {
        getProvider().invoke(DatabaseInterface.SYSTEM_INIT, new Object[0]);
    }

    public int systemFree() {
        return ((Integer) getProvider().invoke(DatabaseInterface.SYSTEM_FREE, new Object[0])).intValue();
    }

    public int getIntOption(String str, int i) {
        return ((Integer) getProvider().invoke(DatabaseInterface.SYSTEM_GETINT, str, Integer.valueOf(i))).intValue();
    }

    public String getStrOption(String str, String str2) {
        return (String) getProvider().invoke(DatabaseInterface.SYSTEM_GETSTR, str, str2);
    }

    public int setIntOption(String str, int i) {
        return ((Integer) getProvider().invoke(DatabaseInterface.SYSTEM_SETINT, str, Integer.valueOf(i))).intValue();
    }

    public int setStrOption(String str, String str2) {
        return ((Integer) getProvider().invoke(DatabaseInterface.SYSTEM_SETSTR, str, str2)).intValue();
    }

    public int getDBVersion() {
        return ((Integer) getProvider().invoke(DatabaseInterface.GET_DATABASE_VERSION, new Object[0])).intValue();
    }
}
