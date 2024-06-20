package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_TIME_ZONE")
public class AccTimeZone extends AbstractORM<AccTimeZone> implements Serializable {
    private static final long serialVersionUID = 1512784716125L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "End_Time", defaultValue = "0")
    private int End_Time = 0;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Start_Time", defaultValue = "0")
    private int Start_Time = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccTimeZone_default.yml";
    }

    public AccTimeZone() {
        super(AccTimeZone.class);
    }

    public int getStart_Time() {
        return this.Start_Time;
    }

    public void setStart_Time(int i) {
        this.Start_Time = i;
    }

    public int getEnd_Time() {
        return this.End_Time;
    }

    public void setEnd_Time(int i) {
        this.End_Time = i;
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
