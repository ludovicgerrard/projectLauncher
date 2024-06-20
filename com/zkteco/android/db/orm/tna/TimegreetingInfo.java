package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "TIMEGREETING_INFO")
public class TimegreetingInfo extends AbstractORM<TimegreetingInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716732L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Content")
    private String Content;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "endTime")
    private int endTime;
    @DatabaseField(columnName = "startTime")
    private int startTime;
    @DatabaseField(columnName = "ttsID")
    private int ttsID;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "TimegreetingInfo_default.yml";
    }

    public TimegreetingInfo() {
        super(TimegreetingInfo.class);
    }

    public int getTtsID() {
        return this.ttsID;
    }

    public void setTtsID(int i) {
        this.ttsID = i;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int i) {
        this.startTime = i;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int i) {
        this.endTime = i;
    }

    public String getContent() {
        return this.Content;
    }

    public void setContent(String str) {
        this.Content = str;
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
