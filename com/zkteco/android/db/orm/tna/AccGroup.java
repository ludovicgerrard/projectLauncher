package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "acc_group")
public class AccGroup extends AbstractORM<AccGroup> implements Serializable {
    private static final long serialVersionUID = 1512784715898L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Timezone1", defaultValue = "1")
    private int Timezone1 = 1;
    @DatabaseField(columnName = "Timezone2", defaultValue = "0")
    private int Timezone2 = 0;
    @DatabaseField(columnName = "Timezone3", defaultValue = "0")
    private int Timezone3 = 0;
    @DatabaseField(columnName = "VaildHoliday", defaultValue = "0")
    private int VaildHoliday = 0;
    @DatabaseField(columnName = "Verification", defaultValue = "0")
    private int Verification = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccGroup_default.yml";
    }

    public AccGroup() {
        super(AccGroup.class);
    }

    public int getVerification() {
        return this.Verification;
    }

    public void setVerification(int i) {
        this.Verification = i;
    }

    public int getVaildHoliday() {
        return this.VaildHoliday;
    }

    public void setVaildHoliday(int i) {
        this.VaildHoliday = i;
    }

    public int getTimezone1() {
        return this.Timezone1;
    }

    public void setTimezone1(int i) {
        this.Timezone1 = i;
    }

    public int getTimezone2() {
        return this.Timezone2;
    }

    public void setTimezone2(int i) {
        this.Timezone2 = i;
    }

    public int getTimezone3() {
        return this.Timezone3;
    }

    public void setTimezone3(int i) {
        this.Timezone3 = i;
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
