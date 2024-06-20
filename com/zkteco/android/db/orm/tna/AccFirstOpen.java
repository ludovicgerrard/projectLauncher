package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_FIRST_OPEN")
public class AccFirstOpen extends AbstractORM<AccFirstOpen> implements Serializable {
    private static final long serialVersionUID = 1512784715883L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "DoorID", defaultValue = "1")
    private int DoorID = 1;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Timezone")
    private int Timezone;
    @DatabaseField(columnName = "UserPIN")
    private String UserPIN;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccFirstOpen_default.yml";
    }

    public AccFirstOpen() {
        super(AccFirstOpen.class);
    }

    public String getUserPIN() {
        return this.UserPIN;
    }

    public void setUserPIN(String str) {
        this.UserPIN = str;
    }

    public int getDoorID() {
        return this.DoorID;
    }

    public void setDoorID(int i) {
        this.DoorID = i;
    }

    public int getTimezone() {
        return this.Timezone;
    }

    public void setTimezone(int i) {
        this.Timezone = i;
    }

    public int getSEND_FLAG() {
        return this.SEND_FLAG;
    }

    public void setSEND_FLAG(int i) {
        this.SEND_FLAG = i;
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
}
