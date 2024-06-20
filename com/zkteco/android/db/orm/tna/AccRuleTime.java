package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_RULE_TIME")
public class AccRuleTime extends AbstractORM<AccRuleTime> implements Serializable {
    private static final long serialVersionUID = 1512784716026L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(canBeNull = false, columnName = "Rule_Name_ID")
    private int Rule_Name_ID;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(canBeNull = false, columnName = "Time_ID")
    private int Time_ID;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccRuleTime_default.yml";
    }

    public AccRuleTime() {
        super(AccRuleTime.class);
    }

    public int getRule_Name_ID() {
        return this.Rule_Name_ID;
    }

    public void setRule_Name_ID(int i) {
        this.Rule_Name_ID = i;
    }

    public int getTime_ID() {
        return this.Time_ID;
    }

    public void setTime_ID(int i) {
        this.Time_ID = i;
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
