package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ROLE_PERMISSION")
public class RolePermission extends AbstractORM<RolePermission> implements Serializable {
    private static final long serialVersionUID = 1512784716665L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Permission")
    private int Permission;
    @DatabaseField(columnName = "Role_Name")
    private String Role_Name;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "RolePermission_default.yml";
    }

    public RolePermission() {
        super(RolePermission.class);
    }

    public String getRole_Name() {
        return this.Role_Name;
    }

    public void setRole_Name(String str) {
        this.Role_Name = str;
    }

    public int getPermission() {
        return this.Permission;
    }

    public void setPermission(int i) {
        this.Permission = i;
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
