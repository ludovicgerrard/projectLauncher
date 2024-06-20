package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "APP_INFO")
public class AppInfo extends AbstractORM<AppInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716228L;
    @DatabaseField(columnName = "App_Name")
    private String App_Name;
    @DatabaseField(columnName = "App_Path")
    private String App_Path;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Flag")
    private int Flag;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Res_ID")
    private int Res_ID;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AppInfo_default.yml";
    }

    public AppInfo() {
        super(AppInfo.class);
    }

    public String getApp_Name() {
        return this.App_Name;
    }

    public void setApp_Name(String str) {
        this.App_Name = str;
    }

    public String getApp_Path() {
        return this.App_Path;
    }

    public void setApp_Path(String str) {
        this.App_Path = str;
    }

    public int getRes_ID() {
        return this.Res_ID;
    }

    public void setRes_ID(int i) {
        this.Res_ID = i;
    }

    public int getFlag() {
        return this.Flag;
    }

    public void setFlag(int i) {
        this.Flag = i;
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
