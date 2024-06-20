package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_RULE_NAME")
public class AccRuleName extends AbstractORM<AccRuleName> implements Serializable {
    private static final long serialVersionUID = 1512784715998L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(canBeNull = false, columnName = "Name_ID")
    private int Name_ID;
    @DatabaseField(canBeNull = false, columnName = "Rule_ID")
    private int Rule_ID;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccRuleName_default.yml";
    }

    public AccRuleName() {
        super(AccRuleName.class);
    }

    public int getRule_ID() {
        return this.Rule_ID;
    }

    public void setRule_ID(int i) {
        this.Rule_ID = i;
    }

    public int getName_ID() {
        return this.Name_ID;
    }

    public void setName_ID(int i) {
        this.Name_ID = i;
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
