package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "FUNC_PERMISSION")
public class FuncPermission extends AbstractORM<FuncPermission> implements Serializable {
    private static final long serialVersionUID = 1512784716401L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Func_Name")
    private String Func_Name;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Permission")
    private int Permission;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "FuncPermission_default.yml";
    }

    public FuncPermission() {
        super(FuncPermission.class);
    }

    public String getFunc_Name() {
        return this.Func_Name;
    }

    public void setFunc_Name(String str) {
        this.Func_Name = str;
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
