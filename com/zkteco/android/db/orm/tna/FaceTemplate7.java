package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "FACE_TEMPLATE_7")
public class FaceTemplate7 extends AbstractORM<FaceTemplate7> implements Serializable {
    private static final long serialVersionUID = 1512784716328L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Face_ID")
    private int Face_ID;
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
        return "FaceTemplate7_default.yml";
    }

    public FaceTemplate7() {
        super(FaceTemplate7.class);
    }

    public String getUser_PIN() {
        return this.User_PIN;
    }

    public void setUser_PIN(String str) {
        this.User_PIN = str;
    }

    public int getFace_ID() {
        return this.Face_ID;
    }

    public void setFace_ID(int i) {
        this.Face_ID = i;
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
}
