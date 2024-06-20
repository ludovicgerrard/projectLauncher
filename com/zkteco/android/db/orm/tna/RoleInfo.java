package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ROLE_INFO")
public class RoleInfo extends AbstractORM<RoleInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716651L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Permissions_Value")
    private int Permissions_Value;
    @DatabaseField(columnName = "Role_Id")
    private int Role_Id;
    @DatabaseField(columnName = "Role_Name")
    private String Role_Name;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "RoleInfo_default.yml";
    }

    public RoleInfo() {
        super(RoleInfo.class);
    }

    public int getRole_Id() {
        return this.Role_Id;
    }

    public void setRole_Id(int i) {
        this.Role_Id = i;
    }

    public String getRole_Name() {
        return this.Role_Name;
    }

    public void setRole_Name(String str) {
        this.Role_Name = str;
    }

    public int getPermissions_Value() {
        return this.Permissions_Value;
    }

    public void setPermissions_Value(int i) {
        this.Permissions_Value = i;
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
