package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "WORK_CODE")
public class WorkCode extends AbstractORM<WorkCode> implements Serializable {
    private static final long serialVersionUID = 1512784716802L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Flag", defaultValue = "0")
    private int Flag = 0;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Work_Code_Name")
    private String Work_Code_Name;
    @DatabaseField(columnName = "Work_Code_Num")
    private String Work_Code_Num;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "WorkCode_default.yml";
    }

    public WorkCode() {
        super(WorkCode.class);
    }

    public String getWork_Code_Num() {
        return this.Work_Code_Num;
    }

    public void setWork_Code_Num(String str) {
        this.Work_Code_Num = str;
    }

    public String getWork_Code_Name() {
        return this.Work_Code_Name;
    }

    public void setWork_Code_Name(String str) {
        this.Work_Code_Name = str;
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
