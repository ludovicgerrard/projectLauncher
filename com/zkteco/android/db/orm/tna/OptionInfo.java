package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "OPTION_INFO")
public class OptionInfo extends AbstractORM<OptionInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716545L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Encrypt_Flag", defaultValue = "0")
    private int Encrypt_Flag = 0;
    @DatabaseField(columnName = "Factory_Set_Value", defaultValue = "0")
    private String Factory_Set_Value = "0";
    @DatabaseField(columnName = "Is_Recovery_Factory_Set", defaultValue = "0")
    private int Is_Recovery_Factory_Set = 0;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Max_Value", defaultValue = "0")
    private int Max_Value = 0;
    @DatabaseField(columnName = "Min_Value", defaultValue = "0")
    private int Min_Value = 0;
    @DatabaseField(columnName = "Option_Name")
    private String Option_Name;
    @DatabaseField(columnName = "Option_Value")
    private String Option_Value;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "OptionInfo_default.yml";
    }

    public OptionInfo() {
        super(OptionInfo.class);
    }

    public String getOption_Name() {
        return this.Option_Name;
    }

    public void setOption_Name(String str) {
        this.Option_Name = str;
    }

    public String getOption_Value() {
        return this.Option_Value;
    }

    public void setOption_Value(String str) {
        this.Option_Value = str;
    }

    public int getEncrypt_Flag() {
        return this.Encrypt_Flag;
    }

    public void setEncrypt_Flag(int i) {
        this.Encrypt_Flag = i;
    }

    public String getFactory_Set_Value() {
        return this.Factory_Set_Value;
    }

    public void setFactory_Set_Value(String str) {
        this.Factory_Set_Value = str;
    }

    public int getIs_Recovery_Factory_Set() {
        return this.Is_Recovery_Factory_Set;
    }

    public void setIs_Recovery_Factory_Set(int i) {
        this.Is_Recovery_Factory_Set = i;
    }

    public int getMax_Value() {
        return this.Max_Value;
    }

    public void setMax_Value(int i) {
        this.Max_Value = i;
    }

    public int getMin_Value() {
        return this.Min_Value;
    }

    public void setMin_Value(int i) {
        this.Min_Value = i;
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
