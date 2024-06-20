package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "STATE_TIME_ZONE")
public class StateTimeZone extends AbstractORM<StateTimeZone> implements Serializable {
    private static final long serialVersionUID = 1512784716706L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "State_Name")
    private String State_Name;
    @DatabaseField(columnName = "Timezone_Name")
    private String Timezone_Name;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "StateTimeZone_default.yml";
    }

    public StateTimeZone() {
        super(StateTimeZone.class);
    }

    public String getState_Name() {
        return this.State_Name;
    }

    public void setState_Name(String str) {
        this.State_Name = str;
    }

    public String getTimezone_Name() {
        return this.Timezone_Name;
    }

    public void setTimezone_Name(String str) {
        this.Timezone_Name = str;
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
