package com.zkteco.android.core.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import java.util.HashSet;
import java.util.Set;

public abstract class DatabaseReceiver extends BroadcastReceiver {
    public static final String ACTION_ON_ADD_PEOPLE_BIOMETRIC = "com.zkteco.android.core.add.people.biometric";
    public static final String ACTION_ON_ADD_PEOPLE_PHOTO = "com.zkteco.android.core.add.people.photo";
    public static final String ACTION_ON_DELETE_ALL_PEOPLE_BIOMETRIC = "com.zkteco.android.core.delete.all.people.biometric";
    public static final String ACTION_ON_DELETE_PEOPLE_BIOMETRIC = "com.zkteco.android.core.delete.people.biometric";
    public static final String ACTION_ON_TABLE_MODIFIED = "com.zkteco.android.core.db.TABLE_MODIFIED";
    public static final String DB_ID = "id";
    public static final String TABLE_NAME = "table-name";
    private static final String TAG = "DatabaseReceiver";
    private String databaseId;
    private Set<String> tableFilter;

    /* access modifiers changed from: protected */
    public abstract void onTableModified(String str);

    public void register(Context context, String str) {
        this.databaseId = str;
        if (this.tableFilter != null) {
            context.registerReceiver(this, new IntentFilter("com.zkteco.android.core.db.TABLE_MODIFIED"));
            context.registerReceiver(this, new IntentFilter(ACTION_ON_ADD_PEOPLE_BIOMETRIC));
            context.registerReceiver(this, new IntentFilter(ACTION_ON_DELETE_PEOPLE_BIOMETRIC));
            context.registerReceiver(this, new IntentFilter(ACTION_ON_DELETE_ALL_PEOPLE_BIOMETRIC));
            context.registerReceiver(this, new IntentFilter(ACTION_ON_ADD_PEOPLE_PHOTO));
        }
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("id");
        String str = TAG;
        Log.d(str, "Our DB ID: " + this.databaseId + "; received db id: " + stringExtra);
        if (stringExtra == null) {
            Log.w(str, "Received DB intent without DB id: " + intent);
        }
        if (this.databaseId.equals(stringExtra)) {
            if (intent.getAction().equals("com.zkteco.android.core.db.TABLE_MODIFIED")) {
                Log.d(str, "Received table changed broadcast");
                HashSet hashSet = (HashSet) intent.getSerializableExtra("table-name");
                for (String next : this.tableFilter) {
                    if (hashSet.contains(next)) {
                        onTableModified(next);
                    }
                }
                return;
            }
            Log.w(str, "Received unknown action " + intent.getAction());
        }
    }

    public void setTableFilter(Set<String> set) {
        this.tableFilter = set;
    }
}
