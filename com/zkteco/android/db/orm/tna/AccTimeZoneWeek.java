package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "acc_timezone")
public class AccTimeZoneWeek extends AbstractORM<AccTimeZoneWeek> implements Serializable {
    private static final long serialVersionUID = 1512784716155L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "FriEnd", defaultValue = "2359")
    private int FriEnd = 2359;
    @DatabaseField(columnName = "FriStart", defaultValue = "0")
    private int FriStart = 0;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "MonEnd", defaultValue = "2359")
    private int MonEnd = 2359;
    @DatabaseField(columnName = "MonStart", defaultValue = "0")
    private int MonStart = 0;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "SatEnd", defaultValue = "2359")
    private int SatEnd = 2359;
    @DatabaseField(columnName = "SatStart", defaultValue = "0")
    private int SatStart = 0;
    @DatabaseField(columnName = "SunEnd", defaultValue = "2359")
    private int SunEnd = 2359;
    @DatabaseField(columnName = "SunStart", defaultValue = "0")
    private int SunStart = 0;
    @DatabaseField(columnName = "ThursEnd", defaultValue = "2359")
    private int ThursEnd = 2359;
    @DatabaseField(columnName = "ThursStart", defaultValue = "0")
    private int ThursStart = 0;
    @DatabaseField(columnName = "TuesEnd", defaultValue = "2359")
    private int TuesEnd = 2359;
    @DatabaseField(columnName = "TuesStart", defaultValue = "0")
    private int TuesStart = 0;
    @DatabaseField(columnName = "WedEnd", defaultValue = "2359")
    private int WedEnd = 2359;
    @DatabaseField(columnName = "WedStart", defaultValue = "0")
    private int WedStart = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccTimeZoneWeek_default.yml";
    }

    public AccTimeZoneWeek() {
        super(AccTimeZoneWeek.class);
    }

    public int getSunStart() {
        return this.SunStart;
    }

    public void setSunStart(int i) {
        this.SunStart = i;
    }

    public int getSunEnd() {
        return this.SunEnd;
    }

    public void setSunEnd(int i) {
        this.SunEnd = i;
    }

    public int getMonStart() {
        return this.MonStart;
    }

    public void setMonStart(int i) {
        this.MonStart = i;
    }

    public int getMonEnd() {
        return this.MonEnd;
    }

    public void setMonEnd(int i) {
        this.MonEnd = i;
    }

    public int getTuesStart() {
        return this.TuesStart;
    }

    public void setTuesStart(int i) {
        this.TuesStart = i;
    }

    public int getTuesEnd() {
        return this.TuesEnd;
    }

    public void setTuesEnd(int i) {
        this.TuesEnd = i;
    }

    public int getWedStart() {
        return this.WedStart;
    }

    public void setWedStart(int i) {
        this.WedStart = i;
    }

    public int getWedEnd() {
        return this.WedEnd;
    }

    public void setWedEnd(int i) {
        this.WedEnd = i;
    }

    public int getThursStart() {
        return this.ThursStart;
    }

    public void setThursStart(int i) {
        this.ThursStart = i;
    }

    public int getThursEnd() {
        return this.ThursEnd;
    }

    public void setThursEnd(int i) {
        this.ThursEnd = i;
    }

    public int getFriStart() {
        return this.FriStart;
    }

    public void setFriStart(int i) {
        this.FriStart = i;
    }

    public int getFriEnd() {
        return this.FriEnd;
    }

    public void setFriEnd(int i) {
        this.FriEnd = i;
    }

    public int getSatStart() {
        return this.SatStart;
    }

    public void setSatStart(int i) {
        this.SatStart = i;
    }

    public int getSatEnd() {
        return this.SatEnd;
    }

    public void setSatEnd(int i) {
        this.SatEnd = i;
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
