package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "DEPARTMENT")
public class Department extends AbstractORM<Department> implements Serializable {
    private static final long serialVersionUID = 1512784716300L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Department_Name")
    private String Department_Name;
    @DatabaseField(columnName = "Department_No", defaultValue = "0")
    private int Department_No = 0;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "Department_default.yml";
    }

    public Department() {
        super(Department.class);
    }

    public int getDepartment_No() {
        return this.Department_No;
    }

    public void setDepartment_No(int i) {
        this.Department_No = i;
    }

    public String getDepartment_Name() {
        return this.Department_Name;
    }

    public void setDepartment_Name(String str) {
        this.Department_Name = str;
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
