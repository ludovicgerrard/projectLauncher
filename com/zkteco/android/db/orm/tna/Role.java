package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ROLE")
public class Role extends AbstractORM<Role> implements Serializable {
    private static final long serialVersionUID = 1512784716637L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Description")
    private String Description;
    @DatabaseField(columnName = "Flag")
    private int Flag;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Res_ID")
    private int Res_ID;
    @DatabaseField(columnName = "Role_Name")
    private String Role_Name;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Using_Status")
    private int Using_Status;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "Role_default.yml";
    }

    public Role() {
        super(Role.class);
    }

    public String getRole_Name() {
        return this.Role_Name;
    }

    public void setRole_Name(String str) {
        this.Role_Name = str;
    }

    public int getRes_ID() {
        return this.Res_ID;
    }

    public void setRes_ID(int i) {
        this.Res_ID = i;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String str) {
        this.Description = str;
    }

    public int getUsing_Status() {
        return this.Using_Status;
    }

    public void setUsing_Status(int i) {
        this.Using_Status = i;
    }

    public int getFlag() {
        return this.Flag;
    }

    public void setFlag(int i) {
        this.Flag = i;
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
