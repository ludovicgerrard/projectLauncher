package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "BELL_INFO")
public class BellInfo extends AbstractORM<BellInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716271L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "ExtbellDelay")
    private int ExtbellDelay;
    @DatabaseField(columnName = "Fri")
    private int Fri;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Mon")
    private int Mon;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Sat")
    private int Sat;
    @DatabaseField(columnName = "Sun")
    private int Sun;
    @DatabaseField(columnName = "Thu")
    private int Thu;
    @DatabaseField(columnName = "Time")
    private int Time;
    @DatabaseField(columnName = "Times")
    private int Times;
    @DatabaseField(columnName = "Tue")
    private int Tue;
    @DatabaseField(columnName = "Valid")
    private int Valid;
    @DatabaseField(columnName = "Volume")
    private int Volume;
    @DatabaseField(columnName = "Wav_Index")
    private int Wav_Index;
    @DatabaseField(columnName = "Way")
    private int Way;
    @DatabaseField(columnName = "Wed")
    private int Wed;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "BellInfo_default.yml";
    }

    public BellInfo() {
        super(BellInfo.class);
    }

    public int getValid() {
        return this.Valid;
    }

    public void setValid(int i) {
        this.Valid = i;
    }

    public int getTime() {
        return this.Time;
    }

    public void setTime(int i) {
        this.Time = i;
    }

    public int getWav_Index() {
        return this.Wav_Index;
    }

    public void setWav_Index(int i) {
        this.Wav_Index = i;
    }

    public int getTimes() {
        return this.Times;
    }

    public void setTimes(int i) {
        this.Times = i;
    }

    public int getWay() {
        return this.Way;
    }

    public void setWay(int i) {
        this.Way = i;
    }

    public int getVolume() {
        return this.Volume;
    }

    public void setVolume(int i) {
        this.Volume = i;
    }

    public int getMon() {
        return this.Mon;
    }

    public void setMon(int i) {
        this.Mon = i;
    }

    public int getTue() {
        return this.Tue;
    }

    public void setTue(int i) {
        this.Tue = i;
    }

    public int getWed() {
        return this.Wed;
    }

    public void setWed(int i) {
        this.Wed = i;
    }

    public int getThu() {
        return this.Thu;
    }

    public void setThu(int i) {
        this.Thu = i;
    }

    public int getFri() {
        return this.Fri;
    }

    public void setFri(int i) {
        this.Fri = i;
    }

    public int getSat() {
        return this.Sat;
    }

    public void setSat(int i) {
        this.Sat = i;
    }

    public int getSun() {
        return this.Sun;
    }

    public void setSun(int i) {
        this.Sun = i;
    }

    public int getExtbellDelay() {
        return this.ExtbellDelay;
    }

    public void setExtbellDelay(int i) {
        this.ExtbellDelay = i;
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
