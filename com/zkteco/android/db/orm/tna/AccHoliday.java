package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "acc_holiday")
public class AccHoliday extends AbstractORM<AccHoliday> implements Serializable {
    private static final long serialVersionUID = 1512784715913L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "EndDate", defaultValue = "0")
    private int EndDate = 0;
    @DatabaseField(columnName = "Holiday_Name")
    private String Holiday_Name;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "StartDate", defaultValue = "0")
    private int StartDate = 0;
    @DatabaseField(columnName = "Timezone", defaultValue = "0")
    private int Timezone = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccHoliday_default.yml";
    }

    public AccHoliday() {
        super(AccHoliday.class);
    }

    public String getHoliday_Name() {
        return this.Holiday_Name;
    }

    public void setHoliday_Name(String str) {
        this.Holiday_Name = str;
    }

    public int getStartDate() {
        return this.StartDate;
    }

    public void setStartDate(int i) {
        this.StartDate = i;
    }

    public int getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(int i) {
        this.EndDate = i;
    }

    public int getTimezone() {
        return this.Timezone;
    }

    public void setTimezone(int i) {
        this.Timezone = i;
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
