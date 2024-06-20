package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ATT_LOG")
public class AttLog extends AbstractORM<AttLog> implements Serializable {
    private static final long serialVersionUID = 1512784716256L;
    @DatabaseField(columnName = "Att_Flag")
    private int Att_Flag;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Mask_Flag")
    private int Mask_Flag;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Sensor_NO")
    private int Sensor_NO;
    @DatabaseField(columnName = "Status")
    private int Status;
    @DatabaseField(columnName = "Temperature")
    private String Temperature;
    @DatabaseField(columnName = "User_PIN")
    private String User_PIN;
    @DatabaseField(columnName = "Verify_Time")
    private String Verify_Time;
    @DatabaseField(columnName = "Verify_Type")
    private int Verify_Type;
    @DatabaseField(columnName = "Work_Code_ID")
    private int Work_Code_ID;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AttLog_default.yml";
    }

    public AttLog() {
        super(AttLog.class);
    }

    public String getUser_PIN() {
        return this.User_PIN;
    }

    public void setUser_PIN(String str) {
        this.User_PIN = str;
    }

    public int getVerify_Type() {
        return this.Verify_Type;
    }

    public void setVerify_Type(int i) {
        this.Verify_Type = i;
    }

    public String getVerify_Time() {
        return this.Verify_Time;
    }

    public void setVerify_Time(String str) {
        this.Verify_Time = str;
    }

    public int getStatus() {
        return this.Status;
    }

    public void setStatus(int i) {
        this.Status = i;
    }

    public int getWork_Code_ID() {
        return this.Work_Code_ID;
    }

    public void setWork_Code_ID(int i) {
        this.Work_Code_ID = i;
    }

    public int getSensor_NO() {
        return this.Sensor_NO;
    }

    public void setSensor_NO(int i) {
        this.Sensor_NO = i;
    }

    public int getAtt_Flag() {
        return this.Att_Flag;
    }

    public void setAtt_Flag(int i) {
        this.Att_Flag = i;
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
