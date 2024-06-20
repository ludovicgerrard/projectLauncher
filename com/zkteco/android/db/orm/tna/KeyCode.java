package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "KEY_CODE")
public class KeyCode extends AbstractORM<KeyCode> implements Serializable {
    private static final long serialVersionUID = 1512784716446L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Key_Name")
    private String Key_Name;
    @DatabaseField(columnName = "Key_Value")
    private int Key_Value;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Res_ID")
    private int Res_ID;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "KeyCode_default.yml";
    }

    public KeyCode() {
        super(KeyCode.class);
    }

    public String getKey_Name() {
        return this.Key_Name;
    }

    public void setKey_Name(String str) {
        this.Key_Name = str;
    }

    public int getRes_ID() {
        return this.Res_ID;
    }

    public void setRes_ID(int i) {
        this.Res_ID = i;
    }

    public int getKey_Value() {
        return this.Key_Value;
    }

    public void setKey_Value(int i) {
        this.Key_Value = i;
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
