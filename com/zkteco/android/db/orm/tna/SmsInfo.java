package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "SMS_INFO")
public class SmsInfo extends AbstractORM<SmsInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716694L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Content")
    private String Content;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Start_Time")
    private String Start_Time;
    @DatabaseField(columnName = "Type")
    private int Type;
    @DatabaseField(columnName = "Valid_Time")
    private int Valid_Time;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "SmsInfo_default.yml";
    }

    public SmsInfo() {
        super(SmsInfo.class);
    }

    public String getStart_Time() {
        return this.Start_Time;
    }

    public void setStart_Time(String str) {
        this.Start_Time = str;
    }

    public int getValid_Time() {
        return this.Valid_Time;
    }

    public void setValid_Time(int i) {
        this.Valid_Time = i;
    }

    public int getType() {
        return this.Type;
    }

    public void setType(int i) {
        this.Type = i;
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
