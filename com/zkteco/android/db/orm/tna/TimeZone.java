package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "TIME_ZONE")
public class TimeZone extends AbstractORM<TimeZone> implements Serializable {
    private static final long serialVersionUID = 1512784716720L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Fri_Time")
    private int Fri_Time;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Mon_Time")
    private int Mon_Time;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Sat_Time")
    private int Sat_Time;
    @DatabaseField(columnName = "Sun_Time")
    private int Sun_Time;
    @DatabaseField(columnName = "Thu_Time")
    private int Thu_Time;
    @DatabaseField(columnName = "Timezone_Name")
    private String Timezone_Name;
    @DatabaseField(columnName = "Tue_Time")
    private int Tue_Time;
    @DatabaseField(columnName = "Wed_Time")
    private int Wed_Time;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "TimeZone_default.yml";
    }

    public TimeZone() {
        super(TimeZone.class);
    }

    public String getTimezone_Name() {
        return this.Timezone_Name;
    }

    public void setTimezone_Name(String str) {
        this.Timezone_Name = str;
    }

    public int getMon_Time() {
        return this.Mon_Time;
    }

    public void setMon_Time(int i) {
        this.Mon_Time = i;
    }

    public int getTue_Time() {
        return this.Tue_Time;
    }

    public void setTue_Time(int i) {
        this.Tue_Time = i;
    }

    public int getWed_Time() {
        return this.Wed_Time;
    }

    public void setWed_Time(int i) {
        this.Wed_Time = i;
    }

    public int getThu_Time() {
        return this.Thu_Time;
    }

    public void setThu_Time(int i) {
        this.Thu_Time = i;
    }

    public int getFri_Time() {
        return this.Fri_Time;
    }

    public void setFri_Time(int i) {
        this.Fri_Time = i;
    }

    public int getSat_Time() {
        return this.Sat_Time;
    }

    public void setSat_Time(int i) {
        this.Sat_Time = i;
    }

    public int getSun_Time() {
        return this.Sun_Time;
    }

    public void setSun_Time(int i) {
        this.Sun_Time = i;
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
