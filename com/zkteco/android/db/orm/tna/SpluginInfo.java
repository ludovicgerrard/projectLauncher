package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "SPLUGIN_INFO")
public class SpluginInfo extends AbstractORM<SpluginInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716869L;
    @DatabaseField(columnName = "Action_Type", defaultValue = "0")
    private int Action_Type = 0;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Icon_Type", defaultValue = "0")
    private int Icon_Type = 0;
    @DatabaseField(columnName = "Is_Default")
    private String Is_Default;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Name")
    private String Name;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "SpluginInfo_default.yml";
    }

    public SpluginInfo() {
        super(SpluginInfo.class);
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String str) {
        this.Name = str;
    }

    public int getIcon_Type() {
        return this.Icon_Type;
    }

    public void setIcon_Type(int i) {
        this.Icon_Type = i;
    }

    public int getAction_Type() {
        return this.Action_Type;
    }

    public void setAction_Type(int i) {
        this.Action_Type = i;
    }

    public String getIs_Default() {
        return this.Is_Default;
    }

    public void setIs_Default(String str) {
        this.Is_Default = str;
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
