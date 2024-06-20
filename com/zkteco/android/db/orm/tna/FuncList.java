package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "FUNC_LIST")
public class FuncList extends AbstractORM<FuncList> implements Serializable {
    private static final long serialVersionUID = 1512784716386L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Flag")
    private int Flag;
    @DatabaseField(columnName = "Func_ID")
    private int Func_ID;
    @DatabaseField(columnName = "Func_Name")
    private String Func_Name;
    @DatabaseField(columnName = "Function")
    private String Function;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Param")
    private String Param;
    @DatabaseField(columnName = "Path_Name")
    private String Path_Name;
    @DatabaseField(columnName = "Res_ID")
    private int Res_ID;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Type")
    private int Type;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "FuncList_default.yml";
    }

    public FuncList() {
        super(FuncList.class);
    }

    public String getFunc_Name() {
        return this.Func_Name;
    }

    public void setFunc_Name(String str) {
        this.Func_Name = str;
    }

    public int getRes_ID() {
        return this.Res_ID;
    }

    public void setRes_ID(int i) {
        this.Res_ID = i;
    }

    public int getType() {
        return this.Type;
    }

    public void setType(int i) {
        this.Type = i;
    }

    public String getPath_Name() {
        return this.Path_Name;
    }

    public void setPath_Name(String str) {
        this.Path_Name = str;
    }

    public String getFunction() {
        return this.Function;
    }

    public void setFunction(String str) {
        this.Function = str;
    }

    public String getParam() {
        return this.Param;
    }

    public void setParam(String str) {
        this.Param = str;
    }

    public int getFlag() {
        return this.Flag;
    }

    public void setFlag(int i) {
        this.Flag = i;
    }

    public int getFunc_ID() {
        return this.Func_ID;
    }

    public void setFunc_ID(int i) {
        this.Func_ID = i;
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
