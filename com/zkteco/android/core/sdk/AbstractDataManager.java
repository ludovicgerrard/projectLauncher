package com.zkteco.android.core.sdk;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.core.interfaces.DatabaseProvider;
import com.zkteco.android.core.interfaces.DatabaseReceiver;
import com.zkteco.android.core.library.CoreProvider;
import com.zkteco.android.db.orm.AbstractORM;
import com.zkteco.android.db.orm.SQLiteInterfaceConnectionSource;
import com.zkteco.util.YAMLHelper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractDataManager extends DatabaseReceiver implements DatabaseInterface {
    private static final int CREATETABLE_EXCEPTION = 1;
    private static final int INITDATABASE_SUCCESS = 0;
    private final String TAG = "AbstractDataManager";
    public DatabaseProvider provider;

    public abstract String getDatabaseId();

    public abstract int getDatabaseVersion();

    public abstract List<AbstractORM> getTables();

    private void create() throws SQLException {
        List<AbstractORM> tables = getTables();
        if (tables != null) {
            ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            for (AbstractORM next : tables) {
                Objects.requireNonNull(next);
                newCachedThreadPool.execute(new Runnable() {
                    public final void run() {
                        AbstractORM.this.createTableWithDefaults();
                    }
                });
            }
            newCachedThreadPool.shutdown();
            try {
                newCachedThreadPool.awaitTermination(5, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e("AbstractDataManager", "create: it costs " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms creating tables");
        }
    }

    public int init(Context context) {
        open(context);
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        String str = sharedPreferencesManager.get("isBDInitOver-" + getDatabaseId(), "999");
        Log.d("AbstractDataManager", "isBDInitOver-" + getDatabaseId() + str);
        if (str.equals("1")) {
            return 0;
        }
        try {
            create();
            sharedPreferencesManager.set("isBDInitOver-" + getDatabaseId(), "1");
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public void open(Context context) {
        this.provider = new DatabaseProvider(new CoreProvider(context));
        try {
            AbstractORM.addSource(getDatabaseId(), new SQLiteInterfaceConnectionSource(new CoreProvider(context), getDatabaseId(), getDatabaseVersion()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initUpdateSql(Context context) throws IOException {
        new ArrayList();
        DatabaseProvider databaseProvider = new DatabaseProvider(new CoreProvider(context));
        for (String str : (List) YAMLHelper.getInstanceFromFile(Environment.getExternalStorageDirectory().getPath() + "/config/update/SqlCommand_default.yml")) {
            if (str.indexOf("[") != -1) {
                databaseProvider.executeSql("ZKDB.db", str, str.substring(str.indexOf("[") + 1, str.indexOf("]")).split(","));
            } else {
                databaseProvider.executeSql("ZKDB.db", str, (Object[]) null);
            }
        }
    }
}
