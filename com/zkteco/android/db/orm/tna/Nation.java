package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "NATION")
public class Nation extends AbstractORM<Nation> implements Serializable {
    private static final long serialVersionUID = 1512784716899L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Nation_ID")
    private int Nation_ID;
    @DatabaseField(columnName = "Nation_Name")
    private String Nation_Name;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "Nation_default.yml";
    }

    public Nation() {
        super(Nation.class);
    }

    public int getNation_ID() {
        return this.Nation_ID;
    }

    public void setNation_ID(int i) {
        this.Nation_ID = i;
    }

    public String getNation_Name() {
        return this.Nation_Name;
    }

    public void setNation_Name(String str) {
        this.Nation_Name = str;
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
