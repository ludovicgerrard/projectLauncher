package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "acc_unlockcomb")
public class AccUnlockcomb extends AbstractORM<AccUnlockcomb> implements Serializable {
    private static final long serialVersionUID = 1512784716171L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Group1", defaultValue = "0")
    private int Group1 = 0;
    @DatabaseField(columnName = "Group2", defaultValue = "0")
    private int Group2 = 0;
    @DatabaseField(columnName = "Group3", defaultValue = "0")
    private int Group3 = 0;
    @DatabaseField(columnName = "Group4", defaultValue = "0")
    private int Group4 = 0;
    @DatabaseField(columnName = "Group5", defaultValue = "0")
    private int Group5 = 0;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccUnlockcomb_default.yml";
    }

    public AccUnlockcomb() {
        super(AccUnlockcomb.class);
    }

    public int getGroup1() {
        return this.Group1;
    }

    public void setGroup1(int i) {
        this.Group1 = i;
    }

    public int getGroup2() {
        return this.Group2;
    }

    public void setGroup2(int i) {
        this.Group2 = i;
    }

    public int getGroup3() {
        return this.Group3;
    }

    public void setGroup3(int i) {
        this.Group3 = i;
    }

    public int getGroup4() {
        return this.Group4;
    }

    public void setGroup4(int i) {
        this.Group4 = i;
    }

    public int getGroup5() {
        return this.Group5;
    }

    public void setGroup5(int i) {
        this.Group5 = i;
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
