package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "FINGER_VEIN_TEMPLATE")
public class FingerVeinTemplate extends AbstractORM<FingerVeinTemplate> implements Serializable {
    private static final long serialVersionUID = 1512784716343L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Fv_ID")
    private int Fv_ID;
    @DatabaseField(columnName = "Fv_ID_Index")
    private int Fv_ID_Index;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Size")
    private int Size;
    @DatabaseField(columnName = "Template", dataType = DataType.BYTE_ARRAY)
    private byte[] Template;
    @DatabaseField(canBeNull = false, columnName = "User_PIN")
    private String User_PIN;
    @DatabaseField(columnName = "Valid")
    private int Valid;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "FingerVeinTemplate_default.yml";
    }

    public FingerVeinTemplate() {
        super(FingerVeinTemplate.class);
    }

    public String getUser_PIN() {
        return this.User_PIN;
    }

    public void setUser_PIN(String str) {
        this.User_PIN = str;
    }

    public int getFv_ID() {
        return this.Fv_ID;
    }

    public void setFv_ID(int i) {
        this.Fv_ID = i;
    }

    public int getValid() {
        return this.Valid;
    }

    public void setValid(int i) {
        this.Valid = i;
    }

    public int getSize() {
        return this.Size;
    }

    public void setSize(int i) {
        this.Size = i;
    }

    public byte[] getTemplate() {
        return this.Template;
    }

    public void setTemplate(byte[] bArr) {
        this.Template = bArr;
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

    public int getFv_ID_Index() {
        return this.Fv_ID_Index;
    }

    public void setFv_ID_Index(int i) {
        this.Fv_ID_Index = i;
    }
}
