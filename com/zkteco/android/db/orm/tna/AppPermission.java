package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "APP_PERMISSION")
public class AppPermission extends AbstractORM<AppPermission> implements Serializable {
    private static final long serialVersionUID = 1512784716242L;
    @DatabaseField(columnName = "App_Name")
    private String App_Name;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Permission")
    private int Permission;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AppPermission_default.yml";
    }

    public AppPermission() {
        super(AppPermission.class);
    }

    public String getApp_Name() {
        return this.App_Name;
    }

    public void setApp_Name(String str) {
        this.App_Name = str;
    }

    public int getPermission() {
        return this.Permission;
    }

    public void setPermission(int i) {
        this.Permission = i;
    }

    public String getCREATE_ID() {
        return this.CREATE_ID;
    }

    public void setCREATE_ID(String str) {
        this.CREATE_ID = str;
    }

    public String getMODIFY_TIME() {
        return this.MODIFY_TIME;
    }

    public void setMODIFY_TIME(String str) {
        this.MODIFY_TIME = str;
    }

    public int getSEND_FLAG() {
        return this.SEND_FLAG;
    }

    public void setSEND_FLAG(int i) {
        this.SEND_FLAG = i;
    }
}
