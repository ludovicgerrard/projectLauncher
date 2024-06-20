package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_ATT_LOG")
public class AccAttLog extends AbstractORM<AccAttLog> implements Serializable {
    private static final long serialVersionUID = 1512784715849L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "DoorID")
    private int DoorID;
    @DatabaseField(columnName = "EventType")
    private int EventType;
    @DatabaseField(columnName = "InOutState")
    private int InOutState;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "MainCard")
    private String MainCard;
    @DatabaseField(columnName = "Mask_Flag")
    private int Mask_Flag;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Temperature")
    private String Temperature;
    @DatabaseField(columnName = "TimeSecond")
    private int TimeSecond;
    @DatabaseField(columnName = "UserPIN")
    private String UserPIN;
    @DatabaseField(columnName = "Verified")
    private int Verified;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccAttLog_default.yml";
    }

    public AccAttLog() {
        super(AccAttLog.class);
    }

    public String getMainCard() {
        return this.MainCard;
    }

    public void setMainCard(String str) {
        this.MainCard = str;
    }

    public String getUserPIN() {
        return this.UserPIN;
    }

    public void setUserPIN(String str) {
        this.UserPIN = str;
    }

    public int getVerified() {
        return this.Verified;
    }

    public void setVerified(int i) {
        this.Verified = i;
    }

    public int getDoorID() {
        return this.DoorID;
    }

    public void setDoorID(int i) {
        this.DoorID = i;
    }

    public int getEventType() {
        return this.EventType;
    }

    public void setEventType(int i) {
        this.EventType = i;
    }

    public int getInOutState() {
        return this.InOutState;
    }

    public void setInOutState(int i) {
        this.InOutState = i;
    }

    public int getTimeSecond() {
        return this.TimeSecond;
    }

    public void setTimeSecond(int i) {
        this.TimeSecond = i;
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

    public String getTemperature() {
        return this.Temperature;
    }

    public void setTemperature(String str) {
        this.Temperature = str;
    }

    public int getMask_Flag() {
        return this.Mask_Flag;
    }

    public void setMask_Flag(int i) {
        this.Mask_Flag = i;
    }
}
