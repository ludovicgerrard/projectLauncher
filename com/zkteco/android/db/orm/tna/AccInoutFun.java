package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_INOUT_FUN")
public class AccInoutFun extends AbstractORM<AccInoutFun> implements Serializable {
    private static final long serialVersionUID = 1512784715944L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "EventType")
    private int EventType;
    @DatabaseField(columnName = "InAddr")
    private int InAddr;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Number")
    private int Number;
    @DatabaseField(columnName = "OutAddr")
    private int OutAddr;
    @DatabaseField(columnName = "OutTime")
    private int OutTime;
    @DatabaseField(columnName = "OutType")
    private int OutType;
    @DatabaseField(columnName = "Reserved")
    private int Reserved;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccInoutFun_default.yml";
    }

    public AccInoutFun() {
        super(AccInoutFun.class);
    }

    public int getNumber() {
        return this.Number;
    }

    public void setNumber(int i) {
        this.Number = i;
    }

    public int getEventType() {
        return this.EventType;
    }

    public void setEventType(int i) {
        this.EventType = i;
    }

    public int getInAddr() {
        return this.InAddr;
    }

    public void setInAddr(int i) {
        this.InAddr = i;
    }

    public int getOutType() {
        return this.OutType;
    }

    public void setOutType(int i) {
        this.OutType = i;
    }

    public int getOutAddr() {
        return this.OutAddr;
    }

    public void setOutAddr(int i) {
        this.OutAddr = i;
    }

    public int getOutTime() {
        return this.OutTime;
    }

    public void setOutTime(int i) {
        this.OutTime = i;
    }

    public int getReserved() {
        return this.Reserved;
    }

    public void setReserved(int i) {
        this.Reserved = i;
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
