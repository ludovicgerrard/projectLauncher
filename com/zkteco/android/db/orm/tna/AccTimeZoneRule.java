package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_TIME_ZONE_RULE")
public class AccTimeZoneRule extends AbstractORM<AccTimeZoneRule> implements Serializable {
    private static final long serialVersionUID = 1512784716141L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(canBeNull = false, columnName = "Time_Zone_ID")
    private int Time_Zone_ID;
    @DatabaseField(columnName = "Type", defaultValue = "0")
    private int Type = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccTimeZoneRule_default.yml";
    }

    public AccTimeZoneRule() {
        super(AccTimeZoneRule.class);
    }

    public int getType() {
        return this.Type;
    }

    public void setType(int i) {
        this.Type = i;
    }

    public int getTime_Zone_ID() {
        return this.Time_Zone_ID;
    }

    public void setTime_Zone_ID(int i) {
        this.Time_Zone_ID = i;
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
