package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "Options")
public class Options extends AbstractORM<Options> implements Serializable {
    private static final long serialVersionUID = 1512784716559L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "optionsname")
    private String optionsname;
    @DatabaseField(columnName = "optionsvalue")
    private String optionsvalue;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "Options_default.yml";
    }

    public Options() {
        super(Options.class);
    }

    public String getOptionsname() {
        return this.optionsname;
    }

    public void setOptionsname(String str) {
        this.optionsname = str;
    }

    public String getOptionsvalue() {
        return this.optionsvalue;
    }

    public void setOptionsvalue(String str) {
        this.optionsvalue = str;
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
