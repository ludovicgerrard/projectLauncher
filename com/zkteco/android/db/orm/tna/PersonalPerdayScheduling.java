package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "PERSONAL_PERDAY_SCHEDULING")
public class PersonalPerdayScheduling extends AbstractORM<PersonalPerdayScheduling> implements Serializable {
    private static final long serialVersionUID = 1512784716586L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Class_No")
    private int Class_No;
    @DatabaseField(columnName = "Date")
    private String Date;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Pin")
    private int Pin;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "PersonalPerdayScheduling_default.yml";
    }

    public PersonalPerdayScheduling() {
        super(PersonalPerdayScheduling.class);
    }

    public int getPin() {
        return this.Pin;
    }

    public void setPin(int i) {
        this.Pin = i;
    }

    public String getDate() {
        return this.Date;
    }

    public void setDate(String str) {
        this.Date = str;
    }

    public int getClass_No() {
        return this.Class_No;
    }

    public void setClass_No(int i) {
        this.Class_No = i;
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
